package su.ezhidze.movieApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, message = "Name should not be empty")
    @Size(max = 100, message = "Name length should not be greater than 100 symbols")
    private String name;

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, message = "Address should not be empty")
    @Size(max = 100, message = "Address length should not be greater than 100 symbols")
    private String address;

    @NotNull(message = "Capacity field cannot be null")
    @Min(value = 10, message = "Cinema should not be less than 10")
    private Integer capacity;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Movie> movies;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Director> directors;

    public Cinema() {
        movies = new ArrayList<>();
        directors = new ArrayList<>();
    }
}
