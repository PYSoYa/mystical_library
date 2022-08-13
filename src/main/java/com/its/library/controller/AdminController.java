package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.*;
import com.its.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String reportList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<ReqReportDTO> reqReportDTOList = reqReportService.debutReportList();
        List<ReqReportDTO> reportWriterList = reqReportService.writerReportList();
        model.addAttribute("debutList", reqReportDTOList);
        model.addAttribute("writerList", reportWriterList);
        return "admin/reportList";
    }

    //데뷔글 댓글 신고 수락
    @GetMapping("/debutComment-delete/{id}")
    public String debutCommentDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        reqReportService.debutCommentDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //데뷔글 댓글 신고 거절
    @GetMapping("/debut-reqReport-delete/{id}")
    public String debutReqReportDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        reqReportService.debutReportDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //작가글 댓글 신고 수락
    @GetMapping("/comment-delete/{id}")
    public String commentDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        reqReportService.commentDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //작가글 댓글 신고 거절
    @GetMapping("/reqReport-delete/{id}")
    public String reqReportDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        reqReportService.reportDelete(id);
        return "redirect:/admin/req-report-list";
    }

    //회원 목록조회
    @GetMapping("/member-list")
    public String memberList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "admin/memberList";
    }

    //회원 목록 삭제 기능
    @GetMapping("/member-delete/{id}")
    public String memberDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        memberService.memberDelete(id);
        return "redirect:/admin/member-list";
    }

    //작가글 승인 리스트
    @GetMapping("/book-list")
    public String bookList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<BookDTO> bookList = bookService.findByHiddenBook();
        model.addAttribute(bookList);
        return "admin/bookList";
    }

    //작가글 승인처리
    @GetMapping("/book-agree/{id}")
    public String bookAgree(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        BookDTO bookDTO = bookService.findById(id);
        bookService.bookAgree(bookDTO);
        return "redirect:/admin/book-list";
    }

    //작가글 거절 처리
    @GetMapping("/book-delete/{id}")
    public String bookDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        bookService.bookDelete(id);
        return "redirect:/admin/book-list";
    }

    //회차 승인 리스트
    @GetMapping("/episode-list")
    public String episodeList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<EpisodeDTO> episodeDTOList = episodeService.findAll();
        model.addAttribute("episodeList", episodeDTOList);
        return "admin/episodeList";
    }
    //회차 승인 처리
    @GetMapping("/episode-agree/{id}")
    public String episodeAgree(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        episodeService.episodeAgree(id);
        return "redirect:/admin/episode-list";
    }

    //회차 승인 거절 처리
    @GetMapping("/episode-delete/{id}")
    public String episodeDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        episodeService.episodeDelete(id);
        return "redirect:/admin/episode-list";
    }

    //작가 승인 요청 리스트
    @GetMapping("/req-writer-list")
    public String reqWriterList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<MemberDTO> reqWriterEntityList = reqWriterService.findAll();
        model.addAttribute("memberList", reqWriterEntityList);
        return "admin/reqWriterList";
    }
    

    //작가 권한 변경 승인 처리
    @GetMapping("/req-role-change/{id}")
    public String roleChange(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        reqWriterService.roleChange(id);
        return "redirect:/admin/req-writer-list";
    }

    //작가 승인 거절 처리
    @GetMapping("/req-writer-delete/{id}")
    public String reqWriterDelete(@PathVariable("id") Long memberId) {
        reqWriterService.reqWriterDelete(memberId);
        return "redirect:/admin/req-writer-list";
    }

    //회원 포인트 충전 리스트
    @GetMapping("/point-history")
    public String pointHistory(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<PointDTO> pointDTOList = pointService.pointHistory();
        model.addAttribute("pointList",pointDTOList);
        return "admin/pointHistory";
    }


}