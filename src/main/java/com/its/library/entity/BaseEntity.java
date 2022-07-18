package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter@Setter
public class BaseEntity {
    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;
    @Column(name = "updateDateTime")
    private LocalDateTime updateDateTime;

}
