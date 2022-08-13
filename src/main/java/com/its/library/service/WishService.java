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
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public String check(WishDTO wishDTO) {
        if (wishDTO.getBookId() == null){
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishDTO.getMemberId());
            MemberEntity memberEntity = new MemberEntity();
            if (optionalMemberEntity.isPresent()){
                memberEntity = optionalMemberEntity.get();
                Optional<WishEntity> optionalWishEntity = wishRepository.findByMemberEntityAndMemberName(memberEntity, wishDTO.getMemberName());
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
                Optional<WishEntity> optionalWishEntity = wishRepository.findByBookEntityAndMemberName(bookEntity, wishDTO.getMemberName());
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
            Long id = wishRepository.save(wishEntity).getId();
            Optional<WishEntity> optionalWishEntity = wishRepository.findById(id);
            if (optionalWishEntity.isPresent()) {
                return "관심합산";
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
            Long id = wishRepository.save(wishEntity).getId();
            Optional<WishEntity> optionalWishEntity = wishRepository.findById(id);
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
            Optional<WishEntity> optionalWishEntity = wishRepository.findByMemberEntityAndMemberName(memberEntity, wishDTO.getMemberName());
            if (optionalWishEntity.isPresent()) {
                wishRepository.deleteById(optionalWishEntity.get().getId());
                return "관심합산";
            } else {
                return "no";
            }
        } else {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishDTO.getBookId());
            BookEntity bookEntity = new BookEntity();
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
            }
            Optional<WishEntity> optionalWishEntity = wishRepository.findByBookEntityAndMemberName(bookEntity, wishDTO.getMemberName());
            if (optionalWishEntity.isPresent()) {
                wishRepository.deleteById(optionalWishEntity.get().getId());
                return "ok";
            } else {
                return "no";
            }
        }

    }
    public List<MemberDTO> memberWishlist(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        MemberEntity memberEntity = new MemberEntity();
        wishEntityList = wishRepository.findAll();
        for (int i = 0; i < wishEntityList.size(); i++) {
            if (wishEntityList.get(i).getBookEntity() == null && wishEntityList.get(i).getMemberName().equals(memberName)) {
                Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(wishEntityList.get(i).getMemberEntity().getId());
                if (optionalMemberEntity.isPresent()) {
                    memberEntity = optionalMemberEntity.get();
                    memberDTOList.add(MemberDTO.toDTO(memberEntity));
                }
            }
        }
        return memberDTOList;
    }
    public List<BookDTO> wishlist(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        wishEntityList = wishRepository.findAll();
        for (int i = 0; i < wishEntityList.size(); i++) {
            if (wishEntityList.get(i).getMemberEntity() == null && wishEntityList.get(i).getMemberName().equals(memberName)) {
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(wishEntityList.get(i).getBookEntity().getId());
                if (optionalBookEntity.isPresent()) {
                    bookDTOList.add(BookDTO.findDTO(optionalBookEntity.get()));
                }
            }
        }
        return bookDTOList;
    }

    public List<WishDTO> findByMemberName(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        wishEntityList = wishRepository.findByMemberName(memberName);
            for (WishEntity wish: wishEntityList) {
                if (wish.getMemberEntity() != null){
                    wishDTOList.add(WishDTO.findMemberDTO(wish));
                }
            }
        return wishDTOList;
    }

    public List<WishDTO> findByBook(String memberName) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        wishEntityList = wishRepository.findByMemberName(memberName);
            for (WishEntity wish: wishEntityList) {
                if (wish.getBookEntity() != null) {
                    wishDTOList.add(WishDTO.findBookDTO(wish));
                }
            }
        return wishDTOList;
    }

    public List<WishDTO> findByBookWish(Long bookId) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<WishDTO> wishDTOList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
        }
        wishEntityList = wishRepository.findByBookEntity_Id(bookEntity.getId());
        for (WishEntity wish: wishEntityList) {
            wishDTOList.add(WishDTO.findBookDTO(wish));
        }
        return wishDTOList;
    }

    public int findByMemberId(Long writerId) {
        return wishRepository.countByMemberEntity_Id(writerId);
    }

    public void deleteByMemberName(String memberName) {
        List<WishEntity> wishEntityList =  wishRepository.findByMemberName(memberName);
        for (WishEntity wish: wishEntityList) {
            wishRepository.deleteById(wish.getId());
        }
    }
}
