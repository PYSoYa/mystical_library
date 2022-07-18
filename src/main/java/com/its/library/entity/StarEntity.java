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
    private Long id;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "episodeId")
    private Long episodeId;
    @Column(name = "star",nullable = false)
    private Double star;
    //별점 - 맴버 manyToOne
    //별점 - 회차 manyToOne

}
