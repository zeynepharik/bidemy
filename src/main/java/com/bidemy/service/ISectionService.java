package com.bidemy.service;

import com.bidemy.model.request.SectionRequest;
import com.bidemy.model.response.SectionResponse;

import java.util.List;

public interface ISectionService {
    SectionResponse createSection(SectionRequest request);

    SectionResponse getSectionById(Long id);

    List<SectionResponse> getAllSectionByCourseId(Long courseId);

    SectionResponse updateSection(Long id, SectionRequest request);

    void deleteSection(Long id);
}
