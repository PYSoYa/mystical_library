package com.its.library.entity;

import com.its.library.dto.HistoryDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodeId")
    private EpisodeEntity episodeEntity;

    @Column
    private Long booKId;

    @Column
    private int hidden;

    @OneToMany(mappedBy = "historyEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeEntity> episodeEntityList = new ArrayList<>();

    public static HistoryEntity saveEntity(HistoryDTO historyDTO, MemberEntity memberEntity, EpisodeEntity episodeEntity) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setMemberEntity(memberEntity);
        historyEntity.setEpisodeEntity(episodeEntity);
        historyEntity.setBooKId(episodeEntity.getBookEntity().getId());
        historyEntity.setHidden(0);
        return historyEntity;
    }




    //열람내역 - 맴버 manyToOne ㅇ
    //열람내역 - 회차 oneToMany ㅇ
}
