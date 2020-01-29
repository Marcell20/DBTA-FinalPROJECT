package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class DetailController {

    @FXML
    private TableView<Detail> tableDetail;
    @FXML
    private TableColumn<Detail,String> col_id;
    @FXML
    private TableColumn<Detail,String> col_itemid;
    @FXML
    private TableColumn<Detail,String> col_itemname;
    @FXML
    private TableColumn<Detail,String> col_itemqty;
    @FXML
    private TableColumn<Detail,String> col_price;
    @FXML
    private TableColumn<Detail,String> col_note;
    @FXML
    private TableColumn<Detail,String> col_note2;
    @FXML
    private Label lStoreID;
    @FXML
    private Label lStoreName;
    @FXML
    private Label msg;
    @FXML
    private Label lTransactionID;
    @FXML
    private Label lTableName;
    @FXML
    private Label lCashierName;
    @FXML
    private Label lWaiterName;
    @FXML
    private Label lDatenTime;
    @FXML
    private Label lsub;
    @FXML
    private Label lser;
    @FXML
    private Label ltax;
    @FXML
    private Label lgrand;

    private Detail selected;
//    public static Integer selectId,selectItem,selectTable,selectCashier,selectWaiter;

    ObservableList<Detail> oblist = FXCollections.observableArrayList();



    public void load_table(){

        oblist.clear();

        try {

            String sql = String.format("select t.tDetailID, t.itemID, i.itemName, t.itemQty, t.itemQty*i.itemPrice as Price, t.note, t.note2 from tDetail t left join Item i using (itemID) where transactionID = '%s';",lTransactionID.getText());
            ResultSet rs = MainController.stm.executeQuery(sql);
            while (rs.next()){
                oblist.add(new Detail(rs.getString("tDetailID"), rs.getString("itemID"), rs.getString("itemName"),
                    rs.getString("itemQty"), rs.getString("Price"), rs.getString("note"), rs.getString("note2")));
            }
        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_itemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        col_itemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        col_itemqty.setCellValueFactory(new PropertyValueFactory<>("itemquantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));
        col_note2.setCellValueFactory(new PropertyValueFactory<>("note2"));

        tableDetail.setItems(oblist);

        try {

            String sql = String.format("select SUM(t.itemQty*i.itemPrice) as Price from tDetail t left join Item i using (itemID) where transactionID = %s group by transactionID ;", lTransactionID.getText());
            ResultSet rs = MainController.stm.executeQuery(sql);
            rs.next();
            lsub.setText(rs.getString("Price"));
            double service = Integer.parseInt(lsub.getText()) * 0.075;
            lser.setText(Double.toString(service));
            double tax = (Double.parseDouble(lsub.getText()) + service) * 0.1;
            ltax.setText(Double.toString(tax));
            double grand = (Double.parseDouble(lsub.getText()) + service + tax);
            lgrand.setText(Double.toString(grand));

        } catch (SQLException e) {
            msg.setText(e.getMessage());
        }
    }

    public void back(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    public void delete(){

        try {
            selected = tableDetail.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedId = selected.getId();
                MainController.stm.executeUpdate("Delete From tDetail where tDetailID=" + selectedId + ";");
                msg.setText("Successful");
            } else {
                msg.setText("please select one of the row");
            }
        } catch (SQLException e){
            msg.setText(e.getMessage());
        }
        load_table();
    }

    public void gotoDetailAdd(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailAdd.fxml"));
        Parent root = loader.load();

        DetailAddController controller = loader.getController();
        controller.pass(lTransactionID.getText());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("New Item");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
        load_table();
    }

    public void gotoDetailEdit(ActionEvent event) throws IOException {

        selected = tableDetail.getSelectionModel().getSelectedItem();

        if (selected == null){
            msg.setText("please select one of the row");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("DetailEdit.fxml"));
        Parent root = loader.load();

        DetailEditController controller = loader.getController();
        controller.pass(selected.getId(),lTransactionID.getText(),selected.getItemid(),selected.getItemname(),selected.getItemquantity(),selected.getNote(),selected.getNote2());

//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.setTitle("Item Edit");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.showAndWait();
//        AddStoreController st = new AddStoreController();
//        st.loadData();
        load_table();
    }

    public void PrintButtonClicked() throws SQLException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("InvoicePancious.pdf"));
            document.setPageSize(new Rectangle(360,2000));
            document.open();
//            document.add(new Paragraph(store));
//            document.add(new Paragraph(address));
            document.add(new Paragraph("                                "+lStoreName.getText()));
            ResultSet rst = MainController.stm.executeQuery("Select * from Store Where StoreID = " + lStoreID.getText()+";");
            rst.next();
            document.add(new Paragraph("                          " + rst.getString("address")));
            document.add(new Paragraph("                                " + rst.getString("telephone")));
            document.add(new Paragraph("========================================="));
            document.add(new Paragraph("Table : " + lTableName.getText()));
            document.add(new Paragraph("Waiter : " + lWaiterName.getText()));
            document.add(new Paragraph("Cashier :" + lCashierName.getText()));
            document.add(new Paragraph(lDatenTime.getText() + "                      " + "Transaction ID :" + lTransactionID.getText()));
            document.add(new Paragraph("========================================="));

            ResultSet rs = MainController.stm.executeQuery("select count(*) from tDetail where transactionID = "+lTransactionID.getText()+" ;");
            rs.next();
            int count = Integer.parseInt(rs.getString("count(*)"));
            for (int i = 0 ; i < count ; i++) {
                Chunk glue = new Chunk(new VerticalPositionMark());
//                document.add(new Paragraph(oblist.get(i).getItemquantity() + "                            " +    oblist.get(i).getItemname() + "                   " + oblist.get(i).getPrice()));
                String space = String.format("%-11s%-40s", oblist.get(i).getItemquantity(),oblist.get(i).getItemname());
                Paragraph p = new Paragraph(space);
//                document.add(new Paragraph(space));
                p.add(new Chunk(glue));
                p.add(oblist.get(i).getPrice());
                document.add(p);
                document.add(new Paragraph(" ******   " + oblist.get(i).getNote()));
                if(oblist.get(i).getNote2() != null){
                    document.add(new Paragraph(" ******   " + oblist.get(i).getNote2()));
                }
//                document.add(new Paragraph(" ******   " +oblist.get(i).getNote() + "\n" + " ******   " + oblist.get(i).getNote2()));

            }

            document.add(new Paragraph("SUBTTL " + "                                                           " + lsub.getText()));
            document.add(new Paragraph("Service Charge 7,5%    " + "                                   " + lser.getText()));
            document.add(new Paragraph("TAX 10%     " + "                                                     " + ltax.getText()));
            document.add(new Paragraph("========================================="));
            String spacetotal =  String.format("%-8s" , lgrand.getText());
            Chunk gluex = new Chunk(new VerticalPositionMark());
            Paragraph Gt = new Paragraph("Grand Total ");
            Gt.add(new Chunk(gluex));
            Gt.add(spacetotal);
            document.add(Gt);
//            document.add(new Paragraph("GrandTotal :     " + "                                         " + lgrand.getText()));



            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        oblist.get().getItemquantity()
    }

    public void pass(String StoreID, String StoreName, String TransactionID, String TableName, String CashierName, String WaiterName, String datentime){
        lStoreID.setText(StoreID);
        lStoreName.setText(StoreName);
        lTransactionID.setText(TransactionID);
        lTableName.setText(TableName);
        lCashierName.setText(CashierName);
        lWaiterName.setText(WaiterName);
        lDatenTime.setText(datentime);
//        lStoreAddress.setText();
    }



}
