package com.hotel.hotelmanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotController implements Initializable {
    @FXML
    private TextField answer;

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private TextField question;

    @FXML
    private Button save;

    @FXML
    private Button search;

    @FXML
    private TextField username;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    @FXML
    public void handleLoginButton(javafx.event.ActionEvent actionEvent) throws IOException {
        login.getScene().getWindow().hide();
        Stage signup = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        signup.setScene(scene);
        signup.show();
    }

    @FXML
    public void handleSaveAction(javafx.event.ActionEvent actionEvent) {
        int check = 0;
        String username_text = username.getText();
        String password_text = password.getText();
        String question_text = question.getText();
        String answer_text = answer.getText();
        if (username_text.equals("") || password_text.equals("") || question_text.equals("") || answer_text.equals("")) {
            OptionPane("Every Field is required", "Error Message");
        } else {
            String query = "SELECT * FROM users WHERE username=? AND securityQuestion=? AND answer=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, username_text);
                pst.setString(2, question_text);
                pst.setString(3, answer_text);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    check = 1;
                    String update = "UPDATE users set password=? WHERE username=?";
                    pst = connection.prepareStatement(update);
                    pst.setString(2, username_text);
                    pst.setString(1, password_text);
                    OptionPane("Password Set Successfully", "Message");
                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (check == 0) {
            OptionPane("Wrong Answer", "Error Message");
        }
    }

    @FXML
    public void handleSearchAction(javafx.event.ActionEvent actionEvent) {
        int check = 0;
        String query = "SELECT * FROM users WHERE username=?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, username.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                check = 1;
                question.setEditable(false);
                question.setText(rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == 0) {
            OptionPane("Incorrect Username", "Error Message");
        }
    }

    private void OptionPane(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
