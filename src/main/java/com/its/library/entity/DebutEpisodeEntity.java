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
    @Column(name = "categoryId",nullable = true)
    private Long categoryId;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "memberName",nullable = true,length = 20)
    private String memberName;
    @Column(name = "with",nullable = true,length = 50)
    private String with;
    @Column(name = "debutTitle",nullable = true,length = 100)
    private String debutTitle;
    @Column(name = "introduce",length = 500)
    private String introduce;
    @Column(name = "debutContents",nullable = true,length = 6000)
    private String debutContents;
    @Column(name = "debutHits",columnDefinition = "int default 0")
    private int debutHits;
    @Column(name = "debutImgName",length = 200)
    private String debutImgName;

}
