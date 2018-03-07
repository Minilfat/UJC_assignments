package org.trip.store.controllers.servlets;

import org.apache.log4j.Logger;
import org.trip.store.models.Tour;
import org.trip.store.models.User;
import org.trip.store.services.ITourService;
import org.trip.store.services.TourService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = "/tours/order")
public class OrderTour extends HttpServlet {

    private static ITourService tourService = new TourService();
    private static final Logger LOGGER = Logger.getLogger(OrderTour.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("logged");
        Long tourId;
        try {
            tourId = Long.valueOf(req.getParameter("tourid"));
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            resp.sendRedirect(req.getContextPath() + "/tours");
            return;
        }

        tourService.orderTour(u.getId(), tourId);

        Tour tour = tourService.getTour(tourId);
        session.setAttribute("tour", tour);
        req.getRequestDispatcher("/WEB-INF/pages/tourInfo.jsp").forward(req,resp);
    }
}
