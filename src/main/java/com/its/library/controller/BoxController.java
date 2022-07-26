package com.its.library.controller;

import com.its.library.dto.BoxDTO;
import com.its.library.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/box")
public class BoxController {
    private BoxService boxService;

    // 포인트 보유 체크
    @PostMapping("/pointCheck")
    public String pointCheck(@ModelAttribute BoxDTO boxDTO, Long episodeId){
        System.out.println("boxDTO = " + boxDTO);
        System.out.println("episodeId = " + episodeId);
        String result = boxService.pointCheck(boxDTO.getMemberId(), episodeId);
        return result;
    }

    // 구매한 회차 저장
    @PostMapping("/save")
    public String save(@ModelAttribute BoxDTO boxDTO) {
        String result = boxService.save(boxDTO);
        return result;
    }
}
