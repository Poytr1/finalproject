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
        Image image = Config.getImages().get(Config.IMAGE_JUNKRAT);
        content.setImage(image);
        content.setFitWidth(Config.SCREEN_WIDTH/15);
        content.setFitHeight(Config.SCREEN_HEIGHT/12);
        setMouseTransparent(true);
    }
    
}
