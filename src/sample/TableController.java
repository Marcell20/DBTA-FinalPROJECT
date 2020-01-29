package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableController {

    @FXML
    private TextField fName;
    @FXML
    private TableView<Table> tableTable;
    @FXML
    private TableColumn<Table, String> col_id;
    @FXML
    private TableColumn<Table, String> col_name;
    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private Label msg;

    private boolean load_sel = false;

    private Table selected;

    ObservableList<Table> oblist = FXCollections.observableArrayList();

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
    }

    public void load_table(){
        oblist.clear();

        try {
//            System.out.println("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
            ResultSet rs = MainController.stm.executeQuery("select * from tab where StoreID= " + lStoreID.getText() + " ;");
            while (rs.next()){
                oblist.add(new Table(rs.getString("tableID"), rs.getString("tableName")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableTable.setItems(oblist);
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void load_selected(ActionEvent event){

        selected = tableTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fName.setText(selected.getName());
            load_sel = true;
        } else {
            msg.setText("please select one of the row");
        }
    }

    public void add(){
        if (load_sel){
            try {
                if (fName.getText().equals("")){
                    msg.setText("Name cannot be empty");
                } else {
                    String sql = String.format("UPDATE tab Set tableName = '%s' where tableID = '%s'", fName.getText(), selected.getId());
                    MainController.stm.executeUpdate(sql);
                    fName.clear();
                    load_table();
                    msg.setText("Edited Successfully");
                    load_sel = false;
                }
            } catch (SQLException e){
                msg.setText(e.getMessage());
            }
        } else {
            try {
                if (fName.getText().equals("")) {
                    msg.setText("Name cannot be empty");
                } else {
                    String sql = String.format("insert into tab(tableName, StoreID) VALUES('%s','%s')", fName.getText(), lStoreID.getText());
                    MainController.stm.executeUpdate(sql);
                    fName.clear();
                    msg.setText("Added Successfully");
                    load_table();
                }
            } catch (SQLException e) {
                msg.setText(e.getMessage());
            }
        }
    }

    public void delete(){

        try {
            selected = tableTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From tab where tableID=" + selectedId + ";");
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
