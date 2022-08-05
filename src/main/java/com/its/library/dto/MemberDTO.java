package com.its.library.dto;

import com.its.library.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long id;
    private String loginId;
    private String memberPassword;
    private String memberEmail;
    private String memberName;
    private MultipartFile memberImg;
    private String memberImgName;
    private int memberPoint;
    private String role;
    private String introduction;
    private String provider;


    @Builder
    public MemberDTO(String loginId, String memberPassword, String memberEmail, String memberName, String memberImgName, int memberPoint, String role, String provider) {
        this.loginId = loginId;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberImgName = memberImgName;
        this.memberPoint = memberPoint;
        this.role = role;
        this.provider = provider;
    }

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setLoginId(memberEntity.getLoginId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberImgName(memberEntity.getMemberImgName());
        memberDTO.setMemberPoint(memberEntity.getMemberPoint());
        memberDTO.setRole(memberEntity.getRole());
        memberDTO.setIntroduction(memberEntity.getIntroduction());
        memberDTO.setProvider(memberEntity.getProvider());
        return memberDTO;
    }
}
