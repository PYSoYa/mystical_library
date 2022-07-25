package com.its.library.controller;

import com.its.library.dto.WishlistDTO;
import com.its.library.service.WishService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishController {
    private final WishService wishService;

    @GetMapping("/save-writer")
    private @ResponseBody String saveWriter(@ModelAttribute WishlistDTO wishlistDTO){

    }
}
