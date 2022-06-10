package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AlbumRepository repo;
    private Album albumExample;
    private String albumJson;
    private List<Album> allAlbums = new ArrayList();
    private String allAlbumsJson;

    @Before
    public void setup() throws Exception {
        this.albumExample = new Album();
        this.albumExample.setAlbumId(1);
        this.albumExample.setTitle("Album Test Title");
        this.albumExample.setArtistId(1);
        this.albumExample.setReleaseDate(LocalDate.ofEpochDay(2022-12-05));
        this.albumExample.setLabelId(1);
        this.albumExample.setListPrice(BigDecimal.valueOf(56.99));
        this.albumJson = this.mapper.writeValueAsString(this.albumExample);

        Album album = new Album();
        album.setAlbumId(2);
        album.setTitle("Album Test Title 2");
        album.setArtistId(2);
        album.setReleaseDate(LocalDate.ofEpochDay(2022-12-05));
        album.setLabelId(2);
        album.setListPrice(BigDecimal.valueOf(56.99));
        this.allAlbums.add(this.albumExample);
        this.allAlbums.add(album);
        this.allAlbumsJson = this.mapper.writeValueAsString(this.allAlbums);

    }

    @Test
    public void shouldReturnNewAlbumOnPostRequest() throws Exception {
        Album inputAlbum = new Album();
        inputAlbum.setAlbumId(3);
        inputAlbum.setTitle("Random Title");
        inputAlbum.setArtistId(200);
        inputAlbum.setReleaseDate(LocalDate.ofEpochDay(2021-12-01));
        inputAlbum.setLabelId(20);
        inputAlbum.setListPrice(BigDecimal.valueOf(10.99));

        Album outputAlbum = new Album();
        outputAlbum.setAlbumId(3);
        outputAlbum.setTitle("Random Title");
        outputAlbum.setArtistId(200);
        outputAlbum.setReleaseDate(LocalDate.ofEpochDay(2021-12-01));
        outputAlbum.setLabelId(20);
        outputAlbum.setListPrice(BigDecimal.valueOf(10.99));

        ((AlbumRepository)Mockito.doReturn(inputAlbum).when(this.repo)).save(outputAlbum);

        this.mockMvc.perform(
                        post("/album")
                                .content(mapper.writeValueAsString(inputAlbum))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputAlbum)));
    }

    @Test
    public void shouldReturnAlbumById() throws Exception {
        Album inputAlbum = new Album();
        inputAlbum.setAlbumId(1);
        inputAlbum.setTitle("Album Test Title");
        inputAlbum.setArtistId(1);
        inputAlbum.setReleaseDate(LocalDate.ofEpochDay(2022-12-05));
        inputAlbum.setLabelId(1);
        inputAlbum.setListPrice(BigDecimal.valueOf(56.99));

        ((AlbumRepository)Mockito.doReturn(Optional.of(inputAlbum)).when(this.repo)).findById(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/album/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json((this.albumJson)));
    }

    @Test
    public void shouldReturnAllAlbums() throws Exception {
        ((AlbumRepository)Mockito.doReturn(this.allAlbums).when(this.repo)).findAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/album"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allAlbumsJson));
    }

    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/album/1")
                        .content(this.albumJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/album/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnAlbumByIdAndReturn404StatusCode() throws Exception {
        ((AlbumRepository) Mockito.doReturn(Optional.empty()).when(this.repo)).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/album/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
