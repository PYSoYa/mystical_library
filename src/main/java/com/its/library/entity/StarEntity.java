package com.its.library.entity;

import com.its.library.dto.MemberDTO;
import com.its.library.dto.StarDTO;
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
    private double star;
    //별점 - 맴버 manyToOne ㅇ
    //별점 - 회차 manyToOne ㅇ

    public static StarEntity saveEntity(StarDTO starDTO, MemberEntity memberEntity, EpisodeEntity episodeEntity) {
        StarEntity starEntity = new StarEntity();
        starEntity.setMemberEntity(memberEntity);
        starEntity.setEpisodeEntity(episodeEntity);
        starEntity.setStar(starDTO.getStar());
        return starEntity;
    }
}
