<%@ page import="it.unipi.lsmd.dto.AdminDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 15/12/2022
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <link rel="stylesheet" href="WebContent/css/style.css">

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

  <title>Ban User</title>
</head>


<body class="main-layout">

<header>
  <!-- header inner -->
  <div class="header">
    <div class="header_white_section">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            <div class="header_information">
              <ul>

              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3 col logo_section">
          <div class="full">
            <div class="center-desk">
              <div class="logo"> <a href="index.html"><img src="images/logo.png" alt="#"></a> </div>
            </div>
          </div>
        </div>
        <div class="col-xl-9 col-lg-9 col-md-9 col-sm-9">
          <div class="menu-area">
            <div class="limit-box">
              <nav class="main-menu">
                <ul class="menu-area-main">
                  <li><a href="banUser">Ban Users</a></li>
                  <li><a href="addAdmin">Add a new Admin</a></li>
                  <li><a href="logout">Logout</a></li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- end header inner -->
</header>

<!-- Ricerca Utenti -->

<div class="titlepage">
  <h3 > Ban User </h3>
</div>

<div class=" pdn-top-30 row">
  <div class=" col-4"></div>
  <div class="col-4 justify-content-center">
    <form method="post" >
      <label >Insert username</label>
      <input  class="form-control" placeholder="" type="text" name="username" size="50">
      <button class="btn btn-primary" type="submit" >Ban User</button>
    </form>
  </div>
</div>
<%
  String message = (String) request.getSession().getAttribute(SecurityUtils.ERROR_MESSAGE);
  if(message != null && !message.equals("")){
%>
  <%=message%>
<%
  }
%>


<%@ include file="footer.jsp" %>

</body>
</html>
