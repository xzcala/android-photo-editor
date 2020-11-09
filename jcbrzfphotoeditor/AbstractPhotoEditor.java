/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcbrzfphotoeditor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 *
 * @author Jon
 */
abstract class AbstractPhotoEditor {
    
    @FXML
    Slider brightnessSlider;
    
    @FXML
    Slider saturationSlider;
    
    @FXML
    Slider sepiaSlider;
    
    @FXML
    Menu filterMenu;
    
    @FXML
    Text filterNameText;
    
    public DoubleProperty brightness = new SimpleDoubleProperty(1);
    public DoubleProperty saturation= new SimpleDoubleProperty(0);
    public DoubleProperty sepia= new SimpleDoubleProperty(0);
    
    @FXML
    AnchorPane rootPane;

    @FXML
    Label label;

    @FXML
    ImageView view;

    @FXML
    MenuItem load, save;
    
   // ArrayList<Visualizer> visualizers;
    
    public abstract void handleOpen(ActionEvent event);
    public abstract void handleSave(ActionEvent event);
    
    //this code came from Prof. Wergeles' Text Editor code that we went over in class
    public final void displayExceptionAlert(Exception ex){
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(ex.getClass().getCanonicalName());
        alert.setContentText(ex.getMessage());
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label exceptionLabel = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(exceptionLabel, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait(); 
    };
    
    public abstract void loadAbout(ActionEvent event)throws IOException;
    public abstract void mainScreen(ActionEvent event)throws IOException;
    public abstract void start();
}
