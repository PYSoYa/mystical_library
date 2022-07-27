package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.service.HistoryService;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;
    private final MemberService memberService;

    // 열람내역 출력
    @GetMapping("/{id}")
    public String list(@PathVariable("id") Long id, Model model) {
        List<BookDTO> bookDTOList =  historyService.list(id);
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("historyList", bookDTOList);
        model.addAttribute("member", memberDTO);
        return "member/boxRecent";
    }

    // 열람내역 숨기기
//    @PutMapping("/hidden")
//    public @ResponseBody String hidden(@ModelAttribute HistoryDTO historyDTO) {
//        String result = historyService.hidden(historyDTO);
//        return result;
//    }
}
