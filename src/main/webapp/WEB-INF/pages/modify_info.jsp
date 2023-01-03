<%@ page import="it.unipi.lsmd.dto.RegisteredUserDTO" %>
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    
    <link rel="stylesheet" href="WebContent/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </head>

<body class="main-layout col-12">
<%@ include file="header.jsp" %>
<div class="container-xl px-4 mt-4 col-12 justify-content-center">


    <div class="row justify-content-center">

        <div class="col-xl-4 justify-content-center">
            <!-- Profile picture card-->
            <div class="card mb-4 mb-xl-0">

                <div class="card-body text-center">
                    <!-- Profile picture image-->
                    <img class="img-account-profile rounded-circle mb-2" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                </div>
            </div>
        </div>
    </div>
    <%
        RegisteredUserDTO user = (RegisteredUserDTO) session.getAttribute(SecurityUtils.AUTHENTICATED_USER_KEY);
    %>
    <div class="row justify-content-center pdn-top-30">
        <div class="col-xl-8 justify-content-center">
            <!-- Account details card-->
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form method="post" action="updateProfile">
                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputUsername">Username (how your name will appear to other users on the site)</label>
                            <input   class="form-control" id="inputUsername" type="text" readonly value=<%=user.getUsername()%>>
                        </div>
                        <!-- Form Row-->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (first name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputFirstName">First name</label>
                                <input   class="form-control" id="inputFirstName" type="text" placeholder="Mario" name="firstName" value="<%=user.getFirstName()%>">
                            </div>
                            <!-- Form Group (last name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputLastName">Last name</label>
                                <input   class="form-control" id="inputLastName" type="text" placeholder="Rossi" name="lastName" value="<%=user.getLastName()%>">
                            </div>
                        </div>
                        <div class="row gx-3 mb-3">
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputOrgName">Email address</label>
                                <input   class="form-control" id="inputOrgName" type="email" placeholdere="Start Bootstrap" name="email" value="<%=user.getEmail()%>">
                            </div>
                            <!-- Form Group (location)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputLocation">Password</label>
                                <input   class="form-control" id="inputLocation" type="password" name="password" value="password">
                            </div>
                        </div>
                        <!-- Form Group (email address)-->
                        <div class="mb-3">
                            <label class="small mb-1" for="inputEmailAddress">Spoken languages</label>
                            <%StringBuilder langs=new StringBuilder();
                                for(String x : user.getSpokenLanguages()){
                                    langs.append(x+",");
                                }
                                String spokenLanguages = String.valueOf(langs).substring(0, langs.length()-1);
                            %>
                            <input   class="form-control" id="inputEmailAddress" type="text" name="languages" value=<%=spokenLanguages%>>
                        </div>
                        <!-- Form Row-->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (phone number)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputPhone">Nationality</label>
                                <input   class="form-control" id="inputPhone" type="text" name="nationality" value=<%=user.getNationality()%>>
                            </div>
                            <!-- Form Group (birthday)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputBirthday">Birthdate</label>
                                <input   class="form-control" id="inputBirthday" type="date" name="birthDate" value=<%=user.getBirthdate()%>>
                            </div>
                        </div>
                        <!-- Save changes button-->
                        <button type="submit" class="btn btn-primary"> Modify information</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>