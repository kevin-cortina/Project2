package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/label")
public class LabelController {

    @Autowired
    private LabelRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody Label label) {
        return repo.save(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getEveryLabel(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable int id) {
        Optional<Label> optionalLabel = repo.findById(id);
        if (optionalLabel.isPresent() == false) {
            throw new IllegalArgumentException("there is no label with id" + id);
        }
        return optionalLabel.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable int id, @RequestBody Label label){
        if (label.getLabelId() ==null){
            label.setLabelId(id);
        } else if (label.getLabelId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + label.getLabelId() + ").");
        }
         repo.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLabel(@PathVariable int id) {
        repo.deleteById(id);
    }

}
