package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "love")
public class LoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loveId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;
    @Column(name = "debutId")
    private Long debutId;
    @Column(name = "love")
    private int love;
    //좋아요 - 맴버 manyToOne
    //좋아요 - 데뷔글 manyToOne
}
