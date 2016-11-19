/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author poytr1
 */
public class Heart extends Parent{
    private ImageView content;
    
    public Heart() {
        content = new ImageView();
        getChildren().add(content);
        Image image = new Image(Heart.class.getResourceAsStream(Config.IMAGE_DIR + "heart.png"));
        content.setImage(image);
        content.setScaleX(0.05);
        content.setScaleY(0.05);
        setMouseTransparent(true);
    }
    
    
}
