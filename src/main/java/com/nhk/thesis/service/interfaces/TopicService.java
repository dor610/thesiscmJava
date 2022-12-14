package com.nhk.thesis.service.interfaces;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.Topic;
import com.nhk.thesis.entity.common.DocumentFile;
import com.nhk.thesis.entity.vo.SemesterVO;
import com.nhk.thesis.entity.vo.TopicVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface TopicService {

    TopicVO getTopic(String id);

    TopicVO getTopicByStudentAndSemester(String student, String semester);

    List<TopicVO> getTopicByStudent(String student);

    List<TopicVO> getTopicInCurrentSemester();

    TopicVO getByStudentInCurrentSemester(String student);

    List<TopicVO> getTopicInCurrentSemesterByAccount(String account);

    List<TopicVO> getTopicByAccount(String account);

    List<TopicVO> getAllTopics();

    List<TopicVO> search(String keyWord);

    List<DocumentFile> getTopicDocument(String id);

    boolean setTopicSourceCode(MultipartFile file, String id) throws IOException, DbxException;

    boolean setTopicReportFile(MultipartFile file, String id) throws IOException, DbxException;

    boolean deleteSourceFile(String id) throws DbxException;

    boolean deleteReportFile(String id) throws DbxException;

    boolean isStudentHasTopic(String student, SemesterVO semester);

    boolean createTopic(String name, String enName, String type, String student, String account);

    boolean updateTopic(String id, String name, String enName, String type, String student);

    boolean deleteTopic(String id);


    void test ();

}
