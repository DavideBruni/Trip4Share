package it.unipi.lsmd.dao.neo4j.exceptions;

public class Neo4jException extends Exception{
    public Neo4jException() {
        super();
    }

    public Neo4jException(String message) {
        super(message);
    }
}
