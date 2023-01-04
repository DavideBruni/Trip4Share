<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unipi.lsmd.dto.ReviewDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.utils.PagesUtilis" %><%--
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


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <!-- style css -->
    <link rel="stylesheet" href="WebContent/css/profile.css">
    <link rel="stylesheet" href="WebContent/css/style.css">

    <title>Reviews</title>
</head>
<body>

<%@ include file="header.jsp" %>

<div id="wrapper">

    <section class="section wb main-layout">
        <div class="row titlepage justify-content-center pdn-top-30" >
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


                    <hr class="invis3">

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
                                        //for(ReviewDTO review : reviews){
                                        for(int i = 0; i < reviews.size() && i < PagesUtilis.REVIEWS_PER_PAGE; i++){
                                %>

                                <div class="blog-meta big-meta mt-4">
                                    <h4><%=reviews.get(i).getTitle()%></h4>
                                    <h4 class="text-right"><%=reviews.get(i).getRating() + "/5 "%></h4>
                                    <p><%=reviews.get(i).getText()%></p>
                                    <small><%=reviews.get(i).getDate()%></small>
                                    <p class="grey text-right mr-5"><a href=<%="user?username="+reviews.get(i).getAuthor()%>><%=reviews.get(i).getAuthor()%></a></p>
                                    <hr class="invis3">
                                </div><!-- end meta -->

                                <% if(reviews.get(i).getAuthor() == authenticatedUserDTO.getUsername()){ %>

                                <div class="row pull-right justify-content-end">
                                    <a class="text-right btn btn-primary bottone mr-5 mt-5" href="" >Delete review</a>
                                    <a class="text-right btn btn-primary bottone mr-5 mt-5" href="#" >Edit review</a>
                                </div>

                                <%}%>


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

                                %>

                                <%
                                    if(page_index != 1){
                                %>
                                    <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index - 1)%>>Previous</a></li>
                                <%
                                    }

                                    if(reviews.size() > PagesUtilis.TRIPS_PER_PAGE){
                                %>
                                <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index + 1)%>>Next</a></li>
                                <%
                                    }
                                %>

                            </ul>
                        </nav>
                    </div><!-- end col -->
                </div><!-- end row -->
            </div><!-- end col -->
            <%
                }else{
            %>
                No Reviews Found!
            <%
                }
            %>






        </div><!-- end col -->

        <!-- end container -->
    </section>


</div><!-- end wrapper -->

<%@ include file="footer.jsp" %>


<!-- Core JavaScript
================================================== -->
<script src="js/jquery.min.js"></script>
<script src="js/tether.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>
