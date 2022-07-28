package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishlistDTO;
import com.its.library.service.MemberService;
import com.its.library.service.WishService;

import lombok.RequiredArgsConstructor;
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
    private @ResponseBody String check(@ModelAttribute WishlistDTO wishlistDTO) {
        String result = wishService.check(wishlistDTO);
        return result;
    }
    // 관심작가 저장처리
    @PostMapping("/save-writer")
    private @ResponseBody String saveWriter(@ModelAttribute WishlistDTO wishlistDTO){
        String result = wishService.saveWriter(wishlistDTO);
        return result;
    }
    // 관심작가 삭제처리
    @DeleteMapping("/delete")
    private @ResponseBody String delete(@ModelAttribute WishlistDTO wishlistDTO) {
        String result = wishService.delete(wishlistDTO);
        return result;
    }

    // 관심작가 목록출력
    @GetMapping("/wishlist/author/{id}")
    public String wishlistAuthor(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<MemberDTO> memberDTOList = wishService.memberWishlist(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("memberList", memberDTOList);
        System.out.println("memberDTOList = " + memberDTOList);
        return "member/wishlistAuthor";
    }

    // 위시리스트-관심 책 목록 페이지 이동
    @GetMapping("/wishlist/book/{id}")
    public String wishlistBook(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.myPage(id);
        List<BookDTO> bookDTOList = wishService.wishlist(id);
        model.addAttribute("member", memberDTO);
        model.addAttribute("bookList", bookDTOList);
        return "member/wishlistBook";
    }
}
