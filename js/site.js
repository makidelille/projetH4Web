var site = "home";
var href = window.location.href;

window.onload = function(){
  var lis = document.querySelectorAll('header nav li');
  for(var i=0; i< lis.length; i++){
    lis[i].onclick = function(event){
      console.log("click");
      site = this.className;
      selectInNav();

      var update = new XMLHttpRequest();
       update.open("GET", "connection.html", true);
       update.onreadystatechange = function () {
           if(update.readyState === 4) && (update.status === 200 || update.status == 0)){
                document.getElementById("container").innerHTML = update.responseText;
            }
       }
      update.send();
    }
  }

  document.querySelector('header nav li.home').click();
};


function selectInNav(){
  var lis = document.querySelectorAll('header nav li');
  for(var i=0; i < lis.length; i++){
    lis[i].removeAttribute("selected");
  }
  var li = document.querySelector('header nav li.' + site) || null;
  if(li != null){
    li.setAttribute("selected",1);
  }
}
