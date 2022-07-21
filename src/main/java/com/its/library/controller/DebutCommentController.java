package com.its.library.controller;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.service.DebutCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/debutComment")
public class DebutCommentController {
    private final DebutCommentService debutCommentService;
    //댓글 저장
    @PostMapping("/save")
    public @ResponseBody List<DebutCommentDTO> save(@ModelAttribute DebutCommentDTO debutCommentDTO){
       List<DebutCommentDTO> result = debutCommentService.save(debutCommentDTO);
        return result;


    }
    @DeleteMapping("/delete")
    public @ResponseBody List<DebutCommentDTO>delete(@RequestParam("commentId")Long id,@RequestParam("debutId")Long debutId){
        debutCommentService.delete(id);
       List<DebutCommentDTO> result = debutCommentService.findById(debutId);
        System.out.println("result = " + result);
        return result;
    }

}
