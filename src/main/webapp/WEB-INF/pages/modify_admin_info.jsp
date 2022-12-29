<%--
  Created by IntelliJ IDEA.
  User: cater
  Date: 28/12/2022
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="it.unipi.lsmd.dto.AdminDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.AuthenticatedUserDTO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <link rel="stylesheet" href="css/style.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <%
    AdminDTO admin = (AdminDTO) request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);

  %>
  <title>Modifing <%= admin.getPassword() %>'s Profile</title>

</head>

<body class="main-layout col-12">
<div class="container-xl px-4 mt-4 col-12 justify-content-center">


  <div class="row justify-content-center pdn-top-30">
    <div class="col-xl-8 justify-content-center">
      <!-- Account details card-->
      <div class="card mb-4">
        <div class="card-header">Account Details</div>
        <div class="card-body">
          <form method="POST" action="modify_admin">
            <!-- Form Group (username)-->
            <div class="mb-3">
              <label class="small mb-1">Username (Admin can't change their usernames)</label>
              <input disabled  class="form-control"  type="text" value="<%=admin.getUsername()%>">
            </div>
            <!-- Form Row-->
            <div class="row gx-3 mb-3">
              <!-- Form Group (first name)-->
              <div class="col-md-6">
                <label class="small mb-1" for="adminFirstName" >First name</label>
                <input   class="form-control" id="adminFirstName"  type="text" value="<%=admin.getFirstName()%>">
              </div>
              <!-- Form Group (last name)-->
              <div class="col-md-6">
                <label class="small mb-1" for="adminLastName">Last name</label>
                <input   class="form-control" id="adminLastName" type="text" value="<%=admin.getLastName()%>">
              </div>
            </div>
            <!-- Form Row        -->
            <div class="row gx-3 mb-3">
              <!-- Form Group (organization name)-->
              <div class="col-md-6">
                <label class="small mb-1"  for="adminPassword" >Password</label>
                <input   class="form-control"  id="adminPassword"  type="pa ssword" value="<%=admin.getPassword()%>">
              </div>
              <!-- Form Group (location)-->
              <div class="mb-3">
                <label class="small mb-1" for="adminEmailAddress">Email address</label>
                <input   class="form-control" id="adminEmailAddress" type="email" value="<%=admin.getEmail()%>">
              </div>
            </div>
            <!-- Save changes button-->
            <button class="btn btn-primary" type="submit">Save changes</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</div>

</body>
