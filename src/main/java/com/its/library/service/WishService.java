package com.its.library.service;

import com.its.library.dto.WishlistDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishlistRepository;
    private final MemberRepository memberRepository;


    public String check(WishlistDTO wishlistDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishlistDTO.getMemberId());
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()){
            memberEntity = optionalMemberEntity.get();
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findByMemberEntityAndMemberName(memberEntity, wishlistDTO.getMemberName());
            if (optionalWishEntity.isPresent()) {
                return "ok";
            } else {
                return "no";
            }
        } else {
            return null;
        }
    }

    public String saveWriter(WishlistDTO wishlistDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishlistDTO.getMemberId());
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
        }
        WishEntity wishEntity = new WishEntity();
        wishEntity = WishEntity.saveWriterEntity(wishlistDTO, memberEntity);
        Long id = wishlistRepository.save(wishEntity).getId();
        Optional<WishEntity> optionalWishEntity = wishlistRepository.findById(id);
        if (optionalWishEntity.isPresent()) {
            return "ok";
        } else {
            return "no";
        }
    }

    public String delete(WishlistDTO wishlistDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishlistDTO.getMemberId());
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
        }
        Optional<WishEntity> optionalWishEntity = wishlistRepository.findByMemberEntityAndMemberName(memberEntity, wishlistDTO.getMemberName());
        if (optionalWishEntity.isPresent()) {
            wishlistRepository.deleteById(optionalWishEntity.get().getId());
            return "ok";
        } else {
            return "no";
        }
    }

}
