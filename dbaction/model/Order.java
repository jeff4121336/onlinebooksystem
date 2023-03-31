package dbaction.model;

import java.sql.*;

public class Order {
    enum Status { ordered, shipped, received };
    private String OID;
    private String UID;
    private Date Order_Date;
    private String ISBN;
    private int Order_Quantity;
    private String Shipping_Status;
    
    public Order(){
    }
    public Order(String OID, String UID, Date Order_Date, String ISBN, int Order_Quantity, String Shipping_Status){
        this.OID = OID;
        this.UID = UID;
        this.Order_Date = Order_Date;
        this.ISBN = ISBN;
        this.Order_Quantity = Order_Quantity;
        this.Shipping_Status = Shipping_Status;
    }

    private boolean isValid_OID(String OID){
        String regex_OID = "\\d{1,8}";
        if (OID.isEmpty() || !OID.matches(regex_OID)){
            System.out.println("OID is not in the correct format.");
            return false;
        }
        return true;
    }

    private boolean isValid_UID(String UID){
        if (UID.isEmpty() || UID.length()>10){
            System.out.println("UID is not in the correct format.");
            return false;
        }
        return true;
    }

    private boolean isValid_ISBN(String ISBN){
        String regex_ISBN = "\\d-\\d{4}-\\d{4}-\\d";
        if (!ISBN.matches(regex_ISBN)) {
            System.out.println("ISBN is not in the correct format.");
            return false;
        }
        return true;
    }

    private boolean isValid_Order_Quantity(int Order_Quantity){
        if (Order_Quantity < 0){
            System.out.println("Order Quantity is not in the correct format.");
            return false;
        }
        return true;
    }

    private boolean isValid_Shipping_Status(String Shipping_Status){
        if (!Shipping_Status.equals("ordered") && !Shipping_Status.equals("shipped") && !Shipping_Status.equals("received")){
            System.out.println("Shipping Status is not in the correct format.");
            return false;
        }
        return true;
    }

    public boolean insert(Connection conn) throws SQLException{
        boolean isInputValid = true;
        OID = OID.trim();
        UID = UID.trim();
        ISBN = ISBN.trim();
        Shipping_Status = Shipping_Status.trim().toLowerCase();
        
        if (!isValid_OID(OID) || !isValid_UID(UID) || !isValid_ISBN(ISBN) || !isValid_Order_Quantity(Order_Quantity) || !isValid_Shipping_Status(Shipping_Status)){
            return false;
        }
        
        // insert to order
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO order_ values(?,?,?)");
            pstmt.setString(1, OID);
            pstmt.setDate(2, Order_Date);
            pstmt.setString(3, Shipping_Status);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e+"in order insertion");
        }
        // insert to product
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO product values(?,?,?)");
            pstmt.setString(1, OID);
            pstmt.setString(2, ISBN);
            pstmt.setInt(3, Order_Quantity);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e+"in product insertion");
        }
        // insert to purchaser
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO purchaser values(?,?)");
            pstmt.setString(1, OID);
            pstmt.setString(2, UID);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e+"in purchaser insertion");
        }
        return isInputValid;
    }

    public int size(Connection conn) throws SQLException{
        int size=-1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT COUNT(*) FROM order_");
            rs.next();
            size = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e+"\nin order size");
        }
        return size;
    }

    public void update_shipping_status(Connection conn,String OID,String Shipping_Status) throws SQLException{
        OID = OID.trim();
        Shipping_Status = Shipping_Status.trim();
        // update shipping status
        try {
            PreparedStatement pstmt_select = conn.prepareStatement("SELECT * FROM ORDER_ Where OID = ?");
            pstmt_select.setString(1, OID);
            ResultSet rs = pstmt_select.executeQuery();
    
            if (rs.next()) {
                String Original_status= rs.getString("SHIPPING_STATUS");
                if ((Original_status.equals("ordered") && Shipping_Status.equals("received")
                    || (Original_status.equals("ordered") && Shipping_Status.equals("shipped"))
                    || (Original_status.equals("shipped") && Shipping_Status.equals("received")))) {
                    PreparedStatement pstmt = conn.prepareStatement("UPDATE ORDER_ SET Shipping_Status = ? WHERE OID = ?");
                    pstmt.setString(1, Shipping_Status);
                    pstmt.setString(2, OID);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("SUCCESS: Shipping status of: "+OID+" is updated from "+ Original_status+ " to " +Shipping_Status);
                }else{            
                    System.out.println("ERROR: Cannot change "+ Original_status +" to "+ Shipping_Status);
                    return;
                }
            } else {
                System.out.println("ERROR: No update is done");
                return;
            }
            
        } catch (SQLException e) {
            System.out.println(e+"in update_shipping_status");
        }

    }
}
