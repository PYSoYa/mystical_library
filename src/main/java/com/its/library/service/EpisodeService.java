package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.WishRepository;
import com.sun.source.tree.LambdaExpressionTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final NoticeService noticeService;
    private final WishRepository wishRepository;
    private final BookRepository bookRepository;


    public List<EpisodeDTO> findAll() {
        List<EpisodeEntity> episodeEntityList = episodeRepository.findAll();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        for (EpisodeEntity episodeEntity: episodeEntityList) {
            EpisodeEntity episodeEntity1 = episodeEntity;
           EpisodeDTO episodeDTO =  EpisodeDTO.findDTO(episodeEntity1);
           episodeDTOList.add(episodeDTO);
        }
        return episodeDTOList;
    }

    public void episodeAgree(Long id) {
       Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
       if (optionalEpisodeEntity.isPresent()){
           EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
           episodeEntity.setWriterRole(1);
           episodeRepository.save(episodeEntity);
           noticeService.save(id);
       }
    }

    public void episodeDelete(Long id) {
        episodeRepository.deleteById(id);
    }

    public List<EpisodeDTO> episodeFindAll(Long bookId) {
        List<EpisodeEntity> episodeEntityList = new ArrayList<>();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        episodeEntityList = episodeRepository.findByBookId(bookId);
        for (EpisodeEntity episode: episodeEntityList) {
            episodeDTOList.add(EpisodeDTO.findDTO(episode));
        }
        return episodeDTOList;
    }

    public Long findBook(Long id) {
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(id);
        if (optionalEpisodeEntity.isPresent()) {
            EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
           Optional<BookEntity> optionalBookEntity = bookRepository.findById(episodeEntity.getBookEntity().getId());
           if (optionalBookEntity.isPresent()) {
               BookEntity bookEntity = optionalBookEntity.get();
               BookDTO bookDTO = BookDTO.findDTO(bookEntity);
               return bookDTO.getId();
           }
        } else {
            return null;
        }
        return null;
    }
}
