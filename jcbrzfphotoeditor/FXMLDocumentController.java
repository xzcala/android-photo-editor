/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcbrzfphotoeditor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jon
 */
public class FXMLDocumentController extends AbstractPhotoEditor implements Initializable {

    private ArrayList<Filter> filters;
    private Filter currentFilter;

    @FXML
    @Override
    public void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        Stage stage = (Stage) rootPane.getScene().getWindow();

        fileChooser.setTitle("Select Image to Edit");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            Image img = new Image(file.toURI().toString());
            view.setImage(img);
        }
    }

    @FXML
    @Override
    public void handleSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) rootPane.getScene().getWindow();

        fileChooser.setTitle("Save Image");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {

            try {

                ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(), null), "png", file);

            } catch (IOException ioex) {

                displayExceptionAlert(ioex);
            } catch (Exception ex) {

                displayExceptionAlert(ex);
            }
        }
    }

    @FXML
    @Override
    public void loadAbout(ActionEvent e) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("AboutScene.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    @Override
    public void mainScreen(ActionEvent e) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filters = new ArrayList<>();
        filters.add(new Bright());
        filters.add(new Dim());
        filters.add(new Old());
        for (Filter filter : filters) {
            MenuItem menuItem = new MenuItem(filter.getName());
            menuItem.setUserData(filter);
            menuItem.setOnAction((ActionEvent event) -> {
                filter.applyFilter(view);
                MenuItem menu = (MenuItem) event.getSource();
                Filter filterItem = (Filter) menuItem.getUserData();
            });
            filterMenu.getItems().add(menuItem);
        }
        currentFilter = filters.get(0);
        
        brightnessSlider.valueProperty().bindBidirectional(brightness);
        brightness.addListener((ObservableValue<? extends Number> observableValue, Number number, Number t1) -> {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness((1 - brightness.get()) * -1);
            colorAdjust.setSaturation(saturation.get());
            
            SepiaTone tone = new SepiaTone();
            tone.setLevel(sepia.get());
            
            colorAdjust.setInput(tone);
            
            view.setEffect(colorAdjust);
        });

        saturationSlider.valueProperty().bindBidirectional(saturation);
        saturation.addListener((ObservableValue<? extends Number> observableValue, Number number, Number t1) -> {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness((1 - brightness.get()) * -1);
            colorAdjust.setSaturation(saturation.get());
            
            SepiaTone st = new SepiaTone();
            st.setLevel(sepia.get());
            
            colorAdjust.setInput(st);
            
            view.setEffect(colorAdjust);
        });

        sepiaSlider.valueProperty().bindBidirectional(sepia);
        sepia.addListener((ObservableValue<? extends Number> observableValue, Number number, Number t1) -> {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness((1 - brightness.get()) * -1);
            colorAdjust.setSaturation(saturation.get());
            
            SepiaTone st = new SepiaTone();
            st.setLevel(sepia.get());
            
            colorAdjust.setInput(st);
            
            view.setEffect(colorAdjust);
        });
    }

    @Override
    public void start() {
        //
    }

}
