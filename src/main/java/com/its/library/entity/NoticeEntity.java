package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "notice")
public class NoticeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishId")
    private WishEntity wishEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episodeId")
    private EpisodeEntity episodeEntity;
    @Column(name = "episodeTitle")
    private String episodeTitle;
    @Column(name = "noticeRead")
    private  boolean noticeRead;


    public static NoticeEntity save(MemberEntity memberEntity, EpisodeEntity episodeEntity, WishEntity wishEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setEpisodeEntity(episodeEntity);
        noticeEntity.setMemberEntity(memberEntity);
        noticeEntity.setWishEntity(wishEntity);
        noticeEntity.setEpisodeTitle(episodeEntity.getEpisodeTitle());
        noticeEntity.setNoticeRead(false);
        return noticeEntity;
    }
}
