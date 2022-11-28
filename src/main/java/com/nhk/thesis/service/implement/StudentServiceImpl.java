package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.Course;
import com.nhk.thesis.entity.Semester;
import com.nhk.thesis.entity.Student;
import com.nhk.thesis.entity.vo.CourseVO;
import com.nhk.thesis.entity.vo.SemesterVO;
import com.nhk.thesis.entity.vo.StudentVO;
import com.nhk.thesis.repository.CourseRepository;
import com.nhk.thesis.repository.StudentRepository;
import com.nhk.thesis.service.interfaces.CourseService;
import com.nhk.thesis.service.interfaces.SemesterService;
import com.nhk.thesis.service.interfaces.StudentService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private SemesterService semesterService;
    private CourseService courseService;
    private CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SemesterService semesterService,
                              CourseService courseService, CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.semesterService = semesterService;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }


    @Override
    public StudentVO getStudentById(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null)
            return  new StudentVO(student);
        return null;
    }

    @Override
    public StudentVO getStudent(String studentCode) {
        System.out.println(studentCode);
        Student student = studentRepository.findByStudentCode(studentCode);
        if(student != null)
            return new StudentVO(student);
        return null;
    }

    @Override
    public List<StudentVO> getStudents(List<String> studentCodes) {
        List<StudentVO> vos = new ArrayList<>();
        vos = studentCodes.stream().map(s -> {
            Student student = studentRepository.findByStudentCode(s);
            return new StudentVO(student);
        }).collect(Collectors.toList());
        return vos;
    }

    @Override
    public List<StudentVO> getStudentsOfCurrentSemester(String account) {
       CourseVO course = courseService.getCurrentCourseByAccount(account);
       if(course != null) {
            return course.getStudentVO();
       }
       return Collections.emptyList();
    }

    @Override
    public Student addStudent(String studentCode, String name, String date, String classCode) throws ParseException {
        Student student = studentRepository.findByStudentCode(studentCode);
        if(student == null) {
            Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            student = new Student(studentCode, name, dateOfBirth, classCode);
            return studentRepository.save(student);
        }
        return student;
    }

    @Override
    public void importStudent(MultipartFile file, String account) throws IOException, ParseException {
        SemesterVO semester = semesterService.getCurrentSemester();
        CourseVO course = courseService.getCurrentCourseByAccount(account);
        List<Student> students = extractDataFromPDF(file.getInputStream());
        List<String> studentCode = new ArrayList<>();
        students = students.stream().map(student -> {
            if(student.checkCourse(semester.getId())) {
                student.addCourse(course.getId(), semester.getId());
                studentCode.add(student.getStudentCode());
                return student;
            } else
                throw new IllegalArgumentException( "Sinh viên " + student.getName() + " - " + student.getStudentCode()  +" đã đăng ký nhóm học phần khác");
        }).collect(Collectors.toList());
        Course c = courseRepository.findById(course.getId()).get();
        c.addStudents(studentCode);
        courseRepository.save(c);
        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> extractDataFromPDF(InputStream inputFile) throws IOException, ParseException {
        PDDocument document = PDDocument.load(inputFile);
        List<Student> students = new ArrayList<>();

        if (!document.isEncrypted()) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);

            // split by whitespace
            String lines[] = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                if(Pattern.compile("^\\d+").matcher(line.substring(0,1)).matches()){
                    List<String> lstData = Arrays.asList(line.split("\\s"));
                    lstData = lstData.subList(1, lstData.size());
                    List<String> nameLst = lstData.subList(1, lstData.size() - 2);
                    students.add(addStudent(lstData.get(0), String.join(" ", nameLst), lstData.get(lstData.size()- 2),
                            lstData.get(lstData.size() - 1)));
                }
            }

        }

        return students;
    }

    @Override
    public List<Student> search(String keyword) {
        return studentRepository.search(normalizeString(keyword));
    }

    @Override
    public void setIMark(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            student.setIMark(true);
            studentRepository.save(student);
        }
    }

    @Override
    public void startIMark(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            SemesterVO semester = semesterService.getCurrentSemester();
            student.setIMark(true);
            student.setiMarkSemester(semester.getId());
            studentRepository.save(student);
        }
    }

    @Override
    public void hihi() {
        studentRepository.saveAll(studentRepository.findAll().stream().map(obj -> {
            obj.setEmail(obj.generateEmail(normalizeString(obj.getName()), obj.getStudentCode()));
            return obj;
        }).collect(Collectors.toList()));
    }


    public String normalizeString(String str) {
        return Normalizer
                .normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
