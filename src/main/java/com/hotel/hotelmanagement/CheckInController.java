package com.hotel.hotelmanagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {
    @FXML
    private Label amount;

    @FXML
    private Label days;

    @FXML
    private Label price;

    @FXML
    private TextField cEmail;

    @FXML
    private TextField cGender;

    @FXML
    private TextField cName;

    @FXML
    private TextField cNationality;

    @FXML
    private TextField cNumber;

    @FXML
    private TextField cPhone;

    @FXML
    private Button submit;

    @FXML
    private DatePicker inDate;

    @FXML
    private DatePicker outDate;

    @FXML
    private ComboBox<String> rNo;

    @FXML
    private ComboBox<String> rType;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        insertRoomType();
    }

    private void insertRoomType() {
        rType.getItems().removeAll(rType.getItems());
        String query = "SELECT DISTINCT roomType FROM rooms";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String room_type = rs.getString("roomType");
                rType.getItems().add(room_type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRoomNo() {
        rNo.getItems().removeAll(rNo.getItems());
        String type = rType.getSelectionModel().getSelectedItem();
        String query = "SELECT roomNumber FROM rooms WHERE roomType=? AND status='Not Booked'";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, type);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String room_no = rs.getString("roomNumber");
                rNo.getItems().add(room_no);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleSelectRoomType(javafx.event.ActionEvent actionEvent) {
        if (!rType.getSelectionModel().getSelectedItem().equals("")) {
            insertRoomNo();
        }
    }

    public void handleSelectRoomNumber(javafx.event.ActionEvent actionEvent) {
        String priceVal = "Price: ";
        String no = rNo.getSelectionModel().getSelectedItem();
        String priceQuery = "SELECT price FROM rooms WHERE roomNumber=?";
        try {
            pst = connection.prepareStatement(priceQuery);
            pst.setString(1, no);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                priceVal = priceVal + rs.getString("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        price.setText(priceVal);
    }

    public void handleCheckInPick(javafx.event.ActionEvent actionEvent) {
        String date = inDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void handleCheckOutPick(javafx.event.ActionEvent actionEvent) {
        int x = outDate.getValue().compareTo(inDate.getValue());
        days.setText("Total days: " + x);
        int p = Integer.parseInt(price.getText().replace("Price: ", ""));
        amount.setText("Total Amount: " + (p * x));
    }

    public void handleSubmitAction(javafx.event.ActionEvent actionEvent) {
        String name = cName.getText();
        String email = cEmail.getText();
        String gender = cGender.getText();
        String nationality = cNationality.getText();
        String number = cNumber.getText();
        String phone = cPhone.getText();
        String roomNo = rNo.getSelectionModel().getSelectedItem();
        String checkIn = inDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String checkOut = outDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (name.equals("") || email.equals("") || gender.equals("") || nationality.equals("")
                || number.equals("") || phone.equals("") || roomNo.equals("") || checkIn.equals("") || checkOut.equals("")) {
            OptionPane("Every Field is required", "Error Message");
        } else {
            String insertCustomer = "INSERT INTO customers(customerIDNumber, customerName, customerNationality, customerGender, customerPhoneNo, customerEmail)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            String insertReservation = "INSERT INTO reservations(customerIDNumber, roomNumber, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";
            String updateRoom = "UPDATE rooms SET status=\"Booked\" WHERE roomNumber=?";
            try {
                pst = connection.prepareStatement(insertCustomer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(1, number);
                pst.setString(2, name);
                pst.setString(3, nationality);
                pst.setString(4, gender);
                pst.setString(5, phone);
                pst.setString(6, email);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst = connection.prepareStatement(insertReservation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(1, number);
                pst.setString(2, roomNo);
                pst.setString(3, checkIn);
                pst.setString(4, checkOut);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst = connection.prepareStatement(updateRoom);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(1, roomNo);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            OptionPane("Check In Successful", "Message");
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
