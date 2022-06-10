package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendation")
public class ArtistRecommendationController {

    @Autowired
    private ArtistRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@RequestBody ArtistRecommendation artistRecommendation) {
        return repo.save(artistRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getEveryAlbumRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendationById(@PathVariable int id) throws IllegalArgumentException {
        Optional<ArtistRecommendation> optionalArtistRecommendation = repo.findById(id);
        if(optionalArtistRecommendation.isPresent() == false) {
            throw new IllegalArgumentException("there is no artist recommendations with id " + id);
        }
        return optionalArtistRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@PathVariable int id, @RequestBody ArtistRecommendation artistRecommendation) throws IllegalArgumentException {
        if (artistRecommendation.getArtistRecommendationId() == null) {
            artistRecommendation.setArtistRecommendationId(id);
        } else if (artistRecommendation.getArtistRecommendationId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + artistRecommendation.getArtistRecommendationId() + ").");
        }
        repo.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtistRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }

}
