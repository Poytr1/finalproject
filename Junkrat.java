/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 *
 * @author poytr1
 */
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Junkrat  extends Parent{
    private ImageView content;
    public Junkrat() {
        content = new ImageView();
        getChildren().add(content);
        Image image = new Image(Junkrat.class.getResourceAsStream(Config.IMAGE_DIR + "junkrat.png"));
        content.setImage(image);
        content.setScaleX(0.2);
        content.setScaleY(0.2);
        setMouseTransparent(true);
    }
    
}
