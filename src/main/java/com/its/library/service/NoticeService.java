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
    private final BookRepository bookRepository;

    @Transactional
    public void save(Long id) {
        EpisodeEntity episodeEntity = episodeRepository.findById(id).get();
        List<WishEntity> wishEntityList = wishRepository.findByBookEntity_Id(episodeEntity.getBookEntity().getId());
        if (wishEntityList.size() != 0) {
            for (int i = 0; i < wishEntityList.size(); i++) {
                MemberEntity memberEntity = memberRepository.findByMemberName(wishEntityList.get(i).getMemberName()).get();
                NoticeEntity noticeEntity = new NoticeEntity();
                noticeEntity.setEpisodeTitle(episodeEntity.getEpisodeTitle());
                noticeEntity.setEpisodeEntity(episodeEntity);
                noticeEntity.setNoticeRead(false);
                noticeEntity.setMemberEntity(memberEntity);
                noticeEntity.setWishEntity(wishEntityList.get(i));
                noticeRepository.save(noticeEntity);
            }
        }
    }

    @Transactional
    public List<NoticeDTO> noticeHistory(Long memberId) {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAllByMemberEntity_IdAndNoticeReadIsFalse(memberId);
        for (NoticeEntity noticeEntity : noticeEntityList) {
            NoticeEntity noticeEntity1 = noticeEntity;
            noticeEntity1.setNoticeRead(true);
            List<NoticeEntity> noticeEntityList1 = new ArrayList<>();
            noticeEntityList1.add(noticeEntity1);
            noticeRepository.saveAll(noticeEntityList1);
        }
        List<NoticeEntity> noticeEntityList1 = noticeRepository.findByMemberEntity_Id(memberId);
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (NoticeEntity noticeEntity : noticeEntityList1) {
            NoticeEntity noticeEntity1 = noticeEntity;
            NoticeDTO noticeDTO = NoticeDTO.save(noticeEntity1);
            noticeDTOList.add(noticeDTO);
        }
        return noticeDTOList;
    }

    @Transactional
    public boolean readFalseCount(Long memberId) {
        List<NoticeEntity> noticeEntityList = noticeRepository.findByMemberEntity_IdAndNoticeReadIsFalse(memberId);
        if (noticeEntityList.size() <= 0) {
            return false;
        }
        return true;
    }

    public String deleteById(Long id) {
        noticeRepository.deleteById(id);
        return "삭제완료";
    }
}

