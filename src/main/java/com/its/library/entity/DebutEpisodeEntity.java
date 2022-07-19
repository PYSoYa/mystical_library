package com.its.library.entity;

import com.its.library.dto.DebutEpisodeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "debut_episode")
public class DebutEpisodeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private DebutCategoryEntity debutCategoryEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;
    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "feat",nullable = false,length = 50)
    private String feat;
    @Column(name = "debutTitle",nullable = false,length = 100)
    private String debutTitle;
    @Column(name = "introduce",length = 500)
    private String introduce;
    @Column(name = "debutContents",nullable = false,length = 6000)
    private String debutContents;
    @Column(name = "debutHits",columnDefinition = "int default 0")
    private int debutHits;
    @Column(name = "debutImgName",length = 200)
    private String debutImgName;

    //데뷔글 - 데뷔글 카테고리 manyToOne
    //데뷔글 - 데뷔글 댓글 OneToMany
    //데뷔글 - 좋아요  OneToMany
    public static DebutEpisodeEntity toSave(DebutCategoryEntity categoryEntity, DebutEpisodeDTO debutEpisodeDTO, MemberEntity memberEntity) {
        DebutEpisodeEntity debutEpisodeEntity = new DebutEpisodeEntity();
        debutEpisodeEntity.setMemberEntity(memberEntity);
        debutEpisodeEntity.setMemberName(memberEntity.getMemberName());
        debutEpisodeEntity.setFeat(debutEpisodeDTO.getFeat());
        debutEpisodeEntity.setDebutImgName(debutEpisodeDTO.getDebutImgName());
        debutEpisodeEntity.setDebutCategoryEntity(categoryEntity);
        debutEpisodeEntity.setDebutTitle(debutEpisodeDTO.getDebutTitle());
        debutEpisodeEntity.setDebutContents(debutEpisodeDTO.getDebutContents());
        debutEpisodeEntity.setIntroduce(debutEpisodeDTO.getIntroduce());
        return debutEpisodeEntity;
    }

    public static DebutEpisodeEntity  toUpdate(DebutCategoryEntity debutCategoryEntity,DebutEpisodeDTO debutEpisodeDTO,MemberEntity memberEntity) {
        DebutEpisodeEntity debutEpisodeEntity = new DebutEpisodeEntity();
        debutEpisodeEntity.setId(debutEpisodeDTO.getId());
        debutEpisodeEntity.setMemberEntity(memberEntity);
        debutEpisodeEntity.setMemberName(memberEntity.getMemberName());
        debutEpisodeEntity.setFeat(debutEpisodeDTO.getFeat());
        debutEpisodeEntity.setDebutImgName(debutEpisodeDTO.getDebutImgName());
        debutEpisodeEntity.setDebutCategoryEntity(debutCategoryEntity);
        debutEpisodeEntity.setDebutTitle(debutEpisodeDTO.getDebutTitle());
        debutEpisodeEntity.setDebutContents(debutEpisodeDTO.getDebutContents());
        debutEpisodeEntity.setIntroduce(debutEpisodeDTO.getIntroduce());
        return debutEpisodeEntity;
    }
}
