package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genreId",nullable = false)
    private GenreEntity genreEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId",nullable = false)
    private MemberEntity memberEntity;

    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "feat",length = 50)
    private String feat;
    @Column(name = "bookTitle",nullable = false,length = 50)
    private String bookTitle;
    @Column(name = "introduce",length = 500)
    private String introduce;
    @Column(name = "bookImgName",nullable = false,length = 100)
    private String bookImgName;
    @Column(name = "status",nullable = false,length = 10)
    private String status;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeEntity> episodeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private  List<WishlistEntity> wishlistEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boxId", nullable = false)
    private BoxEntity boxEntity;


    //책 - 회차  oneToMany ㅇ
    //책 - 맴버  manyToOne ㅇ
    //책 - 보관함 manyToOne ㅇ
    //책 - 장르 manyToOne  ㅇ
    //책 - 관심 oneToMany  ㅇ


}
