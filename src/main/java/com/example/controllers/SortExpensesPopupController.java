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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SortExpensesPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btSubmit;

	@FXML
	private CheckBox chbApproved;

	@FXML
	private CheckBox chbPending;

	@FXML
	private CheckBox chbReturned;

	@FXML
	private ComboBox<String> cbOrderBy;

	@FXML
	private DatePicker dpDate;

	@FXML
	private RadioButton rbAscending;
	
	@FXML
	private RadioButton rbDescending;

	@FXML
	private RadioButton rbDay;

	@FXML
	private RadioButton rbMonth;

	@FXML
	private RadioButton rbYear;
	
    @FXML
    private RadioButton rbAllDates;

	@FXML
	private TextField tfExpenseType1;

	@FXML
	private TextField tfExpenseType2;

	@FXML
	private TextField tfExpenseType3;

	@FXML
	private ToggleGroup tgDate;

	@FXML
	private ToggleGroup tgOrder;
	
	private SortExpensesController sourceController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// fills Order By Combo Box
		ObservableList<String> orderOptions = FXCollections.observableArrayList("Date", "Expense Type",
				"Approval Status", "Amount");
		Collections.sort(orderOptions);
		cbOrderBy.setItems(orderOptions);
		
		// sets default date for date picker
		dpDate.setValue(LocalDate.now());

		// Sets Submit to Button
		btSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sourceController.constructTvExpenses(
						HelperUtils.addSortedExpenses(sourceController, chbApproved.isSelected(), chbPending.isSelected(),
								chbReturned.isSelected(), tfExpenseType1.getText(), tfExpenseType2.getText(),
								tfExpenseType3.getText(), dpDate.getValue(), (RadioButton) tgDate.getSelectedToggle(),
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

	public SortExpensesController getSourceController() {
		return sourceController;
	}

	public void setSourceController(SortExpensesController sourceController) {
		this.sourceController = sourceController;
	}

}
