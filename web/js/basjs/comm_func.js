function id15to18(zjhm) {
    var strJiaoYan = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
                               "2");
    var intQuan = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
                            1);
    var ll_sum = 0;
    var i;
    var ls_check;
    zjhm = zjhm.substring(0, 6) + "19" + zjhm.substring(6);
    for (i = 0; i <= 16; i++) {
        ll_sum = ll_sum + (parseFloat(zjhm.substr(i, 1))) * intQuan[i];
    }
    ll_sum = ll_sum % 11;
    zjhm = zjhm + strJiaoYan[ll_sum];
    return zjhm;
}

//zhoujn
function mouse_over(obj){
  obj.style.backgroundColor="#ffff33"
}
//zhoujn
function mouse_out(obj){
  obj.style.backgroundColor=""
}
function cc(){
  var e = event.srcElement;
  var r =e.createTextRange();
  r.moveStart("character",e.value.length);
  r.collapse(true);
  r.select();
}
function riqi(rq){
  gfPop.fPopCalendar(document.all[rq]);
  return false;
}

function riqitime(rq){
  gfPoptime.fPopCalendar(document.all[rq]);
  return false;
}

function getzjhmXb(zjhm) {
    // 取性别
    if (zjhm.length == 15) {
            xb = zjhm.substr(14, 1);
    } else {
         xb = zjhm.substr(16, 1);
    }
    if (xb % 2==1){
        return "1";
    } else {
        return "2";
    }
}

function getzjhmCsrq(zjhm) {
    // 取出生日期
    if (zjhm.length == 15) {
        birthday = "19" + zjhm.substr(6, 6);
    } else {
        re = new RegExp("[^0-9X]");
        birthday = zjhm.substr(6, 8);
    }
    birthday = birthday.substr(0, 4) + "-" + birthday.substr(4, 2) + "-" +
               birthday.substr(6, 2)
           return birthday;
} 


