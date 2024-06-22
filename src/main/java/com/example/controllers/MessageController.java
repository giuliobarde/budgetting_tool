package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.objects.Message;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import java.util.ArrayList;
import java.util.Date;

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

public class MessageController implements Initializable, MenuController {

	@FXML
	private TableView<Message> tvMessages;
	@FXML
	private TableColumn<Message, String> tcFrom;
	@FXML
	private TableColumn<Message, Date> tcDate;
	@FXML
	private TableColumn<Message, String> tcMessage;
	@FXML
	private Label lbWelcome;
	@FXML
	private Label lbRole;
	@FXML
	private Button btCreateMessage;
	@FXML
	private Button btLogOut;
	@FXML
	private Button btExpenses;
	@FXML
	private Button btPCard;
	@FXML
	private Button btMessages;
	@FXML
	private Button btPCardRequests;
	@FXML
	private Button btRbRequests;
    @FXML
    private Button btSystemOptions;
    @FXML
    private Button btSortMessages;


	private ArrayList<Message> messageList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperUtils.setNameAndRole(Main.getUser().getfName(), Main.getUser().getRole(), this);
		HelperUtils.checkUserPermissions(Main.getUser().getRole(), this);

		// check if user is System Admin
		if (!Main.getUser().getRole().equals("System Admin")) {
			hideCreateMessage();
		}

		constructTvMessages();

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

		// Sets show Create Message Popup to button
		btCreateMessage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.createMessagePopup(event);

			}

		});
		
		btSortMessages.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.sortMessagePopup(event);

			}
		});

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

	// hides the create message button if user is not a regular admin
	public void hideCreateMessage() {
		btCreateMessage.setVisible(false);
		btCreateMessage.managedProperty().bind(btCreateMessage.visibleProperty());

	}

	public void constructTvMessages() {
		// Construct Message List
		messageList = new ArrayList<Message>();
		HelperUtils.addMessages(messageList);

		// fill table columns with info from messageList
		tcFrom.setCellValueFactory(new PropertyValueFactory<Message, String>("creatorName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Message, Date>("date"));
		tcMessage.setCellValueFactory(new PropertyValueFactory<Message, String>("headerText"));

		// sets table with the table columns
		tvMessages.getItems().setAll(messageList);

		// sets each table row to show full message popup when double clicked
		tvMessages.setRowFactory(tv -> {
			TableRow<Message> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Message rowMessage = row.getItem();
					System.out.println(rowMessage.getBodyText());

					// updates the message to not be bold
					HelperUtils.updateMessageRead(rowMessage);

					// opens popup to show full message
					SceneChanger.messagePopup(rowMessage, event);

					// updates boldness to show that it has already been read
					tvMessages.refresh();
				}
			});

			row.itemProperty().addListener((obs, oldMessage, newMessage) -> {
				if (newMessage != null) {
					if (HelperUtils.getMessageRead(newMessage)) {
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
	
	public void constructTvMessages(ArrayList<Message> list) {
		// Construct Message List
		messageList = list;

		// fill table columns with info from messageList
		tcFrom.setCellValueFactory(new PropertyValueFactory<Message, String>("creatorName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Message, Date>("date"));
		tcMessage.setCellValueFactory(new PropertyValueFactory<Message, String>("headerText"));

		// sets table with the table columns
		tvMessages.getItems().setAll(list);
		/*
		// sets each table row to show full message popup when double clicked
		tvMessages.setRowFactory(tv -> {
			TableRow<Message> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Message rowMessage = row.getItem();
					System.out.println(rowMessage.getBodyText());

					// updates the message to not be bold
					HelperUtils.updateMessageRead(rowMessage);

					// opens popup to show full message
					SceneChanger.messagePopup(rowMessage, event);

					// updates boldness to show that it has already been read
					tvMessages.refresh();
				}
			});

			row.itemProperty().addListener((obs, oldMessage, newMessage) -> {
				if (newMessage != null) {
					if (HelperUtils.getMessageRead(newMessage)) {
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
	*/
	}



}
