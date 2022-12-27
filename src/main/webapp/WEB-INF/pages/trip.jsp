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

<%@ include file="/WEB-INF/pages/header.jsp" %>


<body class="tripbgd">

<div class="col-sm-9 mx-auto border border-4 mt-4 tripbgd2 ">



    <!-- Receipe Content Area -->
    <div class="main-form">
        <div class="row">
            <a class="text-right btn btn-primary bottone" href="edittrip" >Modify your trip</a>
        </div>
        <div class="container justify-content-center text-center my-30 ">

            <div class="row justify-content-center">
                <h2 class="pdn-top-40 mt-6 text-center triptitle pull-right "><%= trip.getTitle() %></h2>
                <a href="profile.html">
                    <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="black" class="bi bi-star mt-2" viewBox="0 0 16 16">
                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>

                    </svg>
                </a>

            </div>


            <div class="col-1"></div>
            <div class="receipe-headline my-5 col-10">

                <div class="row">
                    <div class="col-9">
                        <img src="images/signin.jpg">
                    </div>
                    <div class="col-3 pull-right">
                        <div class="receipe-duration margin_top_50 pdn-top-30 ">
                            <h6><%= trip.getPrice() %></h6>
                            <h6><%= trip.getDepartureDate() %></h6>
                            <h6><%= trip.getReturnDate() %></h6>
                            <h6><%= trip.getTags() %></h6>
                          <!--  <h6>Organized By <%= trip.getOrganizer() %></h6> -->

                        </div>
                    </div>
                </div>
            </div>


        </div>

        <div class="col-1"></div>
        <div class="receipe-ratings my-5 col-12">
            <h6>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam cursus magna purus, nec pharetra risus sagittis placerat. Integer aliquam quis nunc et laoreet. In hac habitasse platea dictumst. Praesent congue est eget magna euismod varius id egestas nulla. Suspendisse sollicitudin ligula purus, a auctor tortor pretium ut. Praesent ac dui diam. Nam malesuada turpis dolor, vel pulvinar nisl tincidunt at. Etiam vehicula dui arcu, vitae dapibus eros tincidunt ac. Suspendisse elementum sapien id tempus fringilla. Nam neque erat, interdum at tincidunt vitae, facilisis sit amet lorem. Duis mattis tincidunt felis, non feugiat arcu ornare a. In pharetra arcu nec massa consequat dapibus.</h6>
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

        <div class="row">
            <a class="text-right btn btn-primary bottone" href="modify_trip.html" >Modify your trip</a>
            <a class="text-right btn btn-primary bottone" href="modify_trip.html" >Send Join Request</a>
        </div>
        <p class="grey pull-right ">Last Modified</p>
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
