$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	var formDom = $('#info-content'),
	groupName = $('#groupName'),
	submitDom = $('#sub');

	var WidthCheck=function(str){ 
		var w = 0; 
		var tempCount = 0; 
		for (var i=0; i<str.length; i++) { 
		   var c = str.charCodeAt(i); 
		   //单字节加1 
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
			w++; 
		  
		   }else { 
			w+=2;
		   } 
		 }
		return w;
	}
	
	groupName.blur(function(){
		var pass = $(this).val();
		if(pass == '' || WidthCheck(pass) < 2 || WidthCheck(pass) > 20){
			$(this).parent().find(".ui-text-prompt").hide();
			$(this).parent().find(".ui-check").html('<i></i>2-20位字符组成');
			$(this).parent().find(".ui-check").css({display:"inline"});
		}else
		{
			$(this).parent().find(".ui-check").hide();
			$(this).parent().find(".ui-text-prompt").show();
		}
	});
	
	
	groupName.keyup(function(){
		var me=this, v=$.trim(me.value);
		me.value = v = v.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g, '');
		$(this).focus();
	});
	
	groupName.focus(function(){
		$(this).parent().find(".ui-check").hide();
		$(this).parent().find(".ui-text-prompt").show();
	});
	
	
	//修改提交 
	submitDom.click(function(){
		var pass = groupName.val();
		//密码
		if(pass == ''){
			groupName.parent().find(".ui-check").html('<i></i>组名称不能为空');
			return;
		}
		if(WidthCheck(pass) < 2 || WidthCheck(pass) > 20)
		{
			groupName.parent().find(".ui-check").html('<i></i>2-20位字符组成');
			return;
		}
	
		//提交
		formDom.submit();
	})

});