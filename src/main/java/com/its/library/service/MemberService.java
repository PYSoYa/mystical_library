package com.its.library.service;

import com.its.library.dto.MemberDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

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

    // 아이디+비밀번호 체크 후 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
       Optional<MemberEntity> memberEntity = memberRepository.findByLoginId(memberDTO.getLoginId());
        if(memberEntity.isPresent()){
            if(memberDTO.getMemberPassword().equals(memberEntity.get().getMemberPassword())){
                MemberDTO loginDTO = MemberDTO.toDTO(memberEntity.get());
                return loginDTO;
            }
            else {
                return null;
            }
        } else {
            return null;
        }
    }
}
