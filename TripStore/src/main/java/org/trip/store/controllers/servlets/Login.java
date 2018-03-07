package org.trip.store.controllers.servlets;

import org.apache.log4j.Logger;
import org.trip.store.models.User;
import org.trip.store.services.IUserService;
import org.trip.store.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

//    private static final Logger LOGGER = Logger.getLogger(Login.class);
    private static final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("logged") != null) {
            resp.sendRedirect(req.getContextPath() + "/tours");
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userService.signin(login, password);


        if (user == null) {
            req.setAttribute("errorMessage", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("logged", user);
            resp.sendRedirect(req.getContextPath() + "/tours");
        }

    }
}
