<%@ page import="it.unipi.lsmd.dto.TripHomeDTO" %>
<%@ page import="java.util.List" %><%--
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
    if(trips==null || trips.isEmpty()){%>
Nessun viaggio presente!
    <% }else{
            for(TripHomeDTO t : trips){ %>
Title:
    <%= t.getTitle()%>
<br> Destination
    <%= t.getDestination()  %>
    <br>
<%
            }
        }%>
</body>
</html>
