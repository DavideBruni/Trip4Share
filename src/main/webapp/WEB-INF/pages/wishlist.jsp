<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.PriceDestinationDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 17/12/2022
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wishlist</title>
</head>
<body>
    <%
    List<TripSummaryDTO> trips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.WISHLIST_KEY);

    if(trips == null || trips.isEmpty()){
    %>
        User not found!
    <%
    }else {
        for(TripSummaryDTO trip : trips){ %>
            Title:
            <%= trip.toString()%>

        <%}
    }
    %>


</body>
</html>
