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

public class DashBoardController implements Initializable {
    @FXML
    private Label avaRoom;

    @FXML
    private Label bookedRoom;

    @FXML
    private Label earning;

    @FXML
    private Label pending;

    @FXML
    private Label totalRoom;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        String query = "SELECT COUNT(roomNumber) AS totalRooms, a.totalNotBooked, booked.totalBooked FROM rooms, " +
                "(SELECT COUNT(roomNumber) AS totalBooked FROM rooms WHERE status = 'Booked') AS booked, " +
                "(SELECT COUNT(roomNumber) AS totalNotBooked FROM rooms WHERE status = 'Not Booked') AS a";
        String query2 = "SELECT SUM(b.billAmount) AS totalEarnings, (SELECT SUM((r.price * DATEDIFF(res.checkOutDate, res.checkInDate))) AS Pending FROM reservations res \n" +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber \n" +
                "WHERE res.status = 'Checked In') AS totalPendings FROM bills b \n" +
                "INNER JOIN reservations res ON res.reservationID = b.reservationID;";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalRoom.setText(rs.getString("totalRooms"));
                bookedRoom.setText(rs.getString("totalBooked"));
                avaRoom.setText(rs.getString("totalNotBooked"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            pst = connection.prepareStatement(query2);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                earning.setText(rs.getString("totalEarnings"));
                pending.setText(rs.getString("totalPendings"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
