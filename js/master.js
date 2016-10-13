
  window.onscroll = function(){
      if(document.body.scrollTop > 1){
        document.getElementById('mainHeader').className="min";
        document.getElementById("container").className="down";
      }else{
        document.getElementById('mainHeader').className="";
        document.getElementById("container").className="";
      }
  }

function scrollToTop(){
  var speed = 50;
  var nextDif = document.body.scrollTop - speed;
  if(nextDif > 0){
      window.scrollTo(0, document.body.scrollTop - nextDif/speed);
      setTimeout(scrollToTop, 10);
  }else{
    window.scrollTo(0,0);
  }

}
