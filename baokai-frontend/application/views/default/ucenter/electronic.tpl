<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"宝开彩票"}</title>
	<link href="{$path_img}/images/common/base.css" rel="stylesheet">
	<link href="{$path_img}/images/electronic/electronic.css" rel="stylesheet">
    <link rel="shortcut icon" href="{$path_img}/images/common/favicon.ico">
	<script>
    	function popup(link,names) {
        window.open(link, names, "width=800,height=600,directories=no,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no,menubar=no,z-look=yes")
    }
	</script>
</head>
<body>
	<div class="topbar">
	    <div class="topbar-bg"></div>
		    <div class="g_34">
		        <span class="domain-validate"><a href="http://www.ph158.info:8080/" target="_blank">域名验证</a></span>
		            <ul class="bar-menu">
				        <li class="user " id="J-user-center">
					        <a class="icon-user" href="javascript:;" >您好，{$smarty.session.datas.info.account}</a>
					        <div class="menu-nav">
						<i class="tri"></i>
                        <p>
                             <a  href="{$game_server}/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7">投注记录</a>
							 <a  href="/bet/fuddetail">账户明细</a>
							 <a  href="/safepersonal/safecenter">安全中心</a>
                            
                             <a  href="/proxy/index">代理中心</a>
							 <a  href="{$game_server}/gameUserCenter/queryCurrentUserReport?userId={$smarty.session.datas.info.id}">报表查询</a>
                             <!-- {/if} -->
							 </p>
							 <div class="logo-out">
							<a href="/login/logout">退出登录</a>
						</div>
					</div>

				</li>
				<li class="msg" id="J-top-user-message">
					<a href="javascript:void(0);" class="msg-title" id="msg-title">0</a>
					<div class="msg-box">
						<div class="msg-hd"><i class="tri"></i><a href="/Service/inbox" class="more">更多</a>我的未读消息(<span id="msgcount">0</span>)</div>
						<div class="msg-bd" id="readmsg">
							
						</div>
					</div>
				</li>
				<li class="balance">
				<div class="balance-box">
					<span id="hiddBall" style="display:none">余额：<span id="spanBall" name="spanBall">{$displayAvailBal}</span><i class="refreshBall"></i></span>
					<span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span>
				</div>
				</li>
				<li class="recharge"><a  href="/fund">充值</a></li>
				<li class="withdrawals"><a  href="/withdraw">提现</a></li>
                <li class="chargeAppeal"><a href="/fundappeal/appealrechargelist">催到账</a></li>
				<li class="help"><a  href="/help/goIndex" >帮助</a></li>
				<li class="client-service"><a  href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank">在线客服</a></li>
				
			</ul>
		</div>
	</div>
	<div class="header">
		<div class="g_34">
			<a href="/index" class="logo"></a>
			<ul class="main-menu">
				<li id="J-btn-lottery">
					<a class="link-sub" href="#">彩票</a>
					<div class="lottery-link">
						<i class="tri"></i>
						<div class="lottery-type">
							<h3 class="type-title">时时彩</h3>
							<div class="type-list two-line">
								<ul>
									<li><a href="{$game_server}/gameBet/cqssc/">重庆时时彩</a><span class="icon-hot"></span></li>
									<li><a href="{$game_server}/gameBet/hljssc/">黑龙江时时彩</a></li>
									<li><a href="{$game_server}/gameBet/shssl/">上海时时乐</a></li>
									<li><a href="{$game_server}/gameBet/tjssc/">天津时时彩</a></li>
									<li><a href="{$game_server}/gameBet/slmmc">秒开时时彩</a><span class="icon-new"></span></li>
								</ul>
								<ul>
									<li><a href="{$game_server}/gameBet/jxssc/">江西时时彩</a></li>
									<li><a href="{$game_server}/gameBet/xjssc/">新疆时时彩</a></li>
									<li><a href="{$game_server}/gameBet/llssc/">宝开时时彩</a></li>
									<li><a href="{$game_server}/gameBet/jlffc/">宝开1分彩</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">11选5</h3>
							<div class="type-list">
								<ul>
									<li><a href="{$game_server}/gameBet/sd115/">山东11选5</a></li>
									<li><a href="{$game_server}/gameBet/jx115/">江西11选5</a></li>
									<li><a href="{$game_server}/gameBet/gd115/">广东11选5</a></li>
									<li><a href="{$game_server}/gameBet/ll115/">宝开11选5</a></li>
									<li><a href="{$game_server}/gameBet/sl115/">秒开11选5</a><span class="icon-new"></span></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">快乐彩</h3>
							<div class="type-list">
								<ul>
									<li><a href="{$game_server}/gameBet/bjkl8/">北京快乐8</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">快三</h3>
							<div class="type-list">
								<ul>
									<li><a href="{$game_server}/gameBet/jsk3/">江苏快三</a></li>
									<li><a href="{$game_server}/gameBet/ahk3/">安徽快三</a></li>
									<li><a href="{$game_server}/gameBet/jsdice/">江苏骰宝</a></li>
								</ul>
							</div>
						</div>
						<div class="lottery-type">
							<h3 class="type-title">低频</h3>
							<div class="type-list">
								<ul>
									<li><a href="{$game_server}/gameBet/fc3d/">3D</a></li>
									<li><a href="{$game_server}/gameBet/p5/">排列3/5</a></li>
									<li><a href="{$game_server}/gameBet/ssq/">双色球</a></li>
								</ul>
							</div>
						</div>
					</div>
				</li>
				<li id="J-btn-elegame">
					<a class="link-sub" href="#">宝开游艺</a>
					<div class="ele-game">
						<i class="tri"></i>
						<div class="ele-link ele-btn">
							<h3 class="ele-title">PT老虎机</h3>
							<a href="{$ptgame_server}/pt/index/">立即进入</a>
						</div>
						<div class="ele-link">
							<h3 class="ele-title">休闲游戏</h3>
							<ul>
								<li><a href="">金皇冠</a></li>
								<li><a href="">神奇七</a></li>
								<li><a href="">百变五张</a></li>
								<li><a href="">拜金女郎</a></li>
								<li><a href="">水果机</a></li>
								<li><a href="">十二生肖</a></li>
								<li><a href="">西游转转乐</a></li>
							</ul>
						</div>
						<div class="ele-link ele-btn ele-noborder">
							<h3 class="ele-title">FHX</h3>
							<a href="/fhx/index">FHX客户端下载</a>
						</div>
						<div class="cover-menu-cgame"></div>
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
	<div class="game-content">
		<div class="pt-fhx">
			<div class="game-pt">
				<a class="link-btn goto-pt" href="{$ptgame_server}/pt/index/"></a>
			</div>
			<div class="game-fhx">
				<a class="link-btn goto-fhx" href="/fhx/index"></a>
			</div>
			
		</div>
		<div class="casual-game">
			<ul class="casual-game-list">
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame1.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame2.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame3.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame4.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame5.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame6.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="{$path_img}/images/electronic/game/cgame7.jpg">
						<div class="link-title"><span>进入游戏</span></div>
					</a>
				</li>
			</ul>
			<div class="cover-cgame"></div>
		</div>
	</div>

