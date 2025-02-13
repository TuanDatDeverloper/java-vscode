
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlyinternet";
    private static final String USER = "root";
    private static final String PASSWORD = "D@at952005";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy Driver MySQL!", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

