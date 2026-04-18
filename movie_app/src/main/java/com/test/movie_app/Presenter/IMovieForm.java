package com.test.movie_app.Presenter;

import java.util.*;

public interface IMovieForm {
    void setActorsLists(List<Map<String, Object>> allActors, List<Map<String, Object>> movieActors);
    void setMovieToEdit(Map<String, Object> movieData, List<Map<String, Object>> allActors);
    String getTitle();
    int getDuration();
    int getYear();
    String getDirectorName();
    void setGenreOptions(List<String> genres);
    void setCategoryOptions(List<String> genres);

    String getScreenwriterName();

    String getGenre();

    String getCategory();

    List<Map<String, Object>> getSelectedActors();

    List<String> getSelectedImagePaths();
    boolean areImagesCleared();
}
