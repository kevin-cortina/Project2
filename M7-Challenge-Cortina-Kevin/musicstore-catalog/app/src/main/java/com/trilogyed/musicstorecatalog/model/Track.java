package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="track_id")
    private Integer trackId;

    @Column(name="album_id")
    private Integer albumId;

    @Column(name="title")
    private String title;

    @Column(name="run_time")
    private String runTime;

    public Track() {
    }

    public Track(Integer trackId, Integer albumId, String title, String runTime) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.title = title;
        this.runTime = runTime;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(trackId, track.trackId) && Objects.equals(albumId, track.albumId) && Objects.equals(title, track.title) && Objects.equals(runTime, track.runTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, albumId, title, runTime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runTime='" + runTime + '\'' +
                '}';
    }


}
