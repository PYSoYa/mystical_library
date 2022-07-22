package com.its.library.controller;

import com.its.library.dto.CommentDTO;
import com.its.library.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 회차 댓글 저장처리
    @PostMapping("/save")
    public @ResponseBody List<CommentDTO> commentSave(@ModelAttribute CommentDTO commentDTO){
       List<CommentDTO> result = commentService.commentSave(commentDTO);
        return result;
    }

    // 회차 댓글 삭제처리
    @PostMapping("/delete/{id}")
    public @ResponseBody List<CommentDTO> commentDelete(@PathVariable("id") Long id,
                                                        @RequestParam("episodeId") Long episodeId,
                                                        Model model) {
        List<CommentDTO> result = commentService.commentDelete(id, episodeId);

        return result;
    }
}
