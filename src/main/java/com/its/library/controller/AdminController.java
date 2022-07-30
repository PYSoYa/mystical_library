package com.its.library.controller;

import com.its.library.dto.*;
import com.its.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ReqReportService reqReportService;
    private final MemberService memberService;
    private final BookService bookService;
    private final EpisodeService episodeService;
    private final ReqWriterService reqWriterService;
    private final PointService pointService;

    //댓글 신고 내역 리스트
    @GetMapping("/req-report-list")
    public String reportList(Model model) {
        List<ReqReportDTO> reqReportDTOList = reqReportService.debutReportList();
        List<ReqReportDTO> reportWriterList = reqReportService.writerReportList();
        model.addAttribute("debutList", reqReportDTOList);
        model.addAttribute("writerList", reportWriterList);
        return "admin/reportList";

    }

    //데뷔글 댓글 신고 수락
    @GetMapping("/debutComment-delete/{id}")
    public String debutCommentDelete(@PathVariable("id") Long id) {
        reqReportService.debutCommentDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //데뷔글 댓글 신고 거절
    @GetMapping("/debut-reqReport-delete/{id}")
    public String debutReqReportDelete(@PathVariable("id") Long id) {
        reqReportService.debutReportDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //작가글 댓글 신고 수락
    @GetMapping("/comment-delete/{id}")
    public String commentDelete(@PathVariable("id") Long id) {
        reqReportService.commentDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //작가글 댓글 신고 거절
    @GetMapping("/reqReport-delete/{id}")
    public String reqReportDelete(@PathVariable("id") Long id) {
        reqReportService.reportDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //회원 목록조회
    @GetMapping("/member-list")
    public String memberList(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "admin/memberList";
    }

    //회원 목록 삭제 기능
    @GetMapping("/member-delete/{id}")
    public String memberDelete(@PathVariable("id") Long id) {
        memberService.memberDelete(id);
        return "redirect:/admin/member-list";
    }

    //작가글 승인 리스트
    @GetMapping("/book-list")
    public String bookList(Model model) {
        List<BookDTO> bookList = bookService.findAll();
        model.addAttribute(bookList);
        return "admin/bookList";

    }

    //작가글  승인처리
    @GetMapping("/book-agree/{id}")
    public String bookAgree(@PathVariable("id") Long id) {
        BookDTO bookDTO = bookService.findById(id);
        System.out.println("bookDTO = " + bookDTO);
        bookService.bookAgree(bookDTO);
        return "redirect:/admin/book-list";
    }

    //작가글 거절 처리
    @GetMapping("/book-delete/{id}")
    public String bookDelete(@PathVariable("id") Long id) {
        bookService.bookDelete(id);
        return "redirect:/admin/book-list";
    }

    //회차 승인 리스트
    @GetMapping("/episode-list")
    public String episodeList(Model model) {
        List<EpisodeDTO> episodeDTOList = episodeService.findAll();
        model.addAttribute("episodeList", episodeDTOList);
        return "admin/episodeList";

    }
    //회차 승인 처리
    @GetMapping("/episode-agree/{id}")
    public String episodeAgree(@PathVariable("id") Long id) {
        episodeService.episodeAgree(id);
        return "redirect:/admin/episode-list";
    }
    //회차 승인 거절 처리
    @GetMapping("/episode-delete/{id}")
    public String episodeDelete(@PathVariable("id") Long id) {
        episodeService.episodeDelete(id);
        return "redirect:/admin/episode-list";
    }
    //작가 승인 리스트
    @GetMapping("/req-writer-list")
    public String reqWriterList(Model model) {
        List<MemberDTO> reqWriterEntityList = reqWriterService.findAll();
        model.addAttribute("memberList", reqWriterEntityList);
        return "admin/reqWriterList";

    }
    //작가 승인 처리
    @PostMapping("/req-writer-save")
    public @ResponseBody String reqWriterSave(@RequestParam("id") Long id) {
        String result = reqWriterService.save(id);
        return result;

    }
    //작가 승인시 권환 변경
    @GetMapping("/req-role-change/{id}")
    public String roleChange(@PathVariable("id") Long id) {
        reqWriterService.roleChange(id);

        return "redirect:/admin/req-writer-list";
    }
    //작가 승인 거절 처리
    @GetMapping("/req-writer-delete/{id}")
    public String reqWriterDelete(@PathVariable("id") Long memberId) {
        reqWriterService.reqWriterDelete(memberId);

        return "redirect:/admin/req-writer-list";
    }
    //회원 충전 리스트
    @GetMapping("/point-history")
    public String pointHistory(Model model){
       List<PointDTO> pointDTOList = pointService.pointHistory();
        model.addAttribute("pointList",pointDTOList);
        return "admin/pointHistory";
    }
}
