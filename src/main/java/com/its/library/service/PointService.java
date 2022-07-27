package com.its.library.service;

import com.its.library.dto.PointDTO;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.PointEntity;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final EpisodeRepository episodeRepository;

    public List<PointDTO> pointHistory() {
       List<PointEntity> pointEntityList = pointRepository.findAll();
       List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity pointEntity:pointEntityList) {
             PointEntity pointEntity1 = pointEntity;
            Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(pointEntity1.getEpisodeEntity().getId());
            if (optionalEpisodeEntity.isPresent()){
                EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
                pointDTOList.add(PointDTO.findDTO(pointEntity1,episodeEntity));

            }

        }
        return pointDTOList;
    }
}
