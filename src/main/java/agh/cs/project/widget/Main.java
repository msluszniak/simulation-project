package agh.cs.project.widget;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        VBox box = new VBox();
        stage.setScene(new Scene(box, 500, 500));
        stage.show();
    }
}
