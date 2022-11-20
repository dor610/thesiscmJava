package com.nhk.thesis.service.interfaces;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.entity.constant.UserStatus;
import com.nhk.thesis.entity.constant.UserTitle;
import com.nhk.thesis.entity.vo.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean createUser(String account, String name, String email, String phone, String role, String title) throws UnirestException;

    boolean disableUser(String account) throws UnirestException;

    boolean enableUser(String account) throws UnirestException;

    boolean activateUser(String account, String password, String activationCode);

    boolean changePassword(String account, String password) throws UnirestException;

    boolean updateUser(String account, String name, String email, String phone, String title);

    List<UserVO> getActivatedUser();

    List<UserVO> getDisabledUser();

    List<UserVO> getInactivatedUser();

    List<UserVO> search(String keyword);

    UserVO getUser(String id);

    UserVO getUserByAccount(String account);

    String checkAuthentication(String account);

    List<UserVO> findUserByStatus(UserStatus status);

    Map<String, String> getRole();

    Map<String, String> getTitle();

    String generatePassword(int length);

    UserVO getByNameAndTitle(String name);

    UserTitle processTitle(String title);
}
