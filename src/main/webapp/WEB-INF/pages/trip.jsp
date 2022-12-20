<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %>
<%@ page import="it.unipi.lsmd.dto.TripDetailsDTO" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 14/12/2022
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trip Detail</title>
</head>
<body>
    <%
        TripDetailsDTO trip = (TripDetailsDTO) request.getAttribute("trip");

        if(trip == null){
            %>
            Trip not found!
            <%
        }else{ %>
            Trip title:
            <%= trip.getTitle() %>
            <%= trip.getItinerary().toString() %>

    <%
        }


    %>
</body>
</html>
