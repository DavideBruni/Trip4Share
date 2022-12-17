package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.Review;
import it.unipi.lsmd.model.Trip;
import org.bson.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TripUtils {

    public static DailySchedule dailyScheduleFromDocument(Document result){
        DailySchedule dailySchedule = new DailySchedule();
        dailySchedule.setTitle(result.getString("title"));
        dailySchedule.setDescription(result.getString("description"));
        dailySchedule.setSubtitle(result.getString("subtitle"));
        dailySchedule.setDay(result.getInteger("day"));

        return dailySchedule;
    }

    public static DailyScheduleDTO dailyScheduleModelToDTO(DailySchedule dailySchedule){

        DailyScheduleDTO dailyScheduleDTO = new DailyScheduleDTO();
        dailyScheduleDTO.setTitle(dailySchedule.getTitle());
        dailyScheduleDTO.setSubtitle(dailySchedule.getSubtitle());
        dailyScheduleDTO.setDescription(dailySchedule.getDescription());
        dailyScheduleDTO.setDay(dailySchedule.getDay());

        return dailyScheduleDTO;
    }




    public static Trip tripFromDocument(Document result){

        if(result == null){
            return null;
        }

        Trip trip = new Trip();

        trip.setTitle(result.getString("title"));
        trip.setDescription(result.getString("description"));
        trip.setDestination(result.getString("destination"));

        Integer price = result.getInteger("price");
        trip.setPrice(price.floatValue());

        String departure_date = result.getString("departureDate");
        trip.setDepartureDate(LocalDate.parse(departure_date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        String return_date = result.getString("returnDate");
        trip.setReturnDate(LocalDate.parse(return_date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        try{
            ArrayList<String> tags = result.get("tags", ArrayList.class);
            trip.setTags(tags);
        } catch (Exception e) {
            // TODO - add something?
        }


        try{
            ArrayList<Document> itinerary = result.get("itinerary", ArrayList.class);
            for(Document i : itinerary){
                trip.addItinerary(dailyScheduleFromDocument(i));
            }
        } catch (Exception e) { }

        try{
            ArrayList<String> whatsIncluded = result.get("compreso", ArrayList.class);
            trip.setWhatsIncluded(whatsIncluded);
        } catch (Exception e) { }

        try{
            ArrayList<String> whatsNotIncluded = result.get("nonCompreso", ArrayList.class);
            trip.setWhatsNotIncluded(whatsNotIncluded);
        } catch (Exception e) { }

        trip.setInfo(result.getString("info"));

        return trip;
    }

    public static TripDTO tripModelToDTO(Trip trip) {

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
