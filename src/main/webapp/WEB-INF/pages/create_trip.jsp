<html lang="en">
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.TripDetailsDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmd.dto.DailyScheduleDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <link rel="stylesheet" href="WebContent/css/style.css">
  <link rel="stylesheet" href="WebContent/css/profile.css">
  <!-- <link rel="stylesheet" href="css/signup.css"> -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


</head>


<body class="tripbgd main-form tripbgd2">
<%@ include file="header.jsp" %>
<%
  Object flag = request.getAttribute(SecurityUtils.MODIFY_FLAG);
  TripDetailsDTO t = null;
  if(flag != null){
    t = (TripDetailsDTO) session.getAttribute(SecurityUtils.TRIP);
%>
<form method="post" action="updateTrip">
          <%}else{ %>
            <form method="post" action="addTrip">
  <% } %>
<div class="col-sm-9 mx-auto border border-4 mt-4  ">



    <!-- Receipe Content Area -->

    <div class="container justify-content-center text-center my-30 ">




      <div class="row titlepage justify-content-center" >

        <%
          if(flag!=null){ %>
        <h3>Modify Your Trip </h3>
        <% }else{%>
        <h3>Create Your Trip </h3>
      <%}%>
    </div>
<div class="row justify-content-center">
    <div class="col-4"></div>
    <div class="receipe-duration col-4">
      <div class="row">
        <label class="small mb-1" >Destination</label>
        <% if (t!=null && t.getDestination()!=null){%>
        <input  class="form-control text-center" name="destination" type="text" placeholder="Insert a Destination"  value="<%=t.getDestination()%>" required>
        <%}else{%>
          <input  class="form-control text-center" name="destination" type="text" placeholder="Insert a Destination"  required>
       <% } %>
      </div>
      <div class="row">
        <label class="small mb-1" >Title</label>
        <% if (t!=null && t.getTitle()!=null){%>
        <input  class="form-control text-center" name="title" type="text" placeholder="Insert a Title"  value="<%=t.getTitle()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="title" type="text" placeholder="Insert a Title"  required>
        <% } %>
      </div>
      <div class="row">
        <label class="small mb-1" >price</label>
        <% if (t!=null && t.getPrice()!=0){%>
        <input  class="form-control text-center" name="price" type="number" step=0.01 placeholder="Insert the price"  value="<%=t.getPrice()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="price" type="number" step=0.01 placeholder="Insert the price"  required>
        <% } %>
         </div>

      <div class="row">
        <label class="small mb-1" >Departure Date</label>
        <% if (t!=null && t.getDepartureDate()!=null){%>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="DD/MM/AAAA" onclick="(this.type='date')" value="<%=t.getDepartureDate()%>" required>
        <%}else{%>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="DD/MM/AAAA" onclick="(this.type='date')" required>
        <% } %>
      </div>

      <div class="row">
        <label class="small mb-1" >Return date</label>
        <% if (t!=null && t.getReturnDate()!=null){%>
        <input  class="form-control text-center"  name="returnDate" type="text" placeholder="DD/MM/AAAA" onclick="(this.type='date')" value="<%=t.getReturnDate()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="returnDate" type="text" placeholder="DD/MM/AAAA" onclick="(this.type='date')" required>
        <% } %>

      </div>

      <div class="row">
        <label class="small mb-1" >Tags</label>
        <% if (t!=null && t.getTags()!=null){
          List<String> listTags = t.getTags();
          String tags = listTags.get(0);
          listTags.remove(0);
          for(String x : t.getTags()){
            tags+=","+x;
          }
        %>
        <input  class="form-control text-center" name="tags" type="text" placeholder="Insert tags separated by comma" value="<%=tags%>">
        <%}else{%>
        <input  class="form-control text-center" name="tags" type="text" placeholder="Insert tags separated by comma">
        <% } %>
      </div>

    </div>
    <div class="col-4"></div>
