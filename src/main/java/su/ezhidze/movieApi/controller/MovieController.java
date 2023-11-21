package su.ezhidze.movieApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.ExceptionBodyBuilder;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.service.MovieService;

import java.util.Map;

@Controller
@RequestMapping(path = "/movieApi")
public class MovieController {

    @Autowired
    private MovieService movieService;

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

    @PostMapping(path = "/movies/add", params = {"directorId"})
    public ResponseEntity addNewMovie(@RequestBody Movie movie, @RequestParam Integer directorId) {
        try {
            return ResponseEntity.ok(movieService.addNewMovie(movie, directorId));
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
}
