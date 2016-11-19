/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.scene.Parent;
//import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author poytr1
 */
public class Powerbar extends Parent{
    private Rectangle bar ;
    private Rectangle power;
    
    public void update(int energe) {
       // power.setFill(Color.RED);
        power.setX(bar.getX());
        power.setY(bar.getY());
        power.setWidth(energe);
    }
    
    public Powerbar() {
        bar = new Rectangle();
        power = new Rectangle(0,20);
        getChildren().add(bar);
        getChildren().add(power);
        bar.setWidth(150);
        bar.setHeight(20);
        bar.setX(60);
        bar.setY(Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT + 30);
        bar.setFill(Color.WHITE);
        power.setFill(Color.RED);
        setMouseTransparent(true);
    }
 
    
}
