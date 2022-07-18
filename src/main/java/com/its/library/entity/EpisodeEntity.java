package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "episode")
public class EpisodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bookId")
    private Long bookId;
    @Column(name = "episodeTitle",nullable = false,length = 30)
    private String episodeTitle;
    @Column(name = "episodeContents",nullable = false,length = 6000)
    private String episodeContents;
    @Column(name = "episodeImgName",length = 100)
    private String episodeImgName;
    @Column(name = "payment",nullable = false)
    private int payment;
    @Column(name = "episodeHits",columnDefinition = "int default 0")
    private int episodeHits;
    @Column(name = "hidden",columnDefinition = "int default 0" )
    private int hidden;
    //회차 - 책 manyToOne
    //회차 - 댓글 oneToMany
    //회차 - 열람내역 manyToOne
    //회차 - 포인트 manyToOne
    //회차 - 별점 oneToMany

    
}
