<%@ page import="it.unipi.lsmd.dto.TripSummaryDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 17/12/2022
  Time: 09:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title> Search </title>

    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>


<body class="main-layout ">
<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="main-form .form-horizontal" >
    <div class="titlepage">
        <h2>What are you looking for?</h2>
    </div>

    <div class="center ">
        <ul class="nav justify-content-center nav-pills" id="pills-tab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-1" role="tab" aria-controls="pills-home" aria-selected="true">Trips by Destination</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-2" role="tab" aria-controls="pills-profile" aria-selected="false">Trips by tags</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-3" role="tab" aria-controls="pills-contact" aria-selected="false">Trips By Price </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-4" role="tab" aria-controls="pills-contact" aria-selected="false">Users </a>
            </li>
        </ul>


        <div class="tab-content " id="pills-tabContent" margin: 0-autos>

            <!-- Trip By Destination -->
            <div class="tab-pane fade show active" id="pills-1" role="tabpanel" aria-labelledby="pills-home-tab">
                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">
                        <form method="POST">
                            <label >Insert destination</label>
                            <input class="form-control" placeholder="" type="text" name="destination" size="50">

                            <label >Departure Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="departure_date">

                            <label >Return Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="return_date">

                            <button class="btn btn-primary pull-right mr-5" type="submit" value="submit">Search</button>
                        </form>

                    </div>
                </div>
            </div>

            <!-- Trip By Tag -->
            <div class="tab-pane fade " id="pills-2" role="tabpanel" aria-labelledby="pills-profile-tab">

                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">

                        <form method="POST">

                            <label>Category</label>
                            <select class="form-control" name="tags">
                                <option>Any</option>
                                <option>Option 1</option>
                                <option>Option 2</option>
                                <option>Option 3</option>
                            </select>



                            <label >Departure Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="departure_date">

                            <label >Return Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="return_date">
                        </form>


                    </div>
                </div>

            </div>

            <!-- Trip By Price -->
            <div class="tab-pane fade" id="pills-3" role="tabpanel" aria-labelledby="pills-contact-tab">

                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">

                        <form method="POST">
                            <label >Min Price</label>
                            <input class="form-control" placeholder="00.0" type="text" name="00.0">

                            <label >Max Price</label>
                            <input class="form-control" placeholder="00.0" type="text" name="00.0">

                            <label >Departure Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="departure_date">

                            <label >Return Date</label>
                            <input class="form-control" placeholder="Any" type="date" name="return_date">
                        </form>
                    </div>

                </div>
            </div>

            <!-- User -->
            <div class="tab-pane fade" id="pills-4" role="tabpanel" aria-labelledby="pills-contact-tab">
                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">
                        <form method="POST">
                            <label >Insert username</label>
                            <input  class="form-control" placeholder="" type="text" name="username" size="50">

                            <button class="btn btn-primary pull-right mr-5" type="submit" value="submit">Search</button>
                        </form>
                    </div>

                </div>
            </div>

        </div>

    </div>



    <%
        ArrayList<Object> results = (ArrayList<Object>) request.getAttribute(SecurityUtils.SEARCH_RESULTS);
        if(results != null && results.size() > 0){
    %>
    <div class="titlepage">
        <h2>Results</h2>
    </div>

    <div class="container" >
        <div class="row">
            <div class="col-12 ">
                <hr class="invis3">
                <!-- Single Blog Area  -->
                <%
                    if(results.get(0) instanceof TripSummaryDTO){
                        for(Object result : results){
                            TripSummaryDTO trip = (TripSummaryDTO) result;
                %>
                <div class="single-blog-area blog-style-2 mb-50 wow fadeInUp" data-wow-delay="0.2s" data-wow-duration="1000ms">
                    <div class="row align-items-center">
                        <div class="col-12 col-md-6">
                            <div class="single-blog-thumbnail">
                                <img src="images/blog-image.jpg" alt="">
                                <div class="post-date">
                                    <a>12 <span>march</span></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <!-- Blog Content -->
                            <div class="single-blog-content">
                                <div class="line"></div>
                                <a class="post-tag"><%=trip.getDestination()%></a>
                                <h4><a href=<%="trip?id=" + trip.getId()%> class="post-headline"> <%=trip.getTitle()%></a></h4>
                                <p><%=trip.getDepartureDate()%> <br> <%=trip.getReturnDate()%> </p>
                                <div class="post-meta">
                                    <p>By <a href="#">james smith</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }else if(results.get(0) instanceof OtherUserDTO){
                        for(Object result : results){
                            OtherUserDTO user = (OtherUserDTO) result;
                %>

                <h4><a href=<%="user?username=" + user.getUsername()%>> <%=user.getUsername()%> </a></h4>

                <%
                        }
                    }
                %>


            </div>
        </div>
    </div>
    <%
        }
    %>


</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>

</body>

</html>