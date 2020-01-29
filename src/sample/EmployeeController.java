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

public class EmployeeController implements Initializable {

    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private Label msg;
    @FXML
    private TextField fName;
    @FXML
    private TableView<Employee> tableEmployee;
    @FXML
    private TableColumn<Employee,String> col_id;
    @FXML
    private TableColumn<Employee,String> col_name;
    @FXML
    private TableColumn<Employee,String> col_role;
    @FXML
    private ComboBox<String> cbRole;

    private boolean load_sel = false;

    private Employee selected;

    ObservableList<Employee> oblist = FXCollections.observableArrayList();

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
    }


    public void load_table(){
        oblist.clear();

        try {
//            System.out.println("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
            ResultSet rs = MainController.stm.executeQuery("select * from Employee where StoreID= " + lStoreID.getText() + " ;");
            while (rs.next()){
                oblist.add(new Employee(rs.getString("EmployeeID"), rs.getString("EmployeeName")
                    ,rs.getString("RoleID")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableEmployee.setItems(oblist);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.getItems().add("Manager");
        cbRole.getItems().add("Waiter");
        cbRole.getItems().add("Cashier");
        cbRole.setValue("Waiter");
    }

    public void add(){
        if (load_sel){
            try {
                if (fName.getText().equals("")){
                    msg.setText("Name cannot be empty");
                } else {
                    String sql = String.format("UPDATE Employee Set EmployeeName = '%s' , RoleID = '%s' where EmployeeID = '%s'", fName.getText(), cbRole.getValue(), selected.getId());
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
                    String sql = String.format("insert into Employee(EmployeeName, RoleID, StoreID) VALUES('%s', '%s' ,'%s')", fName.getText(), cbRole.getValue(), lStoreID.getText());
                    MainController.stm.executeUpdate(sql);
                    fName.clear();
                    load_table();
                    msg.setText("Added Successfully");
                }
            } catch (SQLException e) {
                msg.setText(e.getMessage());
            }
        }
    }

    public void delete(){

        try {
            selected = tableEmployee.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From Employee where EmployeeID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select one of the row");
            }
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void load_selected(ActionEvent event){

        selected = tableEmployee.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fName.setText(selected.getName());
            cbRole.setValue(selected.getRole());
            load_sel = true;
        } else {
            msg.setText("please select one of the row");
        }
    }
}
