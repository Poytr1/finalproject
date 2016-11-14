/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 *
 * @author poytr1
 */
public class Arrow extends Parent{
    
    private ImageView imageView;
    public Arrow() {
        imageView = new ImageView();
        Image image = new Image(Arrow.class.getResourceAsStream(Config.IMAGE_DIR + "arrow.png"));
        imageView.setImage(image);
        getChildren().add(imageView);
        setMouseTransparent(true);
    }
    
}
