
function loadimgdetail(tmpgcxh,tmpTplj,tmpTp1,tmpTp2,tmpTp3){
	var curUrl2 = "pic.tfc?method=getTplj&gcxh=" + tmpgcxh + "&tplj=" + tmpTplj + "&tp1=" + tmpTp1 + "&tp2=" + tmpTp2 + "&tp3=" + tmpTp3;
	$.ajaxSettings.async=true;
	$.getJSON(curUrl2, 
		function(data){
			opens();
			code = data['code'];
			if(code == 1){
				tmpFwdz = decodeURIComponent(data['fwdz']);
				tmpTp1 =  encodeURI(data['tp1']);
				tmpTp2 = encodeURI(data['tp2']);
				tmpTp3 = encodeURI(data['tp3']);
				
				srcTztp = '&gcxh=' + data['gcxh'];
				srcTp1 = '';
				srcTp2 = '';
				srcTp3 = '';
				
				tmpTpUrl = '&gcxh=' + data['gcxh'];
				if(tmpFwdz != null && tmpFwdz != ''){
					tmpTpUrl = tmpFwdz + '/readPassPic.tfc?method=getPassPic&fhk=1' + tmpTpUrl;
					srcTztp = tmpFwdz + '/readTzPic.tfc?method=getTzPicture&fhk=1' + srcTztp+'&rand='+Math.random();
				}else{
					tmpTpUrl = 'readPassPic.tfc?method=getPassPic&fhk=1' + tmpTpUrl+'&rand='+Math.random();
					srcTztp = 'readTzPic.tfc?method=getTzPicture&fhk=1' + srcTztp+'&rand='+Math.random();
				}
				
				srcTp1 = tmpTpUrl + '&tpxh=1';
				
				if(tmpTp2 != null && tmpTp2 != ''){
					srcTp2 = tmpTpUrl + '&tpxh=2';
				}
				if(tmpTp3 != null && tmpTp3 != ''){
					srcTp3 = tmpTpUrl + '&tpxh=3';
				}
				
				bodyWidth = $(document.body).width();
				picWidth = parseInt(bodyWidth * 0.96 / 3 - 20);
				picHeight = parseInt(picWidth * 0.8);
				
				
				imgHtm = '<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp1\')"><img id="tp1" src="' + srcTp1 + '" jqimg="' + srcTp1 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				if(srcTp2 != ''){
					imgHtm += '<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp2\')"><img id="tp2" src="' + srcTp2 + '" jqimg="' + srcTp2 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				}
				if(srcTp3 != ''){
					imgHtm += '<div class="jqzoomPassQuery" onclick="passPicWid=openImage(\'tp3\')"><img id="tp3" src="' + srcTp3 + '" jqimg="' + srcTp3 + '" width="' + picWidth + 'px" height="' + picHeight + 'px"></div>';
				}
				$("#passPics").html(imgHtm);
				
				$("#tztpSrc").attr("src", srcTztp);
				
				$(".jqzoomPassQuery").jqueryzoom({
			        xzoom: 300, //设置放大 DIV 长度（默认为 200）
			        yzoom: 300, //设置放大 DIV 高度（默认为 200）
			        offset: 10, //设置放大 DIV 偏移（默认为 10）
			        position: "buttom", //设置放大 DIV 的位置（默认为右边）
			        preload:1,
			        lens:1
			    });
			}
		}		
	);
}