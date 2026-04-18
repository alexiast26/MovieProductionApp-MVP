package com.test.movie_app.View;

import com.test.movie_app.Presenter.IMovieForm;
import com.test.movie_app.Presenter.MoviePresenter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Component
public class MovieFormView implements IMovieForm {
    @FXML private TextField titleField;
    @FXML private TextField yearField;
    @FXML private TextField durationField;
    @FXML private TextField directorField;
    @FXML private TextField screenwriterField;
    @FXML private ChoiceBox<String> genraBox;
    @FXML private ChoiceBox<String> categoryBox;

    @FXML private TableView<Map> addActorsTable;
    @FXML private TableView<Map> existingActorsTable;

    @FXML private Button imgBtn;
    private List<String> selectedImagPaths = new ArrayList<>();
    private boolean imagesCleared = false;

    @Autowired
    private MoviePresenter presenter;

    @FXML
    private void initialize() {
        TableColumn<Map, String> col1 = new TableColumn<>("Name");
        col1.setCellValueFactory(new MapValueFactory("name"));
        addActorsTable.getColumns().setAll(col1);

        TableColumn<Map, String> col2 = new TableColumn<>("Name");
        col2.setCellValueFactory(new MapValueFactory("name"));
        existingActorsTable.getColumns().setAll(col2);
    }

    @FXML
    public void saveData(){
        presenter.saveMovieForm();
        ((Stage)  titleField.getScene().getWindow()).close();
    }

    @FXML
    public void addActorToMovie(){
       Map<String, Object> selectedActor = addActorsTable.getSelectionModel().getSelectedItem();
       if(selectedActor != null && !existingActorsTable.getItems().contains(selectedActor)){
           existingActorsTable.getItems().add(selectedActor);
       }
    }

    @FXML
    public void removeActorFromList(){
        Map<String, Object> selectedActor = existingActorsTable.getSelectionModel().getSelectedItem();
        if(selectedActor != null){
            existingActorsTable.getItems().remove(selectedActor);
        }
    }

    @FXML
    @Override
    public void setMovieToEdit(Map<String, Object> movieData, List<Map<String, Object>> allActors){
        titleField.setText((String) movieData.get("name"));
        yearField.setText((String) movieData.get("year"));
        durationField.setText((String) movieData.get("duration"));
        directorField.setText((String) movieData.get("director"));
        screenwriterField.setText((String) movieData.get("screenwriter"));
        genraBox.setValue((String) movieData.get("genre"));
        categoryBox.setValue((String) movieData.get("category"));

        addActorsTable.setItems(FXCollections.observableArrayList(allActors));

        List<Map<String, Object>> actors = (List<Map<String, Object>>) movieData.get("actors");
        existingActorsTable.setItems(FXCollections.observableArrayList(actors));
        this.imagesCleared = false;
        this.selectedImagPaths.clear();
    }

    @FXML
    @Override
    public void setActorsLists(List<Map<String, Object>> allActors, List<Map<String, Object>> movieActors){
        addActorsTable.setItems(FXCollections.observableArrayList(allActors));
        existingActorsTable.setItems(FXCollections.observableArrayList(movieActors));
    }

    @FXML
    public void chooseImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Movie Image");

        //limit to see only images
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        //open file window
        List<File> files = fileChooser.showOpenMultipleDialog(imgBtn.getScene().getWindow());
        if (files != null){
            selectedImagPaths.clear();
            int limit = Math.min(files.size(), 3);
            for (int i = 0; i < limit; i++){
                selectedImagPaths.add(files.get(i).getAbsolutePath());
            }
            showAlert("Images selected", selectedImagPaths.size() + " images selected were added", null, Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void clearImages() {
        selectedImagPaths.clear();
        imagesCleared = true;
        showAlert("Images Marked for Deletion", "The images will be removed only after you press Save.",
                null, Alert.AlertType.WARNING);
    }

    @Override
    public boolean areImagesCleared() {
        return imagesCleared;
    }

    @Override
    public void setGenreOptions(List<String> genres){
        genraBox.setItems(FXCollections.observableArrayList(genres));
        genraBox.getSelectionModel().selectFirst();
    }

    @Override
    public void setCategoryOptions(List<String> genres){
        categoryBox.setItems(FXCollections.observableArrayList(genres));
        categoryBox.getSelectionModel().selectFirst();
    }

    @Override
    public String getTitle() { return titleField.getText(); }
    @Override
    public int getYear() {
        try {
            return parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Year must be a number", null, Alert.AlertType.ERROR);
            throw new RuntimeException("Year must be a number", e);
        }
    }

    @Override
    public int getDuration() {
        try{
            return parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Duration must be a number", null, Alert.AlertType.ERROR);
            throw new RuntimeException("Duration must be a number", e);
        }
    }

    @Override
    public String getDirectorName() { return directorField.getText(); }
    @Override
    public String getScreenwriterName() { return screenwriterField.getText(); }
    @Override
    public String getGenre() { return genraBox.getValue(); }
    @Override
    public String getCategory() { return categoryBox.getValue(); }
    @Override
    public ArrayList getSelectedActors() { return new ArrayList<>(existingActorsTable.getItems()); }
    @Override
    public List<String> getSelectedImagePaths(){
        return selectedImagPaths;
    }

    public void showAlert(String title, String header, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
