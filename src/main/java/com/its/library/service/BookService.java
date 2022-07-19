package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.GenreDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.GenreEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.GenreRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final MemberRepository memberRepository;

    private final GenreRepository genreRepository;

    public BookDTO reqBookSave(BookDTO bookDTO) throws IOException {
        MultipartFile bookImg = bookDTO.getBookImg();
        String bookImgName = bookImg.getOriginalFilename();
        bookImgName = System.currentTimeMillis() + "_" + bookImgName;
        String savePath = "C:\\springboot_img\\" + bookImgName;
        if(!bookImg.isEmpty()){
            bookImg.transferTo(new File(savePath));
        }
        bookDTO.setBookImgName(bookImgName);

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(bookDTO.getMemberId());
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(bookDTO.getGenreId());

        if(optionalMemberEntity.isPresent()){
            if(optionalGenreEntity.isPresent()){
                MemberEntity memberEntity = optionalMemberEntity.get();
                GenreEntity genreEntity = optionalGenreEntity.get();

                BookEntity bookEntity = BookEntity.saveEntity(bookDTO, memberEntity, genreEntity);
                Long id = bookRepository.save(bookEntity).getId();
                return findById(id);
            } else {
                return null;
            }

        } else {
            return null;
        }

    }

    public BookDTO findById(Long id){
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()){
            BookEntity bookEntity = optionalBookEntity.get();
            BookDTO bookDTO = BookDTO.findDTO(bookEntity);
            return bookDTO;
        } else {
            return null;
        }
    }

    public void categoryList(String category) {

    }
}
