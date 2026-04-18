package com.test.movie_app.Model.Repository;

import com.test.movie_app.Model.Image;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image getImageByImgUrl(String url);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM image WHERE movie_id = :movieId", nativeQuery = true)
    void deleteByMovie_MovieId(@Param("movieId")int movieId);
}
