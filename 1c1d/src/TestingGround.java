import java.sql.Connection;
import java.util.ArrayList;

public class TestingGround {
    public static void main(String[] args) {
        System.out.println("Attempting to connect to database...");

        ArrayList<Product> products = DataAccess.getProducts();

        if (products.isEmpty()) {
            System.out.println("No products found. Check your database.");
        } else {
            for (Product p : products) {
                System.out.println(p.getProdid() + " | " +
                        p.getProdname() + " | ₱" +
                        p.getProdprice() + " | " +
                        p.getProdstatus());
            }
        }
    }
}