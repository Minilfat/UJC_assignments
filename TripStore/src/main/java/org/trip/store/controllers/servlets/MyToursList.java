package org.trip.store.controllers.servlets;

import org.trip.store.models.Tour;
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
import java.util.List;

@WebServlet(urlPatterns = "/tours/my")
public class MyToursList extends HttpServlet {

    private static IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("logged");
        List<Tour> list = userService.getMyTours(u);
        session.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/pages/mytours.jsp").forward(req,resp);
    }
}
