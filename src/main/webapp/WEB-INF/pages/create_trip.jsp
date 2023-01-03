<html lang="en">
<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page import="it.unipi.lsmd.dto.TripDetailsDTO" %>
<%@ page import="java.util.List" %>
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


<body class="tripbgd main-form">
<%@ include file="header.jsp" %>
<% Object flag = request.getAttribute(SecurityUtils.MODIFY_FLAG);
  TripDetailsDTO t = null;
if(flag!=null){
t = (TripDetailsDTO) session.getAttribute(SecurityUtils.TRIP);
%>
<form method="post" action="updateTrip">
          <%}else{ %>
            <form method="post" action="addTrip">
  <% } %>
<div class="col-sm-9 mx-auto border border-4 mt-4 tripbgd2 ">



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
        <input  class="form-control text-center" name="destination" type="text" placeholder="Insert a Title"  value="<%=t.getTitle()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="destination" type="text" placeholder="Insert a Title"  required>
        <% } %>
      </div>
      <div class="row">
        <label class="small mb-1" >price</label>
        <% if (t!=null && t.getPrice()!=0){%>
        <input  class="form-control text-center" name="destination" type="number" step=0.01 placeholder="Insert the price"  value="<%=t.getPrice()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="destination" type="number" step=0.01 placeholder="Insert the price"  required>
        <% } %>
         </div>

      <div class="row">
        <label class="small mb-1" >Departure Date</label>
        <% if (t!=null && t.getDepartureDate()!=null){%>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" value="<%=t.getDepartureDate()%>" required>
        <%}else{%>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" required>
        <% } %>
      </div>

      <div class="row">
        <label class="small mb-1" >Return date</label>
        <% if (t!=null && t.getReturnDate()!=null){%>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" value="<%=t.getReturnDate()%>" required>
        <%}else{%>
        <input  class="form-control text-center" name="returnDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" required>
        <% } %>

      </div>

      <div class="row">
        <label class="small mb-1" >Tags</label>
        <% if (t!=null && t.getTags()!=null){
          List<String> listTags = t.getTags();
          String tags = listTags.get(0);
          listTags.remove(0);
          for(String x : t.getTags()){
            tags=","+x;
          }
        %>
        <input  class="form-control text-center" name="tags" type="text" placeholder="Insert tags separated by comma" value="<%=tags%>">
        <%}else{%>this.type='date')"
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
      <% if (t!=null && t.getDescription()!=null){%>
      <textarea  class="form-control" id="description" name="description" value="<%=t.getDescription()%>"> Description </textarea>
      <%}else{%>
      <textarea  class="form-control" id="description" name="description" > Description </textarea>
       <% } %>
    </div>
  </div>

  <div class="receipe-ratings my-5 col-12 ">
    <div class="row">
      <label class="small mb-1" >Info about the trip</label>
      <% if (t!=null && t.getInfo()!=null){%>
      <textarea  class="form-control" id="description" name="description" value="<%=t.getInfo()%>"> Description </textarea>
      <%}else{%>
      <textarea  class="form-control" id="description" name="description" > Description </textarea>
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
            <div class="single-preparation-step d-flex" >
              <div class="col">
                <h4 class="pdn-top-30 mb-2">Day 1</h4>
                <label class="small mb-1" >Title</label>
                <input type="text" name="title1" required>
                <label class="small mb-1 ml-4" >Subtitle</label>
                <input type="text" name="subtitle1">
                <textarea  class="form-control mt-4" name="day1"> Add description of the day </textarea>
              </div>
            </div>
          </div>
        </div>
        <!-- Itinierary Day END -->


        <!-- SECOND COLUMN -->
        <div class="col-4">
          <!-- Included BEGIN -->
          <div  class=" ">
            <h2 class="text-center pdn-top-30">INCLUDED</h2>

            <div class="custom-control" id="included">
              <input  class="form-control text-center"  name="included0" type="text" placeholder="Insert an option">


              <div class="row justify-content-end mr-4">
                <button class="btn btn-primary mt-3" type="button" onclick="add_included();">Add Field</button>
                <button class="btn btn-primary ml-4 mt-3" type="button" onclick="remove_included();">Remove Field</button>
              </div>
            </div>
          </div>
          <!-- Included END -->

          <!-- not Included BEGIN -->
          <div class="justify-content-center mt-6 pdn-top-30">
            <h2 class="text-center mt-6">NOT INCLUDED</h2>
            <div class="custom-control" id="notincluded">
              <input  class="form-control text-center" name="notIncluded0" type="text" placeholder="Insert an option">
            </div>
            <div class="row justify-content-end mr-4">
              <button class="btn btn-primary mt-3" type="button" onclick="add_notincluded();">Add Field</button>
              <button class="btn btn-primary ml-4 mt-3" type="button" onclick="remove_notincluded();">Remove Field</button>
            </div>
          </div>
        </div>
      </div>



    </div>



    <div class="col-8 text-center" >
      <div class="row pull-right">

        <button class="btn btn-primary ml-4" type="button" onclick="add_day();">Add Day</button>
        <button class="btn btn-primary ml-4" type="button" onclick="remove_day();">Remove Day</button>
      </div>


    </div>



    <div class="col-8 main-form mt-5 mb-5 text-center">
      <button type="submit" class="btn btn-primary btn-info btn-lg ml-4 pull-right">Create!</button>
    </div>

  </div>

  </div>
  </div>
</form>

            <%@ include file="footer.jsp" %>

</body>

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
<script  src="WebContent/js/modify_trip.js">  </script>
</html>