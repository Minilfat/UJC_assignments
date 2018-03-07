package org.trip.store.services;

import org.trip.store.dao.ifaces.TourDAO;
import org.trip.store.dao.ifaces.UserToursDAO;
import org.trip.store.dao.impls.TourDAOImpl;
import org.trip.store.dao.impls.UserToursDAOImpl;
import org.trip.store.models.Tour;

import java.util.List;
import java.util.stream.Collectors;

public class TourService implements ITourService {

    private static TourDAO tourDAO = new TourDAOImpl();
    private static UserToursDAO userToursDAO = new UserToursDAOImpl();



    @Override
    public List<Tour> getAvailableTours() {
        List<Tour> availableTours = tourDAO.getAll();
        final List<Long> exclude = userToursDAO.bookedTours();

        return availableTours.stream()
                .filter(i -> !exclude.contains(i.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void orderTour(Long owner, Long id) {
        userToursDAO.insert(owner, id);
    }

    @Override
    public Tour getTour(Long id) {
        return tourDAO.getById(id);
    }
}
