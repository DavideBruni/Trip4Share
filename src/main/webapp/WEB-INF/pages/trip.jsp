<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %>
<%@ page import="it.unipi.lsmd.dto.TripDetailsDTO" %>
<%@ page import="it.unipi.lsmd.dto.DailyScheduleDTO" %><%--
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
    %>

    <title><%= trip.getTitle() %></title>
</head>
<header >
    <!-- header inner -->
    <div class="header">
        <div class="header_white_section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="header_information">
                            <ul>
                                <li><img src="images/1.png" alt="#"/> 145.street road new York</li>
                                <li><img src="images/2.png" alt="#"/> +71  5678954378</li>
                                <li><img src="images/3.png" alt="#"/> Demo@hmail.com</li>
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
                                    <li class="active"> <a href="pages/registered_home.jsp">Home</a> </li>
                                    <li> <a href="#about">Profile</a> </li>
                                    <li><a href="search.html">Search</a></li>
                                    <li><a href="#blog">Logout</a></li>

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
<body class="tripbgd">

<div class="col-sm-9 mx-auto border border-4 mt-4 tripbgd2 ">



    <!-- Receipe Content Area -->
    <div>
        <div class="container justify-content-center text-center my-30  main-form">
            <a class="text-right btn btn-primary bottone" href="modify_trip.html" >Modify your trip</a>


            <div class="receipe-headline my-5">

                <h2 class="pdn-top-40 mt-6 text-center triptitle"><%=trip.getTitle()%></h2>
                <div class="receipe-duration">
                    <h6>Price</h6>
                    <h6>Departure Date</h6>
                    <h6>Return Date</h6>
                    <h6>Tags</h6>
                    <p class="grey pull-right">Last Modified</p>

                </div>

            </div>
            <img src="images/blog-image.jpg">

            <div class="receipe-ratings my-5">
                <h6>Description</h6>
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
                                <!-- TODO - add subtitle -->
                                <p><%=dailyScheduleDTO.getDescription()%></p>
                            </div>

                        </div>
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

                                <li ><%=string%></li>

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

                                <li ><%=string%></li>

                                <%
                                    }
                                %>

                            </ul>
                        </div>
                    </div>

                </div>


            </div>

        </div>

    </div>

</div>


<footer>
    <div id="contact" class="footer">
        <div class="container">
            <div class="row pdn-top-30">
                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                    <ul class="location_icon">
                        <li> <a href="#"><img src="icon/facebook.png"></a></li>
                        <li> <a href="#"><img src="icon/Twitter.png"></a></li>
                        <li> <a href="#"><img src="icon/linkedin.png"></a></li>
                        <li> <a href="#"><img src="icon/instagram.png"></a></li>
                    </ul>
                </div>
                <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12">
                    <div class="Follow">
                        <h3>CONTACT US</h3>
                        <span>123 Second Street Fifth <br>Avenue,<br>
                       Manhattan, New York<br>
                       +987 654 3210</span>
                    </div>
                </div>
                <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12">
                    <div class="Follow">
                        <h3>ADDITIONAL LINKS</h3>
                        <ul class="link">
                            <li> <a href="#">About us</a></li>
                            <li> <a href="#">Terms and conditions</a></li>
                            <li> <a href="#"> Privacy policy</a></li>
                            <li> <a href="#">News</a></li>
                            <li> <a href="#"> Contact us</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12">
                    <div class="Follow">
                        <h3> Contact</h3>
                        <div class="row">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                <input class="Newsletter" placeholder="Name" type="text">
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                <input class="Newsletter" placeholder="Email" type="text">
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                <textarea class="textarea" placeholder="comment" type="text">Comment</textarea>
                            </div>
                        </div>
                        <button class="Subscribe">Submit</button>
                    </div>
                </div>
            </div>
            <div class="copyright">
                <div class="container">
                    <p>Copyright 2019 All Right Reserved By <a href="https://html.design/">Free html Templates</a></p>
                </div>
            </div>
        </div>
    </div>
</footer>
</body>

<!-- ##### All Javascript Files ##### -->
<!-- jQuery-2.2.4 js -->
<script src="js/jquery/jquery-2.2.4.min.js"></script>
<!-- Popper js -->
<script src="js/bootstrap/popper.min.js"></script>
<!-- Bootstrap js -->
<script src="js/bootstrap/bootstrap.min.js"></script>
<!-- All Plugins js -->
<script src="js/plugins/plugins.js"></script>
<!-- Active js -->
<script src="js/active.js"></script>

</html>
