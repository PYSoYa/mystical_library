package com.its.library.service;

import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.PointEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.PointRepository;
import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    private final BookRepository bookRepository;
    private final PointRepository pointRepository;

    // 회원가입+사진 저장처리
    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberImg = memberDTO.getMemberImg();
        String memberImgName = memberImg.getOriginalFilename();
        memberImgName = System.currentTimeMillis() + "_" + memberImgName;
        String savePath = "C:\\springboot_img\\" + memberImgName;
        if (!memberImg.isEmpty()) {
            memberImg.transferTo(new File(savePath));
        }
        memberDTO.setMemberImgName(memberImgName);

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

    // 아이디+비밀번호 체크 후 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByLoginId(memberDTO.getLoginId());
        if (memberEntity.isPresent()) {
            if (memberDTO.getMemberPassword().equals(memberEntity.get().getMemberPassword())) {
                MemberDTO loginDTO = MemberDTO.findDTO(memberEntity.get());
                return loginDTO;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // 회원 상세조회
    public MemberDTO myPage(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        if (memberEntity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.findDTO(memberEntity.get());
            return memberDTO;
        } else {
            return null;
        }

    }



    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity listParameter : memberEntityList) {
            MemberEntity memberEntity = listParameter;
            MemberDTO memberDTO = MemberDTO.findDTO(memberEntity);
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
            System.out.println("memberEntity = " + memberEntity);
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

    public MemberDTO findByMemberName(String sessionName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(sessionName);
        MemberEntity memberEntity = new MemberEntity();
        MemberDTO memberDTO = new MemberDTO();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            memberDTO = MemberDTO.findDTO(memberEntity);
        }
        return memberDTO;
    }

}
