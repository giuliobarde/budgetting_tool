package com.example.controllers;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReimbursementController implements Initializable, MenuController,SortExpensesController {

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
	private Button btSystemOptions;
	
    @FXML
    private Button btSortRequests;

	@FXML
	private Label lbRole;

	@FXML
	private Label lbWelcome;

	@FXML
	private TableColumn<Expense, String> tcDate;

	@FXML
	private TableColumn<Expense, String> tcFrom;

	@FXML
	private TableColumn<Expense, String> tcRequest;

	@FXML
	private TableView<Expense> tvRequests;

	private ArrayList<Expense> expenseRequestList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperUtils.setNameAndRole(Main.getUser().getfName(), Main.getUser().getRole(), this);
		HelperUtils.checkUserPermissions(Main.getUser().getRole(), this);

		constructTvExpenseRequests();

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
		// Sets Change to PCardRequests Screen to Button
		btPCardRequests.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToGrantPCardRequests(event);
			}

		});

		// Sets Change to Expenses Screen to Button
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
		
		btSortRequests.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSortRequests(event);
			}

		});

	}
	protected void openSortRequests(ActionEvent event) {
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

	public void constructTvExpenseRequests() {
		// Construct Expense List
		expenseRequestList = new ArrayList<Expense>();
		HelperUtils.addExpenseRequest(expenseRequestList);

		// fill table columns with info from expenseRequestList

		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("date"));
		tcFrom.setCellValueFactory(new PropertyValueFactory<Expense, String>("employeeName"));
		tcRequest.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(expenseRequestList);

		// sets each table row to show full message popup when double clicked
		tvRequests.setRowFactory(tv -> {
			TableRow<Expense> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Expense rowExpense = row.getItem();

					// opens popup to show full expense
					SceneChanger.viewExpenseDetails(rowExpense, event, this);

					// updates boldness to show that it has already been read
					tvRequests.refresh();
				}
			});

			row.itemProperty().addListener((obs, oldExpense, newExpense) -> {
				if (newExpense != null) {
					if (!HelperUtils.getApprovalStatus(newExpense)) {
						row.setStyle("-fx-font-weight: normal;");
					} else {
						row.setStyle("-fx-font-weight: bold;");
					}
				} else {
					row.setStyle("");
				}
			});
			return row;
		});

	}

	public void constructTvExpenses(ArrayList<Expense> list) {
		// Construct Expense List
		expenseRequestList = list;

		// fill table columns with info from messageList
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("date"));
		tcFrom.setCellValueFactory(new PropertyValueFactory<Expense, String>("employeeName"));
		tcRequest.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(expenseRequestList);

	}



}
