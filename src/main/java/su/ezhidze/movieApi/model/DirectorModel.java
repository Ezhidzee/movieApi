package su.ezhidze.movieApi.model;

import lombok.Data;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;

import java.util.ArrayList;
import java.util.List;

@Data
public class DirectorModel {
    private Integer id;

    private String name;

    private List<MovieModel> movies;

    public DirectorModel(Director director) {
        movies = new ArrayList<>();
        id = director.getId();
        name = director.getName();
        movies = director.getMovies().stream().map(MovieModel::new).toList();
    }
}
