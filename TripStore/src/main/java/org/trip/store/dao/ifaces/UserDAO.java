package org.trip.store.dao.ifaces;

import org.trip.store.models.User;


public interface UserDAO  extends DAO<Long, User> {

    User getByLoginAndPassword(String login, String password);
    boolean isUnique(String login);

}
