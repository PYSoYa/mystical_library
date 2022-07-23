package com.its.library.controller;

import com.its.library.dto.ReqReportDTO;
import com.its.library.service.ReqReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ReqReportService reqReportService;
    @GetMapping("/req-report-list")
    public String reportList(Model model){
       List<ReqReportDTO> debutReportList =  reqReportService.debutReportList();
       List<ReqReportDTO> reportList =  reqReportService.reportList();


       model.addAttribute("debutReportList", debutReportList);
       model.addAttribute("reportList", reportList);
       return "admin/reportList";

    }
    @GetMapping("/debutComment-delete/{id}")
    public String debutCommentDelete(@PathVariable("id")Long id){
        reqReportService.debutCommentDelete(id);
        return "redirect:/admin/req-report-list";
    }
    @GetMapping("/debut-reqReport-delete/{id}")
    public String debutReqReportDelete(@PathVariable("id")Long id){
        return "redirect:/admin/req-report-list";
    }
    @GetMapping("/comment-delete/{id}")
    public String commentDelete(@PathVariable("id")Long id){
        return "redirect:/admin/req-report-list";
    }
    @GetMapping("/reqReport-delete/{id}")
    public String reqReportDelete(@PathVariable("id")Long id){
        return "redirect:/admin/req-report-list";
    }



}
