<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.TripHomeDTO" %>
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
<%
    List<TripHomeDTO> trips = (List<TripHomeDTO>) request.getAttribute("trips");
    List<OtherUserDTO> suggested = (List<OtherUserDTO>) request.getAttribute("suggested");
    if(trips==null || trips.isEmpty()){%>
        Nessun viaggio presente!
   <% }else{
            for(TripHomeDTO t : trips){ %>
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



    Welcome: <b><%= request.getRemoteUser() %>
</body>
</html>
