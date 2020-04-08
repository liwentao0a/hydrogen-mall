package com.lwt.hmall.ware.listener;

import com.lwt.hmall.api.bean.OmsOrderItem;
import com.lwt.hmall.service.config.RabbitConfig;
import com.lwt.hmall.ware.service.WareService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/18 16:18
 * @Description
 */
@Component
public class WareLockListener {

    @Autowired
    private WareService wareService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = RabbitConfig.WARE_UNLOCK_QUEUE)
    public void wareUnlockQueueListener(List<OmsOrderItem> omsOrderItems){
        try {
            wareService.unlockWareSkuByOrderItems(omsOrderItems);
        }catch (Exception e){
            rabbitTemplate.convertAndSend(RabbitConfig.WARE_LOCK_EXCHANGE,RabbitConfig.WARE_UNLOCK_ROUTING_KEY,omsOrderItems);
        }
    }
}
