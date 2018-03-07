package org.trip.store.controllers.listeners;

import org.trip.store.utils.ConnectionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class AppStartListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();

        if (!ConnectionFactory.init()) {
            context.setAttribute("dberror", "internal server error");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // db close
        ConnectionFactory.close();
    }
}
