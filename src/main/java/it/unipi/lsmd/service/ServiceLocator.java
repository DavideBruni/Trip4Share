package it.unipi.lsmd.service;

import it.unipi.lsmd.service.impl.TripServiceImpl;

public class ServiceLocator {
    public static TripService getTripService(){return new TripServiceImpl();}
}
