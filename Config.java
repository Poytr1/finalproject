/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.collections.ObservableList;
import javafx.util.Duration;
import javafx.scene.image.Image;
//import javafx.scene.layout.Background;

/**
 *
 * @author poytr1
 */
public final class Config {
    public static final Duration ANIMATION_TIME = Duration.millis(40);
    public static final int WINDOW_BORDER = 3;
    public static final int TITLE_BAR_HEIGHT = 19;
    public static final String IMAGE_DIR = "images/";
    public static final int SCREEN_WIDTH = 960;
    public static final int SCREEN_HEIGHT = 720;
    public static final int INFO_TEXT_SPACE = 10;
    public static final int FIELD_WIDTH = SCREEN_WIDTH;
    public static final int FIELD_HEIGHT = SCREEN_HEIGHT * 3 / 5;
    public static final int JUNKRAT_MIN_SPEED = 10;
    
    public static final int IMAGE_BACKGROUND = 0;
    public static final int IMAGE_SPLASH_JUNKRAT = 1;
    public static final int IMAGE_SPLASH_JUNKRATSHADOW = 2;
    public static final int IMAGE_SPLASH_HANZO = 3;
    public static final int IMAGE_SPLASH_HANZOSHADOW = 4;
    public static final int IMAGE_SPLASH_VS = 5;
    public static final int IMAGE_SPLASH_VSSHADOW = 6;
    public static final int IMAGE_SPLASH_PRESSANYKEY = 7;
    public static final int IMAGE_SPLASH_PRESSANYKEYSHADOW = 8;
    public static final int IMAGE_SPLASH_LOGO = 9 ;
    public static final int IMAGE_JUNKRAT = 10;
            
    
    private static final String[] IMAGES_NAMES = new String[] {
        "backgroundshadow.png" ,
        "splash/junkrat.png" ,
        "splash/junkratshadow.png" ,
        "splash/hanzo.png" ,
        "splash/hanzoshadow.png" ,
        "splash/vs.png" ,
        "splash/vsshadow.png" ,
        "splash/pressanykey.png" ,
        "splash/pressanykeyshadow.png" ,
        "splash/LOGO.png" ,
        "junkrat.png"
    };
    
    private static ObservableList<Image> images = javafx.collections.FXCollections.<Image>observableArrayList();
    
    public static ObservableList<Image> getImages() {
        return images;
    }
    public static void initialize() {
       //Image i = new Image(Config.class.getResourceAsStream("images/splash/Junkrat.png"));
       for (String imageName : IMAGES_NAMES) {
            Image image = new Image(Config.class.getResourceAsStream(IMAGE_DIR+imageName));
            if (image.isError()) {
                System.out.println("Image "+imageName+" not found");
            }
            images.add(image);
        }
        //some other images    
        
    }
    
    private Config() {
        
    }
}
