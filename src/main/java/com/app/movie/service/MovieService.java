package com.app.movie.service;

import com.app.movie.dto.MovieRequest;
import com.app.movie.exception.BadRequestException;
import com.app.movie.exception.NotFoundException;
import com.app.movie.model.Category;
import com.app.movie.model.Movie;
import com.app.movie.repository.CategoryRepository;
import com.app.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // get all created movies from database
    // GET Method
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    // get specific movie by id
    // GET Method
    public Movie getMovie(Integer id) {
        Movie requestedMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie with id:" + id + " does not exist!"));
        return requestedMovie;
    }

    // create new movie from DTO request
    // POST Method
    @Transactional
    public void createMovie(MovieRequest movie) {
        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setRating(movie.getRating());
        newMovie.setCategories(getCategoryFromRequest(movie.getCategories()));

        this.movieRepository.save(newMovie);
    }

    // update existing movie entirely
    // any field not provide it becomes null
    // PUT Method
    @Transactional
    public void updateMovie(MovieRequest movie) {
        // verify for existed movie from database
        Movie existingMovie = this.movieRepository.findById(movie.getId())
                .orElseThrow(() -> new NotFoundException("Movie with id: " + movie.getId() + " does not exist!"));

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setRating(movie.getRating());
        existingMovie.setCategories(getCategoryFromRequest(movie.getCategories()));

        this.movieRepository.save(existingMovie);
    }

    // update existing movie partially
    // only requested field are updated; otherwise remain the same
    // as for categories, will be added to existing one
    // PATCH Method
    @Transactional
    public void updateMoviePartially(MovieRequest movie) {
        // verify for existed movie from database
        Movie existingMovie = this.movieRepository.findById(movie.getId())
                .orElseThrow(() -> new NotFoundException("Movie with id: " + movie.getId() + " does not exist!"));


        if (movie.getTitle() != null)
            existingMovie.setTitle(movie.getTitle());

        if (movie.getRating() != null)
            existingMovie.setRating(movie.getRating());

        Set<Category> allCategories = existingMovie.getCategories().stream().collect(Collectors.toSet());
        if (movie.getCategories() != null) {
            allCategories.addAll(getCategoryFromRequest(movie.getCategories()));
        }
        existingMovie.setCategories(new ArrayList<>(allCategories));

        this.movieRepository.save(existingMovie);
    }

    // delete existing movie by id
    // DELETE Method
    @Transactional
    public void deleteMovie(Integer id) {
        Movie existingMovie = this.movieRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Movie with id: " + id + " does not exist!"));
        this.movieRepository.delete(existingMovie);
    }

    // get all new category and existing category (specifically use for DTO)
    // search by category name
    private List<Category> createMovieByCategoryName(List<String> categoryNames) {
        List<Category> allCategories = new ArrayList<>();

        categoryNames.forEach(name -> {
            Category existingCategory = this.categoryRepository.findByCategoryName(name);
            if (existingCategory == null) {
                Category newCategory = new Category();
                newCategory.setCategoryName(name);
                newCategory = this.categoryRepository.save(newCategory);
                allCategories.add(newCategory);
            } else {
                allCategories.add(existingCategory);
            }
        });
        return allCategories;
    }

    // get all new category and existing category (specifically use for DTO)
    // search by category id
    private List<Category> createMovieByCategoryId(List<Integer> categoryIds) {
        List<Category> allCategories = new ArrayList<>();
        categoryIds.forEach(id -> {
            Category existingCategory = this.categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Category with id: " + id + " does not exist!"));
            allCategories.add(existingCategory);
        });
        return allCategories;
    }

    // get all new category and existing category (specifically use for DTO)
    // get category object from name only or id only
    private List<Category> getCategoryFromRequest(List<Object> Categories) {
        List<Category> allCategories;

        Boolean isString = false;
        Boolean isInteger = false;

        for (Object requestCategory : Categories) {
            if (requestCategory instanceof Integer) {
                isInteger = true;
            } else if (requestCategory instanceof String) {
                isString = true;
            } else {
                throw new BadRequestException("Invalid format: " + requestCategory + " must be id or name");
            }
        }

        // create by category name
        if (!isInteger && isString) {
            allCategories = createMovieByCategoryName(Categories.stream().map(name -> name.toString()).toList());
        } else if (isInteger && !isString) {
            allCategories = createMovieByCategoryId(Categories.stream().map(id -> (Integer) id).toList());
        } else {
            throw new BadRequestException("Invalid format!");
        }

        return allCategories;
    }
}
