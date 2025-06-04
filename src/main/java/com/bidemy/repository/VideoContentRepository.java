package com.bidemy.repository;

import com.bidemy.model.entity.VideoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoContentRepository extends JpaRepository<VideoContent,Long> {
}
