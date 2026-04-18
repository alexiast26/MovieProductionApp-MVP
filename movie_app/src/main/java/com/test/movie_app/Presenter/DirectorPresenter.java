package com.test.movie_app.Presenter;

import com.test.movie_app.Model.Director;
import com.test.movie_app.Model.Service.DirectorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class DirectorPresenter {
    private final IDirectorGUI gui;
    private final DirectorService directorService;
    private Integer currentDirectorId = null;

    public DirectorPresenter(@Lazy IDirectorGUI gui, DirectorService directorService) {
        this.gui = gui;
        this.directorService = directorService;
    }

    public void init(){
        refreshTable();
    }

    public void onDirectorSelected(Integer directorId) {
        Director director = directorService.getDirectorById(directorId);
        if(director != null) {
            gui.setDirectorFormDetails(director.getName(), String.valueOf(director.getAge()), director.getGender());
        }

    }

    public void onAddDirectorClicked() {
        try {
            String name = gui.getDirectorName();
            int age = gui.getDirectorAge();
            String gender = gui.getDirectorGender();

            if (currentDirectorId == null) {
                Director director = new Director();
                director.setName(name);
                director.setAge(age);
                director.setGender(gender);
                directorService.saveDirector(director);
            } else {
                Director director = directorService.getDirectorById(currentDirectorId);
                if (director != null) {
                    director.setName(name);
                    director.setAge(age);
                    director.setGender(gender);
                    directorService.saveDirector(director);
                }
            }
            currentDirectorId = null;
            refreshTable();
            gui.directorClearFields();
        } catch (Exception e) {
            gui.showErrorMessage("Error saving director: " + e.getMessage());
        }
   }

   public void onDeleteDirectorClicked() {
        try {
            int selectedId = gui.getSelectedDirector();
            if (selectedId != -1) {
                this.currentDirectorId = selectedId;
                Director director = directorService.getDirectorById(selectedId);
                if (director != null) {
                    directorService.deleteDirector(director);
                    refreshTable();
                }
            } else {
                gui.showErrorMessage("Please select a director");
            }
        }catch (DataIntegrityViolationException e) {
            gui.showErrorMessage("Cannot delete director due to existing references");
        }
   }

   public void onUpdateDirectorClicked() {
        int selectedId = gui.getSelectedDirector();
        if(selectedId != -1){
            this.currentDirectorId = selectedId;
            Director director = directorService.getDirectorById(selectedId);
            if(director != null){
                gui.setDirectorFormDetails(director.getName(), String.valueOf(director.getAge()), director.getGender());
            }
        }else {
            gui.showErrorMessage("Please select a director");
        }
   }

    public void refreshTable(){
        List<Map<String, Object>> directors = directorService.getDirectors().stream().map(director -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", director.getDirectorId());
            map.put("name", director.getName());
            map.put("age", director.getAge());
            map.put("gender", director.getGender());
            return map;
        }).toList();
        gui.setDirectorTable(directors);
    }

}
