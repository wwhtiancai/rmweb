/*
 * jQuery�����
 * ͼƬԤ����
 * ����ͼƬ��ȣ��߶�
 * ͼƬˮƽ����ֱ����
 * Dev By CssRain.cn
 */
jQuery.fn.loadthumb = function(options) {
	options = $.extend({
		 src : "",
		 imgId : "myImgs",
		 parentId : "CRviewer"
	},options);
	var _self = this;
	_self.hide();
	var img = new Image();
	$(img).load(function(){
		imgDem = {};
		imgDem.w  = img.width;
		imgDem.h  = img.height;
//		imgDem = $.imgResize({"w": $("#"+options.parentId).width() ,"h": $("#"+options.parentId).height()},{"w":imgDem.w,"h":imgDem.h});
//		var imgMargins = $.imgCenter({"w": $("#"+options.parentId).width() ,"h": $("#"+options.parentId).height()},{"w":imgDem.w,"h":imgDem.h});
//		$("#"+options.imgId).css({width:imgDem.w,height:imgDem.h,marginLeft:imgMargins.l,marginTop:imgMargins.t});
		_self.attr("src", options.src);
		_self.fadeIn("slow");
	}).attr("src", options.src);  //.atte("src",options.src)Ҫ����load���棬
	return _self;
}
//����ͼƬ��ȣ��߶Ȳ�� ( parentDem�Ǹ�Ԫ�أ�imgDem��ͼƬ )
jQuery.imgResize = function(parentDem,imgDem){
	if(imgDem.w>0 && imgDem.h>0){
		var rate = (parentDem.w/imgDem.w < parentDem.h/imgDem.h)?parentDem.w/imgDem.w:parentDem.h/imgDem.h;
		//��� ָ���߶�/ͼƬ�߶�  С��  ָ�����/ͼƬ��� ��  ��ô�����ǵı����� ȡ ָ���߶�/ͼƬ�߶ȡ�
		//��� ָ���߶�/ͼƬ�߶�  ����  ָ�����/ͼƬ��� ��  ��ô�����ǵı����� ȡ ָ�����/ͼƬ��ȡ�
		if(rate <= 1){   
			imgDem.w = imgDem.w*rate; //ͼƬ�µĿ�� = ��� * ������
		    imgDem.h = imgDem.h*rate;
		}else{//  �������������1�����µĿ�ȵ�����ǰ�Ŀ�ȡ�
			imgDem.w = imgDem.w;
			imgDem.h = imgDem.h;
		}
    }
	return imgDem;
}
//ʹͼƬ�ڸ�Ԫ����ˮƽ����ֱ���У�( parentDem�Ǹ�Ԫ�أ�imgDem��ͼƬ )
jQuery.imgCenter = function(parentDem,imgDem){
	var left = (parentDem.w - imgDem.w)*0.5;
	var top = (parentDem.h - imgDem.h)*0.5;
	return { "l": left , "t": top};
}