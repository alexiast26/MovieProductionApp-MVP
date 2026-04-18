package com.test.movie_app.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "director")
@RequiredArgsConstructor
@Getter
@Setter
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int directorId;

    @NotBlank(message = "Director name can't be empty")
    @Size(min = 2, max = 50, message = "Director name must be between 2 and 50 characters")
    private String name;

    private int age;
    private String gender;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;
}
