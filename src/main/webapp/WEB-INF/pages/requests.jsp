<%@ page import="it.unipi.lsmd.dto.InvolvedPeopleDTO" %>
<%@ page import="it.unipi.lsmd.model.enums.Status" %>
<%@ page import="org.javatuples.Pair" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 28/12/2022
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>

<%@include file="header.jsp"%>

<body class="justify-content-center main-layout">
<div class="row">
  <div class="col-1"></div>
  <div class="col-10">
    <div class="sidebar">
      <div class="widget">
        <div class="titlepage ">
          <h3>People who joined your trip </h3>
        </div>
        <%
          List<Pair<OtherUserDTO, Status>> joiners = (List<Pair<OtherUserDTO, Status>>) request.getAttribute(SecurityUtils.JOINERS);
          if(joiners!= null){
            for(Pair<OtherUserDTO, Status> j : joiners){

        %>
        <div class="blog-list-widget">
          <div class=" row">
            <div class="col-3"></div>
              <div class="row justify-content-center">
                <i><img src=<%=j.getValue0().getPic()%> alt="icon" width="40%"/></i>
                <h3 class="mt-3"><%= j.getValue0().getUsername()+" | "+j.getValue1() %> </h3>
              </div>
            <div class="col-3"></div>
          </div>
        </div><!-- end blog-list -->
      </div><!-- end widget -->
      <% }
            }%>
    </div>
  </div>
  <div class="col-1"></div>
</div>
</div>


<%@include file="footer.jsp"%>

</body>
</html>
