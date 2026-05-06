package com.ankara.audiometer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainLayout mainLayout = new MainLayout();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Audiometer");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}