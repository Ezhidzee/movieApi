package su.ezhidze.movieApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import su.ezhidze.movieApi.entity.Cinema;
import su.ezhidze.movieApi.exception.BadArgumentException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.ExceptionBodyBuilder;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.CinemaModel;
import su.ezhidze.movieApi.service.CinemaService;

import java.util.Map;

@Controller
@RequestMapping(path = "/movieApi")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping(path = "/cinemas", params = {"id"})
    public ResponseEntity getCinemaById(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(cinemaService.getCinemaById(id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/cinemas")
    public ResponseEntity getCinemas() {
        try {
            return ResponseEntity.ok(cinemaService.getCinemas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping(path = "/cinemas/add")
    public ResponseEntity addNewCinema(@RequestBody Cinema cinema) {
        try {
            return ResponseEntity.ok(cinemaService.addNewCinema(cinema));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/cinemas/getMovies")
    public ResponseEntity getMovies(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(cinemaService.getMovies(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping(path = "/cinemas/getDirectors")
    public ResponseEntity getDirectors(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(cinemaService.getDirectors(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/cinemas/addDirector")
    public ResponseEntity addDirector(@RequestParam Integer cinemaId, @RequestParam Integer directorId) {
        try {
            CinemaModel cinemaModel = cinemaService.addDirector(cinemaId, directorId);
            return ResponseEntity.ok(cinemaModel);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/cinemas/deleteDirector")
    public ResponseEntity deleteDirector(@RequestParam Integer cinemaId, @RequestParam Integer directorId) {
        try {
            return ResponseEntity.ok(cinemaService.deleteDirector(cinemaId, directorId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/cinemas/addMovie")
    public ResponseEntity addMovie(@RequestParam Integer cinemaId, @RequestParam Integer movieId) {
        try {
            return ResponseEntity.ok(cinemaService.addMovie(cinemaId, movieId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PutMapping(path = "/cinemas/deleteMovie")
    public ResponseEntity deleteMovie(@RequestParam Integer cinemaId, @RequestParam Integer movieId) {
        try {
            return ResponseEntity.ok(cinemaService.deleteMovie(cinemaId, movieId));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PatchMapping(path = "/cinemas/patch", params = {"id"})
    public ResponseEntity patchCinema(@RequestParam Integer id, @RequestBody Map<String, Object> fields) {
        try {
            return ResponseEntity.ok(cinemaService.patchCinema(id, fields));
        } catch (DuplicateEntryException | BadArgumentException e) {
            return ResponseEntity.internalServerError().body(ExceptionBodyBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @DeleteMapping(path = "/cinemas/delete", params = {"id"})
    public ResponseEntity deleteCinema(@RequestParam Integer id) {
        try {
            cinemaService.delete(id);
            return ResponseEntity.accepted().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionBodyBuilder.build(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ExceptionBodyBuilder.build(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
