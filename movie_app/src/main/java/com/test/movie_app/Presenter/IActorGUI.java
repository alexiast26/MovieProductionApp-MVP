package com.test.movie_app.Presenter;

import java.util.List;
import java.util.Map;

public interface IActorGUI {
    void setActorTable(List<Map<String, Object>> actors);
    void setActorsMovieTable(List<String> movies);
    int getSelectedActor();
    void showErrorMessage(String message);
    void setActorDetails(String name, String age, String gender);
    String getActorName();
    int getActorAge();
    String getActorGender();
    void actorClearFields();

}
