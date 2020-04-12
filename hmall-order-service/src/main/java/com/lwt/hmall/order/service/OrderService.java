package com.lwt.hmall.order.service;

import com.lwt.hmall.api.bean.*;
import com.lwt.hmall.api.constant.CodeEnum;
import com.lwt.hmall.common.client.SkuClient;
import com.lwt.hmall.common.client.UserClient;
import com.lwt.hmall.common.client.WareClient;
import com.lwt.hmall.order.constant.CacheName;
import com.lwt.hmall.order.mapper.OmsOrderItemMapper;
import com.lwt.hmall.order.mapper.OmsOrderMapper;
import com.lwt.hmall.redis.cache.CacheFuzzyRemove;
import com.lwt.hmall.redis.constant.RedisConstants;
import com.lwt.hmall.service.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author lwt
 * @Date 2020/3/8 16:16
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class OrderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private WareClient wareClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private SkuClient skuClient;

    @Autowired
    private CartService cartService;

    @Autowired
    private OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    /**
     * 生成订单编号
     *
     * @return
     */
    public String createOrderSn() {
        String orderToken = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        String key= RedisConstants.PREFIX_TEMP+":"+this.getClass().getName()+
                ":orderSn"+
                ":"+orderToken;
        stringRedisTemplate.opsForValue().set(key, orderToken, 1, TimeUnit.HOURS);
        return orderToken;
    }

    /**
     * 检查订单编号
     *
     * @param orderToken
     * @return
     */
    public boolean checkOrderSn(String orderToken) {
        String key= RedisConstants.PREFIX_TEMP+":"+this.getClass().getName()+
                ":orderSn"+
                ":"+orderToken;
        boolean delete = stringRedisTemplate.delete(key);
        return delete;
    }

    /**
     * 保存订单
     * @param orderSn
     * @param cartItemIds
     * @param userId
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+':get*'",
            "#targetName+':list*'"
    })
    @Transactional
    public boolean saveOrder(String orderSn, List<Long> cartItemIds, long userId, long receiverId) {
        //校验订单令牌
        if (!checkOrderSn(orderSn)) {
            return false;
        }
        //获取用户信息
        Result<UmsUser> userResult = userClient.getUserById(userId);
        if (userResult.getCode() != CodeEnum.SUCCESS.getCode()) {
            return false;
        }
        UmsUser user = userResult.getData();
        //获取收货地址信息
        Result<UmsUserReceiveAddress> receiveAddressResult = userClient.getUserReceiveAddressByUserIdAndReceiveId(userId, receiverId);
        if (receiveAddressResult.getCode()!=CodeEnum.SUCCESS.getCode()){
            return false;
        }
        UmsUserReceiveAddress receiveAddress = receiveAddressResult.getData();

       //获取结算的购物车商品
        List<OmsCartItem> omsCartItems = cartService.listCartItemsInCartItemIds(cartItemIds);

        //删除结算的购物车商品
        if(!cartService.removeCartItemByUserIdAndInCartItemIds(cartItemIds, userId)){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        BigDecimal totalAmount = new BigDecimal(0);
        for (OmsCartItem omsCartItem : omsCartItems) {
            //校验价格
            Result<BigDecimal> priceResult = skuClient.getSkuPriceBySkuId(omsCartItem.getProductSkuId());
            if (priceResult.getCode()==CodeEnum.SUCCESS.getCode()){
                BigDecimal data = priceResult.getData();
                omsCartItem.setPrice(data);
            }
            //统计总金额
            BigDecimal subtotal = omsCartItem.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity()));
            totalAmount.add(subtotal);
        }

        //校验库存

        //保存订单
        OmsOrder omsOrderParam = new OmsOrder();
        omsOrderParam.setUserId(userId);
        omsOrderParam.setOrderSn(orderSn);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        omsOrderParam.setCreateTime(timestamp);
        omsOrderParam.setUsername(user.getUsername());
        omsOrderParam.setTotalAmount(totalAmount);
        omsOrderParam.setPayAmount(totalAmount);
        omsOrderParam.setFreightAmount(new BigDecimal(0));
        omsOrderParam.setPayType(0L);
        omsOrderParam.setSourceType(0L);
        omsOrderParam.setStatus(0L);
        omsOrderParam.setOrderType(0L);
        omsOrderParam.setAutoConfirmDay(7L);
        omsOrderParam.setBillType(0L);
        omsOrderParam.setReceiverName(receiveAddress.getName());
        omsOrderParam.setReceiverPhone(receiveAddress.getPhone());
        omsOrderParam.setReceiverPostCode(receiveAddress.getPostCode());
        omsOrderParam.setReceiverProvince(receiveAddress.getProvince());
        omsOrderParam.setReceiverCity(receiveAddress.getCity());
        omsOrderParam.setReceiverRegion(receiveAddress.getRegion());
        omsOrderParam.setReceiverDetailAddress(receiveAddress.getDetailAddress());
        omsOrderParam.setNote("无备注");
        omsOrderParam.setConfirmStatus(0L);
        omsOrderParam.setDeleteStatus(0L);
        omsOrderParam.setModifyTime(timestamp);
        if(omsOrderMapper.insertSelective(omsOrderParam)<=0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        //保存订单详情
        List<OmsOrderItem> omsOrderItemParams=new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            OmsOrderItem omsOrderItemParam = new OmsOrderItem();
            omsOrderItemParam.setOrderId(omsOrderParam.getId());
            omsOrderItemParam.setOrderSn(omsOrderParam.getOrderSn());
            omsOrderItemParam.setProductId(omsCartItem.getProductId());
            omsOrderItemParam.setProductPic(omsCartItem.getProductPic());
            omsOrderItemParam.setProductName(omsCartItem.getProductName());
            omsOrderItemParam.setProductPrice(omsCartItem.getPrice());
            omsOrderItemParam.setProductQuantity(omsCartItem.getQuantity());
            omsOrderItemParam.setProductSkuId(omsCartItem.getProductSkuId());
            omsOrderItemParam.setProductCategoryId(omsCartItem.getProductCategoryId());
            omsOrderItemParam.setProductAttr(omsCartItem.getProductAttr());
            omsOrderItemParams.add(omsOrderItemParam);
        }
        if(omsOrderItemMapper.insertList(omsOrderItemParams)<=0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        //库存锁定
        Result lockResult = wareClient.lockWareSkuByOrderItems(omsOrderItemParams);
        if (lockResult.getCode()!=CodeEnum.SUCCESS.getCode()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        //发送消息设置订单超时未支付取消
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_PAY_EXCHANGE,RabbitConfig.ORDER_PAY_TTL_ROUTING_KEY,omsOrderParam.getId());

        return true;
    }

    /**
     * 获取订单和订单详情
     * @param userId
     * @return
     */
    @Cacheable
    public List<OmsOrder> listOrdersByUserId(long userId){
        OmsOrder omsOrderParam = new OmsOrder();
        omsOrderParam.setUserId(userId);
        List<OmsOrder> omsOrders = omsOrderMapper.select(omsOrderParam);
        for (OmsOrder omsOrder : omsOrders) {
            OmsOrderItem omsOrderItemParam = new OmsOrderItem();
            omsOrderItemParam.setOrderId(omsOrder.getId());
            List<OmsOrderItem> omsOrderItems = omsOrderItemMapper.select(omsOrderItemParam);
            omsOrder.setOrderItems(omsOrderItems);
        }
        return omsOrders;
    }

}
