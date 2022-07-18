package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@Table(name = "history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "episodeId")
    private Long episodeId;
    @Column(name = "lastTime",nullable = false,updatable = false)
    private LocalDateTime lastTime;
    @Column(name = "hidden",columnDefinition = "int default 0")
    private int hidden;
    //열람내역 - 맴버 manyToOne
    //열람내역 - 회차 oneToMany
}
