package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.vo.IMarkVO;
import com.nhk.thesis.service.interfaces.IMarkService;

import java.util.List;

public class IMarkServiceImpl implements IMarkService {
    @Override
    public boolean create() {
        //check if student already has presentation scheduled
        //check if student is in any topic
        //check if the topic that the provided student is in type of group or individual
        //Add attribute to determine student status in that topic
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public IMarkVO get(String id) {
        return null;
    }

    @Override
    public IMarkVO getByStudentCode(String studentCode) {
        return null;
    }

    @Override
    public List<IMarkVO> getByLecturer(String account) {
        return null;
    }
}
