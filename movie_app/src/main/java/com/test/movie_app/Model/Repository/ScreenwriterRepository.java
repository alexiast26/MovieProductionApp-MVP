package com.test.movie_app.Model.Repository;

import com.test.movie_app.Model.Screenwriter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Integer> {
    Screenwriter findScreenwriterByName(@NotBlank(message = "Screenwriter name can't be empty") @Size(min = 2, max = 50, message = "Screenwriter name must be between 2 and 50 characters") String name);
    Screenwriter findScreenwriterByScreenwriterId(int id);

}
