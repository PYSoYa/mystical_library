package com.its.library.controller;

import com.its.library.common.PagingConst;
import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.*;
import com.its.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final CommentService commentService;
    private final HistoryService historyService;
    private final WishService wishService;
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final EpisodeService episodeService;

    // 책 저장페이지 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/book-save-form")
    public String bookSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);
        return "book/save";
    }

    // 책 저장처리
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/req-book-save")
    public String reqBookSave(@ModelAttribute BookDTO bookDTO) throws IOException {
        BookDTO saveDTO = bookService.reqBookSave(bookDTO);
//        return "redirect:/book?category=" + saveDTO.getCategoryId()  + "/book/" + saveDTO.getId();
        return "redirect:/";
    }

    // 회차 저장페이지 출력
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/episode-save-form/{id}")
    public String episodeSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @PathVariable("id") Long bookId, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        model.addAttribute("bookId", bookId);
        return "book/episodeSave";
    }

    // 회차 저장처리
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/req-episode-save")
    public String reqEpisodeSave(@ModelAttribute EpisodeDTO episodeDTO) throws IOException {
        EpisodeDTO episodeDTO1 = bookService.reqEpisodeSave(episodeDTO);
        BookDTO bookDTO = bookService.findById(episodeDTO.getBookId());
        return "redirect:/";
//        return "redirect:/book/book/" + episodeDTO.getBookId();
    }

    // 책 수정 페이지 출력
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/req-book-update")
    public String bookUpdateForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @RequestParam("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        return "book/update";
    }

    // 책 수정처리 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/req-book-update")
    public String reqBookUpdate(@ModelAttribute BookDTO bookDTO, @ModelAttribute MailDTO mailDTO) throws IOException {

        bookService.reqBookUpdate(bookDTO, mailDTO);
//        return "redirect:/book?category=" + bookDTO.getCategoryId() + "/book/" + bookDTO.getId();
        return "redirect:/";
    }

    // 회차 수정 페이지 출력
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/req-episode-update")
    public String episodeUpdateForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    @RequestParam("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        model.addAttribute("episode", episodeDTO);
        return "book/episodeUpdate";
    }

    // 회차 수정처리 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/req-episode-update")
    public String reqEpisodeUpdate(@ModelAttribute EpisodeDTO episodeDTO, @ModelAttribute MailDTO mailDTO) throws IOException {
        bookService.reqEpisodeUpdate(episodeDTO, mailDTO);
        return "redirect:/";
    }

    // 책 삭제 페이지 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/req-book-delete")
    public String reqBookDeleteForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    @RequestParam("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        model.addAttribute("id", id);
        return "book/delete";
    }

    // 책 삭제 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/req-book-delete")
    public String reqBookDelete(@RequestParam("id") Long id, @RequestParam("memberName") String memberName,
                                @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle) {
        bookService.reqBookDelete(id, memberName, why, mailTitle);
        return "redirect:/";
    }

    // 회차 삭제페이지 요청
    @PreAuthorize("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/req-episode-delete")
    public String reqEpisodeDeleteForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @RequestParam("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        model.addAttribute("id", id);
        return "book/episodeDelete";
    }

    // 회차 삭제 요청
    @Secured({"ROLE_WRITER", "ROLE_ADMIN"})
    @PostMapping("/req-episode-delete")
    public String reqEpisodeDelete(@RequestParam("id") Long id, @RequestParam("memberName") String memberName,
                                   @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle) {
        bookService.reqEpisodeDelete(id, memberName, why, mailTitle);
        return "redirect:/";
    }

    // 카테고리 목록 조회
    @GetMapping("/category")
    public String categoryList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                               @RequestParam("categoryId") Long categoryId, Model model) {
        try {
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

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (NullPointerException e) {
            System.out.println("BookController.categoryList");
        }

        return "book/category";
    }

    // 책 목록 조회 + 페이징
    @GetMapping
    public String bookList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                           @PageableDefault(page = 1) Pageable pageable, @RequestParam("categoryId") Long categoryId,
                           @RequestParam("genreId") Long genreId, Model model) {
        try {
            Page<BookDTO> bookDTOList = bookService.bookList(pageable, categoryId, genreId);
            model.addAttribute("bookList", bookDTOList);
            int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
            int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < bookDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : bookDTOList.getTotalPages();
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            System.out.println("boardEntities.getContent() = " + bookDTOList.getContent()); // 요청페이지에 들어있는 데이터
            System.out.println("boardEntities.getTotalElements() = " + bookDTOList.getTotalElements()); // 전체 글갯수
            System.out.println("boardEntities.getNumber() = " + bookDTOList.getNumber()); // 요청페이지(jpa 기준)
            System.out.println("boardEntities.getTotalPages() = " + bookDTOList.getTotalPages()); // 전체 페이지 갯수
            System.out.println("boardEntities.getSize() = " + bookDTOList.getSize()); // 한페이지에 보여지는 글갯수
            System.out.println("boardEntities.hasPrevious() = " + bookDTOList.hasPrevious()); // 이전페이지 존재 여부
            System.out.println("boardEntities.isFirst() = " + bookDTOList.isFirst()); // 첫페이지인지 여부
            System.out.println("boardEntities.isLast() = " + bookDTOList.isLast()); // 마지막페이지인지 여부
        } catch (NullPointerException e) {
            System.out.println("BookController.bookList");
            System.out.println("java.lang.NullPointerException: null");
        }
        return "redirect:/book/categoryId?categoryId=" + categoryId;
    }

    // 장르 목록 조회 + 조회순 / 별점순 정렬
    @GetMapping("/genre")
    public String genreList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            @RequestParam("genreId") Long genreId,
                            @RequestParam("alignmentId") Long alignmentId, Model model) {
        try {
            List<BookDTO> bookDTOList = bookService.genreList(genreId, alignmentId);
            model.addAttribute("bookList", bookDTOList);
            model.addAttribute("genreId", bookDTOList.get(0).getGenreId());

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (NullPointerException e) {
            System.out.println("BookController.genreList");
            System.out.println("java.lang.NullPointerException: null");
        }
        return "book/genre";
    }

    // 책 상세조회 + 회차목록 페이징 (회원)
    @GetMapping("/book/{id}")
    public String bookDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PageableDefault(page = 1) Pageable pageable,
                             @PathVariable("id") Long id, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            BookDTO bookDTO = bookService.findById(id);
            List<CommentDTO> commentDTOList = commentService.bookCommentList(id);
            int commentSize = commentDTOList.size();
            Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable);
            List<EpisodeDTO> episodeDTOSize = episodeService.episodeFindAll(id);
            int episodeSize = episodeDTOSize.size();
            String memberName = findDTO.getMemberName();
            MemberDTO memberDTO = memberService.findByMemberName(memberName);
            List<WishDTO> wishDTOList = wishService.findByBook(memberDTO.getMemberName());
            List<WishDTO> bookLoveList = wishService.findByBookWish(id);
            int bookLove = bookLoveList.size();
            List<HistoryDTO> historyDTOList = historyService.findByBookId(id, findDTO.getId());
            int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
            int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
            model.addAttribute("episodeSize", episodeSize);
            model.addAttribute("commentSize", commentSize);
            model.addAttribute("bookLove", bookLove);
            model.addAttribute("book", bookDTO);
            model.addAttribute("commentList", commentDTOList);
            model.addAttribute("episodeList", episodeDTOList);
            model.addAttribute("wishlist", wishDTOList);
            model.addAttribute("historyList", historyDTOList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        } catch (NullPointerException e) {
            System.out.println("BookController.bookDetail");
        }
        return "book/detail";
    }

    // 책 상세조회 + 회차목록 페이징 (비회원)
    @GetMapping("/{id}")
    public String bookDetailNotLogin(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @PageableDefault(page = 1) Pageable pageable,
                                 @PathVariable("id") Long id, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (Exception e) {

        } finally {
            BookDTO bookDTO = bookService.findById(id);
            List<CommentDTO> commentDTOList = commentService.bookCommentList(id);
            Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable);
            int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
            int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
            model.addAttribute("book", bookDTO);
            model.addAttribute("commentList", commentDTOList);
            model.addAttribute("episodeList", episodeDTOList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        }
        return "book/detailNoLogin";
    }

    // 회차 상세조회
    @GetMapping("/episode")
    public String episodeDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @RequestParam("bookId") Long bookId, @RequestParam("id") Long id,
                                Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);
        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        BookDTO bookDTO = bookService.findById(bookId);
        MemberDTO memberDTO = memberService.myPage(findDTO.getId());
        List<CommentDTO> commentDTOList = commentService.commentList(id);
        model.addAttribute("member", memberDTO);
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
    public String search(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         @RequestParam("searchType") String searchType,
                         @RequestParam("q") String q, Model model) {
        try {
            List<BookDTO> bookDTOList = bookService.search(searchType, q);
            model.addAttribute("bookList", bookDTOList);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (Exception e) {

        }

        return "book/search";
    }

    // 첫화보기
    @GetMapping("/first")
    public @ResponseBody Long first(@RequestParam("bookId") Long bookId) {
        Long result = bookService.first(bookId);
        return result;
    }


}
