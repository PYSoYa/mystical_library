package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishlistDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public String check(WishlistDTO wishlistDTO) {
        if (wishlistDTO.getBookId() == null){
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
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishlistDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
                Optional<WishEntity> optionalWishEntity = wishlistRepository.findByBookEntityAndMemberName(bookEntity, wishlistDTO.getMemberName());
                if (optionalWishEntity.isPresent()){
                    return "ok";
                } else {
                    return "no";
                }
            } else {
                return null;
            }
        }

    }

    public String saveWriter(WishlistDTO wishlistDTO) {
        if (wishlistDTO.getBookId() == null) {
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
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishlistDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
            }
            WishEntity wishEntity = new WishEntity();
            wishEntity = WishEntity.saveBookEntity(wishlistDTO, bookEntity);
            Long id = wishlistRepository.save(wishEntity).getId();
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findById(id);
            if (optionalWishEntity.isPresent()) {
                return "ok";
            } else {
                return "no";
            }
        }

    }

    public String delete(WishlistDTO wishlistDTO) {
        if (wishlistDTO.getBookId() == null) {
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
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishlistDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
            }
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findByBookEntityAndMemberName(bookEntity, wishlistDTO.getMemberName());
            if (optionalWishEntity.isPresent()) {
                wishlistRepository.deleteById(optionalWishEntity.get().getId());
                return "ok";
            } else {
                return "no";
            }
        }

    }
    public List<MemberDTO> memberWishlist(Long id) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        wishEntityList = wishlistRepository.findAll();
        for (int i = 0; i < wishEntityList.size(); i++) {
            if (wishEntityList.get(i).getBookEntity() == null) {
                Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishEntityList.get(i).getMemberEntity().getId());
                if (optionalMemberEntity.isPresent()) {
                    memberDTOList.add(MemberDTO.findDTO(optionalMemberEntity.get()));

                }
            }
        }
        return memberDTOList;
    }
    public List<BookDTO> wishlist(Long id) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        wishEntityList = wishlistRepository.findAll();
        for (int i = 0; i < wishEntityList.size(); i++) {
            if (wishEntityList.get(i).getMemberEntity() == null) {
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishEntityList.get(i).getBookEntity().getId());
                if (optionalBookEntity.isPresent()) {
                    bookDTOList.add(BookDTO.findDTO(optionalBookEntity.get()));
                }
            }
        }
        return bookDTOList;
    }

}
