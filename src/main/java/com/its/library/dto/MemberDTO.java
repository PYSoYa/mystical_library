package com.its.library.dto;

import com.its.library.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String loginId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private int memberPoint;
    private String introduction;
    private MultipartFile memberImg;
    private String memberImgName;
    private String role;



    public static MemberDTO findDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setLoginId(memberEntity.getLoginId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPoint(memberEntity.getMemberPoint());
        memberDTO.setIntroduction(memberEntity.getIntroduction());
        memberDTO.setMemberImgName(memberEntity.getMemberImgName());
        memberDTO.setRole(memberEntity.getRole());
        return memberDTO;
    }
}
