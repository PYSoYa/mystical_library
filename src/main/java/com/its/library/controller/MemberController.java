package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishlistDTO;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

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

    // 비밀번호 체크
    @PostMapping("/check-password")
    public @ResponseBody String checkPassword(@ModelAttribute MemberDTO memberDTO) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if (loginDTO != null) {
            return "ok";
        } else {
            return "no";
        }
    }

    // 포인트 충전 페이지 이동
    @GetMapping("/purchase-point/{id}")
    public String purchasePoint(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pay";
    }

    // 포인트 충전내역 페이지 이동 (수정필요)


    // 보관함-열람 내역 페이지 이동 (수정필요)
    @GetMapping("/box/recent/{id}")
    public String boxRecent(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/boxRecent";
    }

    // 보관함-소장중인 책 목록 페이지 이동 (수정필요)
    @GetMapping("/box/bought/{id}")
    public String boxBought(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/boxBought";
    }

    // 위시리스트-관심 책 목록 페이지 이동 (수정필요)
    @GetMapping("/wishlist/book/{id}")
    public String wishlistBook(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<BookDTO> bookDTOList = memberService.wishlist(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("bookList", bookDTOList);
        return "member/wishlistBook";
    }

    // 위시리스트-관심 작가 목록 페이지 이동 (수정필요)
    @GetMapping("/wishlist/author/{id}")
    public String wishlistAuthor(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<MemberDTO> memberDTOList = memberService.memberWishlist(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("memberList", memberDTOList);
        System.out.println("memberDTOList = " + memberDTOList);
        return "member/wishlistAuthor";
    }
    //카카오페이 test API
    @GetMapping("/kkoPay")
    public @ResponseBody String kkoPay(@RequestParam("id")Long id, @RequestParam("cash") int memberPoint){
        String result = memberService.pointAdd(id, memberPoint);
        return result;
    }


}
