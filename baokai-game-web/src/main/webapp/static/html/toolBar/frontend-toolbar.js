jQuery(document).ready(function( $ ) {

var registerurl = '/register/?id=14402640&exp=1791343064194&pid=13714760&token=dd82';
var toolbar =
'	<div class="topbar">                                                                                                                                                       '+
'		<div class="topbar-bg"></div>                                                                                                                                          '+
'		<div class="g_34">                                                                                                                                                     '+
					' <span style="display: none;">FF4</span>  ' +
'                    <a href="'+indexServer+'/index" class="back-top-home" target="'+frontpageLink+'" >首页</a>                                                         '+
'                    <div id="J-top-game-menu" class="game-menu" id="ffrog">                                                                                                    	       '+                                                                                                                                  
'                        <span class="game-menu-text dropdown-menu-btn">'+projectName+'</span>                                                                                 '+
'                        <div class="game-menu-panel">                                                                                                                         '+
'                                <span class="triangle game-menu-triangle"><span></span></span>                                                                                '+
'                                <div class="game-menu-inner">                                                                                                                 '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">时时彩</div>                                                                                     '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                               	 <a href="'+gameServer+'/gameBet/cqssc" >重庆时时彩<i></i></a>                                           '+        
'                                                    <a href="'+gameServer+'/gameBet/hljssc" >黑龙江时时彩</a>                                                                '+         
'                                                    <a href="'+gameServer+'/gameBet/shssl" >上海时时乐</a>                                                                   '+        
'                                                    <a href="'+gameServer+'/gameBet/tjssc" >天津时时彩</a>                                                                   '+        
'                                                    <a href="'+gameServer+'/gameBet/xjssc" >新疆时时彩</a>                                                                   '+   
'                                                    <a href="'+gameServer+'/gameBet/txffc" >腾讯分分彩</a>                                                                   '+       
'                                                </div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">11选5</div>                                                                                      '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                                        <a href="'+gameServer+'/gameBet/sd115" >山东11选5</a>                                                                 '+             
'                                                        <a href="'+gameServer+'/gameBet/jx115" >江西11选5</a>                                                                 '+             
'                                                        <a href="'+gameServer+'/gameBet/gd115" >广东11选5</a>                                                                 '+             
'                                                        <a href="'+gameServer+'/gameBet/js115" >江苏11选5</a>                                                                 '+             
'                                                </div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">快乐彩</div>                                                                                     '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                                        <a href="'+gameServer+'/gameBet/bjkl8" >北京快乐8</a>                                                                '+
'                                                </div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">快三</div>                                                                                       '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                                        <a href="'+gameServer+'/gameBet/jsk3" >江苏快三</a>                                                                  '+            
'                                                        <a href="'+gameServer+'/gameBet/jsdice" >江苏骰宝</a>                                                                '+            
'                                                        <a href="'+gameServer+'/gameBet/ahk3" >安徽快三</a>                                                                  '+            
'                                                </div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">低频</div>                                                                                       '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                                       <a href="'+gameServer+'/gameBet/ssq" >双色球</a>                                                                   '+             
'                                                       <a href="'+gameServer+'/gameBet/fc3d" >福彩3D</a>                                                                         '+             
'                                                       <a href="'+gameServer+'/gameBet/p5" >排列3/5</a>                                                                       '+
'                                                       <a href="'+gameServer+'/gameBet/lhc" >六合彩</a>                                                                       ';
toolbar+='</div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                        <div class="game-menu-box">                                                                                                           '+
'                                                <div class="game-menu-title">即开型彩票</div>                                                                                       '+
'                                                <div class="game-menu-list">                                                                                                  '+
'                                                       <a href="'+gameServer+'/gameBet/llssc" >宝开时时彩</a>                                                                   '+
'                                                       <a href="'+gameServer+'/gameBet/jlffc" >宝开1分彩</a>                                                                   '+
'                                                       <a href="'+gameServer+'/gameBet/slmmc" >秒开时时彩</a>                                                                   '+
'                                                       <a href="'+gameServer+'/gameBet/ll115" >宝开11选5</a>                                                                '+
'                                                       <a href="'+gameServer+'/gameBet/sl115" >秒开11选5</a>                                                                '+
'                                                       <a href="'+gameServer+'/gameBet/jldice1" >高频骰宝</a>                                                                '+
'                                                </div>                                                                                                                        '+
'                                        </div>                                                                                                                                '+
'                                </div>                                                                                                                                        '+
'                        </div>                                                                                                                                                '+
'                    </div>                                                                                                                                                    ';

if(global_userID>0){
    toolbar+='                                                                                                                        '+
    '<ul class="bar-menu" id="#hiddBall">     '+
    '                        <li class="user" id="'+userClassId+'">                                                                                                                '+
    '                            <a class="icon-user" href="javascript:;" >您好，'+userName+'</a>                                                                                  '+
    '                            <div class="menu-nav">                                                                                                                            '+
    '                                <i class="tri"></i>                                                                                                                           '+
    '                                <p>                                                                                                                                           '+
    '                                    <a href="'+betHistory+'" >投注记录</a>                                                                                                   '+
    '                                    <a href="'+indexServer+'/bet/fuddetail" >账户明细</a>                                                                                    '+
    '                                    <a href="'+indexServer+'/safepersonal/safecenter" >安全中心</a>                                                                          '
//    '                                    <a href="'+indexServer+'/fund" >我要充值</a>                                                                          '+
 //   '                                    <a href="'+indexServer+'/withdraw" >我要提现</a>                                                                          '
;


    toolbar+=permisssion;
toolbar+='                                </p>                                                                                                                                          '+
    '                                <div class ="logo-out"><a href="'+indexServer+'/login/logout">退出登录</a></div>                                                             '+
    '                            </div>                                                                                                                                            '+
    '                        </li>                                                                                                                                                 '+
    '                       <li class="msg" id="'+msgClassId+'">                                                                                                                   '+
    '                            <span class="msg-title" id="msg-title">0</span>                                                                                                   '+
    '                            <div class="msg-box" id="msg-box">                                                                                                                '+
    '                                <div class="msg-hd"><i class="tri"></i><a href="'+indexServer+'/Service/inbox" class="more" >更多</a>                                         '+
    '                                    我的未读消息<strong >(<span id="msgcount">0</span>)</strong>                                                                             '+
    '                                </div>                                                                                                                                        '+
    '                                <div class="msg-bd" id="readmsg">                                                                                                             '+
    '                                <!--	<a href="">这里是消息提示提示消息</a>-->							                                                                   '+
    '                                </div>                                                                                                                                        '+
    '                            </div>                                                                                                                                            '+
    '                       </li>                                                                                                                                                  '+
    '                       <li class="balance">                                                                                                                                   '+
    '                           <div class="balance-box">                                                                                                                          '+
    '                                <span id="hiddBall" style="display:none">余额 <span id="spanBall">'+displayAvailBal+'</span>                                                  '+
    '                                    <i class="refreshBall"></i>                                                                                                               '+
    '                                </span>                                                                                                                                       '+
    '                               <span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span>                                                                 '+
    '                           </div>                                                                                                                                             '+
    '                       </li>                                                                                                                                                  '+
    '                       <li class="recharge"><a href="'+indexServer+'/fund" >充值</a></li>                                                                                     '+
    '                       <li class="withdrawals"><a href="'+indexServer+'/withdraw" >提现</a></li>                                                                              '+
    '                       <li class="help"><a target="_blank" href="'+indexServer+'/help/goIndex">帮助</a></li>                                                                                    '+ //'+userServer+'/help/goIndex
    '                       <li class="client-service"><a href="'+indexServer+'/ad/noticeList?noticeLevel=3">平台活动</a></li>                                                                               '+
    '</ul> ';
} else { 
	window.location.href=indexServer;  
 /*   toolbar +=
'    <ul class="bar-menu">'+
'        <!-- not log in menu start-->'+
'        <li class="welcome">您好，欢迎来到宝开!</li>'+
'        <li class="signin"><a href="javascript:void(0);">请登录</a>'+
'            <div class="top-login">'+
'                <div class="close-login"><img src="'+staticFileContextPath+'/static/images/indexv2/icon_close.png"></div>'+
'                    <div class="login">'+
'                        <div class="username"><span></span><input type="text" id="J-user-name1" placeholder="用户" autocomplete="off">'+
'                        </div>'+
'                            <div class="password"><span></span><input type="password" id="J-user-password1" autocomplete="off" placeholder="密码"><a href="'+indexServer+'/login/findpwd" target="_blank">忘记密码?</a></div>'+
'                                <div class="captcha">'+
'                                    <input type="text" id="J-verification1" data-name="verification" name="user-name" class="user-ver" placeholder="验证码" >'+
'                                        <div class="captchaimg">'+
'                                            <img class="var-img-area" id="J-vcode-img1" src="'+indexServer+'/login/changevcode" onClick="this.src='+indexServer+'\'/login/changevcode?r=\'+Math.random();" data-src-path="'+indexServer+'/login/changevcode" alt="点击图片刷新验证码" title="点击图片刷新验证码" />'+
'                                            <input type="hidden" id="J-loginParam1" data-name="loginParam" name="loginParam" value="'+loginRand+'" />'+
'                                        </div>'+
'                                    </div>'+
'                                    <div class="errormessage" id="J-msg-show1" style="height: 14px;"></div>'+
'                                    <div class="signin-btn"><a id="J-form-submit1"  href="javascript:void(0);">登 入</a></div>'+
'                                </div>'+
'                            </div>'+
'                        </li>'+
'                        <li class="freeregister"><a href="'+indexServer+registerurl+'">免费注册</a></li>'+
'                       <li class="help"><a href="javascript:void();">帮助</a></li>                                                                                   '+
'                       <li class="client-service"><a href="'+indexServer+'/ad/noticeList?noticeLevel=3">平台活动</a></li>                                                                                   '+
'                    </ul> '
;*/
}



		
		var target = document.getElementById("jsheader");
		//複寫html
		target.innerHTML = toolbar;	
		setTimeout(function() { }, 1);
    if(global_userID>0) {
        //读取，修改余额可见状态值
        var cookieAllball = $.cookie("showAllBall");
        if (cookieAllball == "1") {
            $('#hiddBall').css("display", "inline");
            $('#hiddenBall').css("display", "none");
        } else {
            $('#hiddBall').css("display", "none");
            $('#hiddenBall').css("display", "inline");
        }
        //显示余额
        $('#showAllBall').click(function () {
            var domain = GetCookieDomain();
            $.cookie("showAllBall", "1", { expires: 7,path: '/',domain:domain});
            $('#hiddBall').css("display", "inline");
            $('#hiddenBall').css("display", "none");
        });
        //隐藏余额
        $('#hiddBall').click(function () {
            var domain = GetCookieDomain();
            $.cookie("showAllBall", "0", { expires: 7,path: '/',domain:domain});
            $('#hiddBall').css("display", "none");
            $('#hiddenBall').css("display", "inline");
        });

        //站內信
        new phoenix.Hover({
            triggers: '#' + msgClassId,
            panels: '.msg-box',
            currClass: 'msg-trigger',
            hoverDelayOut: 300
        }); //J-top-user-message
        //使用者選單
        new phoenix.Hover({
            triggers: '#' + userClassId,
            panels: '.menu-nav',
            currClass: 'menu-trigger',
            hoverDelayOut: 300
        });	//=J-user-center
        //顶部用户信息
        new phoenix.Hover({triggers: '#J-top-userinfo', panels: '#J-top-userinfo .menu-nav', hoverDelayOut: 300});
    }
		// 顶部彩种菜单
		new phoenix.Hover({
				triggers:'#J-top-game-menu',
				panels:'#J-top-game-menu .game-menu-panel',
				hoverDelayOut:300,
				currClass:'game-menu-current'
		});
		new phoenix.Hover({
				triggers:'#J-top-game-menu2',
				panels:'#J-top-game-menu2 .game-menu-panel',
				hoverDelayOut:300,
				currClass:'game-menu-current'
		});
		new phoenix.Hover({triggers:'#J-top-game-menu2',panels:'#J-top-game-menu2 .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'});
		//在线客服
		$('#J-client-service').click(function(){
			$(this).find('.service-box').fadeIn().delay(3000).fadeOut();
		});
		//自动查询此用户未读信件(四处)
	
	var noreadmsg2 = "0";	

	try {
		//自动查询此用户未读信件(四处)
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url: '/service2/queryunreadmessage',				
			data:'',				
			success:function(json){				
				if(json.unreadCnt !=0){																
					var html = "";
					$.each(json.receives, function (i){
                                            html += "<a href=\""+indexServer+"/Service2/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+"</a>";	
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
           
           function changeHelpLink(){
                try{
                     //游戏说明链接
                     if (typeof phoenix.Games != "undefined" && $('.lottery-link').length > 0){
                          var helpLink = indexServer + phoenix.Games.getCurrentGame().getGameConfig().getInstance().defConfig["helpLink"];
                          $('.lottery-link').find('.info').attr('href',helpLink);
                      }
                 }catch(err){
                    setTimeout(changeHelpLink,500);
                 }
             }
             setTimeout(changeHelpLink,500);
	  
	   } catch (err) {		
				console.log("网络异常，读取信件信息失败");
		}
	
	//将数字保留两位小数并且千位使用逗号分隔
	function formatMoney(num){
		var num = Number(num),
			re = /(-?\d+)(\d{3})/;
			
		if(Number.prototype.toFixed){
			num = (num).toFixed(2);
		}else{
			num = Math.round(num*100)/100;
		}
		num  =  '' + num;
		while(re.test(num)){
			num = num.replace(re,"$1,$2");
		}
		return num;  
	};

    function GetCookieDomain() {
        var host = location.hostname;
        var ip = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        if (ip.test(host) === true || host === 'localhost') return host;
        var regex = /([^\/]*).*/;
        var match = host.match(regex);
        if (typeof match !== "undefined" && null !== match) host = match[1];
        if (typeof host !== "undefined" && null !== host) {
            var strAry = host.split(".");
            if (strAry.length > 1) {
                host = strAry[strAry.length - 2] + "." + strAry[strAry.length - 1];
            }
        }
        return '.' + host;
    };
	
	//金额刷新
	$('.refreshBall').click(function(event){
		var spanBalls = $('#spanBall');
		var userbaldata = "0";	
			
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url: userbal,		
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');
					 $('.refreshBall').hide();
				},
				success:function(data){	
					if(data || data===0){
						if(userbal.indexOf("queryUserBal")>=0)
							userbaldata = formatMoney(Number(data)/10000);
						else
							userbaldata = data["data"];
						
						spanBalls.removeAttr('style').text( userbaldata);
						$('.refreshBall').show();
					}
				},
				complete:function(){
					$('.refreshBall').show();					
				}
			});
		} catch (err) {		
			console.log("网络异常，读取信件信息失败");
		}
		 event.stopPropagation();
	});
    $('.refreshBall').click();
});

	function popup(link,names) {
        window.open(link, names, "width=800,height=600,directories=no,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no,menubar=no,z-look=yes");
    }
