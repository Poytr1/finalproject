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
import javafx.scene.shape.Rectangle;
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
    private double junkratDir;
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
    private Hanzo hanzo;
    private Arrow arrow_unshot;
    private int power = 0;
    private boolean isPress;
   
    
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
                junkrat.setVisible(false);
                hanzo.setVisible(false);
            }
        }, new KeyValue(message.opacityProperty(), 0));
        KeyFrame kf2 = new KeyFrame(Duration.millis(1500), new KeyValue(message.opacityProperty(), 1));
        KeyFrame kf3 = new KeyFrame(Duration.millis(3000), new KeyValue(message.opacityProperty(), 1));
        KeyFrame kf4 = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                message.setVisible(false);
                hanzo.setTranslateX(hanzo.getsize()/2 + 10);
                hanzo.setTranslateY(Config.SCREEN_HEIGHT - hanzo.getsize()/2 - 10);
                junkratDir = Config.JUNKRAT_MIN_SPEED;
                hanzo.setVisible(true);
                junkrat.setVisible(true);
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
                //rotate arrow ready to shot
                if(state != STARTING_LEVEL) {
                    //rotateArrow()
                }
                
            }
        });
        timeline.getKeyFrames().add(kf);
     
    }
    
    public void start() {
        startingTimeline.play();
        timeline.play();
        
        group.getChildren().get(0).requestFocus();
        updateScore(0);
        //updateLives();
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
        
        
    }
    private void initInfoPanel() {
        infoPanel = new Group();
        //roundCaption
        roundCaption = new Text();
        roundCaption.setText("ROUND"); 
        roundCaption.setTextOrigin(VPos.TOP);
        roundCaption.setFill(Color.rgb(151, 102, 52));
        Font f = new Font("Impact", 18);
        roundCaption.setFont(f);
        roundCaption.setTranslateX(128);
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
        scoreCaption.setTranslateX(164);
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
        livesCaption.setTranslateX(200);
        livesCaption.setTranslateY(30);
        livesCaption.setFill(Color.rgb(51, 102, 51));
        livesCaption.setTextOrigin(VPos.TOP);
        livesCaption.setFont(f);
        //info settings
        Color INFO_LEGEND_COLOR = Color.rgb(0, 114, 188);
        int infoWidth = Config.SCREEN_WIDTH;
        int infoHeight = Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT;
        Rectangle black = new Rectangle();
        black.setWidth(infoWidth);
        black.setHeight(infoHeight);
        black.setFill(INFO_LEGEND_COLOR);
        ImageView verLine = new ImageView();
        verLine.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "verline.png")));
        verLine.setTranslateY(infoHeight - 3);
        
        ImageView logo = new ImageView();
        logo.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "logo.png")));
        logo.setTranslateX(30);
        logo.setTranslateY(30);
        Text legend = new Text();
        legend.setTranslateX(310);
        legend.setTranslateY(30);
        legend.setText("LEGEND");
        legend.setFill(INFO_LEGEND_COLOR);
        legend.setTextOrigin(VPos.TOP);
        legend.setFont(f);
        
        infoPanel.getChildren().addAll(black, verLine, logo, roundCaption, round, scoreCaption, score, livesCaption, legend);
        infoPanel.setTranslateY(Config.FIELD_HEIGHT);
        
    
    }
     private void initContent(int level) {
            state = STARTING_LEVEL;
            levelNumber = level;
            junkrats = new ArrayList<Junkrat>();
            fadeJunkrats = new ArrayList<Junkrat>();
            message = new ImageView();
            message.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "IMAGE_READY")));
            message.setTranslateX((Config.FIELD_WIDTH - message.getImage().getWidth())/2);
            message.setTranslateY((Config.FIELD_HEIGHT - message.getImage().getHeight())/2 + Config.SCREEN_HEIGHT - Config.FIELD_HEIGHT);
            message.setVisible(false);
            initLevel();
            initStartingTimeline();
            initTimeline();
            initInfoPanel();
            ImageView background = new ImageView();
            background.setFocusTraversable(true);
            background.setImage(new Image(Level.class.getResourceAsStream(Config.IMAGE_DIR + "background.png")));
            background.setFitWidth(Config.FIELD_WIDTH);
            background.setFitHeight(Config.FIELD_HEIGHT);
            background.setOnMouseMoved(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    if (me.getY() > hanzo.getTranslateY())
                        rotateArrow(0);
                    else 
                    rotateArrow((double)Math.atan( ((double)hanzo.getTranslateY()-me.getY()) / (me.getX() - hanzo.getTranslateX())));
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
            group.getChildren().addAll(message, infoPanel);
            }   
            
}
