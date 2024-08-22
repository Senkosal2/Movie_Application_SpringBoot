package com.app.movie.dto;

import com.app.movie.validator.createGroup;
import com.app.movie.validator.updateGroup;
import jakarta.validation.constraints.*;

import java.util.List;

public class MovieRequest {

    private Integer id;

    @NotNull(message = "Title cannot be null!", groups = {createGroup.class})
    private String title;

    @DecimalMin(value = "0.5", message = "Rating must be at least 0.5", groups = {createGroup.class, updateGroup.class})
    @DecimalMax(value = "5.0", message = "Rating must not exceed 5.0", groups = {createGroup.class, updateGroup.class})
    private Double rating;

    @NotEmpty(message = "Categories cannot be empty", groups = {createGroup.class})
    private List<Object> categories;

    public MovieRequest() {}

    public MovieRequest(Integer id, String title, Double rating, List<Object> categories) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "Title cannot be null!") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "Title cannot be null!") String title) {
        this.title = title;
    }

    public @DecimalMin(value = "0.5", message = "Rating must be at least 0.5") @DecimalMax(value = "5.0", message = "Rating must not exceed 5.0") Double getRating() {
        return rating;
    }

    public void setRating(@DecimalMin(value = "0.5", message = "Rating must be at least 0.5") @DecimalMax(value = "5.0", message = "Rating must not exceed 5.0") Double rating) {
        this.rating = rating;
    }

    public @NotEmpty(message = "Categories cannot be empty") List<Object> getCategories() {
        return categories;
    }

    public void setCategories(@NotEmpty(message = "Categories cannot be empty") List<Object> categories) {
        this.categories = categories;
    }
}
