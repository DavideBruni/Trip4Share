package org.example.model;

import java.util.Date;
import java.util.List;

public class Trip {
    private String title;
    private String description;
    private List<String> tags;

    private List<Itinerary> itinerary;
    private Date departureDate;
    private Date returnDate;
    private int price;

    private List<String> compreso;

    private List<String> nonCompreso;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private String destination;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Itinerary> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<Itinerary> itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getCompreso() {
        return compreso;
    }

    public void setCompreso(List<String> compreso) {
        this.compreso = compreso;
    }

    public List<String> getNonCompreso() {
        return nonCompreso;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", itinerary=" + itinerary +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", price=" + price +
                ", compreso=" + compreso +
                ", nonCompreso=" + nonCompreso +
                ", info='" + info + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public void setNonCompreso(List<String> nonCompreso) {
        this.nonCompreso = nonCompreso;
    }
}
