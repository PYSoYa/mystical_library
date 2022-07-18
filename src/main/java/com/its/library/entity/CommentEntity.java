package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodeId")
    private EpisodeEntity episodeEntity;

    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "contents",nullable = false,length = 500)
    private String contents;
    //댓글-회차 ManyToOne ㅇ
    //댓글-맴버 ManyToOne ㅇ
}
