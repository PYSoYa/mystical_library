package com.its.library.dto;

import com.its.library.entity.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
        return bookDTO;
    }
}
