package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.service.MemberService;
import com.its.library.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    //포인트 충전 내역 페이지 이동
    @GetMapping("/point-history/purchase/{id}")
    public String purchaseHistory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        List<PointDTO> pointDTOList = pointService.findPointList(memberDTO);
        model.addAttribute("plusPointList", pointDTOList);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryPurchase";
    }

    // 포인트 이용내역 페이지 이동
    @GetMapping("/point-history/use/{id}")
    public String useHistory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        List<PointDTO> pointDTOList = pointService.findPointList(memberDTO);
        model.addAttribute("member", memberDTO);
        model.addAttribute("minusPointList", pointDTOList);
        return "member/pointHistoryUse";
    }

    //회원 포인트 변경 처리
    @GetMapping("/point-history-save")
    public @ResponseBody String pointHistorySave(@RequestParam("id") Long memberId,
                                                 @RequestParam("cash") int memberPoint) {
        String result = memberService.pointHistorySave(memberId, memberPoint);
        return result;
    }

    //회차 구매 처리
    @GetMapping("/pay/{id}")
    public @ResponseBody String pointPay(@PathVariable("id") Long memberId,
                                         @RequestParam("episodeId") Long episodeId,
                                         @RequestParam("bookId") Long bookId) {
        String result = pointService.pointPay(memberId, episodeId, bookId);
        return result;
    }
}
