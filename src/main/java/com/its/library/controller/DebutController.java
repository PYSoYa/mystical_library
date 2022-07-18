package com.its.library.controller;

import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.service.DebutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/debut")
public class DebutController {
    private final DebutService debutService;
    //데뷔글 화면 요청처리
    @GetMapping("/save-form")
    public String saveForm(){
        return "debut/save";
    }
    //데뷔글 저장처리
    @PostMapping("/save")
    public String save(@ModelAttribute DebutEpisodeDTO debutEpisodeDTO, HttpSession session) throws IOException {
        Long id = (Long) session.getAttribute("id");
        debutService.save(debutEpisodeDTO,id);
        return "index";


    }
}
