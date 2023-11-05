package su.ezhidze.movieApi.model;

import su.ezhidze.movieApi.entity.Movie;

import java.sql.Time;

public class MovieModel {
    private Integer id;

    private String title;

    private Integer year;

    private String directorName;

    private Integer directorId;

    private Time length;

    private Integer rating;

    public MovieModel(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        year = movie.getYear();
        directorName = movie.getDirector().getName();
        directorId = movie.getDirector().getId();
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

    public String getDirectorName() { return directorName; }

    public void setDirectorName(String directorName) { this.directorName = directorName; }

    public Integer getDirectorId() { return directorId; }

    public void setDirectorId(Integer directorId) { this.directorId = directorId; }

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
