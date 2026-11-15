import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.*;

public class DataAccess {
    // get all products
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        String query = "SELECT p.prodid, p.vendid, p.prodname, p.proddesc, p.prodprice, p.prodstatus, p.prodcategory, p.prodimg " +
                "FROM product p JOIN vendor v ON p.vendid = v.vendid";

        try {
            Connection con = DataMgr.getCon();
            try (Statement state = con.createStatement()){
                try (ResultSet resSet = state.executeQuery(query)){
                    while (resSet.next()) {
                        Product product = new Product(resSet.getString(1), resSet.getString(2),
                                resSet.getString(3), resSet.getString(4),
                                resSet.getInt(5), resSet.getString(6),
                                resSet.getString(7), resSet.getString(8));
                        productList.add(product);
                    }
                } catch (SQLException sqle) {
                    System.out.println("Cannot retrieve data.");
                }
            } catch (SQLException sqle) {
                System.out.println("Data access has trouble.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.exit(0);
        }
        return productList;
    }

    // tourist login
    public static Tourist login(String username, String password) {
        Tourist tourist = null;

        String query = "SELECT * FROM tourist WHERE touruser = ? AND tourpass = ?";

        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tourist = new Tourist(
                            rs.getString("tourid"),
                            rs.getString("tourname"),
                            rs.getString("touraddress"),
                            rs.getInt("tourcpnum"),
                            rs.getString("touremail"),
                            rs.getString("touruser"),
                            rs.getString("tourpass"),
                            rs.getString("tourloyalty")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        return tourist;
    }

    // tourist sign up
    public static Tourist signUp(String name, String address, int cpnum, String email, String user, String pass) {
        Tourist tourist = null;
        String newId = "11000";
        String idRetrieval = "SELECT tourid FROM tourist ORDER BY tourid DESC LIMIT 1";

        try {
            Connection conn = DataMgr.getCon();
            try (PreparedStatement ps = conn.prepareStatement(idRetrieval)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int lastId = Integer.parseInt(rs.getString("tourid"));
                        newId = String.valueOf(lastId + 1);
                    }
                } catch (SQLException sqle) {
                    System.out.println("Cannot retrieve ID data.");
                }
            } catch (SQLException sqle) {
                System.out.println("Data access has trouble.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        String query = "INSERT INTO tourist(tourid, tourname, touraddress, tourcpnum, touremail, touruser, tourpass) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DataMgr.getCon();
            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, newId);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setInt(4, cpnum);
                ps.setString(5, email);
                ps.setString(6, user);
                ps.setString(7, pass);

                ps.execute();

                tourist = new Tourist(newId, name, address, cpnum, email, user, pass, null);

            } catch (SQLException sqle) {
                System.out.println("Cannot insert new user.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        return tourist;
    }

    // vendor login
    public static Vendor loginVendor(String username, String password) {
        Vendor vendor = null;
        String query = "SELECT * FROM vendor WHERE venduser = ? AND vendpass = ?";

        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vendor = new Vendor(
                            rs.getString("vendid"),
                            rs.getString("vendestabname"),
                            rs.getString("vendownername"),
                            rs.getString("vendaddress"),
                            rs.getInt("vendcpnum"),
                            rs.getString("vendemail"),
                            rs.getString("vendstatus"),
                            rs.getString("vendpass"),
                            rs.getString("venduser")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        return vendor;
    }

    // admin login
    public static Admin loginAdmin(String username, String password) {
        Admin admin = null;
        String query = "SELECT * FROM admin WHERE adminuser = ? AND adminpass = ?";

        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin(
                            rs.getString("adminid"),
                            rs.getString("adminname"),
                            rs.getInt("admincpnum"),
                            rs.getString("adminemail"),
                            rs.getString("adminuser"),
                            rs.getString("adminpass")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        return admin;
    }

    // get products by vendor
    public static ArrayList<Product> getProductsByVendor(String vendid) {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE vendid = ?";

        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, vendid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(
                            rs.getString("prodid"),
                            rs.getString("vendid"),
                            rs.getString("prodname"),
                            rs.getString("proddesc"),
                            rs.getInt("prodprice"),
                            rs.getString("prodstatus"),
                            rs.getString("prodcategory"),
                            rs.getString("prodimg")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Cannot get vendor products.");
        }
        return list;
    }

    // add product
    public static boolean addProduct(Product p) {
        String query = "INSERT INTO product (prodid, vendid, prodname, proddesc, prodprice, prodstatus, prodcategory, prodimg) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
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
            System.out.println("Cannot add product: " + e.getMessage());
            return false;
        }
    }

    // update product (name, desc, price, status, category, img)
    public static boolean updateProduct(Product p) {
        String query = "UPDATE product SET prodname=?, proddesc=?, prodprice=?, prodstatus=?, prodcategory=?, prodimg=? WHERE prodid=?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getProdname());
            ps.setString(2, p.getProddesc());
            ps.setInt(3, p.getProdprice());
            ps.setString(4, p.getProdstatus());
            ps.setString(5, p.getProdcategory());
            ps.setString(6, p.getProdimg());
            ps.setString(7, p.getProdid());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Cannot update product.");
            return false;
        }
    }

    // delete product
    public static boolean deleteProduct(String prodid) {
        String query = "DELETE FROM product WHERE prodid = ?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, prodid);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Cannot delete product. Maybe linked to sales.");
            return false;
        }
    }

    // get all vouchers
    public static ArrayList<Voucher> getVouchers() {
        ArrayList<Voucher> list = new ArrayList<>();
        String query = "SELECT * FROM voucher";
        try (Connection con = DataMgr.getCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Voucher v = new Voucher(
                        rs.getString("voucherid"),
                        rs.getString("voucherdesc"),
                        rs.getDouble("voucherdiscount"),
                        rs.getInt("vouchervalue"),
                        rs.getInt("voucherpointsreq"),
                        rs.getString("vouchervalidfrom"),
                        rs.getString("vouchervalidto")
                );
                list.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Cannot get vouchers.");
        }
        return list;
    }

    // claim voucher for tourist
    public static boolean claimVoucher(String tourid, String voucherid) {
        // check if already claimed
        String check = "SELECT * FROM touristvoucher WHERE tourid=? AND voucherid=?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(check)) {
            ps.setString(1, tourid);
            ps.setString(2, voucherid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("You already have this voucher.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking voucher.");
            return false;
        }

        String query = "INSERT INTO touristvoucher (tourid, voucherid, tvdateredeemed, tvstatus) VALUES (?,?,NULL,'active')";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, tourid);
            ps.setString(2, voucherid);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot claim voucher.");
            return false;
        }
    }

    // get tourist vouchers
    public static ArrayList<TouristVoucher> getTouristVouchers(String tourid) {
        ArrayList<TouristVoucher> list = new ArrayList<>();
        String query = "SELECT * FROM touristvoucher WHERE tourid = ?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, tourid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TouristVoucher tv = new TouristVoucher(
                            rs.getString("tourid"),
                            rs.getString("voucherid"),
                            rs.getString("tvdateredeemed"),
                            rs.getString("tvstatus")
                    );
                    list.add(tv);
                }
            }
        } catch (SQLException e) {
            System.out.println("Cannot get tourist vouchers.");
        }
        return list;
    }

    // get sales history for tourist
    public static ArrayList<Sales> getSalesByTourist(String tourid) {
        ArrayList<Sales> list = new ArrayList<>();
        String query = "SELECT * FROM sales WHERE tourid = ? ORDER BY salesdate DESC";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, tourid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sales s = new Sales(
                            rs.getString("salesid"),
                            rs.getString("tourid"),
                            rs.getString("payid"),
                            rs.getString("salesdate"),
                            rs.getString("salesstatus"),
                            rs.getDouble("salestotalamt"),
                            rs.getInt("salespointsearned"),
                            rs.getDouble("salesdiscountamt"),
                            rs.getString("salespickupdate"),
                            rs.getString("salespickuptime"),
                            rs.getString("salespickupplace")
                    );
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Cannot get sales history.");
        }
        return list;
    }

    // simple create payment
    public static boolean createPayment(String payid, double amount, String mode, String status, String datepaid) {
        String query = "INSERT INTO payment (payid, payamount, paymode, paystatus, paydatepaid) VALUES (?,?,?,?,?)";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, payid);
            ps.setDouble(2, amount);
            ps.setString(3, mode);
            ps.setString(4, status);
            if (datepaid != null) {
                ps.setDate(5, Date.valueOf(datepaid));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot create payment: " + e.getMessage());
            return false;
        }
    }

    // create sales
    public static boolean createSales(Sales s) {
        String query = "INSERT INTO sales (salesid, tourid, payid, salesdate, salesstatus, salestotalamt, salespointsearned, salesdiscountamt, salespickupdate, salespickuptime, salespickupplace) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, s.getSalesid());
            ps.setString(2, s.getTourid());
            ps.setString(3, s.getPayid());
            ps.setDate(4, Date.valueOf(s.getSalesdate()));
            ps.setString(5, s.getSalesstatus());
            ps.setDouble(6, s.getSalestotalamt());
            ps.setInt(7, s.getSalespointsearned());
            ps.setDouble(8, s.getSalesdiscountamt());
            ps.setDate(9, Date.valueOf(s.getSalespickupdate()));
            ps.setTime(10, Time.valueOf(s.getSalespickuptime()));
            ps.setString(11, s.getSalespickupplace());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot create sales: " + e.getMessage());
            return false;
        }
    }

    // create sales details
    public static boolean createSalesDetail(SalesDetails sd) {
        String query = "INSERT INTO salesdetails (salesid, prodid, vendid, sdqty, sdunitprice, sdsubtotal) VALUES (?,?,?,?,?,?)";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, sd.getSalesid());
            ps.setString(2, sd.getProdid());
            ps.setString(3, sd.getVendid());
            ps.setInt(4, sd.getSdqty());
            ps.setDouble(5, sd.getSdunitprice());
            ps.setDouble(6, sd.getSdsubtotal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Cannot create sales detail.");
            return false;
        }
    }

    // get all tourists (for admin)
    public static ArrayList<Tourist> getAllTourists() {
        ArrayList<Tourist> list = new ArrayList<>();
        String query = "SELECT * FROM tourist";
        try (Connection con = DataMgr.getCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Tourist t = new Tourist(
                        rs.getString("tourid"),
                        rs.getString("tourname"),
                        rs.getString("touraddress"),
                        rs.getInt("tourcpnum"),
                        rs.getString("touremail"),
                        rs.getString("touruser"),
                        rs.getString("tourpass"),
                        rs.getString("tourloyalty")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Cannot get tourists.");
        }
        return list;
    }

    // get all vendors (for admin)
    public static ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> list = new ArrayList<>();
        String query = "SELECT * FROM vendor";
        try (Connection con = DataMgr.getCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Vendor v = new Vendor(
                        rs.getString("vendid"),
                        rs.getString("vendestabname"),
                        rs.getString("vendownername"),
                        rs.getString("vendaddress"),
                        rs.getInt("vendcpnum"),
                        rs.getString("vendemail"),
                        rs.getString("vendstatus"),
                        rs.getString("vendpass"),
                        rs.getString("venduser")
                );
                list.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Cannot get vendors.");
        }
        return list;
    }

    // update vendor status (admin)
    public static boolean updateVendorStatus(String vendid, String status) {
        String query = "UPDATE vendor SET vendstatus = ? WHERE vendid = ?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, vendid);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Cannot update vendor status.");
            return false;
        }
    }

    // get product by id
    public static Product getProductById(String prodid) {
        String query = "SELECT * FROM product WHERE prodid = ?";
        try (Connection con = DataMgr.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, prodid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getString("prodid"),
                            rs.getString("vendid"),
                            rs.getString("prodname"),
                            rs.getString("proddesc"),
                            rs.getInt("prodprice"),
                            rs.getString("prodstatus"),
                            rs.getString("prodcategory"),
                            rs.getString("prodimg")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Cannot get product.");
        }
        return null;
    }
}
