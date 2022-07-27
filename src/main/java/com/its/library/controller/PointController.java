package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.service.EpisodeService;
import com.its.library.service.MemberService;
import com.its.library.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final MemberService memberService;
    private final PointService pointService;
    private final EpisodeService episodeService;
    //포인트 충전 내역 페이지 이동
    @GetMapping("/point-history/purchase/{id}")
    public String purchaseHistory(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<PointDTO> pointDTOList= pointService.findPointList(memberDTO);
        model.addAttribute("plusPointList",pointDTOList);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryPurchase";
    }

    // 포인트 이용내역 페이지 이동 (수정필요)
    @GetMapping("/point-history/use/{id}")
    public String useHistory(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<PointDTO> pointDTOList = pointService.findPointList(memberDTO);
        model.addAttribute("member", memberDTO);
        model.addAttribute("minusPointList",pointDTOList);
        return "member/pointHistoryUse";



    }
    @GetMapping("/point-history-save")
    public @ResponseBody String pointHistorySave(@RequestParam("id") Long memberId, @RequestParam("cash") int memberPoint){
        String result = memberService.pointHistorySave(memberId, memberPoint);
        return result;
    }
}
