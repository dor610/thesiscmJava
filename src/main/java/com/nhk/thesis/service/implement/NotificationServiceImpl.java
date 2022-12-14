package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Notification;
import com.nhk.thesis.restApi.WebSocketApi;
import com.nhk.thesis.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private SimpMessagingTemplate messageTemplate;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @Override
    public void sendNotification(Notification notification) {
        messageTemplate.convertAndSendToUser(notification.getRecipient(), "/msg", notification);
    }
}
