package com.its.library.repository;

import com.its.library.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<WishlistEntity, Long> {

}
