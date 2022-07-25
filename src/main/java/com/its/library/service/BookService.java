package com.its.library.service;

import com.its.library.common.PagingConst;
import com.its.library.dto.*;
import com.its.library.entity.*;
import com.its.library.repository.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;
    private final EpisodeRepository episodeRepository;
    private final StarRepository starRepository;

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

    public EpisodeDTO episodeFindById(Long id) {
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        if (optionalEpisodeEntity.isPresent()) {
            EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
            EpisodeDTO episodeDTO = EpisodeDTO.findDTO(episodeEntity);
            System.out.println("episodeDTO = " + episodeDTO);
            return episodeDTO;
        } else {
            return null;
        }
    }


    public Page<EpisodeDTO> episodeFindAll(Long id, Pageable pageable) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
        }

        int page = pageable.getPageNumber();

        page = (page == 1) ? 0 : (page - 1);
        Page<EpisodeEntity> episodeEntities = episodeRepository.findByBookEntity(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")), bookEntity);

        Page<EpisodeDTO> episodeDTOList = episodeEntities.map(

                episode -> new EpisodeDTO(episode.getId(),
                        episode.getBookEntity().getId(),
                        episode.getEpisodeTitle(),
                        episode.getEpisodeContents(),
                        episode.getEpisodeImgName(),
                        episode.getPayment(),
                        episode.getEpisodeHits(),
                        episode.getWriterRole(),
                        episode.getCreatedDateTime()
                ));

        return episodeDTOList;

    }

    private final JavaMailSender mailSender;
    private String mail = "pysoya@naver.com";

    public void reqBookUpdate(BookDTO bookDTO, MailDTO mailDTO) throws IOException {
        MultipartFile bookImg = bookDTO.getBookImg();
        String bookImgName = bookImg.getOriginalFilename();
        bookImgName = System.currentTimeMillis() + "_" + bookImgName;
        String savePath = "C:\\springboot_img\\" + bookImgName;
        if (!bookImg.isEmpty()) {
            bookImg.transferTo(new File(savePath));
        }
        bookDTO.setBookImgName(bookImgName);

        mailDTO.setBookDTO(bookDTO);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailDTO.getMailTitle());
        message.setText(String.valueOf(mailDTO.getBookDTO()));
        mailSender.send(message);
    }

    public void reqEpisodeUpdate(EpisodeDTO episodeDTO, MailDTO mailDTO) throws IOException {
        MultipartFile episodeImg = episodeDTO.getEpisodeImg();
        String episodeImgName = episodeImg.getOriginalFilename();
        episodeImgName = System.currentTimeMillis() + "_" + episodeImgName;
        String savePath = "C:\\springboot_img\\" + episodeImgName;
        if (!episodeImg.isEmpty()) {
            episodeImg.transferTo(new File(savePath));
        }
        episodeDTO.setEpisodeImgName(episodeImgName);

        mailDTO.setEpisodeDTO(episodeDTO);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailDTO.getMailTitle());
        message.setText(String.valueOf(mailDTO.getEpisodeDTO()));
        mailSender.send(message);
    }

    public void reqBookDelete(Long id, String memberName, String why, String mailTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailTitle);
        message.setText("책 고유번호: " + id + "\n" + "\n" + "작가명: " + memberName + "\n" + "삭제사유: " + why);
        mailSender.send(message);
    }

    public void reqEpisodeDelete(Long id, String memberName, String why, String mailTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailTitle);
        message.setText("회차 고유번호: " + id + "\n" + "\n" + "작가명: " + memberName + "\n" + "삭제사유: " + why);
        mailSender.send(message);
    }

    @Transactional
    public Page<BookDTO> bookList(Pageable pageable, Long categoryId, Long genreId) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(genreId);

        CategoryEntity categoryEntity = new CategoryEntity();
        GenreEntity genreEntity = new GenreEntity();

        if (optionalCategoryEntity.isPresent() && optionalGenreEntity.isPresent()) {
            categoryEntity = optionalCategoryEntity.get();
            genreEntity = optionalGenreEntity.get();
        }

        int page = pageable.getPageNumber();

        page = (page == 1) ? 0 : (page - 1);
        Page<BookEntity> bookEntityList = bookRepository.findByCategoryEntityAndGenreEntity(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")), categoryEntity, genreEntity);

        Page<BookDTO> bookDTOList = bookEntityList.map(

                book -> new BookDTO(book.getId(),
                        book.getCategoryEntity().getId(),
                        book.getGenreEntity().getId(),
                        book.getMemberEntity().getId(),
                        book.getMemberName(),
                        book.getFeat(),
                        book.getBookTitle(),
                        book.getIntroduce(),
                        book.getBookImgName(),
                        book.getStatus(),
                        book.getWriterRole(),
                        book.getStar()
                ));

        return bookDTOList;
    }

    public double saveStar(StarDTO starDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(starDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(starDTO.getEpisodeId());

        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();

        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            StarEntity starEntity = StarEntity.saveEntity(starDTO, memberEntity, episodeEntity);
            starRepository.save(starEntity).getId();
            double starAvg = starRepository.starAvg(episodeEntity.getId());
            episodeEntity.setStar(starAvg);
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
            if (optionalBookEntity.isPresent()) {
                episodeRepository.save(episodeEntity);
                BookEntity bookEntity = optionalBookEntity.get();
                double bookStarAvg = bookRepository.starAvg(bookEntity.getId());
                bookEntity.setStar(bookStarAvg);
                bookRepository.save(bookEntity);
            }
            return starAvg;
        } else {
            return 0.0;
        }
    }


    public List<BookDTO> search(String searchType, String q) {
        List<BookDTO> bookDTOList = new ArrayList<>();
        Map<String, String> search = new HashMap<>();
        search.put("type", searchType);
        search.put("q", q);
        if (searchType.equals("작가")) {
            List<BookEntity> bookEntityList = bookRepository.findByMemberNameContaining(q);
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
            return bookDTOList;
        }
        if (searchType.equals("책")) {
            List<BookEntity> bookEntityList = bookRepository.findByBookTitleContaining(q);
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
            return bookDTOList;
        } else {
            return null;
        }
    }

    public List<BookDTO> findAll() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookList : bookEntityList) {
            BookEntity bookEntity = bookList;
            BookDTO bookDTO = BookDTO.findDTO(bookEntity);
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }


    public void bookAgree(BookDTO bookDTO)  {


        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(bookDTO.getMemberName());
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(bookDTO.getGenreId());
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(bookDTO.getCategoryId());

        if (optionalMemberEntity.isPresent() && optionalCategoryEntity.isPresent() && optionalGenreEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            GenreEntity genreEntity = optionalGenreEntity.get();

            BookEntity bookEntity = BookEntity.bookAgree(bookDTO, memberEntity, categoryEntity, genreEntity);
             bookRepository.save(bookEntity);


        }
    }

    public void bookDelete(Long id) {
        bookRepository.deleteById(id);
    }
}
