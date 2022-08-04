package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.WishDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public String check(WishDTO wishDTO) {
        if (wishDTO.getBookId() == null){
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishDTO.getMemberId());
            MemberEntity memberEntity = new MemberEntity();
            if (optionalMemberEntity.isPresent()){
                memberEntity = optionalMemberEntity.get();
                Optional<WishEntity> optionalWishEntity = wishlistRepository.findByMemberEntityAndMemberName(memberEntity, wishDTO.getMemberName());
                if (optionalWishEntity.isPresent()) {
                    return "ok";
                } else {
                    return "no";
                }
            } else {
                return null;
            }
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
                Optional<WishEntity> optionalWishEntity = wishlistRepository.findByBookEntityAndMemberName(bookEntity, wishDTO.getMemberName());
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

    public String saveWriter(WishDTO wishDTO) {
        if (wishDTO.getBookId() == null) {
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishDTO.getMemberId());
            MemberEntity memberEntity = new MemberEntity();
            if (optionalMemberEntity.isPresent()) {
                memberEntity = optionalMemberEntity.get();
            }
            WishEntity wishEntity = new WishEntity();
            wishEntity = WishEntity.saveWriterEntity(wishDTO, memberEntity);
            Long id = wishlistRepository.save(wishEntity).getId();
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findById(id);
            if (optionalWishEntity.isPresent()) {
                return "ok";
            } else {
                return "no";
            }
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
            }
            WishEntity wishEntity = new WishEntity();
            wishEntity = WishEntity.saveBookEntity(wishDTO, bookEntity);
            Long id = wishlistRepository.save(wishEntity).getId();
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findById(id);
            if (optionalWishEntity.isPresent()) {
                return "ok";
            } else {
                return "no";
            }
        }

    }

    public String delete(WishDTO wishDTO) {
        if (wishDTO.getBookId() == null) {
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishDTO.getMemberId());
            MemberEntity memberEntity = new MemberEntity();
            if (optionalMemberEntity.isPresent()) {
                memberEntity = optionalMemberEntity.get();
            }
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findByMemberEntityAndMemberName(memberEntity, wishDTO.getMemberName());
            if (optionalWishEntity.isPresent()) {
                wishlistRepository.deleteById(optionalWishEntity.get().getId());
                return "ok";
            } else {
                return "no";
            }
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
            }
            Optional<WishEntity> optionalWishEntity = wishlistRepository.findByBookEntityAndMemberName(bookEntity, wishDTO.getMemberName());
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
            if (wishEntityList.get(i).getBookEntity() == null && wishEntityList.get(i).getMemberEntity().getId().equals(id)) {
                Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishEntityList.get(i).getMemberEntity().getId());
                if (optionalMemberEntity.isPresent()) {
                    memberDTOList.add(MemberDTO.toDTO(optionalMemberEntity.get()));

                }
            }
        }
        return memberDTOList;
    }
    public List<BookDTO> wishlist(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        wishEntityList = wishlistRepository.findAll();
        for (int i = 0; i < wishEntityList.size(); i++) {
            if (wishEntityList.get(i).getMemberEntity() == null && wishEntityList.get(i).getMemberName().equals(memberName)) {
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishEntityList.get(i).getBookEntity().getId());
                if (optionalBookEntity.isPresent()) {
                    bookDTOList.add(BookDTO.findDTO(optionalBookEntity.get()));
                }
            }
        }
        System.out.println("bookDTOList = " + bookDTOList);
        return bookDTOList;
    }

    public List<WishDTO> findByMemberName(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        wishEntityList = wishlistRepository.findByMemberName(memberName);
            for (WishEntity wish: wishEntityList) {
                if (wish.getMemberEntity() != null){
                    wishDTOList.add(WishDTO.findMemberDTO(wish));
                }
            }
        System.out.println("wishDTOList = " + wishDTOList);
        return wishDTOList;
    }

    public List<WishDTO> findByBook(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        wishEntityList = wishlistRepository.findByMemberName(memberName);
            for (WishEntity wish: wishEntityList) {
                if (wish.getBookEntity() != null) {
                    wishDTOList.add(WishDTO.findBookDTO(wish));
                }
            }
        System.out.println("wishDTOList = " + wishDTOList);
        return wishDTOList;
    }
}
