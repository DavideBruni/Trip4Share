<%--
  Created by IntelliJ IDEA.
  User: cater
  Date: 03/01/2023
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Create a review</title>
    <link rel="stylesheet" href="WebContent/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>

<body class="main-layout col-12">
<%@ include file="header.jsp" %>
<div class="container-xl px-4 mt-4 col-12 justify-content-center">
    <div class="titlepage">
    <h2> Leave a review to <%=session.getAttribute(SecurityUtils.REVIEW_TO)%></h2>
    </div>
    <div class="row justify-content-center pdn-top-30">
        <div class="col-xl-8 justify-content-center">
            <!-- Account details card-->
            <div class="card mb-4 ">


                <div class="card-header text-center">Create Edit your review</div>
                <div class="card-body">
                    <form action="leaveReview" method="post">
                        <!-- Form Group (username)-->
                        <div class="mb-3">
                            <label class="small mb-1">Title</label>
                            <input class="form-control" type="text"  name="title" placeholder="Give your review a title">
                        </div>

                        <!-- Form Group (organization name)-->

                        <textarea class="col-12 mt-4 mb-4" name="text">Insert a comment for your review!</textarea>


                        <!-- Form Row        -->
                        <div class="row gx-3 mb-3">
                            <!-- Form Group (organization name)-->
                            <div class="col-md-6">
                                <label class="small mb-1" for="inputOrgName">Rating</label>
                                <input class="form-control" id="inputOrgName" type="number" placeholder="Rate your experience!" name="value" min="0" max="5">
                            </div>

                        </div>
                        <!-- Save changes button-->
                        <button class="btn btn-primary" type="submit">Save changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%@ include file="footer.jsp" %>
</body>
