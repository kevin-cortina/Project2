package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private ArtistRecommendationRepository repo;

    private ArtistRecommendation artistRecommendation;

    private String artistRecommendationJson;

    private List<ArtistRecommendation> allArtistRecommendation = new ArrayList<>();

    private String allArtistRecommendationsJson;


    @Before
    public void setup() throws Exception {
        this.artistRecommendation = new ArtistRecommendation();
        this.artistRecommendation.setArtistRecommendationId(1);
        this.artistRecommendation.setArtistId(1);
        this.artistRecommendation.setUserId(1);
        this.artistRecommendation.setLiked(Boolean.TRUE);
        this.artistRecommendationJson = this.mapper.writeValueAsString(this.artistRecommendation);

        ArtistRecommendation artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistRecommendationId(2);
        artistRecommendation.setArtistId(2);
        artistRecommendation.setUserId(2);
        artistRecommendation.setLiked(Boolean.TRUE);

        this.allArtistRecommendation.add(this.artistRecommendation);
        this.allArtistRecommendation.add(artistRecommendation);
        this.allArtistRecommendationsJson = this.mapper.writeValueAsString(this.allArtistRecommendation);
    }

    @Test
    public void shouldReturnNewArtistRecommendationOnPostRequest() throws Exception {
        ArtistRecommendation inputArtist = new ArtistRecommendation();
        inputArtist.setArtistId(3);
        inputArtist.setArtistRecommendationId(3);
        inputArtist.setLiked(Boolean.TRUE);
        inputArtist.setUserId(3);

        ArtistRecommendation outputArtist = new ArtistRecommendation();
        outputArtist.setArtistId(3);
        outputArtist.setArtistRecommendationId(3);
        outputArtist.setLiked(Boolean.TRUE);
        outputArtist.setUserId(3);

        ((ArtistRecommendationRepository) Mockito.doReturn(inputArtist).when(this.repo)).save(outputArtist);

        this.mockMvc.perform(
                        post("/artistRecommendation")
                                .content(mapper.writeValueAsString(inputArtist))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputArtist)));
    }

    @Test
    public void shouldReturnArtistRecommendationById() throws Exception {
        ArtistRecommendation inputAlbumRecommendation = new ArtistRecommendation();
        inputAlbumRecommendation.setArtistId(1);
        inputAlbumRecommendation.setArtistRecommendationId(1);
        inputAlbumRecommendation.setUserId(1);
        inputAlbumRecommendation.setLiked(Boolean.TRUE);

        Mockito.doReturn(Optional.of(inputAlbumRecommendation)).when(this.repo).findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.artistRecommendationJson)));
    }

    @Test
    public void shouldReturnAllArtistRecommendations() throws Exception {
        Mockito.doReturn(this.allArtistRecommendation).when(this.repo).findAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRecommendation"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allArtistRecommendationsJson));
    }

    @Test
    public void shouldUpdateArtistRecommendationsByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/artistRecommendation/1")
                        .content(this.artistRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnAlbumByIdAndReturn404StatusCode() throws Exception {
        ((ArtistRecommendationRepository) Mockito.doReturn(Optional.empty()).when(this.repo)).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRecommendation/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
