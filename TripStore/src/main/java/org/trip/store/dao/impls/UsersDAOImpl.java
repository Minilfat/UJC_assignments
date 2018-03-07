package org.trip.store.dao.impls;


import org.apache.log4j.Logger;

import org.trip.store.dao.ifaces.UserDAO;
import org.trip.store.models.User;
import org.trip.store.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UsersDAOImpl implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UsersDAOImpl.class);

    @Override
    public User getById(Long id) {

        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?;";
        LOGGER.info("Get user with id = " + id);

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setLong(1,id);
            ResultSet res = query.executeQuery();

            if (!res.next())
                LOGGER.info("No such user with id " + id);
            else
                user  = new User(id, res.getString("login"), res.getString("password"));

            res.close();

        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }


    @Override
    public Long insert(User e) {

        Long id = null;
        String sql = "INSERT INTO users (login, password) VALUES (?, ?) RETURNING id;";
        LOGGER.info("New user adding");

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setString(1, e.getLogin());
            query.setString(2, e.getPassword());

            ResultSet res = query.executeQuery();


            if (res.next()) id = res.getLong(1);
            if (id != null) e.setId(id);
            res.close();

        } catch (SQLException ex) {
            LOGGER.error(ex);
        }


        return id;

    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?;";
        LOGGER.info("Get user with login = " + login);

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setString(1, login);
            query.setString(2, password);
            ResultSet res = query.executeQuery();

            if (!res.next())
                LOGGER.info("No such user with login " + login);
            else
                user = new User(res.getLong("id"),
                                res.getString("login"),
                                res.getString("password"));

            res.close();

        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return user;
    }

    @Override
    public boolean isUnique(String login) {
        boolean f = false;
        String sql = "SELECT id FROM users WHERE login = ?;";
        LOGGER.info("Search user for login = " + login);

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setString(1, login);
            try(ResultSet res = query.executeQuery()) {
                return !res.next();
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            return true;
        }

    }
}
