<%@ page import="it.unipi.lsmd.dto.AuthenticatedUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/profile.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <%
    AuthenticatedUserDTO user = (AuthenticatedUserDTO) request.getAttribute("user");
    %>
    <title><%= user.getUsername() %></title>
</head>

<body>

<%@ include file="header.jsp" %>

<div class="padding justify-content-center">
    <div >
        <!-- Column -->
        <div class="card">
            <div class="card-body little-profile text-center">
                <div class="pro-img"><img src="https://i.imgur.com/8RKXAIV.jpg" alt="user"></div>
                <h3 class="m-b-0 white">USERNAME | Nome e cognome</h3>
                <h6 class="white">gg/mm/aaaa</h6>



                <a href="javascript:void(0)" class="m-t-10 waves-effect waves-dark btn btn-primary btn-md btn-rounded" data-abc="true">Follow</a>
                <div class="row text-center m-t-20">
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">434K</h3><small>Followers</small>
                    </div>
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">5454</h3><small>Following</small>
                    </div>
                    <div class="col-4">
                        <h3 class="m-b-0 font-light white">5454</h3><small>Average Rating</small>
                    </div>

                </div>
            </div>
            <div class="text-center">
                <h4 class="m-t-10"> Nazionalità </h4>
                <h4 class="m-t-10"> Lingue Parlate </h4>
                <h4 class="m-t-10"> Bio</h4>
            </div>
        </div>
    </div>
</div>


<ul class="nav nav-pills mb-3 justify-content-center" id="pills-tab" role="tablist">
    <li class="nav-item">
        <a class="nav-link active" id="review-tab" data-toggle="pill" href="#review-content" role="tab" aria-controls="pills-home" aria-selected="true">Reviews</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="organized-tab" data-toggle="pill" href="#organized-content" role="tab" aria-controls="pills-profile" aria-selected="false">Organized Trips</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="past-tab" data-toggle="pill" href="#past-content" role="tab" aria-controls="pills-contact" aria-selected="false">Past Trips</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="wishlist-tab" data-toggle="pill" href="#wishlist-content" role="tab" aria-controls="pills-contact" aria-selected="false">WishList</a>
    </li>
</ul>


