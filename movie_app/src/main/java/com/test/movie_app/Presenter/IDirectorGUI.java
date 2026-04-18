package com.test.movie_app.Presenter;

import java.util.List;
import java.util.Map;

public interface IDirectorGUI {
    void setDirectorTable(List<Map<String, Object>> directors);
    void setDirectorFormDetails(String name, String age, String gender);
    int getSelectedDirector();
    String getDirectorName();
    int getDirectorAge();
    String getDirectorGender();
    void showErrorMessage(String message);
    void directorClearFields();
}
