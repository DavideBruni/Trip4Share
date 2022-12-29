
<%@ page import="it.unipi.lsmd.dto.AdminDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.AuthenticatedUserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="stylesheet" href="WebContent/css/style.css">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <%
       AdminDTO admin = (AdminDTO) request.getSession().getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
    %>
    <title><%= admin.getUsername() %>'s Profile</title>
</head>

<body class="main-layout">


<!-- Visualizzazione info profilo e modifica -->
<div class="titlepage">
    <h3 >Your Information</h3>
</div>

<div class="container-xl px-4 mt-4 col-12 justify-content-center">


    <div class="row justify-content-center pdn-top-30">
        <div class="col-xl-8 justify-content-center">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form>
                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1">Username (how your name will appear to other users on the site)</label>
                            <input disabled class="form-control"  type="text" value="<%=admin.getUsername()%>">
                        </div>
                        <!-- Form Row-->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (first name)-->
                            <div class="col-md-6">
                                <label class="small mb-1">First name</label>
                                <input disabled class="form-control" type="text" value="<%=admin.getFirstName()%>">
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="col-md-6">
                                <label class="small mb-1">Last name</label>
                                <input disabled class="form-control"type="text" value="<%=admin.getLastName()%>">
                            </div>
                        </div>
                        <!-- Form Row        -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (organization name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" >Password</label>
                                <input disabled class="form-control" type="password" value="<%=admin.getPassword()%>">
                            </div>
                            <!-- Form Group (location)-->
                            <div class="mb-3">
                                <label class="small mb-1">Email address</label>
                                <input disabled class="form-control" type="email" value="<%=admin.getEmail()%>">
                            </div>
                        </div>
                        <!-- Save changes button-->
                        <a class="btn btn-primary" type="button" href="modify_admin">Modify Information</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="titlepage">
    <h3 > Create a new Admin </h3>
</div>

<!-- Creazione Nuovo Admin -->
<div class="container-xl px-4 mt-4 col-12 justify-content-center">




    <div class="row justify-content-center pdn-top-30">
        <div class="col-xl-8 justify-content-center">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form action="createadmin">
                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1">Username (how your name will appear to other users on the site)</label>
                            <input disabled class="form-control"  type="text" placeholder="Enter your username">
                        </div>
                        <!-- Form Row-->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (first name)-->
                            <div class="col-md-6">
                                <label class="small mb-1">First name</label>
                                <input disabled class="form-control" id="newAdminFirstName" type="text" placeholder="Valerie">
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" >Last name</label>
                                <input disabled class="form-control" id="newAdminLastName" type="text" placeholder="Luna">
                            </div>
                        </div>
                        <!-- Form Row        -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (organization name)-->
                            <div class="col-md-6">
                                <label class="small mb-1">Password</label>
                                <input disabled class="form-control" id="newAdminPassword" type="password" placeholdere="Start Bootstrap">
                            </div>
                            <!-- Form Group (location)-->
                            <div class="mb-3">
                                <label class="small mb-1">Email address</label>
                                <input disabled class="form-control" id="NewAdminEmailAddress" type="email" placeholder="name@example.com">
                            </div>
                        </div>
                        <!-- Save changes button-->
                        <button class="btn btn-primary" type="submit" >Modify Information</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Ricerca Utenti -->

<div class="titlepage">
    <h3 > Ricerca utenti </h3>
</div>

<div class=" pdn-top-30 row">
    <div class=" col-4"></div>
    <div class="col-4 justify-content-center">
<form action="search">
        <label >Insert username</label>
        <input  class="form-control" placeholder="" type="text" name="" size="50">
        <button class="btn btn-primary pull-right mr-5" type="button">Cerca</button>
    </div>
</form>
</div>

</body>