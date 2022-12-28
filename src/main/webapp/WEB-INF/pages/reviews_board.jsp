<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unipi.lsmd.dto.ReviewDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 28/12/2022
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="stylesheet" href="css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <!-- style css -->
    <link rel="stylesheet" href="css/profile.css">
    <link rel="stylesheet" href="css/style.css">

    <title>Reviews</title>
</head>
<body>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div id="wrapper">

    <section class="section wb main-layout">
        <div class="row titlepage justify-content-center" >
            <h3>Reviews</h3>
        </div>

        <div class="container">

            <div >
                <div class="page-wrapper">

                    <div class="col-lg-10 offset-lg-1">
                        <div class="banner-spot clearfix">
                            <div class="banner-img">
                                <img src="upload/banner_02.jpg" alt="" class="img-fluid">
                            </div><!-- end banner-img -->
                        </div><!-- end banner -->
                    </div><!-- end col -->


                    <hr class="invis">

                    <div class="blog-grid-system">

                        <div >
                            <div class="blog-box">
                                <div class="post-media">
                                    <a href="single.html" title="">
                                        <img src="im/blog_travel_01.jpg" alt="" class="img-fluid">
                                        <div class="hovereffect">
                                            <span></span>
                                        </div><!-- end hover -->
                                    </a>
                                </div><!-- end media -->

                                <%
                                    List<ReviewDTO> reviews = (List<ReviewDTO>) request.getAttribute(SecurityUtils.REVIEWS_KEY);
                                    if(reviews != null && reviews.size() > 0){
                                        for(ReviewDTO review : reviews){
                                %>

                                <div class="blog-meta big-meta">
                                    <h4><%=review.getRating() + "/5\t\t " + review.getTitle()%></h4>
                                    <p><%=review.getTitle()%></p>
                                    <small><%=review.getDate()%></small>
                                    <small><a href=<%="user?username="+review.getAuthor()%>><%=review.getAuthor()%></a></small>
                                </div><!-- end meta -->
                                <%
                                        }
                                    }else{
                                %>
                                        No reviews for this user
                                <%
                                    }
                                %>
                            </div><!-- end blog-box -->
                        </div><!-- end col -->





                    </div><!-- end blog-grid-system -->
                </div><!-- end page-wrapper -->

                <hr class="invis3">

                <div class="row pull-right">
                    <div class="col-md-12">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-start">
                                <%
                                    String url = request.getQueryString();
                                    String[] partial_url = url.split("=");
                                    int page_index = Integer.parseInt(partial_url[partial_url.length - 1]);
                                    String new_url = "user?";
                                    for(int i = 0; i < partial_url.length - 1; i++)
                                        new_url = new_url + partial_url[i] + "=";

                                    if(page_index != 1){
                                        new_url = new_url + (page_index - 1);
                                %>
                                        <li class="page-item"><a class="page-link" href=<%=new_url%>>Previous</a></li>
                                <%
                                    }else{
                                        new_url = new_url + (page_index + 1);
                                %>
                                <li class="page-item"><a class="page-link" href=<%=new_url%>>Next</a></li>
                                <%
                                    }
                                %>
                            </ul>
                        </nav>
                    </div><!-- end col -->
                </div><!-- end row -->
            </div><!-- end col -->





        </div><!-- end col -->

        <!-- end container -->
    </section>


</div><!-- end wrapper -->

<!-- Core JavaScript
================================================== -->
<script src="js/jquery.min.js"></script>
<script src="js/tether.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>
