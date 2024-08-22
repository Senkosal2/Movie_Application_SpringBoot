package com.app.movie.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Double rating;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        if (rating < 0.5) throw new RuntimeException("Rating must be greater than or equal to " + 0.5);
        else if (rating > 5) throw new RuntimeException("Rating must be less than or equal to " + 5);
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie() {}

}
