<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.AuthenticatedUserDTO" %>
<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.PagesUtilis" %>
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

       <title>Personal homepage</title>
   </head>


   <%@ include file="header.jsp" %>



<body class="main-layout col-12 justify-content-center">
 <!-- Suggested travels -->

 <% List<TripSummaryDTO> trips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.FOLLOWING_USER_TRIPS);
     List<TripSummaryDTO> suggestedTrips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.SUGGESTED_TRIPS);
     List<OtherUserDTO> users = (List<OtherUserDTO>) request.getAttribute(SecurityUtils.SUGGESTED_USERS);

     if(trips==null && suggestedTrips==null && users==null){ %>

 <div class="row text-center titlepage ml-4 mr-4 mt-4">
     <h2>We are very sorry you don't follow enough users for us to recommend you something<br> Please start following someone and try again ;)</h2>
 </div>

 <%  }else{ %>
 <%
    if(suggestedTrips!= null && !suggestedTrips.isEmpty()){
 %>
          <div>

            <div id="travel" class="traveling pl-2">
               <div class="container col-12">
                  <div class="row">
                     <div class="col-12">
                        <div class="titlepage">
                           <h3> Suggested Travels  </h3>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-1"> </div>
                     <% for(TripSummaryDTO t : suggestedTrips){ %>
                     <div class="col-2">
                        <div class="traveling-box">
                            <img class="myimg" src="WebContent/images/trip_pic1.jpg" alt="icon"/>
                            <h2>
                                <strong>
                                    <a href=<%="trip?id="+t.getId()%> class="post-headline">
                                        <%=t.getDestination() %></a>
                                </strong>
                           </h2>
                        <h6>
                              <%= "<strong>From:</strong><br>"+t.getDepartureDate()+"<br><strong>to:</strong><br>"+t.getReturnDate() %>
                        </h6>
                     </div>
                     </div>
                     <% }%>

                     <div class="col-1"> </div>

                  </div>
               </div>
             </div>
            <!-- end traveling -->

         </div>

 <% }else{%>
 <!-- End suggested travels -->
 <div class="col-9 text-center">
     <h3>We are sorry! We still don't have trips to recommend you<br> Please follow some more people and try again ;)</h3>
 </div>

   <%}%>
<div class="row">
   <div class="container col-9">
      <div class="titlepage">
         <h3 > Organized trips by people you follow</h3>
      </div>
       <%

           if(trips!= null && !trips.isEmpty()){
       %>
      <div class="row">
         <div class="col-12 ">
            <% int i=0;
                for(TripSummaryDTO t : trips){ %>
             <hr class="invis3">
            <!-- Single Blog Area  -->
            <div>
                  <div class="row align-items-center">
                     <div class="col-1"></div>
                     <div class="col-5">
                        <div class="single-blog-thumbnail">
                            <img src="WebContent/images/blog-image.jpg">
                        </div>
                     </div>
                     
                     <div class="col-12 col-md-6">
                        <!-- Blog Content -->
                        <div class="single-blog-content">
                              <div class="line"></div>
                            <h4><a href=<%="trip?id="+t.getId()%> class="post-headline"><strong>Title:  </strong><%=t.getTitle()%></a></h4>
                            <a href=<%="trip?id="+t.getId()%> class="post-tag"><strong> Destination: </strong><%=t.getDestination()%></a>

                                  <div class="post-date">
                                      <span>From: <%= t.getDepartureDate()%></span>
                                      <br>
                                      <span>To: <%= t.getReturnDate()%></span>
                                  </div>
                                 <p>By <a href=<%="user?username="+t.getOrganizer()%>><%=t.getOrganizer()%></a></p>
                        </div>
                     </div>
                  </div>
            </div>
             <%
                i++;
                if(i == PagesUtilis.OBJECT_PER_PAGE_SEARCH)
                break;}%>

            <div class="row">
               <div class="col-md-12">
                  <nav aria-label="Page navigation">
                     <ul class="pagination justify-content-end">
                         <% Integer pag = Integer.valueOf((Integer) request.getAttribute(SecurityUtils.PAGE));
                             if(pag!=1){  %>
                            <li class="page-item"><a class="page-link" href="home?page=<%=pag-1%>">Previous</a></li>
                         <%} if(trips.size() > PagesUtilis.OBJECT_PER_PAGE_SEARCH) {%>
                            <li class="page-item"><a class="page-link" href="home?page=<%=pag+1%>">Next</a></li>
                        <% }%>
                     </ul>
                  </nav>
               </div><!-- end col -->
            </div><!-- end row -->
         </div>
      </div>
       <%}else{%>
       <!-- Se non ci sono suggerimenti -->
       <div class="col-12 text-center">
           <h3>We are sorry! The people you follow haven't organized any trip yet<br> Please follow some more people and try again ;)</h3>
       </div>
       <%}%>
   </div>

    <%

        if(users!= null && !users.isEmpty()){
    %>
  
   <div class="col-3">  
      <div class="sidebar col-11"> 
         <div class="widget">
            <div class="titlepage ">
                <h3><strong>Suggested Users</strong> </h3>
            </div>
            <div class="blog-list-widget">
               <div class="list-group">
               <% for(OtherUserDTO u : users){ %>
                       <div class="row card-body align-items-start ">
                           <img src="WebContent/images/profile2.jpeg" alt="" class="col-4">
                           <a class=" col-8" href=<%="user?username="+u.getUsername()%>>
                           <h5 class="mt-4"><%=u.getUsername()%></h5>
                           </a>
                        </div>

                <% } %>
               </div>
            </div>
         </div>
      </div>
             <!-- end blog-list -->

   </div>
    <% }else{ %>

    <div class="col-3 text-center">
        <h3>We are sorry! We still don't have users to recommend you<br> Please follow some more people and try again ;)</h3>
    </div>
    <%}%>
</div>

 <% }%>
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