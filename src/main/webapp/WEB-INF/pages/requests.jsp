
<%@ page import="it.unipi.lsmd.model.enums.Status" %>
<%@ page import="org.javatuples.Pair" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %>
<%@include file="header.jsp"%>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="WebContent/css/style.css">
    <link rel="stylesheet" href="WebContent/css/profile.css">
    <title>Join Request</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body class="justify-content-center main-layout">
<div class="titlepage text-center pb-5 mt-5">
    <h2>People who request to join your trip </h2>
</div>
<div class="col-12 mt-5">
    <div class="row text-center">
        <div class="col-5 flex-column text-center">
            <div>
                <h2>User</h2>
            </div>

        </div>
        <div class="col-3 flex-column text-center">
            <div>
                <h2>Status</h2>
            </div>
        </div>
        <div class="col-4 flex-column text-center">
            <div>
                <h2>Action</h2>
            </div>
        </div>
    </div>

    <%
        List<Pair<OtherUserDTO, Status>> joiners = (List<Pair<OtherUserDTO, Status>>) request.getAttribute(SecurityUtils.JOINERS);
        if(joiners!= null){
            int i = 1;
            for(Pair<OtherUserDTO, Status> j : joiners){
                String id = (String) request.getAttribute("id");
    %>
    <!-- Utente -->
    <div class="row">
    <div class="col-5 align-items-center">
        <div class="sidebar">
            <div class="widget">
                <div class="blog-list-widget">
                    <div class="row">
                        <div href="single.html" class=" ml-5 list-group-item list-group-item-action flex-column align-items-center">
                            <div class="row justify-content-center mt-3">
                                <i><img src="WebContent/icon/travel-icon.png" alt="icon" width="40%"/></i>
                                <div id=<%=i+"_username"%>>
                                    <a href=<%="user?username=" + j.getValue0().getUsername()%>>
                                        <%= j.getValue0().getUsername()%>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- end blog-list -->
            </div><!-- end widget -->
        </div>
    </div>




    <div class="col-3  list-group-item flex-column align-items-center">
        <div>
            <div >
                <div class="blog-list-widget justify-content-center pdn-top-30 text-center">
                    <div id=<%=i+"_status"%>><%=j.getValue1()%></div>
                </div><!-- end blog-list -->
            </div>
        </div>
    </div>


    <div class="col-3 list-group-item  flex-column ">

        <div class="row justify-content-center" id="div_<%=i%>">
            <% if(j.getValue1().equals(Status.pending)){ %>
            <button id=<%="ba_"+i+"_"+id%> class="accept btn btn-primary mt-3" >Accept </button>
            <button id=<%="br_"+i+"_"+id%> class="reject btn btn-primary mt-3 ml-4">Reject </button>
            <%} else if(j.getValue1().equals(Status.accepted)) {%>
            <button id=<%="bd_"+i+"_"+id%> class="delete btn btn-primary mt-3" >Remove </button>
            <% }
            %>
        </div>
    </div><!-- end blog-list -->

    </div>

    <%
                i++;
            }
        }%>
</div>

</body>
<%@include file="footer.jsp"%>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    $(document).on("click", ".accept", function() {
        var button_id = event.target.id
        var last_ = button_id.indexOf("_",3);
        var id = button_id.substring(last_+1);
        var i =button_id.substring(3,last_);
        var username = $("#"+i+"_username").text().trim();
        $.get("joinManager?id="+id+"&username="+username+"&action=accept", function(responseText) {
            if(responseText == "OK"){
                $( "#ba_"+i+"_"+id).remove();
                $( "#br_"+i+"_"+id).remove();
                $( "#div_"+i).append( "<button id=<\"bd_"+i+"_"+id+"\" class=\"delete btn btn-primary mt-3\" >Remove </button>" );
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
        var username = $("#"+i+"_username").text().trim();
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
        var last_ = button_id.indexOf("_",3);
        var id = button_id.substring(last_+1);
        var i =button_id.substring(3,last_);
        var username = $("#"+i+"_username").text().trim();
        $.get("joinManager?id="+id+"&username="+username+"&action=delete", function(responseText) {
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
