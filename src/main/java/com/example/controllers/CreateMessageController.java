package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class CreateMessageController implements Initializable, PopupController {

    @FXML
    private Button btCancel;
    @FXML
    private Button btSubmit;
    @FXML
    private TextArea taBodyText;
    @FXML
    private TextArea taHeaderText;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	final int max_chars_header = 60;
    	final int max_chars_body = 255;
    	
    	// sets character limit for header and body text areas
    	taHeaderText.setTextFormatter(new TextFormatter<String>(change ->
    	change.getControlNewText().length() <= max_chars_header ? change : null));
    	
    	taBodyText.setTextFormatter(new TextFormatter<String>(change ->
    	change.getControlNewText().length() <= max_chars_body ? change : null));
    	
		
		// Sets cancel creating message to button
		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();
			}

		});
		
		// Creates message to database when clicked
		btSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// checks if there is text in both header and body
				if(taHeaderText.getText().trim().length() > 0 && taBodyText.getText().trim().length() > 0) {
					HelperUtils.createMessage(taHeaderText.getText(),taBodyText.getText(), true, null);
					SceneChanger.messageController.constructTvMessages();
					Stage stage = (Stage) btSubmit.getScene().getWindow();
					stage.close();
				}else {
					HelperUtils.showAlert("Empty Header and/or Body Text", "Message failed to send due to empty header or body text.");
				}

			}

		});
    	
    	
    	
		
	}

}
