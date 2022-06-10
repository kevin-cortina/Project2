package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
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
@WebMvcTest(ArtistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ArtistRepository repo;

    private Artist artistExample;
    private String artistJson;
    private List<Artist> allArtist = new ArrayList();
    private String allArtistJson;

    @Before
    public void setup() throws Exception {
        this.artistExample = new Artist();
        this.artistExample.setArtistId(1);
        this.artistExample.setName("Artist Name 1");
        this.artistExample.setInstagram("Artist Instagram Handle");
        this.artistExample.setTwitter("Artist Twitter Handle");
        this.artistJson = this.mapper.writeValueAsString(this.artistExample);

        Artist artist = new Artist();
        artist.setArtistId(2);
        artist.setName("Artist Name 2");
        artist.setInstagram("Artist Instagram Handle");
        artist.setTwitter("Artist Twitter Handle");

        this.allArtist.add(this.artistExample);
        this.allArtist.add(artist);
        this.allArtistJson = this.mapper.writeValueAsString(this.allArtist);

    }

    @Test
    public void shouldReturnNewArtistOnPostRequest() throws Exception {
        Artist inputArtist = new Artist();
        inputArtist.setArtistId(1);
        inputArtist.setName("Lil Uzi Vert");
        inputArtist.setInstagram("UziVert2000");
        inputArtist.setTwitter("UziVert2000");

        Artist outputArtist = new Artist();
        outputArtist.setArtistId(1);
        outputArtist.setName("Lil Uzi Vert");
        outputArtist.setInstagram("UziVert2000");
        outputArtist.setTwitter("UziVert2000");

        ((ArtistRepository)Mockito.doReturn(inputArtist).when(this.repo)).save(outputArtist);

        this.mockMvc.perform(
                            post("/artist")
                                    .content(mapper.writeValueAsString(inputArtist))
                                    .contentType(MediaType.APPLICATION_JSON)
                            )
                                    .andDo(print())
                                    .andExpect(status().isCreated())
                                    .andExpect(content().json(mapper.writeValueAsString(outputArtist)));
    }

    @Test
    public void shouldReturnArtistById() throws Exception {
    Artist inputArtist = new Artist();
        inputArtist.setArtistId(1);
        inputArtist.setName("Artist Name 1");
        inputArtist.setInstagram("Artist Instagram Handle");
        inputArtist.setTwitter("Artist Twitter Handle");

        ((ArtistRepository)Mockito.doReturn(Optional.of(inputArtist)).when(this.repo)).findById(1);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/artist/1", new Object[0]))
                .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json((this.artistJson)));
    }

    @Test
    public void shouldReturnAllArtist() throws Exception {
        ((ArtistRepository)Mockito.doReturn(this.allArtist).when(this.repo)).findAll();
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/artist", new Object[0]))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.allArtistJson));
    }

    @Test
    public void shouldUpdateArtistByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/artist/1")
                        .content(this.artistJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteArtistByIdAndReturn204StatusCode() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/artist/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldNotReturnAlbumByIdAndReturn404StatusCode() throws Exception {
        ((ArtistRepository) Mockito.doReturn(Optional.empty()).when(this.repo)).findById(1234);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/artist/1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
