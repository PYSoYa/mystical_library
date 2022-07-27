package com.its.library.entity;

import com.its.library.dto.PointDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter@Getter
@Table(name = "point")
public class PointEntity extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodeId")
    private EpisodeEntity episodeEntity;

    @Column(name = "plusPoint")
    private int plusPoint;
    @Column(name = "minusPoint")
    private int minusPoint;
    @Column(name = "totalPoint")
    private  int totalPoint;
    @Column(name = "bookTitle")
    private String bookTitle;
    @Column(name = "episodeTitle")
    private String episodeTitle;

    public static PointEntity save(PointDTO pointDTO,MemberEntity memberEntity) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setPlusPoint(pointDTO.getPlusPoint());
        pointEntity.setMemberEntity(memberEntity);
        pointEntity.setTotalPoint(memberEntity.getMemberPoint());
        return pointEntity;
    }

    public static PointEntity update(PointEntity pointEntity) {
        PointEntity pointEntity1 = new PointEntity();
        pointEntity1.setId(pointEntity.getId());
        pointEntity1.setBookTitle(pointEntity.getBookTitle());
        pointEntity1.setEpisodeTitle(pointEntity.getEpisodeTitle());
        pointEntity.setPlusPoint(pointEntity.getPlusPoint());
        pointEntity1.setMinusPoint(pointEntity.getMinusPoint());
        pointEntity1.setTotalPoint(pointEntity.getTotalPoint());
        pointEntity1.set
    }


    //포인트(결제) - 맴버 manyToOne ㅇ
    //포인트(결제) - 회차 manyToOne ㅇ
}
