package com.its.library.controller;

import com.its.library.common.PagingConst;
import com.its.library.dto.*;
import com.its.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;
    private final HistoryService historyService;
    private final WishService wishService;
    private final MemberService memberService;
    private final NoticeService noticeService;

    // 책 저장페이지 요청
    @GetMapping("/book-save-form")
    public String bookSaveForm() {

        return "book/save";
    }

    // 책 저장처리
    @PostMapping("/req-book-save")
    public String reqBookSave(@ModelAttribute BookDTO bookDTO) throws IOException {

        BookDTO saveDTO = bookService.reqBookSave(bookDTO);
//        return "redirect:/book?category=" + saveDTO.getCategoryId()  + "/book/" + saveDTO.getId();
        return "index";
    }

    // 회차 저장페이지 출력
    @GetMapping("/episode-save-form/{id}")
    public String episodeSaveForm(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "book/episodeSave";
    }

    // 회차 저장처리
    @PostMapping("/req-episode-save")
    public String reqEpisodeSave(@ModelAttribute EpisodeDTO episodeDTO, HttpSession session) throws IOException {

        EpisodeDTO episodeDTO1= bookService.reqEpisodeSave(episodeDTO);
        BookDTO bookDTO = bookService.findById(episodeDTO.getBookId());
        Long memberId = (Long) session.getAttribute("id");
        noticeService.save(episodeDTO1,memberId);

        return "redirect:/book/book/" + episodeDTO.getBookId();
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
    public String categoryList(@RequestParam("categoryId") Long categoryId, Model model) {
        if (categoryId == 1) {
            List<BookDTO> bookDTOList1 = bookService.categoryList1();
            List<BookDTO> bookDTOList2 = bookService.categoryList2();
            List<BookDTO> bookDTOList3 = bookService.categoryList3();
            List<BookDTO> bookDTOList4 = bookService.categoryList4();
            List<BookDTO> bookDTOList5 = bookService.categoryList5();
            model.addAttribute("bookList1", bookDTOList1);
            model.addAttribute("bookList2", bookDTOList2);
            model.addAttribute("bookList3", bookDTOList3);
            model.addAttribute("bookList4", bookDTOList4);
            model.addAttribute("bookList5", bookDTOList5);
        } else if (categoryId == 2) {

        }

        return "book/category";
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
        System.out.println("boardEntities.getContent() = " + bookDTOList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + bookDTOList.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + bookDTOList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + bookDTOList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + bookDTOList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + bookDTOList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + bookDTOList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + bookDTOList.isLast()); // 마지막페이지인지 여부
        return "redirect:/book/categoryId?categoryId=" + categoryId;
    }

    // 장르 목록 조회 + 조회순 / 별점순 정렬
    @GetMapping("/genre")
    public String genreList(@RequestParam("genreId") Long genreId,
                            @RequestParam("alignmentId") Long alignmentId, Model model) {
        List<BookDTO> bookDTOList = bookService.genreList(genreId, alignmentId);
        System.out.println("bookDTOList = " + bookDTOList);
        model.addAttribute("bookList", bookDTOList);
        model.addAttribute("genreId", bookDTOList.get(0).getGenreId());
        return "book/genre";
    }

    // 책 상세조회 + 회차목록 페이징
    @GetMapping("/book/{id}")
    public String bookDetail(@PageableDefault(page = 1) Pageable pageable,
                             @PathVariable("id") Long id, Model model, HttpSession session) {
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        List<CommentDTO> commentDTOList = commentService.bookCommentList(id);
        model.addAttribute("commentList", commentDTOList);
        Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable);
        model.addAttribute("episodeList", episodeDTOList);
        String sessionName = (String) session.getAttribute("name");
        MemberDTO memberDTO = memberService.findByMemberName(sessionName);
        List<WishDTO> wishDTOList = wishService.findByMemberName(memberDTO.getMemberName());
            model.addAttribute("wishlist", wishDTOList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "book/detail";
    }

    // 회차 상세조회
    @GetMapping("/episode")
    public String episodeDetail(@RequestParam("bookId") Long bookId, @RequestParam("id") Long id,
                                Model model) {
        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        BookDTO bookDTO = bookService.findById(bookId);
        List<CommentDTO> commentDTOList = commentService.commentList(id);
        model.addAttribute("book", bookDTO);
        model.addAttribute("episode", episodeDTO);
        model.addAttribute("commentList", commentDTOList);
        return "book/episodeDetail";
    }

    //회차+책 별점 저장처리
    @PostMapping("/save-star")
    public @ResponseBody double saveStar(@ModelAttribute StarDTO starDTO) {
        double result = bookService.saveStar(starDTO);
        return result;
    }

    // 책+작가 검색
    @GetMapping("/search")
    public String search(@RequestParam("searchType") String searchType, @RequestParam("q") String q, Model model) {
        List<BookDTO> bookDTOList = bookService.search(searchType, q);
        model.addAttribute("bookList", bookDTOList);
        return "book/search";
    }

    // 첫화보기
    @GetMapping("/first")
    public String first(@RequestParam("bookId") Long bookId) {
        String result = bookService.first(bookId);
        return result;
    }


}
