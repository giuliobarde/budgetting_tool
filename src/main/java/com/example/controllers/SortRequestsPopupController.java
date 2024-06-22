package com.example.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SortRequestsPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btSubmit;

	@FXML
	private ComboBox<String> cbOrderBy;

	@FXML
	private CheckBox chbApproved;

	@FXML
	private CheckBox chbPending;

	@FXML
	private CheckBox chbReturned;

	@FXML
	private DatePicker dpDate;
    @FXML
    private Label lbFrom;

	@FXML
	private RadioButton rbAllDates;

	@FXML
	private RadioButton rbAscending;

	@FXML
	private RadioButton rbDay;

	@FXML
	private RadioButton rbDescending;

	@FXML
	private RadioButton rbMonth;

	@FXML
	private RadioButton rbYear;

	@FXML
	private TextField tfContains;

	@FXML
	private TextField tfFrom;

	@FXML
	private ToggleGroup tgDate;

	@FXML
	private ToggleGroup tgOrder;

	private SortRequestsController sourceController; // used to check need to send From or not via "viewing own
														// requests"

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		
		// sets default date for date picker
		dpDate.setValue(LocalDate.now());

		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();
			}
		});

		btSubmit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO check for things that would break query
				sourceController.constructTvRequests(HelperUtils.addSortedRequests(sourceController,
						chbApproved.isSelected(), chbPending.isSelected(), chbReturned.isSelected(), tfFrom.getText(),
						tfContains.getText(), dpDate.getValue(), (RadioButton) tgDate.getSelectedToggle(),
						cbOrderBy.getValue(), (RadioButton) tgOrder.getSelectedToggle()));
				Stage stage = (Stage) btSubmit.getScene().getWindow();
				stage.close();
				
			}

		});

	}

	public SortRequestsController getSourceController() {
		return sourceController;
	}

	public void setSourceController(SortRequestsController sourceController) {
		this.sourceController = sourceController;
	}
	
	public void fillCbOrderBy() {
		// set combobox based on sourceController
		ObservableList<String> orderOptions = FXCollections.observableArrayList();	
		orderOptions.addAll("Date","ApprovalStatus","Memo");
		if (!sourceController.getClass().getName().equals("com.example.controllers.RequestGrantPCardController")) {
			orderOptions.add("Employee");
		} else {
			orderOptions.add("Requester");
		}

		Collections.sort(orderOptions);
		cbOrderBy.setItems(orderOptions);
		
	}
	
	public void setLbFrom() {
		if (!sourceController.getClass().getName().equals("com.example.controllers.RequestGrantPCardController")) {
			lbFrom.setText("For: ");
		} else {
			lbFrom.setText("From:");
		}
	}

}
