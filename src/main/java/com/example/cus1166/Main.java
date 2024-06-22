package com.example.cus1166;

import com.example.userclasses.SessionPCard;
import com.example.userclasses.SessionUser;
import com.example.utils.Connect;
import com.example.utils.Encrypt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	private Connect connection;
	private static SessionUser user;
	private static SessionPCard pcard;

	@Override
	public void start(Stage primaryStage) {
		// Connect to the database
		connection = new Connect();
		
		try {
			// sets starting scene as Login
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LoginScene.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static void main(String[] args) {
		launch(args);
	}
	
	public static SessionUser getSessionUser() {
		return getUser();
	}
	
	public static SessionPCard getSessionPCard() {
		return pcard;
	}


	public static SessionUser getUser() {
		return user;
	}

	public static void setUser(SessionUser user) {
		Main.user = user;
	}
	
	public static void setPCard(SessionPCard pcard) {
		Main.pcard = pcard;
	}
}
