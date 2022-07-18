package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "debut_comment")
public class DebutCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "debutId")
    private Long debutId;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "contents",nullable = false,length = 500)
    private String contents;
    //데뷔글 댓글- 데뷔글 ManyToOne
    //데뷔글 댓글- 맴버 ManyToOne
    
}
