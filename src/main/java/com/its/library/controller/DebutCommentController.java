package com.its.library.controller;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.service.DebutCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("debutComment")
public class DebutCommentController {
    private final DebutCommentService debutCommentService;
    @PostMapping("/save")
    public @ResponseBody List<DebutCommentDTO> save(@ModelAttribute DebutCommentDTO debutCommentDTO){
       List<DebutCommentDTO> result = debutCommentService.save(debutCommentDTO);
        return result;


    }

}
