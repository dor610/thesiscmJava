package com.nhk.thesis.service.implement;

import com.nhk.thesis.entity.PresentaionLog;
import com.nhk.thesis.entity.Presentation;
import com.nhk.thesis.entity.User;
import com.nhk.thesis.entity.constant.DaySession;
import com.nhk.thesis.entity.constant.PresentationStatus;
import com.nhk.thesis.entity.vo.*;
import com.nhk.thesis.repository.PresentationLogRepository;
import com.nhk.thesis.repository.PresentationRepository;
import com.nhk.thesis.service.interfaces.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PresentationServiceImpl implements PresentationService {

    private StudentService studentService;
    private UserService userService;
    private TopicService topicService;
    private SemesterService semesterService;
    private PresentationRepository presentationRepository;
    private PresentationLogRepository presentationLogRepository;

    @Autowired
    public PresentationServiceImpl(StudentService studentService, UserService userService, TopicService topicService,
                                   PresentationRepository presentationRepository, SemesterService semesterService,
                                   PresentationLogRepository presentationLogRepository) {
        this.semesterService = semesterService;
        this.userService = userService;
        this.studentService = studentService;
        this.topicService = topicService;
        this.presentationRepository = presentationRepository;
        this.presentationLogRepository = presentationLogRepository;
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public List<PresentationVO> getByStudent(String student) {
        return null;
    }

    @Override
    public PresentationVO getByStudentAndSemester(String student, String semester) {
        return null;
    }

    @Override
    public List<PresentationVO> getBySemester(String semester) {
        return null;
    }

    @Override
    public List<PresentationVO> getByAccountInCurrentSemester(String account) {
        SemesterVO semester = semesterService.getCurrentSemester();
        List<PresentationVO> list = new ArrayList<>();
        UserVO user = userService.getUserByAccount(account);
        if(semester != null && user != null) {
            presentationRepository.findAllByPresidentOrSecretaryOrMemberAndSemester(user.getId(), user.getId(), user.getId(), semester.getId()).forEach(
                    presentation -> {
                        StudentVO student = studentService.getStudentById(presentation.getStudent());
                        TopicVO topic = topicService.getTopic(presentation.getTopic());
                        UserVO president = userService.getUser(presentation.getPresident());
                        UserVO secretary = userService.getUser(presentation.getSecretary());
                        UserVO member = userService.getUser(presentation.getMember());
                        UserVO lecturer = userService.getUser(presentation.getLecturer());
                        list.add(new PresentationVO(presentation, student, topic, semester, lecturer, president, secretary, member));
                    }
            );
        }

        return list;
    }

    @Override
    public List<PresentationVO> getAllByAccount(String account) {
        List<PresentationVO> list = new ArrayList<>();
        UserVO user = userService.getUserByAccount(account);
        if(user != null) {
            presentationRepository.findAllByPresidentOrSecretaryOrMember(user.getId(), user.getId(), user.getId()).forEach(
                    presentation -> {
                        SemesterVO semester = semesterService.getSemester(presentation.getSemester());
                        StudentVO student = studentService.getStudentById(presentation.getStudent());
                        TopicVO topic = topicService.getTopic(presentation.getTopic());
                        UserVO president = userService.getUser(presentation.getPresident());
                        UserVO secretary = userService.getUser(presentation.getSecretary());
                        UserVO member = userService.getUser(presentation.getMember());
                        UserVO lecturer = userService.getUser(presentation.getLecturer());
                        list.add(new PresentationVO(presentation, student, topic, semester, lecturer, president, secretary, member));
                    }
            );
        }

        return list;
    }

    @Override
    public List<PresentationVO> getByCurrentSemester() {
        return null;
    }

    @Override
    public List<PresentationVO> getByTopicId(String topic) {
        return presentationRepository.findByTopic(topic).stream().map(presentation -> {
            SemesterVO semester = semesterService.getSemester(presentation.getSemester());
            StudentVO student = studentService.getStudentById(presentation.getStudent());
            TopicVO topicVO = topicService.getTopic(presentation.getTopic());
            UserVO president = userService.getUser(presentation.getPresident());
            UserVO secretary = userService.getUser(presentation.getSecretary());
            UserVO member = userService.getUser(presentation.getMember());
            UserVO lecturer = userService.getUser(presentation.getLecturer());
            return new PresentationVO(presentation, student, topicVO, semester, lecturer, president, secretary, member);
        }).collect(Collectors.toList());
    }

    @Override
    public PresentationVO getById(String id) {
        Presentation presentation = presentationRepository.findById(id).orElse(null);
        if(presentation != null) {
            StudentVO student = studentService.getStudentById(presentation.getStudent());
            TopicVO topic = topicService.getTopic(presentation.getTopic());
            SemesterVO semester = semesterService.getSemester(presentation.getSemester());
            UserVO lecturer = userService.getUser(presentation.getLecturer());
            UserVO president = userService.getUser(presentation.getPresident());
            UserVO secretary = userService.getUser(presentation.getSecretary());
            UserVO member = userService.getUser(presentation.getMember());
            PresentationVO vo = new PresentationVO(presentation, student, topic, semester, lecturer, president, secretary, member);
            return vo;
        }

        return null;
    }

    @Override
    public List<PresentationVO> getALl() {
        return null;
    }

    @Override
    public List<PresentationVO> getByStatus(PresentationStatus status) {
        return null;
    }


    @Override
    public void importData(MultipartFile file) throws IOException, ParseException {
        PDDocument document = PDDocument.load(file.getInputStream());
        SemesterVO semesterVO = semesterService.getCurrentSemester();
        if (!document.isEncrypted() && semesterVO != null) {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            pdfFileInText = pdfFileInText.substring(pdfFileInText.indexOf("(1) – Chủ tịch, (2) – Ủy viên, (3) – Thư ký"), pdfFileInText.indexOf("Trưởng khoa"));
            pdfFileInText = pdfFileInText.replace("(1) – Chủ tịch, (2) – Ủy viên, (3) – Thư ký", "");
            pdfFileInText = pdfFileInText.replaceAll("STT MSSV Họ tên SV Tên đề tài GVHD Giờ Hội đồng", "");

            // split by whitespace
            String lines[] = pdfFileInText.split("\\r?\\n");
            Pattern pageIndexPattern = Pattern.compile("^\\s+\\d[\\d]?/\\d[\\d]?\\s+$");
            Pattern blankLinePattern = Pattern.compile("^\\s+$");
            Pattern dateAndPlacePattern = Pattern.compile("^\\s*Ngày.+Phòng.*$");
            Pattern startStringPattern = Pattern.compile("^\\d[\\d]?\\s+B?[^AC-Z]?.*$");
            Pattern unnecessaryPartPattern = Pattern.compile("^\\s*Ngày.+Phòng.+Điểm I.*$");
            Map<String, List<List<String>>> map = new LinkedHashMap<>();
            String key = "";
            String nextKey = "";
            String unnecessaryKey = "";
            List<List<String>> valueContain = new ArrayList<>();
            List<String> values = new ArrayList<>();
            boolean isDoingSomething = false;
            for (String line : lines) {
                if(!pageIndexPattern.matcher(line).matches() && !blankLinePattern.matcher(line).matches()){
                    if(dateAndPlacePattern.matcher(line).matches()){
                        if(unnecessaryPartPattern.matcher(line).matches()){
                            unnecessaryKey = line;
                        }
                        if(key.equals("")){
                            key = line;
                        } else {
                            nextKey = line;
                            isDoingSomething = true;
                        }
                    } else if(startStringPattern.matcher(line).matches()) {
                        if(values.size() > 0){
                            valueContain.add(values);
                        }
                        if(isDoingSomething) {
                            map.put(key, valueContain);
                            key = nextKey;
                            isDoingSomething = false;
                            valueContain = new ArrayList<>();
                        }
                        values = new ArrayList<>();
                        values.add(line);
                    } else {
                        values.add(line);
                    }
                }
            }
            valueContain.add(values);
            if(!nextKey.equals(unnecessaryKey)){
                map.put(nextKey, valueContain);
            }

            for(Map.Entry<String, List<List<String>>> data: map.entrySet()) {
                for(List<String> list: data.getValue()) {
                    processData(list, data.getKey(), semesterVO);
                }
            }

        }
    }

    @Override
    public void processData(List<String> data, String dateAndPlace, SemesterVO semester) throws ParseException {
        String stt = "";

        String studentCode = "";
        String name = "";
        String topicName = "";
        String topicEnName = processEnName(data);
        String time = "";
        String date = "";
        String place = "";
        String lecturer = "";
        String president = "";
        String secretary = "";
        String member = "";

        List<String> lineDataArr = new LinkedList<>(Arrays.asList(dateAndPlace.trim().split("\\s+")));
        date = lineDataArr.get(1);
        place = lineDataArr.get(lineDataArr.size() - 1);

        String lecturerAndTime = getLecturerAndTime(data);
        lineDataArr = new LinkedList<>(Arrays.asList(lecturerAndTime.split("\\s+")));
        time = lineDataArr.get(lineDataArr.size() - 1);
        lineDataArr.remove(lineDataArr.size() - 1);
        lecturer = String.join(" ", lineDataArr.toArray(new String[0]));

        String firstLine = data.get(0);
        lineDataArr = new LinkedList<>(Arrays.asList(firstLine.split("\\s+")));
        if(lineDataArr.size() == 1){
            stt = lineDataArr.get(0);
        } else if (lineDataArr.size() == 2) {
            stt = lineDataArr.get(0);
            studentCode = lineDataArr.get(1);
        } else if (lineDataArr.size() != 2){
            stt = lineDataArr.get(0);
            studentCode = lineDataArr.get(1);
            lineDataArr.remove(0);
            lineDataArr.remove(0);
            name = name.equals("")? String.join(" ", lineDataArr) : name + " " + String.join(" ", lineDataArr);
        }
        data.remove(0);


        if(data.size() > 0){
            String secondLine = data.get(0);
            lineDataArr = new ArrayList<>(Arrays.asList(secondLine.split("\\s+")));
            if(Pattern.compile("^.*B\\d{7}.*$").matcher(secondLine).matches()) {
                if (lineDataArr.size() == 1) {
                    studentCode = lineDataArr.get(0);
                } else if (lineDataArr.size() != 0) {
                    studentCode = lineDataArr.get(0);
                    lineDataArr.remove(0);
                    name = name.equals("")? String.join(" ", lineDataArr) : name + " " + String.join(" ", lineDataArr);
                }
            } else {
                if (lineDataArr.size() > 4){
                    topicName = topicName.equals("")? String.join(" ", lineDataArr) : topicName + " " + String.join(" ", lineDataArr);
                }  else {
                    name = name.equals("")? String.join(" ", lineDataArr) : name + " " + String.join(" ", lineDataArr);
                }
            }
            data.remove(0);
        }

        if (data.size() > 0) {
            String thirdLine = data.get(0);
            if(!Pattern.compile("^[123][\\.]?\\s.*$").matcher(thirdLine).matches()){
                if(!topicName.equals("")){
                    topicName = topicName.trim() + " " + thirdLine.trim();
                } else {
                    lineDataArr = new LinkedList<>(Arrays.asList(thirdLine.split("\\s+")));
                    if(lineDataArr.size() <= 3){
                        name = name.equals("")? String.join(" ", lineDataArr) : name + " " + String.join(" ", lineDataArr);
                    } else {
                        topicName = topicName.equals("")? String.join(" ", lineDataArr) : topicName + " " + String.join(" ", lineDataArr);
                    }
                }
                data.remove(0);
            }
        }

        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i< data.size(); i++) {
            String value = data.get(i);
            if(!Pattern.compile("^[123][\\.]?\\s.*$").matcher(value).matches()){
                topicName += value;
                indexes.add(i);
            } else {
                break;
            }
        }

        for(int i = 0; i < indexes.size(); i++) {
            data.remove(indexes.get(i) - i);
        }
        String presidentStr = data.get(0);
        String secretaryStr = data.get(2);
        String memberstr = data.get(1);

        lineDataArr = new LinkedList<>(Arrays.asList(presidentStr.split("\\s+")));
        lineDataArr.remove(0);
        president = String.join(" ", lineDataArr.toArray(new String[0]));

        lineDataArr = new LinkedList<>(Arrays.asList(memberstr.split("\\s+")));
        lineDataArr.remove(0);
        member = String.join(" ", lineDataArr.toArray(new String[0]));

        lineDataArr = new LinkedList<>(Arrays.asList(secretaryStr.split("\\s+")));
        lineDataArr.remove(0);
        secretary = String.join(" ", lineDataArr.toArray(new String[0]));

//        String timeValue = date + " " + time.replace("h", ":");
//        SimpleDateFormat dateFormatter =new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date dateObj = dateFormatter.parse(timeValue);

        StudentVO studentVO = studentService.getStudent(studentCode);
        UserVO lecturerVO = userService.getByNameAndTitle(lecturer);
        UserVO presidentVO = userService.getByNameAndTitle(president);
        UserVO secretaryVO = userService.getByNameAndTitle(secretary);
        UserVO memUserVO = userService.getByNameAndTitle(member);
        TopicVO topicVO = topicService.getByStudentInCurrentSemester(studentVO.getId());

        Presentation presentation = new Presentation(studentVO.getId(), topicVO.getId(), semester.getId(), lecturerVO.getId(),
                String.valueOf(System.currentTimeMillis()), PresentationStatus.UPCOMING, place, time, date,
                Integer.parseInt(time.substring(0, time.indexOf("h"))) > 12? DaySession.AFTERNOON: DaySession.MORNING,
                presidentVO.getId(), secretaryVO.getId(), memUserVO.getId());
        presentationRepository.save(presentation);
    }

    @Override
    public String processEnName(List<String> lst){
        List<Integer> indexes = new ArrayList<>();
        boolean isStart = false;
        String enName = "";
        for (int i = 0; i < lst.size(); i++) {
            String data = lst.get(i);
            if(!isStart) {
                if(data.startsWith("(")) {
                    isStart = true;
                    enName = data;
                    indexes.add(i);
                    if(Pattern.compile("^.*\\).*$").matcher(data).matches())
                        break;
                }
            } else {
                enName = enName + data;
                if(!Pattern.compile("^.*\\..*\\d[\\d]?h[\\d]?[\\d]?.*$").matcher(data).matches() &&
                        !Pattern.compile("^[123][\\.]?\\s.*$").matcher(data).matches()) {
                    indexes.add(i);
                }
                if(Pattern.compile("^.*\\).*$").matcher(data).matches())
                    break;
            }
        }

        for(int i = 0; i < indexes.size(); i++) {
            lst.remove(indexes.get(i) - i);
        }

        enName = enName.replace("(", "");
        enName = enName.replace(")", "");
        enName.trim();
        return enName;
    }

    @Override
    public String getLecturerAndTime(List<String> lst) {
        String value = "";
        for (String data: lst) {
            if(Pattern.compile("^.*\\..*\\d[\\d]?h[\\d]?[\\d]?.*$").matcher(data).matches()) {
                value = data;
                break;
            }
        }

        lst.remove(value);
        return value;
    }

    @Override
    public boolean writeLog(String id, String content) {
        PresentaionLog log = new PresentaionLog(String.valueOf(System.currentTimeMillis()), content, id);
        presentationLogRepository.save(log);
        return true;
    }

    @Override
    public List<PresentaionLog> getAllLogById(String id) {
        List<PresentaionLog> logs = presentationLogRepository.getAllByPresentation(id);
        return logs;
    }
}
