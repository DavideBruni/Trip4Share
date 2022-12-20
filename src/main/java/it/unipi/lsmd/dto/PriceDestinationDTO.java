package it.unipi.lsmd.dto;

public class PriceDestinationDTO {
    private String destination;
    private double price;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
