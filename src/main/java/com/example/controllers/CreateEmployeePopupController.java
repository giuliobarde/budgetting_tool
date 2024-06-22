package com.example.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.objects.Department;
import com.example.utils.Connect;
import com.example.utils.Encrypt;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CreateEmployeePopupController implements Initializable, PopupController{
	

    @FXML
    private Button btCancel;
    @FXML
    private Button btSubmit;
    @FXML
    private ComboBox<Department> cbDepartment;
    @FXML
    private ComboBox<String> cbRole;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfXNumber;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		comboBoxDepartmentSelect(cbDepartment);

        // set character limits for text fields
        final int max_chars_employeeId = 9;
        final int max_chars_password = 60;
        final int max_chars_fName = 20;
        final int max_chars_lName = 20;

        tfXNumber.setTextFormatter(new TextFormatter<String>(
                change -> change.getControlNewText().length() <= max_chars_employeeId ? change : null));

        tfPassword.setTextFormatter(new TextFormatter<String>(
                change -> change.getControlNewText().length() <= max_chars_password ? change : null));

        tfFirstName.setTextFormatter(new TextFormatter<String>(
                change -> change.getControlNewText().length() <= max_chars_fName ? change : null));

        tfLastName.setTextFormatter(new TextFormatter<String>(
                change -> change.getControlNewText().length() <= max_chars_lName ? change : null));

        ObservableList<String> roleType = FXCollections.observableArrayList("Employee", "PCard Admin", "Budget Admin", "System Admin");
        cbRole.setItems(roleType);

        btSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // checks if anything was not submitted in the form
                if (!tfFirstName.getText().trim().isEmpty() && !tfLastName.getText().trim().isEmpty()
                        && !cbDepartment.getSelectionModel().isEmpty() && tfXNumber.getText().length() == 9
                        && !tfPassword.getText().trim().isEmpty()) {
                    HelperUtils.createUser(tfXNumber.getText(), Encrypt.encryptPass(tfPassword.getText()), tfFirstName.getText(),
                            tfLastName.getText(), Integer.parseInt(cbDepartment.getValue().toString()));

                    HelperUtils.createRole(tfXNumber.getText(), Integer.parseInt(cbDepartment.getValue().toString()), cbRole.getValue());

                    System.out.println("User " + tfXNumber.getText() + " successfully added.");

                    Stage stage = (Stage) btSubmit.getScene().getWindow();
                    stage.close();

                } else {
                    HelperUtils.showAlert("Missing elements", "Request failed due to missing elements.");
                }
            }
        });
		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SceneChanger.changeToSystemOptionsPopup(event);
			}
		});
	}

    void comboBoxDepartmentSelect(ComboBox<Department> cdDepartment) {
        try {
            String query = "SELECT * "
                    + "FROM department ";

            PreparedStatement statement = Connect.con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<Department> departments = cdDepartment.getItems();

            while (resultSet.next()) {
                departments.add(new Department(Integer.toString(resultSet.getInt("departmentId")), Integer.toString(resultSet.getInt("universityId")),
                        resultSet.getString("dname")));
            }

            cdDepartment.setItems(departments);

            cdDepartment.setConverter(new StringConverter<Department>() {
                @Override
                public String toString(Department department) {
                    return department.getDepartmentId() + ", " + department.getName();
                }

                @Override
                public Department fromString(String s) {
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
