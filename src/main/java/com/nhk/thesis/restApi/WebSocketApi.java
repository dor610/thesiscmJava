package com.nhk.thesis.restApi;

import com.nhk.thesis.service.implement.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class WebSocketApi {
    public static SimpMessagingTemplate simpMessagingTemplate;
    public static Set<String> activeUser;

    @Autowired
    public WebSocketApi(SimpMessagingTemplate simpMessagingTemplate){
        WebSocketApi.simpMessagingTemplate = simpMessagingTemplate;
        activeUser = new HashSet<>();
    }

    @MessageMapping("/register")
    //Định nghĩa cái điểm đến của tin nhắn. để khi return sẽ gửi về đường dẫn đó
    @SendToUser("/queue/newMember")
    public Set<String> registerUser(String webChatUsername, SimpMessageHeaderAccessor headerAccessor){
        System.out.println("webSocketController: new connect "+ webChatUsername);
        if(!activeUser.contains(webChatUsername)) {
            // Add username to web socket session
            headerAccessor.getSessionAttributes().put("username", webChatUsername);
            activeUser.add(webChatUsername);
            simpMessagingTemplate.convertAndSend("/topic/newMember", webChatUsername);
            return activeUser;
        } else {
            return activeUser;
        }
    }

    //remove an user from connected user list
    @MessageMapping("/unregister")
    //Định nghĩa cái điểm đến của tin nhắn. để khi return sẽ gửi về đường dẫn đó
    @SendTo("/topic/disconnectedUser")
    public String unregisterUser(String webChatUsername){
        activeUser.remove(webChatUsername);
        return webChatUsername;
    }
}
