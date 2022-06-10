package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {

        @Autowired
        private TrackRepository repo;

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Track createTrack(@RequestBody Track track) {
            return repo.save(track);
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<Track> getEveryTrack() {
            return repo.findAll();
        }

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public Track getTrackById(@PathVariable int id) {
            Optional<Track> optionalTrack = repo.findById(id);
            if (optionalTrack.isPresent() == false) {
                throw new IllegalArgumentException("there is no pet with id " + id);
            }
            return optionalTrack.get();
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void updateTrack(@PathVariable int id, @RequestBody Track track) {
            if (track.getTrackId() == null) {
                track.setTrackId(id);
            } else if (track.getTrackId() != id) {
                throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                        "different from the id in your body (" + track.getTrackId() + ").");
            }

            repo.save(track);
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void removeTrack(@PathVariable int id) {
            repo.deleteById(id);
        }
}
