<%@ page import="it.unipi.lsmd.dto.TripHomeDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 17/12/2022
  Time: 09:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <%
    List<OtherUserDTO> users = (List<OtherUserDTO>) request.getAttribute("users_founded");
    List<TripHomeDTO> trips = (List<TripHomeDTO>) request.getAttribute("trips");
    if((users==null && trips == null) || (users.isEmpty() && trips.isEmpty())){%>
            Nessun risultato trovato!
    <% }else{
        if(users!=null && !users.isEmpty()){
            for(OtherUserDTO o : users){ %>
Username:
    <%= o.getUsername() %>
<br>
<%
            }
        }else{
                for(TripHomeDTO t : trips){
                    %>
    Title:
    <%= t.getTitle() %>
    <br>
    <%
                }
            }

    }

    %>

</body>
</html>
