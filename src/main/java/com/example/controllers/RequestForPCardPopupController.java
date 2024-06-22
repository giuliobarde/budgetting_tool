package com.example.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.userclasses.BudgetAdmin;
import com.example.userclasses.PCardAdmin;
import com.example.userclasses.User;
import com.example.utils.Connect;
import com.example.utils.HelperUtils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RequestForPCardPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;
	@FXML
	private Button btSubmit;
	@FXML
	private Label lbPCardAdmin;
	@FXML
	private TextArea taMemo;
	@FXML
	private TextField tfDailyLimit;
	@FXML
	private TextField tfMonthlyLimit;
	@FXML
	private ComboBox<User> cbEmployees;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// sets character limit for memo
		final int max_chars_memo = 255;

		// sets character limit for memo text area
		taMemo.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= max_chars_memo ? change : null));

		// sets P card admin
		PCardAdmin pCardAdmin = new PCardAdmin("Ronald", "Donald", "X01984579", 2, "Department of PCards");
		lbPCardAdmin.setText("Will be sent to: " + pCardAdmin.toString());

		// sets List of eligible employees
		addEligibleEmployees();

		btSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// check to see if the memo box is empty, if empty return request failed
				if (taMemo.getText().isEmpty()) {
					HelperUtils.showAlert("Empty Memo", "Request failed due to missing memo.");
					return;
				}
				// check if user selected an employee
				if (cbEmployees.getSelectionModel().isEmpty()) {
					HelperUtils.showAlert("Employee not selected", "Request failed due to no selected employee.");
					return;
				}
				//check if daily/monthly limit was submitted
				if (tfDailyLimit.getText().trim().isEmpty() || tfMonthlyLimit.getText().trim().isEmpty()) {
					HelperUtils.showAlert("Error adding Pcard", "Error. Daily/Monthly Limits not set");
					return;
				}
				// check if daily/monthly limit is more than yearly limit
				if (Double.parseDouble(tfDailyLimit.getText()) > HelperUtils
						.getMaxYearlyLimit(cbEmployees.getValue().getXNumber())
						|| Double.parseDouble(tfMonthlyLimit.getText()) > HelperUtils
								.getMaxYearlyLimit(cbEmployees.getValue().getXNumber())) {
					HelperUtils.showAlert("Error adding Pcard",
							"Error. Daily/Monthly Limit exceeds employee's max yearly limit");
					return;
				}

				// checks if daily limit is higher than monthly limit
				if (Double.parseDouble(tfDailyLimit.getText()) > Double.parseDouble(tfMonthlyLimit.getText())) {
					HelperUtils.showAlert("Error adding Pcard", "Error. Daily Limit exceeds Monthly Limit");
					return;
				}
				createPCard();

			}

			private void createPCard() {
				HelperUtils.createPCardRequest(taMemo.getText(), pCardAdmin.getXNumber(),
						cbEmployees.getValue().getXNumber(), Double.parseDouble(tfDailyLimit.getText()),
						Double.parseDouble(tfMonthlyLimit.getText()));
				Stage stage = (Stage) btCancel.getScene().getWindow();
				stage.close();

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

	private void addEligibleEmployees() {

		try {
			String query = "select employee.*, department.dname from employee,department"
					+ " where employeeId NOT IN (select employeeId from pcard)" + " AND employee.departmentId = ?"
					+ " AND employee.departmentId = department.departmentId";

			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setInt(1, Main.getSessionUser().getDepartmentId());
			ResultSet resultSet = statement.executeQuery();

			ObservableList<User> employees = cbEmployees.getItems();

			while (resultSet.next()) {
				employees.add(new User(resultSet.getString("fName"), resultSet.getString("lName"),
						resultSet.getString("employee.employeeid"), resultSet.getInt("departmentId"),
						resultSet.getString("department.dname"), "Easter Egg"));
			}

			cbEmployees.setItems(employees);

			cbEmployees.setConverter(new StringConverter<User>() {
				@Override
				public String toString(User employees) {
					return employees.getfName() + " " + employees.getlName();
				}

				@Override
				public User fromString(String s) {
					return null;
				}
			});

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * to do after database
	 * 
	 * public static void addPCardAdmin(ComboBox<BudgetAdmin> cbBudgetAdmin) { try {
	 * String query = "SELECT fName, lName, employee.employeeid, department.dname "
	 * + "FROM budgets_for, employee, department " +
	 * "WHERE budgets_for.employeeid = employee.employeeid " +
	 * "AND employee.departmentid = department.departmentid ";
	 * 
	 * PreparedStatement statement = Connect.con.prepareStatement(query); ResultSet
	 * resultSet = statement.executeQuery();
	 * 
	 * ObservableList<BudgetAdmin> budgetAdmins = cbBudgetAdmin.getItems();
	 * 
	 * while (resultSet.next()) { budgetAdmins.add(new
	 * BudgetAdmin(resultSet.getString("fName"), resultSet.getString("lName"),
	 * resultSet.getString("employee.employeeid"),
	 * resultSet.getString("department.dname"))); }
	 * 
	 * cbBudgetAdmin.setItems(budgetAdmins);
	 * 
	 * cbBudgetAdmin.setConverter(new StringConverter<BudgetAdmin>() {
	 * 
	 * @Override public String toString(BudgetAdmin budgetAdmin) { return
	 * budgetAdmin.getfName() + " " + budgetAdmin.getlName(); }
	 * 
	 * @Override public BudgetAdmin fromString(String s) { return null; } });
	 * 
	 * resultSet.close(); statement.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } }
	 */

}
