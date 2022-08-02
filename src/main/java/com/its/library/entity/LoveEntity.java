package com.its.library.entity;

import com.its.library.dto.LoveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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



    public static LoveEntity toSave(LoveDTO loveDTO, MemberEntity memberEntity) {
        LoveEntity loveEntity = new LoveEntity();
        loveEntity.setDebutId(loveDTO.getDebutId());
        loveEntity.setMemberEntity(memberEntity);
        loveEntity.setLove(1);
        return loveEntity;
    }
    //좋아요 - 맴버 manyToOne
    //좋아요 - 데뷔글 manyToOne
}
