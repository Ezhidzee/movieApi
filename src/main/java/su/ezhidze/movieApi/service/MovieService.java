package su.ezhidze.movieApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import su.ezhidze.movieApi.entity.MovieEntity;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.repository.MovieRepository;

import java.util.Set;

@Service
public class MovieService {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public MovieEntity addNewMovie(MovieEntity movie) {
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new DuplicateEntryException("Duplicate movie title");
        }

        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(movie);
        if (!violations.isEmpty()) {
            String message = "";
            for (ConstraintViolation<MovieEntity> i : violations) {
                message += i.getMessage() + ". ";
            }
            throw new BadArgumentException(message);
        }

        return movieRepository.save(movie);
    }

    public MovieEntity getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
    }

    public Iterable<MovieEntity> getMovies() {
        return movieRepository.findAll();
    }

    public MovieEntity patchMovie(Integer id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(getMovieById(id), JsonNode.class));
        return movieRepository.save(objectMapper.treeToValue(patched, MovieEntity.class));
    }

    public void delete(Integer id) {
        movieRepository.delete(getMovieById(id));
    }
}
