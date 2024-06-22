package com.example.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.example.controllers.MenuController;
import com.example.controllers.SortExpensesController;
import com.example.controllers.SortRequestsController;
import com.example.cus1166.Main;
import com.example.objects.Expense;
import com.example.objects.Message;
import com.example.objects.Request;
import com.example.userclasses.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

public class HelperUtils {

	public static void authenticateUser(ActionEvent event, String employeeId, String password) {
		try {

			String query = "SELECT * FROM employee, department WHERE employeeId = ? and employee.departmentId = department.departmentId";
			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setString(1, employeeId);
			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				showAlert("Authentication Failed", "Authentication failed. Invalid credentials.");
			} else {
				while (resultSet.next()) {
					String passwordCheck = resultSet.getString("password");
					if (Encrypt.checkPass(password, passwordCheck)) {
						String queryPcardAdmin = "SELECT * FROM administrates_pcard WHERE employeeId = ?";
						PreparedStatement statement2 = Connect.con.prepareStatement(queryPcardAdmin);
						statement2.setString(1, employeeId);
						ResultSet resultSet2 = statement2.executeQuery();

						String queryBudgetAdmin = "SELECT * FROM budgets_for WHERE employeeId = ?";
						PreparedStatement statement3 = Connect.con.prepareStatement(queryBudgetAdmin);
						statement3.setString(1, employeeId);
						ResultSet resultSet3 = statement3.executeQuery();

						String queryRegularAdmin = "SELECT * FROM administrates WHERE employeeId = ?";
						PreparedStatement statement4 = Connect.con.prepareStatement(queryRegularAdmin);
						statement4.setString(1, employeeId);
						ResultSet resultSet4 = statement4.executeQuery();

						if (resultSet2.next()) {// check if pcard admin
							Main.setUser(new PCardAdmin(resultSet.getString("fname"), resultSet.getString("lname"),
									resultSet.getString("employeeId"), resultSet.getInt("departmentId"),
									resultSet.getString("dname")));

						} else if (resultSet3.next()) {// check if budget admin
							Main.setUser(new BudgetAdmin(resultSet.getString("fname"), resultSet.getString("lname"),
									resultSet.getString("employeeId"), resultSet.getInt("departmentId"),
									resultSet.getString("dname")));

						} else if (resultSet4.next()) {// regular admin
							Main.setUser(new SystemAdmin(resultSet.getString("fname"), resultSet.getString("lname"),
									resultSet.getString("employeeId"), resultSet.getInt("departmentId"),
									resultSet.getString("dname")));
						} else { // create employee
							Main.setUser(new Employee(resultSet.getString("fname"), resultSet.getString("lname"),
									resultSet.getString("employeeId"), resultSet.getInt("departmentId"),
									resultSet.getString("dname")));

						}
						HelperUtils.checkPCard();
						SceneChanger.changeToMessages(event);

						// close sub queries
						resultSet2.close();
						statement2.close();
						resultSet3.close();
						statement3.close();
						resultSet4.close();
						statement4.close();
					} else {
						showAlert("Authentication Failed", "Authentication failed. Invalid credentials.");
					}

				}

			}

			// Close main query
			resultSet.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void addMessages(ArrayList<Message> messageList) {

		try {
			String query = "SELECT message.messageId,date,headerText,bodyText,fname,lname "
					+ "FROM message,employee,employee_views_message " + "WHERE employee.employeeId = message.creatorId "
					+ "AND employee_views_message.messageId = message.messageId "
					+ "AND employee_views_message.employeeId = ? " + "ORDER BY date DESC";
			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getSessionUser().getXNumber());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				messageList.add(new Message(resultSet.getInt("messageId"), resultSet.getDate("date"),
						resultSet.getString("headerText"), resultSet.getString("bodyText"),
						resultSet.getString("fname") + " " + resultSet.getString("lname")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// TODO bookmark
	public static ArrayList<Message> addSortedMessages(String from, String textContains, LocalDate date,
			RadioButton dateOption, String orderOption, RadioButton orderDirection) {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<Message> list = new ArrayList<Message>();
		try {
			String query = "SELECT message.messageId,date,headerText,bodyText,fname,lname "
					+ " FROM message,employee,employee_views_message "
					+ " WHERE employee.employeeId = message.creatorId "
					+ " AND employee_views_message.messageId = message.messageId "
					+ " AND employee_views_message.employeeId = ? ";

			// check if from was given
			if (!from.isBlank()) {
				query += " AND (lower(employee.fname) LIKE lower(?) OR lower(employee.lname) LIKE lower(?)) ";
			}

			// check if textContains was given
			if (!textContains.isBlank()) {
				query += " AND (lower(message.headerText) LIKE lower(?) OR lower(message.bodyText) LIKE lower(?))";
			}

			// check if date is given

			if (!dateOption.getText().equals("All Dates")) {
				// all queries will need a year
				query += " AND YEAR(date) = ?";

				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {
					query += " AND MONTH(date) = ? ";

				}
				if (dateOption.getText().equals("Day")) {
					query += " AND DAY(date) = ? ";

				}

			}
			String orderBy = " ORDER BY ";
			// adds order by to query
			query += orderBy;
			// orders list depending on chosen option, defaults to date descending if no
			// chosen option
			if (orderOption == null) {
				query += " date ";
			} else {
				if (orderOption.equals("Date")) { // can add more options if needed
					query += " date";
				}
			}

			// sets order that result set will be in, default descending
			if (orderDirection.getText().equals("Ascending")) {
				query += " ASC";
			} else {
				query += " DESC";
			}

			st = Connect.con.prepareStatement(query);
			// set parameters
			int count = 1;
			st.setString(1, Main.getSessionUser().getXNumber());
			if (!from.isBlank()) {
				count++;
				st.setString(count, "%" + from + "%");
				count++;
				st.setString(count, "%" + from + "%");
			}
			if (!textContains.isBlank()) {
				count++;
				st.setString(count, "%" + textContains + "%");
				count++;
				st.setString(count, "%" + textContains + "%");
			}
			// sets date parameters if given
			if (!dateOption.getText().equals("All Dates")) {
				count++;
				st.setInt(count, date.getYear());

				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {
					count++;
					st.setInt(count, date.getMonthValue());
				}
				if (dateOption.getText().equals("Day")) {
					count++;
					st.setInt(count, date.getDayOfMonth());
				}

			}

			rs = st.executeQuery();

			while (rs.next()) {
				list.add((new Message(rs.getInt("messageId"), rs.getDate("date"), rs.getString("headerText"),
						rs.getString("bodyText"), rs.getString("fname") + " " + rs.getString("lname"))));
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void createMessage(String header, String body, boolean admin, String receiverId) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> list;
		if (header.trim().length() > 0 && body.trim().length() > 0) {
			try {
				String insert = "INSERT into message(date,headerText,bodyText,creatorId) values(?,?,?,?)";
				statement = Connect.con.prepareStatement(insert);
				statement.setDate(1, getCurrentDate());
				statement.setString(2, header);
				statement.setString(3, body);
				statement.setString(4, Main.getSessionUser().getXNumber());
				statement.executeUpdate();
				if (admin) {
					list = new ArrayList<String>(getListOfEmployeesXNumbers());
				} else {
					list = new ArrayList<String>(Arrays.asList(receiverId));
				}
				String query = "select max(messageId) from message";
				statement = Connect.con.prepareStatement(query);
				resultSet = statement.executeQuery();
				int max = 0;

				while (resultSet.next()) {
					max = resultSet.getInt("max(messageId)");
				}
				// creates records for employee_views_message to track if message is read or not
				for (String num : list) {
					insert = "INSERT into employee_views_message values(?,?,?)";
					statement = Connect.con.prepareStatement(insert);
					statement.setString(1, num);
					statement.setInt(2, max);
					statement.setBoolean(3, false);

					statement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// Close resources
				try {
					if (statement != null) {
						statement.close();
					}
					if (resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else

		{
			showAlert("Empty Header and/or Body Text", "Message failed to send due to empty header or body text.");
		}

	}

	public static java.sql.Date getCurrentDate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(currentDate);
		Date date;
		try {
			date = dateFormat.parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return null;

	}

	public static boolean getMessageRead(Message message) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			String query = "SELECT * FROM employee_views_message WHERE employeeId = ? AND messageId = ?";
			statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getSessionUser().getXNumber());
			statement.setInt(2, message.getMessageId());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getBoolean("viewStatus")) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public static ArrayList<String> getListOfEmployeesXNumbers() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			String query = "SELECT employeeId FROM employee;";
			statement = Connect.con.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("employeeId"));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void updateMessageRead(Message message) {
		PreparedStatement statement = null;

		try {
			String update = "UPDATE employee_views_message SET viewStatus = 1 WHERE employeeID = ? AND messageId = ?";
			statement = Connect.con.prepareStatement(update);
			statement.setString(1, Main.getSessionUser().getXNumber());
			statement.setInt(2, message.getMessageId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void checkUserPermissions(String role, MenuController controller) {
		// check if user is Budget Admin
		if (!Main.getSessionUser().getRole().equals("Budget Admin")) {
			controller.hideRBRequests();
		}
		// check if user is P Card Admin
		if (!Main.getSessionUser().getRole().equals("PCardAdmin")) {
			controller.hidePCardRequests();
		}

	}

	public static void setNameAndRole(String fname, String role, MenuController controller) {
		controller.setWelcomeFname(fname);
		controller.setRole(role);
	}

	public static void addExpense(ArrayList<Expense> expenseList) {

		try {
			String query = "SELECT expense.*, employee.fname,employee.lname " + "FROM expense,employee "
					+ "WHERE ? = expense.employeeId AND employee.employeeId = expense.employeeId "
					+ "ORDER BY date DESC";
			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getSessionUser().getXNumber());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				expenseList.add(new Expense(resultSet.getInt("expenseId"), resultSet.getString("employeeId"),
						resultSet.getString("fname") + " " + resultSet.getString("lname"),
						resultSet.getString("approvalStatus"), resultSet.getDouble("amount"),
						resultSet.getString("expenseType"), resultSet.getDate("date"), resultSet.getString("memo"),
						resultSet.getString("cardType"), resultSet.getString("cardNumber"),
						resultSet.getString("approverId")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// TODO bookmark
	public static ArrayList<Expense> addSortedExpenses(SortExpensesController controller, boolean approved,
			boolean pending, boolean returned, String expense1, String expense2, String expense3, LocalDate date,
			RadioButton dateOption, String order, RadioButton orderDirection) {
		try {
			String query = "SELECT expense.*,employee.fname,employee.lname FROM expense,employee ";
			String orderBy = " ORDER BY ";
			boolean hasApprovalStatus = false;

			// check to see if from reinbursement scene

			if (controller.getClass().getName().equals("com.example.controllers.ReimbursementController")) {
				query += " WHERE ? = expense.approverId";
			} else {
				query += " WHERE ? = expense.employeeId";
			}

			// adds approval status to arraylist for each that was selected
			ArrayList<String> approvalStatus = new ArrayList<String>();
			ArrayList<String> expenseTypes = new ArrayList<String>();
			if (approved) {
				approvalStatus.add("Approved");
			}
			if (pending) {
				approvalStatus.add("Pending");
			}
			if (returned) {
				approvalStatus.add("Returned");
			}
			// adds expense types to arraylist if exists for each expense type
			if (!expense1.isBlank()) {
				expenseTypes.add("%" + expense1 + "%");
			}
			if (!expense2.isBlank()) {
				expenseTypes.add("%" + expense2 + "%");
			}
			if (!expense3.isBlank()) {
				expenseTypes.add("%" + expense3 + "%");
			}
			if (!approvalStatus.isEmpty() || !expenseTypes.isEmpty()) {
				query += " AND ";
				if (!approvalStatus.isEmpty()) {
					query += " approvalStatus IN (";
					for (int i = 0; i < approvalStatus.size(); i++) {
						if (i > 0) {
							query += ",";
						}
						query += "?";
					}
					query += ")";
					hasApprovalStatus = true;
				}
				if (!expenseTypes.isEmpty()) {
					if (hasApprovalStatus) {
						query += " AND ";
					}
					query += " (lower(expenseType) LIKE lower(?) ";
					if (expenseTypes.size() > 1) {
						query += " OR expenseType LIKE "
								+ repeat("lower(?)", expenseTypes.size() - 1, " OR lower(expenseType) LIKE ");
					}
					query += ")";

				}
			}

			// check if date is given

			if (!dateOption.getText().equals("All Dates")) {
				// all queries will need a year
				query += " AND YEAR(date) = ?";

				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {
					query += " AND MONTH(date) = ? ";

				}
				if (dateOption.getText().equals("Day")) {
					query += " AND DAY(date) = ? ";

				}

			}
			// checks if user is sorting from pcard scene
			if (controller.getClass().getName().equals("PCardController")) {
				query += "AND expense.cardNumber = ?";
			}

			query += " AND employee.employeeId = expense.employeeId";
			// adds order by to query
			query += orderBy;
			// orders list depending on chosen option, defaults to date descending if no
			// chosen option
			if (order == null) {
				query += "date ";
			} else {
				if (order.equals("Amount")) {
					query += "amount ";
				}
				if (order.equals("Approval Status")) {
					query += " approvalStatus";
				}
				if (order.equals("Date")) {
					query += " date";
				}
				if (order.equals("Expense Type")) {
					query += " expenseType";

				}
			}

			// sets order that result set will be in, default descending
			if (orderDirection.getText().equals("Ascending")) {
				query += " ASC";
			} else {
				query += " DESC";
			}

			PreparedStatement statement = Connect.con.prepareStatement(query);

			statement.setString(1, Main.getSessionUser().getXNumber());
			int count = 1;
			if (!approvalStatus.isEmpty() || !expenseTypes.isEmpty()) {
				if (!approvalStatus.isEmpty()) {
					for (int i = 1; i <= approvalStatus.size(); i++) {
						statement.setString(1 + count, approvalStatus.get(i - 1));
						count++;
					}

				}

				if (!expenseTypes.isEmpty()) {
					for (int i = 1; i <= expenseTypes.size(); i++) {

						statement.setString(1 + count, expenseTypes.get(i - 1));
						count++;
					}
				}
			}

			// sets date parameters if given
			if (!dateOption.getText().equals("All Dates")) {
				statement.setInt(1 + count, date.getYear());
				count++;

				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {

					statement.setInt(1 + count, date.getMonthValue());
					count++;

				}
				if (dateOption.getText().equals("Day")) {
					statement.setInt(1 + count, date.getDayOfMonth());
					count++;

				}

			}
			if (controller.getClass().getName().equals("PCardController")) {
				statement.setString(1 + count, Main.getSessionPCard().getCardNumber());
			}


			ResultSet resultSet = statement.executeQuery();
			ArrayList<Expense> list = new ArrayList<Expense>();

			while (resultSet.next()) {
				list.add(new Expense(resultSet.getInt("expenseId"), resultSet.getString("employeeId"),
						resultSet.getString("fname") + " " + resultSet.getString("lname"),
						resultSet.getString("approvalStatus"), resultSet.getDouble("amount"),
						resultSet.getString("expenseType"), resultSet.getDate("date"), resultSet.getString("memo"),
						resultSet.getString("cardType"), resultSet.getString("cardNumber"),
						resultSet.getString("approverId")));
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return null;

	}

	// method for addSortedExpenses
	private static String repeat(String string, int times, String delimiter) {
		return String.join(delimiter, java.util.Collections.nCopies(times, string));
	}

// Method for checking if there is a Pcard For the user logged in.
	public static void checkPCard() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Checking the PCard validity
			String query = "SELECT * FROM pCard WHERE employeeId = ?";
			statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getUser().getXNumber());

			resultSet = statement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				Main.setPCard(new SessionPCard());
				System.out.println("No PCard Detected");
			}
			while (resultSet.next()) {
				Main.setPCard(new SessionPCard(resultSet.getString("PCardID"), resultSet.getDouble("dailyLimit"),
						resultSet.getDouble("monthlyLimit")));

				// Showing the card number and limits to the user
				showAlert("Authentication Successful",
						"PCard #: " + Main.getSessionPCard().getCardNumber() + "\nDaily Limit: $"
								+ Main.getSessionPCard().getDailyLimit() + "\nMonthly Limit: $"
								+ Main.getSessionPCard().getMonthlyLimit());
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			showAlert("Error", "A database error occurred. Please try again.");
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				System.err.println("SQL Exception on close: " + e.getMessage());
			}
		}
	}

// Method for displaying the daily expense for the current date
	public static void displayDailyExpenses() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Assuming we already have a valid PCard session set from checkPCard method
			String pCardId = Main.getSessionPCard().getCardNumber();
			if (pCardId == null) {
				System.out.println("No PCard Detected or Session Not Set");
				return;
			}

			// SQL query to sum up today's expenses for this PCard
			String query = "SELECT SUM(amount) AS totalSpent FROM expense WHERE PCardID = ? AND date = ?";
			statement = Connect.con.prepareStatement(query);
			statement.setString(1, pCardId);
			statement.setDate(2, java.sql.Date.valueOf(LocalDate.now())); // Today's date

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				double totalSpent = resultSet.getDouble("totalSpent");
				if (resultSet.wasNull()) {
					totalSpent = 0.0; // Handling the case where no transactions were found
				}

				// Display the total spent today
				showAlert("Daily Expenses", "Total spent today using PCard #" + pCardId + ": $" + totalSpent);
			} else {
				showAlert("No Expenses", "No expenses recorded for today.");
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			showAlert("Error", "A database error occurred. Please try again.");
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				System.err.println("SQL Exception on close: " + e.getMessage());
			}
		}
	}

	// method to add a new expense
	public static void createExpense(double amount, String expenseType, String memo, String cardType, String cardNumber,
			BudgetAdmin approver) {
		String approverId = approver.getXNumber();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		if (amount != 0) {
			try {
				String insert = "INSERT into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values(?,?,?,?,?,?,?,?,?)";
				statement = Connect.con.prepareStatement(insert);
				statement.setString(1, Main.getUser().getXNumber());
				statement.setString(2, "Pending");
				statement.setDouble(3, amount);
				statement.setString(4, expenseType);
				statement.setDate(5, getCurrentDate());
				statement.setString(6, memo);
				statement.setString(7, cardType);
				statement.setString(8, cardNumber);
				statement.setString(9, approverId);
				statement.executeUpdate();
				System.out.println("Date inserted successfully.");

				String query = "select max(expenseId) from expense";
				statement = Connect.con.prepareStatement(query);
				resultSet = statement.executeQuery();
				int max = 0;

				while (resultSet.next()) {
					max = resultSet.getInt("max(expenseId)");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// Close resources
				try {
					if (statement != null) {
						statement.close();
					}
					if (resultSet != null) {
						resultSet.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else

		{
			showAlert("Empty expense", "No expense inserted.");
		}

	}

	public static void addExpenseRequest(ArrayList<Expense> expenseList) {

		try {
			String query = "SELECT expense.*, employee.fname, employee.lname " + "FROM expense,employee "
					+ "WHERE ? = expense.approverId " + "AND expense.employeeId = employee.employeeId "
					+ "ORDER BY date DESC"; // order by date
			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getSessionUser().getXNumber());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				expenseList.add(new Expense(resultSet.getInt("expenseId"), resultSet.getString("employeeId"),
						resultSet.getString("fname") + " " + resultSet.getString("lname"),
						resultSet.getString("approvalStatus"), resultSet.getDouble("amount"),
						resultSet.getString("expenseType"), resultSet.getDate("date"), resultSet.getString("memo"),
						resultSet.getString("cardType"), resultSet.getString("cardNumber"),
						resultSet.getString("approverId")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean getApprovalStatus(Expense expense) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			String query = "SELECT * FROM expense WHERE expense.expenseId = ?";
			statement = Connect.con.prepareStatement(query);
			statement.setInt(1, expense.getExpenseId());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString("approvalStatus").equals("Pending")) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean checkBudgetAdmin(MenuController baseController) {
		if (baseController.getClass().getName().equals("com.example.controllers.ReimbursementController")) {
			return true;
		} else {
			return false;
		}

	}

	public static String checkRequest(String baseController) {
		if (baseController.equals("com.example.controllers.ExpenseDetailsPopupController")) {
			return "expense";
		} else if (baseController.equals("com.example.controllers.PCardRequestDetailsPopupController")) {
			return "pcard";
		}
		return "error";
	}

	public static void createPCardRequest(String memo, String approverId, String employeeId, Double dailyLimit,
			Double monthlyLimit) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			String insert = "insert into employee_requests_pcard(requesterId, employeeId, memo, dailyLimit, monthlyLimit, pCardAdminId,date,approvalStatus) values (?,?,?,?,?,?,?,?)";
			statement = Connect.con.prepareStatement(insert);
			statement.setString(1, Main.getUser().getXNumber());
			statement.setString(2, employeeId); // get employee id
			statement.setString(3, memo);
			statement.setDouble(4, dailyLimit);
			statement.setDouble(5, monthlyLimit);
			statement.setString(6, approverId);
			statement.setDate(7, getCurrentDate());
			statement.setString(8, "Pending");
			statement.executeUpdate();
			System.out.println("P Card requested successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void denyRequest(String thingToDeny, String reason, Expense expense, Request request) {
		handleRequest(false, thingToDeny, reason, expense, request, null, null, null);
	}

	public static void approveRequest(String thingToApprove, String reason, Expense expense, Request request,
			String pCardNumber, Double dailyLimit, Double monthlyLimit) {
		handleRequest(true, thingToApprove, reason, expense, request, pCardNumber, dailyLimit, monthlyLimit);

	}

	private static void handleRequest(boolean approved, String thingToHandle, String reason, Expense expense,
			Request request, String pCardNumber, Double dailyLimit, Double monthlyLimit) {
		PreparedStatement stUpdate = null;
		PreparedStatement stReason = null;
		PreparedStatement stAddPcard = null;

		try {
			// handle request
			String update = "UPDATE ";
			String decision;
			String pCardMemo;
			// check what is being handled
			int condition = 0;
			if (thingToHandle.equals("expense")) {
				update += "expense SET approvalStatus = ? WHERE expenseId = ?";
				condition = expense.getExpenseId();

			} else if (thingToHandle.equals("pcard")) {
				update += "employee_requests_pcard SET approvalStatus = ? WHERE requestId = ?";
				condition = request.getRequestId();
			}
			stUpdate = Connect.con.prepareStatement(update);
			if (approved) {
				stUpdate.setString(1, "Approved");
				decision = "Approved";
			} else {
				stUpdate.setString(1, "Returned");
				decision = "Denied";
			}
			stUpdate.setInt(2, condition);

			stUpdate.executeUpdate();
			System.out.println(thingToHandle + " " + decision + " successfully.");
			// if pcard is approved, make pcard table
			if (thingToHandle.equals("pcard") && decision.equals("Approved")) {
				String addPCard = "INSERT INTO pcard values(?,?,?,?)";
				stAddPcard = Connect.con.prepareStatement(addPCard);

				stAddPcard.setString(1, pCardNumber);
				stAddPcard.setString(2, request.getEmployeeId());
				stAddPcard.setDouble(3, dailyLimit);
				stAddPcard.setDouble(4, monthlyLimit);

				stAddPcard.executeUpdate();

			}

			// create approves table with reason
			String insert = "INSERT into ";
			if (thingToHandle.equals("expense")) {
				insert += "approves_expense values(?,?,?,?)";

			} else if (thingToHandle.equals("pcard")) {
				insert += "approves_pcard values (?,?,?,?)";
			}
			stReason = Connect.con.prepareStatement(insert);
			stReason.setString(1, Main.getSessionUser().getXNumber());
			stReason.setInt(2, condition);
			stReason.setDate(3, getCurrentDate());
			stReason.setString(4, reason);

			stReason.executeUpdate();
			System.out.println("Approves reason created successfully");

			// creates message to be sent to the request's sender
			if (thingToHandle.equals("expense")) {
				createMessage(decision + " Expense Request",
						"Your expense request was " + decision + " for the following reason: " + reason, false,
						expense.getEmployeeId());
			} else if (thingToHandle.equals("pcard")) {
				if (pCardNumber != null && approved) {
					pCardMemo = "\r\nYour PCard Number is: " + pCardNumber;

				} else {
					pCardMemo = "";
				}
				createMessage(decision + " PCard Request",
						"The PCard request that " + request.getRequesterName() + " submitted for you was " + decision.toLowerCase() + " for the following reason: " + reason + pCardMemo,
						false, request.getEmployeeId());
				createMessage(decision + " PCard Request",
						"Your PCard request was " + decision + " for the following reason: " + reason + pCardMemo,
						false, request.getRequesterId());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stUpdate != null) {
					stUpdate.close();
				}
				if (stReason != null) {
					stReason.close();
				}
				if (stAddPcard != null) {
					stAddPcard.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void addPCardRequest(ArrayList<Request> pCardList) {

		try {
			String query = "select employee_requests_pcard.*, e1.fname as empFname,e1.lname as empLname, e2.fname as reqFname, e2.lname as reqLname"
					+ " from employee_requests_pcard, employee as e1, employee as e2"
					+ " where employee_requests_pcard.employeeId = e1.employeeId"
					+ " and employee_requests_pcard.requesterId = e2.employeeId" + " ORDER BY date DESC";
			PreparedStatement statement = Connect.con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pCardList.add(new Request(resultSet.getInt("requestId"), resultSet.getDate("date"),
						resultSet.getString("requesterId"), resultSet.getString("employeeId"),
						resultSet.getString("empFname") + " " + resultSet.getString("empLname"),
						resultSet.getString("reqFname") + " " + resultSet.getString("reqLname"),
						resultSet.getString("memo"), resultSet.getDouble("dailyLimit"),
						resultSet.getDouble("monthlyLimit"), resultSet.getString("pcardAdminId"),
						resultSet.getString("approvalStatus")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addSubmittedPCardRequest(ArrayList<Request> pCardList) {

		try {
			String query = "select employee_requests_pcard.*, e1.fname as empFname,e1.lname as empLname, e2.fname as reqFname, e2.lname as reqLname"
					+ " from employee_requests_pcard, employee as e1, employee as e2"
					+ " where employee_requests_pcard.employeeId = e1.employeeId"
					+ " and employee_requests_pcard.requesterId = e2.employeeId" + " and e2.employeeId = ?"
					+ " ORDER BY date DESC";
			PreparedStatement statement = Connect.con.prepareStatement(query);
			statement.setString(1, Main.getSessionUser().getXNumber());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pCardList.add(new Request(resultSet.getInt("requestId"), resultSet.getDate("date"),
						resultSet.getString("requesterId"), resultSet.getString("employeeId"),
						resultSet.getString("empFname") + " " + resultSet.getString("empLname"),
						resultSet.getString("reqFname") + " " + resultSet.getString("reqLname"),
						resultSet.getString("memo"), resultSet.getDouble("dailyLimit"),
						resultSet.getDouble("monthlyLimit"), resultSet.getString("pcardAdminId"),
						resultSet.getString("approvalStatus")));
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static double getCurrentYearlyLimit(String XNumber) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String query = "SELECT SUM(amount) AS totalSpent FROM expense WHERE employeeId = ? AND date BETWEEN ? AND ?";
			st = Connect.con.prepareStatement(query);
			st.setString(1, XNumber);
			st.setDate(2, java.sql.Date.valueOf(getCurrentDate().toLocalDate().getYear() - 1 + "-09-01"));
			st.setDate(3, java.sql.Date.valueOf(getCurrentDate().toLocalDate().getYear() + "-09-01"));
			rs = st.executeQuery();

			while (rs.next()) {
				return rs.getDouble("totalSpent");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}

	public static double getMaxYearlyLimit(String XNumber) {
		PreparedStatement st = null;
		ResultSet rs = null;
		double sum = 0;

		try {
			String query = "SELECT * from (SELECT " + "university_selects_policy.universityId AS id,"
					+ " university_selects_policy.policyId as policyId," + " policy.policyAmount "
					+ "from university_selects_policy, policy " + "where university_selects_policy.universityId = 1 " // hardcoded
																														// st
																														// johns
																														// university
					+ "and university_selects_policy.policyId = policy.policyId " + "UNION ALL "
					+ "select department_selects_policy.departmentId as id,"
					+ "department_selects_policy.policyId as policyId," + "policy.policyAmount "
					+ "from department_selects_policy, policy,employee "
					+ "where department_selects_policy.departmentId = employee.departmentId "
					+ "and employee.employeeId = ? " + "and department_selects_policy.policyId = policy.policyId) "
					+ "as combined_results";
			st = Connect.con.prepareStatement(query);
			st.setString(1, XNumber);

			rs = st.executeQuery();

			while (rs.next()) {
				sum += rs.getDouble("policyAmount");
			}
			return sum;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return sum;
	}

	public static SessionUser getEmployeeDetails(String XNumber) {
		PreparedStatement st = null;
		ResultSet rs = null;
		SessionUser employee = null;

		try {
			// get employee fname, name, department name
			String queryEmp = "select employee.employeeId, employee.fname, employee.lname,department.departmentId, department.dname from employee, department"
					+ " where employee.employeeId = ?" + " and employee.departmentId = department.departmentId";

			st = Connect.con.prepareStatement(queryEmp);
			st.setString(1, XNumber);
			rs = st.executeQuery();

			while (rs.next()) {
				// get employee role
				String queryPcard = "SELECT * FROM administrates_pcard WHERE employeeId = ?";
				PreparedStatement stPCard = Connect.con.prepareStatement(queryPcard);
				stPCard.setString(1, XNumber);
				ResultSet rsPcard = stPCard.executeQuery();

				String queryBudget = "SELECT * FROM budgets_for WHERE employeeId = ?";
				PreparedStatement stBudget = Connect.con.prepareStatement(queryBudget);
				stBudget.setString(1, XNumber);
				ResultSet rsBudget = stBudget.executeQuery();

				String queryRegular = "SELECT * FROM administrates WHERE employeeId = ?";
				PreparedStatement stRegular = Connect.con.prepareStatement(queryRegular);
				stRegular.setString(1, XNumber);
				ResultSet rsRegular = stRegular.executeQuery();

				if (rsPcard.next()) {// check if pcard admin
					employee = new PCardAdmin(rs.getString("fname"), rs.getString("lname"), rs.getString("employeeId"),
							rs.getInt("departmentId"), rs.getString("dname"));

				} else if (rsBudget.next()) {// check if budget admin
					employee = new BudgetAdmin(rs.getString("fname"), rs.getString("lname"), rs.getString("employeeId"),
							rs.getInt("departmentId"), rs.getString("dname"));

				} else if (rsRegular.next()) {// regular admin
					employee = new SystemAdmin(rs.getString("fname"), rs.getString("lname"), rs.getString("employeeId"),
							rs.getInt("departmentId"), rs.getString("dname"));
				} else { // create employee
					employee = new Employee(rs.getString("fname"), rs.getString("lname"), rs.getString("employeeId"),
							rs.getInt("departmentId"), rs.getString("dname"));

				}
				return employee;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return employee;

	}

	public static boolean checkExistingPCard(String number) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "Select PCardId from pcard where PCardId = ?";
			st = Connect.con.prepareStatement(query);
			st.setString(1, number);

			rs = st.executeQuery();

			if (rs.isBeforeFirst()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {

		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	public static void createUser(String employeeId, String password, String fName, String lName, int departmentId) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String insert = "INSERT into employee(employeeId,password,fName,lName,departmentId) values(?,?,?,?,?)";
			statement = Connect.con.prepareStatement(insert);
			statement.setString(1, employeeId);
			statement.setString(2, password);
			statement.setString(3, fName);
			statement.setString(4, lName);
			statement.setInt(5, departmentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void createRole(String employeeId, int departmentId, String role) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String insert = null;
		try {
			if ("Budget Admin".equals(role)) {
				insert = "INSERT into budgets_for(employeeId,departmentId) values(?,?)";
				statement = Connect.con.prepareStatement(insert);
				statement.setString(1, employeeId);
				statement.setInt(2, departmentId);
				statement.executeUpdate();
			}
			if ("PCard Admin".equals(role)) {
				insert = "INSERT into administrates_pcard(employeeId) values(?)";
				statement = Connect.con.prepareStatement(insert);
				statement.setString(1, employeeId);
				statement.executeUpdate();
			}
			if ("System Admin".equals(role)) {
				insert = "INSERT into administrates(employeeId) values(?)";
				statement = Connect.con.prepareStatement(insert);
				statement.setString(1, employeeId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<Request> addSortedRequests(SortRequestsController sourceController, boolean approved,
			boolean pending, boolean returned, String from, String textContains, LocalDate date, RadioButton dateOption,
			String orderOption, RadioButton orderDirection) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<Request> list = new ArrayList<Request>();

		try {
			String query = "select employee_requests_pcard.*, e1.fname as empFname,e1.lname as empLname, e2.fname as reqFname, e2.lname as reqLname"
					+ " from employee_requests_pcard, employee as e1, employee as e2"
					+ " where employee_requests_pcard.employeeId = e1.employeeId"
					+ " and employee_requests_pcard.requesterId = e2.employeeId";

			// check if from viewing own requests or other requests
			if (!sourceController.getClass().getName().equals("com.example.controllers.RequestGrantPCardController")) {
				query += " AND employee_requests_pcard.requesterId = ? ";
			}

			// check if any statuses were chosen
			ArrayList<String> approvalStatus = new ArrayList<String>();

			if (approved) {
				approvalStatus.add("Approved");
			}
			if (pending) {
				approvalStatus.add("Pending");
			}
			if (returned) {
				approvalStatus.add("Returned");
			}
			if (!approvalStatus.isEmpty()) {
				query += " AND approvalStatus IN (";
				for (int i = 0; i < approvalStatus.size(); i++) {
					if (i > 0) {
						query += ",";
					}
					query += "?";
				}
				query += ")";
			}

			// check if from was given
			if (!from.isBlank()) {
				if (!sourceController.getClass().getName()
						.equals("com.example.controllers.RequestGrantPCardController")) {
					query += " AND (lower(e1.fname) LIKE lower(?) OR lower(e1.lname) LIKE lower(?)) ";
				} else {
					query += " AND (lower(e2.fname) LIKE lower(?) OR lower(e2.lname) LIKE lower(?)) ";
				}

			}

			// check if textContains was given
			if (!textContains.isBlank()) {
				query += " AND lower(employee_requests_pcard.memo) LIKE lower(?) ";
			}

			// check if date is given

			if (!dateOption.getText().equals("All Dates")) {
				// all queries will need a year
				query += " AND YEAR(date) = ?";

				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {
					query += " AND MONTH(date) = ? ";

				}
				if (dateOption.getText().equals("Day")) {
					query += " AND DAY(date) = ? ";

				}

			}
			String order = " ORDER BY ";
			// orders list depending on chosen option, defaults to date descending if no
			// chosen option
			if (orderOption == null) {
				order += " date ";
			} else {
				if (orderOption.equals("Approval Status")) {
					order += " approvalStatus";
				}
				if (orderOption.equals("Date")) {
					order += " date";
				}
				if (orderOption.equals("Requester")) {
					order += " reqFname";
				}
				if (orderOption.equals("Employee")) {
					order += " empFname";
				}

			}

			// sets order that result set will be in, default descending
			if (orderDirection.getText().equals("Ascending")) {
				order += " ASC";
			} else {
				order += " DESC";
			}

			query += order;

			st = Connect.con.prepareStatement(query);

			// set parameters
			int count = 0;
			if (!sourceController.getClass().getName().equals("com.example.controllers.RequestGrantPCardController")) {
				count++;
				st.setString(count, Main.getSessionUser().getXNumber());
			}

			if (!approvalStatus.isEmpty()) {
				for (int i = 1; i <= approvalStatus.size(); i++) {
					count++;
					st.setString(count, approvalStatus.get(i - 1));

				}
			}
			if (!from.isBlank()) {
				count++;
				st.setString(count, "%" + from + "%");
				count++;
				st.setString(count, "%" + from + "%");
			}
			if (!textContains.isBlank()) {
				count++;
				st.setString(count, "%" + textContains + "%");
			}

			// sets date parameters if given
			if (!dateOption.getText().equals("All Dates")) {
				count++;
				st.setInt(count, date.getYear());
				if (dateOption.getText().equals("Month") || dateOption.getText().equals("Day")) {
					count++;
					st.setInt(count, date.getMonthValue());
				}
				if (dateOption.getText().equals("Day")) {
					count++;
					st.setInt(count, date.getDayOfMonth());
				}
			}
			rs = st.executeQuery();

			while (rs.next()) {
				list.add(new Request(rs.getInt("requestId"), rs.getDate("date"), rs.getString("requesterId"),
						rs.getString("employeeId"), rs.getString("empFname") + " " + rs.getString("empLname"),
						rs.getString("reqFname") + " " + rs.getString("reqLname"), rs.getString("memo"),
						rs.getDouble("dailyLimit"), rs.getDouble("monthlyLimit"), rs.getString("pcardAdminId"),
						rs.getString("approvalStatus")));
			}
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public static void changePassword(String newPW, String xNumber) {
		PreparedStatement st = null;

		try {
			String query = "UPDATE employee SET password = ? where employeeId = ?";
			st = Connect.con.prepareStatement(query);

			st.setString(1, Encrypt.encryptPass(newPW));
			st.setString(2, xNumber);

			st.executeUpdate();
			showAlert("Password Change Successful", "You have successfully changed your password.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
