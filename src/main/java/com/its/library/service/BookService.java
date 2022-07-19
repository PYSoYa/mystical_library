package com.its.library.service;

import com.its.library.common.PagingConst;
import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.GenreDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.*;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final MemberRepository memberRepository;

    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;

    private final EpisodeRepository episodeRepository;

    public BookDTO reqBookSave(BookDTO bookDTO) throws IOException {
        MultipartFile bookImg = bookDTO.getBookImg();
        String bookImgName = bookImg.getOriginalFilename();
        bookImgName = System.currentTimeMillis() + "_" + bookImgName;
        String savePath = "C:\\springboot_img\\" + bookImgName;
        if (!bookImg.isEmpty()) {
            bookImg.transferTo(new File(savePath));
        }
        bookDTO.setBookImgName(bookImgName);

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(bookDTO.getMemberName());
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(bookDTO.getGenreId());
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(bookDTO.getCategoryId());

        if (optionalMemberEntity.isPresent() && optionalCategoryEntity.isPresent() && optionalGenreEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            GenreEntity genreEntity = optionalGenreEntity.get();

            BookEntity bookEntity = BookEntity.saveEntity(bookDTO, memberEntity, categoryEntity, genreEntity);
            Long id = bookRepository.save(bookEntity).getId();
            return findById(id);
        } else {
            return null;
        }

    }

    public BookDTO findById(Long id) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()) {
            BookEntity bookEntity = optionalBookEntity.get();
            BookDTO bookDTO = BookDTO.findDTO(bookEntity);
            return bookDTO;
        } else {
            return null;
        }
    }

    public Long reqEpisodeSave(EpisodeDTO episodeDTO) throws IOException {
        MultipartFile episodeImg = episodeDTO.getEpisodeImg();
        String episodeImgName = episodeImg.getOriginalFilename();
        episodeImgName = System.currentTimeMillis() + "_" + episodeImgName;
        String savePath = "C:\\springboot_img\\" + episodeImgName;
        if (!episodeImg.isEmpty()) {
            episodeImg.transferTo(new File(savePath));
        }
        episodeDTO.setEpisodeImgName(episodeImgName);
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeDTO.getBookId());

        if (optionalBookEntity.isPresent()) {
            BookEntity bookEntity = optionalBookEntity.get();

            EpisodeEntity episodeEntity = EpisodeEntity.saveEntity(episodeDTO, bookEntity);
            Long id = episodeRepository.save(episodeEntity).getId();

            return id;
        } else {
            return null;
        }


    }

    public EpisodeDTO episodeFindById(Long id){
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        if (optionalEpisodeEntity.isPresent()){
            EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
            EpisodeDTO episodeDTO = EpisodeDTO.findDTO(episodeEntity);
            return episodeDTO;
        } else {
            return null;
        }
    }
    public void categoryList(String category) {

    }


    public Page<EpisodeDTO> episodeFindAll(Long id, Pageable pageable) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
        }

        int page = pageable.getPageNumber();

        page = (page == 1)? 0: (page-1);
        Page<EpisodeEntity> episodeEntities = episodeRepository.findByBookEntity(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")),bookEntity);

        Page<EpisodeDTO> episodeDTOList = episodeEntities.map(

                episode -> new EpisodeDTO(episode.getId(),
                                episode.getBookEntity().getId(),
                                episode.getEpisodeTitle(),
                                episode.getEpisodeContents(),
                                episode.getEpisodeImgName(),
                                episode.getPayment(),
                                episode.getEpisodeHits(),
                                episode.getHidden(),
                                episode.getCreatedDateTime()
                        ));

            return episodeDTOList;

    }
}
