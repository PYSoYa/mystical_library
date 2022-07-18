package com.its.library.service;

import com.its.library.dto.MemberDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입+사진 저장처리
    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberImg = memberDTO.getMemberImg();
        String memberImgName = memberImg.getOriginalFilename();
        memberImgName = System.currentTimeMillis() + "_" + memberImgName;
        String savePath = "C:\\springboot_img\\" + memberImgName;
        if(!memberImg.isEmpty()){
            memberImg.transferTo(new File(savePath));
        }
        memberDTO.setMemberImgName(memberImgName);

        MemberEntity memberEntity = MemberEntity.saveEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }
}
