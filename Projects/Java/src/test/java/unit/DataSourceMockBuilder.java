/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import static org.mockito.Mockito.*;

/**
 *
 * @author d_alex
 */
public class DataSourceMockBuilder {

    private DataSource dataSourceMock;
    private Connection connectionMock;
    private PreparedStatement preparedStatement;
    private ResultSet resultSetMock;

    public DataSourceMockBuilder() throws SQLException {
        dataSourceMock = mock(DataSource.class);
        connectionMock = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);

        when(dataSourceMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(Boolean.TRUE);
        when(preparedStatement.executeQuery()).thenReturn(resultSetMock);
    }

    public DataSource getDataSourceMock() {
        return dataSourceMock;
    }

    public Connection getConnectionMock() {
        return connectionMock;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public ResultSet getResultSetMock() {
        return resultSetMock;
    }

    public DataSourceMockBuilder whenResultSetNextReturn(boolean result) throws SQLException{
        when(resultSetMock.next()).thenReturn(result);
        return this;
    }
}
