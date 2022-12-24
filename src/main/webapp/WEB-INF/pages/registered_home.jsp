<%@ page import="it.unipi.lsmd.dto.AuthenticatedUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
   <head>
      <!-- basic -->
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <!-- mobile metas -->
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="viewport" content="initial-scale=1, maximum-scale=1">
      <!-- bootstrap css -->
      <link rel="stylesheet" href="WebContent/css/bootstrap.min.css">
      <!-- style css -->
      <link rel="stylesheet" href="WebContent/css/style.css">
      <!-- Responsive-->
      <link rel="stylesheet" href="WebContent/css/responsive.css">
      <!-- fevicon -->
      <link rel="icon" href="WebContent/images/fevicon.png" type="image/gif" />
      <!-- Scrollbar Custom CSS -->
      <link rel="stylesheet" href="WebContent/css/jquery.mCustomScrollbar.min.css">
      <!-- Tweaks for older IEs-->
      <!-- owl stylesheets --> 
      <link rel="stylesheet" href="WebContent/css/owl.carousel.min.css">
      <link rel="stylesheet" href="WebContent/css/owl.theme.default.min.css">
      <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
   </head>      
      
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
                              <div class="logo"> <a href="index.html"><img src="WebContent/images/logo.png" alt="#"></a> </div>
                           </div>
                        </div>
                     </div>
                     <div class="col-xl-9 col-lg-9 col-md-9 col-sm-9">
                        <div class="menu-area">
                           <div class="limit-box">
                              <nav class="main-menu">
                                 <ul class="menu-area-main">

                                    <%
                                       AuthenticatedUserDTO userDTO = (AuthenticatedUserDTO) session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);

                                    %>
                                    <% if(userDTO.getUsername()!=null) { %>
                                    <li class="active"> Welcome  <%= userDTO.getUsername()%></li>
                                    <%}%>
                                    <li><a href=<%="/user?username="+userDTO.getUsername()%>>Profile</a> </li>
                                    <li><a href="../search.html">Search</a></li>
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
      
<body class="main-layout">
 <!-- Suggested travels -->
 <%
    List<TripSummaryDTO> suggestedTrips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.SUGGESTED_TRIPS);
    if(suggestedTrips!= null && !suggestedTrips.isEmpty()){
 %>
          <div class="row">

            <div id="travel" class="traveling pl-2">
               <div class="container col-12">
                  <div class="row">
                     <div class="col-12">
                        <div class="titlepage">
                           <h3 >Suggested Travels </h3>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-1"> </div>
                     <% for(TripSummaryDTO t : suggestedTrips){ %>
                     <div class="col-2">
                        <div class="traveling-box">
                           <!--<i><img src=<%=t.getImgUrl()%> alt="icon"/></i>-->
                            <i><img src="WebContent/icon/travel-icon.png" alt="icon" width="60%"/></i>
                            <strong><h6>
                              <%=t.getDestination() %>
                           </h6></strong>
                        <h7>
                              <%= "From:<br>"+t.getDepartureDate()+"<br>to:<br>"+t.getReturnDate() %>
                        </h7>
                        </div>
                     </div>
                     <% }%>

                     <div class="col-1"> </div>

                  </div>
               </div>
             </div>
            <!-- end traveling -->

         </div>

 <% } %>
 <!-- End suggested travels -->
      
      
<div class="row">
   <div class="container col-9">
      <div class="titlepage">
         <h2>Organized trips by people you follow</h2>
      </div>
       <%
           List<TripSummaryDTO> trips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.FOLLOWING_USER_TRIPS);
           if(trips!= null && !trips.isEmpty()){
       %>
      <div class="row">
         <div class="col-12 ">
            <% for(TripSummaryDTO t : trips){ %>
             <hr class="invis3">
            <!-- Single Blog Area  -->
            <div>
                  <div class="row align-items-center">
                     <div class="col-1"></div>
                     <div class="col-5">
                        <div class="single-blog-thumbnail">
                              <img src=<%= t.getImgUrl() %> alt="Immagine di viaggio">
                              <div class="post-date">
                                  <span>From: <%= t.getDepartureDate()%></span>
                                  <br>
                                  <span>To: <%= t.getReturnDate()%></span>
                              </div>
                        </div>
                   
                     </div>
                     
                     <div class="col-12 col-md-6">
                        <!-- Blog Content -->
                        <div class="single-blog-content">
                              <div class="line"></div>
                              <a href="#" class="post-tag"><%=t.getDestination()%></a>
                              <h4><a href="#" class="post-headline"><%=t.getTitle()%></a></h4>
                              <div class="post-meta">
                                 <p>By <a href="#">james smith</a></p>
                                 <p><%=t.getLike_counter()%> likes</p>
                              </div>
                        </div>
                     </div>
                  </div>
            </div>
             <%}%>

            <div class="row">
               <div class="col-md-12">
                  <nav aria-label="Page navigation">
                     <ul class="pagination justify-content-end">
                           <li class="page-item"><a class="page-link" href="../trip_board.html">View More</a></li>
                     </ul>
                  </nav>
               </div><!-- end col -->
         </div><!-- end row -->      
         </div>
      </div>
       <%}%>
   </div>

  
   <div class="col-3">  
      <div class="sidebar col-11"> 
         <div class="widget">
            <div class="titlepage ">
               <h3>Suggested Users </h3   >
            </div>
            <div class="blog-list-widget">
               <div class="list-group">
                  <a href="single.html" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="w-100 justify-content-between">
                           <img src="WebContent/icon/travel-icon.png" alt="" class="img-fluid float-left col-4" >
                           <h5 class="mb-1">5 Beautiful buildings you need to before dying</h5>
                           <small>12 Jan, 2016</small>
                        </div>
                  </a>
      
                  <a href="single.html" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="w-100 justify-content-between">
                           <img src="WebContent/icon/travel-icon.png" alt="" class="img-fluid float-left col-4" >                                 <h5 class="mb-1">Let's make an introduction for creative life</h5>
                           <small>11 Jan, 2016</small>
                        </div>
                  </a>
      
                  <a href="single.html" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="w-100 last-item justify-content-between">
                           <img src="WebContent/icon/travel-icon.png" alt="" class="img-fluid float-left col-4" >                                 <h5 class="mb-1">Did you see the most beautiful sea in the world?</h5>
                           <small>07 Jan, 2016</small>
                        </div>
                  </a>
               </div>
            </div><!-- end blog-list -->
      </div><!-- end widget -->
      </div>
   </div>

</div>
        
   
 
     <!-- footer -->
     <footer>
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