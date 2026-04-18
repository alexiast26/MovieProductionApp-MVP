package com.test.movie_app.Presenter;

import java.util.Map;
import java.util.List;

public interface IScreenwriterGUI {
    void setScreenwriterTable(List<Map<String, Object>> directors);
    void setScreenwriterFormDetails(String name, String age, String gender);
    int getSelectedScreenwriter();
    String getScreenwriterName();
    int getScreenwriterAge();
    String getScreenwriterGender();
    void showErrorMessage(String message);
    void screenwClearFields();
}
