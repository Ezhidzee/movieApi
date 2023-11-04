package su.ezhidze.movieApi.repository;

import org.springframework.data.repository.CrudRepository;
import su.ezhidze.movieApi.entity.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    Movie findByTitle(String title);
}
