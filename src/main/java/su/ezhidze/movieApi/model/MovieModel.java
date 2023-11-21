package su.ezhidze.movieApi.model;

import lombok.Data;
import su.ezhidze.movieApi.entity.Movie;

import java.sql.Time;

@Data
public class MovieModel {

    private Integer id;

    private String title;

    private Integer year;

    private String directorName;

    private Integer directorId;

    private Time length;

    private Integer rating;

    public MovieModel(final Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        year = movie.getYear();
        if (movie.getDirector() != null) {
            directorName = movie.getDirector().getName();
            directorId = movie.getDirector().getId();
        }
        length = movie.getLength();
        rating = movie.getRating();
    }
}
