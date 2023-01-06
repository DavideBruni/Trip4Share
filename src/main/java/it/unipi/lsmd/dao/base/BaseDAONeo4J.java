package it.unipi.lsmd.dao.base;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public abstract class BaseDAONeo4J implements AutoCloseable{

    private static final String NEO4J_HOST = "172.16.5.21";
    private static final Integer NEO4J_PORT = 7687;
    private static final String NEO4J_USERNAME = "neo4j";
    private static final String NEO4J_PSW = "Trip4Share";
    protected static Driver driver = null;



    protected static Driver getConnection(){
        if(driver == null) {
            String uri = "bolt://" + NEO4J_HOST + ":" + NEO4J_PORT;
            driver = GraphDatabase.driver(uri, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PSW));
        }
        return driver;
    }

    public void close() throws Exception {
        getConnection().close();
    }


}
