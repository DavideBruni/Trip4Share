<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
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

    <title>Results</title>

</head>


<%@ include file="header.jsp" %>

<body class="main-layout">

<div class="row">
    <div class="col-3"></div>
    <div class="col-6 text-center">

        <%
            String aggregation_title = (String) request.getAttribute(SecurityUtils.SUGGESTIONS_EXPLORE_TITLE);
        %>

        <div class="titlepage mt-5">
            <h2><%=aggregation_title%></h2>
        </div>

        <%
            List<String> suggestions = (List<String>) request.getAttribute(SecurityUtils.SUGGESTIONS_EXPLORE);
            String new_url = request.getAttribute("javax.servlet.forward.request_uri").toString() + "?";
            String url = request.getQueryString();
            if(url != null)
                new_url = new_url + url;

        %>

        <div class="row justify-content-around">
            <%
                String current_value = request.getParameter("value").replace(" ", "%20");
                String search_for = request.getParameter("searchFor");
                String suggestion_url = new_url.replace(search_for, "destination");

                for(int i = 0; i < suggestions.size() && i < PagesUtilis.SUGGESTIONS_EXPLORE; i++ ){
            %>

            <a href=<%=suggestion_url.replace(current_value, suggestions.get(i).replace(" ", "%20"))%>>
                <h2 class="blue"><%=suggestions.get(i).toUpperCase()%></h2>
            </a>
            <%
                }
            %>

        </div>
    </div>
    <div class="col-3"></div>
</div>




<%
    String title = (String) request.getAttribute(SecurityUtils.TITLE_PAGE);
%>

<div class="titlepage mt-4">
    <h2><%=title%></h2>
</div>

<%
    List<TripSummaryDTO> trips = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.TRIPS_RESULT);
    if(trips!= null && !trips.isEmpty()){
%>

<div class="row">
    <div class="container col-9">

        <div class="row">
            <div class="col-12 ">
                <%
                    for(int i = 0; i < trips.size() && i < PagesUtilis.OBJECT_PER_PAGE_SEARCH; i++){
                        if(trips.get(i).isDeleted()){
                %>
                <!-- grafica del cancellato -->

                <div disabled class="btn btn-danger">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                        <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                    </svg>
                    Trip was Deleted
                </div>

                <div>
                    <div class="row align-items-center">
                        <div class="col-1"></div>
                        <div class="col-5">
                            <div class="single-blog-thumbnail">
                                <img src="WebContent/images/trip_pic2.jpeg">
                            </div>
                            <p class="pull-left text-left ml-5 mt-5"><%=trips.get(i).getLike_counter()%> Likes</p>
                        </div>

                        <div class="col-12 col-md-6">
                            <!-- Blog Content -->
                            <div class="single-blog-content">
                                <div class="line"></div>
                                <h4><p class="post-headline">
                                    <strong> Title:  </strong><%=trips.get(i).getTitle()%></p>
                                </h4>
                                <a href=<%="trip?id="+trips.get(i).getId()%> class="post-tag"><strong> Destination: </strong><%=trips.get(i).getDestination()%></a>

                                <div class="post-date">
                                    <span>From: <%= trips.get(i).getDepartureDate()%></span>
                                    <br>
                                    <span>To: <%= trips.get(i).getReturnDate()%></span>
                                </div>
                                <%
                                    if(trips.get(i).getOrganizer() != null){
                                %>
                                <p>By <a href=<%="user?username="+trips.get(i).getOrganizer()%>><%=trips.get(i).getOrganizer()%></a></p>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>

                <%}else{%>
                <hr class="invis3">
                <!-- grafica del normale -->
                <div>
                    <div class="row align-items-center">
                        <div class="col-1"></div>
                        <div class="col-5">
                            <div class="single-blog-thumbnail">
                                <img src="WebContent/images/trip_pic2.jpeg">
                            </div>
                            <p class="pull-left text-left ml-5 mt-5"><%=trips.get(i).getLike_counter()%> Likes</p>
                        </div>

                        <div class="col-12 col-md-6">
                            <!-- Blog Content -->
                            <div class="single-blog-content">
                                <div class="line"></div>
                                <h4><a href=<%="trip?id="+trips.get(i).getId()%> class="post-headline">
                                    <strong> Title:  </strong><%=trips.get(i).getTitle()%></a>
                                </h4>
                                <a href=<%="trip?id="+trips.get(i).getId()%> class="post-tag"><strong> Destination: </strong><%=trips.get(i).getDestination()%></a>

                                <div class="post-date">
                                    <span>From: <%= trips.get(i).getDepartureDate()%></span>
                                    <br>
                                    <span>To: <%= trips.get(i).getReturnDate()%></span>
                                </div>
                                <%
                                    if(trips.get(i).getOrganizer() != null){
                                %>
                                <p>By <a href=<%="user?username="+trips.get(i).getOrganizer()%>><%=trips.get(i).getOrganizer()%></a></p>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>

                <div class="row">
                    <div class="col-md-12">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-end">
                                <%
                                    // http://localhost:8080/Trip4Share_war_exploded/explore?searchFor=destination&value=islanda&return=&departure=&page=1
                                    int page_index = (int) request.getAttribute(SecurityUtils.PAGE);
                                    if(url != null)
                                        new_url = new_url + url;

                                    if(!new_url.endsWith("page="+page_index)){
                                        if(url != null){
                                            new_url = new_url + "&page=";
                                        }else{
                                            new_url = new_url + "page=";
                                        }
                                    }else{
                                        int index = new_url.indexOf("page=");
                                        new_url = new_url.substring(0, index) + "page=";
                                    }

                                    if(page_index != 1){
                                %>
                                <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index - 1)%>>Previous</a></li>
                                <%} if(trips.size() > PagesUtilis.TRIPS_PER_PAGE) {
                                %>
                                <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index + 1)%>>Next</a></li>
                                <% }%>
                            </ul>
                        </nav>
                    </div><!-- end col -->
                </div><!-- end row -->
            </div>
        </div>
        <%}else{%>
        No Trips found!
        <%
            }
        %>
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