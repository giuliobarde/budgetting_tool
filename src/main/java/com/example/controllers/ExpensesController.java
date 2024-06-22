package com.example.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.objects.Expense;
import com.example.objects.Message;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExpensesController implements Initializable, MenuController,SortExpensesController {

	@FXML
	private Button btAddExpense;
	@FXML
	private Button btExpenses;
	@FXML
	private Button btLogOut;
	@FXML
	private Button btMessages;
	@FXML
	private Button btPCard;
	@FXML
	private Button btPCardRequests;
	@FXML
	private Button btRbRequests;
	@FXML
	private Button btSortExpenses;
	@FXML
	private Button btSystemOptions;
	@FXML
	private Label lbRole;
	@FXML
	private Label lbWelcome;
	@FXML
	private Label lbYearlyLimit;
	@FXML
	private TableColumn<Expense, Double> tcAmount;
	@FXML
	private TableColumn<Expense, String> tcApprovalStatus;
	@FXML
	private TableColumn<Expense, Date> tcDate;
	@FXML
	private TableColumn<Expense, String> tcExpenseType;
	@FXML
	private TableColumn<Expense, String> tcMemo;
	@FXML
	private TableView<Expense> tvExpenses;

	private ArrayList<Expense> expenseList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperUtils.checkUserPermissions(Main.getUser().getRole(), this);
		HelperUtils.setNameAndRole(Main.getUser().getfName(), Main.getUser().getRole(), this);


		constructTvExpenses();

		// Sets Return to Login Scene Event to Button
		btLogOut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO end session aka remove the data from the class thing
				SceneChanger.changeToLogin(event);
			}

		});

		// Sets Return to Messages Scene Event to Button
		btMessages.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO end session aka remove the data from the class thing
				SceneChanger.changeToMessages(event);
			}

		});
		// Sets Change to Expenses Screen to Button
		btExpenses.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToExpenses(event);
			}

		});
		// Set Change to P Card Scene to Button
		btPCard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToPCard(event);

			}

		});
		// Sets Change to PCard Requests Screen to Button
		btPCardRequests.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToGrantPCardRequests(event);
			}

		});

		// Sets Change to Reinbursement Requests Screen to Button
		btRbRequests.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToReimbursement(event);
			}

		});
		
		btSystemOptions.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.viewSystemOptionsPopup(event);
			}

		});

		// Sets Sort Expenses Popup to Button
		btSortExpenses.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSortExpensesPopup(event);
			}

		});

		// Add Expense
		btAddExpense.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.addExpense(event);

			}

		});

	}

	protected void openSortExpensesPopup(ActionEvent event) {
		SceneChanger.sortExpensesPopup(event,this);
		
	}

	// Sets first name of User into the Welcome message
	public void setWelcomeFname(String fname) {
		lbWelcome.setText("Welcome " + fname + ".");
	}

	// Sets role of User into the Role message
	public void setRole(String role) {
		lbRole.setText("Role: " + role);
	}

	// Hides The PCard Requests Button if User is not a P Card Admin
	public void hidePCardRequests() {
		btPCardRequests.setVisible(false);
		btPCardRequests.managedProperty().bind(btPCardRequests.visibleProperty());
	}

	// Hides the Reinbursment Requests if User is not a Budget Admin
	public void hideRBRequests() {
		btRbRequests.setVisible(false);
		btRbRequests.managedProperty().bind(btRbRequests.visibleProperty());
	}


	public void constructTvExpenses() {
		// Construct Expense List
		expenseList = new ArrayList<Expense>();
		HelperUtils.addExpense(expenseList);

		// fill table columns with info from messageList
		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Expense, String>("approvalStatus"));
		tcExpenseType.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseType"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));

		// sets table with the table columns
		tvExpenses.getItems().setAll(expenseList);

		tvExpenses.setRowFactory(tv -> {
			TableRow<Expense> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Expense rowExpense = row.getItem();

					// opens popup to show full expense
					SceneChanger.viewExpenseDetails(rowExpense, event, this);

				}
			});
			return row;
		});
		setYearlyLimit();

	}

	public void constructTvExpenses(ArrayList<Expense> list) {
		// Construct Expense List
		expenseList = list;

		// fill table columns with info from messageList
		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Expense, String>("approvalStatus"));
		tcExpenseType.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseType"));
		tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));

		// sets table with the table columns
		tvExpenses.getItems().setAll(expenseList);
		
		setYearlyLimit();
	}

	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}
	
	private void setYearlyLimit() {
		lbYearlyLimit.setText(
				"Yearly Expense Limit: " + HelperUtils.getCurrentYearlyLimit(Main.getSessionUser().getXNumber()) + "/"
						+ HelperUtils.getMaxYearlyLimit(Main.getSessionUser().getXNumber()));

	}
}
