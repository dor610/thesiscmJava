package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.*;
import com.nhk.thesis.entity.constant.CourseStatus;
import com.nhk.thesis.entity.constant.SemesterName;
import com.nhk.thesis.entity.vo.*;
import com.nhk.thesis.repository.CourseRepository;
import com.nhk.thesis.repository.ReportRepository;
import com.nhk.thesis.repository.StudentRepository;
import com.nhk.thesis.repository.TopicRepository;
import com.nhk.thesis.service.interfaces.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private SemesterService semesterService;
    private UserService userService;
    private StudentRepository studentRepository;
    private TopicRepository topicRepository;
    private ResourceLoader resourceLoader;
    private ReportRepository reportRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                             ResourceLoader resourceLoader, TopicRepository topicRepository,
                             ReportRepository reportRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository =studentRepository;
        this.resourceLoader = resourceLoader;
        this.topicRepository = topicRepository;
        this.reportRepository = reportRepository;
    }

    @Autowired
    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CourseVO getCourse(String id) {
        Course course = courseRepository.findById(id).orElse(null);
        if(course != null){
            SemesterVO semester = semesterService.getSemester(course.getSemester());
            UserVO user = userService.getUser(course.getLecturer());
            course.setStatus(generateStatus(course, semester));
            courseRepository.save(course);
            CourseVO courseVO = new CourseVO(course, semester, user);
            courseVO.setStudentVO(getStudents(courseVO.getStudents()));
            return courseVO;
        }
        return null;
    }

    @Override
    public List<CourseVO> getCurrentCourses() {
        SemesterVO semester = semesterService.getCurrentSemester();
        if(semester != null) {
            List<Course> courses = courseRepository.getCourseBySemester(semester.getId());
            courses.forEach(course -> {
                course.setStatus(generateStatus(course, semester));
            });
            courseRepository.saveAll(courses);
            return courses.stream().map(c -> {
                UserVO user = userService.getUser(c.getLecturer());
                return new CourseVO(c, semester, user);
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<CourseVO> getCoursesBySemester(String semester) {
        SemesterVO semesterVO = semesterService.getSemester(semester);
        List<Course> courses = courseRepository.getCourseBySemester(semester);
        if(courses != null){
            return courses.stream().map(course -> {
                UserVO user = userService.getUser(course.getLecturer());
                return new CourseVO(course, semesterVO, user);
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }



    @Override
    public List<CourseVO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> {
            SemesterVO semester = semesterService.getSemester(course.getSemester());
            course.setStatus(generateStatus(course, semester));
        });
        courseRepository.saveAll(courses);
        return courses.stream().map(c -> {
            SemesterVO semester = semesterService.getSemester(c.getSemester());
            UserVO user = userService.getUser(c.getLecturer());
            return new CourseVO(c, semester, user);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CourseVO> getCourseByAccount(String account) {
        UserVO user = userService.getUserByAccount(account);
        if(user != null) {
            return courseRepository.getCoursesByLecturer(user.getId()).stream().map(c -> {
                SemesterVO semester = semesterService.getSemester(c.getSemester());
                return new CourseVO(c, semester, user);
            }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public CourseVO getCurrentCourseByAccount(String account) {
        UserVO user = userService.getUserByAccount(account);
        if(user != null) {
            SemesterVO semester = semesterService.getCurrentSemester();
            if(semester != null) {
                Course course = courseRepository.getCourseByLecturerAndSemester(user.getId(), semester.getId());
                if (course != null) {
                    CourseVO courseVO = new CourseVO(course, semester, user);
                    courseVO.setStudentVO(getStudents(courseVO.getStudents()));
                    return courseVO;
                }
            }
        }
        return null;
    }

    @Override
    public List<CourseVO> getCourseByStudent(String student) {
        return courseRepository.getCourseByStudentsContains(student).stream().map(course -> {
            SemesterVO semester = semesterService.getSemester(course.getSemester());
            UserVO user = userService.getUser(course.getLecturer());
            return new CourseVO(course, semester, user);
        }).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCourse(String id) {
        Course course = courseRepository.findById(id).orElse(null);
        if(course != null) {
            course.setStatus(CourseStatus.DELETED);
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public List<StudentVO> getStudents(List<String> studentCodes) {
        List<StudentVO> vos = new ArrayList<>();
        studentCodes.forEach(s -> {
            Student student = studentRepository.findByStudentCode(s);
            vos.add(new StudentVO(student));
        });
        return vos;
    }

    @Override
    public boolean importCourses(InputStream file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        String schoolYear = sheet.getRow(1).getCell(1).getStringCellValue();
        String[] arr = schoolYear.split(" - ");
        String startYear = arr[0];
        String endYear = arr[1];
        SemesterName semesterName = SemesterName.getSemesterByCode(String.valueOf((int) sheet.getRow(2).getCell(1).getNumericCellValue()));
        SemesterVO semesterVO = semesterService.getSemesterByStartYearAndEndYearAnSemester(startYear, endYear, semesterName);
        CourseStatus status = generateStatus(semesterVO);
        List<Course> courses = new ArrayList<>();
        for (int i = 4; i <= sheet.getLastRowNum(); i ++){
            Row row = sheet.getRow(i);
            String account = "";
            if (row.getCell(1).getCellType().equals(CellType.NUMERIC)) {
                account = String.valueOf((int) row.getCell(1).getNumericCellValue());
            } else {
                account = row.getCell(1).getStringCellValue();
            }
            UserVO user = userService.getUserByAccount(account);
            Course course = new Course(String.valueOf((int)row.getCell(0).getNumericCellValue()), user.getId(), semesterVO.getId(), status);
            courses.add(course);
        }
        if(courses.size() > 0)
            courseRepository.saveAll(courses);
        return true;
    }

    @Override
    public CourseStatus generateStatus(Course course, SemesterVO semester) {
        long time = System.currentTimeMillis();
        long startTime = Long.parseLong(semester.getStartDate());
        long endTime = Long.parseLong(semester.getEndDate());

        if(course.getStatus().equals(CourseStatus.DELETED))
            return CourseStatus.DELETED;
        else if(time < startTime)
            return CourseStatus.UPCOMING;
        else if(time > endTime)
            return CourseStatus.FINISHED;
        else return CourseStatus.HAPPENING;
    }

    @Override
    public byte[] export(String id) throws IOException {
        final Resource fileResource = resourceLoader.getResource("classpath:templates/CT594_sample.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileResource.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        CourseVO courseVO = getCourse(id);
        Map<Integer, XSSFCellStyle> styleMap = extractCellStyle(sheet, 7);
        List<Map<Integer, String>> dataList = extractData(courseVO);
        setBasicData(sheet, courseVO);
        setData(sheet, styleMap, dataList);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        return stream.toByteArray();
    }

    @Override
    public void setBasicData(XSSFSheet sheet, CourseVO courseVO) {
        XSSFRow row3 = sheet.getRow(2);
        row3.getCell(1).setCellValue(courseVO.getLecturer().getAccount());
        row3.getCell(3).setCellValue(normalizeString(courseVO.getLecturer().getName()));
        XSSFRow row4 = sheet.getRow(3);
        row4.getCell(3).setCellValue(courseVO.getClassCode());
        XSSFRow row5 = sheet.getRow(4);
        row5.getCell(1).setCellValue(courseVO.getSemester().getStartYear() + "-" + courseVO.getSemester().getEndYear());
        row5.getCell(3).setCellValue(courseVO.getSemester().getSemesterCode().equals(SemesterName.FIRST_SEMESTER.getCode())? "1": "2");
    }

    public Map<Integer, XSSFCellStyle> extractCellStyle(XSSFSheet sheet, int rowNum){
        XSSFRow row = sheet.getRow(rowNum);
        Map<Integer, XSSFCellStyle> map = new LinkedHashMap<>();
        for(int i = 0; i < row.getLastCellNum(); i++){
            XSSFCell cell = row.getCell(i);
            map.put(i, cell.getCellStyle());
        }
        sheet.removeRow(row);
        return map;
    }

    @Override
    public void setData(XSSFSheet sheet, Map<Integer, XSSFCellStyle> styleMap, List<Map<Integer, String>> dataList) {
        int rowNum = 7;
       for(int j = 0; j <dataList.size(); j++){
           Map<Integer, String> obj = dataList.get(j);
           XSSFRow row = sheet.createRow(rowNum);
           for(Map.Entry<Integer, String> entry: obj.entrySet()) {
               XSSFCell cell = row.createCell(entry.getKey());
               cell.setCellValue(entry.getValue());
               cell.setCellStyle(styleMap.get(entry.getKey()));
           }
           rowNum++;
       }
    }

    public List<Map<Integer, String>> extractData(CourseVO courseVO){
        List<StudentVO> students = courseVO.getStudentVO();
        List<Map<Integer, String>> dataLst = new ArrayList<>();
        for(int i =0; i< students.size(); i++) {
            int count = 0;
            Map<Integer, String> map = new LinkedHashMap<>();
            StudentVO student = students.get(i);
            Report report = reportRepository.findByStudent(student.getId());
            map.put(count, String.valueOf(i+1));
            count++;
            map.put(count, student.getStudentCode());
            count++;
            map.put(count, student.getNormalizedName());
            count++;
            if(report != null && report.isApproved()){
                map.put(count, report.getFinalPoint());
            } else {
                map.put(count, "");
            }
            count++;
            map.put(count, "");
            count++;
            map.put(count, "");
            count++;
            map.put(count, "");
            count++;
            Topic topic = topicRepository.findBySemesterAndMemberContains(courseVO.getSemester().getId(), student.getId());
            if(topic != null) {
                map.put(count, topic.getNormalizedName());
                count++;
                map.put(count, topic.getEnName());
                dataLst.add(map);
            } else {
                map.put(count, "");
                count++;
                map.put(count, "");
                dataLst.add(map);
            }
        }
        return dataLst;
    }

    public CourseStatus generateStatus(SemesterVO semester) {
        long time = System.currentTimeMillis();
        long startTime = Long.parseLong(semester.getStartDate());
        long endTime = Long.parseLong(semester.getEndDate());

        if(time < startTime)
            return CourseStatus.UPCOMING;
        else if(time > endTime)
            return CourseStatus.FINISHED;
        else return CourseStatus.HAPPENING;
    }

    public String normalizeString(String str) {
        return Normalizer
                .normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
