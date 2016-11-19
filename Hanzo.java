/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

//import java.util.jar.Pack200;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author poytr1
 */
public class Hanzo extends Parent{
    private ImageView imageView;
    private double size;
    
    public void changeSize (double size) {
        imageView.setScaleX(size);
        imageView.setScaleY(size);
    }
    
    public double getsize () {
        return this.size;
    }
    
    public Hanzo() {
        imageView = new ImageView();
        getChildren().add(imageView);
        Image image = new Image(Hanzo.class.getResourceAsStream(Config.IMAGE_DIR + "hanzo.png"));
        imageView.setImage(image);
        this.size = 0.2;
        changeSize(size);
        //imageView.setScaleX(BASELINE_OFFSET_SAME_AS_HEIGHT);
        //getChildren().add(imageView);
        setMouseTransparent(true);
    }
}
