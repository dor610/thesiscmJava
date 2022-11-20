package com.nhk.thesis.restApi;

import com.nhk.thesis.entity.vo.PointSheetVO;
import com.nhk.thesis.entity.vo.ReportVO;
import com.nhk.thesis.service.interfaces.PointSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
public class PointSheetApi {
    private PointSheetService pointSheetService;

    @Autowired
    public PointSheetApi(PointSheetService pointSheetService) {
        this.pointSheetService = pointSheetService;
    }

    @GetMapping("")
    public ResponseEntity<Object> get(@RequestParam("id") String id) {
        return new ResponseEntity<>(pointSheetService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Object> reportPoint(@RequestParam("id") String id) {
        return new ResponseEntity<>(pointSheetService.getByPresentation(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getByAccountAndPresentation(@RequestParam("account") String account, @RequestParam("presentation") String presentation) {
        PointSheetVO pointSheet = pointSheetService.getByCreatorAndPresentation(account, presentation);
        if (pointSheet == null) {
            return new ResponseEntity<>("Không tìm được dữ liệu", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(pointSheet, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestParam("aPoint") String aPoint, @RequestParam("bPoint") String bPoint,
                                       @RequestParam("cPoint") String cPoint, @RequestParam("aTotalPoint") double aTotalPoint,
                                       @RequestParam("bTotalPoint") double bTotalPoint, @RequestParam("cTotalPoint") double cTotalPoint,
                                       @RequestParam("sample") String sample, @RequestParam("presentation") String presentation,
                                       @RequestParam("account") String account) {
        return new ResponseEntity<>(pointSheetService.save(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint,
                                                            sample, presentation, account), HttpStatus.OK);
    }
    @PostMapping("/submit")
    public ResponseEntity<Object> submit(@RequestParam("aPoint") String aPoint, @RequestParam("bPoint") String bPoint,
                                         @RequestParam("cPoint") String cPoint, @RequestParam("aTotalPoint") double aTotalPoint,
                                         @RequestParam("bTotalPoint") double bTotalPoint, @RequestParam("cTotalPoint") double cTotalPoint,
                                         @RequestParam("sample") String sample, @RequestParam("presentation") String presentation,
                                         @RequestParam("account") String account) {
        return new ResponseEntity<>(pointSheetService.submit(aPoint, bPoint, cPoint, aTotalPoint, bTotalPoint, cTotalPoint,
                                                            sample, presentation, account), HttpStatus.OK);
    }

}
