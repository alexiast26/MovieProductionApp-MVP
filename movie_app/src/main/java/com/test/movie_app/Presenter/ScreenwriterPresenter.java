package com.test.movie_app.Presenter;

import com.test.movie_app.Model.Screenwriter;
import com.test.movie_app.Model.Service.ScreenwriterService;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ScreenwriterPresenter {
    private final IScreenwriterGUI gui;
    private final ScreenwriterService screenwriterService;
    private Integer currentScreenwriterId = null;

    public ScreenwriterPresenter(@Lazy IScreenwriterGUI gui, ScreenwriterService screenwriterService) {
        this.gui = gui;
        this.screenwriterService = screenwriterService;
    }

    public void init(){
        refreshTable();
    }

    public void onAddScreenwriterClicked() {
        try {
            String name = gui.getScreenwriterName();
            int age = gui.getScreenwriterAge();
            String gender = gui.getScreenwriterGender();

            if (currentScreenwriterId == null) {
                Screenwriter s = new Screenwriter();
                s.setName(name);
                s.setAge(age);
                s.setGender(gender);
                screenwriterService.saveScreenwriter(s);
            } else {
                Screenwriter s = screenwriterService.getScreenwriterById(currentScreenwriterId);
                if (s != null) {
                    s.setName(name);
                    s.setAge(age);
                    s.setGender(gender);
                    screenwriterService.saveScreenwriter(s);
                }
            }
            currentScreenwriterId = null;
            refreshTable();
            gui.screenwClearFields();
        } catch (Exception e) {
            gui.showErrorMessage("Error: " + e.getMessage());
        }
    }

    public void onDeleleteScreenwriterClicked(){
        try {
            int selectedId = gui.getSelectedScreenwriter();
            if (selectedId != -1) {
                Screenwriter screenwriter = screenwriterService.getScreenwriterById(selectedId);
                if (screenwriter != null) {
                    screenwriterService.deleteScreenwriter(screenwriter);
                }
            } else {
                gui.showErrorMessage("Must select a screenwriter");
            }
        } catch (DataIntegrityViolationException e){
            gui.showErrorMessage("Cannot delete screenwriter due to existing references");
        }
    }

    public void onModifyScreenwriterClicked(){
        int selectedId = gui.getSelectedScreenwriter();
        if(selectedId != -1){
            this.currentScreenwriterId = selectedId;
            Screenwriter screenwriter = screenwriterService.getScreenwriterById(selectedId);
            if (screenwriter != null) {
                gui.setScreenwriterFormDetails(screenwriter.getName(), String.valueOf(screenwriter.getAge()), screenwriter.getGender());
            }
        }else {
            gui.showErrorMessage("Must select a screenwriter");
        }
    }

    public void refreshTable(){
        List<Map<String, Object>> screenwriterss = screenwriterService.getScreenwriters().stream().map(screenwriter ->{
            Map<String, Object> map = new HashMap<>();
            map.put("id", screenwriter.getScreenwriterId());
            map.put("name", screenwriter.getName());
            map.put("age", screenwriter.getAge());
            map.put("gender", screenwriter.getGender());
            return map;
        }).toList();
        gui.setScreenwriterTable(screenwriterss);
    }

}
