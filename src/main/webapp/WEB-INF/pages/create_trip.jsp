<%@ page import="it.unipi.lsmd.utils.SecurityUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <link rel="stylesheet" href="WebContent/css/style.css">
  <!-- <link rel="stylesheet" href="css/signup.css"> -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


</head>


<body class="tripbgd main-form">
<% Object flag = request.getAttribute(SecurityUtils.MODIFY_FLAG);
if(flag!=null){%>
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
        <input  class="form-control text-center" name="destination" type="text" placeholder="Insert a Destination" required>
      </div>
      <div class="row">
        <label class="small mb-1" >Title</label>
        <input  class="form-control text-center" name="title" type="text" placeholder="Insert a Title" required>
      </div>
      <div class="row">
        <label class="small mb-1" >price</label>
        <input  class="form-control text-center" name="price" type="number" step="0.01" placeholder="Insert the price" required>
      </div>

      <div class="row">
        <label class="small mb-1" >Departure Date</label>
        <input  class="form-control text-center"  name="departureDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" required>
      </div>

      <div class="row">
        <label class="small mb-1" >Return date</label>
        <input  class="form-control text-center" name="returnDate" type="text" placeholder="12/02/1022" onclick="(this.type='date')" required>
      </div>

      <div class="row">
        <label class="small mb-1" >Tags</label>
        <input  class="form-control text-center" name="tags" type="text" placeholder="Insert tags separated by comma">
      </div>

    </div>
    <div class="col-4"></div>
</div>

    <div class="col-9">
      <div class="row">

        <label class="small mb-1" >Insert new photo Url</label>
        <input type="file" id="img" name="pic" accept="image/*">
       </div>
    </div>

  </div>

  <div class="receipe-ratings my-5 col-12 ">
    <div class="row">
      <label class="small mb-1" >Modify the description</label>
      <textarea  class="form-control" id="description" name="description" > Description
          </textarea>
    </div>
  </div>

  <div class="receipe-ratings my-5 col-12 ">
    <div class="row">
      <label class="small mb-1" >Info about the trip</label>
      <textarea  class="form-control" id="info" name="description" > Informations
          </textarea>
    </div>
  </div>

  <div class="mb-4">
    <div class="row">
      <div class="col-12 col-lg-8 " >
        <!-- Single Preparation Step -->
        <h2>ITINERARIO</h2>
        <div id="wrapper">
          <div class="single-preparation-step d-flex" >
            <div class="col"> <h4 class="pdn-top-30">Day 1</h4>
              <label class="small mb-1" >Title</label>
              <input type="text" name="title1" required>
              <label class="small mb-1" >Subtitle</label>
              <input type="text" name="subtitle1">
              <textarea  class="form-control" name="day1"> Add description of the day </textarea>
            </div>
          </div>
        </div>
      </div>


      <div class="col-12 col-lg-4">
        <div  class="mr-5 ">


          <h2 class="justify-content-start pdn-top-30"> INCLUDED</h2>

          <!-- Custom Checkbox -->
          <ul class="custom-control" id="included">
            <li><input  class="form-control text-center"  name="included0" type="text" placeholder="4 Tbsp (57 gr) butter">
            </li>
          </ul>

          <div class="row pull-right">
            <button class="btn btn-primary padding" type="button" onclick="add_included();">Add Field</button>
            <button class="btn btn-primary ml-4" type="button" onclick="remove_included();">Remove Field</button>
          </div>

        </div>
        <div class="mr-5 pdn-top-30">

          <h2 class="justify-content-start pdn-top-30">NOT INCLUDED</h2>

          <!-- Custom Checkbox -->
          <ul class="custom-control" id="notincluded">
            <li><input  class="form-control text-center" name="notIncluded0" type="text" placeholder="4 Tbsp (57 gr) butter">
            </li>
          </ul>
          <div class="row pull-right">
            <button class="btn btn-primary padding" type="button" onclick="add_notincluded();">Add Field</button>
            <button class="btn btn-primary ml-4" type="button" onclick="remove_notincluded();">Remove Field</button>
          </div>
        </div>
      </div>

    </div>
    <div class="row">
      <!--
      <div class="col-12 col-lg-8 " id="wrapper">

      </div>
        -->
    </div>

    <div class="row">
      <div class="col-12 col-lg-8 " >
        <div class="row pull-right">

          <button class="btn btn-primary padding" type="button" onclick="add_day();">Add Day</button>
          <button class="btn btn-primary ml-4" type="button" onclick="remove_day();">Remove Day</button>
        </div>


      </div>

    </div>


    <input type="submit" class="btn btn-primary" type="button">Create!</input>

  </div>

</div>
</form>
</div>


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