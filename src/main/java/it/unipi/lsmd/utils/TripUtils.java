package it.unipi.lsmd.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.utils.exceptions.IncompleteTripException;
import org.bson.Document;
import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.Trip;
import org.json.JSONObject;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.value.Uncoercible;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;


public interface TripUtils {

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

        trip.setId(result.getObjectId("_id").toHexString());
        trip.setTitle(result.getString("title"));
        trip.setDescription(result.getString("description"));
        trip.setDestination(result.getString("destination").toUpperCase());
        trip.setImg(result.getString("imgUrl"));

        try{
            trip.setLike_counter(result.getInteger("likes"));
        }catch (NullPointerException e){ }

        try{
            trip.setPrice(result.getInteger("price"));
        }catch (NullPointerException e){ }

        trip.setDepartureDate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("departureDate")));
        trip.setReturnDate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("returnDate")));

        try{
            trip.setLast_modified(LocalDateTimeAdapter.convertToLocalDateTimeViaInstant(result.getDate("last_modified")));
        }catch (NullPointerException e){ }

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

    static TripDetailsDTO tripModelToDetailedDTO(Trip trip) {

        if(trip == null){
            return null;
        }

        TripDetailsDTO tripDTO = new TripDetailsDTO();

        tripDTO.setId(trip.getId());
        tripDTO.setTitle(trip.getTitle());
        tripDTO.setId(trip.getId());
        tripDTO.setDestination(trip.getDestination());
        tripDTO.setDescription(trip.getDescription());
        tripDTO.setPrice(trip.getPrice());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setTags(trip.getTags());
        tripDTO.setWhatsIncluded(trip.getWhatsIncluded());
        tripDTO.setWhatsNotIncluded(trip.getWhatsNotIncluded());
        tripDTO.setLike_counter(trip.getLike_counter());
        tripDTO.setOrganizer(trip.getOrganizer());
        tripDTO.setLast_modified(trip.getLast_modified());

        try{
            for(DailySchedule dailySchedule : trip.getItinerary()){
                tripDTO.addItinerary(dailyScheduleModelToDTO(dailySchedule));
            }
        }catch (NullPointerException e){}

        return tripDTO;
    }

    // TODO - change name in tripModelToSummaryDTO
    static TripSummaryDTO tripSummaryDTOFromModel(Trip trip){

        if(trip == null){
            return null;
        }

        TripSummaryDTO tripDTO = new TripSummaryDTO();
        tripDTO.setId(trip.getId());
        tripDTO.setTitle(trip.getTitle());
        tripDTO.setDestination(trip.getDestination());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setLike_counter(trip.getLike_counter());
        tripDTO.setImgUrl(trip.getImg());
        tripDTO.setOrganizer(trip.getOrganizer());    // TODO - add in a trycatch?
        tripDTO.setLast_modified(trip.getLast_modified());

        return tripDTO;
    }

    static TripSummaryDTO tripSummaryFromTripDetails(TripDetailsDTO trip){
        TripSummaryDTO tripDTO = new TripSummaryDTO();

        tripDTO.setTitle(trip.getTitle());
        tripDTO.setId(trip.getId());
        tripDTO.setDestination(trip.getDestination());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setImgUrl(trip.getImg());
        tripDTO.setLike_counter(tripDTO.getLike_counter());
        tripDTO.setOrganizer(trip.getOrganizer());
        tripDTO.setLast_modified(trip.getLast_modified());

        return tripDTO;
    }

    static Trip tripFromTripSummary(TripSummaryDTO tripSummary){
        Trip trip = new Trip();

        trip.setId(tripSummary.getId());
        trip.setTitle(tripSummary.getTitle());
        trip.setId(tripSummary.getId());
        trip.setDestination(tripSummary.getDestination());
        trip.setDepartureDate(tripSummary.getDepartureDate());
        trip.setReturnDate(tripSummary.getReturnDate());
        trip.setLike_counter(tripSummary.getLike_counter());
        trip.setOrganizer(tripSummary.getOrganizer());
        trip.setLast_modified(tripSummary.getLast_modified());

        return trip;
    }

    static String tripToJSONString(Trip trip){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return gson.toJson(trip);
    }

    static Trip tripFromJSONString(String jsonString){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return gson.fromJson(jsonString, Trip.class);
    }

    
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
        Trip trip = new Trip();
        trip.setId(r.get("t._id").asString());
        trip.setDestination(r.get("t.destination").asString());
        trip.setTitle(r.get("t.title").asString());
        trip.setImg(r.get("t.imgUrl").asString());
        trip.setOrganizer(r.get("organizer").asString());


        try {
            trip.setDeleted(r.get("t.deleted").asBoolean());
            trip.setDepartureDate(r.get("t.departureDate").asLocalDate());
            trip.setReturnDate(r.get("t.returnDate").asLocalDate());
        }catch (Uncoercible uncoercible){
            trip.setDeleted(Boolean.FALSE);
            trip.setDepartureDate(null);
            trip.setReturnDate(null);
        }finally {
            return trip;
        }
    }

    static Document documentFromTrip(Trip t) throws IncompleteTripException {
        Document doc = new Document();
        IncompleteTripException iex = new IncompleteTripException();
        try{
            if(t.getDestination()==null)
                throw iex;
            if(t.getTitle()==null)
                throw iex;
            if(t.getPrice()==0)
                throw iex;
            if(t.getDepartureDate()==null)
                throw iex;
            if(t.getReturnDate()==null)
                throw iex;
        }catch (NullPointerException ne){
            throw iex;
        }
        doc.append("destination",t.getDestination());
        doc.append("title",t.getTitle());
        doc.append("departureDate",t.getDepartureDate());
        doc.append("returnDate",t.getReturnDate());
        doc.append("price",t.getPrice());
        if(t.getImg()!=null)
            doc.append("imgUrl",t.getImg());
        if(t.getInfo()!=null)
            doc.append("info",t.getInfo());
        if(t.getDescription()!=null)
            doc.append("description",t.getDescription());
        if(t.getTags()!=null && !t.getTags().isEmpty())
            doc.append("tags",t.getTags());
        if(t.getWhatsIncluded()!=null && !t.getWhatsIncluded().isEmpty())
            doc.append("whatsIncluded",t.getWhatsIncluded());
        if(t.getWhatsNotIncluded()!=null && !t.getWhatsNotIncluded().isEmpty())
            doc.append("whatsNotIncluded",t.getWhatsNotIncluded());
        if(t.getItinerary()!=null && !t.getItinerary().isEmpty())
            doc.append("itinerary",documentsFromItinerary(t.getItinerary()));

        return doc;
    }

    static List<Document> documentsFromItinerary(List<DailySchedule> itinerary) throws IncompleteTripException {
        if(itinerary!=null){
            List<Document> retValue = new ArrayList();
            for(DailySchedule d : itinerary)
                retValue.add(documentFromDailySchedule(d));
            return retValue;
        }
        return null;
    }

    static Document documentFromDailySchedule(DailySchedule d) throws IncompleteTripException {
        try{
            Document doc = new Document();
            doc.append("day",d.getDay());
            doc.append("title",d.getTitle());
            if(d.getSubtitle()!=null)
                doc.append("subtitle",d.getSubtitle());
            if(d.getDescription()!=null)
                doc.append("description",d.getDescription());
            return doc;
        }catch(NullPointerException ne){
            throw new IncompleteTripException();
        }
    }

    static Trip tripModelFromTripDetailsDTO(TripDetailsDTO tripDetailsDTO) {
        Trip t = new Trip();
        t.setId(tripDetailsDTO.getId());
        t.setDepartureDate(tripDetailsDTO.getDepartureDate());
        t.setReturnDate(tripDetailsDTO.getReturnDate());
        t.setImg(tripDetailsDTO.getImg());
        t.setPrice(tripDetailsDTO.getPrice());
        t.setOrganizer(tripDetailsDTO.getOrganizer());
        t.setDescription(tripDetailsDTO.getDescription());
        t.setDestination(tripDetailsDTO.getDestination());
        t.setTitle(tripDetailsDTO.getTitle());
        t.setTags(tripDetailsDTO.getTags());
        t.setItinerary(itineraryDTOtoModel(tripDetailsDTO.getItinerary()));
        t.setWhatsIncluded(tripDetailsDTO.getWhatsIncluded());
        t.setWhatsNotIncluded(tripDetailsDTO.getWhatsNotIncluded());
        t.setInfo(tripDetailsDTO.getInfo());
        return t;
    }

    static List<DailySchedule> itineraryDTOtoModel(List<DailyScheduleDTO> itinerary) {
        List<DailySchedule> i = new ArrayList<>();
        if(itinerary!=null){
            for(DailyScheduleDTO d : itinerary){
                DailySchedule dailySchedule = new DailySchedule();
                dailySchedule.setTitle(d.getTitle());
                dailySchedule.setDay(d.getDay());
                dailySchedule.setSubtitle(d.getSubtitle());
                dailySchedule.setDescription(d.getDescription());
                i.add(dailySchedule);
            }
        }
        return i;
    }
}
