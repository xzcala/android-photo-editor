/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcbrzfphotoeditor;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jon
 */
public class Old implements Filter{

    private final String name = "Old";
    public DoubleProperty brightness = new SimpleDoubleProperty(1);
    public DoubleProperty saturation = new SimpleDoubleProperty(0);
    public DoubleProperty sepia = new SimpleDoubleProperty(0);
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void applyFilter(ImageView view) {
        
        brightness.set(1.3);
        saturation.set(0.2);
        sepia.set(0);
        
        ColorAdjust adjust = new ColorAdjust();
        adjust.setBrightness((1 - brightness.get()) * -1);
        adjust.setSaturation(saturation.get());

        SepiaTone st = new SepiaTone();
        st.setLevel(sepia.get());

        adjust.setInput(st);

        view.setEffect(adjust);
    }
    
}
