package it.unipi.lsmd.dto;

public class DestinationsDTO {
    private String destination;
    private int num_like;
    private int num_trips;

    public DestinationsDTO(String destination, Integer likes, Integer trips) {
        this.destination=destination;
        num_like=likes;
        num_trips=trips;
    }

    public DestinationsDTO(String destination, int num_like) {
        this.destination = destination;
        this.num_like = num_like;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNum_like() {
        return num_like;
    }

    public void setNum_like(int num_like) {
        this.num_like = num_like;
    }

    public int getNum_trips() {
        return num_trips;
    }

    public void setNum_trips(int num_trips) {
        this.num_trips = num_trips;
    }
}
