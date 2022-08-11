package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.BookDTO;
import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.BookEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.service.DebutService;
import com.its.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BookRepository bookRepository;
    private final DebutService debutService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        try {
            List<BookDTO> popularEntityList = new ArrayList<>();
            List<BookDTO> popularEntityList1 = new ArrayList<>();
            List<BookDTO> newList = new ArrayList<>();
            List<BookEntity> bookHitsList = new ArrayList<>();
            List<BookEntity> bookNewList = new ArrayList<>();
            List<DebutEpisodeDTO> debutEpisodeDTOS = debutService.indexNewList();//데뷔 최신글 리스트 5개
            List<DebutEpisodeDTO> top123 =debutService.hitsTop123();
            List<DebutEpisodeDTO> top456 =debutService.hitsTop456();
            model.addAttribute("top123",top123);
            model.addAttribute("top456",top456);
            model.addAttribute("newDebutList",debutEpisodeDTOS);

            bookHitsList = bookRepository.findAllHits();
            if (bookHitsList.size() != 0 && bookHitsList.size() > 5) {
                for (int i = 0; i < 6; i++) {
                    if (i <= 2 ) {
                        popularEntityList.add(BookDTO.findDTO(bookHitsList.get(i))); // 1,2,3
                    }
                    if (i > 2) {
                        popularEntityList1.add(BookDTO.findDTO(bookHitsList.get(i))); // 4,5,6
                    }
                }
            }
            bookNewList = bookRepository.findAllNew();
            if (bookNewList.size() != 0) {
                for (int i = 0; i < bookNewList.size(); i++) {
                    newList.add(BookDTO.findDTO(bookNewList.get(i)));
                }
            }
            model.addAttribute("bookList", popularEntityList); // 전체 카테고리중 조회수 제일 높은 리스트 두개
            model.addAttribute("bookList1", popularEntityList1);
            model.addAttribute("newList", newList); // 전체 카테고리중 완결작
            String loginId = principalDetails.getUsername();
            MemberDTO memberDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", memberDTO);
        } catch (NullPointerException e) {
            System.out.println("HomeController.index");
            System.out.println("java.lang.NullPointerException: null");
        }
        return "index";
    }


}
