
function add_day() {

    document.getElementById('wrapper').innerHTML += '<div class="single-preparation-step d-flex" > <div class="col"> <h4 class="pdn-top-30">DIO</h4><br>  <textarea  class="form-control" name="description"> Prova </textarea> </div> </div>';
}

function remove_day() {
    var select = document.getElementById('wrapper');
    select.removeChild(select.lastChild);
}

function add_included() {

    document.getElementById('included').innerHTML += ' <li><input  class="form-control text-center"  type="text" placeholder="4 Tbsp (57 gr) butter"></li>';
}

function remove_included() {
    var select = document.getElementById('included');
    select.removeChild(select.lastChild);
}

function add_notincluded() {

    document.getElementById('notincluded').innerHTML += ' <li><input  class="form-control text-center"  type="text" placeholder="4 Tbsp (57 gr) butter"></li>';
}

function remove_notincluded() {
    var select = document.getElementById('notincluded');
    select.removeChild(select.lastChild);
}