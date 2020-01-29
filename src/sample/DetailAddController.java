package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailAddController implements Initializable {

    @FXML
    private TableView<Item> tableFood;
    @FXML
    private TableColumn<Item, String> col_idf;
    @FXML
    private TableColumn<Item, String> col_namef;
    @FXML
    private TableColumn<Item, String> col_pricef;
    @FXML
    private Label lTransactionID;
    @FXML
    private Label msg;
    @FXML
    private TextField fItemQty;
    @FXML
    private TableView<Item> tableBeverages;
    @FXML
    private TableColumn<Item, String> col_idb;
    @FXML
    private TableColumn<Item, String> col_nameb;
    @FXML
    private TableColumn<Item, String> col_priceb;
    @FXML
    private Label lItemID;
    @FXML
    private Label lItemName;
    @FXML
    private ComboBox<String> cNote1;
    @FXML
    private ComboBox<String> cNote2;

    private Item selected;

    ObservableList<Item> oblist = FXCollections.observableArrayList();
    ObservableList<Item> oblist1 = FXCollections.observableArrayList();

    public void pass(String TransactionID){
        lTransactionID.setText(TransactionID);
    }

    public void load_table(){

        oblist.clear();
        try {
//            System.out.println("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
            ResultSet rs = MainController.stm.executeQuery("select * from Item where itemType= 'Food';");
            while (rs.next()){
                oblist.add(new Item(rs.getString("itemID"), rs.getString("itemName")
                    ,rs.getString("itemPrice")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_idf.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_namef.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_pricef.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableFood.setItems(oblist);

        oblist1.clear();
        try {
//            System.out.println("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
            ResultSet rs = MainController.stm.executeQuery("select * from Item where itemType= 'Beverage';");
            while (rs.next()){
                oblist1.add(new Item(rs.getString("itemID"), rs.getString("itemName")
                    ,rs.getString("itemPrice")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_idb.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nameb.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_priceb.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableBeverages.setItems(oblist1);
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cNote1.getItems().add("Spaghetti");
        cNote1.getItems().add("Fettucini");
        cNote1.getItems().add("Penne");
        cNote1.getItems().add("Chips");
        cNote1.getItems().add("Corn");
        cNote1.getItems().add("Fries");
        cNote1.getItems().add("ICE");
        cNote1.getItems().add("HOT");
        cNote1.getItems().add("Less Sugar");
        cNote1.getItems().add("Less Ice");
        cNote1.getItems().add("Serve Later");
        cNote2.getItems().add("Spaghetti");
        cNote2.getItems().add("Fettucini");
        cNote2.getItems().add("Penne");
        cNote2.getItems().add("Chips");
        cNote2.getItems().add("Corn");
        cNote2.getItems().add("Fries");
        cNote2.getItems().add("ICE");
        cNote2.getItems().add("HOT");
        cNote2.getItems().add("Less Sugar");
        cNote2.getItems().add("Less Ice");
        cNote2.getItems().add("Serve Later");
        cNote2.getItems().add("");
        cNote1.setValue("Spaghetti");
        cNote2.setValue("");
    }

    public void load_selected_food(ActionEvent event){

        selected = tableFood.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lItemID.setText(selected.getId());
            lItemName.setText(selected.getName());
        } else {
            msg.setText("please select one of the row");
        }
    }

    public void load_selected_beverage(ActionEvent event){

        selected = tableBeverages.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lItemID.setText(selected.getId());
            lItemName.setText(selected.getName());
        } else {
            msg.setText("please select one of the row");
        }
    }

    public void add() {
        Integer qty;
        try{
            qty = Integer.parseInt(fItemQty.getText());
        } catch (Exception e){
            msg.setText("INSERT NUMBER ON ITEM QTY!");
        }
        try {
            if (cNote2.getValue().equals("")) {
                String sql = String.format("insert into tDetail (transactionID,itemID,itemQty,note)\n" +
                    "    values \n" +
                    "    (%s,%s,%s,'%s');", lTransactionID.getText(), lItemID.getText(), fItemQty.getText(), cNote1.getValue());
                MainController.stm.executeUpdate(sql);
            } else {
                String sql = String.format("insert into tDetail (transactionID,itemID,itemQty,note,note2)\n" +
                    "    values \n" +
                    "    (%s,%s,%s,'%s','%s');", lTransactionID.getText(), lItemID.getText(), fItemQty.getText(), cNote1.getValue(), cNote2.getValue());
                MainController.stm.executeUpdate(sql);
            }
            lItemID.setText("");
            lItemName.setText("");
            fItemQty.clear();
            cNote1.setValue("Spaghetti");
            cNote2.setValue("");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
