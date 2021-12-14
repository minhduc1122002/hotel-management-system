package com.hotel.hotelmanagement;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.hotel.hotelmanagement.RoomController.roomList;
import static com.hotel.hotelmanagement.RoomController.rooms;

public class AddRoomController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TextField number;

    @FXML
    private TextField price;

    @FXML
    private TextField type;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        String query = "INSERT INTO rooms (roomNumber, roomType, price) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, number.getText());
            pst.setString(2, type.getText());
            pst.setString(3, price.getText());
            roomList.add(new Room(Integer.parseInt(number.getText()), Integer.parseInt(price.getText()), type.getText(), "Not Booked"));
            rooms.add(new Room(Integer.parseInt(number.getText()), Integer.parseInt(price.getText()), type.getText(), "Not Booked"));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
