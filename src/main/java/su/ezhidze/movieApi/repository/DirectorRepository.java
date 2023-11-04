package su.ezhidze.movieApi.repository;

import org.springframework.data.repository.CrudRepository;
import su.ezhidze.movieApi.entity.Director;

public interface DirectorRepository  extends CrudRepository<Director, Integer> {
    Director findByName(String name);
}
