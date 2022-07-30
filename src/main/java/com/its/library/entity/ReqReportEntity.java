package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "req_report")
public class ReqReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reqReportId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private CommentEntity commentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debutCommentId")
    private DebutCommentEntity debutCommentEntity;
    private String debutContents;
    private String writerContents;



    public static ReqReportEntity toSave(MemberEntity memberEntity, DebutCommentEntity debutComment) {
        ReqReportEntity reqReportEntity = new ReqReportEntity();
        reqReportEntity.setDebutCommentEntity(debutComment);
        reqReportEntity.setMemberEntity(memberEntity);
        reqReportEntity.setDebutContents(debutComment.getContents());
        return reqReportEntity;
    }

    public static ReqReportEntity saveEntity(MemberEntity memberEntity, CommentEntity commentEntity) {
        ReqReportEntity reqReportEntity = new ReqReportEntity();
        reqReportEntity.setMemberEntity(memberEntity);
        reqReportEntity.setCommentEntity(commentEntity);
        reqReportEntity.setWriterContents(commentEntity.getContents());
        return reqReportEntity;
    }


    //신고 - 맴버 manyToOne ㅇ
    //신고 - 작가댓글 manyToOne ㅇ
    //신고 - 데뷔글 댓글 manyToOne ㅇ



}
