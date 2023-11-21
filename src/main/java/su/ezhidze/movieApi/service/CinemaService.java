package su.ezhidze.movieApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ezhidze.movieApi.entity.Cinema;
import su.ezhidze.movieApi.entity.Director;
import su.ezhidze.movieApi.entity.Movie;
import su.ezhidze.movieApi.exception.BadRequestException;
import su.ezhidze.movieApi.exception.DuplicateEntryException;
import su.ezhidze.movieApi.exception.RecordNotFoundException;
import su.ezhidze.movieApi.model.CinemaModel;
import su.ezhidze.movieApi.model.DirectorModel;
import su.ezhidze.movieApi.model.MovieModel;
import su.ezhidze.movieApi.repository.CinemaRepository;
import su.ezhidze.movieApi.repository.DirectorRepository;
import su.ezhidze.movieApi.repository.MovieRepository;
import su.ezhidze.movieApi.validator.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieRepository movieRepository;

    public ArrayList<CinemaModel> getCinemas() {
        return new ArrayList<>(((Collection<Cinema>) cinemaRepository.findAll()).stream().map(CinemaModel::new).toList());
    }

    public CinemaModel getCinemaById(Integer id) {
        return new CinemaModel(cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found")));
    }

    public CinemaModel addNewCinema(final Cinema cinema) {
        if (cinemaRepository.findByAddress(cinema.getAddress()) != null) {
            throw new DuplicateEntryException("Duplicate cinema address");
        }

        Validator.validate(cinema);

        cinemaRepository.save(cinema);
        return new CinemaModel(cinema);
    }

    public ArrayList<MovieModel> getMovies(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));

        return new ArrayList<>((cinema.getMovies().stream().map(MovieModel::new).toList()));
    }

    public ArrayList<DirectorModel> getDirectors(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));

        return new ArrayList<>((cinema.getDirectors().stream().map(DirectorModel::new).toList()));
    }

    public CinemaModel addDirector(Integer cinemaId, Integer directorId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Director newDirector = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));

        if (cinema.getDirectors().contains(newDirector)) {
            throw new DuplicateEntryException("Duplicate director entry in cinema object");
        }
        cinema.getDirectors().add(newDirector);
        for (Movie movie : newDirector.getMovies()) cinema.getMovies().add(movie);

        cinemaRepository.save(cinema);
        return new CinemaModel(cinema);
    }

    public CinemaModel deleteDirector(Integer cinemaId, Integer directorId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Director director = directorRepository.findById(directorId).orElseThrow(() -> new RecordNotFoundException("Director not found"));

        cinema.getMovies().removeIf(movie -> movie.getDirector().equals(director));

        if (cinema.getDirectors().remove(director)) {
            cinemaRepository.save(cinema);
        } else throw new BadRequestException("Cinema doesn't contain that director");

        return new CinemaModel(cinema);
    }

    public CinemaModel addMovie(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Movie newMovie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));

        if (cinema.getMovies().contains(newMovie)) {
            throw new DuplicateEntryException("Duplicate movie entry in cinema object");
        }
        cinema.getMovies().add(newMovie);

        if (newMovie.getDirector() != null && !cinema.getDirectors().contains(newMovie.getDirector())) {
            cinema.getDirectors().add(newMovie.getDirector());
        }

        cinemaRepository.save(cinema);
        return new CinemaModel(cinema);
    }

    public CinemaModel deleteMovie(Integer cinemaId, Integer movieId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RecordNotFoundException("Movie not found"));
        Director director = movie.getDirector();

        if (cinema.getMovies().remove(movie)) {
            cinemaRepository.save(cinema);
        } else throw new BadRequestException("Cinema doesn't contain that movie");

        boolean isDirector = false;
        for (Movie i : cinema.getMovies()) {
            if (i.getDirector().equals(director)) {
                isDirector = true;
                break;
            }
        }

        if (!isDirector) {
            cinema.getDirectors().remove(director);
            cinemaRepository.save(cinema);
        }

        return new CinemaModel(cinema);
    }

    public CinemaModel patchCinema(Integer id, Map<String, Object> fields) {
        Cinema patchCinema = cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found"));
        if (fields.get("name") != null) patchCinema.setName((String) fields.get("name"));
        if (fields.get("address") != null) {
            if (cinemaRepository.findByAddress((String) fields.get("address")) != null)
                throw new DuplicateEntryException("Duplicate cinema address");
            patchCinema.setAddress((String) fields.get("address"));
        }
        if (fields.get("capacity") != null) patchCinema.setCapacity((Integer) fields.get("capacity"));

        cinemaRepository.save(patchCinema);
        return new CinemaModel(patchCinema);
    }

    public void delete(Integer id) {
        cinemaRepository.delete(cinemaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cinema not found")));
    }
}
