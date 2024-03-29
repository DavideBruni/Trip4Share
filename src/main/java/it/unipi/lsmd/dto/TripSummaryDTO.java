package it.unipi.lsmd.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripSummaryDTO {

    private String id;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private boolean deleted;
    private String title;
    private int like_counter;
    private String organizer;

    public TripSummaryDTO(){
        deleted=false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public int getLike_counter() {
        return like_counter;
    }

    public void setLike_counter(int like_counter) {
        this.like_counter = like_counter;
    }


    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }


    @Override
    public String toString() {
        return "TripSummaryDTO{" +
                "id='" + id + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", deleted=" + deleted +
                ", title='" + title + '\'' +
                ", like_counter=" + like_counter +
                ", organizer=" + organizer +
                '}';
    }
}
