package su.ezhidze.movieApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.DirectorModel;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;
import su.ezhidze.movieApi.validator.Validator;

import java.util.ArrayList;
import java.util.Map;

@Service
public class DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    public DirectorModel addNewDirector(Director director) {
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
        ArrayList<DirectorModel> directors = new ArrayList<>();
        for (Director director : directorRepository.findAll()) directors.add(new DirectorModel(director));
        return directors;
    }

    public DirectorModel addMovie(Integer directorId, Integer movieId) {
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));
        director.getMovies().add(movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found")));

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
