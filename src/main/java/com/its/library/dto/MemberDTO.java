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
    private String memberName;
    private int memberPoint;
    private String memberEmail;
    private MultipartFile memberImg;
    private String memberImgName;
    private String introduction;
    private String role;
    private String provider;


    @Builder
    public MemberDTO(String loginId, String memberEmail, String memberPassword, String memberName, int memberPoint, String role, String provider) {
        this.loginId = loginId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPoint = memberPoint;
        this.role = role;
        this.provider = provider;
    }

    public static MemberDTO toDTO(MemberEntity memberEntity) {
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
        memberDTO.setProvider(memberEntity.getProvider());
        return memberDTO;
    }
}
