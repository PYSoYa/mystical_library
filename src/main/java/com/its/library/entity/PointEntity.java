package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter@Getter
@Table(name = "point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "episodeId")
    private Long episodeId;
    @Column(name = "point")
    private int point;

}
