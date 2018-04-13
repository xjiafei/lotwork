//全局未读信件读取(三个模块取)
(function($){
	//读取，修改余额可见状态值
	var noreadmsg2="0",
		cookieAllball = $.cookie("showAllBall"),
		frcid = $.cookie("_frcid") == null ? 0 : $.cookie("_frcid"),
		arrys;	
		
	if(cookieAllball=="1"){	
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	}
	else{	
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	}
	//显示余额
	$('#showAllBall').click(function(){
		$.cookie("showAllBall", "1", { expires: 7,path: '/'}); 
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	});
	//隐藏余额
	$('#hiddBall').click(function(){
		$.cookie("showAllBall", "0", { expires: 7,path: '/'}); 
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	});	
	
	//用户名显示	
	if (frcid != 0 && typeof(frcid)!="undefined"){
		frcid = phoenix.util.decodeBase64(frcid);
		arrys=frcid.split('|');
		$('#userName').html(arrys[1]);
	}else{
		//alert('请登录');	 	
		location.href = 'http://www.firefrog.com/login/index';
		
	}
	
	new phoenix.Hover({triggers:'#J-msg-panel',panels:'.msg-box',currClass:'msg-trigger',hoverDelayOut:300});
	new phoenix.Hover({triggers:'#J-user-panel',panels:'.menu-nav',currClass:'menu-trigger',hoverDelayOut:300});
	//顶部彩种菜单
new phoenix.Hover({triggers:'#J-top-game-menu',panels:'#J-top-game-menu .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'}); 	
	try {
		//自动查询此用户未读信件(四处)
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'',				
			data:'',				
			success:function(json){				
				if(json.unreadCnt !=0){																
					var html = "";
					$.each(json.receives, function (i){
                                            html += "<a href=\"/Service2/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+"</a>";
					});
					$("#readmsg").html(html);	
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));												
					$('#radiuscount').show();
				}
				else {					
					$("#readmsg").html("暂未收到新消息");
					$('#radiuscount').hide();//没有信件事，左菜单小图标隐藏
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
					$('#readmsg').attr("style","text-align:center; color:black;");			
				}
			},
			error:function(xhr, type){
				
			},
			complete:function(){   }
	   });
	   } catch (err) {		
				alert("网络异常，读取信件信息失败");
	   }
	
	
	
})(jQuery);