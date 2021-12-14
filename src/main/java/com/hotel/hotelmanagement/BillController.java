package com.hotel.hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    @FXML
    private TableColumn<Bill, String> Amount;

    @FXML
    private TableColumn<Bill, String> Date;

    @FXML
    private TableColumn<Bill, String> billID;

    @FXML
    private TableColumn<Bill, String> cusIDNumber;

    @FXML
    private TableColumn<Bill, String> customerName;

    @FXML
    private TableColumn<Bill, String> roomNumber;

    @FXML
    private TableView<Bill> billTable;

    @FXML
    private TextField search;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Bill> bills = FXCollections.observableArrayList();

    public static final List<Bill> billList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        cusIDNumber.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        billID.setCellValueFactory(new PropertyValueFactory<>("billID"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        try {
            initBillList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        billTable.setItems(bills);
    }

    public void initBillList() throws IOException {
        billList.clear();
        bills.clear();
        String query = "SELECT b.*, res.roomNumber, res.customerIDNumber, c.customerName FROM bills b\n" +
                "INNER JOIN reservations res ON b.reservationID = res.reservationID\n" +
                "INNER JOIN customers c ON res.customerIDNumber = c.customerIDNumber";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int room_number = Integer.parseInt(rs.getString("roomNumber"));
                int cus_number = Integer.parseInt(rs.getString("customerIDNumber"));
                int bill_id = Integer.parseInt(rs.getString("billID"));
                String date = rs.getString("billDate");
                String cus_name = rs.getString("customerName");
                int bill_amount = Integer.parseInt(rs.getString("billAmount"));
                billList.add(new Bill(bill_id, cus_name, cus_number, date, bill_amount, room_number));
                bills.add(new Bill(bill_id, cus_name, cus_number, date, bill_amount, room_number));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Search(ObservableList<Bill> bills, String s) {
        bills.clear();
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getDate().indexOf(s) == 0) {
                bills.add(billList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(bills, s);
        }
    }

    public void clickBill(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            if (billTable.getSelectionModel().getSelectedItem() != null) {
                String path = "C:\\Users\\Mr.Cuong\\IdeaProjects\\HotelManagement\\res\\";
                Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
                File file = new File(path + "bill" + selectedBill.getBillID() + ".pdf");
                if (file.toString().endsWith(".pdf"))
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
                else {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                }
            }
        }
    }
}
