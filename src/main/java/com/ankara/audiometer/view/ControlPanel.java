package com.ankara.audiometer.view;

import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;

public class ControlPanel extends VBox {
    // Control panel components
    private ComboBox<Integer> frequencyComboBox;
    private Slider intensitySlider;
    private Button sendSignalButton;
    private Label responseStatusLabel;
    private Label intensityValueLabel;
    private ToggleGroup toggleEarGroup;
    private RadioButton leftEarButton,rightEarButton;


    public ControlPanel() {
        // Initialize control panel components here
        this.frequencyComboBox = new ComboBox<>(FXCollections.observableArrayList(250, 500, 1000, 2000, 4000, 8000));
        this.intensitySlider = new Slider(-10, 120, 30);
        this.sendSignalButton = new Button("Send Signal");
        this.responseStatusLabel = new Label("Status: Ready");
        this.intensityValueLabel = new Label(String.format("Current: %.0f dB HL", intensitySlider.getValue()));
        this.toggleEarGroup = new ToggleGroup();
        this.leftEarButton = new RadioButton("Left");
        this.rightEarButton = new RadioButton("Right");

        // Add components to the layout
        setSpacing(20);
        setPadding(new Insets(15));


        // Configure components
        intensitySlider.setShowTickLabels(true);
        intensitySlider.setShowTickMarks(true);
        intensitySlider.setMajorTickUnit(10);
        intensitySlider.setMinorTickCount(1);
        intensitySlider.setSnapToTicks(true);
        intensitySlider.setBlockIncrement(10);

        // Update intensity value label when slider changes
        intensitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double step = 5.0;
            double roundedValue = Math.round(newVal.doubleValue() / step) * step;

            intensityValueLabel.setText(String.format("Current: %.0f dB HL", roundedValue));
        });

        // Set button to fill available width
        sendSignalButton.setMaxWidth(Double.MAX_VALUE);

        // Set toggle groups for left and right ear buttons respectively
        leftEarButton.setToggleGroup(toggleEarGroup);
        rightEarButton.setToggleGroup(toggleEarGroup);

        // Put ear buttons in a HBox for better visibilty
        HBox earBox = new HBox(15,leftEarButton,rightEarButton);


        // Add components to the VBox
        this.getChildren().addAll(
        new Label("Select Ear:"),
        earBox,
        new Label("Test Frequency (Hz):"),
        frequencyComboBox,
        new Label("Intensity Level (dB HL):"),
        intensitySlider,
        intensityValueLabel,
        sendSignalButton,
        responseStatusLabel
    );
    }

    public int getSelectedIntensity() {
        // Return slider's value rounded up, so voice generation is flawless
        return (int) (Math.round(intensitySlider.getValue() / 5.0) * 5.0);
    }

    public Integer getSelectedFrequency() {
        // Return frequency from combo box
        return frequencyComboBox.getValue();
    }

    public Boolean isRightEarSelected() {
        // Return a boolean isRightEar based on selected toggle from the toggle group toggleEarGroup
        RadioButton selectedEar = (RadioButton) toggleEarGroup.getSelectedToggle();

        if (selectedEar != null) {
            if (selectedEar.getText().equals("Left")) {
                return false;
            }
            else {
                return true;
            }
        }
        
        // If none of the ears are selected functions returns null
        return null;
    }
}