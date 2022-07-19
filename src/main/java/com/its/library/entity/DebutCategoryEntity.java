package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@Table(name = "debut_category")
public class DebutCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long id;
    @Column(name = "category",nullable = false,length = 20)
    private String category;

    //데뷔글 카테고리- 데뷔글 oneToMany
    @OneToMany(mappedBy = "debutCategoryEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DebutEpisodeEntity> debutEpisodeEntityList = new ArrayList<>();

}
