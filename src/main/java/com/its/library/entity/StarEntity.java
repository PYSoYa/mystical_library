package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "star")
public class StarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "starId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodeId")
    private EpisodeEntity episodeEntity;

    @Column(name = "star",nullable = false)
    private Double star;
    //별점 - 맴버 manyToOne ㅇ
    //별점 - 회차 manyToOne ㅇ

}
