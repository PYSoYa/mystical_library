package com.its.library.entity;

import com.its.library.dto.BookDTO;
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
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryEntity categoryEntity;

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
    @Column(name = "status", length = 10)
    private String status;
    @Column
    private int writerRole;
    @Column
    private int hits;
    @Column
    private double star;



    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeEntity> episodeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private  List<WishEntity> wishlistEntityList = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boxId")
    private BoxEntity boxEntity;

    public static BookEntity saveEntity(BookDTO bookDTO, MemberEntity memberEntity, CategoryEntity categoryEntity, GenreEntity genreEntity) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setCategoryEntity(categoryEntity);
        bookEntity.setGenreEntity(genreEntity);
        bookEntity.setMemberEntity(memberEntity);
        bookEntity.setMemberName(memberEntity.getMemberName());
        bookEntity.setFeat(bookDTO.getFeat());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setIntroduce(bookDTO.getIntroduce());
        bookEntity.setBookImgName(bookDTO.getBookImgName());
        bookEntity.setStatus("연재");
        bookEntity.setWriterRole(1);
        bookEntity.setHits(0);
        bookEntity.setStar(0.0);
        return  bookEntity;
    }

    public static BookEntity updateEntity(BookDTO bookDTO, MemberEntity memberEntity, CategoryEntity categoryEntity, GenreEntity genreEntity) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setCategoryEntity(categoryEntity);
        bookEntity.setGenreEntity(genreEntity);
        bookEntity.setMemberEntity(memberEntity);
        bookEntity.setMemberName(memberEntity.getMemberName());
        bookEntity.setFeat(bookDTO.getFeat());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setIntroduce(bookDTO.getIntroduce());
        bookEntity.setBookImgName(bookDTO.getBookImgName());
        bookEntity.setStatus(bookDTO.getStatus());
        bookEntity.setWriterRole(1);
        bookEntity.setHits(bookDTO.getHits());
        bookEntity.setStar(bookDTO.getStar());
        return  bookEntity;
    }

    public static BookEntity bookAgree(BookDTO bookDTO, MemberEntity memberEntity, CategoryEntity categoryEntity, GenreEntity genreEntity) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setCategoryEntity(categoryEntity);
        bookEntity.setGenreEntity(genreEntity);
        bookEntity.setMemberEntity(memberEntity);
        bookEntity.setMemberName(memberEntity.getMemberName());
        bookEntity.setFeat(bookDTO.getFeat());
        bookEntity.setBookTitle(bookDTO.getBookTitle());
        bookEntity.setIntroduce(bookDTO.getIntroduce());
        bookEntity.setBookImgName(bookDTO.getBookImgName());
        bookEntity.setStatus(bookDTO.getStatus());
        bookEntity.setWriterRole(2);
        bookEntity.setStar(bookDTO.getStar());
        return  bookEntity;
    }


    //책 - 회차  oneToMany ㅇ
    //책 - 맴버  manyToOne ㅇ
    //책 - 보관함 manyToOne ㅇ
    //책 - 장르 manyToOne  ㅇ
    //책 - 관심 oneToMany  ㅇ


}
