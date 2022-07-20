package com.its.library.dto;

import com.its.library.entity.DebutCommentEntity;
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

    public static DebutCommentDTO toSave(DebutCommentEntity debutComment) {
        DebutCommentDTO debutCommentDTO = new DebutCommentDTO();
        return debutCommentDTO;
    }
}
