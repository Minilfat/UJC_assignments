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

import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class Register extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Login.class);
    private static final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("logged") != null) {
            resp.sendRedirect(req.getContextPath() + "/tours");
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeat = req.getParameter("passwordRepeat");

        if (!password.equals(repeat)
                || "".equals(login) || "".equals(password)) {
            req.setAttribute("registrationError", "Bad input. Try again, please!");
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);

        } else {

            User u = userService.register(login, password);
            if (u == null) {
                req.setAttribute("registrationError", "Such user already exists");
                req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("logged", u);
                resp.sendRedirect(req.getContextPath() + "/tours");
            }

        }
    }
}
