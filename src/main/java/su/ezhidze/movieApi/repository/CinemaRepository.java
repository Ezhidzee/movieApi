package su.ezhidze.movieApi.repository;

import org.springframework.data.repository.CrudRepository;
import su.ezhidze.movieApi.entity.Cinema;

public interface CinemaRepository extends CrudRepository<Cinema, Integer> {
    Cinema findByAddress(String address);
}
