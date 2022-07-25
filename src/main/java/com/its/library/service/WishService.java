package com.its.library.service;

import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishlistRepository;


}
