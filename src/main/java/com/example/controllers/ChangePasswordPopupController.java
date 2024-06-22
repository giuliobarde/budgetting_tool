package com.example.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.cus1166.Main;
import com.example.utils.Connect;
import com.example.utils.Encrypt;
import com.example.utils.HelperUtils;
import com.example.utils.SceneChanger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChangePasswordPopupController implements Initializable, PopupController {

	@FXML
	private Button btCancel;

	@FXML
	private Button btSubmit;

	@FXML
	private TextField tfConfirmPW;

	@FXML
	private TextField tfCurrentPW;

	@FXML
	private TextField tfNewPW;
	private String error = "Error changing password";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (tfNewPW.getText().trim().isEmpty() || tfCurrentPW.getText().trim().isEmpty() || tfConfirmPW.getText().trim().isEmpty()) {
					HelperUtils.showAlert(error, "Error. Please fill all fields.");
					return;
				}
				if (!checkPassword(tfCurrentPW.getText())) {
					HelperUtils.showAlert(error, "Error. Current password does not match");
					return;
				}
				if(tfNewPW.getText().length() < 8) {
					HelperUtils.showAlert(error, "Error: New password needs to be at least 8 characters long");
					return;
				}
				if (!tfNewPW.getText().equals(tfConfirmPW.getText())) {
					HelperUtils.showAlert(error, "Error: New password and confirm password does not match");
					return;
				}
				
				
				
				HelperUtils.changePassword(tfNewPW.getText(), Main.getSessionUser().getXNumber());
				SceneChanger.changeToSystemOptionsPopup(event);
			}
		});

		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToSystemOptionsPopup(event);
			}
		});
		
	}

	private boolean checkPassword(String pw) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String query = "Select * from employee where employeeId = ?";
			st = Connect.con.prepareStatement(query);
			st.setString(1, Main.getSessionUser().getXNumber());

			rs = st.executeQuery();
			while (rs.next()) {
				String passwordCheck = rs.getString("password");
				
				return Encrypt.checkPass(pw, passwordCheck);
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
		return false;
	}

}
