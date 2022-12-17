package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.mongo.TripMongoDAO;
import it.unipi.lsmd.dao.neo4j.TripNeo4jDAO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripServiceImpl implements TripService {
    @Override
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username) {
        TripNeo4jDAO tripNeo4JDAO = new TripNeo4jDAO();
        List<Trip> trips = tripNeo4JDAO.getTripsOrganizedByFollower(username);
        if(trips == null || trips.isEmpty()){
            return new ArrayList<TripHomeDTO>();
        }
        List<TripHomeDTO> tripsDTO = new ArrayList<>();
        for(Trip t : trips){
            TripHomeDTO tripHomeDTO = new TripHomeDTO();
            tripHomeDTO.setDestination(t.getDestination());
            tripHomeDTO.setDeleted(t.getDeleted());
            tripHomeDTO.setDepartureDate(t.getDepartureDate());
            tripHomeDTO.setReturnDate(t.getReturnDate());
            tripHomeDTO.setTitle(t.getTitle());
            tripHomeDTO.setImgUrl(t.getImg());

            tripsDTO.add(tripHomeDTO);
        }
        return tripsDTO;
    }

    @Override
    public List<TripHomeDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page) {
        Date depDate = null;
        Date retDate = null;
        TripMongoDAO tripMongoDAO = new TripMongoDAO();
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripMongoDAO.getTripsByDestination(destination,depDate,retDate,size,page);
            List<TripHomeDTO> tripsDTO = new ArrayList<>();
            for(Trip t : trips){
                TripHomeDTO tDTO = TripUtils.parseTrip(t);
                tripsDTO.add(tDTO);
            }
            return tripsDTO;
        }

    }

    @Override
    public List<TripHomeDTO> getTripsByTag(String tag, String departureDate, String returnDate, int size, int page) {
        Date depDate = null;
        Date retDate = null;
        TripMongoDAO tripMongoDAO = new TripMongoDAO();
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripMongoDAO.getTripsByTag(tag,depDate,retDate,size,page);
            List<TripHomeDTO> tripsDTO = new ArrayList<>();
            for(Trip t : trips){
                TripHomeDTO tDTO = TripUtils.parseTrip(t);
                tripsDTO.add(tDTO);
            }
            return tripsDTO;
        }
    }
}
