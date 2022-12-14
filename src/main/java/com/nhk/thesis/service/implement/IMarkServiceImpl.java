package com.nhk.thesis.service.implement;

import com.dropbox.core.DbxException;
import com.nhk.thesis.entity.Course;
import com.nhk.thesis.entity.IMark;
import com.nhk.thesis.entity.Topic;
import com.nhk.thesis.entity.common.DocumentFile;
import com.nhk.thesis.entity.constant.IMarkStatus;
import com.nhk.thesis.entity.constant.TopicStatus;
import com.nhk.thesis.entity.constant.TopicType;
import com.nhk.thesis.entity.vo.*;
import com.nhk.thesis.repository.IMarkRepository;
import com.nhk.thesis.repository.PresentationRepository;
import com.nhk.thesis.repository.TopicRepository;
import com.nhk.thesis.service.interfaces.*;
import com.nhk.thesis.util.DropboxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IMarkServiceImpl implements IMarkService {

    private PresentationRepository presentationRepository;
    private StudentService studentService;
    private CourseService courseService;
    private UserService userService;
    private TopicRepository topicRepository;
    private IMarkRepository iMarkRepository;
    private SemesterService semesterService;
    private DropboxUtils dropboxUtils;

    @Autowired
    public IMarkServiceImpl(PresentationRepository presentationRepository, TopicRepository topicRepository, IMarkRepository iMarkRepository,
                            StudentService studentService, UserService userService, SemesterService semesterService,
                            DropboxUtils dropboxUtils, CourseService courseService) {
        this.presentationRepository = presentationRepository;
        this.topicRepository = topicRepository;
        this.iMarkRepository = iMarkRepository;
        this.studentService = studentService;
        this.userService = userService;
        this.semesterService = semesterService;
        this.dropboxUtils = dropboxUtils;
        this.courseService = courseService;
    }

    @Override
    public boolean create(String studentCode, String lecturer, MultipartFile file, String lecturerComment, String deanComment, String reason, String other) throws IOException, DbxException {
        StudentVO studentVO = studentService.getStudent(studentCode);
        SemesterVO semesterVO = semesterService.getCurrentSemester();
        //!presentationRepository.existsByStudent(studentVO.getId()) &&
        if( semesterVO != null){
            Topic topic = topicRepository.findBySemesterAndMemberContains(semesterVO.getId(), studentVO.getId());
            if(topic.getType().equals(TopicType.INDIVIDUAL)){
                topic.setStatus(TopicStatus.PAUSED);
                topicRepository.save(topic);
            }

            DocumentFile document = new DocumentFile();
            document.setName(file.getResource().getFilename());
            document.setCreatedDate(String.valueOf(System.currentTimeMillis()));
            document.setSize(String.valueOf(file.getSize()));
            document.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

            Map<String, String> map = dropboxUtils.uploadFile(file.getInputStream(), document.getName());
            document.setUrl(map.get("url"));
            document.setViewUrl(map.get("viewUrl"));
            document.setSharedUrl(map.get("sharedUrl"));
            document.setPath(map.get("path"));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(Long.parseLong(semesterVO.getEndDate())));
            calendar.add(Calendar.YEAR,  1);

            IMark iMark = new IMark(studentVO.getId(), lecturer, String.valueOf(System.currentTimeMillis()), String.valueOf(calendar.getTimeInMillis()),
                    document, deanComment, lecturerComment, reason, semesterVO.getId(), other);
            iMarkRepository.save(iMark);
            studentService.setIMark(studentVO.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String id, MultipartFile file, String lecturerComment, String deanComment, String reason, String other) throws IOException, DbxException {
        IMark iMark = iMarkRepository.findById(id).orElse(null);
        if(iMark != null){
            iMark.setLecturerComment(lecturerComment);
            iMark.setDeanComment(deanComment);
            iMark.setReason(reason);
            iMark.setOther(other);
            if(file != null){
                dropboxUtils.deleteFile(iMark.getDocument().getPath());
                DocumentFile document = new DocumentFile();
                document.setName(file.getResource().getFilename());
                document.setCreatedDate(String.valueOf(System.currentTimeMillis()));
                document.setSize(String.valueOf(file.getSize()));
                document.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

                Map<String, String> map = dropboxUtils.uploadFile(file.getInputStream(), document.getName());
                document.setUrl(map.get("url"));
                document.setViewUrl(map.get("viewUrl"));
                document.setSharedUrl(map.get("sharedUrl"));
                document.setPath(map.get("path"));
                iMark.setDocument(document);
            }
            iMarkRepository.save(iMark);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws DbxException {
        IMark iMark = iMarkRepository.findById(id).orElse(null);
        if(iMark != null) {
            SemesterVO semesterVO = semesterService.getCurrentSemester();
            if(semesterVO != null){
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                Topic topic = topicRepository.findBySemesterAndMemberContains(semesterVO.getId(), studentVO.getId());
                if(topic.getType().equals(TopicType.INDIVIDUAL) && topic.getStatus().equals(TopicStatus.PAUSED)){
                    topic.setStatus(TopicStatus.WORKING);
                    topicRepository.save(topic);
                }
            }
            dropboxUtils.deleteFile(iMark.getDocument().getPath());
            iMarkRepository.delete(iMark);
            return true;
        }
        return false;
    }

    @Override
    public IMarkVO get(String id) {
        IMark iMark = iMarkRepository.findById(id).orElse(null);
        if(iMark != null){
            StudentVO student = studentService.getStudentById(iMark.getStudentCode());
            UserVO lecturer = userService.getUser(iMark.getLecturer());
            SemesterVO semester = semesterService.getSemester(iMark.getSemester());
            return new IMarkVO(iMark, student, lecturer, semester);
        }
        return null;
    }

    @Override
    public IMarkVO getByStudent(String student) {
        IMark iMark = iMarkRepository.findByStudentCode(student);
        if(iMark != null){
            StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
            UserVO lecturer = userService.getUser(iMark.getLecturer());
            SemesterVO semester = semesterService.getSemester(iMark.getSemester());
            return new IMarkVO(iMark, studentVO, lecturer, semester);
        }
        return null;
    }

    @Override
    public List<IMarkVO> getByLecturer(String lecturer) {
        List<IMark> lst = iMarkRepository.findAllByLecturer(lecturer);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<StudentVO> getStudentWithoutIMark(String account) {
        CourseVO course = courseService.getCurrentCourseByAccount(account);
        if(course != null) {
            return course.getStudentVO().stream().filter(studentVO -> {
                IMark iMark = iMarkRepository.findByStudentCode(studentVO.getId());
                if(iMark == null)
                    return true;
                else return false;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<IMarkVO> getByConfirmStatusAndLecturer(boolean confirm, String lecturer) {
        List<IMark> lst = iMarkRepository.findByConfirmAndLecturer(confirm, lecturer);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Integer> getCountIMarkByUserAndSemester(String account, String semester) {
        UserVO user = userService.getUserByAccount(account);
        if(user == null){
            return Collections.emptyList();
        }
        Course course = courseService.getCourseByUserAndSemester(user.getId(), semester);
        List<Integer> list = new ArrayList<>();
        if(course != null) {
            List<IMark> iMarks = iMarkRepository.findAllByLecturerAndSemester(user.getId(), semester);
            list.add(iMarks.size());
            list.add(course.getStudents().size() - iMarks.size());
        } else {
            list.add(0);
            list.add(0);
        }
        return list;
    }

    @Override
    public List<Integer> getCountIMarkBySemester(String semester) {
        List<IMark> list = iMarkRepository.findAllBySemester(semester);
        List<Integer> list1 = new ArrayList<>();
        list1.add(list.size());
        Integer count = 0;
        List<CourseVO> courseVOS = courseService.getCoursesBySemester(semester);
        for (CourseVO c: courseVOS){
            count += c.getStudents().size();
        }
        list1.add(count - list.size());
        return list1;
    }

    @Override
    public List<IMarkVO> getByConfirmAndStatusAndLecturer(boolean confirm, IMarkStatus status, String lecturer) {
        List<IMark> lst = iMarkRepository.findByConfirmAndStatusAndLecturer(confirm, status, lecturer);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IMarkVO> getByCompleteAndConfirmAndStatusAndLecturer(boolean complete, boolean confirm, IMarkStatus status, String lecturer) {
        List<IMark> lst = iMarkRepository.findByCompleteAndConfirmAndStatusAndLecturer(complete, confirm, status, lecturer);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IMarkVO> getByLecturerAndStatus(String lecturer, IMarkStatus status) {
        List<IMark> lst = iMarkRepository.findByLecturerAndStatus(lecturer, status);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IMarkVO> getByStatusAndSemester(IMarkStatus status, String semester) {
        List<IMark> lst = iMarkRepository.findByStatusAndSemester(status, semester);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semesterVO = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semesterVO);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IMarkVO> getBySemester(String semester) {
        List<IMark> lst = iMarkRepository.findAllBySemester(semester);
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semesterVO = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semesterVO);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<IMarkVO> getAll() {
        List<IMark> lst = iMarkRepository.findAll();
        if(lst != null){
            return lst.stream().map(iMark -> {
                StudentVO studentVO = studentService.getStudentById(iMark.getStudentCode());
                UserVO lecturerVO = userService.getUser(iMark.getLecturer());
                SemesterVO semester = semesterService.getSemester(iMark.getSemester());
                return new IMarkVO(iMark, studentVO, lecturerVO, semester);
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void confirm(String id) {
        IMark iMark = iMarkRepository.findById(id).orElse(null);
        if(iMark != null) {
            iMark.setConfirm(true);
            studentService.startIMark(iMark.getStudentCode());
            List<Topic> topic = topicRepository.findAllByMemberContains(iMark.getStudentCode());
            if(topic != null) {
                topicRepository.saveAll(topic.stream().map(t -> {
                    if(t.getStatus().equals(TopicStatus.PAUSED))
                        t.setStatus(TopicStatus.WORKING);
                    return t;
                }).collect(Collectors.toList()));
            }
            iMarkRepository.save(iMark);
        }
    }

    @Override
    public void checkStatus() {
        SemesterVO semester = semesterService.getCurrentSemester();
        List<IMark> newLst = iMarkRepository.findByStatus(IMarkStatus.NEW);
        List<IMark> pendingLst = iMarkRepository.findByStatus(IMarkStatus.PENDING);
        if(semester == null) {
            newLst.forEach(i -> {
                i.setStatus(IMarkStatus.PENDING);
            });
        }
        long currentMilliSec = System.currentTimeMillis();
        pendingLst.forEach(i -> {
            if(semester == null && i.isConfirm())
                i.setComplete(true);
            if(Long.parseLong(i.getExpirationDate()) < currentMilliSec)
                i.setStatus(IMarkStatus.EXPIRED);
        });

        newLst.addAll(pendingLst);
        iMarkRepository.saveAll(newLst);

    }
}
