package it.unipi.lsmd.utils;

import it.unipi.lsmd.dto.TripSummaryDTO;
import org.bson.Document;
import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.Trip;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface TripUtils {

    static TripSummaryDTO parseTrip(Trip t){
        TripSummaryDTO tDTO= new TripSummaryDTO();
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

        trip.setId(result.get("_id").toString());
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

        ArrayList<String> whatsIncluded = result.get("whatsIncluded", ArrayList.class);
        trip.setWhatsIncluded(whatsIncluded);
        ArrayList<String> whatsNotIncluded = result.get("whatsNotIncluded", ArrayList.class);
        trip.setWhatsNotIncluded(whatsNotIncluded);
        trip.setInfo(result.getString("info"));

        return trip;
    }

    static Trip tripFromMap(Map<String, Object> map){
        Trip trip = new Trip();
        trip.setTitle((String) map.get("title"));
        trip.setDestination((String) map.get("destination"));
        trip.setDepartureDate(LocalDate.parse((String) map.get("departureDate")));
        trip.setReturnDate(LocalDate.parse((String) map.get("returnDate")));

        return trip;
    }


    static TripDetailsDTO tripModelToDetailedDTO(Trip trip) {

        if(trip == null){
            return null;
        }

        TripDetailsDTO tripDTO = new TripDetailsDTO();

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

    static TripSummaryDTO tripSummaryDTOFromModel(Trip trip){
        if(trip == null){
            return null;
        }

        TripSummaryDTO tripDTO = new TripSummaryDTO();
        tripDTO.setTitle(trip.getTitle());
        tripDTO.setDestination(trip.getDestination());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());

        return tripDTO;
    }




    // TODO - create method TripSummaryDTO tripModelToSummaryDTO(Trip trip) {

    static Trip destinationFromDocument(Document doc) {
        Trip t = new Trip();
        t.setDestination(doc.getString("_id"));
        try {
            double agg = doc.getDouble("agg");
            t.setPrice(agg);
        }catch (Exception exc){
            t.setPrice(0.00);
        }
        return t;
    }

    static Trip tripFromRecord(Record r){
        Trip t = new Trip();
        t.setDestination(r.get("t.destination").asString());
        t.setTitle(r.get("t.title").asString());
        t.setImg(r.get("t.imgUrl").asString());
        try {
            t.setDeleted(r.get("t.deleted").asBoolean());
            t.setDepartureDate(r.get("t.departureDate").asLocalDate());
            t.setReturnDate(r.get("t.returnDate").asLocalDate());
        }catch (Uncoercible uncoercible){
            t.setDeleted(Boolean.FALSE);
            t.setDepartureDate(null);
            t.setReturnDate(null);
        }finally {
            return t;
        }
    }
}
