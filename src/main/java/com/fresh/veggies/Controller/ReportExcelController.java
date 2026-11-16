package com.fresh.veggies.Controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fresh.veggies.Services.ReportExcelService;
@RestController 
@RequestMapping ("/api/v1/report")
public class ReportExcelController {
    private final ReportExcelService service;
    public ReportExcelController(ReportExcelService serv){
        this.service=serv;
    }
    
     @GetMapping("/export/{reportType}/{subType}/{id}")
    public ResponseEntity<byte[]> exportReport(
            @PathVariable String reportType,
            @PathVariable String subType,
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) throws Exception {

        byte[] excel = service.generateExcel(
                reportType, subType, id, startDate, endDate);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=NCM_Report.xlsx")
                .header("Content-Type",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excel);
    }
}
