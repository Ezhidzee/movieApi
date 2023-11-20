package su.ezhidze.movieApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, message = "Title should not be empty")
    @Size(max = 100, message = "Title length should not be greater than 100 symbols")
    private String title;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year should not be less than 1900")
    @Max(value = 2100, message = "Year should not be greater than 2100")
    private Integer year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    private Director director;

    @NotNull(message = "Length cannot be null")
    private Time length;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private Integer rating;

    public Movie() {
    }
}
