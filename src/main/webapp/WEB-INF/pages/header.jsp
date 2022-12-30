<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: grill
  Date: 25/12/2022
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //Long userId = null;
    //String fullname = null;
    boolean loggedUser = false;
    RegisteredUserDTO authenticatedUserDTO = null;
    if (session != null && session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY) != null){
        authenticatedUserDTO = (RegisteredUserDTO) session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
        loggedUser = true;
    }
%>

<html>
<header>

    <!-- header inner -->
    <div class="header">
        <div class="header_white_section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="header_information">

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
                            <div class="logo"> <a href=" "><img src="WebContent/images/logo.png" alt="Logo"></a> </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-9 col-lg-9 col-md-9 col-sm-9">
                    <div class="menu-area">
                        <div class="limit-box">
                            <nav class="main-menu">
                                <ul class="menu-area-main">
                                    <li class="active"> <a href="">Home</a> </li>
                                    <li> <a href="search">Search</a> </li>
                                    <li><a href="#">Explore</a></li>
                                    <%
                                        if(authenticatedUserDTO != null){
                                    %>
                                    <li><a href=<%="user?username="+authenticatedUserDTO.getUsername()%>>Profile</a></li>
                                    <li><a href="logout">Logout</a></li>
                                    <%
                                        }else{
                                    %>
                                    <li><a href="#">SignUp</a></li>
                                    <li><a href="login">SignIn</a></li>
                                    <%
                                        }
                                    %>
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
