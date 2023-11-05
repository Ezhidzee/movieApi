package su.ezhidze.movieApi.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.DirectorModel;
import su.ezhidze.movieApi.repository.DirectorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DirectorService {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    DirectorRepository directorRepository;

    public DirectorModel addNewDirector(Director director) {
        if (directorRepository.findByName(director.getName()) != null) {
            throw new DuplicateEntryException("Duplicate director name");
        }

        Set<ConstraintViolation<Director>> violations = validator.validate(director);
        if (!violations.isEmpty()) {
            String message = "";
            for (ConstraintViolation<Director> i : violations) {
                message += i.getMessage() + ". ";
            }
            throw new BadArgumentException(message);
        }

        return new DirectorModel(directorRepository.save(director));
    }

    public DirectorModel getDirectorById(Integer id) {
        return new DirectorModel(directorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Director not found")));
    }

    public Iterable<DirectorModel> getDirectors() {
        List<DirectorModel> t = new ArrayList<>();
        for (Director i : directorRepository.findAll()) t.add(new DirectorModel(i));
        return t;
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
