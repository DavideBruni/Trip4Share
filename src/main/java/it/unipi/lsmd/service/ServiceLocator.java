package it.unipi.lsmd.service;

import it.unipi.lsmd.service.impl.TripServiceImpl;
import it.unipi.lsmd.service.impl.UserServiceImpl;

public class ServiceLocator {
    public static TripService getTripService(){return new TripServiceImpl();}
    public static UserService getUserService(){return new UserServiceImpl();}
}
