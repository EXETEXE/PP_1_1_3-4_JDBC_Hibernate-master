package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String url = "jdbc:mysql://localhost:3306/katataskdb";
    private static final String user = "root";
    private static final String password = "HGjd15hj391";

    //    --- Класс Connection для соединения в рамках JDBC ---
    private static Connection connection;

    //    --- Класс SessionFactory для соединения в рамках Hibernate ---
    private static SessionFactory sessionFactory;

    //    --- Соединение JDBC ---
    public static Connection getConnection() {

        try {

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return connection;
    }

    //    --- Закрытие соединения JDBC ---
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

    //    --- Соединение Hibernate ---
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, url);
                settings.put(Environment.USER, user);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    //    --- Закрытие соединения Hibernate ---
    public static void closeSessionFactory() {

        if (sessionFactory != null && sessionFactory.isOpen()) {

            sessionFactory.close();
        }

    }
}
