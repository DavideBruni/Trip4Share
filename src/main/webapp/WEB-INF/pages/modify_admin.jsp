<%@ page import="it.unipi.lsmd.dto.AdminDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 02/01/2023
  Time: 18:44
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

  <title>Edit profile</title>
</head>

<body class="main-layout col-12">

<%
  AdminDTO user = (AdminDTO) session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
%>


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
                  <li><a href="#contact">Search Users</a></li>
                  <li><a href="#contact">Add a new Admin</a></li>
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

<div class="container-xl px-4 mt-4 col-12 justify-content-center">


  <div class="row justify-content-center">

    <div class="col-xl-4 justify-content-center">
      <!-- Profile picture card-->
      <div class="card mb-4 mb-xl-0">

        <div class="card-body text-center">
          <!-- Profile picture image-->
          <img class="img-account-profile rounded-circle mb-2" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="">
          <input   class="form-control" id="imgUrl" type="text" name="birthDate"  placeholder="06/10/1988">
        </div>
      </div>
    </div>
  </div>

  <div class="row justify-content-center pdn-top-30">
    <div class="col-xl-8 justify-content-center">
      <!-- Account details card-->
      <div class="card mb-4">
        <div class="card-header">Account Details</div>
        <div class="card-body">
          <form method="post" action="updateProfile">
            <!-- Form Group (username)-->
            <div class="mb-3">
              <label class="small mb-1" for="inputUsername"></label>
              <input disabled  class="form-control" id="inputUsername" type="text" placeholder=<%=user.getUsername()%>>
            </div>
            <!-- Form Row-->
            <div class="row gx-3 mb-3">
              <!-- Form Group (first name)-->
              <div class="col-md-6">
                <label class="small mb-1" for="inputFirstName">First name</label>
                <input   class="form-control" id="inputFirstName" type="text" name="firstName" placeholder=<%=user.getFirstName()%>>
              </div>
              <!-- Form Group (last name)-->
              <div class="col-md-6">
                <label class="small mb-1" for="inputLastName">Last name</label>
                <input   class="form-control" id="inputLastName" type="text" name="lastName" placeholder=<%=user.getLastName()%>>
              </div>
            </div>
            <!-- Form Row        -->
            <div class="row gx-3 mb-3">
              <!-- Form Group (organization name)-->
              <div class="col-md-6">
                <label class="small mb-1" for="inputOrgName">Password</label>
                <input   class="form-control" id="inputOrgName" type="password" name="password" placeholdere="password">
              </div>
              <!-- Form Group (location)-->
              <div class="mb-3">
                <label class="small mb-1" for="inputEmailAddress">Email address</label>
                <input   class="form-control" id="inputEmailAddress" type="email" name="email" placeholder=<%=user.getEmail()%>>
              </div>
            </div>
            <!-- Save changes button-->
            <button type="submit" class="btn btn-primary">Save changes</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</div>

<%@include file="footer.jsp"%>

</body>
</html>
