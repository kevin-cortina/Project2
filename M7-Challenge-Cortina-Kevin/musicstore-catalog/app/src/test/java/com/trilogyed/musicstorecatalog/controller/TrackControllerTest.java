package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TrackRepository repo;

    private Track trackExample;
    private String trackJson;
    private List<Track> allTrack = new ArrayList();
    private String allTrackJson;

    @Before
    public void setup() throws Exception {
        this.trackExample = new Track();
        this.trackExample.setTrackId(1);
        this.trackExample.setAlbumId(1);
        this.trackExample.setTitle("Track Title 1");
        this.trackExample.setRunTime("RunTime Example 1");
        this.trackJson = this.mapper.writeValueAsString(this.trackExample);

        Track track = new Track();
        track.setTrackId(2);
        track.setAlbumId(2);
        track.setTitle("Track Title 2");
        track.setRunTime("RunTime Example 2");

        this.allTrack.add(this.trackExample);
        this.allTrack.add(track);
        this.allTrackJson = this.mapper.writeValueAsString(this.allTrack);
    }

    @Test
    public void shouldReturnNewTrackOnPostRequest() throws Exception {

        Track inputTrack = new Track();
        inputTrack.setTrackId(1);
        inputTrack.setAlbumId(2);
        inputTrack.setTitle("Track Title 1");
        inputTrack.setRunTime("10:50");

        Track outputTrack = new Track();
        outputTrack.setTrackId(1);
        outputTrack.setAlbumId(2);
        outputTrack.setTitle("Track Title 1");
        outputTrack.setRunTime("10:50");

        ((TrackRepository) Mockito.doReturn(inputTrack).when(this.repo)).save(outputTrack);

        this.mockMvc.perform(
            post("/track")
                .content(mapper.writeValueAsString(inputTrack))
                .contentType(MediaType.APPLICATION_JSON)
            )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputTrack)));
            }

    @Test
    public void shouldReturnTrackById() throws Exception {
        Track inputTrack = new Track();
        inputTrack.setTrackId(1);
        inputTrack.setAlbumId(1);
        inputTrack.setTitle("Track Title 1");
        inputTrack.setRunTime("RunTime Example 1");

        ((TrackRepository)Mockito.doReturn(Optional.of(inputTrack)).when(this.repo)).findById(1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/1", new Object[0]))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.trackJson)));
    }

    @Test
    public void shouldReturnAllTracks() throws Exception {
        ((TrackRepository)Mockito.doReturn(this.allTrack).when(this.repo)).findAll();
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/track", new Object[0]))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allTrackJson));
    }

    @Test
    public void shouldUpdateTrackByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/track/1")
                        .content(this.trackJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/track/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnTrackByIdAndReturn404StatusCode() throws Exception {
        ((TrackRepository) Mockito.doReturn(Optional.empty()).when(this.repo)).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/track/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
