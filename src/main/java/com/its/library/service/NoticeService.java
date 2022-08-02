package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.NoticeDTO;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.NoticeEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;
    private final WishRepository wishRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public void save(EpisodeDTO episodeDTO, Long id) {
       Optional<MemberEntity> optionalMemberEntity =memberRepository.findById(id);
       Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeDTO.getId());
       if (optionalEpisodeEntity.isPresent()& optionalMemberEntity.isPresent()){
           MemberEntity memberEntity = optionalMemberEntity.get();
           EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
          Optional<WishEntity> optionalWishEntity = wishRepository.findByMemberNameAndBookEntity_Id(memberEntity.getMemberName(),episodeEntity.getBookEntity().getId());
          if (optionalWishEntity.isPresent()){
              WishEntity wishEntity = optionalWishEntity.get();
             NoticeEntity noticeEntity = NoticeEntity.save(memberEntity,episodeEntity,wishEntity);
             noticeRepository.save(noticeEntity);

          }

       }




    }

    @Transactional
    public List<NoticeDTO> noticeHistory(Long memberId) {
       List<NoticeEntity> noticeEntityList = noticeRepository.findAllByMemberEntity_IdAndNoticeReadIsFalse(memberId);
        for (NoticeEntity noticeEntity:noticeEntityList) {
            NoticeEntity noticeEntity1 =noticeEntity;
            noticeEntity1.setNoticeRead(true);
            List<NoticeEntity> noticeEntityList1 = new ArrayList<>();
            noticeEntityList1.add(noticeEntity1);
            noticeRepository.saveAll(noticeEntityList1);
        }
        List<NoticeEntity> noticeEntityList1=  noticeRepository.findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (NoticeEntity noticeEntity:noticeEntityList1) {
            NoticeEntity noticeEntity1= noticeEntity;
           NoticeDTO noticeDTO = NoticeDTO.save(noticeEntity1);
            noticeDTOList.add(noticeDTO);


        }
            return noticeDTOList;
    }
    @Transactional
    public boolean readFalseCount(Long memberId) {
       Optional<NoticeEntity> falseResult = noticeRepository.findByIdAndNoticeReadIsFalse(memberId);
       if (falseResult==null){
        return false;
       }
       return true;
    }
}

