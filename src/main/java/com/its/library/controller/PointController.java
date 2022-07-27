package com.its.library.controller;

import com.its.library.dto.MemberDTO;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {
    //포인트 충전 내역 페이지 이동
    private final MemberService memberService;
    @GetMapping("/point-history/purchase/{id}")
    public String purchaseHistory(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryPurchase";
    }

    // 포인트 이용내역 페이지 이동 (수정필요)
    @GetMapping("/point-history/use/{id}")
    public String useHistory(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryUse";
    }
    @GetMapping("/point-history-save")
    public @ResponseBody String pointHistorySave(@RequestParam("id") Long memberId, @RequestParam("cash") int memberPoint){
        String result = memberService.pointHistorySave(memberId, memberPoint);
        return result;
    }
}
