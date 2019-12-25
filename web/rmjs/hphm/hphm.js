function getHphmDivHtml(){
	var ccookie = document.cookie;
	var ccookies = ccookie.split("; ");
	var sft = '';
	var cysft = '';
	for(i = 0; i < ccookies.length; i++){
		if(ccookies[i].substring(0,4) == 'sft='){
			sft = ccookies[i].substr(4);
		}else if(ccookies[i].substring(0,6) == 'cysft='){
			cysft = ccookies[i].substr(6);
		}
	}
	var cysftAry = new Array('','','','','','','','');
	if(cysft != ''){
		var tmpCysftAry = cysft.split("-");
		var idx = tmpCysftAry.length < 8 ? tmpCysftAry.length : 8;
		for(i = 0; i < idx; i++){
			cysftAry[i] = tmpCysftAry[i];
		}
	}

	var hphm_div = '';
	hphm_div = hphm_div + '<table width="200px" border="0" cellpadding="0" cellspacing="1" class="border">';
	hphm_div = hphm_div + '<tr><td class="key_normal" onclick="toV(\'' + sft + '\',1)">' + sft + '</td>';
	for(i = 0; i < 8; i++){
		if(cysftAry[i] != '' && typeof(cysftAry[i]) != undefined){
			hphm_div = hphm_div + '<td class="key_normal" onclick="toV(\'' + cysftAry[i] + '\',1)">' + cysftAry[i] + '</td>';
		}else{
			hphm_div = hphm_div + '<td class="key_normal"></td>';
		}
	}

	hphm_div = hphm_div + '</tr>';

	hphm_div = hphm_div + '<tr><td class="key_1" onclick="toV(\'¾©\',1)">¾©</td><td class="key_1" onclick="toV(\'½ò\',1)">½ò</td><td class="key_1" onclick="toV(\'¼½\',1)">¼½</td><td class="key_1" onclick="toV(\'½ú\',1)">½ú</td><td class="key_1" onclick="toV(\'ÃÉ\',1)">ÃÉ</td><td class="key_1" onclick="toV(\'ÁÉ\',1)">ÁÉ</td><td class="key_1" onclick="toV(\'¼ª\',1)">¼ª</td><td class="key_1" onclick="toV(\'ºÚ\',1)">ºÚ</td><td class="key_2" onclick="toV(\'¾¯\',2)">¾¯</td></tr>';
	hphm_div = hphm_div + '<tr><td class="key_1" onclick="toV(\'»¦\',1)">»¦</td><td class="key_1" onclick="toV(\'ËÕ\',1)">ËÕ</td><td class="key_1" onclick="toV(\'Õã\',1)">Õã</td><td class="key_1" onclick="toV(\'Íî\',1)">Íî</td><td class="key_1" onclick="toV(\'Ãö\',1)">Ãö</td><td class="key_1" onclick="toV(\'¸Ó\',1)">¸Ó</td><td class="key_1" onclick="toV(\'Â³\',1)">Â³</td><td class="key_3" onclick="toV(\'V\',1)">¾ü</td><td class="key_2" onclick="toV(\'Ñ§\',2)">Ñ§</td></tr>';
	hphm_div = hphm_div + '<tr><td class="key_1" onclick="toV(\'Ô¥\',1)">Ô¥</td><td class="key_1" onclick="toV(\'¶õ\',1)">¶õ</td><td class="key_1" onclick="toV(\'Ïæ\',1)">Ïæ</td><td class="key_1" onclick="toV(\'ÔÁ\',1)">ÔÁ</td><td class="key_1" onclick="toV(\'¹ð\',1)">¹ð</td><td class="key_1" onclick="toV(\'Çí\',1)">Çí</td><td class="key_3" onclick="toV(\'Z\',1)">×Ü</td><td class="key_3" onclick="toV(\'K\',1)">¿Õ</td><td class="key_2" onclick="toV(\'¹Ò\',2)">¹Ò</td></tr>';
	hphm_div = hphm_div + '<tr><td class="key_1" onclick="toV(\'Óå\',1)">Óå</td><td class="key_1" onclick="toV(\'´¨\',1)">´¨</td><td class="key_1" onclick="toV(\'¹ó\',1)">¹ó</td><td class="key_1" onclick="toV(\'ÔÆ\',1)">ÔÆ</td><td class="key_1" onclick="toV(\'²Ø\',1)">²Ø</td><td class="key_3" onclick="toV(\'H\',1)">º£</td><td class="key_3" onclick="toV(\'E\',1)">ÅÚ</td><td class="key_3" onclick="toV(\'WJ\',1)">Îä</td><td class="key_2" onclick="toV(\'Ê¹\',2)">Ê¹</td></tr>';
	hphm_div = hphm_div + '<tr><td class="key_1" onclick="toV(\'ÉÂ\',1)">ÉÂ</td><td class="key_1" onclick="toV(\'¸Ê\',1)">¸Ê</td><td class="key_1" onclick="toV(\'Çà\',1)">Çà</td><td class="key_1" onclick="toV(\'Äþ\',1)">Äþ</td><td class="key_1" onclick="toV(\'ÐÂ\',1)">ÐÂ</td><td class="key_1" onclick="toV(\'¸Û\',2)">¸Û</td><td class="key_1" onclick="toV(\'°Ä\',2)">°Ä</td><td class="key_1" onclick="toV(\'Ì¨\',2)">Ì¨</td><td class="key_2" onclick="toV(\'Áì\',2)">Áì</td></tr>';      
	hphm_div = hphm_div + '</table>';
	return hphm_div;
}

