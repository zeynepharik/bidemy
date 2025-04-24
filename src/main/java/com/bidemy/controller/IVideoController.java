package com.bidemy.controller;

import com.bidemy.model.dto.VideoDTO;

import java.util.List;

public interface IVideoController {
    VideoDTO create(VideoDTO videoDTO);
    VideoDTO getById(Long id);
    List<VideoDTO> getAll();
    VideoDTO update(Long id, VideoDTO videoDTO);
    void delete(Long id);
}
