package com.lwt.hmall.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author lwt
 * @Date 2020/3/15 16:48
 * @Description
 */
@Configuration
@ConditionalOnClass(AmqpAdmin.class)
@ConditionalOnProperty(value = "settings.service.rabbit.enable",havingValue = "false",matchIfMissing = true)
@EnableRabbit
public class RabbitConfig {

    /**
     * 订单支付交换机
     */
    public static final String ORDER_PAY_EXCHANGE="order.pay.exchange";
    /**
     * 订单支付延迟队列
     */
    public static final String ORDER_PAY_TTL_QUEUE="order.pay.ttl.queue";
    /**
     * 订单支付延迟队列路由键
     */
    public static final String ORDER_PAY_TTL_ROUTING_KEY="order.pay.ttl.routing.key";
    /**
     * 订单支付死信队列
     */
    public static final String ORDER_PAY_DLQ_QUEUE="order.pay.dlq.queue";
    /**
     * 订单支付死信队列路由键
     */
    public static final String ORDER_PAY_DLQ_ROUTING_KEY="order.pay.dlq.queue.routing.key";


    /**
     * 库存锁定交换机
     */
    public static final String WARE_LOCK_EXCHANGE="ware.lock.exchange";
    /**
     * 库存解锁队列
     */
    public static final String WARE_UNLOCK_QUEUE="ware.unlock.queue";
    /**
     * 库存解锁队列路由键
     */
    public static final String WARE_UNLOCK_ROUTING_KEY="ware.unlock.routing.key";

    public RabbitConfig(AmqpAdmin amqpAdmin){
        initOrderPayExchange(amqpAdmin);
        initWareLockExchange(amqpAdmin);
    }

    private void initWareLockExchange(AmqpAdmin amqpAdmin) {
        Exchange wareLockExchange = ExchangeBuilder.directExchange(WARE_LOCK_EXCHANGE)
                .durable(true)
                .build();
        amqpAdmin.declareExchange(wareLockExchange);

        Queue wareUnlockQueue = QueueBuilder.durable(WARE_UNLOCK_QUEUE)
                .build();
        amqpAdmin.declareQueue(wareUnlockQueue);

        Binding wareUnlockBinding = BindingBuilder.bind(wareUnlockQueue)
                .to(wareLockExchange)
                .with(WARE_UNLOCK_ROUTING_KEY)
                .noargs();
        amqpAdmin.declareBinding(wareUnlockBinding);
    }

    private void initOrderPayExchange(AmqpAdmin amqpAdmin) {
        Exchange orderPayExchange = ExchangeBuilder.directExchange(ORDER_PAY_EXCHANGE)
                .durable(true)//持久化
                .build();
        amqpAdmin.declareExchange(orderPayExchange);

        Queue orderPayTtlQueue = QueueBuilder.durable(ORDER_PAY_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_PAY_EXCHANGE)//设置死信交换机
                .withArgument("x-dead-letter-routing-key", ORDER_PAY_DLQ_ROUTING_KEY)//设置死信路由键
                .withArgument("x-message-ttl", TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES))//设置消息过期时间
                .build();
        amqpAdmin.declareQueue(orderPayTtlQueue);

        Queue orderPayDlqQueue = QueueBuilder.durable(ORDER_PAY_DLQ_QUEUE)
                .build();
        amqpAdmin.declareQueue(orderPayDlqQueue);

        Binding orderPayTtlBinding = BindingBuilder.bind(orderPayTtlQueue)
                .to(orderPayExchange)
                .with(ORDER_PAY_TTL_ROUTING_KEY)
                .noargs();
        amqpAdmin.declareBinding(orderPayTtlBinding);

        Binding orderPayDlqBinding = BindingBuilder.bind(orderPayDlqQueue)
                .to(orderPayExchange)
                .with(ORDER_PAY_DLQ_ROUTING_KEY)
                .noargs();
        amqpAdmin.declareBinding(orderPayDlqBinding);
    }

    /**
     * 设置消息序列化器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
