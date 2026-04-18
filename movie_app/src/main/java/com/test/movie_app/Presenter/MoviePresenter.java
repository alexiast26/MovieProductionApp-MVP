package com.test.movie_app.Presenter;

import com.test.movie_app.Model.*;
import com.test.movie_app.Model.Service.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MoviePresenter {
    private final IMovieGUI gui;
    private final IMovieForm movieForm;
    private final MovieService movieService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final ScreenwriterService screenwriterService;
    private final ImageService imageService;
    private final ApplicationContext context;
    private Integer currentEditingMovieId = null;

    public MoviePresenter(@Lazy IMovieGUI gui, @Lazy IMovieForm movieForm, MovieService movieService, ActorService actorService, ApplicationContext context, DirectorService directorService, ScreenwriterService screenwriterService, ImageService imageService) {
        this.gui = gui;
        this.movieForm = movieForm;
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.screenwriterService = screenwriterService;
        this.imageService = imageService;
        this.context = context;
    }

    public void init(){
        List<String> categories = new ArrayList<>();
        categories.add("All"); // Opțiune pentru a vedea toate filmele
        categories.addAll(java.util.Arrays.stream(Category.values())
                .map(Category::toString)
                .collect(Collectors.toList()));        gui.setCategoryFilter(categories);
        refreshTable();
    }

    public void onMovieSelected(int movieId) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie != null) {
            List<String> actorNames = movie.getActors().stream().map(Actor::getName).toList();
            List<String> imageUrls = movie.getImages().stream().map(Image::getImgUrl).toList();
            gui.setMovieDetails(
                    movie.getName(),
                    movie.getDirector() != null ? movie.getDirector().getName() : "N/A",
                    movie.getScreenwriter() != null ? movie.getScreenwriter().getName() : "N/A",
                    String.valueOf(movie.getDuration()),
                    String.valueOf(movie.getReleaseYear()),
                    actorNames,
                    imageUrls
            );

        }
    }


    public void refreshTable() {
        List<Map<String, Object>> mappedMovies = movieService.getMovies().stream().map(movie -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", movie.getMovieId());
            map.put("name", movie.getName());
            return map;
        }).toList();
        gui.setMovieTable(mappedMovies);
    }

    public void onCategoryFilterChanged(String category) {
        if (category == null || category.equals("All")) {
            refreshTable();
            return;
        }

        try {
            Category selectedCategory = Category.valueOf(category);
            List<Map<String, Object>> filtered = movieService.getMovies().stream().filter(movie -> movie.getCategory() == selectedCategory).map(movie -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", movie.getMovieId());
                map.put("name", movie.getName());
                return map;
            }).toList();
            gui.setMovieTable(filtered);
        }catch (IllegalArgumentException e) {
            gui.showErrorMessage("Invalid category selected");
        }
    }

    public void onAddButtonClicked() {
        currentEditingMovieId = null;
        openForm(null);
    }

    private void openForm(Movie movie) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieForm.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
            Parent root = fxmlLoader.load();

            movieForm.setGenreOptions(Arrays.stream(Genre.values()).map(Genre::toString).collect(Collectors.toList()));
            movieForm.setCategoryOptions(Arrays.stream(Category.values()).map(Category::toString).collect(Collectors.toList()));

            List<Map<String, Object>> allActors = actorService.getActors().stream().map(this::mapActorToMap).toList();
            if (movie == null) {
                movieForm.setActorsLists(allActors, new ArrayList<>());
            } else {
                Map<String, Object> movieMapped = mapMovieToMap(movie);
                movieMapped.put("year", String.valueOf(movie.getReleaseYear()));
                movieMapped.put("duration", String.valueOf(movie.getDuration()));
                if (movie.getDirector() != null) {
                    movieMapped.put("director", movie.getDirector().getName());
                } else {
                    movieMapped.put("director", "");
                }

                if (movie.getScreenwriter() != null) {
                    movieMapped.put("screenwriter", movie.getScreenwriter().getName());
                } else {
                    movieMapped.put("screenwriter", "");
                }
                movieMapped.put("genre", movie.getGenre().name());
                movieMapped.put("category", movie.getCategory().name());
                movieMapped.put("actors", movie.getActors().stream().map(this::mapActorToMap).toList());

                movieForm.setMovieToEdit(movieMapped, allActors);
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveMovieForm() {
        try {
            Movie movie;
            if (currentEditingMovieId != null) {
                movie = movieService.getMovieById(currentEditingMovieId);
            } else {
                movie = new Movie();
            }

            movie.setName(movieForm.getTitle());
            movie.setReleaseYear(movieForm.getYear());
            movie.setDuration(movieForm.getDuration());
            movie.setGenre(Genre.valueOf(movieForm.getGenre()));
            movie.setCategory(Category.valueOf(movieForm.getCategory()));
            movie.setDirector(directorService.getDirectorByName(movieForm.getDirectorName()));
            movie.setScreenwriter(screenwriterService.getScreenwriterByName(movieForm.getScreenwriterName()));

            List<Map<String, Object>> selectedActorsFromGui = movieForm.getSelectedActors();
            List<Actor> actorsToSave = selectedActorsFromGui.stream()
                    .map(actorMap -> {
                        Integer id = (Integer) actorMap.get("id");
                        return actorService.getActorById(id);
                    })
                    .collect(Collectors.toList());

            movie.setActors(actorsToSave);

            Movie savedMovie = movieService.saveMovie(movie);
            boolean shouldDeleteOld = movieForm.areImagesCleared();
            List<String> imagePaths = movieForm.getSelectedImagePaths();

            if (currentEditingMovieId != null) {
                if (shouldDeleteOld || (imagePaths != null && !imagePaths.isEmpty())) {
                    imageService.deleteImagesByMovieId(savedMovie.getMovieId());
                    System.out.println("Cleaned old photos from form");
                }
            }

            if (imagePaths != null && !imagePaths.isEmpty()) {
                getImages(imagePaths, savedMovie);
            }

            currentEditingMovieId = null;
            refreshTable();
        }catch (RuntimeException e){
            System.err.println("Validation failed: " + e.getMessage());
        }
    }

    public void onDeleteButtonClicked () {
        int selectedId = gui.getSelectedMovie();
        if (selectedId != -1) {
            Movie movie = movieService.getMovieById(selectedId);
            if (movie != null) {
                movieService.deleteMovie(movie);
                refreshTable();
            }
        }else {
            gui.showErrorMessage("Must select a movie");
        }
    }

    public void onUpdateClicked (){
        int selectedId = gui.getSelectedMovie();
        if (selectedId != -1) {
            currentEditingMovieId = selectedId;
            Movie movie = movieService.getMovieById(selectedId);
            if(movie != null) {
                openForm(movie);
            }
        }else {
            gui.showErrorMessage("Must select a movie");
        }
    }

    public Director getDirector(String directorName) {
        return directorService.getDirectorByName(directorName);
    }

    public Screenwriter getScreenwriter(String screenwriterName) {
        return screenwriterService.getScreenwriterByName(screenwriterName);
    }

    public List<Image> getImages(List<String> imageURLs, Movie movie) {
        //when creating a new movie, we will add new photos to the database
        List<Image> images = new ArrayList<>();
        for (String url : imageURLs) {
            Image image = new Image();
            image.setImgUrl(url);
            image.setMovie(movie);
            imageService.saveImage(image);
            images.add(image);
        }
        return images;
    }


    private Map<String, Object> mapMovieToMap(Movie m) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", m.getMovieId());
        map.put("name", m.getName());
        return map;
    }

    private Map<String, Object> mapActorToMap(Actor a) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", a.getActorId());
        map.put("name", a.getName());
        return map;
    }

}
