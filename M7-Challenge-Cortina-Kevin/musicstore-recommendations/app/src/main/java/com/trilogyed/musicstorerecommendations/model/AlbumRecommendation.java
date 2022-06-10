package com.trilogyed.musicstorerecommendations.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="album_recommendation")
public class AlbumRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_recommendation_id")
    private Integer albumRecommendationId;

    @Column(name = "album_id")
    @NotNull
    private Integer albumId;

    @Column(name ="user_id")
    @NotNull
    private Integer userId;

    @Column(name="liked")
    @NotNull
    private Boolean liked;

    public AlbumRecommendation(Integer albumRecommendationId, Integer albumId, Integer userId, Boolean liked) {
        this.albumRecommendationId = albumRecommendationId;
        this.albumId = albumId;
        this.userId = userId;
        this.liked = liked;
    }

    public AlbumRecommendation() {

    }

    public Integer getAlbumRecommendationId() {
        return albumRecommendationId;
    }

    public void setAlbumRecommendationId(Integer albumRecommendationId) {
        this.albumRecommendationId = albumRecommendationId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
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
        AlbumRecommendation that = (AlbumRecommendation) o;
        return Objects.equals(albumRecommendationId, that.albumRecommendationId) && Objects.equals(albumId, that.albumId) && Objects.equals(userId, that.userId) && Objects.equals(liked, that.liked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumRecommendationId, albumId, userId, liked);
    }

    @Override
    public String toString() {
        return "AlbumRecommendation{" +
                "albumRecommendationId=" + albumRecommendationId +
                ", albumId=" + albumId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
