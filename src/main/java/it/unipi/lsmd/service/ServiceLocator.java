package it.unipi.lsmd.service;

import it.unipi.lsmd.service.impl.TripServiceImpl;
import it.unipi.lsmd.service.impl.UserServiceImpl;
import it.unipi.lsmd.service.impl.WishlistServiceImpl;

public class ServiceLocator {
    public static TripService getTripService(){return new TripServiceImpl();}
    public static UserService getUserService(){return new UserServiceImpl();}
    public static WishlistService getWishlistService(){return new WishlistServiceImpl();}


}
