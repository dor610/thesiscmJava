package com.nhk.thesis.service.interfaces;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.constant.IMarkStatus;
import com.nhk.thesis.entity.vo.IMarkVO;
import com.nhk.thesis.entity.vo.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMarkService {

    boolean create(String studentCode, String lecturer, MultipartFile file, String lecturerComment, String deanComment, String reason, String other) throws IOException, DbxException;

    boolean update(String id, MultipartFile file, String lecturerComment, String deanComment, String reason, String other) throws IOException, DbxException;

    boolean delete(String id) throws DbxException;

    IMarkVO get(String id);

    IMarkVO getByStudent(String student);

    List<IMarkVO> getByLecturer(String lecturer);

    List<StudentVO> getStudentWithoutIMark(String account);

    List<IMarkVO> getByConfirmStatusAndLecturer(boolean confirm, String lecturer);

    List<Integer> getCountIMarkByUserAndSemester(String account, String semester);

    List<Integer> getCountIMarkBySemester(String semester);

    List<IMarkVO> getByConfirmAndStatusAndLecturer(boolean confirm,IMarkStatus status, String lecturer);

    List<IMarkVO> getByCompleteAndConfirmAndStatusAndLecturer(boolean complete, boolean confirm,IMarkStatus status, String lecturer);

    List<IMarkVO> getByLecturerAndStatus(String lecturer, IMarkStatus status);

    List<IMarkVO> getByStatusAndSemester(IMarkStatus status, String semester);

    List<IMarkVO> getBySemester(String semester);

    List<IMarkVO> getAll();

    void confirm(String id);

    void checkStatus();
}
