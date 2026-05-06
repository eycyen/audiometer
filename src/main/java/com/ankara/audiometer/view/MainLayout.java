package com.ankara.audiometer.view;

import javafx.scene.layout.BorderPane;

public class MainLayout extends BorderPane {
    public MainLayout() {
        ControlPanel controlPanel = new ControlPanel();
        setPrefSize(800, 600);
        
        setLeft(controlPanel);
    }
}