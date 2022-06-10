package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendation")
public class TrackRecommendationController {

    @Autowired
    private TrackRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody TrackRecommendation trackRecommendation) {
        return repo.save(trackRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getEveryTrackRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable int id) throws IllegalArgumentException {
        Optional<TrackRecommendation> optionalTrackRecommendation = repo.findById(id);
        if(optionalTrackRecommendation.isPresent() == false) {
            throw new IllegalArgumentException("there is no track recommendations with id " + id);
        }
        return optionalTrackRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@PathVariable int id, @RequestBody TrackRecommendation trackRecommendation) throws IllegalArgumentException {
        if (trackRecommendation.getTrackRecommendationId() == null) {
            trackRecommendation.setTrackRecommendationId(id);
        } else if(trackRecommendation.getTrackRecommendationId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + trackRecommendation.getTrackRecommendationId() + ").");
        }
        repo.save(trackRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTrackRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }

}
