package com.example.utils;

import java.io.IOException;

import com.example.controllers.ApproveRequestPopupController;
import com.example.controllers.SortExpensesController;
import com.example.controllers.DenyRequestPopupController;
import com.example.controllers.ExpenseDetailsPopupController;
import com.example.controllers.ExpensesController;
import com.example.controllers.MenuController;
import com.example.controllers.MessageController;
import com.example.controllers.MessagePopupController;
import com.example.controllers.PCardRequestDetailsPopupController;
import com.example.controllers.ReimbursementController;
import com.example.controllers.RequestGrantPCardController;
import com.example.controllers.SortExpensesPopupController;
import com.example.controllers.SortRequestsController;
import com.example.controllers.SortRequestsPopupController;
import com.example.cus1166.Main;
import com.example.objects.Expense;
import com.example.objects.Message;
import com.example.objects.Request;
import com.example.userclasses.SessionPCard;
import com.example.userclasses.User;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneChanger {

	public static MessageController messageController;
	public static ExpensesController expensesController;
	public static ReimbursementController reinbursementController;
	public static ExpenseDetailsPopupController expenseDetailsPopupController;
	public static PCardRequestDetailsPopupController pCardRequestDetailsPopupController;
	public static RequestGrantPCardController requestGrantPCardController;

	public static void changeToMessages(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(SceneChanger.class.getClassLoader().getResource("MessageScene.fxml"));
			root = loader.load();

			messageController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Main Menu"); // sets title of stage
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void changeToLogin(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(SceneChanger.class.getClassLoader().getResource("LoginScene.fxml"));
			root = loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Main.setUser(new User());
		Main.setPCard(new SessionPCard());

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Main Menu"); // sets title of stage
		stage.setScene(new Scene(root));
		stage.show();

	}

	public static void changeToExpenses(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(SceneChanger.class.getClassLoader().getResource("ExpensesScene.fxml"));
			root = loader.load();

			expensesController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Expenses"); // sets title of stage
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void changeToPCard(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(SceneChanger.class.getClassLoader().getResource("PCardScene.fxml"));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("P Card"); // sets title of stage
		stage.setScene(new Scene(root));
		stage.show();

	}

	public static void changeToGrantPCardRequests(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("RequestGrantPCardScene.fxml"));
			root = loader.load();
			requestGrantPCardController = loader.getController();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("P Card Requests"); // sets title of stage
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void messagePopup(Message rowMessage, MouseEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("MessagePopupScene.fxml"));
			Parent root = loader.load();

			MessagePopupController messagePopupController = loader.getController();
			messagePopupController.setHeaderText(rowMessage.getHeaderText());
			messagePopupController.setBodyText(rowMessage.getBodyText());

			Stage stage = new Stage();
			stage.setTitle("Message");
			Scene scene = new Scene(root, 600, 400);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createMessagePopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("CreateMessagePopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Create Message");
			Scene scene = new Scene(root, 400, 300);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void changeToReimbursement(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("ReimbursementScene.fxml"));
			Parent root = loader.load();

			reinbursementController = loader.getController();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Reinbursements"); // sets title of stage
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void sortExpensesPopup(ActionEvent event,SortExpensesController sourceController) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("SortExpensesPopupScene.fxml"));
			Parent root = loader.load();
			
			SortExpensesPopupController controller = loader.getController();
			//tracks which scene/table called for sort scene
			controller.setSourceController(sourceController);

			Stage stage = new Stage();
			stage.setTitle("Sort Expenses");
			Scene scene = new Scene(root, 400, 500);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addExpense(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("AddExpensePopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Add Expense");
			Scene scene = new Scene(root, 600, 400);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void viewExpenseDetails(Expense rowExpense, MouseEvent event, MenuController baseController) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("ExpenseDetailsPopupScene.fxml"));
			Parent root = loader.load();

			// method from ExpenseDetailsPopupController to fill in expense details
			expenseDetailsPopupController = loader.getController();
			// check if coming from expense scene or reinbursement scene
			if (!HelperUtils.checkBudgetAdmin(baseController)) {
				expenseDetailsPopupController.hideBudgetAdminControls();
			}
			expenseDetailsPopupController.setEmployee(HelperUtils.getEmployeeDetails(rowExpense.getEmployeeId()));
			expenseDetailsPopupController.setApprover(HelperUtils.getEmployeeDetails(rowExpense.getApproverId()));
			expenseDetailsPopupController.setExpense(rowExpense);
			expenseDetailsPopupController.setExpenseDetails();

			Stage stage = new Stage();
			stage.setTitle("Expense Details");
			Scene scene = new Scene(root, 375, 480);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void requestPCardPopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("RequestForPCardPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("P Card Request");
			Scene scene = new Scene(root, 400, 325);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("Failed to load the FXML file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void denyReasonPopup(ActionEvent event, String baseController, Expense expense, Request request) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("DenyRequestPopupScene.fxml"));

			Parent root = loader.load();

			DenyRequestPopupController controller = loader.getController();
			controller.setThingToDeny(HelperUtils.checkRequest(baseController));
			if (controller.getThingToDeny().equals("expense")) {
				controller.setExpense(expense);
			} else if (controller.getThingToDeny().equals("pcard")) {
				controller.setRequest(request);
			}
			Stage stage = new Stage();
			stage.setTitle("Deny Request");
			Scene scene = new Scene(root, 270, 320);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void approveReasonPopup(ActionEvent event, String baseController, Expense expense, Request request) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("ApproveRequestPopupScene.fxml"));
			Parent root = loader.load();

			ApproveRequestPopupController controller = loader.getController();
			controller.setThingToApprove(HelperUtils.checkRequest(baseController));
			if (controller.getThingToApprove().equals("expense")) {
				controller.setExpense(expense);
			} else if (controller.getThingToApprove().equals("pcard")) {
				controller.setRequest(request);
				controller.setLimits(request);
			}
			// checks if thing to approve is a pcard requests and hides p card stuff if
			// false
			int x = 300;
			int y = 420;
			if (!controller.getThingToApprove().equals("pcard")) {
				controller.hidePCardInput();
				y = 350;
			}
			Stage stage = new Stage();
			stage.setTitle("Approve Request");
			Scene scene = new Scene(root, x, y);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void viewRequestPopup(MouseEvent event, Request request) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("PCardRequestDetailsPopupScene.fxml"));
			Parent root = loader.load();

			pCardRequestDetailsPopupController = loader.getController();
			pCardRequestDetailsPopupController.setEmployee(HelperUtils.getEmployeeDetails(request.getEmployeeId()));
			pCardRequestDetailsPopupController.setRequest(request);
			pCardRequestDetailsPopupController.setRequestDetails();
			if (!request.getApprovalStatus().equals("Pending")) {
				pCardRequestDetailsPopupController.hideOptions();
			}

			// set up stage and display as popup
			Stage stage = new Stage();
			stage.setTitle("P Card Request Details");
			Scene scene = new Scene(root, 375, 400);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void viewSubmittedRequestPopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("ViewSubmittedRequestsPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Viewing Submitted P Card Requests");
			Scene scene = new Scene(root, 600, 400);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("Failed to load the FXML file: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void viewSystemOptionsPopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("SystemOptionsPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("System Options");
			Scene scene = new Scene(root, 400, 400);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("Failed to load the FXML file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void changeToCreateEmployee(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("CreateEmployeePopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Create Employee"); // sets title of stage
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	public static void changeToChangePassword(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("ChangePasswordPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Change Password"); // sets title of stage
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void changeToSystemOptionsPopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("SystemOptionsPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setTitle("Change Password"); // sets title of stage
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
	}

	public static void sortMessagePopup(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("SortMessagesPopupScene.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Sort Messages");
			Scene scene = new Scene(root, 400, 370);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("Failed to load the FXML file: " + e.getMessage());
			e.printStackTrace();
		}
	
	}

	public static void sortRequestsPopup(ActionEvent event, SortRequestsController baseController) {
		try {
			FXMLLoader loader = new FXMLLoader(
					SceneChanger.class.getClassLoader().getResource("SortRequestsPopupScene.fxml"));
			Parent root = loader.load();
			
			SortRequestsPopupController controller = loader.getController();
			controller.setSourceController(baseController);
			controller.fillCbOrderBy();
			controller.setLbFrom();

			Stage stage = new Stage();
			stage.setTitle("Sort Requests");
			Scene scene = new Scene(root, 400, 410);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("Failed to load the FXML file: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
