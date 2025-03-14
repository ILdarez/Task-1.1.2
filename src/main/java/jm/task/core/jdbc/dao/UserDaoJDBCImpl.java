package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.nio.channels.ScatteringByteChannel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL" +
                ");";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при создании таблицы" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users;";
        try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
        preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при удалении таблицы" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String INSERT_USER_SQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setByte(3, age);
        preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при добавлении пользователя" + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с ID " + id + " удален.");
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String SELECT_ALL_USER_SQL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER_SQL);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String CLEAN_TABLE_SQL = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при очистке таблицы: " + e.getMessage());
        }

    }
}
