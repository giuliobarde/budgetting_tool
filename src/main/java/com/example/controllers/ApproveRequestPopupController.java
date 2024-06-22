package com.example.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.example.objects.Expense;
import com.example.objects.Request;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ApproveRequestPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btConfirm;
	
    @FXML
    private HBox hb1;

    @FXML
    private HBox hb2;

    @FXML
    private HBox hb3;


	@FXML
	private Label lbDailyLimit;

	@FXML
	private Label lbMonthlyLimit;

	@FXML
	private Label lbPCardNumber;

	@FXML
	private TextField tfDailyLimit;

	@FXML
	private TextField tfMonthlyLimit;

	@FXML
	private TextField tfPCardNumber;

	@FXML
	private TextField tfReason;

	private String thingToApprove;
	private Expense expense;
	private Request request;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// constrain card number to 16 digits
		UnaryOperator<Change> integerFilter = change -> {
			String input = change.getText();
			if (input.matches("[0-9]*")) {
				if (change.getControlNewText().length() <= 16) {
					return change;
				}
			}

			return null;
		};
		tfPCardNumber.setTextFormatter(new TextFormatter<String>(integerFilter));

		// constrain daily/monthly limits to only numbers
		DecimalFormat format = new DecimalFormat("#");
		tfDailyLimit.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);
			if ((object == null) || ((parsePosition.getIndex()) < (c.getControlNewText().length()))) {
				return null;
			} else {
				if (new BigDecimal(c.getControlNewText()).scale() <= 2)
					return c;
				else
					return null;
			}
		}));
		tfMonthlyLimit.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);
			if ((object == null) || ((parsePosition.getIndex()) < (c.getControlNewText().length()))) {
				return null;
			} else {
				if (new BigDecimal(c.getControlNewText()).scale() <= 2)
					return c;
				else
					return null;
			}
		}));

		btConfirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// checks if reason is empty
				if (tfReason.getText().trim().isEmpty()) {
					HelperUtils.showAlert("Error Approving Request",
							"Error: No submitted reason for approving request");
					return;
				}
				// checks if approving not a pcard (expense)
				if (!thingToApprove.equals("pcard")) {
					tfDailyLimit.setText("0");
					tfMonthlyLimit.setText("0");
					approveRequest();
					return;
				}
				// checks if fields are correct
				if (tfPCardNumber.getText().length() != 16) {
					HelperUtils.showAlert("Error adding Pcard", "Error. Proposed PCard Details can't be used");
					return;
				}
				if (HelperUtils.checkExistingPCard(tfPCardNumber.getText())) {
					HelperUtils.showAlert("Error adding Pcard", "Error. P Card Number already exists.");
					return;
				}
				
				approveRequest();
			}
		});

		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();
			}
		});

	}

	public void setThingToApprove(String thing) {
		if (thing.equals("pcard")) {
			this.thingToApprove = thing;
		} else if (thing.equals("expense")) {
			this.thingToApprove = thing;
		}

	}

	public String getThingToApprove() {
		return thingToApprove;
	}

	public Expense getExpense() {
		return expense;
	}

	public Request getRequest() {
		return request;
	}

	public void setExpense(Expense expense) {
		this.expense = new Expense(expense.getExpenseId(), expense.getEmployeeId(), expense.getEmployeeName(),
				expense.getApprovalStatus(), expense.getAmount(), expense.getExpenseType(), expense.getDate(),
				expense.getMemo(), expense.getCardType(), expense.getCardNumber(), expense.getApproverId());
	}

	public void setRequest(Request request) {
		this.request = new Request(request.getRequestId(), request.getDate(),request.getRequesterId(), request.getEmployeeId(),
				request.getEmpName(), request.getRequesterName(), request.getMemo(),request.getDailyLimit(), request.getMonthlyLimit(), request.getPcardAdminId(), request.getApprovalStatus());
	}

	public void hidePCardInput() {
		lbPCardNumber.setVisible(false);
		lbPCardNumber.managedProperty().bind(lbPCardNumber.visibleProperty());
		tfPCardNumber.setVisible(false);
		tfPCardNumber.managedProperty().bind(tfPCardNumber.visibleProperty());
		lbDailyLimit.setVisible(false);
		lbDailyLimit.managedProperty().bind(lbDailyLimit.visibleProperty());
		tfDailyLimit.setVisible(false);
		tfDailyLimit.managedProperty().bind(tfDailyLimit.visibleProperty());
		lbMonthlyLimit.setVisible(false);
		lbMonthlyLimit.managedProperty().bind(tfMonthlyLimit.visibleProperty());
		tfMonthlyLimit.setVisible(false);
		tfMonthlyLimit.managedProperty().bind(tfMonthlyLimit.visibleProperty());
		removeHBox();
	}

	private void approveRequest() {
		HelperUtils.approveRequest(thingToApprove, tfReason.getText(), expense, request, tfPCardNumber.getText(),
				Double.parseDouble(tfDailyLimit.getText()), Double.parseDouble(tfMonthlyLimit.getText()));
		// check which table to refresh
		if (thingToApprove.equals("expense")) {
			SceneChanger.reinbursementController.constructTvExpenseRequests();
		} else if (thingToApprove.equals("pcard")) {
			SceneChanger.requestGrantPCardController.constructTvRequests();
		}
		// chose current window
		Stage stage = (Stage) btConfirm.getScene().getWindow();
		stage.close();
		// check which secondary window to refresh
		Stage lowerStage = null;
		if (thingToApprove.equals("expense")) {
			lowerStage = (Stage) SceneChanger.expenseDetailsPopupController.getButton().getScene().getWindow();
		} else if (thingToApprove.equals("pcard")) {
			lowerStage = (Stage) SceneChanger.pCardRequestDetailsPopupController.getButton().getScene().getWindow();
		}
		lowerStage.close();
	}

	public void setLimits(Request request) {
		tfDailyLimit.setText(request.getDailyLimit().toString());
		tfMonthlyLimit.setText(request.getMonthlyLimit().toString());
		tfDailyLimit.setEditable(false);
		tfMonthlyLimit.setEditable(false);
		
	}
	
	private void removeHBox() {
		hb1.setPrefHeight(0);
		hb2.setPrefHeight(0);
		hb3.setPrefHeight(0);
	}
}