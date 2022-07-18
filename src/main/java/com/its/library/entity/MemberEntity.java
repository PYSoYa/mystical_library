package com.its.library.entity;

import com.its.library.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "loginId",length = 20,unique = true,nullable = false)
    private String loginId;
    @Column(name = "memberEmail",length = 50,unique = true,nullable = false)
    private String memberEmail;
    @Column(name = "memberPassword", nullable = false,length = 100)
    private String memberPassword;
    @Column(name = "memberName",length = 20,unique = true)
    private String memberName;
    @Column(name = "memberPoint",columnDefinition = "int default 500")//columnDefinition = "int default 500"초기값 500
    private int memberPoint;
    @Column(name = "introduction",length = 500)
    private String introduction;
    @Column(name = "memberImgName",length = 100)
    private String memberImgName;
    @Column(name = "role",nullable = true,length = 20)
    private String role;
    //멤버 - 책 oneToMany
    //멤버 - 보관홤 oneToMany
    //멤버 - 작가댓글 oneToMany
    //멤버 - 비작가 댓글 oneToMany
    //멤버 - 열람내역 oneToMany
    //멤버 - 좋아요 oneToMany
    //멤버 - 포인트 oneToMany
    //멤버 - 신고 oneToMany
    //멤버 - 작가승인 oneToMany
    //멤버 - 별점 oneToMany
    //멤버 - 관심  oneToMany

    public static MemberEntity saveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPoint(memberDTO.getMemberPoint());
        memberEntity.setIntroduction(memberDTO.getIntroduction());
        memberEntity.setMemberImgName(memberDTO.getMemberImgName());
        return memberEntity;
    }
}
