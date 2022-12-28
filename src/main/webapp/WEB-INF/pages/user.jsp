<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.ReviewDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 14/12/2022
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/profile.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <%
        RegisteredUserDTO user = (RegisteredUserDTO) request.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
    %>
    <title><%= user.getUsername() %>'s Profile</title>

</head>

<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="padding justify-content-center">
    <div >
        <!-- Column -->
        <div class="card">
            <%
                Boolean itsMe = (Boolean) request.getAttribute("itsMe");
            %>
            <div class="card-body little-profile justify-content-center">
                <div class="pro-img"><img src="https://i.imgur.com/8RKXAIV.jpg" alt="user"></div>
                <h3 class="m-b-0 white"><%= user.getUsername() %></h3>
                <h3 class="m-t-10 white"><%= user.getFirstName() %> <%= user.getLastName() %></h3>
                <p><%= user.getBirthdate() %></p>

                <%
                    if(!itsMe){
                %>
                <a href="javascript:void(0)" class="m-t-10 waves-effect waves-dark btn btn-primary btn-md btn-rounded" data-abc="true">Follow</a>
                <%}else{%>
                <div class="row">
                <a href="javascript:void(0)" class="m-t-10 waves-effect waves-dark btn btn-primary btn-md btn-rounded" data-abc="true">Edit Profile</a>
                <a href="javascript:void(0)" class="m-t-10 ml-3 waves-effect waves-dark btn btn-primary btn-md btn-rounded" data-abc="true">Create new trip</a>
                </div>
                <%}%>
                <div class="row text-center m-t-20">
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">434K</h3><small>Followers</small>
                    </div>
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">5454</h3><small>Following</small>
                    </div>
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">5454</h3><small>Average Rating</small>
                    </div>
                </div>
            </div>
            <div class="text-center">
                <h4 class="m-t-10"> Nazionalità </h4>
                <h4 class="m-t-10"> Lingue Parlate </h4>
                <h4 class="m-t-10"> Bio</h4>
            </div>
        </div>
    </div>
</div>


<ul class="nav nav-pills mb-3 justify-content-center" id="pills-tab" role="tablist">
    <li class="nav-item">
        <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-a" role="tab" aria-controls="pills-home" aria-selected="true">Reviews</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href=<%="user?username="+authenticatedUserDTO.getUsername()+"&show=OrganizedTrips&page=1"%>  aria-controls="pills-profile" aria-selected="false">Organized Trips</a>
    </li>

    <%
        if(itsMe){
    %>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="pastTrips" role="tab" aria-controls="pills-contact" aria-selected="false">Past Trips</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab1" data-toggle="pill" href="wishlist" role="tab" aria-controls="pills-contact" aria-selected="false">WishList</a>
            </li>
    <%
        }
    %>
</ul>
<div class="tab-content" id="pills-tabContent">
    <div class="tab-pane fade show active" id="pills-a" role="tabpanel" aria-labelledby="pills-home-tab" aria-expanded="true">
        <div class="container" >
            <hr class="invis3">
            <div class = "row justify-content-center ">

                <%
                    for(int i = 0; i < 3 && i < user.getReviews().size(); i++){
                        ReviewDTO reviewDTO = user.getReviews().get(i);
                %>
                <div class="card" style="width: 18rem;">

                    <div class="card-body">
                        <h4 class="card-title white"><%= reviewDTO.getRating() %> - <%= reviewDTO.getTitle() %></h4>
                        <p class="card-text"><%= reviewDTO.getText() %></p>
                        <a href=<%="user?username=" + reviewDTO.getAuthor() %>><%= reviewDTO.getAuthor() %></a>
                    </div>
                </div>
                <%  }
                %>
            </div>
            <hr class="invis3">
            <div class="row">
                <div class="col-md-12">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-end">
                            <li class="page-item"><a class="page-link" href=<%="user?username="+user.getUsername()+"&show=reviews&page=1"%>>View More</a></li>
                            <!---
                            <li class="page-item"><a class="page-link" href=<%="reviews?username="+request.getParameter("username")+"&page=1"%>>View More</a></li>
                            ---->
                        </ul>
                    </nav>
                </div><!-- end col -->
            </div><!-- end row -->


        </div>

    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>

</body>