package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class StoreController {

    @FXML
    private TextField tName;
    @FXML
    private TextField tAddress;
    @FXML
    private TextField tTelephone;
    @FXML
    private Label msg;

    public void add() {
        try {
            String sql = String.format("insert into Store(name, address, telephone) VALUES('%s', '%s' ,'%s')", tName.getText(), tAddress.getText(), tTelephone.getText());
            MainController.stm.executeUpdate(sql);
            tName.clear();
            tAddress.clear();
            tTelephone.clear();
            msg.setText("Successful");
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}
