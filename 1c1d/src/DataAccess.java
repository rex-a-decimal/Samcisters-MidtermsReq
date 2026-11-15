import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.*;

public class DataAccess {
    public static ArrayList<Product> getProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        String query = "SELECT p.prodid, p.vendid, p.prodname, p.proddesc, p.prodprice, p.prodstatus, p.prodcategory, p.prodimg \n" +
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
}