<div class="tab-content" id="pills-tabContent">
    <!-- Reviews DA METTERE LE INFORMAZIONI SULLE PRIME 3 RECENSIONI -->
    <div class="tab-pane fade show active" id="review-content" role="tabpanel" aria-labelledby="pills-home-tab" aria-expanded="true">
        <div class="container" >
            <hr class="invis3">
            <div class = "row justify-content-center ">
                <div class="card" style="width: 18rem;">

                    <div class="card-body">
                        <h5 class="card-title white">Card title</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>

                    </div>
                </div>
                <div class="card" style="width: 18rem;">

                    <div class="card-body">
                        <h5 class="card-title white">Card title</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>

                    </div>
                </div>
                <div class="card" style="width: 18rem;">

                    <div class="card-body">
                        <h5 class="card-title white">Card title</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>

                    </div>
                </div>
            </div>
            <hr class="invis3">
            <div class="row">
                <div class="col-md-12">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-end">
                            <li class="page-item"><a class="page-link" href="/reviews?user=<%user.getUsername();%>">View More</a></li>
                        </ul>
                    </nav>
                </div><!-- end col -->
            </div><!-- end row -->


        </div>

    </div>
    <!-- Organized Trips -->
    <%
        List<TripSummaryDTO> organized = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.ORGANIZED_TRIPS);
        if(organized!= null && !organized.isEmpty()){
    %>
    <div class="tab-pane fade" id="organized-content" role="tabpanel" aria-labelledby="pills-profile-tab">

        <div class="container" >
            <div class="row">
                <div class="col-12 ">
                    <hr class="invis3">
                    <% for(TripSummaryDTO t : organized){ %>
                    <!-- Single Blog Area  -->
                    <div class="single-blog-area blog-style-2 mb-50 wow fadeInUp" data-wow-delay="0.2s" data-wow-duration="1000ms">
                        <div class="row align-items-center">
                            <div class="col-12 col-md-6">
                                <div class="single-blog-thumbnail">
                                    <i><img src="<%=t.getImgUrl()%>" alt="icon"/></i>
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
                                    <p>Curabitur venenatis efficitur lorem sed tempor. Integer aliquet tempor cursus. Nullam vestibulum convallis risus vel condimentum. Nullam auctor lorem in libero luctus, vel volutpat quam tincidunt.</p>
                                    <div class="post-meta">
                                        <p>By <a href="#"><%t.getOrganizer();%></a></p>
                                        <p> <% t.getLike_counter(); %> likes</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="invis3">



                    <%}%>
                    <div class="row pull-right">
                        <div class="col-md-12">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-start">
                                    <li class="page-item"><a class="page-link" href="/organizedtrip?username=<%user.getUsername();%>${currentPage - 1}">Previous</a></li>
                                    <li class="page-item">
                                        <a class="page-link" href="/organizedtrip?username=<% user.getUsername(); %>page=${currentPage + 1}">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div><!-- end col -->
                    </div><!-- end row -->
                </div>
            </div>
        </div>



    </div>
    <% } %>
    <!-- End organized trip -->
    <!-- Past Trips -->
    <%
        List<TripSummaryDTO> past = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.PAST_TRIPS);
        if(past!= null && !past.isEmpty()){
    %>
    <div class="tab-pane fade  " id="past-content" role="tabpanel" aria-labelledby="pills-home-tab">
        <div class="container" >
            <div class="row">
                <div class="col-12 ">
                    <hr class="invis3">
                    <% for(TripSummaryDTO t : past){ %>
                    <!-- Single Blog Area  -->
                    <div class="single-blog-area blog-style-2 mb-50 wow fadeInUp" data-wow-delay="0.2s" data-wow-duration="1000ms">
                        <div class="row align-items-center">
                            <div class="col-12 col-md-6">
                                <div class="single-blog-thumbnail">
                                    <i><img src="<%=t.getImgUrl()%>" alt="icon"/></i>
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
                                    <p>Curabitur venenatis efficitur lorem sed tempor. Integer aliquet tempor cursus. Nullam vestibulum convallis risus vel condimentum. Nullam auctor lorem in libero luctus, vel volutpat quam tincidunt.</p>
                                    <div class="post-meta">
                                        <p>By <a href="#"><%t.getOrganizer();%></a></p>
                                        <p><% t.getLike_counter(); %> likes</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="invis3">
                    <%}%>
                    <div class="row pull-right">
                        <div class="col-md-12">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-start">
                                    <li class="page-item"><a class="page-link" href="/history:page=${currentPage - 1}">Previous</a></li>
                                    <li class="page-item">
                                        <a class="page-link" href="/history:page=${currentPage + 1}">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div><!-- end col -->
                    </div><!-- end row -->
                </div>
            </div>
        </div>

    </div>
    <% } %>
    <!-- Wishlist -->
    <%
        List<TripSummaryDTO> wishlist = (List<TripSummaryDTO>) request.getAttribute(SecurityUtils.WISHLIST_KEY);
        if(wishlist!= null && !wishlist.isEmpty()){
    %>
    <div class="tab-pane fade  " id="wishlist-content" role="tabpanel" aria-labelledby="pills-home-tab">
        <div class="container" >
            <div class="row">
                <div class="col-12 ">
                    <hr class="invis3">
                    <% for(TripSummaryDTO t : wishlist){ %>
                    <!-- Single Blog Area  -->
                    <div class="single-blog-area blog-style-2 mb-50 wow fadeInUp" data-wow-delay="0.2s" data-wow-duration="1000ms">
                        <div class="row align-items-center">
                            <div class="col-12 col-md-6">
                                <div class="single-blog-thumbnail">
                                    <i><img src="<%=t.getImgUrl()%>" alt="icon"/></i>
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
                                    <p>Curabitur venenatis efficitur lorem sed tempor. Integer aliquet tempor cursus. Nullam vestibulum convallis risus vel condimentum. Nullam auctor lorem in libero luctus, vel volutpat quam tincidunt.</p>
                                    <div class="post-meta">
                                        <p>By <a href="#"><%t.getOrganizer();%></a></p>
                                        <p><% t.getLike_counter(); %></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr class="invis3">
                    <%}%>
                    <div class="row pull-right">
                        <div class="col-md-12">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-start">
                                    <li class="page-item"><a class="page-link" href="/wishlist:page=${currentPage + 1}">Previous</a></li>
                                    <li class="page-item">
                                        <a class="page-link" href="/wishlist:page=${currentPage + 1}">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div><!-- end col -->
                    </div><!-- end row -->
                </div>
            </div>
        </div>

    </div>
    <% } %>
</div>



</body>