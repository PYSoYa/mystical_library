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
    private Long id;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "commentId")
    private Long commentId;
    @Column(name = "debutCommentId")
    private Long debutCommentId;
    //신고 - 맴버 manyToOne
    //신고 - 작가댓글 manyToOne
    //신고 - 데뷔글 댓글 manyToOne



}