</div>

  </div>

  <div class="receipe-ratings my-5 col-12 ">
    <div class="row">
      <label class="small mb-1" >Modify the description</label>
      <% if(t!=null && t.getDescription()!=null){%>
      <textarea  class="form-control" id="description" name="description"> <%=t.getDescription()%> </textarea>
      <%}else{%>
      <textarea  class="form-control" id="description" name="description" > Description </textarea>
       <% } %>
    </div>
  </div>

  <div class="receipe-ratings my-5 col-12 ">
    <div class="row">
      <label class="small mb-1" >Info about the trip</label>
      <% if (t!=null && t.getInfo()!=null){%>
      <textarea  class="form-control" id="info" name="info" > <%=t.getInfo()%>   </textarea>
      <%}else{%>
      <textarea  class="form-control" id="info" name="info" > Trip general information </textarea>
      <% } %>
    </div>
  </div>

    <div class="mb-4">
      <div class="row">
        <!-- FIRST COLUMN -->
        <!-- Itinierary Day BEGIN -->
        <div class="col-8" >
          <h2>ITINERARIO</h2>
          <div id="wrapper">
            <% if(t!=null && t.getItinerary()!=null && !t.getItinerary().isEmpty()){
            List<DailyScheduleDTO> it = t.getItinerary();
            for(DailyScheduleDTO d : it){
              int day = d.getDay();
            %>
              <div class="single-preparation-step d-flex" >
                <div class="col-12">
                  <h4 class="pdn-top-30 mb-2">Day <%=day%></h4>

                    <label class="small mb-1" for="<%="title"+day%>">Title</label>
                    <input type="text" name="<%="title"+day%>" required value="<%=d.getTitle()%>">

                    <label class="small mb-1 ml-4" >Subtitle</label>
                    <input type="text" name="<%="subtitle"+day%>" value="<%=d.getSubtitle()%>">

                  <textarea  class="form-control mt-4" name="<%="day"+day%>"><% if(d.getDescription()==null){ %>Add description of the day<%}else{%><%=d.getDescription()%><%}%>
                  </textarea>
                </div>
              </div>
            <% }
            }else{%>
            <div class="single-preparation-step d-flex" >
              <div class="col-8">
                <h4 class="pdn-top-30 mb-2">Day 1</h4>
                <div class="row justify-content-around">
                  <label class="small mb-1" >Title</label>
                  <input type="text" name="title1" required>
                  <label class="small mb-1 ml-4" >Subtitle</label>
                <input type="text" name="subtitle1" >
                </div>
                <textarea  class="form-control mt-4" name="day1">Add description of the day
                  </textarea>
              </div>
            </div>
            <% } %>
          </div>
        </div>
        <!-- Itinierary Day END -->


        <!-- SECOND COLUMN -->
        <div class="col-4">
          <!-- Included BEGIN -->
          <div  class=" ">
            <h2 class="text-center pdn-top-30">INCLUDED</h2>

            <div class="custom-control" id="included">
              <% if(t!=null && t.getWhatsIncluded()!=null && !t.getWhatsIncluded().isEmpty()){
                List<String> it = t.getWhatsIncluded();
                int i=0;
                for(String d : it){
              %>
              <input  class="form-control text-center" name="<%="included"+i%>" type="text" value="<%=d%>">
              <%
                  i++;}}else{%>
              <input  class="form-control text-center" name="included0" type="text" placeholder="Insert an option">
              <%}%>
            </div>
            <div class="row justify-content-end mr-4">
              <button class="btn btn-primary mt-3" type="button" onclick="add_included();">Add Field</button>
              <button class="btn btn-primary ml-4 mt-3" type="button" onclick="remove_included();">Remove Field</button>
            </div>
          </div>
          <!-- Included END -->

          <!-- not Included BEGIN -->
          <div class="justify-content-center mt-6 pdn-top-30">
            <h2 class="text-center mt-6">NOT INCLUDED</h2>
            <div class="custom-control" id="notincluded">
              <% if(t!=null && t.getWhatsNotIncluded()!=null && !t.getWhatsNotIncluded().isEmpty()){
              List<String> it = t.getWhatsNotIncluded();
              int i=0;
              for(String d : it){
            %>
              <input  class="form-control text-center" name="<%="notIncluded"+i%>" type="text" value="<%=d%>">
              <%
                i++;}}else{%>
              <input  class="form-control text-center" name="notIncluded0" type="text" placeholder="Insert an option">
              <%}%>
            </div>
            <div class="row justify-content-end mr-4">
              <button class="btn btn-primary mt-3" type="button" onclick="add_notincluded();">Add Field</button>
              <button class="btn btn-primary ml-4 mt-3" type="button" onclick="remove_notincluded();">Remove Field</button>
            </div>
          </div>
        </div>
      </div>



    </div>

  <div class="row">
      <div class="col-8 text-center">

        <div class="row pull-right">
          <button class="btn btn-primary ml-4" type="button" onclick="add_day();">Add Day</button>
          <button class="btn btn-primary ml-4" type="button" onclick="remove_day();">Remove Day</button>
        </div>

      </div>
    <div class="col-4"></div>
  </div>

  <div class="row mt-5 mb-5 text-center">
    <button type="submit" class="btn btn-primary btn-info btn-lg ml-4 pull-right">Create!</button>
  </div>

  </div>


