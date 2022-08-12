package com.its.library.controller;

import com.its.library.config.auth.PrincipalDetails;
import com.its.library.dto.BookDTO;
import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishDTO;
import com.its.library.entity.BookEntity;
import com.its.library.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final DebutService debutService;
    private final MemberService memberService;
    private final ReqWriterService reqWriterService;
    private final BookService bookService;
    private final WishService wishService;

    // 회원가입시 아이디 중복체크
    @PostMapping("/login-id-dup-check")
    public @ResponseBody String LoginIdDupCheck(@RequestParam String loginId) {
        MemberDTO memberDTO = memberService.findByLoginId(loginId);
        if (memberDTO == null) {
            return "ok";
        } else {
            return "no";
        }
    }

    // 회원가입시 이메일 인증
    @PostMapping("/email-authentication")
    public @ResponseBody String emailAuthentication(@RequestParam String memberEmail) {
        String emailNum = memberService.emailAuthentication(memberEmail);
        return emailNum;
    }

    // 회원가입 처리
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/login-page")
    public String loginPage(@AuthenticationPrincipal PrincipalDetails principalDetails,
                            @RequestParam(value = "exception", required = false) String exception, Model model) {
        try {
            String loginId = principalDetails.getUsername();
            MemberDTO memberDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", memberDTO);
        } catch (NullPointerException e) {
            System.out.println("HomeController.index");
        }
        model.addAttribute("exception", exception);
        return "member/login";
    }

    // 회원정보 조회
    @GetMapping("/myPage/{id}")
    public String myPage(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         @PathVariable("id") Long id, Model model) {
        try {
            MemberDTO memberDTO = memberService.myPage(id);
            model.addAttribute("member", memberDTO);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            if (findDTO.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/admin/book-list";
            }
            List<WishDTO> wishDTOList = wishService.findByMemberName(findDTO.getMemberName());
            List<WishDTO> writerList = new ArrayList<>();
            for (int i = 0; i < wishDTOList.size(); i++) {
                if (wishDTOList.get(i).getMemberId() == memberDTO.getId()) {
                    writerList.add(wishDTOList.get(i));
                }
            }
            model.addAttribute("wishlist", writerList);
        } catch (Exception e) {

        } finally {
            List<BookDTO> bookDTOList = bookService.findAllByOnStatus(id);
            model.addAttribute("bookList", bookDTOList);

            int wishCount = wishService.findByMemberId(id);
            model.addAttribute("wishCount", wishCount);
        }
        return "member/myPage";
    }


    // 회원정보 조회 (완결 작품)
    @GetMapping("/myPage/{id}/completion")
    public String myPage2(@AuthenticationPrincipal PrincipalDetails principalDetails,
                          @PathVariable("id") Long id, Model model) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        if (findDTO.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/book-list";
        } else {
            MemberDTO memberDTO = memberService.myPage(id);
            model.addAttribute("member", memberDTO);
            bookDTOList = bookService.finishBook(id);
            int wishCount = wishService.findByMemberId(id);
            wishDTOList = wishService.findByMemberName(findDTO.getMemberName());
            List<WishDTO> writerList = new ArrayList<>();
            for (int i = 0; i < wishDTOList.size(); i++) {
                if (wishDTOList.get(i).getMemberId() == memberDTO.getId()) {
                    writerList.add(wishDTOList.get(i));
                }
            }
            model.addAttribute("wishlist", writerList);
            model.addAttribute("wishCount", wishCount);
            model.addAttribute("bookList", bookDTOList);
            model.addAttribute("wishlist", writerList);
        }
        return "member/myPageCompletion";
    }

    // 회원정보 조회 (데뷔글)
    @GetMapping("/myPage/{id}/debut")
    public String myPage3(@AuthenticationPrincipal PrincipalDetails principalDetails,
                          @PathVariable("id") Long id, Model model) {
        try {
            MemberDTO memberDTO = memberService.myPage(id);
            model.addAttribute("member", memberDTO);

            String loginId = principalDetails.getUsername();
            MemberDTO findDTO = memberService.findByLoginId(loginId);
            model.addAttribute("authentication", findDTO);

            if (findDTO.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/admin/book-list";
            }
            List<DebutEpisodeDTO> debutEpisodeDTOList = debutService.myDebutWrite(id);
            model.addAttribute("myDebutList", debutEpisodeDTOList);
            if (findDTO.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/admin/book-list";
            } else {
                int wishCount = wishService.findByMemberId(id);
                List<WishDTO> wishDTOList = wishService.findByMemberName(findDTO.getMemberName());
                List<WishDTO> writerList = new ArrayList<>();
                for (int i = 0; i < wishDTOList.size(); i++) {
                    if (wishDTOList.get(i).getMemberId() == memberDTO.getId()) {
                        writerList.add(wishDTOList.get(i));
                    }
                }
                model.addAttribute("wishCount", wishCount);
                model.addAttribute("wishlist", writerList);
            }
        } catch (Exception e) {
            model.addAttribute("authentication", new MemberDTO());
        }
        List<DebutEpisodeDTO> debutEpisodeDTOList = debutService.myDebutWrite(id);
        model.addAttribute("myDebutList", debutEpisodeDTOList);
        return "member/myPageDebut";
    }

    // 비밀번호 체크
    @PostMapping("/check-password")
    public @ResponseBody String checkPassword(@ModelAttribute MemberDTO memberDTO) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if (loginDTO != null) {
            return "ok";
        } else {
            return "no";
        }
    }

    // 업데이트 폼 페이지 요청
    @GetMapping("/update-form/{id}")
    public String updateForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/update";
    }

    // 회원정보 수정 처리
    @PostMapping("/update")
    public String update(MemberDTO memberDTO) throws IOException {
        memberService.update(memberDTO);
        return "redirect:/member/myPage/" + memberDTO.getId();
    }


    // 닉네임 변경시 중복 체크
    @PostMapping("/name-dup-check")
    public @ResponseBody String memberNameDupCheck(@RequestParam String memberName) {
        MemberDTO memberDTO = memberService.findByMemberName(memberName);
        if (memberDTO.getId() == null) {
            return "ok";
        } else {
            return "no";
        }
    }

    // 포인트 충전 페이지 이동
    @GetMapping("/purchase-point/{id}")
    public String purchasePoint(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pay";
    }

    // 포인트 충전내역 페이지 이동 (수정필요)
    @GetMapping("/point-history/purchase/{id}")
    public String purchaseHistory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryPurchase";
    }

    // 포인트 이용내역 페이지 이동 (수정필요)
    @GetMapping("/point-history/use/{id}")
    public String useHistory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                             @PathVariable("id") Long id, Model model) {
        String loginId = principalDetails.getUsername();
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        model.addAttribute("authentication", findDTO);

        MemberDTO memberDTO = memberService.myPage(id);
        model.addAttribute("member", memberDTO);
        return "member/pointHistoryUse";
    }

    // 카카오페이
    @GetMapping("/kkoPay")
    public @ResponseBody String kkoPay(@RequestParam("id") Long id,
                                       @RequestParam("cash") int memberPoint) {
        String result = memberService.pointAdd(id, memberPoint);
        return result;
    }

    //작가 승인 요청
    @PostMapping("/req-writer-save")
    public @ResponseBody String reqWriterSave(@RequestParam("id") Long id) {
        String result = reqWriterService.save(id);
        return result;
    }

    // 아이디 찾기 페이지 요청
    @GetMapping("/find/id")
    public String findMemberId() {
        return "member/findLoginId";
    }

    @PostMapping("/find-by-email")
    public @ResponseBody String findByEmail(@RequestParam String memberEmail) {
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        if (memberDTO != null) {
            String emailNum = memberService.emailAuthentication(memberEmail);
            return emailNum;
        } else {
            return "empty";
        }
    }

    @PostMapping("/find-login-id")
    public @ResponseBody MemberDTO findLoginId(@RequestParam String memberEmail) {
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        return memberDTO;
    }

    // 비밀번호 찾기 페이지 요청
    @GetMapping("/find/password")
    public String findMemberPassword() {
        return "member/findMemberPassword";
    }

    @PostMapping("/find-by-login-id-and-email")
    public @ResponseBody String findByLoginIdAndMemberEmail(@ModelAttribute MemberDTO memberDTO) {
        MemberDTO findDTO = memberService.findByLoginIdAndMemberEmail(memberDTO);
        if (findDTO != null) {
            String emailNum = memberService.emailAuthentication(memberDTO.getMemberEmail());
            return emailNum;
        } else {
            return "empty";
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity passwordReset(@RequestParam String memberEmail) {
        memberService.passwordReset(memberEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