<div class="footer footer-bottom">
    <div class="footer-help">
        <div class="footer-m">
            <div class="footer-help-link">
                <a href="javascript:void(0);" >玩法介绍</a>
                <a href="javascript:void(0);" >如何充值</a>
                <a href="javascript:void(0);" >提现须知</a>
            </div>
        </div>
    </div>
    <div class="footer-link">
        <div class="g_34">

            <p class="footer-copyright">©2014-2017宝开彩票 All Rights Reserved</p>
        </div>
    </div>
</div>

			</div>
		</div>
	</div>

	<script type="text/javascript">
		global_path_url="http://static.phl58.co/static";
		hjUserData= "luck888|||||菲律宾||||1198908|0|";
		global_userName="luck888";
		global_userID="1198908";
		global_params_notice = "all,ad_top,agent_first_page";


	</script>

	<!--基础工具-->
	<script src="{$path_js}/js/base-all.js"></script>
	<script src="{$path_js}/js/electronic/electronic.js"></script>
	<script src="{$path_js}/js/phoenix.ga.js"></script>
	<script type="text/javascript">
		global_path_url="{$path_img}";
                 
                dynamicroot ="{$dynamicroot}";
		gameserver = "{$game_server}";
		hjUserData= "{$hjUserData}";
		path = "{$path_js}";
		global_userName="{$smarty.session.datas.info.account}";
		global_userID="{$smarty.session.datas.info.id}";
		global_params_notice = "all,ad_top,agent_first_page";
		var sEndTime = {$gameResult[$playgameid]['saleEndTime']|default:0};
		var sNowTime = {$nowTime|default:0};
		(function() {       
			function async_load(){           
				var s = document.createElement('script');          
				s.type = 'text/javascript';          
				s.async = true;           
				s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s={if $smarty.session.datas.info.vipLvl >= '1'}20{else}17{/if}";           
				var x = document.getElementsByTagName('script')[0];          
				x.parentNode.insertBefore(s, x);      
			}       
		if (window.attachEvent)           
		window.attachEvent('onload', async_load);
		else 
		window.addEventListener('load', async_load, false);  
		})();
	</script>

</body>
</html>
