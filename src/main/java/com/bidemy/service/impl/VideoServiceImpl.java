package com.bidemy.service.impl;

import com.bidemy.exception.BusinessValidationException;
import com.bidemy.exception.BusinessValidationRule;
import com.bidemy.mapper.VideoMapper;
import com.bidemy.model.dto.VideoDTO;
import com.bidemy.model.entity.Category;
import com.bidemy.model.entity.Video;
import com.bidemy.repository.VideoRepository;
import com.bidemy.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements IVideoService {

    private final VideoMapper videoMapper;
    private final VideoRepository videoRepository;

    @Override
    public VideoDTO create(VideoDTO videoDTO) {
        Video video = videoMapper.toEntity(videoDTO);
        video = videoRepository.save(video);
        return videoMapper.toDto(video);
    }

    @Override
    public VideoDTO getById(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.VİDEO_NOT_FOUND));
        return videoMapper.toDto(video);
    }

    @Override
    public List<VideoDTO> getAll() {
        List<Video> videos = videoRepository.findAll();
        List<VideoDTO> videoDTOS = new ArrayList<>();
        for (Video video : videos) {
            videoDTOS.add(videoMapper.toDto(video));
        }
        return videoDTOS;
    }

    @Override
    public VideoDTO update(Long id, VideoDTO videoDTO) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.VİDEO_NOT_FOUND));
        video.setTitle(videoDTO.getTitle());
        video.setDuration(videoDTO.getDuration());
        video.setCategory(new Category(videoDTO.getCategoryId(), null));
        video = videoRepository.save(video);
        return videoMapper.toDto(video);
    }

    @Override
    public void delete(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new BusinessValidationException(BusinessValidationRule.VİDEO_NOT_FOUND));
        videoRepository.delete(video);
    }
}
