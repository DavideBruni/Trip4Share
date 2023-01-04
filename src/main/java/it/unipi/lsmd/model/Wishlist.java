package it.unipi.lsmd.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {

    List<Trip> wishlist;

    public Wishlist() {
        this.wishlist = new ArrayList<Trip>();
    }

    public List<Trip> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Trip> wishlist) {
        this.wishlist = wishlist;
    }

    public void addToWishlist(Trip trip){
        wishlist.add(trip);
    }
}
