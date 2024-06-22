package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MessagePopupController implements Initializable, PopupController {

	@FXML
	private Button btClose;
	@FXML
	private Label lbHeaderText;
	@FXML
	private TextArea taBodyText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Sets Close Event to Button
		btClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btClose.getScene().getWindow();
				stage.close();
			}

		});

	}
	
	// Sets header text to the header text label
	public void setHeaderText(String header) {
		lbHeaderText.setText(header);
	}
	
	public void setBodyText(String body) {
		taBodyText.setText(body);
	}

}
