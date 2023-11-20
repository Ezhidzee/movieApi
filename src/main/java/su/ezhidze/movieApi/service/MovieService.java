package su.ezhidze.movieApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.MovieModel;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;
import su.ezhidze.movieApi.validator.Validator;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    public MovieModel addNewMovie(Movie movie) {
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new DuplicateEntryException("Duplicate movie title");
        }

        Validator.validate(movie);

        return new MovieModel(movieRepository.save(movie));
    }

    public MovieModel getMovieById(Integer id) {
        return new MovieModel(movieRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Movie not found")));
    }

    public ArrayList<MovieModel> getMovies() {
        ArrayList<MovieModel> movies = new ArrayList<>();
        for (Movie movie : movieRepository.findAll()) movies.add(new MovieModel(movie));
        return movies;
    }

    public MovieModel setDirector(Integer movieId, Integer directorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
        movie.setDirector(directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found")));

        return new MovieModel(movieRepository.save(movie));
    }

    public MovieModel patchMovie(Integer movieId, Map<String, Object> fields) {
        Movie patchedMovie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
        if (fields.get("directorId") != null && patchedMovie.getDirector().getId() != fields.get("directorId")) {
            patchedMovie.setDirector(directorRepository.findById((Integer) fields.get("directorId")).orElseThrow(() -> new RecordNotFoundException("Director not found")));
        }
        if (fields.get("title") != null) {
            if (movieRepository.findByTitle((String) fields.get("title")) != null)
                throw new DuplicateEntryException("Duplicate movie title");
            patchedMovie.setTitle((String) fields.get("title"));
        }
        if (fields.get("year") != null) patchedMovie.setYear((Integer) fields.get("year"));
        if (fields.get("length") != null) patchedMovie.setLength((Time) fields.get("length"));
        if (fields.get("rating") != null) patchedMovie.setRating((Integer) fields.get("rating"));
        movieRepository.save(patchedMovie);
        return new MovieModel(patchedMovie);
    }

    public void delete(Integer id) {
        movieRepository.delete(movieRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Movie not found")));
    }
}
