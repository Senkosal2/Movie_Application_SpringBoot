package com.app.movie.service;

import com.app.movie.dto.MovieRequest;
import com.app.movie.exception.BadRequestException;
import com.app.movie.exception.NotFoundException;
import com.app.movie.model.Category;
import com.app.movie.model.Movie;
import com.app.movie.repository.CategoryRepository;
import com.app.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private Movie movie;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        category = new Category();
        category.setId(1);
        category.setCategoryName("Action");

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setRating(3.4);
        movie.setCategories(Collections.singletonList(category));
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(1, result.size());
        assertEquals(movie.getTitle(), result.get(0).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetMovie() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovie(1);

        assertNotNull(result);
        assertEquals(movie.getTitle(), result.getTitle());
        verify(movieRepository, times(1)).findById(1);
    }

    @Test
    void testCreateMovie() {
        MovieRequest request = new MovieRequest();
        request.setTitle("New Movie");
        request.setRating(2.5);
        request.setCategories(Collections.singletonList("Comedy"));

        when(categoryRepository.findByCategoryName("Comedy")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        movieService.createMovie(request);

        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie() {
        MovieRequest request = new MovieRequest();
        request.setId(1);
        request.setTitle("Updated Movie");
        request.setRating(4.5);
        request.setCategories(List.of("Action"));

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(i -> i.getArguments()[0]);

        movieService.updateMovie(request);

        verify(movieRepository, times(1)).save(any(Movie.class));
        assertEquals("Updated Movie", movie.getTitle());
    }

    @Test
    void testUpdateMoviePartially() {
        MovieRequest request = new MovieRequest();
        request.setId(1);
        request.setRating(3.4);
        request.setCategories(Collections.singletonList("Drama"));

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(categoryRepository.findByCategoryName("Drama")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        movieService.updateMoviePartially(request);

        verify(movieRepository, times(1)).save(any(Movie.class));
        assertEquals(3.4, movie.getRating());
    }

    @Test
    void testDeleteMovie() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(1);

        verify(movieRepository, times(1)).delete(movie);
    }

}
