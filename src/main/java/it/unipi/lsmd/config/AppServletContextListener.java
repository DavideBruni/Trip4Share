package it.unipi.lsmd.config;

import it.unipi.lsmd.dao.base.BaseDAOMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppServletContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        BaseDAOMongo.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }


}