package com.bidemy.service;

import com.bidemy.model.dto.VideoDTO;

import java.util.List;

public interface IVideoService {
    VideoDTO create(VideoDTO videoDTO);
    VideoDTO getById(Long id);
    List<VideoDTO> getAll();
    VideoDTO update(Long id, VideoDTO videoDTO);
    void delete(Long id);

}
