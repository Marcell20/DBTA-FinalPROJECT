package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailEditController implements Initializable {

    @FXML
    private Label lID;
    @FXML
    private Label lTransactionID;
    @FXML
    private Label lItemID;
    @FXML
    private Label lItemName;
    @FXML
    private Label msg;
    @FXML
    private TextField fItemQty;
    @FXML
    private ComboBox<String> cNote1;
    @FXML
    private ComboBox<String> cNote2;

    public void pass(String ID, String TransactionID, String ItemID, String ItemName, String ItemQty, String Note1, String Note2){
        lID.setText(ID);
        lTransactionID.setText(TransactionID);
        lItemID.setText(ItemID);
        lItemName.setText(ItemName);
        fItemQty.setText(ItemQty);
        cNote1.setValue(Note1);
        cNote2.setValue(Note2);
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
    }

    public void save(){
        String Note2;
        try{
            Integer qty = Integer.parseInt(fItemQty.getText());
        } catch (Exception e){
            msg.setText("INPUT NUMBER on QTY!");
        }
        if (cNote2.getValue().equals("")){
            Note2 = "NULL";
        } else {
            Note2 = cNote2.getValue();
        }
        try{
            String sql = String.format("update tDetail set itemQty = '%s' , note = '%s', note2 = '%s' where tDetailID = %s;",fItemQty.getText(),cNote1.getValue(),Note2,lID.getText());
            MainController.stm.executeUpdate(sql);
            msg.setText("SUCCESSFUL");
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
