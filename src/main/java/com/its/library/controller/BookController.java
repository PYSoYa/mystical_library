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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private final StarService starService;
    private final ReqReportService reqReportService;

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
        return "redirect:/member/myPage/" + saveDTO.getMemberId() + "/waiting";
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

        return "redirect:/book?id=" + episodeDTO.getBookId() + "&alignmentId=0";
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
                                @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle,
                                @RequestParam("fromAddress") String fromAddress) {
        bookService.reqBookDelete(id, memberName, why, mailTitle, fromAddress);
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
                                   @RequestParam("why") String why, @RequestParam("mailTitle") String mailTitle,
                                   @RequestParam("fromAddress") String fromAddress) {
        bookService.reqEpisodeDelete(id, memberName, why, mailTitle, fromAddress);
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
                model.addAttribute("categoryId", 1);
            } else if (categoryId == 2) {
                List<BookDTO> bookDTOList = bookService.siList();
                model.addAttribute("bookList", bookDTOList);
                model.addAttribute("categoryId", 2);
            } else {
                List<BookDTO> bookDTOList = bookService.essayList();
                model.addAttribute("booList", bookDTOList);
                model.addAttribute("categoryId", 3);
            }

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (NullPointerException e) {

        }

        return "book/category";
    }

    // 책 목록 조회 + 페이징
