package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Таблица user создана или уже существует");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Таблица user успешно удалена");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO user(name, lastName, age) values (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE from user where id= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Пользователь с id: " + id + " удалён");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Таблица user очищена");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
