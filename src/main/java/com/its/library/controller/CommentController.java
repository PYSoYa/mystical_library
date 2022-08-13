package com.its.library.controller;

import com.its.library.dto.CommentDTO;
import com.its.library.service.CommentService;
import com.its.library.service.ReqReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ReqReportService reqReportService;

    // 회차 댓글 저장처리
    @PostMapping("/save")
    public @ResponseBody List<CommentDTO> commentSave(@ModelAttribute CommentDTO commentDTO) {
        List<CommentDTO> result = commentService.commentSave(commentDTO);
        return result;
    }

    // 회차 댓글 삭제처리
    @DeleteMapping("/delete/{id}")
    public @ResponseBody List<CommentDTO> commentDelete(@PathVariable("id") Long id,
                                                        @RequestParam("episodeId") Long episodeId) {
        List<CommentDTO> result = commentService.commentDelete(id, episodeId);
        return result;
    }

    // 회차 댓글 신고처리
    @PostMapping("/report-save/{id}")
    public @ResponseBody String reportSave(@PathVariable("id") Long id, @RequestParam("loginId") Long loginId) {
        String result = commentService.reportSave(id, loginId);
        return result;
    }

    // 회차 댓글 내용수정
    @PostMapping("/update")
    public @ResponseBody List<CommentDTO> update(@ModelAttribute CommentDTO commentDTO) {
        List<CommentDTO> result = commentService.update(commentDTO);
        return result;
    }

    // 신고내역 체크
    @PostMapping("/req-report-check")
    public @ResponseBody String reqReportCheck(@RequestParam("id") Long id,
                                               @RequestParam("memberId") Long memberId) {
        String result = reqReportService.findReportComment(id, memberId);
        return result;
    }

}
