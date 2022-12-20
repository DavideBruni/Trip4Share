<%@ page import="it.unipi.lsmd.dto.TripHomeDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.PriceDestinationDTO" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 17/12/2022
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
    List<TripHomeDTO> trips = (List<TripHomeDTO>) request.getAttribute("trips");
    List<String> destinations = (List<String>) request.getAttribute("destinations");
    List<PriceDestinationDTO> dest_price = (List<PriceDestinationDTO>) request.getAttribute("dest&price");
    if(trips!=null && !trips.isEmpty()){
        for(TripHomeDTO t : trips){ %>
            Title:
            <%= t.getTitle()%>
            <br> Destination
            <%= t.getDestination()  %>
            <br>
<%
                }
    }else if(destinations!=null && !destinations.isEmpty()){
            for(String d : destinations){ %>
                Dest:
                    <%= d%>
<br>
    <%
                }
    }else if(dest_price!=null && !dest_price.isEmpty()){
        for(PriceDestinationDTO pd : dest_price){ %>
Destination:
    <%= pd.getDestination()%>
<br> Price:
    <%= pd.getPrice()  %>
<br>
<%
        }
    }else{  %>
    Nessun risultato trovato
    <%
    }%>
</body>
</html>
