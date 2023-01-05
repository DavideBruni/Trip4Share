package it.unipi.lsmd.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.TripSummaryDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.model.Tag;
import it.unipi.lsmd.utils.exceptions.IncompleteTripException;
import org.bson.Document;
import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.DailySchedule;
import it.unipi.lsmd.model.Trip;
import org.json.JSONObject;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.value.Uncoercible;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


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

        try{
            trip.setLike_counter(result.getInteger("likes"));
        }catch (NullPointerException e){ }

        try{
            trip.setPrice(result.getDouble( "price"));
        }catch (NullPointerException e){ }

        trip.setDepartureDate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("departureDate")));
        trip.setReturnDate(LocalDateAdapter.convertToLocalDateViaInstant(result.getDate("returnDate")));

        try{
            trip.setLast_modified(LocalDateTimeAdapter.convertToLocalDateTimeViaInstant(result.getDate("last_modified")));
        }catch (NullPointerException e){ }

        //ArrayList<String> tags_strings = result.get("tags", ArrayList.class);
        trip.setTags(stringToTag(result.get("tags", ArrayList.class)));

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
        tripDTO.setDestination(trip.getDestination());
        tripDTO.setDescription(trip.getDescription());
        tripDTO.setInfo(trip.getInfo());
        tripDTO.setPrice(trip.getPrice());
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setTags(tagsToString(trip.getTags()));
        tripDTO.setLast_modified(trip.getLast_modified());
        tripDTO.setWhatsIncluded(trip.getWhatsIncluded());
        tripDTO.setWhatsNotIncluded(trip.getWhatsNotIncluded());
        tripDTO.setLike_counter(trip.getLike_counter());
        try {
            tripDTO.setOrganizer(trip.getOrganizer().getUsername());
        }catch(NullPointerException ex){
            tripDTO.setOrganizer(null);
        }
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
        try {
            tripDTO.setOrganizer(trip.getOrganizer().getUsername());
        }catch(NullPointerException ex){
            tripDTO.setOrganizer(null);
        }
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
        tripDTO.setLike_counter(trip.getLike_counter());
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
        trip.setOrganizer(new RegisteredUser(tripSummary.getOrganizer()));
        trip.setLast_modified(tripSummary.getLast_modified());

        return trip;
    }

    static String tripToJSONString(TripSummaryDTO trip){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return gson.toJson(trip);
    }

    static TripSummaryDTO tripFromJSONString(String jsonString){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return gson.fromJson(jsonString, TripSummaryDTO.class);
    }

    
    static Trip destinationFromDocument(Document doc) {
        Trip t = new Trip();
        t.setDestination(doc.getString("_id"));
        try {
            double price = Math.round(doc.getDouble("agg") * 100) / 100.0;
            t.setPrice(price);
        }catch (Exception e){
            t.setPrice(0.00);
        }
        return t;
    }

    static Trip tripFromRecord(Record r){
        Trip trip = new Trip();
        trip.setId(r.get("t._id").asString());
        trip.setDestination(r.get("t.destination").asString());
        trip.setTitle(r.get("t.title").asString());
        trip.setOrganizer(new RegisteredUser(r.get("organizer").asString()));


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
        doc.append("destination",t.getDestination().toLowerCase());
        doc.append("title",t.getTitle());
        doc.append("departureDate",t.getDepartureDate());
        doc.append("returnDate",t.getReturnDate());
        doc.append("price",t.getPrice());
        if(t.getInfo()!=null)
            doc.append("info",t.getInfo());
        if(t.getDescription()!=null)
            doc.append("description",t.getDescription());
        if(t.getTags()!=null && !t.getTags().isEmpty())
            doc.append("tags",TripUtils.tagsToString(t.getTags()));
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
        t.setPrice(tripDetailsDTO.getPrice());
        t.setOrganizer(new RegisteredUser(tripDetailsDTO.getOrganizer()));
        t.setDescription(tripDetailsDTO.getDescription());
        t.setDestination(tripDetailsDTO.getDestination());
        t.setTitle(tripDetailsDTO.getTitle());
        t.setTags(stringToTag(tripDetailsDTO.getTags()));
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

    static TripDetailsDTO tripDetailsDTOfromRequest(HttpServletRequest request) throws IncompleteTripException {

        TripDetailsDTO trip = new TripDetailsDTO();
        trip.setDestination(request.getParameter("destination"));
        trip.setTitle(request.getParameter("title"));
        trip.setPrice(Double.parseDouble(request.getParameter("price")));
        trip.setDepartureDate(LocalDate.parse(request.getParameter("departureDate")));
        trip.setReturnDate(LocalDate.parse(request.getParameter("returnDate")));
        trip.setDepartureDate(LocalDate.parse(request.getParameter("departureDate")));
        trip.setReturnDate(LocalDate.parse(request.getParameter("returnDate")));

        if(trip.getDestination() == null || trip.getTitle()==null || trip.getPrice()==0 || trip.getDepartureDate()==null || trip.getReturnDate()== null)
            throw new IncompleteTripException();

        List<String> tags = Arrays.asList(request.getParameter("tags").split(","));
        trip.setTags(tags);
        trip.setDescription(request.getParameter("description"));
        List<DailyScheduleDTO> itinerary = new ArrayList<>();
        for (int i = 1; ; i++) {
            String title = request.getParameter("title" + i);
            if (title == null)
                break;
            DailyScheduleDTO d = new DailyScheduleDTO();
            d.setDay(i);
            d.setTitle(title);
            String subtitle = request.getParameter("subtitle" + i);
            d.setSubtitle(subtitle);
            String description = request.getParameter("description" + i);
            d.setDescription(description);
            itinerary.add(d);
        }
        if(itinerary.size()==0)
            throw new IncompleteTripException();
        trip.setItinerary(itinerary);
        List<String> included = new ArrayList<>();
        List<String> notIncluded = new ArrayList<>();
        for (int i = 0; ; i++) {
            String str = request.getParameter("included" + i);
            if (str != null)
                included.add(str);
            else
                break;
        }
        trip.setWhatsIncluded(included);
        for (int i = 0; ; i++) {
            String str = request.getParameter("notIncluded" + i);
            if (str != null)
                notIncluded.add(str);
            else
                break;
        }
        trip.setWhatsNotIncluded(notIncluded);
        String username = ((RegisteredUserDTO) request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)).getUsername();
        if(username==null)
            throw new IncompleteTripException();
        trip.setOrganizer(username);
        trip.setLast_modified(LocalDateTime.now());
        trip.setInfo(request.getParameter("info"));
        return trip;
    }

    static List<String> tagsToString(List<Tag> tags){

        if(tags == null)
            return null;

        List<String> list = new ArrayList<String>();
        for(Tag tag : tags){
            list.add(tag.getTag());
        }
        return list;
    }

    static List<Tag> stringToTag(List<String> tags){

        if(tags == null)
            return null;

        List<Tag> list = new ArrayList<Tag>();
        for(String tag : tags){
            list.add(new Tag(tag));
        }
        return list;
    }
}
