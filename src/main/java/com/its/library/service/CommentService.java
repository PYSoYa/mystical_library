package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.CommentDTO;
import com.its.library.entity.*;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberRepository memberRepository;
    private final ReqReportRepository reqReportRepository;

    public List<CommentDTO> commentSave(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(commentDTO.getEpisodeId());
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        List<CommentEntity> commentEntityList = new ArrayList<>();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
        }
        commentRepository.save(CommentEntity.saveEntity(commentDTO, memberEntity, episodeEntity));

        commentEntityList = commentRepository.findByEpisodeId(episodeEntity.getId());
        for (CommentEntity comment : commentEntityList) {
            commentDTOList.add(CommentDTO.findDTO(comment));
        }
        return commentDTOList;
    }

    public List<CommentDTO> bookCommentList(Long id) {
        List<CommentEntity> commentEntityList = commentRepository.findByBookId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity comment : commentEntityList) {
            commentDTOList.add(CommentDTO.findDTO(comment));
        }
        return commentDTOList;
    }

    public List<CommentDTO> commentList(Long id) {
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        EpisodeEntity episodeEntity = new EpisodeEntity();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (optionalEpisodeEntity.isPresent()) {
            episodeEntity = optionalEpisodeEntity.get();
            List<CommentEntity> commentEntityList = commentRepository.findByEpisodeId(episodeEntity.getId());
            for (CommentEntity comment : commentEntityList) {
                commentDTOList.add(CommentDTO.findDTO(comment));
            }
            return commentDTOList;
        } else {
            return null;
        }

    }

    public List<CommentDTO> commentDelete(Long id, Long episodeId) {
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        EpisodeEntity episodeEntity = new EpisodeEntity();
        CommentEntity commentEntity = new CommentEntity();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        if (optionalCommentEntity.isPresent()) {
            commentEntity = optionalCommentEntity.get();
            commentRepository.delete(commentEntity);
        } else {
            return null;
        }
        if (optionalEpisodeEntity.isPresent()) {
            episodeEntity = optionalEpisodeEntity.get();
            List<CommentEntity> commentEntityList = commentRepository.findByEpisodeEntity(episodeEntity);
            for (CommentEntity comment : commentEntityList) {
                commentDTOList.add(CommentDTO.findDTO(comment));
            }
            return commentDTOList;
        } else {
            return null;
        }

    }

    public String reportSave(Long id, Long loginId, String contents) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(loginId);
        CommentEntity commentEntity = new CommentEntity();
        MemberEntity memberEntity = new MemberEntity();
        CommentDTO commentDTO = new CommentDTO();
        if (optionalCommentEntity.isPresent() && optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            commentEntity = optionalCommentEntity.get();
            reqReportRepository.save(ReqReportEntity.saveEntity(memberEntity, commentEntity));
            commentDTO = CommentDTO.findDTO(commentEntity);
            if (commentDTO != null) {
                return "ok";
            } else {
                return "no";
            }
        } else {
            return null;
        }
    }

    public List<CommentDTO> update(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        List<CommentEntity> commentEntityList = new ArrayList<>();
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentDTO.getId());
        if (optionalCommentEntity.isPresent()) {
            commentEntity = optionalCommentEntity.get();
            commentEntity.setContents(commentDTO.getContents());
            commentRepository.save(commentEntity);
        }
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(commentDTO.getEpisodeId());
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalEpisodeEntity.isPresent()) {
            episodeEntity = optionalEpisodeEntity.get();
            commentEntityList = commentRepository.findByEpisodeId(episodeEntity.getId());
            for (CommentEntity comment : commentEntityList) {
                commentDTOList.add(CommentDTO.findDTO(comment));
            }
            return commentDTOList;
        } else {
            return null;
        }
    }
}
