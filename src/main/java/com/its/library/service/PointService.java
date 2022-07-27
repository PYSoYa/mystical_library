package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.entity.BaseEntity;
import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.PointEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final EpisodeRepository episodeRepository;
    private final BookRepository bookRepository;


    public List<PointDTO> pointHistory() {
        List<PointEntity> pointEntityList = pointRepository.findAll();
        List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity pointEntity : pointEntityList) {
            PointEntity pointEntity1 = pointEntity;
            Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(pointEntity1.getEpisodeEntity().getId());
            if (optionalEpisodeEntity.isPresent()) {
                EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
               Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
               if (optionalBookEntity.isPresent()){
                   BookEntity bookEntity = optionalBookEntity.get();
                   pointDTOList.add(PointDTO.findDTO(pointEntity1, episodeEntity,bookEntity));
               }


            }

        }
        return pointDTOList;
    }

    @Transactional
    public List<PointDTO> findPointList(MemberDTO memberDTO) {
        List<PointEntity> pointEntityList = pointRepository.findByMemberEntity_Id(memberDTO.getId());
        List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity pointEntity : pointEntityList) {
            PointEntity pointEntity1 = pointEntity;
            Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(pointEntity1.getEpisodeEntity().getId());
            if (optionalEpisodeEntity.isPresent()) {
                EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
                if (optionalBookEntity.isPresent()){
                    BookEntity bookEntity = optionalBookEntity.get();
                    pointDTOList.add(PointDTO.findDTO(pointEntity1, episodeEntity,bookEntity));
                }


            }
        }
        return pointDTOList;
    }




}
