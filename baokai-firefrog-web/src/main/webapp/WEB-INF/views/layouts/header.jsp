<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!-- toolbar start -->
   <div class="topbar">
		<div class="topbar-bg"></div>
		<div class="g_34">
			<span class="domain-validate"><a href="http://www.ph158.info:8080/"  target="_blank">域名验证</a></span>
		  
			<ul class="bar-menu">
				<li class="user" id="J-user-center">
					<a class="icon-user" href="javascript:;" >您好，${userName}</a>
					<div class="menu-nav">
						<i class="tri"></i>
						<p>
                        <a href="${frontGame}/gameUserCenter/queryOrders?time=7">投注记录</a>
                        <c:if test="${ isAllZero !=0}">
						<c:if test="${userlvl!=-1}">
	                        <a href="/proxy/index">代理中心</a>
	                        <a href="${frontGame}/gameUserCenter/queryCurrentUserReport">报表管理</a>
                        </c:if></c:if>
                        <a href="${currentContextPath}/bet/fuddetail">账户明细</a>
                        <a href="${currentContextPath}/safepersonal/safecenter">安全中心</a>
                        </p>
                        <div class ="logo-out">
                        <a href="${currentContextPath}/login/logout">退出登录</a>
                        </div>
					</div>

				</li>
				
				
				<li class="msg" id="J-top-user-message">
					<a href="#" class="msg-title" id="msg-title">0</a>
					<div class="msg-box">
						<div class="msg-hd"><i class="tri"></i><a href="${currentContextPath}/Service/inbox" class="more">更多</a>我的未读消息(<span id="msgcount">0</span>)</div>
						<div class="msg-bd" id="readmsg">
							
						</div>
					</div>
				</li>
				<li class="balance">
				<div class="balance-box">
					<span id="hiddBall" style="display:none">余额：<span id="spanBall"><c:if test="${displayAvailBal==null}">0.00</c:if>  <c:if test="${displayAvailBal!=null}">${displayAvailBal}</c:if> </span><i class="refreshBall"></i></span>
					<span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span>
				</div></li>
				<li class="recharge"><a href="${currentContextPath}/fund">充值</a></li>
				<li class="withdrawals"><a href="${currentContextPath}/withdraw">提现</a></li>
				 <!-- <li class="chargeAppeal"><a href="${currentContextPath}/fundappeal/appealrechargelist">催到账</a></li> -->
				  <li class="help"><a href="${userContextPath}/help/goIndex">帮助</a></li>  
                <li class=" client-service"><a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>
					<div class="service-box"><i class="tri"></i>在线客服打不开？
                        <a class="support_service_link" a="${userName}" l="${sessionScope.info.vipLvl}" aid="${sessionScope.info.id}">请点击</a>
                    </div>
                </li>               
			</ul>
		</div>
	</div>
	
	<script>
		function popup(link,names) {
			window.open(link, names, "width=800,height=600,directories=no,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no,menubar=no,z-look=yes")
		}
	</script>
	
	<!-- toolbar end -->
	<!-- header start -->
	<div class="header">
		<div class="g_33">
		 <div style="height:10px">
	      
	    </div>
			<a class="logo" title="首页" href="/index"></a>
			<ul class="main-menu">
				<li id="J-btn-lottery">
					<a class="link-sub" href="#">彩票</a>
					<div class="lottery-link">
						<i class="tri"></i>
						<div class="lottery-type">
							<h3 class="type-title">时时彩</h3>
							<div class="type-list two-line">
								<ul>
									<li><a href="${frontGame}/gameBet/cqssc/">重庆时时彩</a><span class="icon-hot"></span></li>
									<li><a href="${frontGame}/gameBet/hljssc/">黑龙江时时彩</a></li>
									<li><a href="${frontGame}/gameBet/shssl/">上海时时乐</a></li>
									<li><a href="${frontGame}/gameBet/tjssc/">天津时时彩</a></li>
									<li><a href="${frontGame}/gameBet/slmmc">顺利秒秒彩</a><span class="icon-new"></span></li>
								</ul>
								<ul>
									<li><a href="${frontGame}/gameBet/jxssc/">江西时时彩</a></li>
									<li><a href="${frontGame}/gameBet/xjssc/">新疆时时彩</a></li>
									<li><a href="${frontGame}/gameBet/llssc/">乐利时时彩</a></li>
									<li><a href="${frontGame}/gameBet/jlffc/">吉利分分彩</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">11选5</h3>
							<div class="type-list">
								<ul>
									<li><a href="${frontGame}/gameBet/sd115/">山东11选5</a></li>
									<li><a href="${frontGame}/gameBet/jx115/">江西11选5</a></li>
									<li><a href="${frontGame}/gameBet/gd115/">广东11选5</a></li>
									<li><a href="${frontGame}/gameBet/ll115/">乐利11选5</a></li>
									<li><a href="${frontGame}/gameBet/sl115/">顺利11选5</a><span class="icon-new"></span></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">快乐彩</h3>
							<div class="type-list">
								<ul>
									<li><a href="${frontGame}/gameBet/bjkl8/">北京快乐8</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">快三</h3>
							<div class="type-list">
								<ul>
									<li><a href="${frontGame}/gameBet/jsk3/">江苏快三</a></li>
									<li><a href="${frontGame}/gameBet/ahk3/">安徽快三</a></li>
									<li><a href="${frontGame}/gameBet/jsdice/">江苏骰宝</a></li>
									<li><a href="${frontGame}/gameBet/jldice1/" class="new-game">吉利骰宝(娱乐厅)</a></li>
									<li><a href="${frontGame}/gameBet/jldice2/" class="new-game">吉利骰宝(至尊厅)</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">低频</h3>
							<div class="type-list">
								<ul>
									<li><a href="${frontGame}/gameBet/fc3d/">3D</a></li>
									<li><a href="${frontGame}/gameBet/p5/">排列3/5</a></li>
									<li><a href="${frontGame}/gameBet/ssq/">双色球</a></li>
								</ul>
							</div>
						</div>
					</div>
				</li>
				<li><a href="/ad/noticeList?noticeLevel=3">平台活动</a></li>
				<li id="J-btn-download">
					<a class="link-sub" href="javascript:;">下载中心</a>
					<div class="app-download">
						<i class="tri"></i>
						<div class="download-btn">
							<div class="download-app">
								<a href="http://mobile.ios188.com:6060/mobileApp/index.html">手机版下载</a>
							</div>
							<div class="download-safe">
								<a href="http://mobile.ios188.com:6060/safeApp/index.html">安全中心</a>
							</div>
							<div class="download-exe">
								<a href="http://ptclient.b0.upaiyun.com/pt_client.exe">PT客户端中心</a>
							</div>
							<div class="download-login">
								<a href="http://www.ph158.info:8080/quicklogin.html" target="_blank">快速登录器</a>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
		</div>
	</div>
		</div>
	</div>
	<script defer="true" type="text/javascript" src="${currentContextPath}/support/support_service?p=FF4"></script>
    <script type="text/javascript">
