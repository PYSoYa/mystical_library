package com.its.library.controller;

import com.its.library.dto.ReqReportDTO;
import com.its.library.entity.ReqReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reqReport")
public class ReqReportController {
    private final ReqReportService reqReportService;
    @GetMapping("/list")
    public String reportList(Model model){
       List<ReqReportDTO> debutReportList =  reqReportService.debutReportList();
       List<ReqReportDTO> reportList =  reqReportService.reportList();


       model.addAttribute("debutReportList", debutReportList);
       model.addAttribute("reportList", reportList);
       return "admin/reportList";

    }


}
