package com.example.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.objects.Request;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewSubmittedRequestsPopupController implements Initializable, PopupController,SortRequestsController {

	@FXML
	private Button btClose;
	@FXML
	private Button btSortRequests;
	@FXML
	private TableColumn<Request, String> tcApprovalStatus;
	@FXML
	private TableColumn<Request, Date> tcDate;
	@FXML
	private TableColumn<Request, String> tcEmployee;
	@FXML
	private TableColumn<Request, String> tcMemo;
	@FXML
	private TableView<Request> tvRequests;

	private ArrayList<Request> pCardRequestList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		constructTvRequests();

		btClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btClose.getScene().getWindow();
				stage.close();
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
		SceneChanger.sortRequestsPopup(event, this);

	}

	private void constructTvRequests() {
		pCardRequestList = new ArrayList<Request>();
		HelperUtils.addSubmittedPCardRequest(pCardRequestList);

		// fill table columns with info from messageList
		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Request, String>("approvalStatus"));
		tcEmployee.setCellValueFactory(new PropertyValueFactory<Request, String>("empName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Request, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(pCardRequestList);

		// open detail popup when double click
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

	@Override
	public void constructTvRequests(ArrayList<Request> sortedRequests) {
		// Construct Expense List
		pCardRequestList = sortedRequests;

		// fill table columns with info from messageList

		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Request, String>("approvalStatus"));
		tcEmployee.setCellValueFactory(new PropertyValueFactory<Request, String>("empName"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Request, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Request, String>("memo"));

		// sets table with the table columns
		tvRequests.getItems().setAll(pCardRequestList);

		
	}

}
