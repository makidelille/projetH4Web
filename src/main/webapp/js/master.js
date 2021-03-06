String.prototype.contains = function (s) {
  return this.indexOf(s) != -1;
};

var site = "home";

window.onscroll = function(){
      if(document.body.scrollTop > 1 || document.documentElement.scrollTop > 1){
        document.getElementById('mainHeader').className="min";
        document.getElementById("container").className="down";
      }else{
        document.getElementById('mainHeader').className="";
        document.getElementById("container").className="";
      }
  }

window.onload = function(){
    history.state = null;
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


window.onpopstate = function(event){
  console.log(event);
}

function scrollToPos(posY){
  var speed = 50;
  var nextDif = (document.documentElement.scrollTop | document.body.scrollTop) - speed-posY;
  if(nextDif > 0){
      window.scrollTo(0, (document.documentElement.scrollTop | document.body.scrollTop) - nextDif/speed);
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

function loadPage(baseUrl){
  console.log(baseUrl);
  pageUrl = "pages/" + baseUrl;
  var update = new XMLHttpRequest();
   update.open("GET", pageUrl, true);
   update.onreadystatechange = function () {
       if(update.readyState === 4 && (update.status === 200 || update.status == 0)){
            Array.prototype.forEach.call(document.querySelectorAll("link:not([href=\"css/master.css\"]):not([href=\"css/mobile.css\"]):not([href=\"img/icon.ico\""), function(ele){
                    ele.remove();
            });
            var html  = document.createElement('html');
            html.innerHTML = update.responseText;
            document.title = html.querySelector('title') != null ? html.querySelector('title').innerText : "";
            document.getElementById("container").innerHTML = update.responseText;

            var obj = {page:pageUrl};

            history.pushState(obj, pageUrl, baseUrl);
            console.log(history.state);

            Array.prototype.forEach.call(document.querySelectorAll('#container a'),function(a){
              a.onclick = aOnClick;
            });
            setCookie("last",site,7);
        }
   };
  update.send();
}

function aOnClick(event){
  if(this.href.startsWith(window.location.origin)){
    event.preventDefault();
    loadPage(this.href.replace(window.location.origin,""));
    return false;
  }
}

function disconnect(){
    var req = new XMLHttpRequest();
    req.open("POST","post/logout",true);
    req.send();
    clearCookie();
    window.location.reload();

}

function  imgLoad(evt, img) {
      var tgt = evt.target || window.event.srcElement,
          files = tgt.files;

      // FileReader support
      if (FileReader && files && files.length) {

          var fr = new FileReader();
          fr.onload = function () {
              document.getElementById(img).src = fr.result;
          };
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
    setCookie("last","home",7);
}


function editProfil(){
  document.getElementById('infos').className ="editable";
  document.getElementById('medias').className ="editable";
  document.querySelector('button[name="Edit"]').disabled = true;
    document.querySelector('button[name="Save"]').disabled = null;

  textAreaAdjust(document.querySelector("#infos textarea"));

  document.querySelectorAll('#medias a').forEach(function(a){
    a.onclick = function(event){
      if(confirm("do you want to delete ?")){
        var id = this.href.substring(this.href.indexOf("id=")).replace("id=","");
          var req = new XMLHttpRequest();
          req.open("POST", "post/delete?id="  + id,true);
          req.onload= function(){
              if(this.response == "ok") window.location.reload();
          };
          req.send();
      }else{

      }
      return false;
    }
  });

  document.querySelector('#infos figure').onclick = function(){
    document.getElementById('profilPicInput').click()
  };

  textAreaAdjust(document.querySelector('#infos textarea'));

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

function failedConnection(info){
    document.getElementById('msgBox').parentElement.className ="";
    document.getElementById('msgBoxInfo').innerHTML = info;
}


function onSubmit(){
    document.getElementById('msgBox').parentElement.className ="on";
    if(document.getElementById('login').className == "on"){
        var req = new XMLHttpRequest();
        req.open("POST","post/login?name=" + document.getElementById("logname").value + "&mdp=" + document.getElementById("logmdp").value,true);
        req.contentType =  "application/x-www-form-urlencoded";
        req.onload = function () {
            if(this.response == "ok"){
                window.location.reload();
                setCookie('last','myprofil');
            }else{
                failedConnection("impossible de se connecter ! Les indentifiants sont ils corrects ?");
            }
        };
        req.send();


    }else{
        //we register
        var req1 = new XMLHttpRequest();
        req1.open("POST","post/register?name=" + document.getElementById("regname").value + "&mdp=" + document.getElementById("regmdp").value + "&mdp_confirm=" + document.getElementById("regmdp_confirm").value,true);
        req1.contentType =  "application/x-www-form-urlencoded";
        req1.onload = function () {
            if(this.response == "ok"){
                window.location.reload();
                setCookie('last','myprofil');
            }else if(this.response == "taken"){
                failedConnection("Nom d'utilisateur deja pris !");
            }else{
                failedConnection("Une erreur s'est produite !");
            }
        };
        req1.send();

    }
    return false;
}


 function showElement(ele){
   if(ele.className.contains("hidden")){
     ele.className = ele.className.replace("hidden", "");
   }
 }

 function hideElement(ele){
   if(!ele.className.contains("hidden")){
     ele.className = ele.className + " hidden";
   }
 }

function postCommment(form,photoid, response){
    var com = form.querySelector("textarea").value;
    var color = form.querySelector("input[type=\"color\"]").value.replace("#","");
    var req = new XMLHttpRequest();
    req.open("POST","post/comment?" + "photoId=" + photoid + "&comment="  + com +"&color="+ color + (response ? "&response=" + response:""),true);
    req.contentType = "application/x-www-form-urlencoded";
    req.onload = function(){
        if(this.response = "ok")
            window.location.reload();
        else{
            alert("nope");
        }
    };
    req.send();
    return false;
}
