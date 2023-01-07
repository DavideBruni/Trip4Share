<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unipi.lsmd.utils.PagesUtilis" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 29/12/2022
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="WebContent/css/style.css">
    <link rel="stylesheet" href="WebContent/css/profile.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

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


    <title>Title</title>
</head>

<body class="justify-content-center main-layout">

<%@ include file="/WEB-INF/pages/header.jsp" %>


    <%
        String title = (String) request.getAttribute(SecurityUtils.TITLE_PAGE);
    %>

    <div class="titlepage">
        <h2><%=title%></h2>
    </div>

    <%
        ArrayList<OtherUserDTO> users = (ArrayList<OtherUserDTO>) request.getAttribute(SecurityUtils.USER_RESULTS);
        if(users != null && users.size() > 0){
    %>

    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <div class="sidebar">
                <div class="widget">

                    <%
                        for(int i = 0; i < users.size() && i < PagesUtilis.USERS_PER_PAGE; i++){
                    %>
                    <div class="blog-list-widget">
                        <div class=" row">
                            <div class="col-3"></div>
                            <a class="list-group-item list-group-item-action flex-column align-items-center col-6" href=<%="user?username="+users.get(i).getUsername()%>>
                                <div class="row justify-content-center">
                                    <i><img src="WebContent/images/profile2.jpeg" alt="icon" width="40%"/></i>
                                    <h3 class="mt-3"><%=users.get(i).getUsername()%></h3>
                                </div>
                            </a>
                            <div class="col-3"></div>
                        </div>
                    </div><!-- end blog-list -->
                    <%
                            }
                        %>

                    <div class="row">
                        <div class="col-md-12">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-end">
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

                                        if(page_index != 1){
                                    %>
                                    <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index - 1)%>>Previous</a></li>
                                    <%} if(users.size() > PagesUtilis.USERS_PER_PAGE) {
                                    %>
                                    <li class="page-item"><a class="page-link" href=<%=new_url+ (page_index + 1)%>>Next</a></li>
                                    <% }%>
                                </ul>
                            </nav>
                        </div><!-- end col -->
                    </div><!-- end row -->




                    <%    }else{
                    %>
                        No users found!
                    <%
                        }
                    %>
                </div><!-- end widget -->
            </div>
        </div>
        <div class="col-1"></div>
    </div>
    </div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
</body>
</html>
