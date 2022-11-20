package com.nhk.thesis.service.interfaces;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.entity.User;

public interface MailService {

    public JsonNode sendSimpleMessage() throws UnirestException;

    public JsonNode sendAccountActivationEmail(User user, String rawPassword) throws  UnirestException;

    public JsonNode sendAccountDisabledEmail() throws UnirestException;

    public JsonNode sendAccountEnableEmail() throws  UnirestException;

    public JsonNode sendPasswordChangedEmail() throws UnirestException;
}
