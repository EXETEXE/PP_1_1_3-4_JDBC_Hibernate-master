package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String url = "jdbc:mysql://localhost:3306/katataskdb";
    private static final String user = "root";
    private static final String password = "HGjd15hj391";
    private static Connection connection;

    public static Connection getConnection() {

        try {

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {

        try {

            if (connection != null && !connection.isClosed()) {

                connection.close();
            }

            System.out.println("Соединение закрыто");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
