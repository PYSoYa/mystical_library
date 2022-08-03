package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.MemberDTO;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO memberDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", memberDTO);
        } catch (NullPointerException e) {
            System.out.println("HomeController.index");
            System.out.println("java.lang.NullPointerException: null");
        }
        return "index";
    }
}
