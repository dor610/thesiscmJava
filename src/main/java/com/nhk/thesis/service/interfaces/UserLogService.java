package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.UserLog;
import com.nhk.thesis.entity.constant.UserLogType;
import com.nhk.thesis.entity.vo.UserLogVO;

import java.util.List;

public interface UserLogService {

    List<UserLogVO> getUserLogsByUserId(String userId);

    void writeLog(String userId, UserLogType type, String timestamp, String note, String change);
}
