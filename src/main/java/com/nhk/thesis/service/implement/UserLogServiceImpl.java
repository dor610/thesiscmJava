package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.UserLog;
import com.nhk.thesis.entity.constant.UserLogType;
import com.nhk.thesis.entity.vo.UserLogVO;
import com.nhk.thesis.repository.UserLogRepository;
import com.nhk.thesis.service.interfaces.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogServiceImpl implements UserLogService {

    private UserLogRepository userLogRepository;

    @Autowired
    public UserLogServiceImpl(UserLogRepository userLogRepository){
        this.userLogRepository = userLogRepository;
    }

    @Override
    public List<UserLogVO> getUserLogsByUserId(String userId) {
        List<UserLog> logs = userLogRepository.getUserLogsByUserId(userId);
        List<UserLogVO> logVO = logs.stream().map(log -> {return new UserLogVO(log);}).collect(Collectors.toList());

        return logVO;
    }

    @Override
    public void writeLog(String userId, UserLogType type, String timestamp, String note, String change) {
        UserLog userLog = new UserLog(userId, type, note, timestamp, change);
        userLogRepository.save(userLog);
    }

}
