package su.ezhidze.movieApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.ExceptionBodyBuilder;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.service.DirectorService;
import su.ezhidze.movieApi.service.MovieService;

import java.util.Map;

@Controller
@RequestMapping(path = "/movieApi")
public class MainController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @GetMapping(path = "/movies", params = {"id"})
    public ResponseEntity getMovieById(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(movieService.getMovieById(id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/movies")
    public ResponseEntity getMovies() {
        try {
            return ResponseEntity.ok(movieService.getMovies());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping(path = "/movies/add")
    public ResponseEntity addNewMovie(@RequestBody Movie movie) {
        try {
            return ResponseEntity.ok(movieService.addNewMovie(movie));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/movies/setDirector")
    public ResponseEntity setDirector(@RequestParam Integer movieId, @RequestParam Integer directorId) {
        try {
            return ResponseEntity.ok(movieService.setDirector(movieId, directorId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PatchMapping(path = "/movies/patch", params = {"id"})
    public ResponseEntity patchMovie(@RequestParam Integer id, @RequestBody Map<String, Object> fields) {
        try {
            return ResponseEntity.ok(movieService.patchMovie(id, fields));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping(path = "/movies/delete", params = {"id"})
    public ResponseEntity deleteMovie(@RequestParam Integer id) {
        try {
            movieService.delete(id);
            return ResponseEntity.accepted().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping(path = "/directors/add")
    public ResponseEntity addNewMovie(@RequestBody Director director) {
        try {
            return ResponseEntity.ok(directorService.addNewDirector(director));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/directors", params = {"id"})
    public ResponseEntity getDirectorById(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(directorService.getDirectorById(id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/directors")
    public ResponseEntity getDirectors() {
        try {
            return ResponseEntity.ok(directorService.getDirectors());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/directors/addMovie")
    public ResponseEntity addMovie(@RequestParam Integer directorId, @RequestParam Integer movieId) {
        try {
            return ResponseEntity.ok(directorService.addMovie(directorId, movieId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PatchMapping(path = "/directors/patch", params = {"id"})
    public ResponseEntity patchDirector(@RequestParam Integer id, @RequestBody Map<String, Object> fields) {
        try {
            return ResponseEntity.ok(directorService.patchDirector(id, fields));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping(path = "/directors/delete", params = {"id"})
    public ResponseEntity deleteDirector(@RequestParam Integer id) {
        try {
            directorService.delete(id);
            return ResponseEntity.accepted().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }


}
