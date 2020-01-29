package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionAddController {

    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private ComboBox<String> cTableID;
    @FXML
    private Label lTableName;
    @FXML
    private ComboBox<String> cCashierID;
    @FXML
    private Label lCashierName;
    @FXML
    private ComboBox<String> cWaiterID;
    @FXML
    private Label lWaiterName;
    @FXML
    private Label msg;
    @FXML
    private ComboBox<String> cGuest;

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
        load();
    }

    public void load() {

        try {
            String sql = String.format("select * from tab where tab.storeID = %s and not exists (select tableID from transaction where status = 'on-going' and transaction.storeID = %s and tab.tableID = transaction.tableID);",lStoreID.getText(),lStoreID.getText());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            String first = rs.getString("tableID");
            cTableID.setValue(first);
            cTableID.getItems().add(first);
            lTableName.setText(rs.getString("tableName"));
            while (rs.next()){
                cTableID.getItems().add(rs.getString("tableID"));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }
        try {
            String sql = String.format("select * from Employee where StoreID = %s and RoleID = 'Cashier';",lStoreID.getText());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            String first = rs.getString("EmployeeID");
            cCashierID.setValue(first);
            cCashierID.getItems().add(first);
            lCashierName.setText(rs.getString("EmployeeName"));
            while (rs.next()){
                cCashierID.getItems().add(rs.getString("EmployeeID"));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }
        try {
            String sql = String.format("select * from Employee where StoreID = %s and RoleID = 'Waiter';",lStoreID.getText());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            String first = rs.getString("EmployeeID");
            cWaiterID.setValue(first);
            cWaiterID.getItems().add(first);
            lWaiterName.setText(rs.getString("EmployeeName"));
            while (rs.next()){
                cWaiterID.getItems().add(rs.getString("EmployeeID"));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }
        cGuest.getItems().add("1");
        cGuest.getItems().add("2");
        cGuest.getItems().add("3");
        cGuest.getItems().add("4");
        cGuest.getItems().add("5");
        cGuest.getItems().add("6");
        cGuest.getItems().add("7");
        cGuest.getItems().add("8");
        cGuest.getItems().add("9");
        cGuest.getItems().add("10");
        cGuest.getItems().add("11");
        cGuest.getItems().add("12");
        cGuest.getItems().add("13");
        cGuest.getItems().add("14");
        cGuest.getItems().add("15");
        cGuest.setValue("1");
    }

    public void Create(ActionEvent event){
        try{
            String sql = String.format("insert into transaction (StoreID,tableID,cashierID,waiterID,guestNo,status) values (%s,%s,%s,%s,%s,'on-going');",lStoreID.getText(),
                cTableID.getValue(),cCashierID.getValue(),cWaiterID.getValue(),cGuest.getValue());
            MainController.stm.executeUpdate(sql);
            back(event);
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void choose_table(){
        try{
            String sql = String.format("select tableName from tab where tableID = %s;",cTableID.getValue());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            lTableName.setText(rs.getString("tableName"));
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }
    public void choose_cashier(){
        try{
            String sql = String.format("select EmployeeName from Employee where EmployeeID = %s;",cCashierID.getValue());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            lCashierName.setText(rs.getString("EmployeeName"));
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }
    public void choose_waiter(){
        try{
            String sql = String.format("select EmployeeName from Employee where EmployeeID = %s;",cWaiterID.getValue());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            lWaiterName.setText(rs.getString("EmployeeName"));
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }
}
