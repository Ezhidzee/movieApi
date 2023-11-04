package su.ezhidze.movieApi.model;

import su.ezhidze.movieApi.entity.Movie;

import java.sql.Time;

public class MovieModel {
    private Integer id;

    private String title;

    private Integer year;

    private String director;

    private Time length;

    private Integer rating;

    public MovieModel(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        year = movie.getYear();
        director = movie.getDirector().getName();
        length = movie.getLength();
        rating = movie.getRating();
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Time getLength() {
        return length;
    }

    public void setLength(Time length) {
        this.length = length;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
