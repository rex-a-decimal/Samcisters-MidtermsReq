import java.sql.Connection;

public class TestConn {
    public static void main(String[] args) {
        System.out.println("Attempting to connect to database...");

        try {
            Connection con = DataMgr.getCon();
            System.out.println("SUCCESS! Connected to the database.");
            DataMgr.closeCon();
            System.out.println("Connection closed safely.");
        } catch (Exception e) {
            System.out.println("FAILED to connect.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}