package com.ankara.audiometer.view;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AudiogramGraph extends  BorderPane{
    private Label hearingLabel;
    private Label frequencyLabel;
    private GridPane  decibelLevels;
    private GridPane  hertzLevels;
    private GridPane graphGrid;
    private int columns = 7;
    private int rows = 13;

    public AudiogramGraph(){
        setPrefSize(400, 400);
        setPadding(new Insets(20,10,20,10));
        setStyle("-fx-background-color: white;");

        this.frequencyLabel = new Label("Frequency Level (Hz)");
        this.hearingLabel = new Label("Hearing Level (db) ");
        hearingLabel.setRotate(-90.0);

        //Plot grid columns and rows
        this.graphGrid = new GridPane();
        for(int i = 0; i<columns; i++)
        {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.00/columns);
            graphGrid.getColumnConstraints().add(colConst);
        }
        for(int j = 0; j<rows;j++)
        {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.00/rows);
            graphGrid.getRowConstraints().add(rowConst);
        }

        //Place Panes into GridPane to draw borders and access individual cells
        Pane[][] gridPanes = new Pane[rows][columns];
        for(int i = 0; i<columns; i++){   
            for(int j = 0; j<rows; j++){
                Pane pane = new Pane();
                pane.setStyle("-fx-border-color: black; -fx-border-width: 0.2;");
                graphGrid.add(pane, i, j);
                gridPanes[j][i] = pane;
            }
        }

        //Plot the vertical graph labels
        this.decibelLevels = new GridPane();
        int decibels = -10;
        for(int j =0; j<14; j++)
        {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(15);
            if(j==13)
                rowConst.setPercentHeight(7.5);
            decibelLevels.getRowConstraints().add(rowConst);
            Pane pane = new Pane(new Label(String.valueOf((int)(decibels + 10*j))));
            decibelLevels.add(pane, 0,j);
        }
        decibelLevels.setPadding(new Insets(0,0,-20,0));

        //Plot the horizontal graph labels
        this.hertzLevels = new GridPane();
        int hertzs = 250;
        for(int i = 0; i<7; i++)
        {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(14.3);
            hertzLevels.getColumnConstraints().add(colConst);
            Pane pane = new Pane();
            if(i!=0)
                pane.getChildren().add((new Label(String.valueOf((int)(hertzs*(Math.pow(2, i-1)))))));
            hertzLevels.add(pane, i, 0);
        }
        hertzLevels.setPadding(new Insets(0,0,0, 110));

        //Add components to be placed on the top via VBox
        VBox topNodes = new VBox();
        topNodes.getChildren().addAll(frequencyLabel,hertzLevels);
        topNodes.setAlignment(Pos.CENTER);
        setTop(topNodes);

        //Add components to be placed on the left via HBox
        HBox leftNodes = new HBox(); 
        leftNodes.getChildren().addAll(hearingLabel, decibelLevels);
        leftNodes.setAlignment(Pos.CENTER);
        setLeft(leftNodes);

        setCenter(graphGrid);
    }
}