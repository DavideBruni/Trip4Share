package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.DailySchedule;

import java.time.LocalDate;
import java.util.ArrayList;

public class TripDTO {

    private String title;
    private String description;
    private String destination;
    private double price;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private ArrayList<String> tags;
    private ArrayList<DailyScheduleDTO> itinerary;
    private ArrayList<String> whatsIncluded;
    private ArrayList<String> whatsNotIncluded;
    private String info;
    private String img;


    public TripDTO(){
        tags = new ArrayList<String>();
        itinerary = new ArrayList<DailyScheduleDTO>();
        whatsIncluded = new ArrayList<String>();
        whatsNotIncluded = new ArrayList<String>();
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

    public ArrayList<DailyScheduleDTO> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<DailyScheduleDTO> itinerary) {
        this.itinerary = itinerary;
    }

    public void addItinerary(DailyScheduleDTO itinerary){
        this.itinerary.add(itinerary);
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // TODO - add toString method
}
