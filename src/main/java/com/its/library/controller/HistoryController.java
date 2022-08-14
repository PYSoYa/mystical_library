package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.BookDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.service.HistoryService;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String list(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<BookDTO> bookDTOList =  historyService.list(id);
        System.out.println("bookDTOList = " + bookDTOList);
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("bookList", bookDTOList);
        model.addAttribute("member", memberDTO);
        return "member/boxRecent";
    }

    // 열람내역 숨기기
    @PutMapping("/hidden")
    public @ResponseBody String hidden(@ModelAttribute HistoryDTO historyDTO) {
        String result = historyService.hidden(historyDTO);
        return result;
    }

    // 열람내역 저장
    @PostMapping("/save")
    public @ResponseBody String save(@ModelAttribute HistoryDTO historyDTO) {
        String result = historyService.historyUpdate(historyDTO);
        return result;
    }

    // 열람내역 확인
    @PostMapping("/check")
    public @ResponseBody Long check(@ModelAttribute HistoryDTO historyDTO) {
        Long result = historyService.findByHistory(historyDTO);
        return result;
    }

    // 이어보기
    @GetMapping("/again")
    public @ResponseBody Long again(@RequestParam("bookId") Long bookId, @RequestParam("memberId") Long memberId) {
        Long episodeId = historyService.findByAgain(bookId, memberId);
        return episodeId;
    }

    @PostMapping("/episodeCheck")
    public @ResponseBody String episodeCheck(@RequestParam("memberId") Long memberId,
                                             @RequestParam("episodeId") Long episodeId) {
        String result = historyService.episodeCheck(memberId, episodeId);
        return result;
    }
}
