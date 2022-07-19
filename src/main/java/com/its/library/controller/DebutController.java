package com.its.library.controller;

import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.service.DebutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    //데뷔글 상세조회
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){
      DebutEpisodeDTO debutEpisodeDTO = debutService.detail(id);
      model.addAttribute("debut",debutEpisodeDTO);
      return "debut/detail";
    }
    //데뷔글 업데이트 폼
    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable Long id,Model model){
      DebutEpisodeDTO debutEpisodeDTO =  debutService.updateForm(id);
      model.addAttribute("debut",debutEpisodeDTO);
        return "debut/update";
    }
    //데뷔글 업데이터 처리
    @PostMapping("/update")
    public String update(@ModelAttribute DebutEpisodeDTO debutEpisodeDTO){
        debutService.update(debutEpisodeDTO);
        return "index";
    }

}
