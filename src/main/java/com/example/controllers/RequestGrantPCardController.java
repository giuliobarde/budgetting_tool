package com.example.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestGrantPCardController implements Initializable, MenuController,SortRequestsController {

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
	private TableColumn<Request, String> tcApprovalStatus;
	@FXML
	private TableColumn<Request, Date> tcDate;
	@FXML
	private TableColumn<Request, String> tcFrom;
	@FXML
	private TableColumn<Request, String> tcMemo;
	@FXML
	private TableView<Request> tvRequests;

	private ArrayList<Request> pCardRequestList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperUtils.checkUserPermissions(Main.getUser().getRole(), this);
		HelperUtils.setNameAndRole(Main.getUser().getfName(), Main.getUser().getRole(), this);

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
		// Sets Change to Expenses Screen to Button
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

		constructTvRequests();

	}

	protected void openSortRequests(ActionEvent event) {
		SceneChanger.sortRequestsPopup(event,this);
		
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


	public void constructTvRequests() {
		// Construct Expense List
		pCardRequestList = new ArrayList<Request>();
		HelperUtils.addPCardRequest(pCardRequestList);
		for (Request r : pCardRequestList) {
			System.out.println(r.getRequesterName());
		}

		// fill table columns with info from messageList
		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Request, String>("approvalStatus"));
		tcFrom.setCellValueFactory(new PropertyValueFactory<Request, String>("requesterName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Request, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(pCardRequestList);

		// sets each table row to show full message popup when double clicked
		tvRequests.setRowFactory(tv -> {
			TableRow<Request> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Request rowRequest = row.getItem();

					// opens popup to show full expense
					SceneChanger.viewRequestPopup(event, rowRequest);

					// updates boldness to show that it has already been read
					tvRequests.refresh();
				}
			});

			return row;
		});

	}

	public void constructTvRequests(ArrayList<Request> list) {
		// Construct Expense List
		pCardRequestList = list;

		// fill table columns with info from messageList

		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Request, String>("approvalStatus"));
		tcFrom.setCellValueFactory(new PropertyValueFactory<Request, String>("requesterName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Request, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(pCardRequestList);

	}

	public Node getButton() {
		return btLogOut;
	}

}
