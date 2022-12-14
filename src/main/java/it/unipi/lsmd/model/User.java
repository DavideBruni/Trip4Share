package it.unipi.lsmd.model;


public abstract class User {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;    // TODO - useless?
    private String type;        // TODO - useless?

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
