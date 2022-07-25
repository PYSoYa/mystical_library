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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;
    private final BookRepository bookRepository;

    // 회원가입+사진 저장처리
    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberImg = memberDTO.getMemberImg();
        String memberImgName = memberImg.getOriginalFilename();
        memberImgName = System.currentTimeMillis() + "_" + memberImgName;
        String savePath = "C:\\springboot_img\\" + memberImgName;
        if (!memberImg.isEmpty()) {
            memberImg.transferTo(new File(savePath));
        }
        memberDTO.setMemberImgName(memberImgName);

        MemberEntity memberEntity = MemberEntity.saveEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    // 아이디+비밀번호 체크 후 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByLoginId(memberDTO.getLoginId());
        if (memberEntity.isPresent()) {
            if (memberDTO.getMemberPassword().equals(memberEntity.get().getMemberPassword())) {
                MemberDTO loginDTO = MemberDTO.findDTO(memberEntity.get());
                return loginDTO;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // 회원 상세조회
    public MemberDTO myPage(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        if (memberEntity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.findDTO(memberEntity.get());
            return memberDTO;
        } else {
            return null;
        }

    }

    public List<BookDTO> wishlist(Long id) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        wishEntityList = wishRepository.findAll();
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

    public List<MemberDTO> memberWishlist(Long id) {
        List<WishEntity> wishEntityList = new ArrayList<>();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        wishEntityList = wishRepository.findAll();
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

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity listParameter : memberEntityList) {
            MemberEntity memberEntity = listParameter;
            MemberDTO memberDTO = MemberDTO.findDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public void memberDelete(Long id) {
        memberRepository.deleteById(id);
    }
}
