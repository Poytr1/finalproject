/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static MainFrame mainFrame;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
    
    @Override public void start(Stage stage) {
        Config.initialize();    //to initialize the image destination
        Group root = new Group();     
        mainFrame = new MainFrame(root);    //initialize the frame 
        stage.setTitle("Hanzo VS Junkrat!");    //initialize the stage
        stage.setResizable(false);
        stage.setWidth(Config.SCREEN_WIDTH + 2*Config.WINDOW_BORDER);
        stage.setHeight(Config.SCREEN_HEIGHT+ 2*Config.WINDOW_BORDER + Config.TITLE_BAR_HEIGHT);
        Scene scene = new Scene(root);     //initialize the scene
        scene.setFill(Color.BLACK);
        stage.setScene(scene);         //to put rhe new scene into the stage
        mainFrame.changeState(MainFrame.SPLASH);      //state = 0
        stage.show();          //stage to show
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public class MainFrame {
        // Instance of scene root node
        private Group root;

        // Instance of splash (if exists)
        private Splash splash;

        // Instance of level (if exists)
        private Level level;

        // Number of lifes
        private int lifeCount;

        // Current score
        private int score;

        private MainFrame(Group root) {
            this.root = root;
        }

        public int getState() {
            return state;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getLifeCount() {
            return lifeCount;
        }

     /*   public void increaseLives() {
            lifeCount = Math.min(lifeCount + 1, Config.MAX_LIVES);
        }
     */
        public void decreaseLives() {
            lifeCount--;
        }

        // Initializes game (lifes, scores etc)
        public void startGame() {
            lifeCount = 3;
            score = 0;
            changeState(1);
        }

        // Current state of the game. The next values are available
        // 0 - Splash
        public static final int SPLASH = 0;
        // 1..Level.LEVEL_COUNT - Level
        private int state = SPLASH;    //initialize the state

        public void changeState(int newState) {
            this.state = newState;
            if (splash != null) {
                splash.stop();
            }
            if (level != null) {
                level.stop();
            }
        
            if (state < 1 /*|| state > LevelData.getLevelsCount()*/) {
                root.getChildren().remove(level);
                level = null;
                splash = new Splash();
                root.getChildren().add(splash);
                splash.start();
            } else {
                root.getChildren().remove(splash);
                splash = null;
                level = new Level(state);
                root.getChildren().add(level);
                level.start();
            }
        }
    }

}
