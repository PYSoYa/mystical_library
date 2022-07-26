package com.its.library.entity;

import com.its.library.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;
    @Column(name = "loginId",length = 20,unique = true,nullable = false)
    private String loginId;
    @Column(name = "memberEmail",length = 50,unique = true,nullable = false)
    private String memberEmail;
    @Column(name = "memberPassword", nullable = false,length = 100)
    private String memberPassword;
    @Column(name = "memberName",length = 20,unique = true)
    private String memberName;
    @Column(name = "memberPoint")
    private int memberPoint;
    @Column(name = "introduction",length = 500)
    private String introduction;
    @Column(name = "memberImgName",length = 100)
    private String memberImgName;
    @Column(name = "role",nullable = true,length = 20)
    private String role;
    //멤버 - 책 oneToMany ㅇ
    //멤버 - 보관홤 oneToMany ㅇ
    //멤버 - 작가댓글 oneToMany ㅇ
    //멤버 - 비작가 댓글 oneToMany ㅇ
    //멤버 - 열람내역 oneToMany ㅇ
    //멤버 - 좋아요 oneToMany ㅇ
    //멤버 - 포인트 oneToMany ㅇ
    //멤버 - 신고 oneToMany ㅇ
    //멤버 - 작가승인 oneToMany ㅇ
    //멤버 - 별점 oneToMany ㅇ
    //멤버 - 관심  oneToMany ㅇ

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookEntity> bookEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoxEntity> boxEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DebutCommentEntity> debutCommentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistoryEntity> historyEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoveEntity> loveEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointEntity> pointEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReqReportEntity> reqReportEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReqWriterEntity> reqWriterEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StarEntity> starEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishEntity> wishlistEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DebutEpisodeEntity> debutEpisodeEntityList = new ArrayList<>();


    public static MemberEntity saveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPoint(500);
        memberEntity.setIntroduction(memberDTO.getIntroduction());
        memberEntity.setMemberImgName(memberDTO.getMemberImgName());
        memberEntity.setRole("일반회원");
        return memberEntity;
    }



    public static MemberEntity roleChange(MemberEntity memberEntity) {
        memberEntity.setId(memberEntity.getId());
        memberEntity.setLoginId(memberEntity.getLoginId());
        memberEntity.setMemberEmail(memberEntity.getMemberEmail());
        memberEntity.setMemberPassword(memberEntity.getMemberPassword());
        memberEntity.setMemberName(memberEntity.getMemberName());
        memberEntity.setMemberPoint(memberEntity.getMemberPoint());
        memberEntity.setIntroduction(memberEntity.getIntroduction());
        memberEntity.setMemberImgName(memberEntity.getMemberImgName());
        memberEntity.setRole("작가");
        return memberEntity;
    }
}
