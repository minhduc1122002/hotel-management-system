package com.hotel.hotelmanagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public static int selectedRoomNumber;

    @FXML
    private TextField email;

    @FXML
    private TextField gender;

    @FXML
    private TextField inDate;

    @FXML
    private TextField name;

    @FXML
    private TextField nationality;

    @FXML
    private TextField outDate;

    @FXML
    private TextField phone;

    @FXML
    private TextField price;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static void setSelectedRoomNumber(int selectedRoomNumber) {
        CustomerController.selectedRoomNumber = selectedRoomNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        if (selectedRoomNumber != 0) {
            String query = "SELECT c.*, res.checkInDate, res.checkOutDate, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS Total FROM customers c \n" +
                    "INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber\n" +
                    "INNER JOIN rooms r ON r.roomNumber = res.roomNumber\n" +
                    "WHERE r.roomNumber=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, Integer.toString(selectedRoomNumber));
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    price.setText(rs.getString("Total"));
                    name.setText(rs.getString("customerName"));
                    email.setText(rs.getString("customerEmail"));
                    phone.setText(rs.getString("customerPhoneNo"));
                    gender.setText(rs.getString("customerGender"));
                    nationality.setText(rs.getString("customerNationality"));
                    inDate.setText(rs.getString("checkInDate"));
                    outDate.setText(rs.getString("checkOutDate"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            price.setEditable(false);
            name.setEditable(false);
            email.setEditable(false);
            phone.setEditable(false);
            gender.setEditable(false);
            nationality.setEditable(false);
            inDate.setEditable(false);
            outDate.setEditable(false);
        }
    }
}
