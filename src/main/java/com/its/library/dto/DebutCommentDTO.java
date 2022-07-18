package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebutCommentDTO {

    private Long id;
    private Long debutId;
    private Long memberId;
    private String memberName;
    private String contents;
    private LocalDateTime createdTime;

}
