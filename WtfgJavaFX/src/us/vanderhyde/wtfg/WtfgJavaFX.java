//World's tiniest fighting game JavaFX application
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.wtfg;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class WtfgJavaFX extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Canvas canvas = new Canvas(1280, 720);
        Group root = new Group();
        root.getChildren().add( canvas );
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("World's Tiniest Fighting Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
