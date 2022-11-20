package com.nhk.thesis.service.implement;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.entity.User;
import com.nhk.thesis.service.interfaces.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public JsonNode sendSimpleMessage() throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/mail.thesiscm.tech/messages")
			.basicAuth("api", "c06e7bb58838714c444982f592d49ddd-4534758e-d1085791")
                .queryString("from", "Hệ thống quản lý luận văn <USER@mail.thesiscm.tech>")
                .queryString("to", "khangb1805770@student.ctu.edu.vn")
                .queryString("subject", "hello")
                .queryString("text", "testing")
                .asJson();
        return request.getBody();
    }

    @Override
    public JsonNode sendAccountActivationEmail(User user, String rawPassword) throws UnirestException {
        return null;
    }

    @Override
    public JsonNode sendAccountDisabledEmail() throws UnirestException {
        return null;
    }

    @Override
    public JsonNode sendAccountEnableEmail() throws UnirestException {
        return null;
    }

    @Override
    public JsonNode sendPasswordChangedEmail() throws UnirestException {
        return null;
    }
}
