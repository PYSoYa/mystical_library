package com.its.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/book")
public class BookController {


    @GetMapping("/category")
    public String categoryList(@RequestParam("category") String category){

        return "book/category";
    }
}
