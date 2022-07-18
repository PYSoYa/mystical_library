package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqReportDTO {

    private Long id;
    private Long memberId;
    private Long commentId;
    private Long debutCommentId;

}
