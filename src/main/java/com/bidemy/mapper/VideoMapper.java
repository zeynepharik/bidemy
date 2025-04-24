package com.bidemy.mapper;


import com.bidemy.model.dto.VideoDTO;
import com.bidemy.model.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoDTO toDto(Video video);

    Video toEntity(VideoDTO videoDTO);
}
