package org.trip.store.dao.impls;

import org.apache.log4j.Logger;
import org.trip.store.models.Tour;
import org.trip.store.utils.ConnectionFactory;
import org.trip.store.dao.ifaces.UserToursDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserToursDAOImpl implements UserToursDAO {

    private static final Logger LOGGER = Logger.getLogger(UsersDAOImpl.class);

    @Override
    public List<Long> getUserTours(Long userId) {
        List<Long> toursIds = new ArrayList<>();

        String sql = "SELECT tour FROM usertours WHERE owner = ?;";
        LOGGER.info("User id " + userId + " tour list");

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setLong(1, userId);
            ResultSet res = query.executeQuery();

            while (res.next()) {
                toursIds.add(res.getLong("tour"));
            }
            res.close();

        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return toursIds;
    }

    @Override
    public void insert(Long owner, Long tour) {

        String sql = "INSERT INTO usertours (owner, tour) VALUES (?, ?);";
        LOGGER.info("New tour adding for user: " + owner + " tour id: " + tour);

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setLong(1, owner);
            query.setLong(2, tour);
            query.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.error(ex);
        }
    }

    @Override
    public List<Long> bookedTours() {
        List<Long> toursIds = new ArrayList<>();

        String sql = "SELECT DISTINCT tour FROM usertours;";
        LOGGER.info("Getting list of booked tours");

        try (Connection connection = ConnectionFactory.get();
             Statement query = connection.createStatement();
             ResultSet res = query.executeQuery(sql)) {

            while (res.next()) {
                toursIds.add(res.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toursIds;
    }
}
