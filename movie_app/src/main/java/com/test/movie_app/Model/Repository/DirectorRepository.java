package com.test.movie_app.Model.Repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.movie_app.Model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {
    Director findDirectorByName(@NotBlank(message = "Director name can't be empty") @Size(min = 2, max = 50, message = "Director name must be between 2 and 50 characters") String name);
    Director findDirectorByDirectorId(int id);
}
