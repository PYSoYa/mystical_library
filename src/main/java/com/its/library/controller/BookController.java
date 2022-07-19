package com.its.library.controller;

import com.its.library.dto.BookDTO;
import com.its.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    // 책 저장페이지 요청
    @GetMapping("/book-save-form")
    public String bookSaveForm(){

        return "book/save";
    }

    // 책 저장처리
    @PostMapping("/req-book-save")
    public String reqBookSave(@ModelAttribute BookDTO bookDTO) throws IOException {

        System.out.println("bookDTO = " + bookDTO);
        BookDTO saveDTO = bookService.reqBookSave(bookDTO);
//        return "redirect:/book?category=" + saveDTO.getCategoryId()  + "/book/" + saveDTO.getId();
        return "redirect:/member/myPage/" + saveDTO.getMemberId();
    }

    @GetMapping("/category")
    public String categoryList(@RequestParam("category") String category){
        bookService.categoryList(category);
        return "book/category";
    }
}