</form>

<%@ include file="footer.jsp" %>

</body>
<script>
  var included;
  var not_included;
  var day;
  <% if(t!=null && t.getItinerary()!=null && !t.getItinerary().isEmpty()){%>
  day = <%=t.getItinerary().size()+1%>;
  <%}else{%>
  day=2;
  <%}%>
  <% if(t!=null && t.getWhatsIncluded()!=null && !t.getWhatsIncluded().isEmpty()){%>
  included = <%=t.getWhatsIncluded().size()+1%>;
  <%}else{%>
  included=1;
  <%}%>
  <% if(t!=null && t.getWhatsNotIncluded()!=null && !t.getWhatsNotIncluded().isEmpty()){%>
  not_included = <%=t.getWhatsNotIncluded().size()+1%>;
  <%}else{%>
  not_included=1;
  <%}%>




  function add_day() {
    document.getElementById('wrapper').innerHTML += '<div class="single-preparation-step d-flex" > <div class="col-8"> <h4 class="pdn-top-30">Day '+day+'</h4> <div class="row justify-content-center>"<label class="small mb-1" >Title</label>' +
            '              <input type="text" name="title'+day+'" required>' +
            '              <label class="small mb-1 ml-4" >Subtitle</label>' +
            '              <input type="text" name="subtitle'+day+'"> </div>  <textarea class="form-control mt-4" name="day'+day+'"> Add description of the day </textarea> </div> </div>';
    day++;
  }

  function remove_day() {
    var select = document.getElementById('wrapper');
    select.removeChild(select.lastChild);
    day--;
  }

  function add_included() {
    document.getElementById('included').innerHTML += '<input  class="form-control text-center" name="included'+included+'" type="text" placeholder="Insert an option">';
    included++;
  }

  function remove_included() {
    var select = document.getElementById('included');
    select.removeChild(select.lastChild);
    select.removeChild(select.lastChild);
    included--;
  }

  function add_notincluded() {
    document.getElementById('notincluded').innerHTML += '<input  class="form-control text-center"  name="notIncluded'+not_included+'" type="text" placeholder="Insert an option">';
    not_included++;
  }

  function remove_notincluded() {
    var select = document.getElementById('notincluded');
    select.removeChild(select.lastChild);
    select.removeChild(select.lastChild);
  }
</script>
<!-- ##### All Javascript Files ##### -->
<!-- jQuery-2.2.4 js -->
<script src="WebContent/js/jquery/jquery-2.2.4.min.js"></script>
<!-- Popper js -->
<script src="WebContent/js/bootstrap/popper.min.js"></script>
<!-- Bootstrap js -->
<script src="WebContent/js/bootstrap/bootstrap.min.js"></script>
<!-- All Plugins js -->
<script src="WebContent/js/plugins/plugins.js"></script>
<!-- Active js -->
<script src="WebContent/js/active.js"></script>
</html>