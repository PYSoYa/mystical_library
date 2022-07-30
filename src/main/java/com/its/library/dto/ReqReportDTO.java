package com.its.library.dto;

import com.its.library.entity.CommentEntity;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqReportEntity;
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
    private String debutContents;
    private String writerContents;





    public static ReqReportDTO debutReportList(ReqReportEntity reqReport1) {
        ReqReportDTO reqReportDTO = new ReqReportDTO();
        reqReportDTO.setId(reqReport1.getId());
        reqReportDTO.setMemberId(reqReport1.getMemberEntity().getId());
        reqReportDTO.setDebutCommentId(reqReport1.getDebutCommentEntity().getId());
        reqReportDTO.setDebutContents(reqReport1.getDebutContents());


        return reqReportDTO;
    }

    public static ReqReportDTO writerReportList(ReqReportEntity reqReport1) {
        ReqReportDTO reqReportDTO = new ReqReportDTO();
        reqReportDTO.setId(reqReport1.getId());
        reqReportDTO.setMemberId(reqReport1.getMemberEntity().getId());
        reqReportDTO.setCommentId(reqReport1.getCommentEntity().getId());
        reqReportDTO.setWriterContents(reqReport1.getWriterContents());
        return reqReportDTO;
    }
}
