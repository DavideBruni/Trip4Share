
<%@ page import="it.unipi.lsmd.dto.InvolvedPeopleDTO" %>
<%@ page import="it.unipi.lsmd.model.enums.Status" %>
<%@ page import="org.javatuples.Pair" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.OtherUserDTO" %>
<%@include file="header.jsp"%>
<body>
<div class="row mt-5">

  <%
    List<Pair<OtherUserDTO, Status>> joiners = (List<Pair<OtherUserDTO, Status>>) request.getAttribute(SecurityUtils.JOINERS);
    if(joiners!= null){
      int i = 1;
      for(Pair<OtherUserDTO, Status> j : joiners){
        String id = (String) request.getAttribute("id");
  %>
  <!-- Utente -->
  <div class="col-5 justify-content-center">
    <div class="sidebar">
      <div class="widget">
        <div class="blog-list-widget">
          <div class="row">
            <div href="single.html" class=" ml-5 list-group-item list-group-item-action flex-column align-items-center">
              <div class="row justify-content-center">
                <i><img src=<%=j.getValue0().getPic()%> alt="icon" width="40%"/></i>
                <div id=<%=i+"_username"%> class="col-6 mt-3"><%= j.getValue0().getUsername()%></div>
            </div>
          </div>
        </div>
      </div><!-- end blog-list -->
    </div><!-- end widget -->
  </div>




    <div class="col-3  list-group-item flex-column align-items-center">
      <div>
        <div >
          <div class="blog-list-widget justify-content-center pdn-top-30 text-center">
            <div id=<%=i+"_status"%> class="col-6 mt-3"><%=j.getValue1()%></div>
        </div><!-- end blog-list -->
      </div>
    </div>



    <div class="col-3 list-group-item  flex-column ">

      <div class="row justify-content-center">
        <% if(j.getValue1().equals(Status.pending)){ %>
        <button id=<%="ba_"+i+"_"+id%> class="accept" >Accept </button>
        <button id=<%="br_"+i+"_"+id%> class="reject">Reject </button>
        <%} else if(j.getValue1().equals(Status.accepted)) {%>
        <button id=<%="bd_"+i+"_"+id%> class="delete" >Remove </button>
        <% }
        %>
      </div>
    </div><!-- end blog-list -->

</div>

<%
      i++;
    }
  }%>


</body>
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
        $("#"+id).text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with
      }else{
        $("#"+id).text(responseText);
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
      $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
    });
  });

  $(document).on("click", ".delete", function() {
    var button_id = event.target.id
    var last_ = button_id.indexOf("_",3);
    var id = button_id.substring(last_+1);
    var i =button_id.substring(3,last_);
    var username = $("#"+i+"_username").text();
    $.get("joinManager?id="+id+"&username="+username+"&action=delete", function(responseText) {
      $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
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
