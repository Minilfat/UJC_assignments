package org.trip.store.utils;



import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class ConnectionFactory {

    private final static String DB_PROPS = "db.properties";
    private static PGPoolingDataSource connectionPool;
    private final static Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

    public static boolean init() {

        boolean res;

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream resourceStream = loader.getResourceAsStream(DB_PROPS)) {
            Properties props = new Properties();
            props.load(resourceStream);
            connectionPool = new PGPoolingDataSource();
            connectionPool.setDataSourceName(props.getProperty("db.dataSourceName"));
            connectionPool.setServerName(props.getProperty("db.server"));
            connectionPool.setPortNumber(Integer.parseInt(props.getProperty("db.port")));
            connectionPool.setDatabaseName(props.getProperty("db.database"));
            connectionPool.setUser(props.getProperty("db.user"));
            connectionPool.setPassword(props.getProperty("db.password"));
            connectionPool.setMaxConnections(Integer.parseInt(props.getProperty("db.maxConnection")));

            try {
                Connection test = connectionPool.getConnection();
                DatabaseMetaData dataTest = test.getMetaData();
                int i = dataTest.getDatabaseMajorVersion(); //.getTables(null, null, "%", null);
                test.close();

                LOGGER.info("DBMS version: " + i);
                res = true;

            } catch (SQLException e) {
                LOGGER.error(e);
                res = false;
            }

        } catch (IOException e) {
            LOGGER.error("Could not find properties file");
            LOGGER.error(e);
            res = false;
        }

        return res;
    }


    public static Connection get() throws SQLException {
        return connectionPool.getConnection();
    }

    public static void close() {
        LOGGER.info("Closing database connection pool");
        connectionPool.close();
    }




}
