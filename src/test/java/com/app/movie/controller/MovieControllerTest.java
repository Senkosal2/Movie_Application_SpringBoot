package com.app.movie.controller;

import com.app.movie.dto.MovieRequest;
import com.app.movie.model.Category;
import com.app.movie.model.Movie;
import com.app.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService; // Use @MockBean to provide a mock MovieService

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        Category category = new Category(1, "Action");
        Movie movie = new Movie(1, "Movie", 3.5, Collections.singletonList(category));

        when(movieService.getAllMovies()).thenReturn(Collections.singletonList(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Movie"))
                .andExpect(jsonPath("$[0].rating").value(3.5))
                .andExpect(jsonPath("$[0].categories[0].id").value(1))
                .andExpect(jsonPath("$[0].categories[0].categoryName").value("Action"));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    public void testGetMovie() throws Exception {
        Category category = new Category(1, "Action");
        Movie movie = new Movie(1, "Movie", 3.5, Collections.singletonList(category));

        when(movieService.getMovie(1)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Movie"))
                .andExpect(jsonPath("$.rating").value(3.5))
                .andExpect(jsonPath("$.categories[0].id").value(1))
                .andExpect(jsonPath("$.categories[0].categoryName").value("Action"));

        verify(movieService, times(1)).getMovie(1);
    }

    @Test
    public void testCreateNewMovie() throws Exception {
        MovieRequest movieRequest = new MovieRequest(
                "NewMovie",
                3.5,
                Collections.singletonList(1));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/movies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isCreated());

        verify(movieService, times(1)).createMovie(any(MovieRequest.class));
    }

    @Test
    public void testUpdateMovie() throws Exception {
        MovieRequest movieRequest = new MovieRequest(
                "updateMovieEntirely",
                3.5,
                Collections.singletonList(1));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/movies/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isOk());

        verify(movieService, times(1)).updateMovie(any(MovieRequest.class));
    }

    @Test
    public void testUpdateMoviePartially() throws Exception {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setTitle("UpdateMoviePartially");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/movies/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isOk());

        verify(movieService, times(1)).updateMoviePartially(any(MovieRequest.class));
    }

    @Test
    public void testDeleteMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/movies/delete/{id}", 1))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).deleteMovie(1);
    }
}
