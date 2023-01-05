package it.unipi.lsmd.model;

import it.unipi.lsmd.model.enums.Status;
import org.javatuples.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Trip {

    private String id;
    private String title;
    private String description;
    private String destination;
    private double price;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private List<Tag> tags;
    private List<DailySchedule> itinerary;
    private List<String> whatsIncluded;
    private List<String> whatsNotIncluded;
    private String info;
    private String img;
    private Boolean deleted;
    private int like_counter;
    private LocalDateTime last_modified;
    private RegisteredUser organizer;
    private List<Pair<RegisteredUser, Status>> joiners;

    public Trip(){
        tags = new ArrayList<>();
        itinerary = new ArrayList<>();
        whatsIncluded = new ArrayList<>();
        whatsNotIncluded = new ArrayList<>();
        joiners = new ArrayList<>();
    }

    public List<Pair<RegisteredUser, Status>> getJoiners() {
        return joiners;
    }

    public void setJoiners(List<Pair<RegisteredUser,Status>> joiners){
        this.joiners=joiners;
    }
    public String getId(){return this.id;}
    public void setId(String id){this.id = id;}

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

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getTagsAsStrings(){
        List<String> tags_string = new ArrayList<>();
        for(Tag t : tags){
            tags_string.add(t.getTag());
        }
        return tags_string;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<DailySchedule> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<DailySchedule> itinerary) {
        this.itinerary = itinerary;
    }

    public void addItinerary(DailySchedule itinerary) {
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


    public int getLike_counter() {
        return like_counter;
    }

    public void setLike_counter(int like_counter) {
        this.like_counter = like_counter;
    }

    public LocalDateTime getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }

    @Override
    public String toString() {
        return "Trip{" +
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
                ", deleted=" + deleted +
                ", like_counter=" + like_counter +
                ", last_modified=" + last_modified +
                '}';
    }


    public void setOrganizer(RegisteredUser organizer) {
        this.organizer = organizer;
    }

    public RegisteredUser getOrganizer() {
        return organizer;
    }
}
