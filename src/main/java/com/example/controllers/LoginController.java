package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.HelperUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class LoginController implements Initializable {

	//FMXL fields
	@FXML
	private TextField tfEmployeeId;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private Button btSubmit;

	String userId;
	
	// TODO set char(9) limit to XNumber input
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize user id
    	final int max_chars_id = 9;
    	
    	tfEmployeeId.setTextFormatter(new TextFormatter<String>(change ->
    	change.getControlNewText().length() <= max_chars_id ? change : null));
		// Login Event
				btSubmit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						userId = tfEmployeeId.getText();
						HelperUtils.authenticateUser(event, userId, pfPassword.getText());
						
					}


				});

	}

}
