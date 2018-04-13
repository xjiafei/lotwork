<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>{$title|default:"宝开彩票"}</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="{$path_img}/images/common/base.css" rel="stylesheet">
<link href="{$path_img}/images/ucenter/index.css" rel="stylesheet">
{include file='/default/script-base.tpl'}
</head>
<body class="ucenter-index">
    <!-- toolbar start -->
	<div class="bg-toolbar"></div>
	<div class="toolbar">
		<div class="g_33">
				<!-- <a href="#" class="download-app">手机投注送红包，速度下载！</a> -->
				<ul class="menu">
					<li class="user hover" id="J-user-panel">
						<a href="#">您好，{$smarty.session.datas.info.account}<i class="tri" id="J-user-tri"></i></a>
						<div class="menu-nav">
							<i class="tri"></i>
							<a href="{$game_server}/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7">游戏记录</a>
							<a href="{$game_server}/gameUserCenter/queryCurrentUserReport?userId={$smarty.session.datas.info.id}">报表管理</a>
							<a href="/bet/fuddetail">资金明细</a>
							<a href="/safepersonal/safecenter">安全中心</a>
							<a href="/login/logout">安全退出</a>
						</div>
					</li>
					<li class="msg" id="J-msg-panel">
						<span class="msg-title" id="msg-title">0</span>
						<div class="msg-box" id="msg-box">
							<div class="msg-hd"><i class="tri"></i><a href="/Service/inbox" class="more">更多</a>我的未读消息<strong >(<span id="msgcount">0</span>)</strong></div>
							<div class="msg-bd" id="readmsg">
							<!--	<a href="">这里是消息提示提示消息</a>-->							
							</div>
						</div>
					</li>
					<li class="balance"><span id="hiddBall" style="display:none">余额：<span id="spanBall">{$displayAvailBal}</span></span><span id="hiddenBall">余额已隐藏 <a href="#" id="showAllBall">显示</a></span></li>
					<li class="recharge"><a href="/fund">充值</a></li>
					<li class="withdrawals"><a href="/withdraw">提现</a></li>
					<!--<li class="withdrawals"><a target="_blank" href="{$game_server}/gameBet/bet?lotteryId=99101">游戏入口</a></li>-->
                                   <li class="chargeAppeal"><a href="/fundappeal/appealrechargelist">催到账</a></li>
					<li class="quit"><a href="/login/logout">退出</a></li>
				</ul>
		</div>
	</div>
	<!-- toolbar end -->
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
			<div class="service">
				<a title="客服" class="link-service" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552"javascript:hj5107.openChat()"configID=2945"javascript:hj5107.openChat()"jid=4611765122"javascript:hj5107.openChat()"skillId=145"javascript:hj5107.openChat()"s=1" />在线客服</a>
				<a title="帮助" target="_blank" class="link-help" href="/help/goIndex">帮助</a>
			</div>
		</div>
	</div>
	<!-- header end -->
	<!-- content start -->
	<div class="uc-container clearfix common-main">
		<div class="left-menu">
			<h4 class="high">高频</h4>
			<ul>
				<!--<li><a target="_blank" href="{$game_server}/gameBet/bet?lotteryId=99101&userId={$smarty.session.datas.info.id}">重庆时时彩</a></li>-->
                <li><a target="_blank" href="{$game_server}/gameBet/cqssc/">重庆时时彩</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/jxssc/">江西时时彩</a></li>
				<li class="award"><a target="_blank" href="{$game_server}/gameBet/hljssc/">黑龙江时时彩</a><i class="icon-award"></i></li>
				<li><a target="_blank" href="{$game_server}/gameBet/xjssc/">新疆时时彩</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/tjssc/">天津时时彩</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/llssc/">宝开时时彩</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/shssl/">上海时时乐</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/sd115/">山东11选5</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/jx115/">江西11选5</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/gd115/">广东11选5</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/cq115/">重庆11选5</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/ll115/">宝开11选5</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/bjkl8/">北京快乐8</a></li>
			</ul>
			<div class="line"></div>
			<h4 class="low">低频</h4>
			<ul>
				<li class="new">
					<a target="_blank" href="{$game_server}/gameBet/ssq/">双色球</a>
					<i class="icon-new"></i>
				</li>
				<li><a target="_blank" href="{$game_server}/gameBet/fc3d/">福彩3D</a></li>
				<li><a target="_blank" href="{$game_server}/gameBet/p5/">排列3/5</a></li>
			</ul>
		</div>
		<div class="content">
			<div class="clearfix">
				<!--user-info-area-->
				<div class="user-info">
					<div class="info">
						<div class="account">
							下午好, {$smarty.session.datas.info.account}
						</div> 
						<!-- <a href="#" class="post-mail">发送站内信</a> -->
					</div>
					<div class="balance">
						余额: <em>{$displayAvailBal|default:0}</em> 元
					</div>
					<div class="clearfix">
						<a href="/fund" class="recharge">充 值</a>
						<a href="/withdraw" class="withdrawal">提 现</a>
					</div>
				</div>
				<div class="user-safe">
					<div class="safety-info clearfix">
						<h6>安全等级</h6>
						{if $safeLevel eq '5'}
						<div class="safety-level three"></div>
						{else if $safeLevel eq '3' or $safeLevel eq '4'}
						<div class="safety-level two"></div>
						<a href="/safepersonal/safecenter" class="level-up">提升</a>
						{else}
						<div class="safety-level one"></div>
						<a href="/safepersonal/safecenter" class="level-up">提升</a>
						{/if}
					</div>
					{if $safeLevel neq '5'}
					<div class="satety-msg clearfix">
						为了您的账户安全，请提升您的安全等级！
					</div>
					{else}
					<div class="satety-msg clearfix">
						您的账号安全级别为高,你好棒哦,请继续保持！
					</div>
					{/if}
					<div class="bind-info clearfix">
					{if $emailActived eq '1'}
						<span class="mail-lock mail-bind">已绑定</span>
					{else}
						<a href="/safepersonal/bindmail" title="绑定邮箱"><span class="mail-lock mail-unbind">未绑定</span></a>
					{/if}
					{if $result.isBindCard eq '1'}
						<span class="card-lock card-bind">已绑定</span>
					{else}
						<a href="/bindcard" title="绑定银行卡"><span class="card-lock card-unbind">未绑定</span></a>
					{/if}
					{if $withdrawPasswd eq '1'}
						<span class="phone-lock phone-bind">已绑定</span>
					{else}
						<a href="/safepersonal/safecodeset" title="绑定安全信息"><span class="phone-lock phone-unbind">未绑定</span></a>
					{/if}
					</div>
				</div>
			</div>
			<!--user-info-area-end-->
			<h4 class="ct-title played clearfix">
				上次玩过的游戏
			</h4>
			<div class="lottery-info">
				<div class="logo-area">
					<img title="时时彩" src="{$path_img}/images/ucenter/index/logo/logo-ssc-cq.png" />
				</div>
				<div class="detail">
					<p>重庆时时彩 距下一期投注截至：<span class="over-time">{$result.lastGame.nowstoptime}</span></p>
					<p>第{$result.lastGame.lastnumber}期 开奖号码: 
						<!-- {foreach from=$result.lastGame.lastballs item=data} -->
						<i>{$data}</i>
						<!-- {/foreach} -->
					</p>
				</div>
				<a target="_blank" href="{$game_server}/gameBet/cqssc" class="bet-now">立即投注</a>
			</div>
			<div class="trans-history">
				<h6>重庆时时彩最新交易记录</h6>
				<a target="_blank" href="{$game_server}/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7" class="link-more">更多></a>
			</div>
			<table class="history-detail">
				<tbody>
					<tr class="title-qihao">
						<th>方案编号</th>
						<th>期号</th>
						<th>投注金额</th>
						<th>奖金</th>
						<th>投注时间</th>
						<th>操作</th>
					</tr>
					<!-- {foreach from=$result.lastBetList item=data} -->
					<tr>
						<td>{$data.orderCode}</td>
						<td>{$data.webIssueCode}</td>
						<td>￥{$data.totamount}</td>
						<td>{if $data.status eq '2'}<span style="color:red;">￥{$data.totwin}</span>{else}{$data.statusName}{/if}</td>
						<td>{$data.formatSaleTime}</td>
						<td><a target="_blank" href="{$game_server}/gameUserCenter/queryOrderDetailManagement?orderId={$data.orderId}">详情</a></td>
					</tr>
					<!-- {/foreach} -->
				</tbody>
			</table>
			<h4 class="ct-title recommend clearfix">
				热门彩种推荐
			</h4>
			<ul class="lottery-list">
				<li>
					<img title="时时彩" src="{$path_img}/images/ucenter/index/logo/logo-ssc-cq.png" />
					<h6>重庆时时彩</h6>
					<p>官方同步开奖，玩法多样，返奖率高</p>
					<p class="color-red">全天120期，最高奖金18万</p>
					<a target="_blank" href="{$game_server}/gameBet/cqssc" class="bet-now">立即投注</a>
				</li>
				<li>
					<img title="时时彩" src="{$path_img}/images/ucenter/index/logo/logo-ll115.jpg" />
					<h6>宝开11选5</h6>
					<p>独有夜间版，乐利宝贝与您有个约会</p>
					<p class="color-red">12分钟一开奖，全天115期</p>
					<a href="#" class="bet-now">立即投注</a>
				</li>
				<li>
					<img title="时时彩" src="{$path_img}/images/ucenter/index/logo/logo-kl8.jpg" />
					<h6>北京快乐8</h6>
					<p>开奖快中奖概率高，财富快速累积</p>
					<p class="color-red">五分钟一开奖，每天179期</p>
					<a href="#" class="bet-now">立即投注</a>
				</li>
			</ul>
		</div>
		<div class="right-sidebar">
			{if $result.adSpaces[0].isDftUsed eq '1'}
				<div class="ad">
					{if $result.adSpaces[0].dftImg eq ''}
						<img src="{$path_img}/images/ucenter/index/ad.jpg" />
					{else}
						<img src="{$dynamicroot}/{$result.adSpaces[0].dftImg}" />
					{/if}
				</div>
            {else}
	            <div class="index-content-slider">
	            	<input type="hidden" name="urlTarget}" value="{$result.adSpaces[0].urlTarget}"/>
	            	<input type="hidden" name="name" value="{$result.adSpaces[0].name}"/>
	                <div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_top">
	                   
	                </div>
	            </div>
            {/if}
			<!--<div class="ad">
				<img src="{$path_img}/images/ucenter/index/ad.jpg" />
			</div>-->
			<h4 class="announcement">
				网站公告
				<!-- <div class="date">9月3日</div> -->
			</h4>
			<!-- <div class="announcement-detail">
				半夜睡不著觉？又没有屋顶可以唱歌？ 没关系！乐利宝贝会陪著你！
			</div> -->
			<ul class="info-list">
			<!-- {foreach from=$result.lastNotice item=data} -->
				<li>
					<em>·</em>
                    <div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;float:left;">
					<a href="/ad/adNoticeQuery?id={$data.id}" title="{$data.title}" class="info">{$data.title}</a>
                    </div>
					<span class="date">{$data.gmtEffectBegin}</span>
				</li>
			<!-- {/foreach}	 -->
			</ul>
            {if $result.adSpaces[1].isDftUsed eq '1'}
				<div class="ad2">
				{if $result.adSpaces[1].dftImg eq ''}
					<img src="{$path_img}/images/ucenter/index/ad2.jpg" />
				{else}
					<img src="{$dynamicroot}/{$result.adSpaces[1].dftImg}" />
				{/if}
				</div>
            {else}
	            <div class="index-content-sliderCenter">
	            	<input type="hidden" name="urlTarget}" value="{$result.adSpaces[1].urlTarget}" />
	            	<input type="hidden" name="name" value="{$result.adSpaces[1].name}" />
	                <div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_center">
	                   
	                </div>
           		</div>
            {/if}
			<!--<div class="ad2">
				<img src="{$path_img}/images/ucenter/index/ad2.jpg" />
			</div>-->
			<h4 class="new-member">新手指南</h4>
			<ul class="info-list">
			<!-- {foreach from=$result.lastNew item=data} -->
				<li>
					<em>·</em>
					<a target="_blank" href="/help/queryGeneralDetail?helpId={$data.id}" class="info">{$data.title}</a>
				</li>
			<!-- {/foreach} -->
			</ul>
		</div>
		<!-- content end -->
	</div>
