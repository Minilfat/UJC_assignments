package org.trip.store.dao;

import org.apache.log4j.Logger;
import org.junit.*;
import org.trip.store.dao.ifaces.TourDAO;
import org.trip.store.dao.impls.TourDAOImpl;
import org.trip.store.models.Tour;
import org.trip.store.utils.ConnectionFactory;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TourDaoTest {

    private static Logger LOG = Logger.getLogger(TourDaoTest.class);
    private TourDAO tourDAO = new TourDAOImpl();


    // тестирование запросов к БД
    // не учитывая всемозможные констрайнты типа foreign keys

    // запросы к тестовой postgresql базе
    // в которой должна существовать таблица

    //CREATE TABLE tours
    //(
    //  id     SERIAL NOT NULL
    //    CONSTRAINT tours_pkey
    //    PRIMARY KEY,
    //  price  DOUBLE PRECISION,
    //  "from" DATE,
    //  "to"   DATE,
    //  city   VARCHAR(255)
    //);



    @Before
    public void setUp() throws SQLException {
        LOG.info("Init db connection pool");
        ConnectionFactory.init();
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM tours;");
        s.execute("ALTER SEQUENCE tours_id_seq RESTART WITH 1");
        s.close();
        c.close();
        LOG.info("\"tours\" table was cleaned up");
    }

    @After
    public void tearDown() throws SQLException {
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM tours;");
        LOG.info("\"users\" table was cleaned up");
        s.close();
        c.close();
        ConnectionFactory.close();
    }


    @Test
    public void Run() {

        List<Tour> tours = new ArrayList<>();

        LocalDate from = LocalDate.now();
        LocalDate to =  LocalDate.of(2018, 4, 12);
        Tour testTour0 = new Tour(20.0, "TestCity0", from, to);
        Tour testTour1 = new Tour(20.0, "TestCity1", from, to);
        Tour testTour2 = new Tour(20.0, "TestCity2", from, to);
        tours.add(testTour0);
        tours.add(testTour1);
        tours.add(testTour2);


        Long id1 = tourDAO.insert(testTour0);
        Tour retrievedTour = tourDAO.getById(id1);
        assertEquals(testTour0, retrievedTour);

        tourDAO.insert(testTour1);
        tourDAO.insert(testTour2);

        List<Tour> retrievedTours = tourDAO.getAll();

        assertEquals(tours, retrievedTours);

    }
}