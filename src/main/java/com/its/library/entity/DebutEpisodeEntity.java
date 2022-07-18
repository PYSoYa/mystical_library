package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "debut_episode")
public class DebutEpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "categoryId",nullable = false)
    private Long categoryId;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "with",nullable = false,length = 50)
    private String with;
    @Column(name = "debutTitle",nullable = false,length = 100)
    private String debutTitle;
    @Column(name = "introduce",length = 500)
    private String introduce;
    @Column(name = "debutContents",nullable = false,length = 6000)
    private String debutContents;
    @Column(name = "debutHits",columnDefinition = "int default 0")
    private int debutHits;
    @Column(name = "debutImgName",length = 200)
    private String debutImgName;
    //데뷔글 - 데뷔글 카테고리 manyToOne
    //데뷔글 - 데뷔글 댓글 OneToMany
    //데뷔글 - 좋아요  OneToMany
}
