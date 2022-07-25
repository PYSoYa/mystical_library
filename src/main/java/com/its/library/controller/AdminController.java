package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.ReqReportDTO;
import com.its.library.service.BookService;
import com.its.library.service.MemberService;
import com.its.library.service.ReqReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ReqReportService reqReportService;
    private final MemberService memberService;
    private final BookService bookService;

    //댓글 신고 내역 리스트
    @GetMapping("/req-report-list")
    public String reportList(Model model){
       List<ReqReportDTO> debutReportList =  reqReportService.debutReportList();
       List<ReqReportDTO> reportList =  reqReportService.reportList();
       model.addAttribute("debutReportList", debutReportList);
       model.addAttribute("reportList", reportList);
       return "admin/reportList";

    }
    //데뷔글 댓글 신고 수락
    @GetMapping("/debutComment-delete/{id}")
    public String debutCommentDelete(@PathVariable("id")Long id){
        reqReportService.debutCommentDelete(id);
        return "redirect:/admin/req-report-list";
    }
    //데뷔글 댓글 신고 거절
    @GetMapping("/debut-reqReport-delete/{id}")
    public String debutReqReportDelete(@PathVariable("id")Long id){
        reqReportService.debutReportDelete(id);
        return "redirect:/admin/req-report-list";
    }
    //작가글 댓글 신고 수락
    @GetMapping("/comment-delete/{id}")
    public String commentDelete(@PathVariable("id")Long id){
        reqReportService.commentDelete(id);
        return "redirect:/admin/req-report-list";
    }
    //작가글 댓글 신고 거절
    @GetMapping("/reqReport-delete/{id}")
    public String reqReportDelete(@PathVariable("id")Long id){
        reqReportService.reportDelete(id);
        return "redirect:/admin/req-report-list";
    }
    //회원 목록조회
    @GetMapping("/member-list")
    public String memberList(Model model){
       List<MemberDTO> memberDTOList = memberService.findAll();
       model.addAttribute("memberList",memberDTOList);
       return "admin/memberList";
    }
    //회원 목록 삭제 기능
    @GetMapping("/member-delete/{id}")
    public String memberDelete(@PathVariable("id")Long id){
        memberService.memberDelete(id);
        return "redirect:/admin/member-list";
    }
    //작가글 승인 리스트
    @GetMapping("/book-list")
    public String bookList(Model model){
       List<BookDTO> bookList = bookService.findAll();
       model.addAttribute(bookList);
        return "admin/bookList";

    }
    @GetMapping("/book-agree/{id}")
    public String bookAgree(@PathVariable("id")Long id)  {
      BookDTO bookDTO =  bookService.findById(id);
        System.out.println("bookDTO = " + bookDTO);
       bookService.bookAgree(bookDTO);
       return "redirect:/admin/book-list";
    }




}
