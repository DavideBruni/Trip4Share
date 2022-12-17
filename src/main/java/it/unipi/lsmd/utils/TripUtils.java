package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.model.Admin;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.model.User;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public interface TripUtils {
    static Trip tripFromDocument(Document result){
        Trip trip = new Trip();
        trip.setDestination(result.getString("destination"));
        trip.setDepartureDate(convertToLocalDateViaInstant(result.getDate("departureDate")));
        trip.setDepartureDate(convertToLocalDateViaInstant(result.getDate("returnDate")));
        // trip.setImg(result.getString("imgUrl"));
        trip.setTitle(result.getString("title"));
        return trip;
    }

    static TripHomeDTO parseTrip(Trip t){
        TripHomeDTO tDTO= new TripHomeDTO();
        tDTO.setDestination(t.getDestination());
        tDTO.setTitle(t.getTitle());
        tDTO.setDeleted(t.getDeleted());
        tDTO.setDepartureDate(t.getDepartureDate());
        tDTO.setReturnDate(t.getReturnDate());
        tDTO.setImgUrl(t.getImg());
        return tDTO;
    }

    static Trip tripDetailsFromDocument(Document result){
        return null;
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
