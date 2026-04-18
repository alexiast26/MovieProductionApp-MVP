package com.test.movie_app.Model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "movie")
@RequiredArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int movieId;

    @NotBlank(message = "Movie name can't be empty")
    @Size(min = 2, max = 50, message = "Movie name must be between 2 and 50 characters")
    private String name;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "release_year")
    private int releaseYear;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "actormovie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToOne
    @JoinColumn(name = "screenwriter_id")
    private Screenwriter screenwriter;
}
