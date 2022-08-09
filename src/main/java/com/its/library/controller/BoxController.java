package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.BookDTO;
import com.its.library.dto.BoxDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.service.BoxService;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/box")
public class BoxController {
    private final BoxService boxService;
    private final MemberService memberService;

    // 포인트 보유 체크
    @PostMapping("/pointCheck")
    public @ResponseBody String pointCheck(@ModelAttribute BoxDTO boxDTO,
                                           @ModelAttribute HistoryDTO historyDTO, Long episodeId){
        String result = boxService.pointCheck(boxDTO,historyDTO, episodeId);
        return result;
    }

    // 구매한 회차 저장
    @PostMapping("/save")
    public @ResponseBody String save(@ModelAttribute BoxDTO boxDTO,
                                     @RequestParam("memberName") String memberName) {
        String result = boxService.save(boxDTO, memberName);
        return result;
    }

    // 구매한 책 출력
    @GetMapping("/{id}")
    public String list(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       @PathVariable("id") Long id, Model model){
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<BookDTO> bookDTOList = boxService.list(id);
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("bookList", bookDTOList);
        return "member/boxBought";
    }
}
