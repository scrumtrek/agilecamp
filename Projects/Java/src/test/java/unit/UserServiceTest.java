/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unit;

import org.junit.Test;
import ru.agilecamp.habitator.FrontController;
import ru.agilecamp.habitator.HabitsException;
import ru.agilecamp.habitator.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 *
 * @author d_alex
 */
public class UserServiceTest {

    @Test
    public void shouldCreateUserWhenRegistered() throws SQLException, HabitsException {
        DataSourceMockBuilder dataSourceMockBuilder = new DataSourceMockBuilder()
                .whenResultSetNextReturn(false);

        UserService userService = new UserService(dataSourceMockBuilder.getDataSourceMock());

        userService.registerUser("testLogin", "testPassword", "testPassword");
        verify(dataSourceMockBuilder.getPreparedStatement()).execute();
    }

    @Test
    public void shouldDropUser() throws SQLException, HabitsException {
        DataSourceMockBuilder dataSourceMockBuilder = new DataSourceMockBuilder();

        UserService userService = new UserService(dataSourceMockBuilder.getDataSourceMock());

        userService.dropUser(1);
        verify(dataSourceMockBuilder.getPreparedStatement()).execute();
    }

    @Test
    public void shouldLoginUser() throws SQLException {
        DataSourceMockBuilder dataSourceMockBuilder = new DataSourceMockBuilder()
                .whenResultSetNextReturn(true);
        when(dataSourceMockBuilder.getResultSetMock().getInt("id")).thenReturn(1);

        UserService userService = new UserService(dataSourceMockBuilder.getDataSourceMock());
        Integer userId = userService.authentication("testLogin", "testPassword");

        assertNotNull(userId);
    }

    /*
    @Test
    public void shouldSuccessfullyLogin() throws ServletException, IOException, SQLException {
        ServletAPIMockBuilder servletAPIMockBuilder = new ServletAPIMockBuilder();
        when(servletAPIMockBuilder.getRequest().getParameter("action")).thenReturn("login");
        when(servletAPIMockBuilder.getRequest().getParameter("name")).thenReturn("username");
        when(servletAPIMockBuilder.getRequest().getParameter("password")).thenReturn("password");

        HttpServletRequest request = servletAPIMockBuilder.getRequest();
        HttpServletResponse response = servletAPIMockBuilder.getResponse();

        UserService userService = mock(UserService.class);
        when(userService.authentication("username", "password")).thenReturn(1);

        FrontController frontController = new FrontController();
        frontController.setUserService(userService);

        frontController.service(request, response);

        verify(request.getSession()).setAttribute("username", 1);
        verify(request.getSession()).setAttribute("isLoggedIn", true);
    }
    */
}
