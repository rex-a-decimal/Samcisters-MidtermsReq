import java.sql.*;

public class DataMgr {
    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/teamsamcisters";
    private static final String USER = "root";
    private static final String PASS = "";

    private DataMgr() {
    }

    public static Connection getCon() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(URL, USER, PASS);
        }
        return con;
    }

    public static void closeCon() {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException sqle) {
                System.out.println("Cannot close the connection.");
            }
        }
    }
}
