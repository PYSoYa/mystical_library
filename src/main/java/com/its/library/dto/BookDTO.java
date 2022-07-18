package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private Long genreId;
    private Long memberId;
    private String memberName;
    private String with;
    private String bookTitle;
    private String introduce;
    private MultipartFile bookImg;
    private String bookImgName;
    private String status;

}
