package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MenuController {

    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;

    public void pass(String StoreID, String StoreName){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
    }

    public void gotoEmployee(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Employee.fxml"));
        Parent root = loader.load();

        EmployeeController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Employee");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }

    public void gotoItem(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Item.fxml"));
        Parent root = loader.load();

        ItemController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Item");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }

    public void gotoTable(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Table.fxml"));
        Parent root = loader.load();

        TableController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Table");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }

    public void gotoHistory(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("History.fxml"));
        Parent root = loader.load();

        HistoryController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("History");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }

    public void gotoOngoing(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Ongoing.fxml"));
        Parent root = loader.load();

        OngoingController controller = loader.getController();
        controller.pass(lStoreID.getText(),lStoreName.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Ongoing");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
    }
}
