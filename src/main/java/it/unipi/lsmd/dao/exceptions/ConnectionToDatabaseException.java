package it.unipi.lsmd.dao.exceptions;

public class ConnectionToDatabaseException extends Exception {
    public ConnectionToDatabaseException() {
        super();
    }

    public ConnectionToDatabaseException(String message) {
        super(message);
    }
}
