package com.example.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SortMessagesPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btSubmit;

	@FXML
	private ComboBox<String> cbOrderBy;

	@FXML
	private DatePicker dpDate;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// fills Order By Combo Box
		ObservableList<String> orderOptions = FXCollections.observableArrayList("Date");
		Collections.sort(orderOptions);
		cbOrderBy.setItems(orderOptions);

		// sets default date for date picker
		dpDate.setValue(LocalDate.now());

		btSubmit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO check for things that would break query
				SceneChanger.messageController.constructTvMessages(HelperUtils.addSortedMessages(tfFrom.getText(),
						tfContains.getText(), dpDate.getValue(), (RadioButton) tgDate.getSelectedToggle(),
						cbOrderBy.getValue(), (RadioButton) tgOrder.getSelectedToggle()));
				Stage stage = (Stage) btSubmit.getScene().getWindow();
				stage.close();

			}

		});

		// Sets Cancel/Close Scene to Button
		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();
			}

		});

	}

}
