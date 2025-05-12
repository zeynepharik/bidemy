package com.bidemy.controller.impl;

import com.bidemy.controller.IVideoController;
import com.bidemy.model.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoControllerImpl implements IVideoController {

    private final IVideoService videoService;

    @PostMapping
    @Override
    public VideoDTO create(@RequestBody VideoDTO videoDTO) {
        return videoService.create(videoDTO);
    }

    @GetMapping("/{id}")
    @Override
    public VideoDTO getById(@PathVariable Long id) {
        return videoService.getById(id);
    }

    @GetMapping
    @Override
    public List<VideoDTO> getAll() {
        return videoService.getAll();
    }

    @PutMapping("/{id}")
    @Override
    public VideoDTO update(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        return videoService.update(id,videoDTO);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        videoService.delete(id);
    }
}
