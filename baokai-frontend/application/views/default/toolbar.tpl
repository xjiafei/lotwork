<div class="bg-toolbar"></div>
   <div class="toolbar">
	<div class="g_33">
		<div class="game-menu" id="J-top-game-menu">
           <span class="game-menu-text dropdown-menu-btn">彩票</span>
			<div class="game-menu-panel">
				<span class="triangle game-menu-triangle"><span></span></span>
				<div class="game-menu-inner">
					<div class="game-menu-box">
						<div class="game-menu-title">时时彩</div>
						<div class="game-menu-list">
							<a target="_blank" class="hot-game" href="{$game_server}/gameBet/cqssc/">重庆时时彩<i></i></a>
							<a target="_blank" href="{$game_server}/gameBet/jxssc/">江西时时彩</a>
							<a target="_blank" href="{$game_server}/gameBet/hljssc/">黑龙江时时彩</a>
							<a target="_blank" href="{$game_server}/gameBet/xjssc/">新疆时时彩</a>
							<a target="_blank" href="{$game_server}/gameBet/shssl/">上海时时乐</a>
							<a target="_blank" href="{$game_server}/gameBet/llssc/">宝开时时彩</a>
							<a target="_blank" href="{$game_server}/gameBet/tjssc/">天津时时彩</a>
							<a target="_blank" href="{$game_server}/gameBet/jlffc/">宝开1分彩</a>
							<a target="_blank" href="{$game_server}/gameBet/txffc/">腾讯分分彩</a>
							<a target="_blank" class="new-game" href="{$game_server}/gameBet/slmmc">秒开时时彩<i></i></a>
						</div>
					</div>
					<div class="game-menu-box">
						<div class="game-menu-title">11选5</div>
						<div class="game-menu-list">
							<a target="_blank" href="{$game_server}/gameBet/sd115/">山东11选5</a>
							<a target="_blank" href="{$game_server}/gameBet/jx115/">江西11选5</a>
							<a target="_blank" href="{$game_server}/gameBet/gd115/">广东11选5</a>
							<a target="_blank" href="{$game_server}/gameBet/ll115/">宝开11选5</a>
						</div>
					</div>
					<div class="game-menu-box">
						<div class="game-menu-title">快乐彩</div>
						<div class="game-menu-list">
							<a target="_blank" href="{$game_server}/gameBet/bjkl8/">北京快乐8</a>
						</div>
					</div>
					<div class="game-menu-box">
						<div class="game-menu-title">低频</div>
						<div class="game-menu-list">
							<a target="_blank" href="{$game_server}/gameBet/fc3d/">3D</a>
							<a target="_blank" href="{$game_server}/gameBet/p5/">排列3/5</a>
							<a target="_blank" href="{$game_server}/gameBet/ssq/">双色球</a>
						</div>
					</div>
				</div>
			</div>
		</div>
							<a href="/" class="back-top-home">老虎机</a>
           <a href="/index/" class="back-top-home">返回首页</a>
		<ul class="menu">
			<li class="username">您好，{$smarty.session.datas.info.account}</li>
			<li class="user hover" id="J-top-userinfo">
				<a href="#" class="dropdown-menu-btn">我的账户<i class="tri"></i></a>
				<div class="menu-nav">
					<i class="tri"></i>
                       <a target="_blank" href="{$game_server}/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7">投注记录</a>
					   <a target="_blank" href="/bet/fuddetail">账户明细</a>
					   <a target="_blank" href="/safepersonal/safecenter">安全中心</a>
                       <a target="_blank" href="{$game_server}/gameUserCenter/queryCurrentUserReport?userId={$smarty.session.datas.info.id}">报表查询</a>
                       <a target="_blank" href="/proxy/index">代理中心</a>
                       <!-- {/if} -->
				</div>
			</li>
			<li class="msg" id="J-top-user-message">
				<a href="#" class="msg-title" id="msg-title">0</a>
				<div class="msg-box">
					<div class="msg-hd"><i class="tri"></i><a href="/Service/inbox" class="more">更多</a>我的未读消息(<span id="msgcount">0</span>)</div>
					<div class="msg-bd" id="readmsg">
						
					</div>
				</div>
			</li>
			<li class="balance">
				<span id="hiddBall" style="display:none">余额：<span id="spanBall">{$displayAvailBal}</span><i class="refreshBall"></i></span>
				<span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span>
			</li>
			<li class="recharge" style="display:block"><a target="_blank" href="/fund">充值</a></li>
			<li class="withdrawals"><a target="_blank" href="/withdraw">提现</a></li>
                    <li class="chargeAppeal"><a href="/fundappeal/appealrechargelist">催到账</a></li>
			<li class="quit"><a href="/login/logout">退出</a></li>
		</ul>
	</div>
</div>

{literal}
<script>
// 顶部导航需要的脚本
(function($){
	$('[name="Page_firefrog_index"]').length>0?$('.back-top-home').hide():$('.back-top-home').show();
	
	// 顶部用户信息
	new phoenix.Hover({triggers:'#J-top-userinfo',panels:'#J-top-userinfo .menu-nav',hoverDelayOut:300});

	// 顶部彩种菜单
	new phoenix.Hover({triggers:'#J-top-game-menu',panels:'#J-top-game-menu .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'});

	// 顶部站内消息
	new phoenix.Hover({triggers:'#J-top-user-message',panels:'#J-top-user-message .msg-box',hoverDelayOut:300});

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
			url:'/service2/queryunreadmessage',				
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
	//setInterval(sUserMoney, 15000);	
	function sUserMoney(){		
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/index/getuserbal',				
				data:'',
				success:function(data){	
					if( data['status']=="ok")
					{
						setTimeout( function (){$('#spanBall').text(data["data"]); },500 );						
					}
				}
			});
		} catch (err) {		
			
		}
	}
})(jQuery);
</script>

{/literal}
<script type="text/javascript" src="{$path_js}/js/operations/removeIssue/removeIssueforGame.js"></script>


