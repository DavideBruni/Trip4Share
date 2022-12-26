package it.unipi.lsmd.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TripDetailsDTO {

    private String id;
    private String title;
    private String description;
    private String destination;
    private double price;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private List<String> tags;
    private List<DailyScheduleDTO> itinerary;
    private List<String> whatsIncluded;
    private List<String> whatsNotIncluded;
    private String info;
    private String img;

    private int like_counter;


    public TripDetailsDTO(){
        tags = new ArrayList<String>();
        itinerary = new ArrayList<DailyScheduleDTO>();
        whatsIncluded = new ArrayList<String>();
        whatsNotIncluded = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<DailyScheduleDTO> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<DailyScheduleDTO> itinerary) {
        this.itinerary = itinerary;
    }

    public void addItinerary(DailyScheduleDTO itinerary){
        this.itinerary.add(itinerary);
    }

    public List<String> getWhatsIncluded() {
        return whatsIncluded;
    }

    public void setWhatsIncluded(List<String> whatsIncluded) {
        this.whatsIncluded = whatsIncluded;
    }

    public List<String> getWhatsNotIncluded() {
        return whatsNotIncluded;
    }

    public void setWhatsNotIncluded(List<String> whatsNotIncluded) {
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

    public int getLike_counter() {
        return like_counter;
    }

    public void setLike_counter(int like_counter) {
        this.like_counter = like_counter;
    }

    @Override
    public String toString() {
        return "TripDetailsDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
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
                ", img='" + img + '\'' +
                ", like_counter=" + like_counter +
                '}';
    }
}
