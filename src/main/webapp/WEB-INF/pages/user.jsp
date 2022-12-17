<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 14/12/2022
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
    <%
        RegisteredUserDTO user = (RegisteredUserDTO) request.getAttribute("user");
        Boolean itsMe = (Boolean) request.getAttribute("itsMe");

        if(user == null){
        %>
            User not found!
        <%
        }else{ %>
            Profile of
            <%= user.getUsername() %>
            <%
                if (itsMe){ %>
                    it's you!
            <%  }

            try{
            %>
                Reviews:
            <%= user.getReviews().get(0) %>
            <%
            }catch(IndexOutOfBoundsException e){ %>
                No reviews available
            <%}
        }


    %>

</body>
</html>
