package com.its.library.service;

import com.its.library.dto.*;
import com.its.library.entity.BookEntity;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.PointEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.PointRepository;
import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    private final BookRepository bookRepository;
    private final PointRepository pointRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JavaMailSender mailSender;
    private String fromMail = "oloveo24@naver.com";


    // 회원가입시 이메일 인증
    public String emailAuthentication(String memberEmail) {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random r = new Random();
        int randomNum = r.nextInt(888888) + 111111;
        int emailNum = randomNum;
        String toAddress = memberEmail;
        String title = "[" + emailNum + "]" + " 신비한서재 이메일 인증을 진행해주세요.";
        String content = "신비한 서재를 방문해주셔서 감사합니다.\n" +
                "인증 번호는 " + emailNum + " 입니다.\n" +
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberEmail);
        message.setFrom(fromMail);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        return Integer.toString(emailNum);
    }


    // 회원가입 + 사진 저장처리
    public Long save(MemberDTO memberDTO) throws IOException {
        String rawPassword = memberDTO.getMemberPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        memberDTO.setMemberPassword(encodedPassword);

        MultipartFile memberImg = memberDTO.getMemberImg();
        String memberImgName = memberImg.getOriginalFilename();
        if (!memberImg.isEmpty()) {
            memberImgName = System.currentTimeMillis() + "_" + memberImgName;
            String savePath = "C:\\springboot_img\\" + memberImgName;
            memberImg.transferTo(new File(savePath));
            memberDTO.setMemberImgName(memberImgName);
        } else {
            memberImgName = "mystical_user.png";
            memberDTO.setMemberImgName(memberImgName);
        }

        MemberEntity memberEntity = MemberEntity.saveEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            PointDTO pointDTO = PointDTO.memberDTO(memberEntity);
            PointEntity pointEntity = PointEntity.memberSave(pointDTO, memberEntity);
            pointRepository.save(pointEntity);
        }
        return id;
    }

    // 아이디 + 비밀번호 체크 후 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
        MemberDTO loginDTO = findByLoginId(memberDTO.getLoginId());
        String rawPassword = memberDTO.getMemberPassword();
        if (bCryptPasswordEncoder.matches(rawPassword, loginDTO.getMemberPassword())) {
            return loginDTO;
        } else {
            return null;
        }
    }

    // 시큐리티 적용 후 쓸 일이 많아서 로그인 체크 메서드와 분리 O
    // 로그인 아이디로 회원 여부 조회
    public MemberDTO findByLoginId(String loginId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByLoginId(loginId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO findDTO = MemberDTO.toDTO(memberEntity);
            return findDTO;
        } else {
            return null;
        }
    }

    // 회원 상세조회
    public MemberDTO myPage(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        if (memberEntity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity.get());
            return memberDTO;
        } else {
            return null;
        }

    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity member : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toDTO(member);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public void memberDelete(Long id) {
        memberRepository.deleteById(id);
    }


    public String pointAdd(Long id, int memberPoint) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            memberEntity.setMemberPoint(memberEntity.getMemberPoint() + memberPoint);
            memberRepository.save(memberEntity);

            return "ok";
        } else {
            return "no";
        }

    }


    public String pointHistorySave(Long memberId, int memberPoint) {
        PointDTO pointDTO = new PointDTO();
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            pointDTO.setMemberId(memberEntity.getId());
            pointDTO.setPlusPoint(memberPoint);
            PointEntity pointEntity = PointEntity.save(pointDTO, memberEntity);
            pointRepository.save(pointEntity);
            return "yes";
        } else {
            return "no";
        }


    }

    public MemberDTO findByMemberName(String memberName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(memberName);
        MemberEntity memberEntity = new MemberEntity();
        MemberDTO memberDTO = new MemberDTO();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            memberDTO = MemberDTO.toDTO(memberEntity);
        }
        return memberDTO;
    }

    public void update(MemberDTO memberDTO) throws IOException {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberDTO.getId());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();

            MultipartFile memberImg = memberDTO.getMemberImg();
            if (!memberImg.isEmpty()) {
                String memberImgName = memberImg.getOriginalFilename();
                memberImgName = System.currentTimeMillis() + "_" + memberImgName;
                String savePath = "C:\\springboot_img\\" + memberImgName;
                memberImg.transferTo(new File(savePath));
                memberEntity.setMemberImgName(memberImgName);
            }
            memberEntity.setMemberName(memberDTO.getMemberName());
            memberEntity.setIntroduction(memberDTO.getIntroduction());
            memberRepository.save(memberEntity);
        }
    }


    public MemberDTO findByMemberEmail(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }

}
