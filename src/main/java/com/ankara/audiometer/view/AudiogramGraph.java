package com.ankara.audiometer.view;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AudiogramGraph extends BorderPane{
    private Canvas canvas;
    private GraphicsContext gc;

    private Label hearingLabel;
    private Label frequencyLabel;

    public Pane audioGraph;
    private final int[] FREQUENCIES = {250, 500, 1000, 2000,  4000, 8000};
    private final int MIN_DB = -10;
    private final int MAX_DB = 120;

    // Padding and spacing for the grid
    private double padding;
    private double spacingX,spacingY;

    public AudiogramGraph(){
        setPrefSize(1200, 800);
        setPadding(new Insets( 20, 0, 0, 0));
        setStyle("-fx-background-color: white;");

        this.frequencyLabel = new Label("Frequency Level (Hz)");
        frequencyLabel.setFont(new Font(16));
        this.hearingLabel = new Label("Hearing Level (db) ");
        hearingLabel.setFont(new Font(16));
        hearingLabel.setRotate(-90.0);

        this.canvas = new Canvas(1100,800);
        this.gc = canvas.getGraphicsContext2D();
        
        this.padding = 50;
        this.spacingX = (canvas.getWidth() - padding * 2) / (FREQUENCIES.length - 1);
        this.spacingY = (canvas.getHeight() - padding * 2) / (MAX_DB - MIN_DB) * 10; // 10 dB steps

        // Draw the audiogram grid
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        drawGrid();

        plotPoint(1000, 30, true); // Plot red O for right ear
        plotPoint(2000, 50, false); // Plot blue X for left ear

        // Place elements in appropriate areas on the BorderPane
        setTop(frequencyLabel);
        setAlignment(frequencyLabel, Pos.CENTER);
        setLeft(hearingLabel);
        setAlignment(hearingLabel, Pos.CENTER);
        setCenter(canvas);
        setAlignment(canvas, Pos.CENTER);
    }

    public void drawGrid() {
        gc.setStroke(Color.LIGHTGRAY);
         // Draw vertical lines and frequency labels
        for (int i = 0 ; i < FREQUENCIES.length ; i++) {
            double currentX = padding + (i * spacingX);

            // Draw vertical line for current frequency
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(currentX, padding, currentX, canvas.getHeight() - padding);

            // Draw the text label for the frequency
            gc.setFill(Color.BLACK);
            // currentX - 12 to center the text above the line, padding - 15 to position it above the top line
            gc.fillText(String.valueOf(FREQUENCIES[i]), currentX - 12, padding - 15);
        }
         // Draw horizontal lines for dB levels and labels
        for (int i = 0 ; i < ((MAX_DB - MIN_DB) / 10) + 1; i++) {
            double currentY = padding + (i * spacingY);

            // Draw horizontal line for current dB level
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(padding, currentY, canvas.getWidth() - padding , currentY);

            // Draw the text label for the dB level
            gc.setFill(Color.BLACK);
            // currentY + 5 to position it slightly below the line, padding - 30 to position it to the left of the line
            gc.fillText(String.valueOf(MIN_DB + (i * 10)), padding - 30, currentY + 5);
        }

    }

    // Method to plot a point for the right ear (O) and left ear (X) based on frequency and intensity
    public void plotPoint(int frequency, int intensity, boolean isRightEar) {

        // Find the index of the frequency in the FREQUENCIES array
        int freqIndex = -1;
        for (int i = 0; i < FREQUENCIES.length; i++) {
            if (FREQUENCIES[i] == frequency) {
                freqIndex = i;
                break;
            }
        }

        // If frequency is not found, throw an exception
        if (freqIndex == -1) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }

        // Calculate the x and y coordinates for the point based on the frequency index and intensity
        double x = padding + (freqIndex * spacingX);
        double y = padding + ((intensity - MIN_DB) / 10.0 * spacingY);

        // Draw a point at the calculated coordinates
        if(isRightEar) // If right ear, draw a red O
        {  
            gc.setStroke(Color.RED);
            gc.setLineWidth(2.0); // Set line width for better visibility
            gc.strokeOval(x - 7, y - 7, 14, 14); // Draw a circle with radius 7
        }
        else // If left ear, draw a blue X
        {          
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(1.0); // Set line width for better visibility
            gc.setFont(new Font(20)); // Set font size for better visibility
            gc.strokeText("X", x - 5, y + 5); // Draw an X outline at the point
            gc.setFill(Color.BLUE); 
            gc.fillText("X", x - 5, y + 5); // Fill the outlined X with the same color
        }

    }

}