package com.trilogyed.musicstorerecommendations.repository;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Integer> {
}
