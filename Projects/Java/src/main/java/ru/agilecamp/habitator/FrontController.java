package ru.agilecamp.habitator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@SuppressWarnings("serial")
@WebServlet("/do")
public class FrontController extends HttpServlet {

    public static final String INDEXJSP = "index.jsp";
    public static final String LOGINJSP = "login.jsp";
    public static final String REGISTRATION_FORMJSP = "registration_form.jsp";
    public static final String MY_HABITSJSP = "my_habits.jsp";
    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;
    private UserService userService;
    private HabitsService habitsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = new UserService(dataSource);
        habitsService = new HabitsService(dataSource);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Logger.getLogger(FrontController.class.getName()).log(Level.INFO, "action: " + action);
        if ("login".equals(action)) {
            login(request, response);
        } else if ("register".equals(action)) {
            registerNewUser(request, response);
        } else if (request.getSession().getAttribute("isLoggedIn").equals(true)) {
            if ("logout".equals(action)) {
                logout(request, response);
            } else if ("my_habits".equals(action)) {
                habitsList(request, response);
            } else if ("add_habit".equals(action)) {
                addHabit(request, response);
            } else if ("drop_account".equals(action)) {
                dropUser(request, response);
            } else {
                redirectTo(request, response, INDEXJSP);
            }
        } else {
            redirectTo(request, response, INDEXJSP);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        redirectTo(request, response, INDEXJSP);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login = request.getParameter("name");
            String password = request.getParameter("password");

            Integer userId = userService.authentication(login, password);

            if (userId == null) {
                redirectTo(request, response, LOGINJSP);
            } else {
                request.getSession().setAttribute("username", userId);
                request.getSession().setAttribute("isLoggedIn", true);

                redirectTo(request, response, INDEXJSP);
            }
        } catch (SQLException e) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException(e);
        }
    }

    private void registerNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("name");
        String password = request.getParameter("password");
        String passwordAgain = request.getParameter("password_again");
        try {
            userService.registerUser(login, password, passwordAgain);

            request.getRequestDispatcher(INDEXJSP).forward(request, response);
        } catch (HabitsException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);

            redirectTo(request, response, REGISTRATION_FORMJSP);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void dropUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = getCurrentUserId(request);

            userService.dropUser(userId);
            logout(request, response);

            redirectTo(request, response, INDEXJSP);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    private void habitsList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Set<String> myHabits = habitsService.getHabitsForUser(getCurrentUserId(request));
            request.setAttribute("data", myHabits);
        } catch (SQLException e) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException(e);
        }
        request.getRequestDispatcher(MY_HABITSJSP).forward(request, response);
    }

    private void addHabit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String habitName = request.getParameter("habit");
        Integer userId = getCurrentUserId(request);
        try {
            if (habitName.length() > 0) {
                habitsService.addHabit(userId, habitName);

                redirectTo(request, response, INDEXJSP);
            } else {
                redirectTo(request, response, "add_habit.jsp");
            }
        } catch (SQLException e) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException(e);
        }
    }

    private Integer getCurrentUserId(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("username");
    }

    private void redirectTo(HttpServletRequest request, HttpServletResponse response, String jspName) throws ServletException, IOException {
        request.getRequestDispatcher(jspName).forward(request, response);
    }
}
