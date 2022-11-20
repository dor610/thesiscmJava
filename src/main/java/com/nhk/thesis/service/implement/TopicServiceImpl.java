package com.nhk.thesis.service.implement;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.Topic;
import com.nhk.thesis.entity.common.DocumentFile;
import com.nhk.thesis.entity.constant.TopicStatus;
import com.nhk.thesis.entity.constant.TopicType;
import com.nhk.thesis.entity.vo.SemesterVO;
import com.nhk.thesis.entity.vo.StudentVO;
import com.nhk.thesis.entity.vo.TopicVO;
import com.nhk.thesis.entity.vo.UserVO;
import com.nhk.thesis.repository.TopicRepository;
import com.nhk.thesis.service.interfaces.SemesterService;
import com.nhk.thesis.service.interfaces.StudentService;
import com.nhk.thesis.service.interfaces.TopicService;
import com.nhk.thesis.service.interfaces.UserService;
import com.nhk.thesis.util.DropboxUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;
    private SemesterService semesterService;
    private UserService userService;
    private StudentService studentService;
    private DropboxUtils dropboxUtils;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, SemesterService semesterService,
                            UserService userService, StudentService studentService,
                            DropboxUtils dropboxUtils){
        this.topicRepository = topicRepository;
        this.semesterService = semesterService;
        this.userService = userService;
        this.studentService = studentService;
        this.dropboxUtils = dropboxUtils;
    }

    @Override
    public TopicVO getTopic(String id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if(topic != null) {
            SemesterVO semester = semesterService.getSemester(topic.getSemester());
            UserVO user = userService.getUser(topic.getLecturer());
            List<StudentVO> students = new ArrayList<>();
            topic.getMember().forEach(student -> {
                students.add(studentService.getStudentById(student));
            });
            return new TopicVO(topic, semester, user, students);
        }
        return null;
    }

    @Override
    public TopicVO getTopicByStudentAndSemester(String student, String semester) {
        Topic topic = topicRepository.findBySemesterAndMemberContains(semester, student);
        if(topic != null) {
            SemesterVO semesterVO = semesterService.getSemester(topic.getSemester());
            UserVO user = userService.getUser(topic.getLecturer());
            List<StudentVO> students = new ArrayList<>();
            topic.getMember().forEach(obj -> {
                students.add(studentService.getStudentById(obj));
            });
            return new TopicVO(topic, semesterVO, user, students);
        }
        return null;
    }

    @Override
    public List<TopicVO> getTopicByStudent(String student) {
        List<Topic> topic = topicRepository.findAllByMemberContains(student);
        List<TopicVO> topics = new ArrayList<>();
        topic.forEach(t -> {
            SemesterVO semester = semesterService.getSemester(t.getSemester());
            UserVO user = userService.getUser(t.getLecturer());
            List<StudentVO> students = new ArrayList<>();
            t.getMember().forEach(s -> {
                students.add(studentService.getStudentById(s));
            });
            topics.add(new TopicVO(t, semester, user, students));
        });
        return topics;
    }

    @Override
    public List<TopicVO> getTopicInCurrentSemester() {
        SemesterVO semester = semesterService.getCurrentSemester();
        List<TopicVO> topics = new ArrayList<>();
        if(semester != null) {
            List<Topic> topic = topicRepository.findAllBySemester(semester.getId());
            topic.forEach(t -> {
                UserVO user = userService.getUser(t.getLecturer());
                List<StudentVO> students = new ArrayList<>();
                t.getMember().forEach(s -> {
                    students.add(studentService.getStudentById(s));
                });
                topics.add(new TopicVO(t, semester, user, students));
            });
        }
        return topics;
    }

    @Override
    public TopicVO getByStudentInCurrentSemester(String student) {
        SemesterVO semester = semesterService.getCurrentSemester();
        if(semester != null) {
            Topic topic = topicRepository.findBySemesterAndMemberContains(semester.getId(), student);
            if(topic != null) {
                UserVO user = userService.getUser(topic.getLecturer());
                List<StudentVO> students = new ArrayList<>();
                topic.getMember().forEach(s -> {
                    students.add(studentService.getStudentById(s));
                });
                return new TopicVO(topic, semester, user, students);
            }
        }
        return null;
    }

    @Override
    public List<TopicVO> getTopicInCurrentSemesterByAccount(String account) {
        UserVO user = userService.getUserByAccount(account);
        SemesterVO semester = semesterService.getCurrentSemester();
        List<TopicVO> topics = new ArrayList<>();
        if(semester != null && user != null) {
            topicRepository.findAllBySemesterAndLecturer(semester.getId(), user.getId()).forEach(t -> {
                List<StudentVO> students = new ArrayList<>();
                t.getMember().forEach(s -> {
                    students.add(studentService.getStudentById(s));
                });
                topics.add(new TopicVO(t, semester, user, students));
            });
        }

        return topics;
    }

    @Override
    public List<TopicVO> getTopicByAccount(String account) {
        UserVO user = userService.getUserByAccount(account);
        List<TopicVO> topics = new ArrayList<>();
        if(user != null) {
            List<Topic> list = topicRepository.findAllByLecturer(user.getId());
            list.forEach(t -> {
                List<StudentVO> students = new ArrayList<>();
                SemesterVO semester = semesterService.getSemester(t.getSemester());
                t.getMember().forEach(s -> {
                    students.add(studentService.getStudentById(s));
                });
                topics.add(new TopicVO(t, semester, user, students));
            });
        }

        return topics;
    }

    @Override
    public List<TopicVO> getAllTopics() {
        List<Topic> topic = topicRepository.findAll();
        List<TopicVO> topics = new ArrayList<>();
        topic.forEach(t -> {
            SemesterVO semester = semesterService.getSemester(t.getSemester());
            UserVO user = userService.getUser(t.getLecturer());
            List<StudentVO> students = new ArrayList<>();
            t.getMember().forEach(s -> {
                students.add(studentService.getStudentById(s));
            });
            topics.add(new TopicVO(t, semester, user, students));
        });
        return topics;
    }

    @Override
    public List<TopicVO> search(String keyWord) {
        return topicRepository.search(normalizeString(keyWord)).stream().map(topic -> {
            SemesterVO semester = semesterService.getSemester(topic.getSemester());
            TopicVO topicVO = new TopicVO();
            topicVO.setEnName(topic.getEnName());
            topicVO.setName(topic.getName());
            topicVO.setId(topic.getId());
            topicVO.setSemester(semester);
            return topicVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DocumentFile> getTopicDocument(String id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if(topic != null) {
            List<DocumentFile> list = new ArrayList<>();
            list.add(topic.getSourceCode());
            list.add(topic.getReportFile());

            return list;
        }

        return Collections.emptyList();
    }

    @Override
    public boolean setTopicSourceCode(MultipartFile file, String id) throws IOException, DbxException {
        Topic topic = topicRepository.findById(id).orElse(null);

        if(topic != null) {
            if (topic.getReportFile() != null) {
                deleteSourceFile(id);
            }
            DocumentFile document = new DocumentFile();
            document.setName(file.getResource().getFilename());
            document.setCreatedDate(String.valueOf(System.currentTimeMillis()));
            document.setSize(String.valueOf(file.getSize()));
            document.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

            Map<String, String> map = dropboxUtils.uploadFile(file.getInputStream(), document.getName());
            document.setUrl(map.get("url"));
            document.setViewUrl("viewUrl");
            document.setSharedUrl(map.get("sharedUrl"));
            document.setPath(map.get("path"));
            topic.setSourceCode(document);
            topicRepository.save(topic);
            return true;
        }
        return false;
    }

    @Override
    public boolean setTopicReportFile(MultipartFile file, String id) throws IOException, DbxException {
        Topic topic = topicRepository.findById(id).orElse(null);

        if(topic != null) {
            if (topic.getReportFile() != null) {
                deleteReportFile(id);
            }
            DocumentFile document = new DocumentFile();
            document.setName(file.getResource().getFilename());
            document.setCreatedDate(String.valueOf(System.currentTimeMillis()));
            document.setSize(String.valueOf(file.getSize()));
            document.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

            Map<String, String> map = dropboxUtils.uploadFile(file.getInputStream(), document.getName());
            document.setUrl(map.get("url"));
            document.setViewUrl("viewUrl");
            document.setSharedUrl(map.get("sharedUrl"));
            document.setPath(map.get("path"));
            topic.setReportFile(document);
            topicRepository.save(topic);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSourceFile(String id) throws DbxException {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic != null) {
            DocumentFile document = topic.getSourceCode();
            topic.setSourceCode(null);
            topicRepository.save(topic);
            dropboxUtils.deleteFile(document.getPath());
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteReportFile(String id) throws DbxException {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic != null) {
            DocumentFile document = topic.getReportFile();
            topic.setReportFile(null);
            topicRepository.save(topic);
            dropboxUtils.deleteFile(document.getPath());
            return true;
        }

        return false;
    }

    @Override
    public boolean isStudentHasTopic(String student,SemesterVO semester) {
        if(semester != null) {
            Topic topics = topicRepository.findBySemesterAndMemberContains(semester.getId(), student);
            return topics != null;
        }
        return false;
    }

    @Override
    public boolean createTopic(String name, String enName, String type, String student, String account) {
        UserVO user  = userService.getUserByAccount(account);
        SemesterVO semester = semesterService.getCurrentSemester();
        List<String> member = Arrays.asList(student.split(","));
        for (String mem: member) {
            if(isStudentHasTopic(mem, semester))
                return false;
        }
        Topic topic = new Topic(name, enName, TopicType.getByCode(type), user.getId(), member, semester.getId());
        topicRepository.save(topic);
        return true;
    }

    @Override
    public boolean updateTopic(String id, String name, String enName, String type, String student) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if(topic != null) {
            topic.setName(name);
            topic.setEnName(enName);
            topic.setType(TopicType.getByCode(type));
            List<String> member = Arrays.asList(student.split(","));
            SemesterVO semester = semesterService.getCurrentSemester();
            for (String mem: member) {
                if(!topic.getMember().contains(mem) && isStudentHasTopic(mem, semester))
                    return false;
            }
            topic.setMember(member);
            topicRepository.save(topic);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTopic(String id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if(topic != null) {
            topic.setStatus(TopicStatus.CANCELED);
            topicRepository.save(topic);
            return true;
        }
        return false;
    }

    public String normalizeString(String str) {
        return Normalizer
                .normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
