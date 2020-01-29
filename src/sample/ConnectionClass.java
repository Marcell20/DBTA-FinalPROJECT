package sample;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionClass {

    private Connection connection;
    private Statement statement;

    public Connection getConnection(){

        String dbName = "10_bill";
//        String userName = "MIC7424";
//        String password = "ewqkzef9";

        String userName = "MAR7544";
        String password = "5ek8hre8";

//        String userName = "KEV8156";
//        String password = "1279riu9";

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://dbta.1ez.xyz/"+ dbName , userName , password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

//    public void insertData(String name, String type, String price){
//        String sql = String.format("insert into Item(name , type, price) VALUES( '%s', '%s' ,'%s')" ,name ,type, price);
//        //System.out.println(sql);
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(sql);
//            System.out.println("Data Inserted");
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//    }
//    public ConnectionClass(){
//        String dbName = "10_bill";
//        String userName = "MAR7544";
//        String password = "5ek8hre8";
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            // Create Connection
//            connection = DriverManager.getConnection("jdbc:mysql://dbta.1ez.xyz/"+ dbName , userName , password);
//            statement = connection.createStatement();
//        }
//        catch (Exception e){
//            System.out.println("Error : " + e);
//        }
//    }
}

