package com.hotel.hotelmanagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.hotel.hotelmanagement.RoomController.roomList;
import static com.hotel.hotelmanagement.RoomController.rooms;

public class BillInfoController implements Initializable {

    public static int selectedResID;

    public static Reservation selectedReservation;

    @FXML
    private TextField Amount;

    @FXML
    private Button print;

    @FXML
    private TextField customerIDNumber;

    @FXML
    private TextField customerName;

    @FXML
    private TextField roomNumber;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static void setSelectedReservationID(int selectedReservationID) {
        selectedResID = selectedReservationID;
    }

    public static void setSelectedReservation(Reservation reservation) {
        selectedReservation = reservation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        if (selectedResID != 0) {
            String query = "SELECT res.reservationID, res.roomNumber, c.customerIDNumber, c.customerName, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice FROM customers c\n" +
                    "INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber\n" +
                    "INNER JOIN rooms r ON r.roomNumber = res.roomNumber\n" +
                    "WHERE res.reservationID=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, Integer.toString(selectedResID));
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    roomNumber.setText(rs.getString("roomNumber"));
                    customerIDNumber.setText(rs.getString("customerIDNumber"));
                    customerName.setText(rs.getString("customerName"));
                    Amount.setText(rs.getString("totalPrice"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            roomNumber.setEditable(false);
            customerIDNumber.setEditable(false);
            customerName.setEditable(false);
            Amount.setEditable(false);
        }
    }

    public void handlePrintAction(javafx.event.ActionEvent actionEvent) throws IOException {
        String id = "";
        String insertBills = "INSERT INTO bills(reservationID, billDate, billAmount) VALUES (?, ?, ?)";
        String updateRoom = "UPDATE rooms SET status=\"Not Booked\" WHERE roomNumber=?";
        String updateReservation = "UPDATE reservations SET status=\"Checked Out\" WHERE reservationID=?";
        String selectBill = "SELECT billID FROM bills WHERE reservationID=?";
        if (!selectedReservation.getStatus().equals("Checked Out")) {
            try {
                pst = connection.prepareStatement(insertBills);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(1, String.valueOf(selectedReservation.getResID()));
                pst.setString(2, selectedReservation.getCheckOutDate());
                pst.setString(3, String.valueOf(selectedReservation.getTotalPrice()));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst = connection.prepareStatement(updateRoom);
                pst.setString(1, String.valueOf(selectedReservation.getRoomNumber()));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst = connection.prepareStatement(updateReservation);
                pst.setString(1, String.valueOf(selectedReservation.getResID()));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            pst = connection.prepareStatement(selectBill);
            pst.setString(1, String.valueOf(selectedReservation.getResID()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getString("billID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        createBill(id);
    }

    private void createBill(String id) throws IOException {
        String billID = "";
        String customerName = "";
        String customerIDNumber = "";
        String customerPhoneNo = "";
        String roomNumber = "";
        String roomType = "";
        String priceRoom = "";
        String checkIn = "";
        String checkOut = "";
        String totalDay = "";
        String totalPrice = "";
        String path = "C:\\Users\\Mr.Cuong\\IdeaProjects\\HotelManagement\\res\\";
        String billQuery = "SELECT b.billID, c.customerIDNumber, c.customerName, c.customerPhoneNo, r.roomNumber, r.roomType, r.price, res.checkInDate, res.checkOutDate, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice, DATEDIFF(res.checkOutDate, res.checkInDate) AS totalDay FROM bills b\n" +
                "INNER JOIN reservations res ON b.reservationID = res.reservationID\n" +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber\n" +
                "INNER JOIN customers c ON c.customerIDNumber = res.customerIDNumber\n" +
                "WHERE b.billID=?";
        try {
            pst = connection.prepareStatement(billQuery);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                billID = rs.getString("billID");
                customerName = rs.getString("customerName");
                customerIDNumber = rs.getString("customerIDNumber");
                customerPhoneNo = rs.getString("customerPhoneNo");
                roomNumber = rs.getString("roomNumber");
                roomType = rs.getString("roomType");
                priceRoom = rs.getString("price");
                checkIn = rs.getString("checkInDate");
                checkOut = rs.getString("checkOutDate");
                totalDay = rs.getString("totalDay");
                totalPrice = rs.getString("totalPrice");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "bill" + id + ".pdf"));
            doc.open();
            Paragraph paragraph1 = new Paragraph("Bill ID: " + billID + "\nCustomer Details:\nName: " + customerName + "\nID Number: " + customerIDNumber +
                    "\nMobile Number: " + customerPhoneNo + "\n");
            doc.add(paragraph1);
            Paragraph paragraph2 = new Paragraph("\nRoom Details:\nRoom Number: " + roomNumber + "\nRoom Type: " + roomType +
                    "\nPrice Per Day " + priceRoom + "\n" + "\n");
            doc.add(paragraph2);
            PdfPTable table = new PdfPTable(4);
            table.addCell("Check In Date: " + checkIn);
            table.addCell("Check Out Date: " + checkOut);
            table.addCell("Number of Days Stay: " + totalDay);
            table.addCell("Total Amount Paid: " + totalPrice);
            doc.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();

        File file = new File(path + "bill" + id + ".pdf");
        if (file.toString().endsWith(".pdf"))
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
        else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }
}
