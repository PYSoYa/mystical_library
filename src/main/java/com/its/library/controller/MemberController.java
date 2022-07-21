package com.its.library.controller;

import com.its.library.dto.MemberDTO;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지 요청
    @GetMapping("/save-form")
    public String saveForm(){
        return "member/save";
    }

    // 회원가입 처리
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "index";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){

        MemberDTO memberDTO1 = memberService.login(memberDTO);
        session.setAttribute("id",memberDTO1.getId());
        session.setAttribute("name", memberDTO1.getMemberName());
        return "redirect:/";
    }

    // 회원정보 조회
    @GetMapping("/myPage/{id}")
    public String myPage(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/myPage";
    }

    // 회원정보 조회 (완결 작품)
    @GetMapping("/myPage/{id}/completion")
    public String myPage2(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/myPageCompletion";
    }

    // 회원정보 조회 (데뷔 글)
    @GetMapping("/myPage/{id}/debut")
    public String myPage3(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/myPageDebut";
    }

    // 업데이트 폼 페이지 요청
    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/update";
    }
}
