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
    @Column(length = 50, unique = true, nullable = false)
    private String loginId;
    @Column(nullable = false, length = 100)
    private String memberPassword;
    @Column(length = 50, nullable = false)
    private String memberEmail;
    @Column(length = 50, unique = true)
    private String memberName;
    @Column(length = 100)
    private String memberImgName;
    @Column
    private int memberPoint;
    @Column(nullable = false, length = 20)
    private String role;
    @Column(length = 500)
    private String introduction;
    @Column
    private String provider;
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
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<NoticeEntity> noticeEntityList = new ArrayList<>();

    public static MemberEntity saveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberImgName(memberDTO.getMemberImgName());
        memberEntity.setMemberPoint(500);
        memberEntity.setRole("ROLE_USER");
        memberEntity.setIntroduction(memberDTO.getIntroduction());
        memberEntity.setProvider(memberDTO.getProvider());
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
        memberEntity.setRole("ROLE_WRITER");
        memberEntity.setProvider(memberEntity.getProvider());
        return memberEntity;
    }

    public static MemberEntity pointPay(MemberEntity memberEntity, PointEntity pointEntity1) {
        memberEntity.setId(memberEntity.getId());
        memberEntity.setLoginId(memberEntity.getLoginId());
        memberEntity.setMemberEmail(memberEntity.getMemberEmail());
        memberEntity.setMemberPassword(memberEntity.getMemberPassword());
        memberEntity.setMemberName(memberEntity.getMemberName());
        memberEntity.setMemberPoint(pointEntity1.getTotalPoint());
        memberEntity.setIntroduction(memberEntity.getIntroduction());
        memberEntity.setMemberImgName(memberEntity.getMemberImgName());
        memberEntity.setRole(memberEntity.getRole());
        memberEntity.setProvider(memberEntity.getProvider());
        return memberEntity;
    }

    public static MemberEntity updateEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPoint(500);
        memberEntity.setIntroduction(memberDTO.getIntroduction());
        memberEntity.setMemberImgName(memberDTO.getMemberImgName());
        memberEntity.setRole("ROLE_USER");
        memberEntity.setProvider(memberDTO.getProvider());
        return memberEntity;
    }
}
