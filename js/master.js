

var site = "home";
var href = window.location.href;


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

    document.querySelector('header nav li.home').click();
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
   }
  update.send();
}

function aOnClick(event){
  event.preventDefault();
  loadPage(this.href);
  return false;
}

function  imgLoad(evt) {
      var tgt = evt.target || window.event.srcElement,
          files = tgt.files;

      // FileReader support
      if (FileReader && files && files.length) {

          var fr = new FileReader();
          fr.onload = function () {
              document.getElementById("imgUpload").src = fr.result;
          }
          fr.readAsDataURL(files[0]);
      }
}
