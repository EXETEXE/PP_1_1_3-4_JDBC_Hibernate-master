package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String url = "jdbc:mysql://localhost:3306/katataskdb";
    private static final String user = "root";
    private static final String password = "HGjd15hj391";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{

        return connection = DriverManager.getConnection(url, user, password);
    }
}
