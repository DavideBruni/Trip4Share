package it.unipi.lsmd.controller;

import it.unipi.lsmd.dto.DailyScheduleDTO;
import it.unipi.lsmd.dto.RegisteredUserDTO;
import it.unipi.lsmd.dto.TripDetailsDTO;
import it.unipi.lsmd.model.RegisteredUser;
import it.unipi.lsmd.service.ServiceLocator;
import it.unipi.lsmd.service.TripService;
import it.unipi.lsmd.utils.LocalDateAdapter;
import it.unipi.lsmd.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@WebServlet("/addTrip")
public class AddTrip extends HttpServlet{
    private final TripService tripService = ServiceLocator.getTripService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/create_trip.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession()==null || request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        try {
            TripDetailsDTO trip = new TripDetailsDTO();
            trip.setDestination(request.getParameter("destination"));
            trip.setTitle(request.getParameter("title"));
            trip.setPrice(Double.parseDouble(request.getParameter("price")));
            trip.setDepartureDate(LocalDate.parse(request.getParameter("departureDate")));
            trip.setReturnDate(LocalDate.parse(request.getParameter("returnDate")));
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
            trip.setOrganizer(username);
            trip.setLast_modified(LocalDateTime.now());
            trip.setInfo(request.getParameter("info"));

            if(tripService.addTrip(trip)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/success.jsp");
                requestDispatcher.forward(request, response);
            }else{
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
                requestDispatcher.forward(request, response);
            }
        }catch(Exception e){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/unsuccess.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
