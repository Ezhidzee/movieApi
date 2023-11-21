package su.ezhidze.movieApi.model;

import lombok.Data;
import su.ezhidze.movieApi.entity.Cinema;

import java.util.ArrayList;
import java.util.List;

@Data
public class CinemaModel {

    private Integer id;

    private String name;

    private String address;

    private Integer capacity;

    private List<MovieModel> movies;

    private List<DirectorModel> directors;

    public CinemaModel(final Cinema cinema) {
        id = cinema.getId();
        name = cinema.getName();
        address = cinema.getAddress();
        capacity = cinema.getCapacity();
        movies = new ArrayList<>();
        directors = new ArrayList<>();
        movies = cinema.getMovies().stream().map(MovieModel::new).toList();
        directors = cinema.getDirectors().stream().map(DirectorModel::new).toList();
        directors.forEach(director -> director.setMovies(director.getMovies().stream().filter(movie -> movies.contains(movie)).toList()));
    }
}
