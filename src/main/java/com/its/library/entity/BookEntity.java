package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "genreId",nullable = false)
    private Long genreId;
    @Column(name = "memberId",nullable = false)
    private Long memberId;
    @Column(name = "memberName",nullable = false,length = 20)
    private String memberName;
    @Column(name = "with",length = 50)
    private String with;
    @Column(name = "bookTitle",nullable = false,length = 50)
    private String bookTitle;
    @Column(name = "introduce",length = 500)
    private String introduce;
    @Column(name = "bookImgName",nullable = false,length = 100)
    private String bookImgName;
    @Column(name = "status",nullable = false,length = 10)
    private String status;
    //책 - 회차  oneToMany
    //책 - 맴버  manyToOne
    //책 - 보관함 manyToOne
    //책 - 장르 manyToOne
    //책 - 관심 oneToMany

}
