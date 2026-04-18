package com.test.movie_app.Model.Repository;

import com.test.movie_app.Model.Actor;
import com.test.movie_app.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByActorsContaining(Actor actor);
    Movie findByMovieId(int id);
}
