package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.TripDetailsDAO;
import it.unipi.lsmd.dto.TripDTO;
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

    private TripDetailsDAO tripDetailsDAO;
    private TripDAO tripDAO;

    public TripServiceImpl(){
        tripDetailsDAO = DAOLocator.getTripDetailsDAO();
        tripDAO = DAOLocator.getTripDAO();
    }

    @Override
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username) {
        List<Trip> trips = tripDAO.getTripsOrganizedByFollower(username);
        if(trips == null || trips.isEmpty()){
            return new ArrayList<>();
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

    public TripDTO getTrip(String id){
        Trip trip = tripDetailsDAO.getTrip(id);
        return TripUtils.tripModelToDTO(trip);
    }

    @Override
    public List<TripHomeDTO> getTripsByDestination(String destination, String departureDate, String returnDate, int size, int page) {
        Date depDate = null;
        Date retDate = null;
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripDetailsDAO.getTripsByDestination(destination,depDate,retDate,size,page);
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
        try {
            depDate =new SimpleDateFormat("dd-MM-yyyy").parse(departureDate);
            try{
                retDate = new SimpleDateFormat("dd-MM-yyyy").parse(returnDate);
            }catch (ParseException ex){ }
        } catch (ParseException e) {
            depDate = new Date();
        }finally{
            List<Trip> trips= tripDetailsDAO.getTripsByTag(tag,depDate,retDate,size,page);
            List<TripHomeDTO> tripsDTO = new ArrayList<>();
            for(Trip t : trips){
                TripHomeDTO tDTO = TripUtils.parseTrip(t);
                tripsDTO.add(tDTO);
            }
            return tripsDTO;
        }
    }
}
