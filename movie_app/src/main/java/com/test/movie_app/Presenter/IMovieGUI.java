package com.test.movie_app.Presenter;

import java.util.List;
import java.util.Map;

public interface IMovieGUI {
    void setMovieTable(List<Map<String, Object>> moviesData);
    void setMovieDetails(String title, String director, String screenwriter, String duration, String year, List<String> actorsName, List<String> imageUrls);
    int getSelectedMovie();
    void showErrorMessage(String message);
    void setCategoryFilter(List<String> categories);
}