var isHphmDivMouseOn = false;

function showHphmDiv(){
	var isshow = $("#hphm_div").css("display");
	if(isshow == 'none'){
		var offset = $("#hphm").offset();
		var he = $("#hphm").height();
		$("#hphm_div").html(getHphmDivHtml());
		$("#hphm_div").css("top",offset.top+he+4);
		$("#hphm_div").css("left",offset.left);
		$("#hphm_div").css("display","block");
		$("#hphm").focus();
	}else{
		$("#hphm_div").css("display","none");
	}
}
function hiddenHphmDiv(){
	$("#hphm_div").css("display","none");
}
function toV(key1, key2){
	if(key2 == 1){
		tmpVal = $("#hphm").val();
		if(tmpVal.length != ''){
			tmpPreHphm = tmpVal.substring(0,1);
			//alert("ÊÇ·ñÎª×Ö·û£º" + (!(tmpPreHphm >= 'A' && tmpPreHphm <= 'Z') && !(tmpPreHphm >= '0' && tmpPreHphm <= '9')));
			if(!(tmpPreHphm >= 'A' && tmpPreHphm <= 'Z') && !(tmpPreHphm >= '0' && tmpPreHphm <= '9')){
				$("#hphm").val(key1 + tmpVal.substring(1));
			}else{
				$("#hphm").val(key1 + tmpVal);
			}
		}else{
			$("#hphm").val(key1 + tmpVal);
		}
		setCysft(key1);
	}else{
		$("#hphm").val($("#hphm").val() + key1);
	}
	$("#hphm_div").css("display","none");
	$("#hphm").focus();
}

function hphmToUp(){
	var curkey = this.event.keyCode;
	if(curkey >= 65 && curkey <= 99){
		var curpos = $("#hphm").position();
		$("#hphm").val($("#hphm").val().toUpperCase());
		$("#hphm").position(curpos + 1);
	}
}

function hphmToUpAll(){
	$("#hphm").val($("#hphm").val().toUpperCase());
//	alert(isHphmDivMouseOn);
	if(!isHphmDivMouseOn){
		hiddenHphmDiv();
	}
}

function setPos(){
	var c_length = $("#hphm").val().length;
	$("#hphm").position(c_length);
}

function setIsHphmDivMouseOn(isMouseOn){
//	alert(isHphmDivMouseOn);
	isHphmDivMouseOn = isMouseOn;
}

function setCysft(s){
	tcysft_new = '';
	cm = document.cookie;
	cms = cm.split("; ");
	csft = '';
	tcysft = '';
	for(i = 0; i < cms.length; i++){
		if(cms[i].substring(0,6) == 'cysft='){
			tcysft = cms[i].substr(6);
		}else if(cms[i].substring(0,4) == 'sft='){
			csft = cms[i].substr(4);
		}
	}
	//alert("sft:" + csft + ";s:" + s + ";sft==s:" + (s == csft))
	if(s == csft){
		return;
	}
	if(tcysft != '' && typeof(tcysft) != undefined){
		tcysfts = tcysft.split("-");
		if(typeof(tcysfts) != undefined){
			cidx = tcysfts.length < 7 ? tcysfts.length : 7;
			for(i = 0; i < cidx; i++){
				if(tcysfts[i] != s){
					tcysft_new += '-' + tcysfts[i];
				}
			}
		}
	}
	var vdate = new Date();
	vdate.setDate(vdate.getDate() + 30);
	tcysft_new = 'cysft=' + s + tcysft_new + '; expires=' + vdate.toGMTString();
	document.cookie = tcysft_new;
}