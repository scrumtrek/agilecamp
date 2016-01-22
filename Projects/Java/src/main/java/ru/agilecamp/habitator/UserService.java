package ru.agilecamp.habitator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author d_alex
 */
public class UserService {

    private DataSource dataSource;
    private static final String IS_USER_EXISTS_SQL = "SELECT id FROM HABITATOR01.USERS WHERE name = ?";
    private static final String ADD_USER_SQL = "INSERT INTO HABITATOR01.USERS (name, password) VALUES (?, ?)";
    private static final String DROP_USER_SQL = "DELETE FROM HABITATOR01.USERS WHERE id = ?";
    private static final String LOGIN_SQL = "SELECT id FROM HABITATOR01.USERS WHERE name = ? AND password = ?";

    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Register new user
     *
     * @param login - user login
     * @param password - user password
     * @param passwordAgain - user password again
     * @throws HabitsException
     */
    public void registerUser(String login, String password, String passwordAgain) throws HabitsException, SQLException {
        if (login.length() > 0 && password.length() > 0 && password.equals(passwordAgain)) {
            Connection connection = dataSource.getConnection();
            try {
                PreparedStatement ps = connection.prepareStatement(IS_USER_EXISTS_SQL);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new HabitsException("user already exists");
                } else {
                    ps = connection.prepareStatement(ADD_USER_SQL);
                    ps.setString(1, login);
                    ps.setString(2, password);
                    ps.execute();
                }
            } finally {
                connection.close();
            }
        } else {
            throw new HabitsException("Password not equals another password or login is empty");
        }
    }

    /**
     * authenticate user
     *
     * @param login
     * @param password
     * @return user id
     */
    public Integer authentication(String login, String password) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(LOGIN_SQL);

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();
            return result.next() ? result.getInt("id") : null;
        } finally {
            connection.close();
        }
    }

    /**
     * Drop user
     *
     * @param id - user id
     */
    public void dropUser(int userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(DROP_USER_SQL);
            ps.setInt(1, userId);
            ps.execute();
        } finally {
            connection.close();
        }
    }
}
