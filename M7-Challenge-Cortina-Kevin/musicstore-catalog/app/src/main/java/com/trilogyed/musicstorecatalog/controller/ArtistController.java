package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist) {
        return repo.save(artist);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getEveryArtist() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable int id) {
        Optional<Artist> optionalArtist = repo.findById(id);
        if(!optionalArtist.isPresent()){
            throw new IllegalArgumentException("there is label with id" + id);
        }
        return optionalArtist.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable int id, @RequestBody Artist artist) {
        if(artist.getArtistId() == null) {
            artist.setArtistId(id);
        }else if (artist.getArtistId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + artist.getArtistId() + ").");
        }
        repo.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtist(@PathVariable int id){
        repo.deleteById(id);
    }


}
