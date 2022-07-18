package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;

    @Column(name = "category",nullable = false,length = 20)
    private String category;

    //카테고리 - 장르 oneToMany ㅇ
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GenreEntity> genreEntityList = new ArrayList<>();


    @PreRemove
    private void preRemove() {
        genreEntityList.forEach(genre -> genre.setCategoryEntity(null));
    }
}
