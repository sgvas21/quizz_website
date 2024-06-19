package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class    DBConnection {
    private static final String user = "root";
    private static final String password = "RootRoot12!@";
    private static final String db_name = "databaza";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("org.postgresql.Driver");
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/moresy" + db_name, user, password);
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, password);
    }
}