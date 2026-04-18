package com.test.movie_app.View;

import com.test.movie_app.Presenter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AppView implements IMovieGUI, IActorGUI, IDirectorGUI, IScreenwriterGUI {
    //Movie Tab
    @FXML private TableView<Map<String, Object>> movieTable;
    @FXML private TableColumn<Map, Object> idMovie;
    @FXML private TableColumn<Map, Object> movieName;

    @FXML private VBox detailsVbox;
    @FXML private Text titleText;
    @FXML private Text directorText;
    @FXML private Text screenwriterText;
    @FXML private Text durationText;
    @FXML private Text yearText;
    @FXML private TableView<String> movieActorsTable;
    @FXML private TableColumn<String, String> movieActors;

    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;

    @FXML private ChoiceBox<String> categoryFilter;

    //Actor Tab
    @FXML private TableView<Map<String, Object>> actorsTable;
    @FXML private TableColumn<Map, Object> idActor;
    @FXML private TableColumn<Map, Object> actorName;
    @FXML private TableColumn<Map, Object> actorAge;
    @FXML private TableColumn<Map, Object> actorGender;

    @FXML private TableView<String> actorMoviesTable;
    @FXML private TableColumn<String, String> actorMovies;
    @FXML private TextField actorNameAdd;
    @FXML private TextField actorAgeAdd;
    @FXML private TextField actorGenderAdd;

    //Director Tab
    @FXML private TableView<Map<String, Object>> directorTable;
    @FXML private TableColumn<Map, Object> idDirector;
    @FXML private TableColumn<Map, Object> directorName;
    @FXML private TableColumn<Map, Object> directorAge;
    @FXML private TableColumn<Map, Object> directorGender;

    @FXML private TextField directorNameAdd;
    @FXML private TextField directorAgeAdd;
    @FXML private TextField directorGenderAdd;

    //Screenwriter Tab
    @FXML private TableView<Map<String, Object>> screenwriterTable;
    @FXML private TableColumn<Map, Object> idScreenwriter;
    @FXML private TableColumn<Map, Object> screenwriterName;
    @FXML private TableColumn<Map, Object> screenwriterAge;
    @FXML private TableColumn<Map, Object> screenwriterGender;

    @FXML private TextField screenwriterNameAdd;
    @FXML private TextField screenwriterAgeAdd;
    @FXML private TextField screenwriterGenderAdd;


    @FXML private TabPane mainTab;
    @FXML private Tab movieTab;
    @FXML private Tab actorTab;
    @FXML private Tab directorTab;
    @FXML private Tab screenwriterTab;


    @Autowired
    private MoviePresenter moviePresenter;
    @Autowired
    private ActorPresenter actorPresenter;
    @Autowired
    private DirectorPresenter directorPresenter;
    @Autowired
    private ScreenwriterPresenter screenwriterPresenter;

    @FXML
    public void initialize() {
        setupMovieColumns();
        setupActorColumns();
        setupDirectorColumns();
        setupScreenwriterColumns();

        mainTab.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == movieTab) {
                moviePresenter.init();
            } else if (newTab == actorTab) {
                actorPresenter.init();
            } else if (newTab == directorTab) {
                directorPresenter.init();
            } else if (newTab == screenwriterTab) {
                screenwriterPresenter.init();
            }
        });

        moviePresenter.init();
    }

    private void setupMovieColumns() {
        idMovie.setCellValueFactory(new MapValueFactory<>("id"));
        movieName.setCellValueFactory(new MapValueFactory<>("name"));
        movieActors.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue()));

        movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) moviePresenter.onMovieSelected((Integer) newVal.get("id"));
        });

        categoryFilter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                moviePresenter.onCategoryFilterChanged(newValue);
            }
        });

        detailsVbox.visibleProperty().bind(movieTable.getSelectionModel().selectedItemProperty().isNotNull());
        detailsVbox.managedProperty().bind(detailsVbox.visibleProperty());
    }

    private void setupActorColumns() {
        idActor.setCellValueFactory(new MapValueFactory<>("id"));
        actorName.setCellValueFactory(new MapValueFactory<>("name"));
        actorAge.setCellValueFactory(new MapValueFactory<>("age"));
        actorGender.setCellValueFactory(new MapValueFactory<>("gender"));
        actorMovies.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue()));
        actorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int actorId = (Integer) newVal.get("id");
                actorPresenter.onActorSelected(actorId);
            }
        });
    }

    private void setupDirectorColumns() {
        idDirector.setCellValueFactory(new MapValueFactory<>("id"));
        directorName.setCellValueFactory(new MapValueFactory<>("name"));
        directorAge.setCellValueFactory(new MapValueFactory<>("age"));
        directorGender.setCellValueFactory(new MapValueFactory<>("gender"));
    }

    private void setupScreenwriterColumns() {
        idScreenwriter.setCellValueFactory(new MapValueFactory<>("id"));
        screenwriterName.setCellValueFactory(new MapValueFactory<>("name"));
        screenwriterAge.setCellValueFactory(new MapValueFactory<>("age"));
        screenwriterGender.setCellValueFactory(new MapValueFactory<>("gender"));
    }


    @Override
    public void setMovieTable(List<Map<String, Object>> moviesData) {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(moviesData);
        movieTable.setItems(data);
    }

    @Override
    public void setCategoryFilter(List<String> categories) {
        categoryFilter.getItems().addAll(categories);
    }

    @Override
    public void setMovieDetails(String title, String director, String screenwriter, String duration, String year, List<String> actorsNames, List<String>imageUrls){
        titleText.setText("Title: " + title);
        directorText.setText("Director: " + director);
        screenwriterText.setText("Screenwriter: " + screenwriter);
        durationText.setText("Duration: " + duration + " min");
        yearText.setText("Release Year: " + year);
        if (actorsNames != null) {
            movieActorsTable.setItems(FXCollections.observableArrayList(actorsNames));
        } else {
            movieActorsTable.getItems().clear();
        }

        image1.setImage(null);
        image2.setImage(null);
        image3.setImage(null);

        if (imageUrls != null) {
            if (imageUrls.size() >= 1) {
                image1.setImage(new javafx.scene.image.Image("file:" + imageUrls.get(0)));
            }
            if (imageUrls.size() >= 2) {
                image2.setImage(new javafx.scene.image.Image("file:" + imageUrls.get(1)));
            }
            if (imageUrls.size() >= 3) {
                image3.setImage(new javafx.scene.image.Image("file:" + imageUrls.get(2)));
            }
        }
    }

    @Override
    public int getSelectedMovie() {
        Map<String, Object> selectedItem = movieTable.getSelectionModel().getSelectedItem();
        return (selectedItem != null) ? (Integer) selectedItem.get("id") : -1;
    }

    @FXML
    public void addMovie() {
        moviePresenter.onAddButtonClicked();
    }

    @FXML
    public void deleteMovie() {
        moviePresenter.onDeleteButtonClicked();
    }

    @FXML
    public void modifyMovie() {
        moviePresenter.onUpdateClicked();
    }

    @Override
    public void setActorTable(List<Map<String, Object>> actors) {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(actors);
        actorsTable.setItems(data);
    }

    @Override
    public void setActorsMovieTable(List<String> movies) {
        actorMoviesTable.setItems(FXCollections.observableArrayList(movies));
    }

    @Override
    public int getSelectedActor() {
        Map<String, Object> selectedItem = actorsTable.getSelectionModel().getSelectedItem();
        return (selectedItem != null) ? (Integer) selectedItem.get("id") : -1;
    }

    @Override
    public void setDirectorTable(List<Map<String, Object>> directors) {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(directors);
        directorTable.setItems(data);
    }

    @Override
    public void setDirectorFormDetails(String name, String age, String gender) {
        directorNameAdd.setText(name);
        directorAgeAdd.setText(age);
        directorGenderAdd.setText(gender);
    }

    @Override
    public int getSelectedDirector() {
        Map<String, Object> selectedItem = directorTable.getSelectionModel().getSelectedItem();
        return (selectedItem != null) ? (Integer) selectedItem.get("id") : -1;
    }

    @Override
    public String getDirectorName() {
        String name = directorNameAdd.getText();
        if (name.isEmpty()) {
            showErrorMessage("Please enter a director name");
        }
        return name;
    }

    @Override
    public int getDirectorAge() {
        try {
            int age = Integer.parseInt(directorAgeAdd.getText());
            if (age < 0) {
                showErrorMessage("Please enter a valid age");
                throw new RuntimeException("Validation error");
            }
            return age;
        }catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid age");
            throw new RuntimeException("NumberFormat error");
        }
    }

    @Override
    public String getDirectorGender() {
        String gender = directorGenderAdd.getText();
        if (gender.isEmpty()) {
            showErrorMessage("Please enter a director gender");
        }
        return gender;
    }

    @Override
    public void setScreenwriterTable(List<Map<String, Object>> directors) {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(directors);
        screenwriterTable.setItems(data);
    }

    @Override
    public void setScreenwriterFormDetails(String name, String age, String gender) {
        screenwriterNameAdd.setText(name);
        screenwriterAgeAdd.setText(age);
        screenwriterGenderAdd.setText(gender);
    }

    @Override
    public int getSelectedScreenwriter() {
        Map<String, Object> selectedItem = screenwriterTable.getSelectionModel().getSelectedItem();
        return (selectedItem != null) ? (Integer) selectedItem.get("id") : -1;
    }

    @Override
    public String getScreenwriterName() {
        String name = screenwriterNameAdd.getText();
        if (name.isEmpty()) {
            showErrorMessage("Please enter a screenwriter name");
        }
        return name;
    }

    @Override
    public int getScreenwriterAge() {
        try {
            int age = Integer.parseInt(screenwriterAgeAdd.getText());
            if (age < 0) {
                showErrorMessage("Please enter a valid age");
                throw new RuntimeException("Validation error");
            }
            return age;
        }catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid age");
            throw new RuntimeException("NumberFormat error");
        }
    }

    @Override
    public String getScreenwriterGender() {
        String gender = screenwriterGenderAdd.getText();
        if (gender.isEmpty()) {
            showErrorMessage("Please enter a screenwriter gender");
        }
        return gender;
    }

    @Override
    public void showErrorMessage(String message) {
        showAlert("App problem", null, message, Alert.AlertType.ERROR);
    }

    @Override
    public void screenwClearFields() {
        screenwriterNameAdd.clear();
        screenwriterAgeAdd.clear();
        screenwriterGenderAdd.clear();
    }

    @Override
    public void directorClearFields() {
        directorNameAdd.clear();
        directorAgeAdd.clear();
        directorGenderAdd.clear();
    }

    @Override
    public void setActorDetails(String name, String age, String gender) {
        actorNameAdd.setText(name);
        actorAgeAdd.setText(age);
        actorGenderAdd.setText(gender);
    }

    @Override
    public String getActorName() {
        String name = actorNameAdd.getText();
        if (name.isEmpty()) {
            showErrorMessage("Please enter a actor name");
        }
        return name;
    }

    @Override
    public int getActorAge() {
        try {
            int age = Integer.parseInt(actorAgeAdd.getText());
            if (age < 0) {
                showErrorMessage("Please enter a valid age");
                throw new RuntimeException("Validation error");
            }
            return age;
        }catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid age");
            throw new RuntimeException("NumberFormat error");
        }
    }

    @Override
    public String getActorGender() {
        String gender = actorGenderAdd.getText();
        if (gender.isEmpty()) {
            showErrorMessage("Please enter a valid gender");
        }
        return gender;
    }

    @Override
    public void actorClearFields() {
        actorNameAdd.clear();
        actorAgeAdd.clear();
        actorGenderAdd.clear();
    }


    public void showAlert(String title, String header, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void addActor() {
        actorPresenter.onAddActorClicked();
    }

    @FXML
    public void deleteActor() {
        actorPresenter.onDeleteActorClicked();
    }

    @FXML
    public void modifyActor() {
        actorPresenter.onModifyActorClicked();
    }

    @FXML
    public void getMovies() {
        System.out.println("Get Movies clicked");
        try {
            actorPresenter.onGetMoviesClicked();
        }catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Info", "Actor has not participated in a movie in the database", e.getMessage(), Alert.AlertType.INFORMATION);
            actorMoviesTable.getItems().clear();
        }
    }

    @FXML
    public void addDirector() {
        directorPresenter.onAddDirectorClicked();
    }

    @FXML
    public void deleteDirector() {
        directorPresenter.onDeleteDirectorClicked();
    }

    @FXML
    public void modifyDirector() {
        directorPresenter.onUpdateDirectorClicked();
    }

    @FXML
    public void addScreenwriter() {
        screenwriterPresenter.onAddScreenwriterClicked();
    }

    @FXML
    public void deleteScreenwriter() {
        screenwriterPresenter.onDeleleteScreenwriterClicked();
    }

    @FXML
    public void modifyScreenwriter() {
        screenwriterPresenter.onModifyScreenwriterClicked();
    }

}
