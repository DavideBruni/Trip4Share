package it.unipi.lsmd.config;

import it.unipi.lsmd.dao.base.BaseDAOMongo;
import it.unipi.lsmd.dao.base.BaseDAORedis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppServletContextListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        BaseDAORedis.initPool();
        BaseDAOMongo.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        BaseDAORedis.closePool();
    }


}