</body>
<div class="footer">
	<div class="g_33">
		<span class="copy">©2014-2017宝开彩票 All Rights Reserved</span><a target="_blank" href="/help/goIndex">帮助中心</a> | <a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552"javascript:hj5107.openChat()"configID=2945"javascript:hj5107.openChat()"jid=4611765122"javascript:hj5107.openChat()"skillId=145"javascript:hj5107.openChat()"s=1">在线客服</a> | <a href="javascript:void(0);">游戏介绍</a> | <a href="javascript:void(0);">下载客户端</a> | <a href="javascript:void(0);">翻墙工具</a>
	</div>
</div>
</html>
{literal}
<script>
//全局未读信件读取(三个模块取值)
var noreadmsg2="0";
(function($){
	//读取，修改余额可见状态值
	var cookieAllball=$.cookie("showAllBall");
	if(cookieAllball=="1"){	$('#hiddBall').css("display","inline"); $('#hiddenBall').css("display","none");}
	else{	$('#hiddBall').css("display","none");	$('#hiddenBall').css("display","inline");};
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
	
	new phoenix.Hover({triggers:'#J-msg-panel',panels:'.msg-box',currClass:'msg-trigger',hoverDelayOut:300});
	new phoenix.Hover({triggers:'#J-user-panel',panels:'.menu-nav',currClass:'menu-trigger',hoverDelayOut:300});
	//顶部彩种菜单
		new phoenix.Hover({triggers:'#J-top-game-menu',panels:'#J-top-game-menu .game-menu-panel',hoverDelayOut:300,currClass:'game-menu-current'});
		
	//debugger
	try {
		//自动查询此用户未读信件(四处)
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
				
				}
				else {					
					$("#readmsg").html("暂未收到新消息");
					$('#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
					$('#readmsg').attr("style","text-align:center; color:black;");			
				}
			},			
			complete:function(){   }
		});
	    } catch (err) {		
			alert("网络异常，读取信件信息失败");
	    }
	//页面广告
	new phoenix.GlobalAd({
		width:230,
		height:250,
		url:'/api/jsonp/getAdverts?k=index_pos_top&r='+Math.random(),
		callback:function(){
			var slider = new phoenix.Slider({
				par:$('#J-globalad-index_pos_top'),
				panels:'.slider-pic li',
				triggers:'.slider-num li',
				sliderDirection:'left',
				sliderIsCarousel:true
			});
		}
	});
	
	//页面广告
	new phoenix.GlobalAd({
		width:230,
		height:75,
		url:'/api/jsonp/getAdverts?k=index_pos_center&r='+Math.random(),
		callback:function(){
			var slider = new phoenix.Slider({
				par:$('#J-globalad-index_pos_center'),
				panels:'.slider-pic li',
				triggers:'.slider-num li',
				sliderDirection:'left',
				sliderIsCarousel:true
			});
		}
	});
	
	try {
		//自动查询此用户未读信件(四处)
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/index/getuserbal',				
			data:'',				
			success:function(data){	
				if( data['status']=="ok")
				{
					$("#spanBall").text(data['data']);
					$(".balance em").text(data['data']);
				}
			},			
			complete:function(){   }
		});
    } catch (err) {		
		
    }
	
})(jQuery);

</script>
{/literal}
</body>
</html>
