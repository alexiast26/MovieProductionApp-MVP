package com.test.movie_app.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
@RequiredArgsConstructor
@Setter
@Getter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private int actorId;

    @NotBlank(message = "Actor name can't be empty")
    @Size(min = 2, max = 50, message = "Actor name must be between 2 and 50 characters")
    private String name;

    private int age;
    private String gender;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();
}
