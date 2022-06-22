package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private static void dbUpdate(String sql) {

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";

        dbUpdate(sql);

        System.out.println("Таблица user создана или уже существует");

    }

    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";

        dbUpdate(sql);

        System.out.println("Таблица user успешно удалена");
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO user(name, lastName, age) values (" +
                "'" + name + "'," +
                "'" + lastName + "'," +
                age +
                ");";

        dbUpdate(sql);

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {

        String sql = "DELETE from user where id=" + id + ";";

        dbUpdate(sql);

        System.out.println("Пользователь с id: " + id + " удалён");
    }

    public List<User> getAllUsers() {

        List<User> result = new ArrayList<>();

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {

                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));

                user.setId(resultSet.getLong("id"));

                result.add(user);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {

        String sql = "TRUNCATE TABLE user;";

        dbUpdate(sql);

        System.out.println("Таблица user очищена");

    }
}
