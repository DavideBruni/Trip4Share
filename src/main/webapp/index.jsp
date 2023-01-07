<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
   <head>
      <!-- basic -->
      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      
      <link rel="stylesheet" href="WebContent/css/style.css">
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  
   </head>
   <!-- body -->
   <body class="main-layout">

   <%@ include file="/WEB-INF/pages/header.jsp" %>

   <div class="banner-main">
         <img src="WebContent/images/night-skyline-wallpaper-25.jpg" alt="#"/>
         <div class="container">
            <div class="text-bg">
               <h1>Welcome to<br><strong class="white">trip4share</strong></h1>
            </div>
         </div>
      </div>
         
         <div class="bg">
            <div class="container">
                     <div class="box justify-content-center">
                        <p>

                        </p>
                       
                     </div>
                 
               
            </div>
           
         </div>
      </div>
 
      <!--Most populars -->
      <%
         List<TripSummaryDTO> mostPopulars = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.MOST_POPULAR);
         if(mostPopulars!= null && !mostPopulars.isEmpty()){
      %>
      <div class="Tours pb-5 mb-5">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <div class="titlepage mt-5">
                     <h2>Most popular trips</h2>
                     <span>Hei there! Welcome on our website! Here we introduce you some of the most popular trips amongst out user to get you started</span>
                  </div>
               </div>
            </div>
            <section>
               <div class="row">
                  <div class="col-md-12">
                     <div class="owl-carousel owl-theme">
                        <% for(TripSummaryDTO t : mostPopulars){ %>
                        <div class="item">
                           <img class="img-responsive" src="WebContent/images/trip_pic1.jpg" alt="Image of a trip" />
                           <h3><a class=""text-decoration-none href=<%="trip?id="+t.getId()%>> <%= t.getTitle() %></a></h3>
                           <p>Destination: <%= t.getDestination()%> <br>
                              Departure Date: <%=t.getDepartureDate()%> <br>
                              Return Date: <%=t.getReturnDate()%>
                           </p>
                        </div>
                        <% } %>
                     </div>
                  </div>
               </div>
            </section>
         </div>
      </div>
      <%
         }
      %>
      <!-- end Most populars -->

       <!--Cheapest trips -->
      <%
         List<TripSummaryDTO> cheapest = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.CHEAPEST_TRIPS);
         if(cheapest!= null && !cheapest.isEmpty()){
      %>
       <div class="Tours pt-5 pb-5 mb-5">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <div class="titlepage">
                     <h2>Cheapest Trips</h2>
                     <span>We will all travel so much more if only trips were free... Unfortunately we still can't do that. <br> But in the mean time here are the cheapest trips on out website!</span>
                  </div>
               </div>
            </div>
            <section id="demos">
               <div class="row">
                  <div class="col-md-12">
                     <div class="owl-carousel owl-theme">
                        <% for(TripSummaryDTO t : cheapest){ %>
                        <div class="item">
                           <img class="img-responsive" src="WebContent/images/trip_pic1.jpg" alt="Image of a trip" />
                           <h3> <a class="text-decoration-none" href=<%="trip?id="+t.getId()%>> <%= t.getTitle() %> </a></h3>
                           <p>Destination: <%= t.getDestination()%> <br>
                              Departure Date: <%=t.getDepartureDate()%> <br>
                              Return Date: <%=t.getReturnDate()%>
                           </p>
                        </div>
                        <% } %>
                     </div>
                  </div>
               </div>
            </section>
         </div>
      </div>
      <%
         }
      %>
      <!-- end cheapest -->


   <%@ include file="/WEB-INF/pages/footer.jsp" %>
      <!-- Javascript files-->
      <script src="WebContent/js/jquery.min.js"></script>
      <script src="WebContent/js/popper.min.js"></script>
      <script src="WebContent/js/bootstrap.bundle.min.js"></script>
      <script src="WebContent/js/jquery-3.0.0.min.js"></script>
      <script src="WebContent/js/plugin.js"></script>
      <!-- sidebar -->
      <script src="WebContent/js/jquery.mCustomScrollbar.concat.min.js"></script>
      <script src="WebContent/js/custom.js"></script>
      <!-- javascript --> 
      <script src="WebContent/js/owl.carousel.js"></script>
      <script>
         $(document).ready(function() {
           var owl = $('.owl-carousel');
           owl.owlCarousel({
             margin: 10,
             nav: true,
             loop: true,
             responsive: {
               0: {
                 items: 1
               },
               600: {
                 items: 2
               },
               1000: {
                 items: 3
               }
             }
           })
         })
      </script>
     
   </body>
</html>