package com.trilogyed.musicstorerecommendations.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="artist_recommendation")
public class ArtistRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="artist_recommendation_id")
    private Integer artistRecommendationId;

    @Column(name="artist_id")
    @NotNull
    private Integer artistId;

    @Column(name="user_id")
    @NotNull
    private Integer userId;

    @Column(name="liked")
    @NotNull
    private Boolean liked;

    public ArtistRecommendation() {
    }

    public ArtistRecommendation(Integer artistRecommendationId, Integer artistId, Integer userId, Boolean liked) {
        this.artistRecommendationId = artistRecommendationId;
        this.artistId = artistId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getArtistRecommendationId() {
        return artistRecommendationId;
    }

    public void setArtistRecommendationId(Integer artistRecommendationId) {
        this.artistRecommendationId = artistRecommendationId;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
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
        ArtistRecommendation that = (ArtistRecommendation) o;
        return Objects.equals(artistRecommendationId, that.artistRecommendationId) && Objects.equals(artistId, that.artistId) && Objects.equals(userId, that.userId) && Objects.equals(liked, that.liked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistRecommendationId, artistId, userId, liked);
    }

    @Override
    public String toString() {
        return "ArtistRecommendation{" +
                "artistRecommendationId=" + artistRecommendationId +
                ", artistId=" + artistId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
