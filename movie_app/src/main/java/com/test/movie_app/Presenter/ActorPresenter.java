package com.test.movie_app.Presenter;


import com.test.movie_app.Model.Actor;
import com.test.movie_app.Model.Movie;
import com.test.movie_app.Model.Service.ActorService;
import com.test.movie_app.Model.Service.MovieService;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActorPresenter {
    private final IActorGUI gui;
    private final MovieService movieService;
    private final ActorService actorService;
    private Integer currentActorId = null;

    public ActorPresenter(@Lazy IActorGUI gui, MovieService movieService, ActorService actorService) {
        this.gui = gui;
        this.movieService = movieService;
        this.actorService = actorService;
    }

    public void init(){
        refreshTable();
    }

    public void onActorSelected(int actorId) {
        Actor actor = actorService.getActorById(actorId);
        if (actor != null) {
            gui.setActorDetails(actor.getName(), String.valueOf(actor.getAge()), actor.getGender() != null ? actor.getGender() : "N/A");
        }
    }

    public void onAddActorClicked() {
        try {
            String name = gui.getActorName();
            int age = gui.getActorAge();
            String gender = gui.getActorGender();

            if (currentActorId == null) {
                Actor actor = new Actor();
                actor.setName(name);
                actor.setAge(age);
                actor.setGender(gender);
                actorService.saveActor(actor);
            } else {
                Actor actor = actorService.getActorById(currentActorId);
                if (actor != null) {
                    actor.setName(name);
                    actor.setAge(age);
                    actor.setGender(gender);
                    actorService.updateActor(actor);
                }
            }

            currentActorId = null;
            refreshTable();
            gui.actorClearFields();
        } catch (Exception e) {
            gui.showErrorMessage("Invalid data: " + e.getMessage());
        }
    }

    public void onDeleteActorClicked() {
        try {
            int id_selected = gui.getSelectedActor();
            if (id_selected != -1) {
                Actor actor = actorService.getActorById(id_selected);
                if (actor != null) {
                    actorService.deleteActor(actor);
                    refreshTable();
                }
            } else {
                gui.showErrorMessage("Must select an actor");
            }
        }catch (DataIntegrityViolationException e) {
            gui.showErrorMessage("Cannot delete actor due to existing references");
        }
    }


    public void onModifyActorClicked() {
        int id_selected = gui.getSelectedActor();
        if (id_selected != -1) {
            this.currentActorId = id_selected;
            Actor actor = actorService.getActorById(id_selected);
            if (actor != null) {
                gui.setActorDetails(actor.getName(), String.valueOf(actor.getAge()), actor.getGender());
            }
        } else {
            gui.showErrorMessage("Must select an actor to modify");
        }
    }


    public void onGetMoviesClicked() {
        int id_selected = gui.getSelectedActor();
        if (id_selected != -1) {
            Actor actor = actorService.getActorById(id_selected);
            if (actor != null) {
                List<String> movies = movieService.getMoviesByActor(actor).stream().map(Movie::getName).toList();
                gui.setActorsMovieTable(movies);
            }
        }

    }

    public void refreshTable() {
        List<Map<String, Object>> mappedActors = actorService.getActors().stream().map(actor ->{
            Map<String, Object> map = new HashMap<>();
            map.put("id", actor.getActorId());
            map.put("name", actor.getName());
            map.put("age", actor.getAge());
            map.put("gender", actor.getGender());
            return map;
        }).toList();
        gui.setActorTable(mappedActors);
    }
}