//    @GetMapping
//    public String bookList(@AuthenticationPrincipal PrincipalDetails principalDetails,
//                           @PageableDefault(page = 1) Pageable pageable, @RequestParam("categoryId") Long categoryId,
//                           @RequestParam("genreId") Long genreId, Model model) {
//        try {
//            Page<BookDTO> bookDTOList = bookService.bookList(pageable, categoryId, genreId);
//            model.addAttribute("bookList", bookDTOList);
//            int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
//            int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < bookDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : bookDTOList.getTotalPages();
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//
//            String loginId = principalDetails.getUsername();
//            MemberDTO findDTO = memberService.findByLoginId(loginId);
//            model.addAttribute("authentication", findDTO);
//        } catch (NullPointerException e) {
//
//        }
//        return "redirect:/book/categoryId?categoryId=" + categoryId;
//    }

    // 장르 목록 조회 + 조회순 / 별점순 정렬
    @GetMapping("/genre")
    public String genreList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            @RequestParam("genreId") Long genreId,
                            @RequestParam("alignmentId") Long alignmentId, Model model) {
        try {
            List<BookDTO> bookDTOList = bookService.genreList(genreId, alignmentId);
            model.addAttribute("bookList", bookDTOList);
            model.addAttribute("genreId", genreId);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (NullPointerException e) {

        }
        return "book/genre";
    }

    // 책 상세조회 + 회차목록 페이징 (회원)
    @GetMapping("/book")
    public String bookDetail(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PageableDefault(page = 1) Pageable pageable,
                             @RequestParam("id") Long id,
                             @RequestParam("alignmentId") Long alignmentId, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            BookDTO bookDTO = bookService.findById(id);
            if (alignmentId == 0) {
                Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable, alignmentId);
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
                int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
                model.addAttribute("episodeList", episodeDTOList);
                model.addAttribute("alignmentId", alignmentId);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
            } else if (alignmentId == 1) {
                Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable, alignmentId);
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
                int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
                model.addAttribute("episodeList", episodeDTOList);
                model.addAttribute("alignmentId", alignmentId);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
            }
            String memberName = findDTO.getMemberName();
            MemberDTO memberDTO = memberService.findByMemberName(memberName);
            List<WishDTO> wishDTOList = wishService.findByBook(memberDTO.getMemberName());

            List<EpisodeDTO> episodeDTOSize = episodeService.episodeFindAll(id);
            int episodeSize = episodeDTOSize.size();
            List<CommentDTO> commentDTOList = commentService.bookCommentList(id);
            int commentSize = commentDTOList.size();
            List<WishDTO> bookLoveList = wishService.findByBookWish(id);
            int bookLove = bookLoveList.size();
            List<HistoryDTO> historyDTOList = historyService.findByBookId(id, findDTO.getId());

            model.addAttribute("episodeSize", episodeSize);
            model.addAttribute("commentSize", commentSize);
            model.addAttribute("bookLove", bookLove);
            model.addAttribute("book", bookDTO);
            model.addAttribute("commentList", commentDTOList);
            model.addAttribute("wishlist", wishDTOList);
            model.addAttribute("historyList", historyDTOList);

        } catch (NullPointerException e) {

        }
        return "book/detail";
    }

    // 책 상세조회 + 회차목록 페이징 (비회원)
    @GetMapping("/no")
    public String bookDetailNotLogin(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PageableDefault(page = 1) Pageable pageable,
                                     @RequestParam("id") Long id,
                                     @RequestParam("alignmentId") Long alignmentId, Model model) {
        try {
            BookDTO bookDTO = bookService.findById(id);
            model.addAttribute("book", bookDTO);
            if (alignmentId == 0) {
                Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable, alignmentId);
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
                int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
                model.addAttribute("episodeList", episodeDTOList);
                model.addAttribute("alignmentId", alignmentId);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
            } else if (alignmentId == 1) {
                Page<EpisodeDTO> episodeDTOList = bookService.episodeFindAll(id, pageable, alignmentId);
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
                int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < episodeDTOList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : episodeDTOList.getTotalPages();
                model.addAttribute("episodeList", episodeDTOList);
                model.addAttribute("alignmentId", alignmentId);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
            }
            List<EpisodeDTO> episodeDTOSize = episodeService.episodeFindAll(id);
            int episodeSize = episodeDTOSize.size();
            model.addAttribute("episodeSize", episodeSize);

            List<CommentDTO> commentDTOList = commentService.bookCommentList(id);
            int commentSize = commentDTOList.size();
            model.addAttribute("commentSize", commentSize);
            model.addAttribute("commentList", commentDTOList);
            List<WishDTO> bookLoveList = wishService.findByBookWish(id);
            int bookLove = bookLoveList.size();
            model.addAttribute("bookLove", bookLove);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);
        } catch (NullPointerException e) {

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
        StarDTO starDTO = new StarDTO();
        EpisodeDTO episodeDTO = bookService.episodeFindById(id);
        BookDTO bookDTO = bookService.findById(bookId);
        MemberDTO memberDTO = memberService.myPage(findDTO.getId());
        List<CommentDTO> commentDTOList = commentService.commentList(id);
        starDTO = starService.starList(findDTO.getId(), id);
        List<EpisodeDTO> episodeDTOList = episodeService.episodeFindAll(bookId);
        List<EpisodeDTO> episodeDTOList1 = bookService.beforeAfter(bookId);
        for (int i = 0; i < episodeDTOList1.size(); i++) {
            if (episodeDTOList1.get(i).getId() == id && i != 0) {
                if (i != episodeDTOList1.size() - 1) {
                    model.addAttribute("before", episodeDTOList1.get(i - 1).getId());
                    model.addAttribute("after", episodeDTOList1.get(i + 1).getId());
                } else {
                    model.addAttribute("before", episodeDTOList1.get(i - 1).getId());
                    model.addAttribute("after", 0);
                }
            } else if (episodeDTOList1.size() == 1 && i == 0) {
                model.addAttribute("before", 0);
                model.addAttribute("after", 0);
            } else if (episodeDTOList1.get(i).getId() == id && i == 0) {
                model.addAttribute("before", 0);
                model.addAttribute("after", episodeDTOList1.get(i + 1).getId());
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("episodeList", episodeDTOList);
        model.addAttribute("star", starDTO);
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
            if (searchType.equals("책")) {
                List<BookDTO> bookDTOList = bookService.searchBook(q);
                model.addAttribute("bookList", bookDTOList);

                String loginId = principalDetails.getUsername();
                MemberDTO findDTO = memberService.findByLoginId(loginId);
                model.addAttribute("authentication", findDTO);
                return "book/search";
            } else {
                List<MemberDTO> memberDTOList = bookService.searchMember(q);
                model.addAttribute("memberList", memberDTOList);

                String loginId = principalDetails.getUsername();
                MemberDTO findDTO = memberService.findByLoginId(loginId);
                model.addAttribute("authentication", findDTO);
            }
        } catch (Exception e) {

        }
        return "member/search";
    }

    // 첫화보기
    @GetMapping("/first")
    public @ResponseBody Long first(@RequestParam("bookId") Long bookId) {
        Long result = bookService.first(bookId);
        return result;
    }

    // 알림창에서 이동
    @PostMapping("/findBook")
    public @ResponseBody Long findBook(@RequestParam("id") Long id,
                                       @RequestParam("episodeId") Long episodeId) {
        noticeService.deleteById(id);
        Long result = episodeService.findBook(episodeId);
        return result;
    }

}
