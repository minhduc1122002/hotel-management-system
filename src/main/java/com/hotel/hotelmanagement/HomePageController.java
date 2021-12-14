package com.hotel.hotelmanagement;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Label adminName;

    @FXML
    private Button bill;

    @FXML
    private Button dash;

    @FXML
    private Button checkin;

    @FXML
    private Button checkout;

    @FXML
    private AnchorPane holdPane;

    @FXML
    private Button room;

    private AnchorPane Pane;

    public static String name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText(name);
    }

    private void setNode(Node node) {
        holdPane.getChildren().clear();
        holdPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void createRoom(javafx.event.ActionEvent actionEvent) {
        try {
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            dash.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            Pane = FXMLLoader.load(getClass().getResource("room.fxml"));
            setNode(Pane);
            room.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCheckIn(javafx.event.ActionEvent actionEvent) {
        try {
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            Pane = FXMLLoader.load(getClass().getResource("checkin.fxml"));
            dash.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            setNode(Pane);
            checkin.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCheckOut(javafx.event.ActionEvent actionEvent) {
        try {
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            dash.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            Pane = FXMLLoader.load(getClass().getResource("checkout.fxml"));
            setNode(Pane);
            checkout.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCustomerBill(javafx.event.ActionEvent actionEvent) {
        try {
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            dash.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            Pane = FXMLLoader.load(getClass().getResource("bill.fxml"));
            setNode(Pane);
            bill.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDash(javafx.event.ActionEvent actionEvent) {
        try {
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            Pane = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            setNode(Pane);
            dash.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogout(MouseEvent event) throws IOException {
        bill.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }
}
