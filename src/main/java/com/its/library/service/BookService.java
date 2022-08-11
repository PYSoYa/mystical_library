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
import java.lang.invoke.CallSite;
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
        if (!bookImg.isEmpty()) {
            bookImgName = System.currentTimeMillis() + "_" + bookImgName;
            String savePath = "C:\\springboot_img\\" + bookImgName;
            bookImg.transferTo(new File(savePath));
            bookDTO.setBookImgName(bookImgName);
        }

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

    public EpisodeDTO reqEpisodeSave(EpisodeDTO episodeDTO) throws IOException {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeDTO.getBookId());
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
        }
        MultipartFile episodeImg = episodeDTO.getEpisodeImg();
        String episodeImgName = episodeImg.getOriginalFilename();
        if (!episodeImg.isEmpty()) {
            episodeImgName = System.currentTimeMillis() + "_" + episodeImgName;
            String savePath = "C:\\springboot_img\\" + episodeImgName;
            episodeImg.transferTo(new File(savePath));
            episodeDTO.setEpisodeImgName(episodeImgName);
        } else {
            episodeDTO.setEpisodeImgName(bookEntity.getBookImgName());
        }
        EpisodeEntity episodeEntity = EpisodeEntity.saveEntity(episodeDTO, bookEntity);
        EpisodeEntity episodeEntity1 = episodeRepository.save(episodeEntity);
        EpisodeDTO episodeDTO1 = EpisodeDTO.findDTO(episodeEntity1);
        bookEntity.setEpisodeUpdateTime(episodeEntity.getCreatedDateTime());
        bookRepository.save(bookEntity);

        return episodeDTO1;

    }

    public EpisodeDTO episodeFindById(Long id) {
        BookEntity bookEntity = new BookEntity();
        List<EpisodeEntity> episodeEntityList = new ArrayList<>();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        episodeRepository.episodeHits(id);
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        if (optionalEpisodeEntity.isPresent()) {
            EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
            bookEntity = optionalBookEntity.get();
            episodeEntityList = episodeRepository.findByBookEntity_Id(bookEntity.getId());
            for (EpisodeEntity episode : episodeEntityList) {
                episodeDTOList.add(EpisodeDTO.findDTO(episode));
                System.out.println("episodeDTOList = " + episodeDTOList);
            }
            int hits = bookRepository.hitsSum(episodeEntityList.get(0).getBookEntity().getId());
            bookEntity.setHits(hits);
            bookRepository.save(bookEntity);
            EpisodeDTO episodeDTO = EpisodeDTO.findDTO(episodeEntity);
            return episodeDTO;
        } else {
            return null;
        }
    }

    @Transactional
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
                        episode.getHits(),
                        episode.getWriterRole(),
                        episode.getStar(),
                        episode.getCreatedDateTime()
                ));

        return episodeDTOList;

    }

    private final JavaMailSender mailSender;
    private final String mail = "oloveo24@naver.com";

    public void reqBookUpdate(BookDTO bookDTO, MailDTO mailDTO) throws IOException {
        MultipartFile bookImg = bookDTO.getBookImg();
        String bookImgName = bookImg.getOriginalFilename();
        if (!bookImg.isEmpty()) {
            bookImgName = System.currentTimeMillis() + "_" + bookImgName;
            String savePath = "C:\\springboot_img\\" + bookImgName;
            bookImg.transferTo(new File(savePath));
            bookDTO.setBookImgName(bookImgName);
        }

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
        if (!episodeImg.isEmpty()) {
            episodeImgName = System.currentTimeMillis() + "_" + episodeImgName;
            String savePath = "C:\\springboot_img\\" + episodeImgName;
            episodeImg.transferTo(new File(savePath));
            episodeDTO.setEpisodeImgName(episodeImgName);
        }

        mailDTO.setEpisodeDTO(episodeDTO);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailDTO.getMailTitle());
        message.setText(String.valueOf(mailDTO.getEpisodeDTO()));
        mailSender.send(message);
    }

    public void reqBookDelete(Long id, String memberName, String why, String mailTitle, String fromAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailTitle);
        message.setText("회신 이메일: " + fromAddress +
                "\n"
                + "책 고유번호: " + id +
                "\n"
                + "작가명: " + memberName +
                "\n"
                + "삭제사유: " + why);
        mailSender.send(message);
    }

    public void reqEpisodeDelete(Long id, String memberName, String why, String mailTitle, String fromAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom(mail);
        message.setSubject(mailTitle);
        message.setText("회신 이메일: " + fromAddress +
                "\n"
                + "책 고유번호: " + id +
                "\n"
                + "작가명: " + memberName +
                "\n"
                + "삭제사유: " + why);
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
                        book.getHits(),
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
            episodeEntity.setStar(Math.round(starAvg * 100) / 100.0);
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
            if (optionalBookEntity.isPresent()) {
                episodeRepository.save(episodeEntity);
                BookEntity bookEntity = optionalBookEntity.get();
                double bookStarAvg = bookRepository.starAvg(bookEntity.getId());
                bookEntity.setStar(Math.round(bookStarAvg * 100) / 100.0);
                bookRepository.save(bookEntity);
            }
            return Math.round(starAvg * 100) / 100.0;
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

    public List<BookDTO> findByHiddenBook() {
        List<BookEntity> bookEntityList = bookRepository.findByWriterRole(0);
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookList : bookEntityList) {
            BookEntity bookEntity = bookList;
            BookDTO bookDTO = BookDTO.findDTO(bookEntity);
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }


    public void bookAgree(BookDTO bookDTO) {
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

    public List<BookDTO> genreList(Long genreId, Long alignmentId) {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<EpisodeEntity> episodeEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        List<EpisodeDTO> episodeDTOList1 = new ArrayList<>();
        List<Long> list = new ArrayList<>();
        GenreEntity genreEntity = new GenreEntity();
        BookEntity bookEntity = new BookEntity();
        Optional<GenreEntity> optionalGenreEntity = genreRepository.findById(genreId);

        if (optionalGenreEntity.isPresent()) {
            genreEntity = optionalGenreEntity.get();
        }
        if (alignmentId == 0) {
            bookEntityList = bookRepository.findByGenreEntityOrderByHitsDesc(genreEntity);
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
        } else if (alignmentId == 1) { // 장르별 최신순 정렬
            bookEntityList = bookRepository.findByGenreEntity(genreEntity.getId());
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book)); // 장르가 일치하는 책 리스트
            }
        } else if (alignmentId == 2) { // 장르별 별점순 정렬
            bookEntityList = bookRepository.findByGenreEntityOrderByStarDesc(genreEntity);
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
        } else if (alignmentId == 3) { // 장르별 조회순 정렬
            bookEntityList = bookRepository.findByGenreEntityOrderByHitsDesc(genreEntity);
            for (BookEntity book : bookEntityList) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
        }
        return bookDTOList;
    }

    // 장르 1번 리스트
    public List<BookDTO> categoryList1() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList1 = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 1 && book.getGenreEntity().getId() == 1) {
                if (bookDTOList1.size() < 6) {
                    bookDTOList1.add(BookDTO.findDTO(book));
                    if (bookDTOList1.size() == 5) {
                        break;
                    }
                }
            }
        }
        return bookDTOList1;
    }

    // 장르 2번 리스트
    public List<BookDTO> categoryList2() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList2 = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 1 && book.getGenreEntity().getId() == 2) {
                if (bookDTOList2.size() < 6) {
                    bookDTOList2.add(BookDTO.findDTO(book));
                    if (bookDTOList2.size() == 5) {
                        break;
                    }
                }
            }
        }
        return bookDTOList2;
    }

    // 장르 3번 리스트
    public List<BookDTO> categoryList3() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList3 = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 1 && book.getGenreEntity().getId() == 3) {
                if (bookDTOList3.size() < 6) {
                    bookDTOList3.add(BookDTO.findDTO(book));
                    if (bookDTOList3.size() == 5) {
                        break;
                    }
                }
            }
        }
        return bookDTOList3;
    }

    // 장르 4번 리스트
    public List<BookDTO> categoryList4() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList4 = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 1 && book.getGenreEntity().getId() == 4) {
                if (bookDTOList4.size() < 6) {
                    bookDTOList4.add(BookDTO.findDTO(book));
                    if (bookDTOList4.size() == 5) {
                        break;
                    }
                }
            }
        }
        return bookDTOList4;
    }

    // 장르 5번 리스트
    public List<BookDTO> categoryList5() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList5 = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 1 && book.getGenreEntity().getId() == 5) {
                if (bookDTOList5.size() < 6) {
                    bookDTOList5.add(BookDTO.findDTO(book));
                    if (bookDTOList5.size() == 5) {
                        break;
                    }
                }
            }
        }
        return bookDTOList5;
    }

    public List<BookDTO> siList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 2) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
        }
        return bookDTOList;
    }

    public List<BookDTO> essayList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookEntityList = bookRepository.findAll();
        for (BookEntity book : bookEntityList) {
            if (book.getCategoryEntity().getId() == 3) {
                bookDTOList.add(BookDTO.findDTO(book));
            }
        }
        return bookDTOList;
    }

    public Long first(Long bookId) {
        List<EpisodeEntity> episodeEntityList = new ArrayList<>();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
            episodeEntityList = episodeRepository.findByBookId(bookEntity.getId());
            for (EpisodeEntity episode : episodeEntityList) {
                episodeDTOList.add(EpisodeDTO.findDTO(episode));
            }
        }
        return episodeDTOList.get(0).getId();
    }

    public List<BookDTO> findAllByOnStatus(Long memberId) {
        List<BookEntity> bookEntityList = bookRepository.findAllByMemberEntity_IdAndStatus(memberId, "연재");
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity book : bookEntityList) {
            bookDTOList.add(BookDTO.findDTO(book));
        }
        return bookDTOList;
    }

    public List<BookDTO> finishBook(Long memberId) {
        List<BookEntity> bookEntityList = bookRepository.findAllByMemberEntity_IdAndStatus(memberId, "완결");
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity book : bookEntityList) {
            bookDTOList.add(BookDTO.findDTO(book));
        }
        return bookDTOList;
    }
}
