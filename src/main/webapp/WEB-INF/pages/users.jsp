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
    if(users==null || users.isEmpty()){%>
Nessun utente trovato!
    <% }else{
            for(OtherUserDTO o : users){ %>
Username:
    <%= o.getUsername() %>
<br>
<%
            }
        }
    %>

</body>
</html>
