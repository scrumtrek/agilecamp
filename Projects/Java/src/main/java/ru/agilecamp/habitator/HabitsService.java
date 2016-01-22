/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.agilecamp.habitator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author d_alex
 */
public class HabitsService {
    private static final String ADD_HABIT_SQL = "INSERT INTO HABITATOR01.HABITS (username, name) VALUES (?, ?)";
    private static final String GET_HABITS_LIST_SQL = "SELECT name FROM HABITATOR01.HABITS WHERE username = ?";

    private DataSource dataSource;

    public HabitsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Set<String> getHabitsForUser(int userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_HABITS_LIST_SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            Set<String> list = new HashSet<String>();
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            return list;
        } finally {
            connection.close();
        }
    }

    public void addHabit(int userId, String habitName) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(ADD_HABIT_SQL);

            ps.setInt(1, userId);
            ps.setString(2, habitName);

            ps.execute();
        } finally {
            connection.close();
        }
    }

}
