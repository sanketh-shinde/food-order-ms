package com.foodorder.notification.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @RabbitListener(queues = "testQueue")
    public void listen(String message) {
        System.out.println(message);
    }

}
