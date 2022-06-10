package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album) {
        return repo.save(album);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getEveryAlbum() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable int id) {
        Optional<Album> optionalAlbum = repo.findById(id);
        if (optionalAlbum.isPresent()) {
            return optionalAlbum.get();
        } else {
            throw new IllegalArgumentException("there is no album with id " + id);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable int id, @RequestBody Album album){
        if (album.getAlbumId() == null) {
            album.setAlbumId(id);
        } else if (album.getAlbumId() != id) {
            throw new IllegalArgumentException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + album.getAlbumId() + ").");
        }
        repo.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtist(@PathVariable int id) {
        repo.deleteById(id);
    }


}
