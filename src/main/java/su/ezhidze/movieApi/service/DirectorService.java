package su.ezhidze.movieApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.BadRequestException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.DirectorModel;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;
import su.ezhidze.movieApi.validator.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    public DirectorModel addNewDirector(final Director director) {
        if (directorRepository.findByName(director.getName()) != null) {
            throw new DuplicateEntryException("Duplicate director name");
        }

        Validator.validate(director);

        return new DirectorModel(directorRepository.save(director));
    }

    public DirectorModel getDirectorById(Integer id) {
        return new DirectorModel(directorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Director not found")));
    }

    public ArrayList<DirectorModel> getDirectors() {
        return new ArrayList<>(((Collection<Director>) directorRepository.findAll()).stream().map(DirectorModel::new).toList());
    }

    public DirectorModel addMovie(Integer directorId, Integer movieId) {
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
        if (movie.getDirector() == null) {
            movie.setDirector(director);
            movieRepository.save(movie);
        } else throw new BadRequestException("Provided movie belongs to other director");

        director.getMovies().add(movie);
        return new DirectorModel(director);
    }

    public DirectorModel deleteMovie(Integer directorId, Integer movieId) {
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));

        if (!director.getMovies().contains(movie)) {
            throw new BadRequestException("Director doesn't have that movie");
        } else {
            movie.setDirector(null);
            director.getMovies().remove(movie);
            movieRepository.save(movie);
            directorRepository.save(director);
        }

        return new DirectorModel(director);
    }

    public DirectorModel patchDirector(Integer id, Map<String, Object> fields) {
        Director patchedDirector = directorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Director not found"));
        if (fields.get("name") != null) {
            if (directorRepository.findByName((String) fields.get("name")) != null)
                throw new DuplicateEntryException("Duplicate director name");
            patchedDirector.setName((String) fields.get("name"));
        }
        directorRepository.save(patchedDirector);
        return new DirectorModel(patchedDirector);
    }

    public void delete(Integer id) {
        directorRepository.delete(directorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Director not found")));
    }
}
