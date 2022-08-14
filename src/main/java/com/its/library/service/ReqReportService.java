package com.its.library.service;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.dto.ReqReportDTO;
import com.its.library.entity.CommentEntity;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqReportEntity;
import com.its.library.repository.CommentRepository;
import com.its.library.repository.DebutCommentRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.ReqReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReqReportService {
    private final ReqReportRepository reqReportRepository;
    private final DebutCommentRepository debutCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    //데뷔글 신고 내역

    //작가글 신고내역

    @Transactional
    public List<ReqReportDTO> debutReportList() {
       List<ReqReportEntity> reqReportEntityList = reqReportRepository.findAllByDebutCommentEntityIsNotNull();
       List<ReqReportDTO> reqReportDTOList = new ArrayList<>();
        for (ReqReportEntity reqReport :reqReportEntityList) {
            ReqReportEntity reqReport1 = reqReport;
           ReqReportDTO reqReportDTO = ReqReportDTO.debutReportList(reqReport1);
           reqReportDTOList.add(reqReportDTO);
        }
        return reqReportDTOList;
    }

    //작가글 신고내역
    @Transactional
    public List<ReqReportDTO> writerReportList() {
        List<ReqReportEntity> reqReportEntityList = reqReportRepository.findAllByCommentEntityIsNotNull();
        List<ReqReportDTO> reqReportDTOList = new ArrayList<>();
        for (ReqReportEntity reqReport:reqReportEntityList) {
            ReqReportEntity reqReport1 = reqReport;
            ReqReportDTO reqReportDTO = ReqReportDTO.writerReportList(reqReport1);
            reqReportDTOList.add(reqReportDTO);

        }
        return reqReportDTOList;
    }

    //댓글 신고 승인 처리
    public void debutCommentDelete(Long id) {
        Optional<ReqReportEntity> reqReportEntity = reqReportRepository.findById(id);
        if (reqReportEntity.isPresent()) {
            ReqReportEntity reqReportEntity1 = reqReportEntity.get();
            Optional<DebutCommentEntity> optionalDebutCommentEntity = debutCommentRepository.findById(reqReportEntity1.getDebutCommentEntity().getId());
            if (optionalDebutCommentEntity.isPresent()) {
                DebutCommentEntity debutComment = optionalDebutCommentEntity.get();
                DebutCommentEntity debutCommentEntity = DebutCommentEntity.toUpdate(debutComment);
                debutCommentRepository.save(debutCommentEntity);
                reqReportRepository.deleteByDebutCommentEntity(debutComment);

            }
        }

    }

    //댓글 신고 거절 기능
    public void debutReportDelete(Long id) {
        reqReportRepository.deleteById(id);

    }

    public void commentDelete(Long id) {
        Optional<ReqReportEntity> reqReportEntity = reqReportRepository.findById(id);
        if (reqReportEntity.isPresent()) {
            ReqReportEntity reqReportEntity1 = reqReportEntity.get();
            Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(reqReportEntity1.getCommentEntity().getId());
            if (optionalCommentEntity.isPresent()) {
                CommentEntity comment = optionalCommentEntity.get();
                CommentEntity commentEntity = CommentEntity.toUpdate(comment);
                commentRepository.save(commentEntity);
                reqReportRepository.deleteByCommentEntity(comment);

            }
        }
    }

    public void reportDelete(Long id) {
        reqReportRepository.deleteById(id);
    }

    public String findReportComment(Long id, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        if (optionalCommentEntity.isPresent() && optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            CommentEntity commentEntity = optionalCommentEntity.get();
            Optional<ReqReportEntity> optionalReqReportEntity = reqReportRepository.findByMemberEntityAndCommentEntity(memberEntity, commentEntity);
            if (optionalReqReportEntity.isPresent()) {
                return "신고내역있음";
            }
        }
        return null;
    }

    public String reqReportDebutComment(Long id, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<DebutCommentEntity> optionalDebutCommentEntity = debutCommentRepository.findById(id);
        if (optionalMemberEntity.isPresent() && optionalDebutCommentEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            DebutCommentEntity debutCommentEntity = optionalDebutCommentEntity.get();
            Optional<ReqReportEntity> optionalReqReportEntity = reqReportRepository.findByMemberEntityAndDebutCommentEntity(memberEntity, debutCommentEntity);
            if (optionalReqReportEntity.isPresent()) {
                return "신고내역있음";
            }
        }
        return null;
    }
}
