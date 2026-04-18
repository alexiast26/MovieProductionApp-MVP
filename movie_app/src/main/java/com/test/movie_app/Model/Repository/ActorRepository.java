package com.test.movie_app.Model.Repository;

import com.test.movie_app.Model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Actor findActorByActorId(Integer id);
}
