package com.app.movie.dto;

import java.util.List;
import java.util.Objects;

public class MovieRequest {

    private Integer id;
    private String title;
    private Double rating;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }
}
