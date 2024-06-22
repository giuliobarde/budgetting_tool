package com.example.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.example.cus1166.Main;
import com.example.objects.Expense;
import com.example.userclasses.BudgetAdmin;
import com.example.utils.Connect;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class AddExpensePopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btSubmit;

	@FXML
	private ComboBox<BudgetAdmin> cbBudgetAdmin;

	@FXML
	private ComboBox<String> cdCardType;

	@FXML
	private ComboBox<String> cdExpenseType;

	@FXML
	private TextArea taMemo;

	@FXML
	private TextField tfAmount;

	@FXML
	private TextField tfCardNumber;

	@FXML
	void comboBoxCardSelect(ActionEvent event) {
		// if user has p card, auto fill textfield with p card number
		if (cdCardType.getValue().equals("Pcard")) {
			if (Main.getSessionPCard().getCardNumber() != null) {
				tfCardNumber.setText(Main.getSessionPCard().getCardNumber());
				tfCardNumber.setEditable(false);
			} else {
				// alert
				HelperUtils.showAlert("No PCard detected", "No P Card detected.");
				cdCardType.setValue("Credit card");
			}

		} else {
			tfCardNumber.setEditable(true);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// adds budget admins to drop down menu
		addBudgetAdmins(cbBudgetAdmin);

		// list for card type drop down
		ObservableList<String> cardType = FXCollections.observableArrayList("Pcard", "Credit card");
		cdCardType.setItems(cardType);

		// list for expense type drop down
		ObservableList<String> expenseType = FXCollections.observableArrayList("Food", "Conference", "Development",
				"Transportation", "Supplies", "Other");
		cdExpenseType.setItems(expenseType);

		// set character limits for text fields
		final int max_chars_memo = 50;
		final int max_chars_cardNumber = 16;

		// sets filters for textfields
		taMemo.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= max_chars_memo ? change : null));

		// set constraint to only numbers for card number text field
		UnaryOperator<Change> integerFilter = change -> {
			String input = change.getText();
			if (input.matches("[0-9]*")) {
				if (change.getControlNewText().length() <= max_chars_cardNumber) {
					return change;
				}
			}

			return null;
		};
		tfCardNumber.setTextFormatter(new TextFormatter<String>(integerFilter));

		// set constraint to amount textfield for only money numbers
		DecimalFormat format = new DecimalFormat("#");
		tfAmount.setTextFormatter(new TextFormatter<>(c -> {
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

		// Creates expense to database when clicked
		btSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// checks if anything was not submitted in the form
			if (!tfAmount.getText().trim().isEmpty() && taMemo.getText().length() > 0
						&& !cbBudgetAdmin.getSelectionModel().isEmpty() && !cdExpenseType.getSelectionModel().isEmpty()
						&& !cdCardType.getSelectionModel().isEmpty() && tfCardNumber.getText().length() == 16) {
				// adds current yearly limit to proposed expense amount to check if user is not going over limit
					if (HelperUtils.getCurrentYearlyLimit(Main.getSessionUser().getXNumber())
							+ Double.parseDouble(tfAmount.getText()) 
							< HelperUtils.getMaxYearlyLimit(Main.getSessionUser().getXNumber())) {
						HelperUtils.createExpense(Double.parseDouble(tfAmount.getText()), cdExpenseType.getValue(),
								taMemo.getText(), cdCardType.getValue(), tfCardNumber.getText(),
								cbBudgetAdmin.getValue());
						
						// refreshes table in still open expense scene to reflect the new expense
						SceneChanger.expensesController.constructTvExpenses(); 
						
						// gets and closes add expense window
						Stage stage = (Stage) btSubmit.getScene().getWindow(); 
						stage.close();
					} else {
						HelperUtils.showAlert("Over limit",
								"The proposed amount you submitted would go over your yearly limit.");
					}

				} else {
					HelperUtils.showAlert("Missing elements", "Request failed due to missing elements.");
				}
			}
		});
		// closes scene
		btCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();

			}

		});
	}
	// helper method to add budget admins to given combobox
	public static void addBudgetAdmins(ComboBox<BudgetAdmin> cbBudgetAdmin) {
		try {
			String query = "SELECT fName, lName, employee.employeeid,employee.departmentId, department.dname "
					+ "FROM budgets_for, employee, department " + "WHERE budgets_for.employeeid = employee.employeeid "
					+ "AND employee.departmentid = department.departmentid ";

			PreparedStatement statement = Connect.con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			ObservableList<BudgetAdmin> budgetAdmins = cbBudgetAdmin.getItems();

			while (resultSet.next()) {
				budgetAdmins.add(new BudgetAdmin(resultSet.getString("fName"), resultSet.getString("lName"),
						resultSet.getString("employee.employeeid"), resultSet.getInt("departmentId"),
						resultSet.getString("department.dname")));
			}

			cbBudgetAdmin.setItems(budgetAdmins);

			cbBudgetAdmin.setConverter(new StringConverter<BudgetAdmin>() {
				@Override
				public String toString(BudgetAdmin budgetAdmin) {
					return budgetAdmin.getfName() + " " + budgetAdmin.getlName();
				}

				@Override
				public BudgetAdmin fromString(String s) {
					return null;
				}
			});

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
