package com.its.library.service;

import com.its.library.dto.BookDTO;
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

    public void historySave(HistoryDTO historyDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(historyDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(historyDTO.getEpisodeId());
        MemberEntity memberEntity = new MemberEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            historyEntity = HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity);
            historyRepository.save(historyEntity);
        }

    }

    public List<BookDTO> list(Long id) {
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        BookEntity bookEntity= new BookEntity();
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
        List<HistoryEntity> historyEntityList = historyRepository.findByBooKId(historyDTO.getBookId());
        for (int i = 0; i < historyEntityList.size(); i++) {
            historyEntityList.get(i).setHidden(historyDTO.getHidden());
            historyRepository.save(historyEntityList.get(i));
        }
        return "숨기기";
    }

    public void findByWishListBook(Long id) {

    }
}
