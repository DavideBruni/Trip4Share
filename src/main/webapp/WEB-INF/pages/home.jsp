<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 14/12/2022
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%
    List<TripSummaryDTO> trips = (List<TripSummaryDTO>) request.getAttribute("trips");
    List<OtherUserDTO> suggested = (List<OtherUserDTO>) request.getAttribute("suggested");
    if(trips==null || trips.isEmpty()){%>
        Nessun viaggio presente!
   <% }else{
            for(TripSummaryDTO t : trips){ %>
        Title:
        <%= t.getTitle() %>
        <br> Destination
        <%= t.getDestination()  %>
<%
        }
}
    if(suggested==null || suggested.isEmpty()){%>
        Nessun utente presente!
<% }else{
    for(OtherUserDTO s : suggested){ %>
Username:
<%= s.getUsername() %>
<br>
<%
        }
    }
%>

<%@ include file="footer.jsp" %>
</body>
</html>
