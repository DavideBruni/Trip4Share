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


   <%@ include file="header.jsp" %>

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
                           <i><img src="<%=t.getImgUrl()%>" alt="icon"/></i>
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
                            <img src="<%= t.getImgUrl() %>" alt="Immagine di viaggio">
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
          <a class="text-right btn btn-primary bottone" href="" >Refresh</a>
      </div>
   </div>

</div>


 <%@ include file="footer.jsp" %>

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