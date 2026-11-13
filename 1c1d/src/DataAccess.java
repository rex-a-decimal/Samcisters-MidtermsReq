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
}
