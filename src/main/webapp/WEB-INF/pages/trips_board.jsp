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

<%
    String title = (String) request.getAttribute(SecurityUtils.TITLE_PAGE);
%>

<div class="titlepage">
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
                    for(int i = 0; i < trips.size() && i < PagesUtilis.TRIPS_PER_PAGE; i++){
                %>
                <hr class="invis3">
                <!-- Single Blog Area  -->
                <div>
                    <div class="row align-items-center">
                        <div class="col-1"></div>
                        <div class="col-5">
                            <div class="single-blog-thumbnail">
                                <!-- <img src="<%= trips.get(i).getImgUrl() %>" alt="Immagine di viaggio"> -->
                                <img src="WebContent/images/blog-image.jpg">
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
                                <p class="pull-right text-right ml-5 mb-5"><%=trips.get(i).getLike_counter()%> Likes</p>
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
                                    String new_url = request.getAttribute("javax.servlet.forward.request_uri").toString() + "?";
                                    String url = request.getQueryString();
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