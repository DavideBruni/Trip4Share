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

    <link rel="stylesheet" href="WebContent/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>


<body class="main-layout ">
<%@ include file="header.jsp" %>

<div class="main-layout .form-horizontal" >

    <div class="banner-main">
        <img src="WebContent/images/night-skyline-wallpaper-25.jpg" alt="#"/>
        <div class="container">
            <div class="text-bg">
                <h1><small>Find out the </small><br><strong class="white">most popular destinations</strong></h1>
            </div>
        </div>
    </div>
    <div class="center pdn-top-30">
        <ul class="nav justify-content-center nav-pills" id="pills-tab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-1" role="tab" aria-controls="pills-home" aria-selected="true">Overall</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-2" role="tab" aria-controls="pills-profile" aria-selected="false">By tags</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-3" role="tab" aria-controls="pills-contact" aria-selected="false">By price </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-4" role="tab" aria-controls="pills-contact" aria-selected="false">By period </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-5" role="tab" aria-controls="pills-contact" aria-selected="false">Most exclusive </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-6" role="tab" aria-controls="pills-contact" aria-selected="false">Cheaper on average </a>
            </li>
        </ul>


        <div class="tab-content " id="pills-tabContent" margin: 0-autos>

            <!-- Overall-->
            <div class="tab-pane fade show active" id="pills-1" role="tabpanel" aria-labelledby="pills-home-tab">
                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4 justify-content-center">
                            <button id="overall" class="btn btn-primary mr-5 buttonSearch">Find out!</button>
                    </div>
                </div>
            </div>

            <!-- By Tag -->
            <div class="tab-pane fade " id="pills-2" role="tabpanel" aria-labelledby="pills-profile-tab">

                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">

                        <form class="form">
                            <label >Insert tag</label>
                            <input class="form-control" placeholder="" type="text" name="tag" size="50" id="tag_input">
                            <button class="btn btn-primary pull-right mr-5" id="tag" type="submit">Search</button>
                        </form>

                    </div>
                </div>

            </div>

            <!-- Trip By Price -->
            <div class="tab-pane fade" id="pills-3" role="tabpanel" aria-labelledby="pills-contact-tab">

                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">

                        <form class="form">
                            <label >Min Price</label>
                            <input class="form-control" placeholder="00.0" type="number" step="0.01" name="min_price" id="price_from">

                            <label >Max Price</label>
                            <input class="form-control" placeholder="00.0" type="number" step="0.01" name="max_price" id="price_to">
                            <button class="btn btn-primary pull-right mr-5" type="submit" id="price">Search</button>
                        </form>
                    </div>

                </div>
            </div>

            <!-- By period -->
            <div class="tab-pane fade" id="pills-4" role="tabpanel" aria-labelledby="pills-contact-tab">

                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">
                    <form class="form">
                        <label >Departure Date</label>
                        <input class="form-control" placeholder="Any" type="date" name="departure_date" id="departureDate">

                        <label >Return Date</label>
                        <input class="form-control" placeholder="Any" type="date" name="return_date" id="returnDate">

                        <button class="btn btn-primary pull-right mr-5" type="submit" id="period">Search</button>
                    </form>
                    </div>

                </div>
            </div>

            <!-- Exclusive-->
            <div class="tab-pane fade" id="pills-5" role="tabpanel" aria-labelledby="pills-home-tab">
                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">
                        <button id="exclusive" class="btn btn-primary pull-right mr-5 buttonSearch" >Find out!</button>
                    </div>
                </div>
            </div>

            <!-- Overall-->
            <div class="tab-pane fade" id="pills-6" role="tabpanel" aria-labelledby="pills-home-tab">
                <div class=" pdn-top-30 row">
                    <div class=" col-4"></div>
                    <div class="col-4">
                        <button id="cheaper" class="btn btn-primary pull-right mr-5 buttonSearch">Find out!</button>
                    </div>
                </div>
            </div>

        </div>
    </div>
    </div>
    <div id = "result" class="text-center">
    <div> </div>
    </div>

</div>

<%@ include file="footer.jsp" %>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    $('.form').on("submit", function(event) {
        event.preventDefault();
        var button_id = event.target.querySelector("button").id;
        var action_param=button_id;
        if(button_id=="period"){
            var dep = event.target.querySelector('#departureDate').value;
            var ret = event.target.querySelector('#returnDate').value;
            action_param+="&departureDate="+dep+"&returnDate="+ret;
        }else if(button_id=="tag"){
            var tag = event.target.querySelector('#tag_input').value;
            action_param+="&tag="+tag;
        }else if(button_id=="price"){
            var price_from = event.target.querySelector('#price_from').value;
            var price_to = event.target.querySelector('#price_to').value;
            action_param+="&from="+price_from+"&to="+price_to;
        }
        $.get("destinations?action="+action_param, function(responseText) {
            var div = document.getElementById("result");
            while (div.firstChild) {
                div.removeChild(div.lastChild);
            }
            $.each(responseText,function (index, dest){
                $( "#result").append( "<a href=\"explore?searchFor=destination&value="+dest.destination+"&return=&departure=&page=1\">"+dest.destination+"</a>" );
                $( "#result").append( "<br>"+dest.num_like+" likes <br><br>" );

            });

        });
    });

    $(document).on("click", ".buttonSearch", function() {
        var button_id = event.target.id;
        var action_param=button_id;
        $.get("destinations?action="+action_param, function(responseText) {
            var div = document.getElementById("result");
            while (div.firstChild) {
                div.removeChild(div.lastChild);
            }
            $.each(responseText,function (index, dest){
                $("#result").append("<a href=\"explore?searchFor=destination&value=" + dest.destination + "&return=&departure=&page=1\">" + dest.destination + "</a>");
                if(button_id!="cheaper") {
                    $("#result").append("<br>" + dest.num_like + " likes & "+dest.num_trips+" trips<br><br>");
                }else{
                    $("#result").append("<br>" + dest.price+"€<br><br>");
                }
            });

        });
    });
</script>

</body>

</html>