package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AlbumRecommendationRepository repo;
    private AlbumRecommendation albumRecommendation;
    private String albumRecommendationJson;
    private List<AlbumRecommendation> allAlbumRecommendation = new ArrayList();
    private String allAlbumRecommendationsJson;

    @Before
    public void setup() throws Exception {
    this.albumRecommendation = new AlbumRecommendation();
    this.albumRecommendation.setAlbumId(1);
    this.albumRecommendation.setAlbumRecommendationId(1);
    this.albumRecommendation.setUserId(1);
    this.albumRecommendation.setLiked(Boolean.TRUE);
    this.albumRecommendationJson = this.mapper.writeValueAsString(this.albumRecommendation);

    AlbumRecommendation albumRecommendation = new AlbumRecommendation();
    albumRecommendation.setAlbumRecommendationId(2);
    albumRecommendation.setAlbumId(2);
    albumRecommendation.setUserId(2);
    albumRecommendation.setLiked(Boolean.TRUE);

    this.allAlbumRecommendation.add(this.albumRecommendation);
    this.allAlbumRecommendation.add(albumRecommendation);
    this.allAlbumRecommendationsJson = this.mapper.writeValueAsString(this.allAlbumRecommendation);
    }

    @Test
    public void shouldReturnNewAlbumRecommendationOnPostRequest() throws Exception {
        AlbumRecommendation inputAlbum = new AlbumRecommendation();
        inputAlbum.setAlbumId(3);
        inputAlbum.setAlbumRecommendationId(3);
        inputAlbum.setLiked(Boolean.TRUE);
        inputAlbum.setUserId(3);

        AlbumRecommendation outputAlbum = new AlbumRecommendation();
        outputAlbum.setAlbumId(3);
        outputAlbum.setAlbumRecommendationId(3);
        outputAlbum.setLiked(Boolean.TRUE);
        outputAlbum.setUserId(3);

        Mockito.doReturn(inputAlbum).when(this.repo).save(outputAlbum);

        this.mockMvc.perform(
                        post("/albumRecommendation")
                                .content(mapper.writeValueAsString(inputAlbum))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputAlbum)));
    }

    @Test
    public void shouldReturnAlbumRecommendationById() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation();
        inputAlbumRecommendation.setAlbumId(1);
        inputAlbumRecommendation.setAlbumRecommendationId(1);
        inputAlbumRecommendation.setUserId(1);
        inputAlbumRecommendation.setLiked(Boolean.TRUE);

        Mockito.doReturn(Optional.of(inputAlbumRecommendation)).when(this.repo).findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.albumRecommendationJson)));
    }

    @Test
    public void shouldReturnAllAlbumRecommendations() throws Exception {
        Mockito.doReturn(this.allAlbumRecommendation).when(this.repo).findAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRecommendation"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allAlbumRecommendationsJson));
    }

    @Test
    public void shouldUpdateAlbumRecommendationsByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/albumRecommendation/1")
                        .content(this.albumRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/albumRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnAlbumByIdAndReturn404StatusCode() throws Exception {
        Mockito.doReturn(Optional.empty()).when(this.repo).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRecommendation/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
