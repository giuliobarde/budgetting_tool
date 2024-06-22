package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.objects.Request;
import com.example.userclasses.SessionUser;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PCardRequestDetailsPopupController implements Initializable, PopupController{

	@FXML
    private Button btApprove;
    @FXML
    private Button btClose;
    @FXML
    private Button btDeny;
    @FXML
    private Label lbApprovalStatus;
    @FXML
    private Label lbDailyLimit;
    @FXML
    private Label lbDate;
    @FXML
    private Label lbDepartment;
    @FXML
    private Label lbMemo;
    @FXML
    private Label lbMonthlyLimit;
    @FXML
    private Label lbName;
    @FXML
    private Label lbPCardAdminOptions;
    @FXML
    private Label lbRole;
    @FXML
    private Label lbYearlyLimit;

    private Request request;
    private SessionUser employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Check if the logged in user is the one who made the request and not an admin
        if (!Main.getSessionUser().getRole().equals("PCardAdmin")) {
            hideOptions();
        }

        btApprove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneChanger.approveReasonPopup(event, PCardRequestDetailsPopupController.class.getName(), null, request);
            }
        });

        btDeny.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneChanger.denyReasonPopup(event, PCardRequestDetailsPopupController.class.getName(), null, request);
            }
        });

        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) btClose.getScene().getWindow();
                stage.close();
            }
        });
    }



	public void setRequestDetails() {
		lbApprovalStatus.setText("Approval Status: " + request.getApprovalStatus());
		lbDate.setText("Date sent: " + request.getDate().toString());
		lbDepartment.setText("Employee's Department: " + employee.getDepartmentName()); 
		lbMemo.setText("Memo: " + request.getMemo());
		lbName.setText("Employee Name: " + employee.getfName() + " " + employee.getlName()); 
		lbRole.setText("Employee Role: " + employee.getRole()); 
		lbDailyLimit.setText("Daily Limit: " + request.getDailyLimit());
		lbMonthlyLimit.setText("Monthly Limit: " + request.getMonthlyLimit());
		lbYearlyLimit.setText("Yearly Limit: " + HelperUtils.getCurrentYearlyLimit(request.getEmployeeId()) + "/" + HelperUtils.getMaxYearlyLimit(request.getEmployeeId()));
		
		
		
	}
	public void setEmployee(SessionUser employee) {
		this.employee = employee;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}


	public Node getButton() {
		// TODO Auto-generated method stub
		return btClose;
	}
	
	public void hideOptions() {
		lbPCardAdminOptions.setVisible(false);
		lbPCardAdminOptions.managedProperty().bind(lbPCardAdminOptions.visibleProperty());
		btApprove.setVisible(false);
		btApprove.managedProperty().bind(btApprove.visibleProperty());
		btDeny.setVisible(false);
		btDeny.managedProperty().bind(btDeny.visibleProperty());
	}

}
