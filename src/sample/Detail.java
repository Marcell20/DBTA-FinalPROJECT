package sample;

public class Detail {
    private String id,itemid,itemname,itemquantity,price,note,note2;


    public Detail(String id, String itemid, String itemname, String itemquantity, String price, String note, String note2) {
        this.id = id;
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.price = price;
        this.note = note;
        this.note2 = note2;
    }

    public String getId() {
        return id;
    }

    public String getItemid() {
        return itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public String getPrice() {
        return price;
    }

    public String getNote() {
        return note;
    }

    public String getNote2() {
        return note2;
    }
}
