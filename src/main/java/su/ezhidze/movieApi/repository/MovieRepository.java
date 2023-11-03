package su.ezhidze.movieApi.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;

import su.ezhidze.movieApi.entity.MovieEntity;

public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {
    MovieEntity findByTitle(String title);
}
