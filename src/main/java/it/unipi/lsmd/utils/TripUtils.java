package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.model.*;
import org.bson.Document;
import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.Trip;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public interface TripUtils {

    static TripHomeDTO parseTrip(Trip t){
        TripHomeDTO tDTO= new TripHomeDTO();
        tDTO.setDestination(t.getDestination());
        tDTO.setTitle(t.getTitle());
        tDTO.setDepartureDate(t.getDepartureDate());
        tDTO.setReturnDate(t.getReturnDate());
        tDTO.setImgUrl(t.getImg());
        return tDTO;
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    static DailySchedule dailyScheduleFromDocument(Document result){
        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setTitle(result.getString("title"));
        dailySchedule.setDescription(result.getString("description"));
        dailySchedule.setSubtitle(result.getString("subtitle"));
        dailySchedule.setDay(result.getInteger("day"));
        return dailySchedule;
    }

    static DailyScheduleDTO dailyScheduleModelToDTO(DailySchedule dailySchedule){
        DailyScheduleDTO dailyScheduleDTO = new DailyScheduleDTO();
        dailyScheduleDTO.setTitle(dailySchedule.getTitle());
        dailyScheduleDTO.setSubtitle(dailySchedule.getSubtitle());
        dailyScheduleDTO.setDescription(dailySchedule.getDescription());
        dailyScheduleDTO.setDay(dailySchedule.getDay());
        return dailyScheduleDTO;
    }


    static Trip tripFromDocument(Document result){

        if(result == null){
            return null;
        }
        Trip trip = new Trip();
        trip.setTitle(result.getString("title"));
        trip.setDescription(result.getString("description"));
        trip.setDestination(result.getString("destination"));
        //TODO
        // trip.setImg(result.getString("imgUrl"));
        if(result.getInteger("price")!=null) {
            Integer price = result.getInteger("price");
            trip.setPrice(price);
        }
        trip.setDepartureDate(convertToLocalDateViaInstant(result.getDate("departureDate")));
        trip.setReturnDate(convertToLocalDateViaInstant(result.getDate("returnDate")));
        ArrayList<String> tags = result.get("tags", ArrayList.class);
        trip.setTags(tags);

        ArrayList<Document> itinerary = result.get("itinerary", ArrayList.class);
        if(itinerary!=null)
            for(Document i : itinerary)
                trip.addItinerary(dailyScheduleFromDocument(i));


        ArrayList<String> whatsIncluded = result.get("compreso", ArrayList.class);
        trip.setWhatsIncluded(whatsIncluded);
        ArrayList<String> whatsNotIncluded = result.get("nonCompreso", ArrayList.class);
        trip.setWhatsNotIncluded(whatsNotIncluded);
        trip.setInfo(result.getString("info"));

        return trip;
    }

    static TripDTO tripModelToDTO(Trip trip) {

        if(trip == null){
            return null;
        }

        TripDTO tripDTO = new TripDTO();

        tripDTO.setTitle(trip.getTitle());
        tripDTO.setDescription(trip.getDescription());
        tripDTO.setPrice(trip.getPrice());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setTags(trip.getTags());
        tripDTO.setWhatsIncluded(trip.getWhatsIncluded());
        tripDTO.setWhatsNotIncluded(trip.getWhatsNotIncluded());

        try{
            for(DailySchedule dailySchedule : trip.getItinerary()){
                tripDTO.addItinerary(dailyScheduleModelToDTO(dailySchedule));
            }
        }catch (NullPointerException e){}

        return tripDTO;
    }
}
