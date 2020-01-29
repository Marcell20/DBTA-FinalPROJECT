package sample;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private String id,table,cashier,waiter,guest,datentime;

    public Transaction(String id, String table, String cashier, String waiter, String guest, String datentime) throws SQLException {
        this.id = id;
        this.table = table;
        this.cashier = cashier;
        this.waiter = waiter;
        this.guest = guest;
        this.datentime = datentime;

//        ResultSet rs = MainController.stm.executeQuery("Select EmployeeName from Employee where EmployeeID = " + this.cashier + " ;");
//        rs.next();
//        this.cashier = rs.getString("EmployeeName");
//        rs = MainController.stm.executeQuery("Select EmployeeName from Employee where EmployeeID = " + this.waiter + " ;");
//        rs.next();
//        this.waiter = rs.getString("EmployeeName");
    }

    public String getId() {
        return id;
    }

    public String getTable() {
        return table;
    }

    public String getCashier() {
        return cashier;
    }

    public String getWaiter() {
        return waiter;
    }

    public String getGuest() {
        return guest;
    }

    public String getDatentime() {
        return datentime;
    }
}
