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

        plotPointO(1000, 30);
        plotPointX(2000, 50);

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

    // Method to plot a point for the right ear (O) based on frequency and intensity
    public void plotPointO(int frequency, int intensity) {

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
        gc.setStroke(Color.RED);
        gc.setLineWidth(2.0); // Set line width for better visibility
        gc.strokeOval(x - 5, y - 5, 10, 10); // Draw a circle with radius 5
    }

    // Same thing as plotPointO but for X points (left ear)
    public void plotPointX(int frequency, int intensity) {

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
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1.0); // Set line width for better visibility
        gc.strokeText("X", x - 5, y + 5); // Draw an X at the point
    }

}