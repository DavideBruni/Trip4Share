<%@ page import="it.unipi.lsmd.dto.InvolvedPeopleDTO" %>
<%@ page import="it.unipi.lsmd.model.enums.Status" %>
<%@ page import="org.javatuples.Pair" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %><%--
  Created by IntelliJ IDEA.
  User: david
  Date: 28/12/2022
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
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
</head>

<%@include file="header.jsp"%>

<body class="justify-content-center main-layout">
<div class="row">
        <div class="titlepage">
          <h3>People who joined your trip </h3>
        </div>
</div>
  <div class="row">
        <div class="col-6 text-center"> People </div>
        <div class="col-6 text-center"> Status </div>
  </div>
        <%
          List<Pair<OtherUserDTO, Status>> joiners = (List<Pair<OtherUserDTO, Status>>) request.getAttribute(SecurityUtils.JOINERS);
          if(joiners!= null){
            int i = 1;
            for(Pair<OtherUserDTO, Status> j : joiners){
              String id = (String) request.getAttribute("id");
        %>
        <div class="blog-list-widget">
          <div class="row">
              <div id=<%="div_"+i%> class="row justify-content-center">
                <i><img src=<%=j.getValue0().getPic()%> alt="icon" width="40%"/></i>
                <div id=<%=i+"_username"%> class="col-6 mt-3"><%= j.getValue0().getUsername()%></div>
                <div id=<%=i+"_status"%> class="col-6 mt-3"><%=j.getValue1()%></div>
            <% if(j.getValue1().equals(Status.pending)){ %>
                <button id=<%="ba_"+i+"_"+id%> class="accept" >Accept </button>
                <button id=<%="br_"+i+"_"+id%> class="reject">Reject </button>
            <%} else if(j.getValue1().equals(Status.accepted)) {%>
                <button id=<%="bd_"+i+"_"+id%> class="delete" >Remove </button>
            <% }
            %>
              </div>
          </div>
        </div>


      <%
            i++;
            }
            }%>



<%@include file="footer.jsp"%>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
  $(document).on("click", ".accept", function() {
    var button_id = event.target.id
    var last_ = button_id.indexOf("_",3);
    var id = button_id.substring(last_+1);
    var i =button_id.substring(3,last_);
    var username = $("#"+i+"_username").text();
    $.get("joinManager?id="+id+"&username="+username+"&action=accept", function(responseText) {
      if(responseText == "OK"){
          $( "#ba_"+i+"_"+id).remove();
          $( "#br_"+i+"_"+id).remove();
          $( "#div_"+i).append( "<button id=<\"bd_"+i+"_"+id+"\" class=\"delete\" >Remove </button>" );
          $("#"+i+"_status").text("accepted");
      }else{
        alert("Errore durante l'operazione");
      }

    });
  });

  $(document).on("click", ".reject", function() {
    var button_id = event.target.id
    var last_ = button_id.indexOf("_",3);
    var id = button_id.substring(last_+1);
    var i =button_id.substring(3,last_);
    var username = $("#"+i+"_username").text();
    $.get("joinManager?id="+id+"&username="+username+"&action=reject", function(responseText) {
        if(responseText == "OK"){
            $( "#ba_"+i+"_"+id).remove();
            $( "#br_"+i+"_"+id).remove();
            $("#"+i+"_status").text("rejected");
        }else{
            alert("Errore durante l'operazione");
        }
        });
  });

  $(document).on("click", ".delete", function() {
      var button_id = event.target.id
      var last_ = button_id.indexOf("_", 3);
      var id = button_id.substring(last_ + 1);
      var i = button_id.substring(3, last_);
      var username = $("#" + i + "_username").text();
      $.get("joinManager?id=" + id + "&username=" + username + "&action=delete", function (responseText) {
          if (responseText == "OK") {
              $("#div_" + i).remove();
          } else {
              alert("Errore durante l'operazione");
          }
      });
  });
</script>

<!-- Javascript files-->
<script src="WebContent/js/jquery.min.js"></script>
<script src="WebContent/js/popper.min.js"></script>
<script src="WebContent/js/bootstrap.bundle.min.js"></script>
<script src="WebContent/js/jquery-3.0.0.min.js"></script>
<script src="WebContent/js/plugin.js"></script>
<!-- sidebar -->
<script src="WebContent/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="WebContent/js/custom.js"></script>
<!-- javascript -->
<script src="WebContent/js/owl.carousel.js"></script>
<script>
  $(document).ready(function() {
    var owl = $('.owl-carousel');
    owl.owlCarousel({
      margin: 10,
      nav: true,
      loop: true,
      responsive: {
        0: {
          items: 1
        },
        600: {
          items: 2
        },
        1000: {
          items: 3
        }
      }
    })
  })
</script>
</body>
</html>
