package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.vo.IMarkVO;

import java.util.List;

public interface IMarkService {

    boolean create();

    boolean update();

    boolean delete();

    IMarkVO get(String id);

    IMarkVO getByStudentCode(String studentCode);

    List<IMarkVO> getByLecturer(String account);


}
