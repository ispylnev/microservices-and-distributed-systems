package com.notification.service;

import com.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record NotificationConsumer(NotificationService notificationService) {


    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void onMessage(NotificationRequest notificationRequest) {
        log.info("consumed {} from queue", notificationRequest);
        notificationService.saveNotification(notificationRequest);
    }
}
