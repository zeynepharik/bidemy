package com.bidemy.service;


import com.bidemy.model.dto.CourseDTO;
import com.bidemy.model.request.CourseRequest;
import com.bidemy.model.response.CourseResponse;

import java.io.IOException;
import java.util.List;

public interface ICourseService {
   CourseResponse create(CourseRequest request);

   CourseResponse getById(Long id);

   List<CourseResponse> getAll();

   CourseResponse update(Long id, CourseRequest request) throws IOException;

   void delete(Long id);

   void publishCourse(Long courseId);
}
