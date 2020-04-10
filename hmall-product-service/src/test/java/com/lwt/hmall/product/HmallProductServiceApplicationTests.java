package com.lwt.hmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HmallProductServiceApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    void contextLoads() {
        Exchange te = ExchangeBuilder.directExchange("te").durable(true).build();
        Queue tq = QueueBuilder.durable("tq").build();
        Binding t = BindingBuilder.bind(tq).to(te).with("t").noargs();
        amqpAdmin.declareExchange(te);
        amqpAdmin.declareQueue(tq);
        amqpAdmin.declareBinding(t);
        rabbitTemplate.convertAndSend("te","t","test");
    }

}
