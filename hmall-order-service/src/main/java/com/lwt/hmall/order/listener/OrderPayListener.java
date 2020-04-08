package com.lwt.hmall.order.listener;

import com.lwt.hmall.api.bean.OmsOrder;
import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.order.mapper.OmsOrderItemMapper;
import com.lwt.hmall.order.mapper.OmsOrderMapper;
import com.lwt.hmall.service.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/16 15:35
 * @Description
 */
@Component
public class OrderPayListener {

    @Autowired
    private OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.ORDER_PAY_DLQ_QUEUE)
    @Transactional
    public void handler(Long orderId){
        try {
            OmsOrder omsOrder = omsOrderMapper.selectByPrimaryKey(orderId);
            //订单如果未支付则删除
            if (omsOrder!=null&&omsOrder.getStatus()==0){
                omsOrderMapper.delete(omsOrder);
                OmsOrderItem omsOrderItemParam = new OmsOrderItem();
                omsOrderItemParam.setOrderId(orderId);
                List<OmsOrderItem> omsOrderItems = omsOrderItemMapper.select(omsOrderItemParam);
                omsOrderItemMapper.delete(omsOrderItemParam);
                rabbitTemplate.convertAndSend(RabbitConfig.WARE_LOCK_EXCHANGE,RabbitConfig.WARE_UNLOCK_ROUTING_KEY,omsOrderItems);
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rabbitTemplate.convertAndSend(RabbitConfig.ORDER_PAY_EXCHANGE,RabbitConfig.ORDER_PAY_DLQ_ROUTING_KEY,orderId);
        }
    }
}
