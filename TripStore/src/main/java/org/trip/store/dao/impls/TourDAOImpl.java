package org.trip.store.dao.impls;


import org.apache.log4j.Logger;
import org.trip.store.dao.ifaces.TourDAO;
import org.trip.store.models.Tour;
import org.trip.store.utils.ConnectionFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAOImpl implements TourDAO {

    private static final Logger LOGGER = Logger.getLogger(TourDAOImpl.class);

    @Override
    public Tour getById(Long key) {
        Tour tour = null;
        String sql = "SELECT * FROM tours WHERE id = ?;";
        LOGGER.info("Get tour with id = " + key);

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {

            query.setLong(1,key);
            ResultSet res = query.executeQuery();

            if (!res.next()) {
                LOGGER.info("No such tour with id " + key);
            } else {

                tour = new Tour(res.getLong("id"),
                        res.getDouble("price"),
                        res.getString("city"),
                        res.getDate("from").toLocalDate(),
                        res.getDate("to").toLocalDate()
                );
            }

            res.close();

        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return tour;
    }

    @Override
    public List<Tour> getAll() {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT * FROM tours;";

        try (Connection connection = ConnectionFactory.get();
             Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery(sql)) {

            while (res.next()) {

                tours.add(
                    new Tour(res.getLong("id"),
                            res.getDouble("price"),
                            res.getString("city"),
                            res.getDate("from").toLocalDate(),
                            res.getDate("to").toLocalDate())
                );

            }


        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return tours;
    }

    public Long insert( Tour e) {

        Long id = null;
        String sql = "INSERT INTO tours (city, price, \"from\", \"to\") VALUES (?, ?, ?, ?) RETURNING id;";
        LOGGER.info("New tour adding");

        try (Connection connection = ConnectionFactory.get();
             PreparedStatement query = connection.prepareStatement(sql)) {


            Date from = Date.valueOf(e.getFrom());
            Date to   = Date.valueOf(e.getTo());

            query.setString(1, e.getCity());
            query.setDouble(2, e.getPrice());
            query.setDate(3, from);
            query.setDate(4, to);


            ResultSet res = query.executeQuery();

            if (res.next()) id = res.getLong(1);
            if (id != null) e.setId(id);

            res.close();

        } catch (SQLException ex) {
            LOGGER.error(ex);
        }


        return id;

    }
}
