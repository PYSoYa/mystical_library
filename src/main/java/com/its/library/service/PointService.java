package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.dto.PointDTO;
import com.its.library.entity.*;
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
    private final MemberRepository memberRepository;


    public List<PointDTO> pointHistory() {
        List<PointEntity> pointEntityList = pointRepository.findAll();
        List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity pointEntity : pointEntityList) {
            PointEntity pointEntity1 = pointEntity;
            Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(pointEntity1.getEpisodeEntity().getId());
            if (optionalEpisodeEntity.isPresent()) {
                EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
                if (optionalBookEntity.isPresent()) {
                    BookEntity bookEntity = optionalBookEntity.get();
                    pointDTOList.add(PointDTO.findDTO(pointEntity1, episodeEntity, bookEntity));
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
                if (optionalBookEntity.isPresent()) {
                    BookEntity bookEntity = optionalBookEntity.get();
                    pointDTOList.add(PointDTO.findDTO(pointEntity1, episodeEntity, bookEntity));
                }


            }
        }
        return pointDTOList;
    }

    @Transactional
    public String pointPay(Long memberId, Long episodeId, Long bookId) {
        List<PointEntity> pointEntityList = pointRepository.findByMemberEntity_IdOrderByIdDesc(memberId);
        System.out.println("pointEntityList = " + pointEntityList);
        PointEntity pointEntity = new PointEntity();
        for (int i=0;i==0;i++){
         pointEntity =pointEntityList.get(i);
            System.out.println("pointEntity = " + pointEntity);
        }
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);

        if (optionalEpisodeEntity.isPresent()) {
            EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
            if (optionalMemberEntity.isPresent() & optionalBookEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                BookEntity bookEntity = optionalBookEntity.get();
            if (pointEntity.getTotalPoint() >= episodeEntity.getPrice()) {
                    PointEntity pointEntity1 = PointEntity.update(pointEntity, episodeEntity, memberEntity, bookEntity);
                    pointRepository.save(pointEntity1);
                   MemberEntity member =MemberEntity.pointPay(memberEntity, pointEntity1);
                   memberRepository.save(member);
                    return "ok";
                } else {
                    return "no";
                }
            } else {
                return "no";
            }
        } else {
            return "no";
        }

    }
}
