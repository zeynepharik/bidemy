package com.bidemy.repository;

import com.bidemy.model.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByLesson_Id(Long lessonId);
}
