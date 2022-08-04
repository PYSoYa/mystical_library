package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.HistoryEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.HistoryRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final EpisodeRepository episodeRepository;

    public String historyUpdate(HistoryDTO historyDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(historyDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(historyDTO.getEpisodeId());
        MemberEntity memberEntity = new MemberEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            historyEntity = HistoryEntity.updateEntity(historyDTO, memberEntity, episodeEntity);
            historyRepository.save(historyEntity);
            return "저장완료";
        } else {
            return null;
        }

    }

    public List<BookDTO> list(Long id) {
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        List<HistoryDTO> historyDTOList = new ArrayList<>();
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
        }
        List<BookDTO> bookDTOList = new ArrayList<>();
        List<Long> list = historyRepository.findByMemberId(memberEntity.getId());
        for (int i = 0; i < list.size(); i++) {
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(list.get(i));
            if (optionalBookEntity.isPresent()) {
                bookEntity = optionalBookEntity.get();
                historyEntityList = historyRepository.findByBooKId(bookEntity.getId());
                historyDTOList.add(HistoryDTO.findDTO(historyEntityList.get(0)));
            }
        }
        for (int i = 0; i < historyDTOList.size(); i++) {
            if (historyDTOList.get(i).getHidden() == 0) {
                bookDTOList.add(BookDTO.findDTO(bookEntity));
            }
        }
        return bookDTOList;
    }

    public String hidden(HistoryDTO historyDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(historyDTO.getMemberId());
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(historyDTO.getBookId());
        MemberEntity memberEntity = new MemberEntity();
        BookEntity bookEntity = new BookEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        if (optionalMemberEntity.isPresent() && optionalBookEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            bookEntity = optionalBookEntity.get();

            List<HistoryEntity> historyEntityList = historyRepository.findByMemberEntityAndBooKId(memberEntity, bookEntity.getId());
            for (int i = 0; i < historyEntityList.size(); i++) {
                historyRepository.deleteById(historyEntityList.get(i).getId());
            }
            return "숨기기";
        } else {
            return null;
        }
    }

    public Long findByHistory(HistoryDTO historyDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(historyDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(historyDTO.getEpisodeId());
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            Optional<HistoryEntity> optionalHistoryEntity = historyRepository.findByMemberEntityAndEpisodeEntity(memberEntity, episodeEntity);
            if (optionalHistoryEntity.isPresent()) {
                historyEntity = optionalHistoryEntity.get();
                if (historyEntity != null) {
                    Long historyId = historyEntity.getId();
                    return historyId;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public List<HistoryDTO> findByBookId(Long bookId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        MemberEntity memberEntity = new MemberEntity();
        List<HistoryDTO> historyDTOList = new ArrayList<>();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            List<HistoryEntity> historyEntityList = historyRepository.findByMemberEntityAndBooKId(memberEntity, bookId);
            for (HistoryEntity history: historyEntityList) {
                historyDTOList.add(HistoryDTO.findDTO(history));
            }
            return historyDTOList;
        } else {
            return null;
        }
    }
}
