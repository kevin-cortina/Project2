package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private TrackRecommendationRepository repo;

    private TrackRecommendation trackRecommendation;

    private String trackRecommendationJson;

    private List<TrackRecommendation> allTrackRecommendation = new ArrayList<>();

    private String allTrackRecommendationsJson;

    @Before
    public void setup() throws Exception {
        this.trackRecommendation = new TrackRecommendation();
        this.trackRecommendation.setTrackRecommendationId(1);
        this.trackRecommendation.setTrackId(1);
        this.trackRecommendation.setUserId(1);
        this.trackRecommendation.setLiked(Boolean.TRUE);
        this.trackRecommendationJson = this.mapper.writeValueAsString(this.trackRecommendation);

        TrackRecommendation trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackRecommendationId(2);
        trackRecommendation.setTrackId(2);
        trackRecommendation.setUserId(2);
        trackRecommendation.setLiked(Boolean.TRUE);

        this.allTrackRecommendation.add(this.trackRecommendation);
        this.allTrackRecommendation.add(trackRecommendation);
        this.allTrackRecommendationsJson = this.mapper.writeValueAsString(this.allTrackRecommendation);
    }

    @Test
    public void shouldReturnNewTrackRecommendationOnPostRequest() throws Exception {
        TrackRecommendation inputTrack = new TrackRecommendation();
        inputTrack.setTrackId(3);
        inputTrack.setTrackRecommendationId(3);
        inputTrack.setLiked(Boolean.TRUE);
        inputTrack.setUserId(3);

        TrackRecommendation outputTrack = new TrackRecommendation();
        outputTrack.setTrackId(3);
        outputTrack.setTrackRecommendationId(3);
        outputTrack.setLiked(Boolean.TRUE);
        outputTrack.setUserId(3);

        Mockito.doReturn(inputTrack).when(this.repo).save(outputTrack);

        this.mockMvc.perform(
                        post("/trackRecommendation")
                                .content(mapper.writeValueAsString(inputTrack))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputTrack)));
    }

    @Test
    public void shouldReturnTrackRecommendationById() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation();
        inputTrackRecommendation.setTrackId(1);
        inputTrackRecommendation.setTrackRecommendationId(1);
        inputTrackRecommendation.setUserId(1);
        inputTrackRecommendation.setLiked(Boolean.TRUE);

        Mockito.doReturn(Optional.of(inputTrackRecommendation)).when(this.repo).findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.trackRecommendationJson)));
    }

    @Test
    public void shouldReturnAllTrackRecommendations() throws Exception {
        Mockito.doReturn(this.allTrackRecommendation).when(this.repo).findAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRecommendation"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allTrackRecommendationsJson));
    }

    @Test
    public void shouldUpdateTrackRecommendationsByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/trackRecommendation/1")
                        .content(this.trackRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnTrackByIdAndReturn404StatusCode() throws Exception {
        Mockito.doReturn(Optional.empty()).when(this.repo).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRecommendation/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