(function($){
	
	$('[name="Page_firefrog_index"]').length>0?$('.back-top-home').hide():$('.back-top-home').show();
	
	// 顶部用户信息
	new phoenix.Hover({triggers:'#J-user-center',panels:'#J-user-center .menu-nav',hoverDelayOut:300});

	// 顶部彩种菜单
	new phoenix.Hover({triggers:'#J-top-game-menu',panels:'#J-top-game-menu .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'});

	// 顶部站内消息
	new phoenix.Hover({triggers:'#J-top-user-message',panels:'#J-top-user-message .msg-box',hoverDelayOut:300});
	
	// 顶部彩种菜单
	new phoenix.Hover({triggers:'#J-btn-lottery',panels:'#J-btn-lottery .lottery-link',hoverDelayOut:300,currClass:'game-menu-current'});
	// 彩种链接
	new phoenix.Hover({
		triggers: '#J-btn-elegame',
		panels: '#J-btn-elegame .ele-game',
		hoverDelayOut: 300
	});
		new phoenix.Hover({triggers:'#J-btn-download',panels:'#J-btn-download .app-download',hoverDelayOut :300});
		
	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if(cookieAllball=="1"){	
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	}else{	
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
	try {
			//自动查询此用户未读信件
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/service/queryunreadmessage',				
				data:'',				
				success:function(json){	
					if(json.unreadCnt !=0){																
						var html = "";
						$.each(json.receives, function (i){
							if(i==4){
								html += "<a href=\"/Service/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].title+"..."+"</a>";								
							}
							else{
								html += "<a href=\"/Service/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].title+"</a>";
							}
						});
						$("#readmsg").html(html);	
						$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));												
					
					}
					else {					
						$("#readmsg").html("暂未收到新消息");
						$('#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
						$('#readmsg').attr("style","text-align:center; color:black;");			
					}
				}			
			});
		    } catch (err) {		
				
		    }
	
	 //金额刷新
	$('.refreshBall').click(function(event){
		var spanBalls = $('#spanBall');	
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/index/getuserbal',			
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');
					 $('.refreshBall').hide();
				},
				success:function(data){	
					if( data['status']=="ok")
					{
						spanBalls.removeAttr('style').text(data["data"]);
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

	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if(cookieAllball=="1"){	
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	}else{	
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	}
	
	     try {
			//用户余额查询
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/index/getuserbal',				
				data:'',				
				success:function(data){	
					if( data['status']=="ok" &&  parseInt(data['data']) > 0)
					{
						$("#spanBall").text(data['data']);
					}
				}
			});
	    } catch (err) {		
			
	    }
	
	
})(jQuery);

</script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/removeIssue/removeIssueforGame.js" ></script>
	<!-- header end -->
