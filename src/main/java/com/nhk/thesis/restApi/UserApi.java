package com.nhk.thesis.restApi;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nhk.thesis.entity.User;
import com.nhk.thesis.entity.UserLog;
import com.nhk.thesis.entity.constant.UserStatus;
import com.nhk.thesis.entity.vo.UserLogVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.service.interfaces.UserLogService;
import com.nhk.thesis.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private UserService userService;

    private UserLogService userLogService;

    @Autowired
    public UserApi(UserService userService, UserLogService userLogService){
        this.userService = userService;
        this.userLogService = userLogService;
    }

    //create new user //
    @PostMapping("")
    public ResponseEntity<String> createAccount(@RequestParam("account") String account, @RequestParam("name") String name,
                                                @RequestParam("phone") String phone,
                                                @RequestParam("email") String email,
                                                @RequestParam("role") String role,
                                                @RequestParam("title") String title){
        try{
            boolean result = userService.createUser(account, name, email, phone, role, title);
            if(result){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/password")
    public ResponseEntity<Object> changePassword(@RequestParam("account") String account, @RequestParam("password") String password) throws UnirestException {
        return new ResponseEntity<>(userService.changePassword(account, password), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateAccount(@RequestParam("account") String account, @RequestParam("name") String name,
                                                @RequestParam("phone") String phone, @RequestParam("email") String email,
                                                @RequestParam("title") String title) {
        boolean result = userService.updateUser(account, name, email, phone, title);

        if(result)
            return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/activate")
    public ResponseEntity<Object> activateAccount(@RequestParam("account") String account, @RequestParam("password") String password,
                                                  @RequestParam("activationCode") String activationCode) {
        boolean result = userService.activateUser(account, password, activationCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/disabled")
    public ResponseEntity<Object> disableAccount(@RequestParam("account") String account) throws UnirestException {
        return new ResponseEntity<>(userService.disableUser(account), HttpStatus.OK);
    }

    @PostMapping("/enabled")
    public ResponseEntity<Object> enableAccount(@RequestParam("account") String account) throws UnirestException {
        return new ResponseEntity<>(userService.enableUser(account), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserInfo(@RequestParam("id") String id){
        UserVO user = userService.getUser(id);
        if(user == null){
            return new ResponseEntity<>("lỗi rồi", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<Object> getUserInfoByAccount(@RequestParam("account") String account) {
        UserVO user = userService.getUserByAccount(account);
        if(user == null){
            return new ResponseEntity<>("Lỗi rồi", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/log")
    public ResponseEntity<Object> getUserLogs(@RequestParam("id") String id) {
        List<UserLogVO> logs = userLogService.getUserLogsByUserId(id);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/enabled")
    public ResponseEntity<Object> getEnabledUsers() {
        List<UserVO> users = userService.getActivatedUser();
        if(users == null){
            return new ResponseEntity<>("Không tồn tại",HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/disabled")
    public ResponseEntity<Object> getDisabledUsers() {
        List<UserVO> users = userService.getDisabledUser();
        if(users == null){
            return new ResponseEntity<>("Không tồn tại",HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/inactivate")
    public ResponseEntity<Object> getInactivateUsers() {
        List<UserVO> users = userService.getInactivatedUser();
        if(users == null){
            return new ResponseEntity<>("Không tồn tại",HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/authenticate")
    public ResponseEntity<Object> checkAuthentication(@RequestParam("account") String account){
        UserVO result = userService.checkAuthentication(account);
        if(result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/role")
    public ResponseEntity<Object> getAllRoles() {
        return new ResponseEntity<>(userService.getRole(), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<Object> getAllTitles() { return new ResponseEntity<>(userService.getTitle(), HttpStatus.OK);}

    @GetMapping("/status")
    public ResponseEntity<Object> getUserByStatus(@RequestParam("status") String code) {
        return new ResponseEntity<>(userService.findUserByStatus(UserStatus.getUserStatusByCode(code)), HttpStatus.OK);
    }
}
