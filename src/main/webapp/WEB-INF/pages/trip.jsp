<%@ page import="it.unipi.lsmd.dto.*" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 14/12/2022
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="stylesheet" href="WebContent/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <%
        TripDetailsDTO trip = (TripDetailsDTO) request.getAttribute("trip");
        if(trip != null){
    %>
        <title><%= trip.getTitle() %></title>
    <%
        }else{
    %>
        <title>Trip Not Found</title>
    <%
        }
    %>
</head>



<body class="tripbgd">

<%@ include file="header.jsp" %>


<%
    if(trip != null){
%>
<div class="col-sm-9 mx-auto border border-4 mt-4 tripbgd2 ">

    <!-- Receipe Content Area -->
    <div class="main-form">

        <div class="container justify-content-center text-center my-30 ">

            <div class="row justify-content-between">
                <h2 class="pdn-top-40 mt-6 text-center triptitle"><%= trip.getTitle() %></h2>
                <%
                    String url = "trip?id=" + trip.getId() + "&action=";
                    if(trip.isInWishlist()){
                %>
                <a href=<%=url + "remove"%>>
                    <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="black" class="bi bi-star mt-2" viewBox="0 0 16 16">
                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                    </svg>
                </a>
                <%
                    }else{
                %>
                <a href=<%=url + "add"%>>
                    <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="black" class="bi bi-star mt-2" viewBox="0 0 16 16">
                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                    </svg>
                </a>
                <%
                    }
                %>

            </div>


            <div class="col-1"></div>
            <div class="receipe-headline my-5 col-10">

                <div class="row">
                    <div class="col-9">
                        <img src="images/signin.jpg">
                    </div>
                    <div class="col-3 pull-right">
                        <div class="receipe-duration text-right margin_top_50 pdn-top-30 ">
                            <h4>Price: <%= trip.getPrice() %>€</h4>
                            <h4>Departure date:<br><%= trip.getDepartureDate() %></h4>
                            <h4>Return date:<br> <%= trip.getReturnDate() %></h4>
                            <% if(trip.getTags()!=null || !trip.getTags().isEmpty()){ %>
                            <h4><%= trip.getTags() %></h4>
                            <% } %>
                            <h4><a href=<%="user?username="+trip.getOrganizer()%>>Organized By <%= trip.getOrganizer() %></a></h4>
                        </div>
                    </div>
                </div>
            </div>


        </div>

        <div class="col-1"></div>
        <div class="receipe-ratings my-5 col-12">
            <h6> <%=trip.getDescription()%></h6>
        </div>

        <div class="receipe-ratings my-5 col-12">
            <h2>Additional Information:</h2><br>
            <h6> <%=trip.getInfo()%></h6>
        </div>


            <div class="mb-4">
                <div class="row">
                    <div class="col-12 col-lg-8">
                        <!-- Single Preparation Step -->

                        <h2>Itinerary</h2>
                        <%
                            for(DailyScheduleDTO dailyScheduleDTO : trip.getItinerary()){
                        %>
                        <div class="single-preparation-step d-flex">
                            <div class="col">
                                <h4 class="pdn-top-30"><%="Day " + dailyScheduleDTO.getDay() + " - " + dailyScheduleDTO.getTitle()%></h4><br>
                                <% if(dailyScheduleDTO.getSubtitle()!=null){ %>
                                <h5><%= dailyScheduleDTO.getSubtitle() %></h5>
                                <% } %>
                                <% if(dailyScheduleDTO.getDescription()!=null){ %>
                                <p><%= dailyScheduleDTO.getDescription() %></p>
                                <% } %>
                            </div>

                        </div>
                        <hr class="invis3">
                        <%
                            }
                        %>
                    </div>

                    <!-- WhatsIncluded -->
                    <div class="col-12 col-lg-4">
                        <div  class="mr-5 ">


                            <h2 class="justify-content-start pdn-top-30"> INCLUDED</h2>

                            <!-- Custom Checkbox -->
                            <ul class="custom-control">

                                <%
                                    for(String string : trip.getWhatsIncluded()){
                                %>

                                <li ><%="- " + string%></li>

                                <%
                                    }
                                %>

                            </ul>
                        </div>
                        <div class="mr-5">
                            <h2 class="justify-content-start pdn-top-30">NOT INCLUDED</h2>

                            <!-- Custom Checkbox -->
                            <ul class="custom-control ">

                                <%
                                    for(String string : trip.getWhatsNotIncluded()){
                                %>

                                <li ><%="- " + string%></li>

                                <%
                                    }
                                %>

                            </ul>
                        </div>
                    </div>

                </div>
            </div>

        <%
            if(session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) != null){
        %>

        <div class="row">
            <% String username = ((AuthenticatedUserDTO)session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)).getUsername();
                if(username.equals(trip.getOrganizer())){
            %>
            <div class="row">
            <a class="text-right btn btn-primary bottone ml-3" href="<%="updateTrip?id="+trip.getId()%>" >Modify your trip</a>
            <a class="text-right btn btn-primary bottone ml-3" href="<%="deleteTrip?id="+trip.getId()%>" >Delete your trip</a>
            <a class="text-right btn btn-primary bottone ml-3" href="#" >View Pending Requests</a>
            </div>
            <% }%>
        </div>
        <div class="row" id="join_div">
            <% String status = (String) request.getAttribute(SecurityUtils.STATUS);
            if((status == null) && (!username.equals(trip.getOrganizer()))){ %>
            <button class="text-right btn btn-primary bottone send-button" id="join_button">Join!</button>
            <% }else{
                %>
            <span id="status_span"><%="Join request status: "+status+" "%></span><br>
            <%
            if(status!="rejected"){ %>
            <button class="text-right btn btn-primary bottone send-button" id="cancel_button">Cancel request</button>
            <% }
            } %>
        </div>

        <%
                //request to do based on current status
            }
        %>
        <p class="grey pull-right ">Last Modified:  <%= trip.getLast_modified()%></p>
        </div>

    </div>


