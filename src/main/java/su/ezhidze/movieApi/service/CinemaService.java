package su.ezhidze.movieApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Cinema;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.CinemaModel;
import su.ezhidze.movieApi.model.DirectorModel;
import su.ezhidze.movieApi.model.MovieModel;
import su.ezhidze.movieApi.repository.CinemaRepository;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;

import java.util.ArrayList;

@Service
public class CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;

    public ArrayList<CinemaModel> getCinemas() {
        ArrayList<CinemaModel> cinemas = new ArrayList<>();
        for (Cinema cinema : cinemaRepository.findAll()) cinemas.add(new CinemaModel(cinema));

        return cinemas;
    }

    public CinemaModel getCinemaById(Integer id) {
        return new CinemaModel(cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found")));
    }

    public ArrayList<MovieModel> getMovies(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        ArrayList<MovieModel> movies = new ArrayList<>();
        for (Movie movie : cinema.getMovies()) movies.add(new MovieModel(movie));

        return movies;
    }

    public ArrayList<DirectorModel> getDirectors(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        ArrayList<DirectorModel> directors = new ArrayList<>();
        for (Director director : cinema.getDirectors()) directors.add(new DirectorModel(director));

        return directors;
    }


    public CinemaModel addDirector(Integer cinemaId, Integer directorId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Director newDirector = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));

        if (cinema.getDirectors().contains(newDirector)) throw new DuplicateEntryException("Duplicate director entry in cinema object");
        cinema.getDirectors().add(newDirector);

        return new CinemaModel(cinema);
    }

    public CinemaModel addMovie(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Movie newMovie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));

        if (cinema.getMovies().contains(newMovie)) throw new DuplicateEntryException("Duplicate movie entry in cinema object");
        cinema.getMovies().add(newMovie);

        return new CinemaModel(cinema);
    }
}
