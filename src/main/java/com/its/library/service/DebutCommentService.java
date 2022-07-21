package com.its.library.service;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.DebutEpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.DebutCommentRepository;
import com.its.library.repository.DebutRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebutCommentService {
    private final DebutCommentRepository debutCommentRepository;
    private final MemberRepository memberRepository;
    private final DebutRepository debutRepository;

    //댓글 저장 후 댓글 목록 조회 처리
    public List<DebutCommentDTO> save(DebutCommentDTO debutCommentDTO) {
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(debutCommentDTO.getDebutId());
        DebutEpisodeEntity debutEpisodeEntity = new DebutEpisodeEntity();
        if (optionalDebutEpisodeEntity.isPresent()) {
            debutEpisodeEntity = optionalDebutEpisodeEntity.get();

        }
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(debutCommentDTO.getMemberId());
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
        }

        DebutCommentEntity debutCommentEntity = DebutCommentEntity.toSave(debutCommentDTO, memberEntity, debutEpisodeEntity);
        debutCommentRepository.save(debutCommentEntity);
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity1 = debutRepository.findById(debutCommentDTO.getDebutId());
        List<DebutCommentDTO> debutCommentDTOList = new ArrayList<>();

        if (optionalDebutEpisodeEntity1.isPresent()) {
            DebutEpisodeEntity debutEpisodeEntity1 = optionalDebutEpisodeEntity1.get();
            List<DebutCommentEntity> debutCommentEntityList = debutEpisodeEntity1.getDebutCommentEntityList();
            for (DebutCommentEntity debutComment : debutCommentEntityList) {
                DebutCommentDTO debutCommentDTO1 = DebutCommentDTO.toDTO(debutComment);
                debutCommentDTOList.add(debutCommentDTO1);
            }
            return debutCommentDTOList;
        } else {
            return null;

        }
    }

    //아이디 찾기
    public List<DebutCommentDTO> findById(Long id) {
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity1 = debutRepository.findById(id);
        List<DebutCommentDTO> debutCommentDTOList = new ArrayList<>();

        if (optionalDebutEpisodeEntity1.isPresent()) {
            DebutEpisodeEntity debutEpisodeEntity1 = optionalDebutEpisodeEntity1.get();
            List<DebutCommentEntity> debutCommentEntityList = debutEpisodeEntity1.getDebutCommentEntityList();
            for (DebutCommentEntity debutComment : debutCommentEntityList) {
                DebutCommentDTO debutCommentDTO1 = DebutCommentDTO.toDTO(debutComment);
                debutCommentDTOList.add(debutCommentDTO1);
            }
            return debutCommentDTOList;
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        debutCommentRepository.deleteById(id);
    }
}
