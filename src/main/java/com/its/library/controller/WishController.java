package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishDTO;
import com.its.library.service.MemberService;
import com.its.library.service.WishService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishController {
    private final WishService wishService;
    private final MemberService memberService;

    // 관심작가 유무 체크
    @PostMapping("/check")
    private @ResponseBody String check(@ModelAttribute WishDTO wishDTO) {
        String result = wishService.check(wishDTO);
        return result;
    }

    // 관심작가 저장처리
    @PostMapping("/save-writer")
    private @ResponseBody String saveWriter(@ModelAttribute WishDTO wishDTO){
        String result = wishService.saveWriter(wishDTO);
        return result;
    }

    // 관심작가 삭제처리
    @DeleteMapping("/delete")
    private @ResponseBody String delete(@ModelAttribute WishDTO wishDTO) {
        String result = wishService.delete(wishDTO);
        return result;
    }

    // 관심작가 목록출력
    @GetMapping("/wishlist/author/{id}")
    public String wishlistAuthor(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        List<MemberDTO> memberDTOList = wishService.memberWishlist(memberDTO.getMemberName());
        model.addAttribute("member", memberDTO);
        model.addAttribute("memberList", memberDTOList);
        return "member/wishlistAuthor";
    }

    // 위시리스트-관심 책 목록 페이지 이동
    @GetMapping("/wishlist/book/{id}")
    public String wishlistBook(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        List<BookDTO> bookDTOList = wishService.wishlist(memberDTO.getMemberName());
        model.addAttribute("member", memberDTO);
        model.addAttribute("bookList", bookDTOList);
        return "member/wishlistBook";
    }
}
