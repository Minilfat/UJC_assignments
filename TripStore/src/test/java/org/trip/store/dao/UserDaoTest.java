package org.trip.store.dao;


import org.apache.log4j.Logger;
import org.junit.*;
import org.trip.store.dao.ifaces.UserDAO;
import org.trip.store.dao.impls.UsersDAOImpl;
import org.trip.store.models.User;
import org.trip.store.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class UserDaoTest {


    UserDAO userDAO = new UsersDAOImpl();
    private static Logger LOG = Logger.getLogger(UserDaoTest.class);


    // тестирование запросов к БД
    // не учитывая всемозможные констрайнты типа foreign keys

    // запросы к тестовой postgresql базе
    // в которой должна существовать таблица

    //    CREATE TABLE users
    //            (
    //                    id       SERIAL NOT NULL
    //                    CONSTRAINT users_pkey
    //                    PRIMARY KEY,
    //                    login    VARCHAR(255),
    //    password VARCHAR(255)
    //);

    @Before
    public void setUp() throws SQLException {
        LOG.info("Init db connection pool");
        ConnectionFactory.init();
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM users;");
        s.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");
        s.close();
        c.close();
        LOG.info("\"users\" table was cleaned up");
    }

    @After
    public void tearDown() throws SQLException {

        // clean db tables
        Connection c = ConnectionFactory.get();
        Statement s = c.createStatement();
        s.executeUpdate("DELETE FROM users;");
        s.close();
        c.close();
        LOG.info("\"users\" table was cleaned up");
        ConnectionFactory.close();
    }

    @Test
    public void Run() {

        User testUser = new User("testdbuser1", "password");
        Long id = userDAO.insert(testUser);

        assertEquals(1L, testUser.getId());

        User retrievedUser = userDAO.getById(id);
        User retrievedUser2 = userDAO.getByLoginAndPassword(testUser.getLogin(), testUser.getPassword());
        assertEquals(testUser, retrievedUser);
        assertEquals(testUser, retrievedUser2);
        assertEquals(null, userDAO.getByLoginAndPassword("nouser",""));

        assertEquals(true, userDAO.isUnique("newuser"));
        assertEquals(false, userDAO.isUnique("testdbuser1"));



    }

}