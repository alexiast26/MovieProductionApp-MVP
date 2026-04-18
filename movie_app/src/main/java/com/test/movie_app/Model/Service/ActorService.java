package com.test.movie_app.Model.Service;

import com.test.movie_app.Model.Actor;
import com.test.movie_app.Model.Movie;
import com.test.movie_app.Model.Repository.ActorRepository;
import com.test.movie_app.Model.Repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    public void validateActor(Actor actor) {
        if (actor.getName() == null || actor.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Actor name cannot be empty");
        }
    }

    @Transactional
    public void saveActor(Actor actor) {
        validateActor(actor);
        actorRepository.save(actor);
    }

    public void deleteActor(Actor actor) {
        List<Movie> movies = movieRepository.findByActorsContaining(actor);
        for (Movie movie : movies) {
            movie.getActors().remove(actor);
            movieRepository.save(movie);
        }
        actorRepository.delete(actor);
    }

    @Transactional
    public void updateActor(Actor actor) {
        if (!actorRepository.existsById(actor.getActorId())){
            throw new IllegalArgumentException("Actor does not exist");
        }
        validateActor(actor);
        actorRepository.save(actor);
    }

    public Actor getActorById(Integer id) {
        return actorRepository.findActorByActorId(id);
    }
}
