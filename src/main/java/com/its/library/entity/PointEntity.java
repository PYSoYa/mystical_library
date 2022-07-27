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

    public static PointEntity save(PointDTO pointDTO,MemberEntity memberEntity) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setPlusPoint(pointDTO.getPlusPoint());
        pointEntity.setMemberEntity(memberEntity);
        pointEntity.setTotalPoint(memberEntity.getMemberPoint());
        return pointEntity;
    }




    //포인트(결제) - 맴버 manyToOne ㅇ
    //포인트(결제) - 회차 manyToOne ㅇ
}
