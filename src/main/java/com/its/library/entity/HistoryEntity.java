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
    @Column(name = "lastTime",nullable = true,updatable = true)
    private LocalDateTime lastTime;
    @Column(name = "hidden",columnDefinition = "int default 0")
    private int hidden;
}
