package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.objects.Expense;
import com.example.objects.Request;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DenyRequestPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btConfirm;

	@FXML
	private TextField tfReason;

	private String thingToDeny;
	private Expense expense;
	private Request request;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btConfirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HelperUtils.denyRequest(thingToDeny, tfReason.getText(), expense, request);
				if (thingToDeny.equals("expense")) {
					SceneChanger.reinbursementController.constructTvExpenseRequests();
				} else if (thingToDeny.equals("pcard")) {
					SceneChanger.requestGrantPCardController.constructTvRequests();
				}

				Stage stage = (Stage) btConfirm.getScene().getWindow();
				stage.close();
				Stage lowerStage = null;
				if (thingToDeny.equals("expense")) {
					lowerStage = (Stage) SceneChanger.expenseDetailsPopupController.getButton().getScene().getWindow();
				} else if (thingToDeny.equals("pcard")) {
					lowerStage = (Stage) SceneChanger.pCardRequestDetailsPopupController.getButton().getScene()
							.getWindow();
				}

				lowerStage.close();

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

	public void setThingToDeny(String thing) {
		if (thing.equals("pcard")) {
			this.thingToDeny = thing;
		} else if (thing.equals("expense")) {
			this.thingToDeny = thing;
		}

	}

	public String getThingToDeny() {
		return thingToDeny;
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
				request.getEmpName(),request.getRequesterName(), request.getMemo(),request.getDailyLimit(),request.getMonthlyLimit(), request.getPcardAdminId(), request.getApprovalStatus());
	}

}
