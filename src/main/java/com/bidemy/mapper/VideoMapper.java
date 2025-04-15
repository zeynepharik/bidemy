package com.bidemy.mapper;


import com.bidemy.model.dto.VideoDTO;
import com.bidemy.model.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(source = "video.id", target = "videoId")
    VideoDTO toDTO(Video video);

    @Mapping(source = "videoId", target = "video.id")
    Video toEntity(VideoDTO videoDTO);
}
