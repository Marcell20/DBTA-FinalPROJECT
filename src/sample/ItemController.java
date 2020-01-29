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

public class ItemController implements Initializable {

    @FXML
    private TextField fName;
    @FXML
    private TableView<Item> tableFood;
    @FXML
    private TableColumn<Item, String > col_idf;
    @FXML
    private TableColumn<Item, String > col_namef;
    @FXML
    private TableColumn<Item, String > col_pricef;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private Label msg;
    @FXML
    private TextField fPrice;
    @FXML
    private TableView<Item> tableBeverages;
    @FXML
    private TableColumn<Item, String> col_idb;
    @FXML
    private TableColumn<Item, String> col_nameb;
    @FXML
    private TableColumn<Item, String> col_priceb;

    private boolean load_sel = false;

    private Item selected;

    ObservableList<Item> oblist = FXCollections.observableArrayList();

    ObservableList<Item> oblist1 = FXCollections.observableArrayList();

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbType.getItems().add("Food");
        cbType.getItems().add("Beverage");
        cbType.setValue("Food");
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

    public void load_selected_food(ActionEvent event){

        selected = tableFood.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fName.setText(selected.getName());
            fPrice.setText(selected.getPrice());
            cbType.setValue("Food");
            load_sel = true;
        } else {
            msg.setText("please select one of the row");
        }
    }

    public void load_selected_beverage(ActionEvent event){

        selected = tableBeverages.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fName.setText(selected.getName());
            fPrice.setText(selected.getPrice());
            cbType.setValue("Beverage");
            load_sel = true;
        } else {
            msg.setText("please select one of the row");
        }
    }

    public void add(){
        if (load_sel){
            try {
                if (fName.getText().equals("") || fPrice.getText().equals("")){
                    msg.setText("Name or Price cannot be empty");
                } else {
                    String sql = String.format("UPDATE Item Set itemName = '%s' , itemType = '%s', itemPrice = '%s' where itemID = '%s'", fName.getText(), cbType.getValue(), fPrice.getText(), selected.getId());
                    MainController.stm.executeUpdate(sql);
                    fName.clear();
                    fPrice.clear();
                    load_table();
                    msg.setText("Edited Successfully");
                    load_sel = false;
                }
            } catch (SQLException e){
                msg.setText(e.getMessage());
            }
        } else {
            try {
                if (fName.getText().equals("") || fPrice.getText().equals("")) {
                    msg.setText("Name or Price cannot be empty");
                } else {
                    String sql = String.format("insert into Item(itemName, itemPrice, itemType) VALUES('%s', '%s' ,'%s')", fName.getText(), fPrice.getText(), cbType.getValue());
                    MainController.stm.executeUpdate(sql);
                    fName.clear();
                    fPrice.clear();
                    load_table();
                    msg.setText("Added Successfully");
                }
            } catch (SQLException e) {
                msg.setText(e.getMessage());
            }
        }
    }

    public void delete_food(){

        try {
            selected = tableFood.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From Item where itemID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select one of the row");
            }
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

    public void delete_beverage(){

        try {
            selected = tableBeverages.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From Item where itemID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select one of the row");
            }
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

}
