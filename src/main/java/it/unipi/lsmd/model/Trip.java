package it.unipi.lsmd.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Trip {

    private String title;
    private String description;
    private String destination;
    private double price;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private ArrayList<String> tags;
    private ArrayList<DailySchedule> itinerary;
    private ArrayList<String> whatsIncluded;
    private ArrayList<String> whatsNotIncluded;
    private String info;
    private String img;
    private Boolean deleted;

    public Trip(){
        tags = new ArrayList<String>();
        itinerary = new ArrayList<DailySchedule>();
        whatsIncluded = new ArrayList<String>();
        whatsNotIncluded = new ArrayList<String>();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

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

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<DailySchedule> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<DailySchedule> itinerary) {
        this.itinerary = itinerary;
    }

    public ArrayList<String> getWhatsIncluded() {
        return whatsIncluded;
    }

    public void setWhatsIncluded(ArrayList<String> whatsIncluded) {
        this.whatsIncluded = whatsIncluded;
    }

    public ArrayList<String> getWhatsNotIncluded() {
        return whatsNotIncluded;
    }

    public void setWhatsNotIncluded(ArrayList<String> whatsNotIncluded) {
        this.whatsNotIncluded = whatsNotIncluded;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", destination='" + destination + '\'' +
                ", price=" + price +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", tags=" + tags +
                ", itinerary=" + itinerary +
                ", whatsIncluded=" + whatsIncluded +
                ", whatsNotIncluded=" + whatsNotIncluded +
                ", info='" + info + '\'' +
                '}';
    }

    public void addItinerary(DailySchedule itinerary) {
        this.itinerary.add(itinerary);
    }
}
