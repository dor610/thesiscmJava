package com.nhk.thesis.service.implement;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.entity.User;
import com.nhk.thesis.entity.constant.UserLogType;
import com.nhk.thesis.entity.constant.UserRole;
import com.nhk.thesis.entity.constant.UserStatus;
import com.nhk.thesis.entity.constant.UserTitle;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.UserRepository;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private MailServiceImpl mailService;

    private UserLogServiceImpl userLogService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailServiceImpl mailService, UserLogServiceImpl userLogService){
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userLogService = userLogService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean createUser(String account, String name, String email, String phone, String role, String title) throws UnirestException {
        String password = "abc";//generatePassword(10);
        String activationCode = generatePassword(10);
        UserRole userRole = UserRole.getRoleByCode(role);
        if (userRepository.existsByAccount(account)) {
            return false;
        }
        User user = new User(account, password, name, email, phone, userRole, UserTitle.getUserTitleByCode(title), activationCode);
        userRepository.insert(user);
        mailService.sendAccountActivationEmail(user, password);
        userLogService.writeLog(user.getId(), UserLogType.CREATE, System.currentTimeMillis()+"","Người dùng được tạo mới", "" );
        return true;
    }

    @Override
    public boolean disableUser(String account) throws UnirestException {
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            if(UserStatus.ENABLED.equals(user.getStatus())) {
                user.setStatus(UserStatus.DISABLED);
                userRepository.save(user);
                mailService.sendAccountDisabledEmail();
                userLogService.writeLog(user.getId(), UserLogType.DEACTIVATE, String.valueOf(System.currentTimeMillis()),
                        "Vô hiệu hoá người dùng", "");
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean enableUser(String account) throws UnirestException {
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            if(UserStatus.DISABLED.equals(user.getStatus())) {
                user.setStatus(UserStatus.ENABLED);
                userRepository.save(user);
                mailService.sendAccountEnableEmail();
                userLogService.writeLog(user.getId(), UserLogType.ACTIVATE, String.valueOf(System.currentTimeMillis()), "Kích hoạt tài khoản người dùng", "");
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean activateUser(String account, String password, String activationCode) {
        User user = userRepository.findUserByAccount(account);
        if(user != null) {
            if(UserStatus.INACTIVATE.equals(user.getStatus())) {
                    if (user.getActivationCode().equals(activationCode)) {
                        user.setStatus(UserStatus.ENABLED);
                        user.setPassword(passwordEncoder.encode(password));
                        userRepository.save(user);
                        userLogService.writeLog(user.getId(), UserLogType.ACTIVATE, String.valueOf(System.currentTimeMillis()),
                                "Kích hoạt tài khoản", "");
                        return true;
                    }
                }
            return false;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean changePassword(String account, String password) throws UnirestException {
        User user = userRepository.findUserByAccount(account);

        if(user != null && !passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            mailService.sendPasswordChangedEmail();
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUser(String account, String name, String email, String phone, String title) {
        User user = userRepository.findUserByAccount(account);
        if(user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setTitle(UserTitle.getUserTitleByCode(title));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public List<UserVO> getActivatedUser() {
        List<User> users =  userRepository.findUserByStatus(UserStatus.ENABLED);
        return users.stream().map((u) -> { return new UserVO(u);}).collect(Collectors.toList());
    }

    @Override
    public List<UserVO> getDisabledUser() {
        List<User> users =  userRepository.findUserByStatus(UserStatus.DISABLED);
        return users.stream().map((u) -> { return new UserVO(u);}).collect(Collectors.toList());
    }

    @Override
    public List<UserVO> getInactivatedUser() {
        List<User> users =  userRepository.findUserByStatus(UserStatus.INACTIVATE);
        return users.stream().map((u) -> { return new UserVO(u);}).collect(Collectors.toList());
    }

    @Override
    public List<UserVO> search(String keyword) {
        return null;
    }

    @Override
    public UserVO getUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) return new UserVO(user);
        return null;
    }

    @Override
    public UserVO getUserByAccount(String account) {
        User user = userRepository.findUserByAccount(account);
        if(user != null) return new UserVO(user);
        return null;
    }

    @Override
    public String checkAuthentication(String account) {
        if(userRepository.existsByAccount(account))
            return account;
        return null;
    }

    @Override
    public List<UserVO> findUserByStatus(UserStatus status) {
        List<User> users = userRepository.findUserByStatus(status);
        List<UserVO> result = new ArrayList<>();
        users.forEach((u) ->  result.add(new UserVO(u)));

        return result;
    }

    @Override
    public Map<String, String> getRole() {
        return UserRole.getAllRole();
    }

    @Override
    public Map<String, String> getTitle() {
        return UserTitle.getValues();
    }

    public String generatePassword(int length){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        //return passwordEncoder.encode(sb.toString());
        return sb.toString();
    }

    @Override
    public UserVO getByNameAndTitle(String str) {
        List<String> list = new ArrayList<>(Arrays.asList(str.split("\\s+")));
        if(list.size() > 0) {
            String title = list.get(0).replace(".", "");
            list.remove(0);
            String name = String.join(" ", list.toArray(new String[0]));
            User user = userRepository.findUserByNameAndTitle(name, processTitle(title));
            if(user != null) {
                return new UserVO(user);
            }
        }

        return null;
    }

    @Override
    public UserTitle processTitle(String title) {
        String processedTitle = title.toLowerCase();
        if(processedTitle.equals("ts"))
            return UserTitle.TS;
        if(processedTitle.equals("ths"))
            return UserTitle.Ths;
        if(processedTitle.equals("pgs"))
            return UserTitle.PGs;
        return null;
    }
}
