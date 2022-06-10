package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendation")
public class LabelRecommendationController {

    @Autowired
    private LabelRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody LabelRecommendation labelRecommendation) {
        return repo.save(labelRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getEveryAlbumRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getLabelRecommendation(@PathVariable int id) throws IllegalArgumentException {
        Optional<LabelRecommendation> optionalLabelRecommendation = repo.findById(id);
        if(optionalLabelRecommendation.isPresent() == false) {
            throw new IllegalArgumentException("there is no label recommendations with id " + id);
        }
        return optionalLabelRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@PathVariable int id, @RequestBody LabelRecommendation labelRecommendation) throws IllegalArgumentException {
        if (labelRecommendation.getLabelRecommendationId() == null) {
            labelRecommendation.setLabelRecommendationId(id);
        } else if (labelRecommendation.getLabelRecommendationId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + labelRecommendation.getLabelRecommendationId() + ").");
        }
        repo.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLabelRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }

}
