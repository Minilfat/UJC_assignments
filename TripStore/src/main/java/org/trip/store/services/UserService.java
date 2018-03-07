package org.trip.store.services;

import org.trip.store.dao.ifaces.TourDAO;
import org.trip.store.dao.ifaces.UserDAO;
import org.trip.store.dao.ifaces.UserToursDAO;
import org.trip.store.dao.impls.TourDAOImpl;
import org.trip.store.dao.impls.UserToursDAOImpl;
import org.trip.store.dao.impls.UsersDAOImpl;
import org.trip.store.models.Tour;
import org.trip.store.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService{

    private static UserDAO userDAO = new UsersDAOImpl();
    private static TourDAO tourDAO = new TourDAOImpl();
    private static UserToursDAO userToursDAO = new UserToursDAOImpl();


    @Override
    public User signin(String login, String password) {
        return userDAO.getByLoginAndPassword(login, password);
    }

    @Override
    public User register(String login, String password) {

        if (login == null || !userDAO.isUnique(login)) {
            return null;
        } else {
            User user = new User(login, password);
            userDAO.insert(user);
            return user;
        }
    }

    @Override
    public List<Tour> getMyTours(User u) {
        if (u == null)
            return null;

        List<Tour> tours = new ArrayList<>();
        List<Long> ids = userToursDAO.getUserTours(u.getId());

        for (Long i : ids)
            tours.add(tourDAO.getById(i));

        return  tours;
    }
}
