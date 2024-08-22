package com.app.movie.controller;

import com.app.movie.dto.MovieRequest;
import com.app.movie.model.Movie;
import com.app.movie.service.MovieService;
import com.app.movie.validator.createGroup;
import com.app.movie.validator.updateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Validated
public class MovieController {

    @Autowired
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(this.movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer id) {
        return new ResponseEntity<>(this.movieService.getMovie(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity createNewMovie(@Validated(createGroup.class) @RequestBody MovieRequest movie) {
        this.movieService.createMovie(movie);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMovie(@PathVariable("id") Integer id,
                                      @Validated(updateGroup.class) @RequestBody MovieRequest movie) {
        movie.setId(id);
        this.movieService.updateMovie(movie);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updateMoviePartially(@PathVariable("id") Integer id,
                                               @Validated(updateGroup.class) @RequestBody MovieRequest movie) {
        movie.setId(id);
        this.movieService.updateMoviePartially(movie);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") Integer id) {
        this.movieService.deleteMovie(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
