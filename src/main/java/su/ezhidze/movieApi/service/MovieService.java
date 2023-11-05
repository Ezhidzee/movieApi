package su.ezhidze.movieApi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.MovieModel;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MovieService {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public MovieModel addNewMovie(Movie movie, Integer directorId) {
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new DuplicateEntryException("Duplicate movie title");
        }

        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        if (!violations.isEmpty()) {
            String message = "";
            for (ConstraintViolation<Movie> i : violations) {
                message += i.getMessage() + ". ";
            }
            throw new BadArgumentException(message);
        }

        movie.setDirector(directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found")));

        return new MovieModel(movieRepository.save(movie));
    }

    public MovieModel getMovieById(Integer id) {
        return new MovieModel(movieRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Movie not found")));
    }

    public Iterable<MovieModel> getMovies() {
        List<MovieModel> t = new ArrayList<>();
        for (Movie i : movieRepository.findAll()) t.add(new MovieModel(i));
        return t;
    }

    public MovieModel patchMovie(Integer movieId, Map<String, Object> fields) {
        Movie patchedMovie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
        if (fields.get("directorId") != null && patchedMovie.getDirector().getId() != fields.get("directorId")) {
            patchedMovie.setDirector(directorRepository.findById((Integer) fields.get("directorId")).orElseThrow(() -> new RecordNotFoundException("Director not found")));
        }
        if (fields.get("title") != null) {
            if (movieRepository.findByTitle((String) fields.get("title")) != null) throw new DuplicateEntryException("Duplicate movie title");
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
