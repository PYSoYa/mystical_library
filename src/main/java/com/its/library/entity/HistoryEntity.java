package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "history")
public class HistoryEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @Column(name = "episodeId")
    private Long episodeId;
    @Column(name = "lastTime",nullable = false,updatable = false)
    private LocalDateTime lastTime;
    @Column(name = "hidden",columnDefinition = "int default 0")
    private int hidden;

    @OneToMany(mappedBy = "historyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeEntity> episodeEntityList = new ArrayList<>();


    //열람내역 - 맴버 manyToOne ㅇ
    //열람내역 - 회차 oneToMany ㅇ
}
