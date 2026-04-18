package com.test.movie_app.Model.Service;

import com.test.movie_app.Model.*;
import com.test.movie_app.Model.Repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    private void validateMovie(Movie movie) {
        if (movie.getName() == null || movie.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Movie name cannot be empty");
        }
        if (movie.getDuration() <= 0) {
            throw new IllegalArgumentException("Movie duration must be greater than 0 minutes");
        }
    }

    public Movie saveMovie(Movie movie) {
        validateMovie(movie);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

    @Transactional
    public void updateMovie(Movie movie) {
        if (!movieRepository.existsById(movie.getMovieId())){
            throw new IllegalArgumentException("Movie with id " + movie.getMovieId() + " does not exist");
        }
        validateMovie(movie);
        movieRepository.save(movie);
    }

    public List<Movie> getMoviesByActor(Actor actor) {
        List<Movie> movies = movieRepository.findByActorsContaining(actor);
        if (movies.isEmpty()) {
            throw new IllegalArgumentException("Movie with actor " + actor.getName() + " does not exist");
        }
        return movies;
    }

    public Movie getMovieById(int id) {
        return movieRepository.findByMovieId(id);
    }

}
