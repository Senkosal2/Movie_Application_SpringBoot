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
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    // get specific movie by id
    public Movie getMovie(Integer id) {
        Movie requestedMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie with id:" + id + " does not exist!"));
        return requestedMovie;
    }

    // create new movie from Movie Object request
    @Transactional
    public void createMovie(Movie movie) {
        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setRating(movie.getRating());

        // all requested category ids
        Set<Integer> requestedCategoriesIds = movie.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        // existing categories from database
        Set<Category> existingCategories = this.categoryRepository.findAllById(requestedCategoriesIds).stream().collect(Collectors.toSet());

        // extract existing category ids
        Set<Integer> existingCategoryIds = existingCategories
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        // add all new categories
        Set<Category> newCategories = movie.getCategories()
                .stream()
                .filter(category -> !existingCategoryIds.contains(category.getId()))
                .map(category -> {
                    Category newCategory = new Category();

                    if (category.getId() != null) {
                        throw new NotFoundException("Category with id: " + category.getId() + " does not exist!");
                    }

                    Category existingCategory = this.categoryRepository.findByCategoryName(category.getCategoryName());

                    newCategory.setCategoryName(category.getCategoryName());

                    return existingCategory == null ? newCategory : existingCategory;
                })
                .collect(Collectors.toSet());

        if (!newCategories.isEmpty()) {
            existingCategories.addAll(newCategories);
        }

        newMovie.setCategories(existingCategories.stream().toList());
        this.movieRepository.save(newMovie);
    }

    // create new movie from DTO request
    @Transactional
    public void createMovie(MovieRequest movie) {
        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setRating(movie.getRating());

        List<Category> allCategories;

        Boolean isString = false;
        Boolean isInteger = false;

        for (Object requestCategory : movie.getCategories()) {
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
            allCategories = createMovieByCategoryName(movie);
        } else if (isInteger && !isString) {
            allCategories = createMovieByCategoryId(movie);
        } else {
            throw new BadRequestException("Invalid format!");
        }

        newMovie.setCategories(allCategories);

        this.movieRepository.save(newMovie);
    }

    // get all new category and existing category (specifically use for DTO)
    // search by category name
    private List<Category> createMovieByCategoryName(MovieRequest movie) {
        List<Category> allCategories = new ArrayList<>();
        movie.getCategories().forEach(name -> {
            Category existingCategory = this.categoryRepository.findByCategoryName(name.toString());
            if (existingCategory == null) {
                Category newCategory = new Category();
                newCategory.setCategoryName(name.toString());
                allCategories.add(newCategory);
            } else {
                allCategories.add(existingCategory);
            }
        });
        return allCategories;
    }

    // get all new category and existing category (specifically use for DTO)
    // search by category id
    private List<Category> createMovieByCategoryId(MovieRequest movie) {
        List<Category> allCategories = new ArrayList<>();
        movie.getCategories()
                .stream()
                .map(id -> (Integer) id)
                .forEach(id -> {
                    Category existingCategory = this.categoryRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Category with id: " + id + " does not exist!"));
                    allCategories.add(existingCategory);
                });
        return allCategories;
    }
}