</div>
<%
}else{
%>

<div class="titlepage">
    <h2>Trip not found!</h2>
</div>

<%
    }
%>
<%@include file="footer.jsp"%>
</body>

<% if (session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY)!=null){ %>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    $(document).on("click", ".send-button", function() {
        var button_id = event.target.id
        var username = "<%= ((AuthenticatedUserDTO)(session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY))).getUsername() %>";
        var id ="<%= request.getParameter("id") %>";
        var url=""
        if(button_id=="join_button"){
            url = "join?id="+id+"&username="+username+"&action=join";
        }else{
            url = "join?id="+id+"&username="+username+"&action=cancel";
        }
        $.get(url, function(responseText) {
            if (responseText == "OK") {
                if(button_id=="join_button"){
                    $("#join_button").remove();
                    $("#join_div").append('<button class="text-right btn btn-primary bottone send-button" id="cancel_button">Cancel request</button>');
                }else{
                    $("#cancel_button").remove();
                    $("#status_span").remove();
                    $("#join_div").append('<button class="text-right btn btn-primary bottone send-button" id="join_button">Join!</button>');
                }
            } else {
                alert("Errore durante l'operazione");
            }

        });
    });

</script>
<% } %>
<!-- ##### All Javascript Files ##### -->
<!-- jQuery-2.2.4 js -->
<script src="WebContent/js/jquery/jquery-2.2.4.min.js"></script>
<!-- Popper js -->
<script src="WebContent/js/bootstrap/popper.min.js"></script>
<!-- Bootstrap js -->
<script src="WebContent/js/bootstrap/bootstrap.min.js"></script>
<!-- All Plugins js -->
<script src="WebContent/js/plugins/plugins.js"></script>
<!-- Active js -->
<script src="WebContent/js/active.js"></script>

</html>