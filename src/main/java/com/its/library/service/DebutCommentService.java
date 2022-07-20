package com.its.library.service;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.repository.DebutCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebutCommentService {
    private final DebutCommentRepository debutCommentRepository;
    public List<DebutCommentDTO> save(DebutCommentDTO debutCommentDTO) {
       DebutCommentEntity debutCommentEntity = DebutCommentEntity.toSave(debutCommentDTO);
       debutCommentRepository.save(debutCommentEntity);
      List<DebutCommentEntity> debutCommentEntityList = debutCommentRepository.findAll();
      List<DebutCommentDTO> debutCommentDTOList = new ArrayList<>();
        for (DebutCommentEntity debutComment: debutCommentEntityList

             ) {DebutCommentDTO.toSave(debutComment);

        }



    }
}
