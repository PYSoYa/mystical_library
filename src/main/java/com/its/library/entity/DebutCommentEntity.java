package com.its.library.entity;

import com.its.library.dto.DebutCommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "debut_comment")
public class DebutCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debutCommentId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debutId")
    private DebutEpisodeEntity debutEpisodeEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;
    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "contents",nullable = false,length = 500)
    private String contents;

    public static DebutCommentEntity toSave(DebutCommentDTO debutCommentDTO) {
        DebutCommentEntity debutCommentEntity = new DebutCommentEntity();
        debutCommentEntity.setMemberName();
    }
    //데뷔글 댓글- 데뷔글 ManyToOne
    //데뷔글 댓글- 맴버 ManyToOne
    
}
