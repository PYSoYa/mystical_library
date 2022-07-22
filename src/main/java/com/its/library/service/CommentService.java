package com.its.library.service;

import com.its.library.dto.CommentDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.CommentEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.CommentRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<CommentDTO> commentSave(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(commentDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(commentDTO.getEpisodeId());
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        List<CommentEntity> commentEntityList = new ArrayList<>();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        if(optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()){
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
        }
        commentRepository.save(CommentEntity.saveEntity(commentDTO, memberEntity, episodeEntity));

        commentEntityList = commentRepository.findByEpisodeEntity(episodeEntity);
        for (CommentEntity comment: commentEntityList){
            commentDTOList.add(CommentDTO.findDTO(comment));
        }
        System.out.println("commentDTOList = " + commentDTOList);
        return commentDTOList;
    }

    public List<CommentDTO> commentList(Long id) {
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        EpisodeEntity episodeEntity = new EpisodeEntity();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (optionalEpisodeEntity.isPresent()){
            episodeEntity = optionalEpisodeEntity.get();
            List<CommentEntity> commentEntityList = commentRepository.findByEpisodeEntity(episodeEntity);
            for (CommentEntity comment: commentEntityList){
                commentDTOList.add(CommentDTO.findDTO(comment));
            }
            return commentDTOList;
        } else {
            return null;
        }

    }
}
