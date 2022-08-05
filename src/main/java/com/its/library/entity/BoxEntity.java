package com.its.library.entity;

import com.its.library.dto.BoxDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "box")
public class BoxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boxId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @Column(name = "bookId")
    private Long bookId;

    @Column(name = "episodeId")
    private Long episodeId;

    @OneToMany(mappedBy = "boxEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookEntity> bookEntityList = new ArrayList<>();

    public BoxEntity saveEntity(BoxDTO boxDTO, MemberEntity memberEntity) {
        BoxEntity boxEntity = new BoxEntity();
        if (boxDTO.getId() != null) {
            boxEntity.setId(boxDTO.getId());
        }
        boxEntity.setMemberEntity(memberEntity);
        boxEntity.setBookId(boxDTO.getBookId());
        boxEntity.setEpisodeId(boxDTO.getEpisodeId());
        return boxEntity;
    }

    //보관함 - 맴버 manyToOne ㅇ
    //보관함 - 책 oneToMany ㅇ

}
