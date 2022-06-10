package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private LabelRecommendationRepository repo;

    private LabelRecommendation labelRecommendation;

    private String labelRecommendationJson;

    private List<LabelRecommendation> allLabelRecommendation = new ArrayList<>();

    private String allLabelRecommendationsJson;

    @Before
    public void setup() throws Exception {
        this.labelRecommendation = new LabelRecommendation();
        this.labelRecommendation.setLabelRecommendationId(1);
        this.labelRecommendation.setLabelId(1);
        this.labelRecommendation.setUserId(1);
        this.labelRecommendation.setLiked(Boolean.TRUE);
        this.labelRecommendationJson = this.mapper.writeValueAsString(this.labelRecommendation);

        LabelRecommendation labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelRecommendationId(2);
        labelRecommendation.setLabelId(2);
        labelRecommendation.setUserId(2);
        labelRecommendation.setLiked(Boolean.TRUE);

        this.allLabelRecommendation.add(this.labelRecommendation);
        this.allLabelRecommendation.add(labelRecommendation);
        this.allLabelRecommendationsJson = this.mapper.writeValueAsString(this.allLabelRecommendation);
    }

    @Test
    public void shouldReturnNewLabelRecommendationOnPostRequest() throws Exception {
        LabelRecommendation inputArtist = new LabelRecommendation();
        inputArtist.setLabelId(3);
        inputArtist.setLabelRecommendationId(3);
        inputArtist.setLiked(Boolean.TRUE);
        inputArtist.setUserId(3);

        LabelRecommendation outputArtist = new LabelRecommendation();
        outputArtist.setLabelId(3);
        outputArtist.setLabelRecommendationId(3);
        outputArtist.setLiked(Boolean.TRUE);
        outputArtist.setUserId(3);

        Mockito.doReturn(inputArtist).when(this.repo).save(outputArtist);

        this.mockMvc.perform(
                        post("/labelRecommendation")
                                .content(mapper.writeValueAsString(inputArtist))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputArtist)));
    }

    @Test
    public void shouldReturnLabelRecommendationById() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation();
        inputLabelRecommendation.setLabelId(1);
        inputLabelRecommendation.setLabelRecommendationId(1);
        inputLabelRecommendation.setUserId(1);
        inputLabelRecommendation.setLiked(Boolean.TRUE);

        Mockito.doReturn(Optional.of(inputLabelRecommendation)).when(this.repo).findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.labelRecommendationJson)));
    }

    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception {
        Mockito.doReturn(this.allLabelRecommendation).when(this.repo).findAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRecommendation"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allLabelRecommendationsJson));
    }

    @Test
    public void shouldUpdateLabelRecommendationsByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/labelRecommendation/1")
                        .content(this.labelRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnLabelByIdAndReturn404StatusCode() throws Exception {
        Mockito.doReturn(Optional.empty()).when(this.repo).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRecommendation/1234"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
