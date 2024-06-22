package com.example.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.objects.Expense;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PCardController implements Initializable, MenuController,SortExpensesController {

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
	private Button btRequestPCard;
	@FXML
	private Button btViewSubmittedRequests;
	@FXML
	private Button btSystemOptions;
	@FXML
	private Button btSortExpenses;
	@FXML
	private Label lbDailyLimit;
	@FXML
	private Label lbMonthlyLimit;
	@FXML
	private Label lbRole;
	@FXML
	private Label lbWelcome;
	@FXML
	private ProgressBar pbDailyLimit;
	@FXML
	private ProgressBar pbMonthlyLimit;
	@FXML
	private TableColumn<Expense, Double> tcAmount;
	@FXML
	private TableColumn<Expense, String> tcApprovalStatus;
	@FXML
	private TableColumn<Expense, Date> tcDate;
	@FXML
	private TableColumn<Expense, String> tcMemo;
	@FXML
	private TableView<Expense> tvExpenses;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperUtils.setNameAndRole(Main.getUser().getfName(), Main.getUser().getRole(), this);
		HelperUtils.checkUserPermissions(Main.getUser().getRole(), this);

		if (!Main.getSessionUser().getRole().equals("Budget Admin")) {
			hideRequestPCardButton();
			hideViewSubmittedRequestsButton();
		}
		if (Main.getSessionPCard().getCardNumber().equals("0")) {
			btSortExpenses.setVisible(false);
			btSortExpenses.managedProperty().bind(btSortExpenses.visibleProperty());
		}

		loadExpenses();

		updateExpenseProgressBars();

		// Sets Return to Login Scene Event to Button
		btLogOut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToLogin(event);
			}

		});
		// Sets Return to Messages Scene Event to Button
		btMessages.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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

		// Sets Change to Reimbursement Screen to Button
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

		// Sets Change to Expenses Screen to Button
		btRequestPCard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.requestPCardPopup(event);
			}

		});

		btViewSubmittedRequests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SceneChanger.viewSubmittedRequestPopup(event);

			}

		});
		btSortExpenses.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSortExpensesPopup(event);
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

	private void hideViewSubmittedRequestsButton() {
		btViewSubmittedRequests.setVisible(false);
		btViewSubmittedRequests.managedProperty().bind(btViewSubmittedRequests.visibleProperty());

	}

	public void hideRequestPCardButton() {
		btRequestPCard.setVisible(false);
		btRequestPCard.managedProperty().bind(btRequestPCard.visibleProperty());
	}

	private void loadExpenses() {

		ObservableList<Expense> expenses = FXCollections.observableArrayList();
		String sql = "SELECT expense.*,employee.fname,employee.lname FROM expense,employee WHERE cardNumber = ? AND employee.employeeId = expense.employeeId";
		try (PreparedStatement statement = Connect.con.prepareStatement(sql)) {
			statement.setString(1, Main.getSessionPCard().getCardNumber());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				expenses.add(new Expense(rs.getInt("expenseId"), rs.getString("employeeId"),
						rs.getString("fname") + " " + rs.getString("lname"), rs.getString("approvalStatus"),
						rs.getDouble("amount"), rs.getString("expenseType"), rs.getDate("date"), rs.getString("memo"),
						rs.getString("cardType"), rs.getString("cardNumber"), rs.getString("approverId")));

			}
			tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
			tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Expense, String>("approvalStatus"));
			tcDate.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
			tcMemo.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));

			tvExpenses.getItems().setAll(expenses);

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
		} catch (SQLException e) {
			e.printStackTrace(); // Consider more robust error handling
		}
	}

	// Method to calculate the spending

	private void updateExpenseProgressBars() {
		// Retrieve current expenses and limits
		double dailyExpense = getDailyExpenses();
		double monthlyExpense = getMonthlyExpenses();
		double dailyLimit = Main.getSessionPCard().getDailyLimit();
		double monthlyLimit = Main.getSessionPCard().getMonthlyLimit();

		// Calculate the used percentage of limits
		double dailyUsagePercentage = dailyLimit > 0 ? dailyExpense / dailyLimit : 0;
		double monthlyUsagePercentage = monthlyLimit > 0 ? monthlyExpense / monthlyLimit : 0;

		// Update the progress bars
		pbDailyLimit.setProgress(dailyUsagePercentage);
		pbMonthlyLimit.setProgress(monthlyUsagePercentage);

		// Update the labels with formatted text
		lbDailyLimit.setText(String.format("$%.2f / $%.2f", dailyExpense, dailyLimit));
		lbMonthlyLimit.setText(String.format("$%.2f / $%.2f", monthlyExpense, monthlyLimit));
	}

	private double getDailyExpenses() {

		double pbDailyLimit = 0.0;
		String sql = "SELECT SUM(amount) AS total FROM Expense WHERE cardNumber = ? AND DATE(date) = ?";

		try (PreparedStatement statement = Connect.con.prepareStatement(sql)) {
			statement.setString(1, Main.getSessionPCard().getCardNumber());
			statement.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Using current date

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					pbDailyLimit = rs.getDouble("total");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exception
		}
		return pbDailyLimit;
	}

	private double getMonthlyExpenses() {
		double pbMonthlyLimit = 0.0;
		String sql = "SELECT SUM(amount) AS total FROM Expense WHERE cardNumber = ? AND MONTH(date) = ? AND YEAR(date) = ?";
		LocalDate today = LocalDate.now();

		try (PreparedStatement statement = Connect.con.prepareStatement(sql)) {
			statement.setString(1, Main.getSessionPCard().getCardNumber());
			statement.setInt(2, today.getMonthValue());
			statement.setInt(3, today.getYear());

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					pbMonthlyLimit = rs.getDouble("total");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exception
		}
		return pbMonthlyLimit;
	}
	@Override
	public void constructTvExpenses(ArrayList<Expense> sortedExpenses) {
		ArrayList<Expense> expenseList = sortedExpenses;
		
		
		tcAmount.setCellValueFactory(new PropertyValueFactory<Expense, Double>("amount"));
		tcApprovalStatus.setCellValueFactory(new PropertyValueFactory<Expense, String>("approvalStatus"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
		tcMemo.setCellValueFactory(new PropertyValueFactory<Expense, String>("memo"));
		
		tvExpenses.getItems().setAll(expenseList);

		
	}

}
