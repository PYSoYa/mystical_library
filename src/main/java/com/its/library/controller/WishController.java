package com.its.library.controller;

import com.its.library.dto.WishlistDTO;
import com.its.library.service.WishService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishController {
    private final WishService wishService;

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
}
