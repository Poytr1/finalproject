/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

//import javafx.event.EventDispatchChain;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import finalproject.Main.MainFrame;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author poytr1
 */
public class Level extends Parent {
    
    private static final Main.MainFrame mainFrame = Main.getMainFrame();

    private ArrayList<Junkrat> junkrats;
    private ArrayList<Junkrat> fadeJunkrats;
    private ArrayList<Heart> hearts;
    private int junkratCount ;
    private static final int JUNK_NUM = 4;
    private Group group;
    
    //states
    //0 - starting level
    //1 - ready
    //2 - playing
    //3 - game over  
    private static final int STARTING_LEVEL = 0;
    private static final int READY = 1;
    private static final int PLAYING = 2;
    private static final int GAME_OVER = 3;
   
    private int state;
    private int levelNumber;
    private double junkratDir = 0;
    private Text roundCaption;
    private Text round;
    private Text scoreCaption;
    private Text score;
    private Text livesCaption;   
    private ImageView message;
    private Timeline startingTimeline;
    private Timeline timeline;
    private Group infoPanel;
    private Junkrat junkrat;
    private ImageView junkratshadow;
    private Hanzo hanzo;
    private Powerbar powerbar;
    private Arrow arrow_unshot;
    private int power = 0;
    private int stateArg = 0;
    private boolean isPress;
    private double angle;
    private double arrowDirx;
    private double arrowDiry;
    private double timer = 0;
   
    
    public Level(int levelNumber) {
        group = new Group();
        getChildren().add(group);
        initContent(levelNumber);
    }
    
