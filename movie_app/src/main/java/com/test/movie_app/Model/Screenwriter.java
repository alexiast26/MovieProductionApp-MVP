package com.test.movie_app.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "screenwriter")
@RequiredArgsConstructor
@Getter
@Setter
public class Screenwriter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screenwriter_id")
    private int screenwriterId;

    @NotBlank(message = "Screenwriter name can't be empty")
    @Size(min = 2, max = 50, message = "Screenwriter name must be between 2 and 50 characters")
    private String name;

    private int age;
    private String gender;

    @OneToMany(mappedBy = "screenwriter")
    private List<Movie> movies;
}
