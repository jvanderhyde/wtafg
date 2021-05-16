//World's tiniest fighting game JavaFX application
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.wtfg;

import java.util.HashSet;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class WtfgJavaFX extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        final Canvas canvas = new Canvas(640, 360);
        final Group root = new Group();
        root.getChildren().add( canvas );
        final Scene scene = new Scene(root);
        
        HashSet<String> input = new HashSet<>();
        scene.setOnKeyPressed((KeyEvent e) ->
            input.add(e.getCode().toString()));
        scene.setOnKeyReleased((KeyEvent e) ->
            input.remove(e.getCode().toString()));
        
        final FightingGame game = new FightingGame();
        
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                game.update(currentNanoTime, input, canvas.getGraphicsContext2D());
            }
        }.start();
        
        primaryStage.setTitle("World's Tiniest Arena Fighting Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
