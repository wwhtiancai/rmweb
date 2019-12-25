document.onkeydown=function enterToTab(){
  if(event.srcElement.type != 'submit' && event.srcElement.type!="image" 
  	&& event.srcElement.type != 'textarea'&& event.keyCode == 13){
      event.keyCode = 9;
  }
}