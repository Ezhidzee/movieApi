package su.ezhidze.movieApi.model;

import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class DirectorModel {
    private Integer id;

    private String name;

    private List<MovieModel> movies;

    public DirectorModel(Director director) {
        movies = new ArrayList<>();
        id = director.getId();
        name = director.getName();
        for (Movie i : director.getMovies()) movies.add(new MovieModel(i));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }
}
