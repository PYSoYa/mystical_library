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
    @Column(name = "genreId",nullable = true)
    private Long genreId;
    @Column(name = "memberId",nullable = true)
    private Long memberId;
    @Column(name = "memberName",nullable = true,length = 20)
    private String memberName;
    @Column(name = "with",length = 50)
    private String with;
    @Column(name = "bookTitle",nullable = true,length = 50)
    private String bookTitle;
    @Column(name = "introduce",nullable = true,length = 500)
    private String introduce;
    @Column(name = "bookImgName",nullable = true,length = 100)
    private String bookImgName;
    @Column(name = "status",nullable = true,length = 10)
    private String status;
}
