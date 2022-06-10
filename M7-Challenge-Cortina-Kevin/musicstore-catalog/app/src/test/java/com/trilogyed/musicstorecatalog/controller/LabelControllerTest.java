package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@WebMvcTest(LabelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LabelRepository repo;

    private Label labelExample;
    private String labelJson;
    private List<Label> allLabel = new ArrayList();
    private String allLabelJson;

    @Before
    public void setup() throws Exception {
        this.labelExample = new Label();
        this.labelExample.setLabelId(1);
        this.labelExample.setName("Label Name 1");
        this.labelExample.setWebsite("Label Website 1");
        this.labelJson = this.mapper.writeValueAsString(this.labelExample);

        Label label = new Label();
        label.setLabelId(2);
        label.setName("Label Name 2");
        label.setWebsite("Label Website 2");

        this.allLabel.add(this.labelExample);
        this.allLabel.add(label);
        this.allLabelJson = this.mapper.writeValueAsString(this.allLabel);
    }

    @Test
    public void shouldReturnNewLabelOnPostRequest() throws Exception {

        Label inputLabel = new Label();
        inputLabel.setLabelId(1);
        inputLabel.setName("Death Row Records");
        inputLabel.setWebsite("DeathRowRecords.com");

        Label outputLabel = new Label();
        outputLabel.setLabelId(1);
        outputLabel.setName("Death Row Records");
        outputLabel.setWebsite("DeathRowRecords.com");

        ((LabelRepository) Mockito.doReturn(inputLabel).when(this.repo)).save(outputLabel);

        this.mockMvc.perform(
                        post("/label")
                                .content(mapper.writeValueAsString(inputLabel))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputLabel)));
    }

    @Test
    public void shouldReturnLabelById() throws Exception {
        Label inputLabel = new Label();
        inputLabel.setLabelId(1);
        inputLabel.setName("Label Name 1");
        inputLabel.setWebsite("Label Website 1");

        ((LabelRepository)Mockito.doReturn(Optional.of(inputLabel)).when(this.repo)).findById(1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/label/1", new Object[0]))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.labelJson)));
    }

    @Test
    public void shouldReturnAllLabels() throws Exception {
        ((LabelRepository)Mockito.doReturn(this.allLabel).when(this.repo)).findAll();
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/label", new Object[0]))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allLabelJson));
    }

    @Test
    public void shouldUpdateLabelByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/label/1")
                        .content(this.labelJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/label/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnLabelByIdAndReturn404StatusCode() throws Exception {
        ((LabelRepository) Mockito.doReturn(Optional.empty()).when(this.repo)).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/label/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
