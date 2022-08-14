package com.its.library.controller;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.service.DebutCommentService;
import com.its.library.service.ReqReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/debutComment")
public class DebutCommentController {
    private final DebutCommentService debutCommentService;
    private final ReqReportService reqReportService;

    //댓글 저장
    @PostMapping("/save")
    public @ResponseBody List<DebutCommentDTO> save(@ModelAttribute DebutCommentDTO debutCommentDTO) {
        List<DebutCommentDTO> result = debutCommentService.save(debutCommentDTO);
        return result;


    }
    //댓글 삭제
    @DeleteMapping("/delete")
    public @ResponseBody List<DebutCommentDTO> delete(@RequestParam("commentId") Long id, @RequestParam("debutId") Long debutId) {
        debutCommentService.delete(id);
        List<DebutCommentDTO> result = debutCommentService.findById(debutId);
        return result;
    }
//    @GetMapping("/updateForm/{id}")
//    public @ResponseBody DebutCommentDTO updateForm(@PathVariable Long id){
//       DebutCommentDTO debutCommentDTO = debutCommentService.updateForm(id);
//       return debutCommentDTO;
//    }
    //댓글 수정
    @PutMapping("/update")
    public @ResponseBody String update(@RequestParam("id")Long id,@RequestParam("contents")String contents) {
        String result = debutCommentService.update(id,contents);
        return result;
    }
    //신고내역 저장
    @PostMapping("/list/{id}")
    public @ResponseBody String reportSave(@PathVariable("id")Long id, @RequestParam("memberId")Long memberId){
       String result =debutCommentService.reportSave(id,memberId);
       return result;

    }

    // 신고내역 체크
    @PostMapping("/req-report-check")
    public @ResponseBody String reqReportDebutComment(@RequestParam("id") Long id,
                                               @RequestParam("memberId") Long memberId) {
        String result = reqReportService.reqReportDebutComment(id, memberId);
        return result;
    }

}