package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.NoticeDTO;
import com.its.library.service.MemberService;
import com.its.library.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping("/history/{id}")
    public String noticeHistory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PathVariable("id") Long memberId, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        List<NoticeDTO> noticeDTOList = noticeService.noticeHistory(memberId);
        model.addAttribute("noticeList", noticeDTOList);
        return "notice/noticeHistory";
    }

    @GetMapping("/readCount")
    public @ResponseBody boolean noticeReadCount(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            boolean result = noticeService.readFalseCount(findDTO.getId());
            return result;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
