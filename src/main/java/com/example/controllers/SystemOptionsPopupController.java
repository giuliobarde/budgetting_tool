package com.example.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.text.ParsePosition;

import com.example.cus1166.Main;
import com.example.objects.Department;
import com.example.userclasses.BudgetAdmin;
import com.example.utils.Connect;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SystemOptionsPopupController implements Initializable, PopupController{
	

    @FXML
    private Button btClose;

    @FXML
    private Button btCreateEmployee;
    
    @FXML
    private Button btChangePassword;

    @FXML
    private ComboBox<Department> cdDepartment;


    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// check if user is system admin
		if (!Main.getSessionUser().getRole().equals("System Admin")) {
			hideAdminOptions();
		}
		
		
        btCreateEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	SceneChanger.changeToCreateEmployee(event);
            }
        });
        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btClose.getScene().getWindow();
                stage.close();
            }
        });
        
        btChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	SceneChanger.changeToChangePassword(event);
            }
        });
		
	}

	private void hideAdminOptions() {
		btCreateEmployee.setVisible(false);
		btCreateEmployee.managedProperty().bind(btCreateEmployee.visibleProperty());
		
	}

}