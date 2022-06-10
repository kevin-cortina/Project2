package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendation")
public class AlbumRecommendationController {

    @Autowired
    private AlbumRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation) {
        return repo.save(albumRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getEveryAlbumRecommendation () {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable int id) throws IllegalArgumentException {
        Optional<AlbumRecommendation> optionalAlbumRecommendation = repo.findById(id);
        if(!optionalAlbumRecommendation.isPresent()) {
            throw new IllegalArgumentException("there is no album recommendations with id " + id);
        }
        return optionalAlbumRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@PathVariable int id, @RequestBody AlbumRecommendation albumRecommendation) throws IllegalArgumentException {
        if(albumRecommendation.getAlbumRecommendationId() == null) {
            albumRecommendation.setAlbumRecommendationId(id);
        } else if (albumRecommendation.getAlbumRecommendationId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + albumRecommendation.getAlbumRecommendationId() + ").");
        }
        repo.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlbumRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }

}
