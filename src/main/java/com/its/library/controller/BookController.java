package com.its.library.controller;

import com.its.library.common.PagingConst;
import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.MailDTO;
import com.its.library.dto.StarDTO;
import com.its.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    // 책 저장페이지 요청
    @GetMapping("/book-save-form")
    public String bookSaveForm() {

        return "book/save";
    }

    // 책 저장처리
    @PostMapping("/req-book-save")
    public String reqBookSave(@ModelAttribute BookDTO bookDTO) throws IOException {

        BookDTO saveDTO = bookService.reqBookSave(bookDTO);
//      return "redirect:/book?category=" + saveDTO.getCategoryId()  + "/book/" + saveDTO.getId();
        return "redirect:/member/myPage/" + saveDTO.getMemberId();
    }

    // 회차 저장페이지 출력
    @GetMapping("/episode-save-form/{id}")
    public String episodeSaveForm(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "book/episodeSave";
    }

    // 회차 저장처리
    @PostMapping("/req-episode-save")
    public String reqEpisodeSave(@ModelAttribute EpisodeDTO episodeDTO) throws IOException {
        Long id = bookService.reqEpisodeSave(episodeDTO);
        BookDTO bookDTO = bookService.findById(episodeDTO.getBookId());
        return "redirect:/book?category=" + bookDTO.getCategoryId() + "/book/" + id;
    }

    // 책 수정 페이지 출력
    @GetMapping("/req-book-update")
    public String bookUpdateForm(@RequestParam("id") Long id, Model model) {
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        return "book/update";
    }

    // 책 수정처리 요청
    @PostMapping("/req-book-update")
    public String reqBookUpdate(@ModelAttribute BookDTO bookDTO, @ModelAttribute MailDTO mailDTO) throws IOException {

        bookService.reqBookUpdate(bookDTO, mailDTO);
//        return "redirect:/book?category=" + bookDTO.getCategoryId() + "/book/" + bookDTO.getId();
        return "redirect:/";
    }

    // 회차 수정 페이지 출력
    @GetMapping("/req-episode-update")
    public String episodeUpdateForm(@RequestParam("id") Long id, Model model) {
        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        model.addAttribute("episode", episodeDTO);
        return "book/episodeUpdate";
    }

    // 회차 수정처리 요청
    @PostMapping("/req-episode-update")
    public String reqEpisodeUpdate(@ModelAttribute EpisodeDTO episodeDTO, @ModelAttribute MailDTO mailDTO) throws IOException {
        bookService.reqEpisodeUpdate(episodeDTO, mailDTO);
        return "redirect:/";
    }

    // 책 삭제 페이지 요청
    @GetMapping("/req-book-delete")
    public String reqBookDeleteForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "book/delete";
    }

    // 책 삭제 요청
    @PostMapping("/req-book-delete")
    public String reqBookDelete(@RequestParam("id") Long id, @RequestParam("memberName") String memberName,
                                @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle) {
        bookService.reqBookDelete(id, memberName, why, mailTitle);
        return "redirect:/";
    }

    // 회차 삭제페이지 요청
    @GetMapping("/req-episode-delete")
    public String reqEpisodeDeleteForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "book/episodeDelete";
    }

    // 회차 삭제 요청
    @PostMapping("/req-episode-delete")
    public String reqEpisodeDelete(@RequestParam("id") Long id, @RequestParam("memberName") String memberName,
                                   @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle) {
        bookService.reqEpisodeDelete(id, memberName, why, mailTitle);
        return "redirect:/";
    }

    // 카테고리 목록 조회
    @GetMapping("/category")
    public String categoryList(@RequestParam("id") Long id){
        return null;
    }

    // 책 목록 조회 + 페이징
    @GetMapping
    public String bookList(@PageableDefault(page = 1) Pageable pageable, @RequestParam("categoryId") Long categoryId,
                           @RequestParam("genreId") Long genreId, Model model) {
        Page<BookDTO> bookDTOList = bookService.bookList(pageable, categoryId, genreId);
        model.addAttribute("bookList", bookDTOList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < bookDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : bookDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        System.out.println(" = " + "sdfsdf");
        System.out.println("boardEntities.getContent() = " + bookDTOList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + bookDTOList.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + bookDTOList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + bookDTOList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + bookDTOList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + bookDTOList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + bookDTOList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + bookDTOList.isLast()); // 마지막페이지인지 여부
        return "book/book";
    }


    // 책 상세조회 + 회차목록 페이징
    @GetMapping("/book/{id}")
    public String bookDetail(@PageableDefault(page = 1) Pageable pageable,
                             @PathVariable("id") Long id, Model model) {
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable);
        model.addAttribute("episodeList", episodeDTOList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        System.out.println(" = " + "sdfsdf");
        System.out.println("boardEntities.getContent() = " + episodeDTOList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + episodeDTOList.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + episodeDTOList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + episodeDTOList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + episodeDTOList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + episodeDTOList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + episodeDTOList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + episodeDTOList.isLast()); // 마지막페이지인지 여부
        return "book/detail";
    }



    // 회차 상세조회
    @GetMapping("/episode")
    public String episodeDetail(@RequestParam("bookId") Long bookId, @RequestParam("id") Long id,
                                Model model) {
        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        BookDTO bookDTO = bookService.findById(bookId);
        model.addAttribute("book", bookDTO);
        model.addAttribute("episode", episodeDTO);
        return "book/episodeDetail";
    }

    //별점 저장처리
    @PostMapping("/save-star")
    public @ResponseBody double saveStar(@ModelAttribute StarDTO starDTO) {
        double result = bookService.saveStar(starDTO);
         return result;
    }

    // 책 별점 저장처리

}
