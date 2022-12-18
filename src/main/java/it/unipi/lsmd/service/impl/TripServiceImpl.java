package it.unipi.lsmd.service.impl;

import it.unipi.lsmd.dao.DAOLocator;
import it.unipi.lsmd.dao.TripDAO;
import it.unipi.lsmd.dao.neo4j.TripDAONeo4j;
import it.unipi.lsmd.dto.TripDTO;
import it.unipi.lsmd.dto.TripHomeDTO;
import it.unipi.lsmd.model.Trip;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.TripUtils;

import java.util.ArrayList;
import java.util.List;

public class TripServiceImpl implements TripService {

    private TripDAO tripDAO;

    public TripServiceImpl(){
        tripDAO = DAOLocator.getTripDAO();
    }

    @Override
    public List<TripHomeDTO> getTripsOrganizedByFollowers(String username) {
        TripDAONeo4j tripDAONeo4j = new TripDAONeo4j();
        List<Trip> trips = tripDAONeo4j.getTripsOrganizedByFollower(username);
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

    public TripDTO getTrip(String id){
        Trip trip = tripDAO.getTrip(id);
        return TripUtils.tripModelToDTO(trip);
    }
}
