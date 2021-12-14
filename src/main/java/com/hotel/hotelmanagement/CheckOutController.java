package com.hotel.hotelmanagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {

    @FXML
    private TableColumn<Reservation, String> checkIn;

    @FXML
    private TableColumn<Reservation, String> checkOut;

    @FXML
    private TableColumn<Reservation, String> customerName;

    @FXML
    private TableColumn<Reservation, String> roomNumber;

    @FXML
    private TableView<Reservation> roomTable;

    @FXML
    private TextField search;

    @FXML
    private Button today;

    @FXML
    private TableColumn<Reservation, String> totalDays;

    @FXML
    private TableColumn<Reservation, String> totalPrice;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private Button unspecified;

    @FXML
    private ComboBox<String> sort;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Reservation> reservations  = FXCollections.observableArrayList();

    public static final List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        sort.getItems().removeAll(sort.getItems());
        sort.getItems().addAll("Today", "Checked In", "Checked Out");
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        checkIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalDays.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            initReservationList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomTable.setItems(reservations);
    }

    public void initReservationList() throws IOException {
        reservationList.clear();
        reservations.clear();
        String query = "SELECT res.status, res.reservationID, res.roomNumber, c.customerName, res.checkInDate, res.checkOutDate, DATEDIFF(res.checkOutDate, res.checkInDate) AS totalDays, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice FROM customers c\n" +
                "INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber\n" +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber\n";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int res_id = Integer.parseInt(rs.getString("reservationID"));
                int room_no = Integer.parseInt(rs.getString("roomNumber"));
                String cus_name = rs.getString("customerName");
                String check_in = rs.getString("checkInDate");
                String check_out = rs.getString("checkOutDate");
                int total_price = Integer.parseInt(rs.getString("totalPrice"));
                int total_days = Integer.parseInt(rs.getString("totalDays"));
                String res_status = rs.getString("status");
                reservationList.add(new Reservation(res_id, room_no, cus_name, check_in, check_out, total_days, total_price, res_status));
                reservations.add(new Reservation(res_id, room_no, cus_name, check_in, check_out, total_days, total_price, res_status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchByRoomNumber(ObservableList<Reservation> res, String s) {
        res.clear();
        for (int i = 0; i < reservationList.size(); i++) {
            if (Integer.toString(reservationList.get(i).getRoomNumber()).indexOf(s) == 0) {
                res.add(reservationList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            searchByRoomNumber(reservations, s);
        }
    }

    public void handleCheckoutButton(javafx.event.ActionEvent actionEvent) {

    }

    public void updateTable(Reservation x) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).equals(x)) {
                reservations.get(i).setStatus("Checked Out");
            }
        }
        roomTable.setItems(reservations);
    }

    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            if (roomTable.getSelectionModel().getSelectedItem() != null) {
                Reservation selectedReservation = roomTable.getSelectionModel().getSelectedItem();
                BillInfoController.setSelectedReservationID(selectedReservation.getResID());
                BillInfoController.setSelectedReservation(selectedReservation);
                Stage add = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("billinfo.fxml"));
                Scene scene = new Scene(root);
                add.setScene(scene);
                add.show();
            }
        }
    }

    public void handleComboboxSelection(javafx.event.ActionEvent actionEvent) {
        if (sort.getSelectionModel().getSelectedItem().equals("Today")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getCheckOutDate().equals(java.time.LocalDate.now().toString()) &&
                reservationList.get(i).getStatus().equals("Checked In")) {
                    reservations.add(reservationList.get(i));
                }
            }
        } else if (sort.getSelectionModel().getSelectedItem().equals("Checked In")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getStatus().equals("Checked In")) {
                    reservations.add(reservationList.get(i));
                }
            }
        } else if (sort.getSelectionModel().getSelectedItem().equals("Checked Out")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getStatus().equals("Checked Out")) {
                    reservations.add(reservationList.get(i));
                }
            }
        }
    }
}
