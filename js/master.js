

var site = "home";

window.onscroll = function(){
      if(document.body.scrollTop > 1){
        document.getElementById('mainHeader').className="min";
        document.getElementById("container").className="down";
      }else{
        document.getElementById('mainHeader').className="";
        document.getElementById("container").className="";
      }
  }

window.onload = function(){
    var lis = document.querySelectorAll('header nav li');
    for(var i=0; i< lis.length; i++){
      lis[i].onclick = function(event){
        site = this.className;
        selectInNav();
        loadPage(site + ".html");
      }
    }
    if(getCookie("last") != ""){
      site = getCookie("last");
    }
    console.log(site);
    document.querySelector('header nav li.' + site).click();
    scrollToPos(1);
  };

function scrollToPos(posY){
  var speed = 50;
  var nextDif = document.body.scrollTop - speed-posY;
  if(nextDif > 0){
      window.scrollTo(0, document.body.scrollTop - nextDif/speed);
      setTimeout(function(){
        scrollToPos(posY);
      }, 10);
  }else{
    window.scrollTo(posY,0);
  }

}


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

function loadPage(pageUrl){
  console.log(pageUrl);
   document.querySelectorAll("link:not([href=\"css/master.css\"])").forEach(function(ele){
              ele.remove();
  });
  var update = new XMLHttpRequest();
   update.open("GET", pageUrl, true);
   update.onreadystatechange = function () {
       if(update.readyState === 4 && (update.status === 200 || update.status == 0)){
            document.querySelectorAll("link:not([href=\"css/master.css\"])").forEach(function(ele){
                    ele.remove();
            });
            var html  = document.createElement('html');
            html.innerHTML = update.responseText;
            document.title = html.querySelector('title') != null ? html.querySelector('title').innerText : "";
            document.getElementById("container").innerHTML = update.responseText;
        }

        document.querySelectorAll('#container a').forEach(function(a){
          a.onclick = aOnClick;
        });
        setCookie("last",site,7);
   }
  update.send();
}

function aOnClick(event){
  if(this.href.startsWith(window.location.origin)){
    event.preventDefault();
    loadPage(this.href);
    return false;
  }
}

function disconnect(){
  clearCookie();
  //TODO window.location.reload();
  window.location.href="/index.html" //TODO for demo
}

function  imgLoad(evt, target) {
      var tgt = evt.target || window.event.srcElement,
          files = tgt.files;

      // FileReader support
      if (FileReader && files && files.length) {

          var fr = new FileReader();
          fr.onload = function () {
              document.getElementById(target).src = fr.result;
          }
          fr.readAsDataURL(files[0]);
      }
}


function textAreaAdjust(o) {
  o.style.height = "1px";
  o.style.height = (o.scrollHeight)+"px";
}

//src : http://www.w3schools.com/js/js_cookies.asp
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function clearCookie(){
  document.cookie = "last=;expires=" + new Date().toUTCString();
}


function editProfil(){
  document.getElementById('infos').className ="editable";
  document.getElementById('medias').className ="editable";
  document.querySelector('button[name="Edit"]').onclick = saveProfil;
  document.querySelector('button[name="Edit"]').innerText = "Save";
  document.querySelector('button[name="Edit"]').name = "Save";

  document.querySelectorAll('#medias a').forEach(function(a){
    a.onclick = function(event){
      if(confirm("do you want to delete ?")){
        //Supprimer
      }else{

      }
      return false;
    }
  });
  document.querySelector('#infos figure').onclick = function(){document.getElementById('profilPicInput').click()};
}

function saveProfil(){
  document.getElementById('infos').className ="";
  document.getElementById('medias').className ="";
  document.querySelector('button[name="Save"]').onclick = editProfil;
  document.querySelector('button[name="Save"]').innerText = "Edit";
  document.querySelector('button[name="Save"]').name = "Edit";

  document.querySelectorAll('#medias a').forEach(function(a){
    a.onclick = aOnClick;
  });

  document.querySelector('#infos figure').onclick = function(){};

  //send update
}

function toggleLogin(){
   document.getElementById('msgBox').parentElement.className = "";
   if(document.getElementById('login').className == "on"){
     document.getElementById('login').className = "";
     document.getElementById('register').className = "on";
     document.getElementById('toggleBtn').innerText = "deja un compte ? (cliquez ici)";
   }else{
     document.getElementById('login').className = "on";
     document.getElementById('register').className = "";
     document.getElementById('toggleBtn').innerText = "pas de compte ? (cliquez ici)";
   }
 }

 function onLoginSubmit(){
   document.getElementById('msgBox').parentElement.className ="on";
   if(document.getElementById('login').className == "on"){
     //ajax to log
     //if answer true
     //we reload and setcookie
     //TODO window.location.reload();
     setCookie('last','profil');
     window.location.href = "/index_alt.html"; //TODO temp for demo
   }


   return false;
 }
