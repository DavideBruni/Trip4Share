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
     
      <!-- header -->
      <header>
         <!-- header inner -->
         <div class="header">
            <div class="header_white_section">
               <div class="container-fluid">
                  <div class="row">
                     <div class="col-md-12">
                        <div class="header_information">
                           <ul>
                              <li><img src="WebContent/images/1.png" alt="#"/> 145.street road new York</li>
                              <li><img src="WebContent/images/2.png" alt="#"/> +71  5678954378</li>
                              <li><img src="WebContent/images/3.png" alt="#"/> Demo@hmail.com</li>
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
                           <div class="logo"> <a href="index.jsp"><img src="WebContent/images/logo.png" alt="#" height="auto"></a> </div>
                        </div>
                     </div>
                  </div>
                  <div class="col-xl-9 col-lg-9 col-md-9 col-sm-9">
                     <div class="menu-area">
                        <div class="limit-box">
                           <nav class="main-menu">
                              <ul class="menu-area-main">
                                 <!-- <li class="active"> <a href="#">Home</a> </li> -->
                                 <li> <a href="signup.html">Sign Up</a> </li>
                                 <li><a href="signin.html">Sign In</a></li>
                                 <li><a href="search.html">Search Trips</a></li>
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
      <!-- end header -->
   
   

      <div class="banner-main">
         <img src="WebContent/images/banner.jpg" alt="#"/>
         <div class="container">
            <div class="text-bg">
               <h1>Welcome to<br><strong class="white">trip4share</strong></h1>
     
            </div>
         </div>
      </div>
         
         <div class="bg">
            <div class="container">
                     <div class="box justify-content-center">
                        <p> <span>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure thereThere are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there</span></p>
                       
                     </div>
                 
               
            </div>
           
         </div>
      </div>
 
      <!--Most populars -->
      <%
         List<TripSummaryDTO> mostPopulars = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.MOST_POPULAR);
         if(mostPopulars!= null && !mostPopulars.isEmpty()){
      %>
      <div class="Tours">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <div class="titlepage">
                     <h2>Most popular trips</h2>
                     <span>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters,</span>
                  </div>
               </div>
            </div>
            <section id="demos">
               <div class="row">
                  <div class="col-md-12">
                     <div class="owl-carousel owl-theme">
                        <% for(TripSummaryDTO t : mostPopulars){ %>
                        <div class="item">
                           <img class="img-responsive" src="<%= t.getImgUrl()%>" alt="Image of a trip" />
                           <h3><a class=""text-decoration-none href=""#> <%= t.getTitle() %></a><</h3>
                           <p>Destination: <%= t.getDestination()%> <br>
                              Partenza: <%=t.getDepartureDate()%> | Ritorno: <%=t.getReturnDate()%>
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
       <div class="Tours pt-5 pb-5">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <div class="titlepage">
                     <h2>Cheapest Trips</h2>
                     <span>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters,</span> 
                  </div>
               </div>
            </div>
            <section id="demos">
               <div class="row">
                  <div class="col-md-12">
                     <div class="owl-carousel owl-theme">
                        <% for(TripSummaryDTO t : cheapest){ %>
                        <div class="item">
                           <img class="img-responsive" src="<%= t.getImgUrl()%>" alt="Image of a trip" />
                           <h3> <a class="text-decoration-none" href="#"> <%= t.getTitle() %> </a></h3>
                           <p>Destination: <%= t.getDestination()%> <br>
                              Partenza: <%=t.getDepartureDate()%> | Ritorno: <%=t.getReturnDate()%>
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
     

      <footer class="pt-5">
         <div id="contact" class="footer">
            <div class="container">
               <div class="row pdn-top-30">
                  <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                     <ul class="location_icon">
                        <li> <a href="#"><img src="WebContent/icon/facebook.png"></a></li>
                        <li> <a href="#"><img src="WebContent/icon/Twitter.png"></a></li>
                        <li> <a href="#"><img src="WebContent/icon/linkedin.png"></a></li>
                        <li> <a href="#"><img src="WebContent/icon/instagram.png"></a></li>
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
      <!-- end footer -->
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