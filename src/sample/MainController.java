package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Store> tableStore;
    @FXML
    private TableColumn<Store, String> col_id;
    @FXML
    private TableColumn<Store, String> col_name;
    @FXML
    private TableColumn<Store, String> col_address;
    @FXML
    private TableColumn<Store, String> col_telephone;
    @FXML
    private Label msg;

    private ConnectionClass db = new ConnectionClass();
    public static Statement stm;
    ObservableList<Store> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            stm = db.getConnection().createStatement();
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        load_table();
    }

    public void load_table(){

        oblist.clear();

        try {
            ResultSet rs = stm.executeQuery("select * from Store");
            while (rs.next()){
                oblist.add(new Store(rs.getString("StoreID"), rs.getString("name"),rs.getString("address"),
                    rs.getString("telephone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        tableStore.setItems(oblist);
    }

    public void proceed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();
//        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));

        Store selected = tableStore.getSelectionModel().getSelectedItem();

        if (selected != null) {
            MenuController controller = loader.getController();
            controller.pass(selected.getId(), selected.getName());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } else {
            msg.setText("please select a store");
        }

    }

    public void newStore(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Store.fxml"));
        Parent root = loader.load();
//        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("New Store");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
        load_table();

//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }

    public void delete() {
        try {
            Store selected = tableStore.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                stm.executeUpdate("Delete From Store where StoreID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select");
            }
        } catch (Exception e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

}
