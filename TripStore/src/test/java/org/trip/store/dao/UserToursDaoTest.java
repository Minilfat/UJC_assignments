package org.trip.store.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.trip.store.dao.ifaces.UserToursDAO;
import org.trip.store.dao.impls.UserToursDAOImpl;
import org.trip.store.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;


import static org.junit.Assert.*;

public class UserToursDaoTest {

    private static Logger LOG = Logger.getLogger(TourDaoTest.class);
    private UserToursDAO userToursDAO = new UserToursDAOImpl();

    // тестирование запросов к БД
    // не учитывая всемозможные констрайнты типа foreign keys

    // запросы к тестовой postgresql базе
    // в которой должна существовать таблица

    // CREATE TABLE usertours
    //(
    //  owner SERIAL NOT NULL,
    //  tour  SERIAL NOT NULL
    //);

    @Before
    public void setUp() throws SQLException {

        LOG.info("Init db connection pool");
        ConnectionFactory.init();
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM usertours;");
        s.close();
        c.close();
        LOG.info("\"usertours\" table was cleaned up");

    }

    @After
    public void tearDown() throws SQLException {
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM usertours;");
        LOG.info("\"usertours\" table was cleaned up");
        s.close();
        c.close();
        ConnectionFactory.close();
    }

    @Test
    public void Run() {

        List<Long> owner1Tours = new ArrayList<>();
        List<Long> bookedTours = new ArrayList<>();


        Long owner1 = 1L;
        Long owner2 = 2L;
        Long tour1  = 1L;
        Long tour2  = 2L;

        owner1Tours.add(tour1);
        owner1Tours.add(tour2);
        bookedTours.add(1L);
        bookedTours.add(2L);

        userToursDAO.insert(owner1, tour1);
        userToursDAO.insert(owner1, tour2);
        userToursDAO.insert(owner2, tour2);

        List<Long> owner1ToursRetrieved = userToursDAO.getUserTours(owner1);

        assertEquals(owner1Tours, owner1ToursRetrieved);

        assertThat("List equality without order",
                bookedTours, containsInAnyOrder(userToursDAO.bookedTours().toArray()));


    }
}