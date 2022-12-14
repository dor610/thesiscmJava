package com.nhk.thesis.service.interfaces;

import com.nhk.thesis.entity.Course;
import com.nhk.thesis.entity.constant.CourseStatus;
import com.nhk.thesis.entity.vo.CourseVO;
import com.nhk.thesis.entity.vo.SemesterVO;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface CourseService {
    CourseVO getCourse(String id);

    Course getCourseByUserAndSemester(String user, String semester);

    List<CourseVO> getCurrentCourses();

    List<CourseVO> getCoursesBySemester(String semester);

    List<CourseVO> getAllCourses();

    List<CourseVO> getCourseByAccount(String account);

    CourseVO getCurrentCourseByAccount(String account);

    List<CourseVO> getCourseByStudent(String student);

    Map<String, Integer> countCourseAndStudent(String semester);

    boolean deleteCourse(String id);

    boolean importCourses(InputStream file) throws IOException;

    CourseStatus generateStatus(Course course, SemesterVO semester);

    byte[] export(String id) throws IOException;

    void setBasicData(XSSFSheet sheet, CourseVO courseVO);

    void setData(XSSFSheet sheet, Map<Integer, XSSFCellStyle> styleMap, List<Map<Integer, String>> dataList);
}
