import java.util.ArrayList;
import java.sql.*;

public class DataAccess {

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> list = new ArrayList<>();
        String q = "SELECT * FROM product";
        try (Connection con = DataMgr.getCon(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(q)) {
            while (rs.next()) {
                list.add(new Product(rs.getString("prodid"), rs.getString("vendid"), rs.getString("prodname"),
                        rs.getString("proddesc"), rs.getInt("prodprice"), rs.getString("prodstatus"),
                        rs.getString("prodcategory"), rs.getString("prodimg")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting products.");
        }
        return list;
    }

    public static Product getProductById(String id) {
        String q = "SELECT * FROM product WHERE prodid = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getString("prodid"), rs.getString("vendid"), rs.getString("prodname"),
                        rs.getString("proddesc"), rs.getInt("prodprice"), rs.getString("prodstatus"),
                        rs.getString("prodcategory"), rs.getString("prodimg"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting product.");
        }
        return null;
    }

    public static ArrayList<Product> getProductsByVendor(String vendid) {
        ArrayList<Product> list = new ArrayList<>();
        String q = "SELECT * FROM product WHERE vendid = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, vendid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString("prodid"), rs.getString("vendid"), rs.getString("prodname"),
                        rs.getString("proddesc"), rs.getInt("prodprice"), rs.getString("prodstatus"),
                        rs.getString("prodcategory"), rs.getString("prodimg")));
            }
        } catch (SQLException e) {
            System.out.println("Error.");
        }
        return list;
    }

    public static Tourist loginTourist(String user, String pass) {
        String q = "SELECT * FROM tourist WHERE touruser = ? AND tourpass = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Tourist(rs.getString("tourid"), rs.getString("tourname"), rs.getString("touraddress"),
                        rs.getInt("tourcpnum"), rs.getString("touremail"), rs.getString("touruser"),
                        rs.getString("tourpass"), rs.getString("tourloyalty"));
            }
        } catch (SQLException e) {
            System.out.println("Login error.");
        }
        return null;
    }

    public static Tourist signUpTourist(String name, String address, int cp, String email, String user, String pass) {
        String newId = "11000";
        try (Connection con = DataMgr.getCon(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT tourid FROM tourist ORDER BY tourid DESC LIMIT 1")) {
            if (rs.next()) newId = String.valueOf(Integer.parseInt(rs.getString(1)) + 1);
        } catch (Exception e) {}

        String q = "INSERT INTO tourist(tourid, tourname, touraddress, tourcpnum, touremail, touruser, tourpass) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, newId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setInt(4, cp);
            ps.setString(5, email);
            ps.setString(6, user);
            ps.setString(7, pass);
            ps.executeUpdate();
            return new Tourist(newId, name, address, cp, email, user, pass, null);
        } catch (SQLException e) {
            System.out.println("Sign up failed.");
            return null;
        }
    }

    public static Vendor loginVendor(String user, String pass) {
        String q = "SELECT * FROM vendor WHERE venduser = ? AND vendpass = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Vendor(rs.getString("vendid"), rs.getString("vendestabname"), rs.getString("vendownername"),
                        rs.getString("vendaddress"), rs.getInt("vendcpnum"), rs.getString("vendemail"),
                        rs.getString("vendstatus"), rs.getString("vendpass"), rs.getString("venduser"));
            }
        } catch (SQLException e) {
            System.out.println("Login error.");
        }
        return null;
    }

    public static Admin loginAdmin(String user, String pass) {
        String q = "SELECT * FROM admin WHERE adminuser = ? AND adminpass = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString("adminid"), rs.getString("adminname"), rs.getInt("admincpnum"),
                        rs.getString("adminemail"), rs.getString("adminuser"), rs.getString("adminpass"));
            }
        } catch (SQLException e) {
            System.out.println("Login error.");
        }
        return null;
    }

    public static boolean addProduct(Product p) {
        String q = "INSERT INTO product VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, p.getProdid());
            ps.setString(2, p.getVendid());
            ps.setString(3, p.getProdname());
            ps.setString(4, p.getProddesc());
            ps.setInt(5, p.getProdprice());
            ps.setString(6, p.getProdstatus());
            ps.setString(7, p.getProdcategory());
            ps.setString(8, p.getProdimg());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add product failed.");
            return false;
        }
    }

    public static boolean updateProduct(Product p) {
        String q = "UPDATE product SET prodname=?, proddesc=?, prodprice=?, prodstatus=?, prodcategory=?, prodimg=? WHERE prodid=?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, p.getProdname());
            ps.setString(2, p.getProddesc());
            ps.setInt(3, p.getProdprice());
            ps.setString(4, p.getProdstatus());
            ps.setString(5, p.getProdcategory());
            ps.setString(6, p.getProdimg());
            ps.setString(7, p.getProdid());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed.");
            return false;
        }
    }

    public static boolean deleteProduct(String id) {
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement("DELETE FROM product WHERE prodid=?")) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete failed (maybe used in sales).");
            return false;
        }
    }

    public static ArrayList<Voucher> getVouchers() {
        ArrayList<Voucher> list = new ArrayList<>();
        try (Connection con = DataMgr.getCon(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM voucher")) {
            while (rs.next()) {
                list.add(new Voucher(rs.getString("voucherid"), rs.getString("voucherdesc"), rs.getDouble("voucherdiscount"),
                        rs.getInt("vouchervalue"), rs.getInt("voucherpointsreq"), rs.getString("vouchervalidfrom"), rs.getString("vouchervalidto")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting vouchers.");
        }
        return list;
    }

    public static boolean claimVoucher(String tourid, String vid) {
        try (Connection con = DataMgr.getCon()) {
            PreparedStatement check = con.prepareStatement("SELECT * FROM touristvoucher WHERE tourid=? AND voucherid=?");
            check.setString(1, tourid);
            check.setString(2, vid);
            if (check.executeQuery().next()) {
                System.out.println("Already claimed.");
                return false;
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO touristvoucher VALUES (?, ?, NULL, 'active')");
            ps.setString(1, tourid);
            ps.setString(2, vid);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Claim failed.");
            return false;
        }
    }

    public static ArrayList<Sales> getSalesByTourist(String tourid) {
        ArrayList<Sales> list = new ArrayList<>();
        String q = "SELECT * FROM sales WHERE tourid = ?";
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, tourid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Sales(rs.getString("salesid"), rs.getString("tourid"), rs.getString("payid"),
                        rs.getString("salesdate"), rs.getString("salesstatus"), rs.getDouble("salestotalamt"),
                        rs.getInt("salespointsearned"), rs.getDouble("salesdiscountamt"),
                        rs.getString("salespickupdate"), rs.getString("salespickuptime"), rs.getString("salespickupplace")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting history.");
        }
        return list;
    }

    public static boolean createOrder(String payid, double amt, String mode, String salesid, String tourid,
                                      String pdate, String ptime, String pplace, ArrayList<String[]> items) {
        try (Connection con = DataMgr.getCon()) {
            // payment
            PreparedStatement p1 = con.prepareStatement("INSERT INTO payment VALUES (?,?,?,?,NULL)");
            p1.setString(1, payid);
            p1.setDouble(2, amt);
            p1.setString(3, mode);
            p1.setString(4, "pending");
            p1.executeUpdate();

            // sales
            PreparedStatement p2 = con.prepareStatement(
                "INSERT INTO sales VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            p2.setString(1, salesid);
            p2.setString(2, tourid);
            p2.setString(3, payid);
            p2.setDate(4, Date.valueOf(java.time.LocalDate.now().toString()));
            p2.setString(5, "Pending");
            p2.setDouble(6, amt);
            p2.setInt(7, (int)(amt/100));
            p2.setDouble(8, 0);
            p2.setDate(9, Date.valueOf(pdate));
            p2.setTime(10, Time.valueOf(ptime));
            p2.setString(11, pplace);
            p2.executeUpdate();

            // salesdetails
            for (String[] item : items) {
                Product prod = getProductById(item[0]);
                if (prod == null) continue;
                int qty = Integer.parseInt(item[1]);
                PreparedStatement p3 = con.prepareStatement("INSERT INTO salesdetails VALUES (?,?,?,?,?,?)");
                p3.setString(1, salesid);
                p3.setString(2, prod.getProdid());
                p3.setString(3, prod.getVendid());
                p3.setInt(4, qty);
                p3.setDouble(5, prod.getProdprice());
                p3.setDouble(6, prod.getProdprice() * qty);
                p3.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Order failed: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Tourist> getAllTourists() {
        ArrayList<Tourist> list = new ArrayList<>();
        try (Connection con = DataMgr.getCon(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM tourist")) {
            while (rs.next()) {
                list.add(new Tourist(rs.getString("tourid"), rs.getString("tourname"), rs.getString("touraddress"),
                        rs.getInt("tourcpnum"), rs.getString("touremail"), rs.getString("touruser"),
                        rs.getString("tourpass"), rs.getString("tourloyalty")));
            }
        } catch (SQLException e) {
            System.out.println("Error.");
        }
        return list;
    }

    public static ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> list = new ArrayList<>();
        try (Connection con = DataMgr.getCon(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM vendor")) {
            while (rs.next()) {
                list.add(new Vendor(rs.getString("vendid"), rs.getString("vendestabname"), rs.getString("vendownername"),
                        rs.getString("vendaddress"), rs.getInt("vendcpnum"), rs.getString("vendemail"),
                        rs.getString("vendstatus"), rs.getString("vendpass"), rs.getString("venduser")));
            }
        } catch (SQLException e) {
            System.out.println("Error.");
        }
        return list;
    }

    public static boolean updateVendorStatus(String id, String status) {
        try (Connection con = DataMgr.getCon(); PreparedStatement ps = con.prepareStatement("UPDATE vendor SET vendstatus=? WHERE vendid=?")) {
            ps.setString(1, status);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update failed.");
            return false;
        }
    }
}
