package com.lwt.hmall.order;

import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class HmallOrderServiceApplicationTests {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate<String,Object> template;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Jedis jedis;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private MessageConverter messageConverter;

    @Test
    void contextLoads() {
        System.out.println(messageConverter);
        UmsUser umsUser = new UmsUser();
        umsUser.setNickname("bbb");
//        rabbitTemplate.convertAndSend("test","a",umsUser);

//        Exchange exchange = new DirectExchange("test.exchange");
//        amqpAdmin.declareExchange(exchange);
//        Queue queue = new Queue("test.queue");
//        amqpAdmin.declareQueue(queue);
//        Binding binding = new Binding("test.queue", Binding.DestinationType.QUEUE, "test.exchange", "test.queue", null);
//        amqpAdmin.declareBinding(binding);
//        rabbitTemplate.convertAndSend("test.exchange","test.ttl",umsUser);


        long convert = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
        System.out.println(convert);
    }





}
