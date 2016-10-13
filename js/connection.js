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

function onSubmit(){
  document.getElementById('msgBox').parentElement.className ="on"

  //TODO ajax here

  return false;
}
