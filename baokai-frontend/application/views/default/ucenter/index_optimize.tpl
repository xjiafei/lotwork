<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"宝开彩票"}</title>
	<link href="{$path_img}/images/common/base.min.css" rel="stylesheet">
	<link href="{$path_img}/images/index/index.min.css" rel="stylesheet">
    <link rel="shortcut icon" href=""{$path_img}/images/common/favicon.ico">
</head>
<body>
	<div class="bg-toolbar"></div>
	<div class="toolbar">
		<div class="g_33">
			<div class="game-menu" id="J-top-game-menu">
            <span class="game-menu-text dropdown-menu-btn">宝开彩票</span>
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
							<div class="game-menu-title">快三</div>
							<div class="game-menu-list">
								<a target="_blank" href="{$game_server}/gameBet/jsk3/">江苏快三</a>
								<a target="_blank" href="{$game_server}/gameBet/ahk3/">安徽快三</a>
								<a target="_blank" href="{$game_server}/gameBet/jsdice/">江苏骰宝</a>
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
            <a href="/index/" class="back-top-home">返回首页</a>
			<ul class="menu">
				<li class="username">您好，{$smarty.session.datas.info.account}</li>
				<li class="user hover" id="J-top-userinfo">
					<a href="javascript:void(0);" class="dropdown-menu-btn">我的账户<i class="tri"></i></a>
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
					<a href="javascript:void(0);" class="msg-title" id="msg-title">0</a>
					<div class="msg-box">
						<div class="msg-hd"><i class="tri"></i><a href="/Service/inbox" class="more">更多</a>我的未读消息(<span id="msgcount">0</span>)</div>
						<div class="msg-bd" id="readmsg">
							
						</div>
					</div>
				</li>
				<li class="balance">
					<span id="hiddBall" style="display:none">余额：<span id="spanBall" name="spanBall">{$displayAvailBal}</span><i class="refreshBall"></i></span>
					<span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span>
				</li>
				<li class="recharge"><a target="_blank" href="/fund">充值</a></li>
				<li class="withdrawals"><a target="_blank" href="/withdraw">提现</a></li>
                            <li class="chargeAppeal"><a href="/fundappeal/appealrechargelist">催到账</a></li>
				<li class="quit"><a href="/login/logout">退出</a></li>
			</ul>
		</div>
	</div>
    
	<div class="header">
		<div class="g_33">
			<a href="/index/" class="logo" name="Page_firefrog_index"></a>
			<div class="service">
				<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1"  class="link-service" title="客服">联系客服</a>
				<a href="/help/goIndex" target="_blank" class="link-help" title="帮助">投注指南</a>
			</div>
		</div>
	</div>
	<div class="container-top">
		<div class="g_33">
			<div class="user-tools">
				<h2 class="user-name">欢迎你,<strong>{$smarty.session.datas.info.account}</strong></h2>
				<div class="user-info clearfix">
					<!-- {if $smarty.session.datas.info.vipLvl eq '1'} -->
						<a href="/vip/" class="user-head user-head-vip" target="_blank" ></a>
					<!-- {else} -->
						<a href="/vip/" class="user-head user-head-normal" target="_blank" ></a>
					<!-- {/if} --> 
					<div class="user-balance">
						<div class="user-balance-title">可用余额</div>
						<div class="user-balance-number" name="spanBall">{$displayAvailBal}</div>
					</div>
				</div>
				<div class="safe-info">
					<div class="safe-info-title">{if $safeLevel neq 100}<a href="/safepersonal/safecenter/" target="_blank">提升</a>{/if}安全等级：
					{if $safeLevel eq 100}高{else if $safeLevel eq 60 or $safeLevel eq 80}中{else}低{/if}
					</div>
					<div class="safe-info-progress ">
						<div class="progress-wapper progress-{if $safeLevel eq 100}high{else if $safeLevel eq 60 or $safeLevel eq 80}middle{else}low{/if}"><div class="progress-inner" style="width:{$safeLevel}%"></div></div>
					</div>
					<div class="safe-info-ip">
						上次登录:
						<!-- {if $lastLoginDate eq ''} -->
						首次登录平台
						<!-- {else} -->
						{$lastLoginDate}
						<!-- {/if} -->
						</br>{$smarty.session.datas.info.lastArea}
					</div>
				</div>
				<div class="account-info">
					<a target="_blank" href="/fund/" class="btn-recharge">充值</a>
					<div class="account-info-btn clearfix">
						<a target="_blank" href="/withdraw/" class="btn-withdrawals">提现</a>
						<a target="_blank" href="/transfer/" class="btn-transfer">转账</a>
					</div>
					<div class="account-info-link clearfix">
						<a href="/help/queryGeneralDetail?helpId=917" target="_blank">如何充值</a>
						<a href="/help/queryGeneralDetail?helpId=738" target="_blank">提现须知</a>
					</div>
				</div>
			</div>
			<div id="focus"
				data-cycle-slides="> .item"
				data-cycle-pager="> .cycle-pager-wrap .cycle-pager"
				data-cycle-prev="> .prev"
				data-cycle-next="> .next"
				data-cycle-fx="scrollHorz"
				data-cycle-timeout="4000"
				data-cycle-loader="wait"
				data-cycle-speed="800"
				data-pause-on-hover="true"
				>
				<div class="cycle-pager-wrap">
					<div class="cycle-pager"></div>
				</div>
				<span class="prev_next prev">&#9664;</span>
				<span class="prev_next next">&#9654;</span>
			</div>
		</div>
	</div>
	<div class="container-body">
		<div class="g_33">
			<div class="col-main">
				<div class="lottery-main">
					<div class="lottery-main-hd">
						<div class="deadline-text">距离<strong id="nowNumber">{$gameResult['99101']['currentIssue']}</strong>期截止</div>
						<div class="deadline-number">
							{if $gameResult['99101']['saleEndDate']["hour"][0] neq '0' or $gameResult['99101']['saleEndDate']["hour"][1] neq '0'}
							<em class="hour-left">{$gameResult['99101']['saleEndDate']["hour"][0]}</em>
							<em class="hour-right">{$gameResult['99101']['saleEndDate']["hour"][1]}</em>
							<span>:</span>
							{/if}
							<em class="min-left">{$gameResult['99101']['saleEndDate']["min"][0]}</em>
							<em class="min-right">{$gameResult['99101']['saleEndDate']["min"][1]}</em>
							<span>:</span>
							<em class="sec-left">{$gameResult['99101']['saleEndDate']["sec"][0]}</em>
							<em class="sec-right">{$gameResult['99101']['saleEndDate']["sec"][1]}</em>
						</div>
						<a href="{$game_server}/gameBet/cqssc/" class="deadline-btn">立即投注</a>
					</div>
					<div class="lottery-main-bd">
						<a href="{$game_server}/gameBet/sd115/" class="lottery-main-pic1"><span></span></a>
						<a href="{$game_server}/gameBet/jxssc/" class="lottery-main-pic2"><span></span></a>
						<a href="{$game_server}/gameBet/gd115/" class="lottery-main-pic3"><span></span></a>
						<a href="{$game_server}/gameBet/shssl/" class="lottery-main-pic4"><span></span></a>
						<a href="{$game_server}/gameBet/jx115/" class="lottery-main-pic5"><span></span></a>
						<a href="{$game_server}/gameBet/bjkl8/" class="lottery-main-pic6"><span></span></a>
						<a href="{$game_server}/gameBet/jsk3/" class="lottery-main-pic7"><span></span></a>
						<a href="{$game_server}/gameBet/jsdice/" class="lottery-main-pic8"><span></span></a>
					</div>
				</div>
				<div class="lottery-sub">
					<div class="lottery-sub-hd">
						<ul class="omit-number">
							{foreach from=$gameResult['99108']['omityTrend'] item=data}
								<li>
									<strong>{$data[0]}</strong>
									<span>{$data[1]}</span>
								</li>
							{/foreach}
						</ul>
						<div class="omit-text">当前遗漏号码</div>
						<a href="{$game_server}/gameBet/fc3d/" class="omit-btn">立即投注</a>
					</div>
					<div class="lottery-sub-bd">
						<div class="p35">
							<a href="{$game_server}/gameBet/p5/" class="p35-btn"></a>
							<ul class="p35-number">
								{foreach from=$gameResult['99109']['omityTrend'] item=data}
									<li>
										<strong>{$data[0]}</strong>
										<span>{$data[1]}</span>
									</li>
								{/foreach}
							</ul>
							<div class="p35-text">当前遗漏号码</div>
						</div>
						<div class="ssq">
							<a href="{$game_server}/gameBet/ssq/" class="ssq-btn"></a>
							<div class="ssq-number">
								<em>&yen;</em>
								<em>6</em>
								<em>0</em>
								<em>0</em>
								<em>0</em>
								<em>0</em>
								<em>0</em>
								<em>0</em>
								<i class="comma-m"></i>
								<i class="comma-t"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="lottery-extra">
					<div class="lottery-extra-hd">
						<ul class="mmc-info">
							{foreach from=$gameResult['99112']['wins'] item=data}
								<li>{$data.username}&nbsp;&nbsp;中奖{$data.prize}元</li>
							{/foreach}
						</ul>
						<a href="{$game_server}/gameBet/slmmc" class="mmc-btn">立即投注</a>
					</div>
					<div class="lottery-extra-bd">
						<a class="lottery-extra-pic1" href="{$game_server}/gameBet/llssc?isVideo=1"></a>
						<a class="lottery-extra-pic2" href="{$game_server}/gameBet/ll115?isVideo=1"></a>
						<a class="lottery-extra-pic3" href="{$game_server}/gameBet/jlffc/"></a>
						<div class="lottery-extra-bd-text">
							<ul class="lelicai-animate">
								{foreach from=$gameResult['99106']['wins'] item=data}
								<li>{$data.username}&nbsp;&nbsp;中奖{$data.prize}元</li>
								{/foreach}
							</ul>
							<ul class="n115-animate">
								{foreach from=$gameResult['99305']['wins'] item=data}
								<li>{$data.username}&nbsp;&nbsp;中奖{$data.prize}元</li>
								{/foreach}
							</ul>
							<ul class="jili-animate">
								{foreach from=$gameResult['99111']['wins'] item=data}
								<li>{$data.username}&nbsp;&nbsp;中奖{$data.prize}元</li>
								{/foreach}
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sub">
				<div class="news-list">
					<div class="news-list-hd"><a class="more" href="/ad/noticeList"  target="_blank">更多</a>平台动态</div>
					<ul class="news-list-bd">
						<!-- {foreach from=$result.lastNotice item=data} -->
							<li>
								<a href="/ad/adNoticeQuery?id={$data.id}" target="_blank">{$data.title}</a>
								<span>{$data.gmtEffectBegin}</span>
							</li>
						<!-- {/foreach}	 -->
					</ul>
				</div>
                <div id="J-globalad-index_down_app" class="download-app"></div>
                <div id='J-globalad-index_beginner_guide' class="beginner-guide"></div>
			</div>
		</div>
	</div>
	<!-- {if $smarty.session.datas.info.userLvl neq '0' and $result.qqs[0]['qq'] neq ''} -->
	<div id="J-dialog-qq" class="dialog-qqlist">
		<a id="J-dialogqq-close" href="#" class="close" title="关闭"></a>
		<h4>选择在线的上级联络</h4>
		<div class="qq-list">
		<!-- {foreach from=$result.qqs item=data} -->
			<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin={$data.qq}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:{$data.qq}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
		<!-- {/foreach}	 -->
		</div>
	</div>
	<!-- {/if} -->
	<div class="footer">
		<div class="footer-help">
			<div class="g_33">
				<div class="footer-help-link">
					<a href="/help/queryLotterylHelp?cateId2=576&cate2Name=%25E6%2597%25B6%25E6%2597%25B6%25E5%25BD%25A9&orderBy=no%20desc,gmt_created%20desc" target="_blank">玩法介绍</a>
					<a href="/help/queryGeneralDetail?helpId=917" target="_blank">如何充值</a>
					<a href="/help/queryGeneralDetail?helpId=738" target="_blank">提现须知</a>
				</div>
			</div>
		</div>
		<div class="footer-link">
			<div class="g_33">
		      
                <p class="footer-copyright">&copy;2014-2017 宝开彩票 All Rights Reserved</p>
             </div>
		</div>
	</div>
	<!-- {if $smarty.session.datas.info.userLvl neq '0'} -->
	<div class="slider-bar" id="sliderBar">
		<div class="hd"><a href="javascript:void(0);" id='sliderBar_close' class="close"></a>联系上级</div>
		<div class="bd">
			<!-- {if $result.qqs[0]['qq'] neq ''} -->
			<a id="J-button-qq" href="javascript:void(0);" class="qq"></a>
			<!-- {/if} -->
			<a href="/Service/servicesup?unread=2" target="_blank" class="letters"></a>
		</div>
	</div>
	<!-- {/if} -->
	<!-- {if $dailyBonus neq '1'} -->
	<div id="eggBar" class="egg-bar">
		<i class="egg-bar-tip animation wobble"></i>
	</div>
	<div id="eggPop" class="egg-pop">
		<a href="javascript:void(0);" id="eggClose" class="egg-close"></a>
		<div class="hammer" id="hammer">锤子</div>
		<div class="egg-tip"><div id="result" class="price"></div></div>
		<div class="egg-tips egg-tip1 animation wobble"></div>
		<div class="egg-tips egg-tip2 animation wobble"></div>
		<div class="egg-tips egg-tip3 animation wobble"></div>
		<div class="egg-light"></div>
		<a href="javascript:void(0);" class="egg-btn"></a>
		<ul class="egg-list clearfix">
			<li></li>
			<li></li>
			<li></li>
		</ul>
		<p class="egg-info">您每天可参与一次“砸蛋抢金”活动<br />选择一个金蛋砸开，可立即获取奖金<br />奖金将计入您的账户余额<br />砸蛋进度于每天00:00重置</p>
	</div>
	<!-- {/if} -->
	<script type="text/javascript">
		global_path_url="{$path_img}";
		hjUserData= "{$hjUserData}";
		global_userName="{$smarty.session.datas.info.account}";
		global_userID="{$smarty.session.datas.info.id}";
		global_params_notice = "all,ad_top,agent_first_page";
		var sEndTime = {$gameResult['99101']['saleEndTime']|default:0};
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
	<script type="text/javascript" src="{$path_js}/js/base-all.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
	<script type="text/javascript" src="{$path_js}/js/index/index.min.js"></script>
    <!-- {if $lastLoginDate eq ''} -->
    <script type="text/javascript">
	    // showGuide表示页面是否播放引导
		// 通常页面已经播放过就不再播放
	    $.getScript(global_path_url+'/js/guide.js', function(){
           var guide = $('body').pageGuide(param);
		});
    </script>
    <!-- {/if} -->
</body>
</html>
