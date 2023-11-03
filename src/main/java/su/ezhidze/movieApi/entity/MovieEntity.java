package su.ezhidze.movieApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

@Entity
public class MovieEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, message = "Title should not be empty")
    @Size(max = 100, message = "Title length should not be greater than 100 symbols")
    private String title;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year should not be less than 1900")
    @Max(value = 2100, message = "Year should not be greater than 2100")
    private Integer year;

    @NotNull(message = "Director field cannot be null")
    @Size(min = 1, message = "Director field should not be empty")
    @Size(max = 100, message = "Director field length should not be greater than 100 symbols")
    private String director;

    @NotNull(message = "Length cannot be null")
    private Time length;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private Integer rating;

    public MovieEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Time getLength() {
        return length;
    }

    public void setLength(Time length) {
        this.length = length;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
