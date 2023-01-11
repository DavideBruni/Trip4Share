package it.unipi.lsmd.controller;

import com.google.gson.Gson;
import it.unipi.lsmd.dto.DestinationsDTO;
import it.unipi.lsmd.dto.PriceDestinationDTO;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.PagesUtilis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/destinations")
public class DestinationsServlet extends HttpServlet {
    private final TripService tripService = ServiceLocator.getTripService();
    private static final Logger logger = LoggerFactory.getLogger(DestinationsServlet.class);


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if(action == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<DestinationsDTO> dests;
        List<PriceDestinationDTO> priceDest;
        String json;
        switch (action){
            case "overall":
                dests = tripService.mostPopularTripsOverall(PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(dests);
                resp.getWriter().write(json);
                break;
            case "exclusive":
                dests = tripService.mostExclusive(PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(dests);
                resp.getWriter().write(json);
                break;
            case "cheaper":
                priceDest = tripService.cheapestDestinationsByAvg(PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(priceDest);
                resp.getWriter().write(json);
                break;
            case "period":
                String depDate = req.getParameter("departureDate");
                String retDate = req.getParameter("returnDate");
                dests = tripService.mostPopularDestinationsByPeriod(depDate,retDate,PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(dests);
                resp.getWriter().write(json);
                break;
            case "price":
                double from,to;
                try {
                    from = Double.parseDouble(req.getParameter("from"));
                    to = Double.parseDouble(req.getParameter("to"));
                }catch(Exception e){
                    resp.getWriter().write("");
                    break;
                }
                dests = tripService.mostPopularDestinationsByPrice(from,to,PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(dests);
                resp.getWriter().write(json);
                break;
            case "tag":
                String tag = req.getParameter("tag");
                dests = tripService.mostPopularDestinationsByTag(tag,PagesUtilis.SUGGESTIONS_EXPLORE);
                json = new Gson().toJson(dests);
                resp.getWriter().write(json);
                break;
            default:
                resp.getWriter().write("");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getQueryString());
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
