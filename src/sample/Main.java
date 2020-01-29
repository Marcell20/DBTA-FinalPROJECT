package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest( e -> {
            try {
                MainController.stm.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
