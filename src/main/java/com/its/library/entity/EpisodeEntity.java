package com.its.library.entity;

import com.its.library.dto.EpisodeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "episode")
public class EpisodeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episodeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookEntity bookEntity;

    @Column(name = "episodeTitle",nullable = false,length = 30)
    private String episodeTitle;
    @Column(name = "episodeContents",nullable = false,length = 6000)
    private String episodeContents;
    @Column(name = "episodeImgName",length = 100)
    private String episodeImgName;
    @Column(name = "payment",nullable = false)
    private int payment;
    @Column(name = "episodeHits",columnDefinition = "int default 0")
    private int episodeHits;
    @Column(name = "hidden",columnDefinition = "int default 0" )
    private int hidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historyId")
    private HistoryEntity historyEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pointId")
    private PointEntity pointEntity;

    @OneToMany(mappedBy = "episodeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "episodeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StarEntity> starEntityList = new ArrayList<>();

    public static EpisodeEntity saveEntity(EpisodeDTO episodeDTO, BookEntity bookEntity) {
        EpisodeEntity episodeEntity = new EpisodeEntity();
        episodeEntity.setBookEntity(bookEntity);
        episodeEntity.setEpisodeTitle(episodeDTO.getEpisodeTitle());
        episodeEntity.setEpisodeContents(episodeDTO.getEpisodeContents());
        episodeEntity.setEpisodeImgName(episodeDTO.getEpisodeImgName());
        episodeEntity.setPayment(episodeDTO.getPayment());
        episodeEntity.setEpisodeHits(0);
        episodeEntity.setHidden(0);
        return episodeEntity;
    }


    //회차 - 책 manyToOne ㅇ
    //회차 - 댓글 oneToMany ㅇ
    //회차 - 열람내역 manyToOne ㅇ
    //회차 - 포인트 manyToOne ㅇ
    //회차 - 별점 oneToMany ㅇ

    
}
