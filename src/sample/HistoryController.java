package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryController {

    @FXML
    private TableView<Transaction> tableTransaction;
    @FXML
    private TableColumn<Transaction, String> col_id;
    @FXML
    private TableColumn<Transaction, String> col_table;
    @FXML
    private TableColumn<Transaction, String> col_cashier;
    @FXML
    private TableColumn<Transaction, String> col_waiter;
    @FXML
    private TableColumn<Transaction, String> col_guest;
    @FXML
    private TableColumn<Transaction, String> col_datentime;
    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private Label msg;
    @FXML
    private TextField fTable;
    @FXML
    private DatePicker fDate;

    private Transaction selected;

    ObservableList<Transaction> oblist = FXCollections.observableArrayList();

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
    }

    public void load_table(){

        oblist.clear();

        String date;
        if (fDate.getValue() == null) {
            date = "%%";
        } else {
            date = "%" + fDate.getValue() + "%";
        }
        String table = "%" + fTable.getText() + "%";

        try {

            String sql = String.format("select t.transactionID, tab.tableName, e.EmployeeName as cashierName, w.EmployeeName as waiterName, t.guestNo, t.DatenTime from transaction t left join tab tab using (tableID) left join Employee e ON t.cashierID = e.EmployeeID left join Employee w ON t.waiterID = w.EmployeeID where t.StoreID = '%s' and tableName like '%s' and DatenTime like '%s' and t.status = 'done' order by DatenTime desc;"
                , lStoreID.getText(), table, date);
//            System.out.println("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
//            ResultSet rs = MainController.stm.executeQuery("select t.transactionID, tab.tableName, t.cashierID, t.waiterID, t.guestNo, t.DatenTime  from transaction t left join tab tab using(tableID) where t.StoreID = "+ lStoreID.getText() +" and t.status = 'done';");
            ResultSet rs = MainController.stm.executeQuery(sql);
            while (rs.next()){
                oblist.add(new Transaction(rs.getString("transactionID"), rs.getString("tableName"), rs.getString("cashierName"),
                    rs.getString("waiterName"), rs.getString("guestNO"), rs.getString("DatenTime")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_table.setCellValueFactory(new PropertyValueFactory<>("table"));
        col_cashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));
        col_waiter.setCellValueFactory(new PropertyValueFactory<>("waiter"));
        col_guest.setCellValueFactory(new PropertyValueFactory<>("guest"));
        col_datentime.setCellValueFactory(new PropertyValueFactory<>("datentime"));

        tableTransaction.setItems(oblist);
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void delete(){

        try {
            selected = tableTransaction.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From transaction where transactionID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select one of the row");
            }
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

    public void gotoDetail(ActionEvent event) throws IOException {

        selected = tableTransaction.getSelectionModel().getSelectedItem();
        if (selected == null){
            msg.setText("Please select one of the row");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Detail.fxml"));
        Parent root = loader.load();

        DetailController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText(),selected.getId(),selected.getTable(),selected.getCashier(),selected.getWaiter(),selected.getDatentime());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Detail");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }
}
