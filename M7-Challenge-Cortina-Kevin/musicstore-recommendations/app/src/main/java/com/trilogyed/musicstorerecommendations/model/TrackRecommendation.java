package com.trilogyed.musicstorerecommendations.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="track_recommendation")
public class TrackRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="track_recommendation_id")
    private Integer trackRecommendationId;

    @Column(name="track_id")
    @NotNull
    private Integer trackId;

    @Column(name="user_id")
    @NotNull
    private Integer userId;

    @Column(name="liked")
    @NotNull
    private Boolean liked;

    public TrackRecommendation() {
    }

    public TrackRecommendation(Integer trackRecommendationId, Integer trackId, Integer userId, Boolean liked) {
        this.trackRecommendationId = trackRecommendationId;
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getTrackRecommendationId() {
        return trackRecommendationId;
    }

    public void setTrackRecommendationId(Integer trackRecommendationId) {
        this.trackRecommendationId = trackRecommendationId;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public Integer getUserId() {
        return userId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackRecommendation that = (TrackRecommendation) o;
        return trackRecommendationId.equals(that.trackRecommendationId) && trackId.equals(that.trackId) && userId.equals(that.userId) && liked.equals(that.liked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackRecommendationId, trackId, userId, liked);
    }

    @Override
    public String toString() {
        return "TrackRecommendation{" +
                "trackRecommendationId=" + trackRecommendationId +
                ", trackId=" + trackId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
