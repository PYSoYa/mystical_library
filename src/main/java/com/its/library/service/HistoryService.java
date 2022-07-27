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
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
        }
        List<HistoryEntity> historyEntityList = historyRepository.findByMemberEntity(memberEntity);
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (int i = 0; i < historyEntityList.size(); i++) {
            Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(historyEntityList.get(i).getEpisodeEntity().getId());
            if (optionalEpisodeEntity.isPresent()) {
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(optionalEpisodeEntity.get().getBookEntity().getId());
                if (optionalBookEntity.isPresent()) {
                    bookDTOList.add(BookDTO.findDTO(optionalBookEntity.get()));
                    }
                }
            }
        for (int j = 1; j < bookDTOList.size(); j++){
            if (bookDTOList.get(j).getId().equals(optionalBookEntity.get().getId())) {
                bookDTOList.remove(j);
            }
        }
        return bookDTOList;
    }
}
