package com.app.movie.controller;

import com.app.movie.dto.MovieRequest;
import com.app.movie.exception.NotFoundException;
import com.app.movie.model.Movie;
import com.app.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
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
    public ResponseEntity createNewMovie(@RequestBody MovieRequest movie) {
        this.movieService.createMovie(movie);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/create/category")
    public ResponseEntity createNewMovie(@RequestBody Movie movie) {
        this.movieService.createMovie(movie);
        return new ResponseEntity(HttpStatus.OK);
    }
}
