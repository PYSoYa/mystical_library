package com.its.library.dto;

import com.its.library.entity.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private Long categoryId;
    private Long genreId;
    private Long memberId;
    private String memberName;
    private String feat;
    private String bookTitle;
    private String introduce;
    private MultipartFile bookImg;
    private String bookImgName;
    private String status;
    private int writerRole;
    private int hits;
    private int love;
    private double star;
    private LocalDateTime episodeUpdateTime;

    public BookDTO(Long id, Long categoryId, Long genreId, Long memberId, String memberName, String feat, String bookTitle, String introduce, String bookImgName, String status, int writerRole, int hits, int love, double star) {
        this.id = id;
        this.categoryId = categoryId;
        this.genreId = genreId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.feat = feat;
        this.bookTitle = bookTitle;
        this.introduce = introduce;
        this.bookImgName = bookImgName;
        this.status = status;
        this.writerRole = writerRole;
        this.hits = hits;
        this.love = love;
        this.star = star;
    }

    public static BookDTO findDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setCategoryId(bookEntity.getCategoryEntity().getId());
        bookDTO.setGenreId(bookEntity.getGenreEntity().getId());
        bookDTO.setMemberId(bookEntity.getMemberEntity().getId());
        bookDTO.setMemberName(bookEntity.getMemberName());
        bookDTO.setFeat(bookEntity.getFeat());
        bookDTO.setBookTitle(bookEntity.getBookTitle());
        bookDTO.setIntroduce(bookEntity.getIntroduce());
        bookDTO.setBookImgName(bookEntity.getBookImgName());
        bookDTO.setStatus(bookEntity.getStatus());
        bookDTO.setWriterRole(bookEntity.getWriterRole());
        bookDTO.setHits(bookEntity.getHits());
        bookDTO.setLove(bookEntity.getLove());
        bookDTO.setStar(bookEntity.getStar());
        return bookDTO;
    }
}
