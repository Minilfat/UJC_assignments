package org.trip.store.services;

import org.trip.store.models.Tour;
import org.trip.store.models.User;

import java.util.List;

public interface IUserService {

    User signin(String login, String password);
    User register(String login, String password);
    List<Tour> getMyTours(User user);

}
