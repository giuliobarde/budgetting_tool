package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.objects.Expense;
import com.example.userclasses.SessionUser;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ExpenseDetailsPopupController implements Initializable, PopupController {

	@FXML
	private Button btApprove;

	@FXML
	private Button btClose;

	@FXML
	private Button btDeny;

	@FXML
	private Label lbAmount;

	@FXML
	private Label lbApprovalStatus;

	@FXML
	private Label lbBudgetAdmin;

	@FXML
	private Label lbCardType;
	
	@FXML
	private Label lbCardNumber;

	@FXML
	private Label lbDate;

	@FXML
	private Label lbDepartment;

	@FXML
	private Label lbExpenseType;

	@FXML
	private Label lbMemo;

	@FXML
	private Label lbName;

	@FXML
	private Label lbRole;

	@FXML
	private Label lbYearlyLimit;
	
	@FXML
	private Label lbBudgetAdminOptions;

	private Expense expense;
	private SessionUser employee;


	private SessionUser approver;



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO add approve button functionality
		btApprove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.approveReasonPopup(event,ExpenseDetailsPopupController.class.getName(),expense,null);

			}

		});

		btDeny.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.denyReasonPopup(event, ExpenseDetailsPopupController.class.getName(), expense, null);
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

	public void setExpenseDetails() {
		lbAmount.setText("Amount: " + expense.getAmount());
		lbApprovalStatus.setText("Approval Status: " + expense.getApprovalStatus());
		lbCardType.setText("Card Type: " + expense.getCardType());
		lbCardNumber.setText("Card Number: ************" + expense.getCardNumber().substring(expense.getCardNumber().length() - 4));
		lbDate.setText("Date: " + expense.getDate());
		lbExpenseType.setText("Expense Type: " + expense.getExpenseType());
		lbMemo.setText("Memo: " + expense.getMemo());
		lbYearlyLimit.setText("Yearly Limit: " + HelperUtils.getCurrentYearlyLimit(expense.getEmployeeId()) + "/" + HelperUtils.getMaxYearlyLimit(expense.getEmployeeId()));
		lbBudgetAdmin.setText("Budget Admin: " + approver.getfName() + " " + approver.getlName());
		lbDepartment.setText("Department: " + employee.getDepartmentName());
		lbName.setText("Employee Name: " + employee.getfName() + " " + employee.getlName());
		lbRole.setText("Employee Role: " + employee.getRole());

		// Show or hide the approve/deny button based on approval status
		boolean isPending = "pending".equalsIgnoreCase(expense.getApprovalStatus());
	    btApprove.setVisible(isPending);
	    btApprove.managedProperty().bind(btApprove.visibleProperty());
	    btDeny.setVisible(isPending);
	    btDeny.managedProperty().bind(btDeny.visibleProperty());
	    lbBudgetAdminOptions.setVisible(isPending);
	    lbBudgetAdminOptions.managedProperty().bind(lbBudgetAdminOptions.visibleProperty());
	}
	

	public void hideBudgetAdminControls() {
		btApprove.setVisible(false);
		btApprove.managedProperty().bind(btApprove.visibleProperty());
		btDeny.setVisible(false);
		btDeny.managedProperty().bind(btDeny.visibleProperty());
		lbBudgetAdminOptions.setVisible(false);
		lbBudgetAdminOptions.managedProperty().bind(lbBudgetAdmin.visibleProperty());
	}
	
	

	public void setExpense(Expense expense) {
		this.expense = new Expense(expense.getExpenseId(), expense.getEmployeeId(),expense.getEmployeeName(), expense.getApprovalStatus(),
				expense.getAmount(), expense.getExpenseType(), expense.getDate(), expense.getMemo(),
				expense.getCardType(), expense.getCardNumber(), expense.getApproverId());
	}

	public Expense getExpense() {
		return expense;
	}

	public Button getButton() {
		return btClose;
	}

	public SessionUser getEmployee() {
		return employee;
	}

	public void setEmployee(SessionUser employee) {
		this.employee = employee;
	}
	
	public SessionUser getApprover() {
		return approver;
	}

	public void setApprover(SessionUser approver) {
		this.approver = approver;
	}
}