    private void initStartingTimeline() {
        startingTimeline = new Timeline();
        KeyFrame kf1 = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                message.setVisible(true);
                state = STARTING_LEVEL;
            }
        }, new KeyValue(message.opacityProperty(), 0));
        KeyFrame kf2 = new KeyFrame(Duration.millis(1500), new KeyValue(message.opacityProperty(), 1));
        KeyFrame kf3 = new KeyFrame(Duration.millis(3000), new KeyValue(message.opacityProperty(), 1));
        KeyFrame kf4 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                message.setVisible(false);
                powerbar.setVisible(true);
                junkratDir = Config.JUNKRAT_MIN_SPEED;
                state = READY;
            }
        },new KeyValue(message.opacityProperty(), 0));
        
        startingTimeline.getKeyFrames().addAll(kf1,kf2,kf3,kf4);
    }
    
    private void initTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(timeline.INDEFINITE);
        
        KeyFrame kf = new KeyFrame(Config.ANIMATION_TIME, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Iterator<Junkrat> junkratIterator = fadeJunkrats.iterator(); 
                while(junkratIterator.hasNext()) {
                    Junkrat junkrat = junkratIterator.next();
                    junkrat.setOpacity(junkrat.getOpacity() - 0.1);
                    if(junkrat.getOpacity() <= 0) {
                        junkrat.setVisible(false);
                        junkratIterator.remove();
                    }
                }
                if(state != STARTING_LEVEL) {
                    stateArg++;
                    /*if(stateArg == 90) {
                        stateArg = 0;
                    } */
                    junkrat.setRotate(-stateArg * junkratDir);
                    junkrat.setTranslateX(630 - stateArg * junkratDir / junkrat.getScaleX()/2);
                    junkratshadow.setRotate(-stateArg * junkratDir);
                    junkratshadow.setTranslateX(640 - stateArg * junkratDir / junkrat.getScaleX()/2);
                    powerbar.update(power);
                    if((hanzo.getTranslateX()) == (junkrat.getTranslateX())) {
                        lostLife();
                        stateArg = 0;
                    }
                   // arrowDirx = Math.abs(Math.cos(Math.abs(angle / 180 * Math.PI)) * power);
                   // arrowDiry = Math.abs(Math.sin(Math.abs(angle / 180 * Math.PI)) * power);
                    if((arrowDirx>0) && (!isPress)) {
                        arrow_unshot.setTranslateX(-800 + timer * arrowDirx);
                        arrow_unshot.setTranslateY(505 - (-(10 * Math.pow(timer, 2)) + arrowDiry * timer));
                        arrow_unshot.setRotate(angle - timer/0.3 * angle / (arrowDiry / 5));
                        timer += 0.3;
                    }
                    
                    if (arrow_unshot.getTranslateY() > 600) {
                        arrowDirx = 0;
                        arrowDiry = 0;
                        timer = 0;
                        arrow_unshot.setRotate(0);
                        arrow_unshot.setTranslateX(-800);
                        arrow_unshot.setTranslateY(505);
                    }
                }
            }
        });
        KeyFrame kf1 = new KeyFrame(Config.ANIMATION_TIME_FAST, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isPress) {
                    if (power<=150)
                        power += 4;
                    else
                        ;
                    arrowDirx = Math.abs(Math.cos(Math.abs(angle / 180 * Math.PI)) * power);
                    arrowDiry = Math.abs(Math.sin(Math.abs(angle / 180 * Math.PI)) * power);
                    angle = arrow_unshot.getRotate();
                   //S ystem.out.println(junkrat.getx());
                    
                }
                else {
                    power = 0;
                }
            }
        });
        timeline.getKeyFrames().addAll(kf,kf1);
     
    }
    
    public void start() {
        startingTimeline.play();
        timeline.play();
        
        group.getChildren().get(0).requestFocus();
        updateScore(0);
        updateLives();
    }
    
    public void stop() {
        startingTimeline.stop();
        timeline.stop();
    }
    
    private void initLevel() {
        for(int i = 0 ; i < JUNK_NUM ; i++) {
        Junkrat junkrat = new Junkrat();
        junkrat.setTranslateX(Config.SCREEN_WIDTH);
        junkrat.setTranslateY(Config.SCREEN_HEIGHT);
        junkrats.add(junkrat);
        junkratCount ++;
        }
    }
    
    private Junkrat getJunkrat(int i) {
        return junkrats.get(i);
    }
    
    private void updateScore(int inc) {
        mainFrame.setScore(mainFrame.getScore() + inc);
        score.setText(mainFrame.getScore() + "");
    }
    
    private void rotateArrow(double shita) {
        arrow_unshot.setRotate(- shita * 150);     
    }
    private void updateLives() {
        Heart newHeart;
        if(mainFrame.lifeCount == 3) {
            for(int i = 0 ; i < mainFrame.getLifeCount() ; i ++) {
            Heart heart = new Heart();
            heart.setTranslateX(95 + (i-1) * 35);
            heart.setTranslateY(-178);
            heart.setVisible(true);
            hearts.add(heart);
            infoPanel.getChildren().add(heart);
        }}
        else {
            newHeart = hearts.get(hearts.size() -1);
            hearts.remove(newHeart);
            infoPanel.getChildren().remove(newHeart);
        }
    }
    private void lostLife() {
        mainFrame.decreaseLives();
        if (mainFrame.getLifeCount() < 0) {
            state = GAME_OVER;
            hanzo.setVisible(false);
            junkrat.setVisible(false);
            junkratshadow.setVisible(false);
            //arrow_unshot.setVisible(false);
            message.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "gameover.png")));
            message.setTranslateX((Config.FIELD_WIDTH - message.getImage().getWidth())/2);
            message.setTranslateY((Config.FIELD_HEIGHT - message.getImage().getHeight())/2 + Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT);
            message.setVisible(true);
            message.setOpacity(1);
        }
        else {
            mainFrame.lifeCount -- ;
            updateLives();  
        }
    }
    
    private void initInfoPanel() {
        infoPanel = new Group();
        //roundCaption
        roundCaption = new Text();
        roundCaption.setText("ROUND"); 
        roundCaption.setTextOrigin(VPos.TOP);
        roundCaption.setFill(Color.rgb(51, 102, 51));
        Font f = new Font("Impact", 18);
        roundCaption.setFont(f);
        roundCaption.setTranslateX(160);
        roundCaption.setTranslateY(30);
        //round
        round = new Text();
        round.setTranslateX(roundCaption.getTranslateX());
        round.setTranslateY(roundCaption.getTranslateY() + roundCaption.getBoundsInLocal().getHeight()+Config.INFO_TEXT_SPACE);
        round.setText(levelNumber + "");
        round.setTextOrigin(VPos.TOP);
        round.setFont(f);
        round.setFill(Color.rgb(0, 204, 102));
        //scoreCaption
        scoreCaption = new Text();
        scoreCaption.setText("SCORE");
        scoreCaption.setFill(Color.rgb(51, 102, 51));
        scoreCaption.setTranslateX(220);
        scoreCaption.setTranslateY(30);
        scoreCaption.setTextOrigin(VPos.TOP);
        scoreCaption.setFont(f);
        //score
        score = new Text();
        score.setTranslateX(scoreCaption.getTranslateX());
        score.setTranslateY(scoreCaption.getTranslateY() + scoreCaption.getBoundsInLocal().getHeight() + Config.INFO_TEXT_SPACE);
        score.setFill(Color.rgb(0, 204, 102));
        score.setTextOrigin(VPos.TOP);
        score.setFont(f);
        score.setText("");
        //lifesCaption
        livesCaption = new Text();
        livesCaption.setText("LIFE");
        livesCaption.setTranslateX(300);
        livesCaption.setTranslateY(30);
        livesCaption.setFill(Color.rgb(51, 102, 51));
        livesCaption.setTextOrigin(VPos.TOP);
        livesCaption.setFont(f);
        //info settings
        //Color INFO_LEGEND_COLOR = Color.rgb(0, 114, 188);
        int infoWidth = Config.SCREEN_WIDTH;
        int infoHeight = Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT;
        ImageView black = new ImageView();
        black.setFitWidth(infoWidth);
        black.setFitHeight(infoHeight);
        black.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "black.jpeg")));
        
        ImageView verLine = new ImageView();
        verLine.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "vline.png")));
        verLine.setScaleX(2);
        verLine.setTranslateY(infoHeight - 3);
        
        ImageView logo = new ImageView();
        logo.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "logo.png")));
        logo.setScaleX(0.3);
        logo.setScaleY(0.3);
        logo.setTranslateX(-180);
        logo.setTranslateY(-190);
        
        infoPanel.getChildren().addAll(black, verLine, logo, roundCaption, round, scoreCaption, score, livesCaption);
        infoPanel.setTranslateY(0);
        
    
    }
     private void initContent(int level) {
            state = STARTING_LEVEL;
            levelNumber = level;
            junkrats = new ArrayList<Junkrat>();
            fadeJunkrats = new ArrayList<Junkrat>();
            hearts = new ArrayList<Heart>();
            hanzo = new Hanzo();
            hanzo.setTranslateX(-170);
            hanzo.setTranslateY(400);
            hanzo.setVisible(true);
            junkrat = new Junkrat();
            junkrat.setTranslateX(630);
            junkrat.setTranslateY(400);
            junkrat.setVisible(true);
            arrow_unshot = new Arrow();
            arrow_unshot.setTranslateX(-800);
            arrow_unshot.setTranslateY(505);
            arrow_unshot.setVisible(true);
            message = new ImageView();
            message.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "ready.png")));
            message.setTranslateX((Config.FIELD_WIDTH - message.getImage().getWidth())/2);
            message.setTranslateY((Config.FIELD_HEIGHT - message.getImage().getHeight())/2 + Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT);
            message.setVisible(false);
            powerbar = new Powerbar();
            //powerbar.setVisible(false);
            //powerbar.set
           // powerbar.setTranslateY(300);
            initLevel();
            initStartingTimeline();
            initTimeline();
            initInfoPanel();
            junkratshadow = new ImageView();
            junkratshadow.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "junkratshadow.png")));
            junkratshadow.setScaleX(0.2);
            junkratshadow.setScaleY(0.2);
            junkratshadow.setTranslateX(640);
            junkratshadow.setTranslateY(400);
            junkratshadow.setOpacity(0.5);
            ImageView background = new ImageView();
            background.setFocusTraversable(true);
            background.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "background.png")));
            background.setFitWidth(Config.FIELD_WIDTH);
            background.setFitHeight(Config.FIELD_HEIGHT);
            background.setTranslateY(Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT);
            background.setOnMouseMoved(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    if (me.getY() > arrow_unshot.getTranslateY()) {
                        rotateArrow(0);
                        angle = 0;
                    }
                    else {
                        rotateArrow((double)Math.atan(((double)arrow_unshot.getTranslateY()-me.getY() + 60) / (me.getX() - arrow_unshot.getTranslateX())));
                    }
                }
                });
            background.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    isPress = true;
                }
            });
            background.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    isPress = false;
                    if (me.getButton() == MouseButton.SECONDARY) {
                        
        
                    }
                    else 
                        ;
                }
            });
            background.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if ((ke.getCode() == KeyCode.POWER) || (ke.getCode() == KeyCode.X)) {
                        Platform.exit();
                    }
                    if(state == READY && (ke.getCode() == KeyCode.SPACE || ke.getCode() == KeyCode.ENTER ||ke.getCode() == KeyCode.PLAY)) {
                        state = PLAYING;
                    }
                    if(state == GAME_OVER) {
                        mainFrame.changeState(MainFrame.SPLASH);
                    }
                    if(state == PLAYING && ke.getCode() == KeyCode.Q) {
                       // lostLife();
                        return;
                    }
                }
            });
            group.getChildren().add(background);
            group.getChildren().addAll(hanzo,junkrat,message, infoPanel,powerbar,junkratshadow,arrow_unshot);
            }   
            
}
