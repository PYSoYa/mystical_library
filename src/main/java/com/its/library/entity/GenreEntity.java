package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreId")
    private Long id;


    @Column(name = "genre",nullable = false,length = 20)
    private String genre;

    @OneToMany(mappedBy = "genreEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookEntity> bookEntityList = new ArrayList<>();

    //장르- 책 oneToMany ㅇ

}
