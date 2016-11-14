/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author poytr1
 */
public class Splash extends Parent {
    
    private static final int STATE_SHOW_TITLE = 0;
    private static final int STATE_SHOW_STRIKE = 1;
    private static final int STATE_LOGO = 2;

    private static final int LOGO_AMPLITUDE_X = Config.SCREEN_WIDTH * 2 / 3;
    private static final int LOGO_AMPLITUDE_Y = Config.SCREEN_WIDTH / 2;
    
    private ImageView background;
    private ImageView hanzo;
    private ImageView hanzoshadow;
    private ImageView junkrat;
    private ImageView junkratshadow;
    private Timeline timeline;
    private int state;
    private int stateArg;
    private ImageView vs;
    private ImageView vsshadow;
    private ImageView pressanykey;
    private ImageView pressanykeyshadow;
    private ImageView LOGO;
    private ImageView[] NODES;
    private ImageView[] NODES_SHADOWS;
    
    
    
    private void initTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME , new EventHandler<ActionEvent>() {
            public  void handle(ActionEvent event) {
                if (state == STATE_SHOW_TITLE) {
                    stateArg++;
                    int center = Config.SCREEN_WIDTH/2;
                    int offset = (int)(Math.cos(stateArg / 4.0) * (40 - stateArg) / 40 *center);
                    hanzo.setTranslateX(center - hanzo.getImage().getWidth()/1.5f  + offset);
                    junkrat.setTranslateX(center + junkrat.getImage().getWidth()/8f - offset);
                    if(stateArg == 40) {
                        stateArg = 0;
                        state = STATE_SHOW_STRIKE;
                        
                    }
                    return;
                }
                if (state == STATE_SHOW_STRIKE) {
                    if(stateArg == 0) {
                        vs.setTranslateX(junkrat.getImage().getWidth() - hanzo.getImage().getWidth());
                        vs.setScaleX(0);
                        vs.setScaleY(0);
                        vs.setVisible(true);
                    }
                    stateArg ++;
                    double coef = stateArg / 200f;
                   // hanzo.setTranslateX(junkrat.getTranslateX()/5 + (junkrat.getImage().getWidth() - hanzo.getImage().getWidth() )/ 2f * (1 - coef) );
                    vs.setScaleX(coef);
                    vs.setScaleY(coef);
                    vs.setRotate((30 - stateArg) * 2);
                    if (stateArg == 30) {
                        stateArg = 0;
                        state = STATE_LOGO;
                    }
                    return;
                }
                // LOGO STATE add
                if (pressanykey.getOpacity()<1) {
                    pressanykey.setOpacity(pressanykey.getOpacity() + 0.05f);
                }
                stateArg --;
                double x = LOGO_AMPLITUDE_X * Math.cos(stateArg / 100.0);
                double y = LOGO_AMPLITUDE_Y * Math.sin(stateArg / 100.0);
                if(y < 0 ) {
                    for(Node node:NODES_SHADOWS) {
                        node.setTranslateX(-1000);
                    }
                    return;
                }
                double LOGOx = Config.SCREEN_WIDTH / 2 + x;
                double LOGOy = Config.SCREEN_HEIGHT / 2 - y;
                LOGO.setTranslateX( LOGOx - LOGO.getImage().getWidth() / 2 + 100);
                LOGO.setTranslateY(LOGOy - LOGO.getImage().getHeight() / 2 + 250);
                LOGO.setRotate(-stateArg);
                for (int i = 0 ; i < NODES.length ; i ++) {
                    NODES_SHADOWS[i].setOpacity(y / LOGO_AMPLITUDE_Y / 2 );
                    NODES_SHADOWS[i].setTranslateX(NODES[i].getTranslateX() + (NODES[i].getTranslateX()+NODES[i].getImage().getWidth()/ 2 - LOGOx)/20);
                    NODES_SHADOWS[i].setTranslateY(NODES[i].getTranslateY() + (NODES[i].getTranslateY()+NODES[i].getImage().getHeight()/ 2 - LOGOy)/20);
                }
            }
        });  
        timeline.getKeyFrames().add(kf);
    }
    
    public void start() {
        background.requestFocus();
        timeline.play();
    }
    
    public void stop() {
        timeline.stop();
    }
    
    Splash() {
        //initial
        state = STATE_SHOW_TITLE;
        stateArg = 0;
        initTimeline();
        
        background = new ImageView();
       // background.setFocusTraversable(true);
        background.setFocusTraversable(true);
        background.setImage(Config.getImages().get(Config.IMAGE_BACKGROUND));
        background.setFitWidth(Config.SCREEN_WIDTH);
        background.setFitHeight(Config.SCREEN_HEIGHT);
        background.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Main.getMainFrame().startGame();
            }
        });
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                Main.getMainFrame().startGame();
            }
        });
        hanzo = new ImageView();
        hanzo.setImage(Config.getImages().get(Config.IMAGE_SPLASH_HANZO));
        hanzo.setFitWidth(Config.SCREEN_WIDTH/4);
        hanzo.setFitHeight(Config.SCREEN_HEIGHT/3);
        hanzo.setTranslateX(-1000);
        hanzo.setTranslateY(hanzo.getImage().getHeight()/3);
        
        hanzoshadow = new ImageView();
        hanzoshadow.setImage(Config.getImages().get(Config.IMAGE_SPLASH_HANZOSHADOW));
        hanzoshadow.setFitWidth(Config.SCREEN_WIDTH/4);
        hanzoshadow.setFitHeight(Config.SCREEN_HEIGHT/3);
        hanzoshadow.setTranslateX(-1000);
        
        //background.setImage(Config.getImages().get(Config.IMAGE_SPLASH_BACKGROUND));
        //hanzoshadow.setTranslateX(-1000);
        junkrat = new ImageView();
        junkrat.setImage(Config.getImages().get(Config.IMAGE_SPLASH_JUNKRAT));
        junkrat.setFitWidth(Config.SCREEN_WIDTH/4);
        junkrat.setFitHeight(Config.SCREEN_HEIGHT/3);
        junkrat.setTranslateX(-1000);
        junkrat.setTranslateY(hanzo.getTranslateY() + hanzo.getImage().getHeight() / 4);
        
        junkratshadow = new ImageView();    
        junkratshadow.setImage(Config.getImages().get(Config.IMAGE_SPLASH_JUNKRATSHADOW));
        junkratshadow.setFitWidth(Config.SCREEN_WIDTH/4);
        junkratshadow.setFitHeight(Config.SCREEN_HEIGHT/3);
        junkratshadow.setTranslateX(-1000);
        
        vs = new ImageView();
        vs.setImage(Config.getImages().get(Config.IMAGE_SPLASH_VS));
        //vs.setFitWidth(200);
        //vs.setFitHeight(200);
        vs.setTranslateY((hanzo.getTranslateY() - hanzo.getImage().getHeight())/ 2 + 30);
        vs.setVisible(false);
        
        pressanykey = new ImageView();
        pressanykey.setImage(Config.getImages().get(Config.IMAGE_SPLASH_PRESSANYKEY));
        pressanykey.setTranslateX((Config.SCREEN_WIDTH - pressanykey.getImage().getWidth()) / 2 - 30);
        double y = junkrat.getTranslateY() + junkrat.getImage().getHeight() / 5 ;
        pressanykey.setTranslateY(y + (Config.SCREEN_HEIGHT - y) / 2);
        pressanykey.setOpacity(0);
        
        vsshadow = new ImageView();
        vsshadow.setImage(Config.getImages().get(Config.IMAGE_SPLASH_VSSHADOW));
        vsshadow.setScaleX(0.15);
        vsshadow.setScaleY(0.15);
       // vsshadow.setFitWidth(Config.SCREEN_WIDTH/4);
       // vsshadow.setFitHeight(Config.SCREEN_HEIGHT/3);
        vsshadow.setTranslateX(-1000);
        
        pressanykeyshadow = new ImageView();
        pressanykeyshadow.setImage(Config.getImages().get(Config.IMAGE_SPLASH_PRESSANYKEYSHADOW));
        pressanykeyshadow.setTranslateX(-1000);
        
        LOGO = new ImageView();
        LOGO.setImage(Config.getImages().get(Config.IMAGE_SPLASH_LOGO));
        LOGO.setFitWidth(200);
        LOGO.setFitHeight(200);
        LOGO.setTranslateX(-1000);
        
        NODES = new ImageView[] {hanzo, junkrat, vs, pressanykey};
        NODES_SHADOWS = new ImageView[] {hanzoshadow, junkratshadow, vsshadow, pressanykeyshadow};
        
        Group group = new Group();
        group.getChildren().add(background);
        group.getChildren().addAll(NODES);
        group.getChildren().addAll(NODES_SHADOWS);
        group.getChildren().add(LOGO);
        getChildren().add(group);
            
    }
    
}
