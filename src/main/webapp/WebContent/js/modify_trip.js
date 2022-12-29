var included = 1;
var not_included = 1;
var day=2;
function add_day() {
    document.getElementById('wrapper').innerHTML += '<div class="single-preparation-step d-flex" > <div class="col"> <h4 class="pdn-top-30">Day '+day+' </h4><label class="small mb-1" >Title</label>' +
        '              <input type="text" name="title'+day+'" required>' +
        '              <label class="small mb-1" >Subtitle</label>' +
        '              <input type="text" name="subtitle'+day+'">  <textarea  class="form-control" name="day'+day+'"> Add description of the day </textarea> </div> </div>';
    day++;
}

function remove_day() {
    var select = document.getElementById('wrapper');
    select.removeChild(select.lastChild);
    day--;
}

function add_included() {
    document.getElementById('included').innerHTML += ' <li><input  class="form-control text-center" name="included'+included+'" type="text" placeholder="4 Tbsp (57 gr) butter"></li>';
    included++;
}

function remove_included() {
    var select = document.getElementById('included');
    select.removeChild(select.lastChild);
    select.removeChild(select.lastChild);
    included--;
}

function add_notincluded() {
    document.getElementById('notincluded').innerHTML += ' <li><input  class="form-control text-center"  name="notIncluded'+not_included+'" type="text" placeholder="4 Tbsp (57 gr) butter"></li>';
    not_included++;
}

function remove_notincluded() {
    var select = document.getElementById('notincluded');
    select.removeChild(select.lastChild);
    select.removeChild(select.lastChild);
}