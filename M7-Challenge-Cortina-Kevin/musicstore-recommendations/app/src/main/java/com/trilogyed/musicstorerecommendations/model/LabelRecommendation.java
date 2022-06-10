package com.trilogyed.musicstorerecommendations.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="label_recommendation")
public class LabelRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="label_recommendation_id")
    private Integer labelRecommendationId;

    @Column(name="label_id")
    @NotNull
    private Integer labelId;

    @Column(name="user_id")
    @NotNull
    private Integer userId;

    @Column(name="liked")
    @NotNull
    private Boolean liked;

    public LabelRecommendation() {
    }

    public LabelRecommendation(Integer labelRecommendationId, Integer labelId, Integer userId, Boolean liked) {
        this.labelRecommendationId = labelRecommendationId;
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getLabelRecommendationId() {
        return labelRecommendationId;
    }

    public void setLabelRecommendationId(Integer labelRecommendationId) {
        this.labelRecommendationId = labelRecommendationId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelRecommendation that = (LabelRecommendation) o;
        return labelRecommendationId.equals(that.labelRecommendationId) && labelId.equals(that.labelId) && userId.equals(that.userId) && liked.equals(that.liked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelRecommendationId, labelId, userId, liked);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "LabelRecommendation{" +
                "labelRecommendationId=" + labelRecommendationId +
                ", labelId=" + labelId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
