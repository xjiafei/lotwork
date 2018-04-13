<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title>宝开娱乐 - 羊年活动</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta content="initial-scale=1, maximum-scale=1, minimum-scale=1, minimal-ui" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" href="${staticFileContextPath}/static/css/activity/sheep_year_mobile/style.css" />
<link rel="stylesheet" media="screen" href="${staticFileContextPath}/static/css/activity/sheep_year_mobile/animation.css" />
<link rel="stylesheet" media="screen" href="${staticFileContextPath}/static/css/activity/sheep_year_mobile/hongbao.css" />
</head>
<body>



<div id="game1" class="screen" data-anchor="game1" >
	
	<img src="${staticFileContextPath}/static/css/activity/images/s1_bg.jpg" alt="" />
	<div class="lantern lantern1"></div>
	<div class="lantern lantern2"></div>
	<div class="lantern lantern3"></div>
	<div class="game1-box1 screen-wrap" id="J-reward-panel">
		<h2 class="seo_txt">羊年玩大的！你的红包你做主~</h2>
		<div class="riband"></div>
		
		<!-- 
			.rp-step-1 => 当前红包还不能进行申领（前一个红包还未申领结束）
			.rp-step-2 => 当前红包可申领（点击申领）
			.rp-step-3 => 可提现额度低于100元
			.rp-step-4 => 可提现额度大于100元
			.rp-step-5 => 开启红包任务（倒计时）
			.rp-step-6 => 红包任务完成（点击领取红包）
			.rp-step-7 => 红包任务结束（红包已经领取）
		 -->

		<!-- 当前红包还不能进行申领（前一个红包还未申领结束） -->
		<textarea class="data-save" id="J-step-1">			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;p&gt;领取前一个红包后即可打开哦！&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>

		<!-- 当前红包可申领（点击申领） -->
		<textarea class="data-save" id="J-step-2">			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;div class="rp-button" data-action="apply"&gt;申领红包&lt;/div&gt;
				&lt;/div&gt;
				&lt;div class="rp-c-rule"&gt;
					&lt;p&gt;20天内完成红包金额的30倍的有效投注即可申领&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>

		<!-- 红包任务结束（红包已领取） -->
		<textarea class="data-save" id="J-step-3">	
			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;h3&gt;您已经主动放弃了红包！&lt;/h3&gt;
					&lt;p&gt;放弃是为了更好的开始，再接再励！&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>
        
		<!-- 可提现额度大于100元 -->
		<textarea class="data-save" id="J-step-4">		
			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-input-aera"&gt;
					&lt;h3&gt;输入红包金额&lt;/span&gt;&lt;/h3&gt;
					&lt;div class="rp-c-input-box"&gt;
						&lt;input class="amount" type="text"&gt;
						&lt;button class="submit-amount" data-action="submit-amount"&gt;确&nbsp;定&lt;/button&gt;
					&lt;/div&gt;
					&lt;p class="tips-limit"&gt;
						红包范围：&lt;span class="money-amount"&gt;100-38,888 &lt;/span&gt;元
					&lt;/p&gt; 
					&lt;p&gt;
						20天内完成红包金额的30倍的有效投注即可申领
					&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>

		<!-- 开启红包任务（倒计时） -->
		<textarea class="data-save" id="J-step-5">	
			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;div class="rp-count-down"&gt;
						
					&lt;/div&gt;
					&lt;p class="receive"&gt;领取￥&lt;span class="current-rewards"&gt;{{rewardsNum}}&lt;/span&gt;元红包&lt;/p&gt;
				&lt;/div&gt;
				&lt;div class="rp-progress-bar"&gt;
					&lt;div class="desc"&gt;截止今日零点有效投注：{{lastBet}} 元&lt;/div&gt;
					&lt;div class="rp-progress"&gt;&lt;/div&gt;
					&lt;a href="javascript:void(0);" class="rp-button give-up" data-action="quit"&gt;放弃红包&lt;/a&gt;
				&lt;/div&gt;
				&lt;div class="rp-c-btns"&gt;
					&lt;div class="rp-target-number"&gt;
						&lt;p&gt;目标投注：￥&lt;span class="expect-amount"&gt;{{expected}}&lt;/span&gt;          （红包金额 X 30）&lt;/p&gt;
					&lt;/div&gt;
				&lt;/div&gt;
			&lt;/div&gt;

		</textarea>
		<!-- 红包任务完成（点击领取红包） -->
		<textarea class="data-save" id="J-step-6">			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-btns"&gt;
					&lt;a href="javascript:void(0);" class="rp-button" data-action="get"&gt;领取 &lt;span&gt;{{rewardsNum}}&lt;/span&gt; 元红包&lt;/a&gt;
				&lt;/div&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;h3&gt;恭喜您完成任务啦！&lt;/h3&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>

		<!-- 红包任务结束（红包已领取） -->
		<textarea class="data-save" id="J-step-7">	
			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;h3&gt;红包已经申领成功啦！&lt;/h3&gt;
					&lt;p&gt;红包会在申领成功后三个工作日内派发到账户中&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>
        
        <!-- 红包任务结束（红包已领取） -->
		<textarea class="data-save" id="J-step-10">	
			&lt;div class="rp-cover1"&gt;&lt;/div&gt;
			&lt;div class="rp-cover2"&gt;&lt;/div&gt;
			&lt;div class="rp-content"&gt;
				&lt;div class="rp-c-top"&gt;
					&lt;h3&gt;很遗憾，时间超限！&lt;/h3&gt;
					&lt;p&gt;红包不能再申领了&lt;/p&gt;
				&lt;/div&gt;
			&lt;/div&gt;
		</textarea>
		<div id="J-hongbao" class=""
			data-cycle-slides="> div.item"
			data-cycle-pager="> .cycle-pager-wrap .cycle-pager"
			data-cycle-fx="scrollHorz"
			data-cycle-timeout="4000"
			data-cycle-speed="800"
			data-cycle-paused="true"
			data-cycle-swipe="true"
			data-cycle-swipe-fx="scrollHorz"
			data-pause-on-hover="true">
			<div class="cycle-pager-wrap">
				<div class="cycle-pager"></div>
			</div>
		</div>
	</div>
	<ul class="slider-dot">
		<li class="current"></li>
		<li></li>
		<li></li>
	</ul>
	<div class="game1-box2">
		<div class="screen-wrap">
			<div class="event-info">
				<p>一个红包不够？给你三个！就这么任性！</p>
			</div>
		</div>
	</div>
	<div class="game1-box3">
		<a data-rule="rule1.html" href="javascript:void(0);" class="event-rules" id="rules1">查看活动规则</a>
		<a href="javascript:void(0);" class="event-next" id="next1">进入下一个活动</a>
	</div>
</div>

<div id="game2" class="screen">
	<div class="screen-wrap">
		<div class="slogan">
			<img src="${staticFileContextPath}/static/css/activity/images/s2_slogan.png" alt="" />
		</div>
		<a  href="javascript:void(0);" class="betting-records">我的记录</a>
		<div class="dice-status">
			<div class="dice-status-1">
				<p class="status-times"><span data-amount="dice">${diceLastNum}</span>次</p>
				
				
			</div>
			<div class="dice-status-2">
				<p class="status-times"><span id="dice-win-continus">${diceContinueNum}</span>次</p>
			</div>
		</div>
		
		<div class="dice-bell" id="dice-table">
			<div id="dice-ball1" class="dice-ball dice-num-1"></div>
			<div id="dice-ball2" class="dice-ball dice-num-3"></div>
			<div id="dice-ball3" class="dice-ball dice-num-5"></div>
		</div>
		
		
		<div class="dice-desktop">
			<a href="javascript:void(0);" class="dice-button dice-button-big">
				<span>押大</span>
				<p>11-18</p>
			</a>
			<a href="javascript:void(0);" class="dice-button dice-button-small">
				<span>押小</span>
				<p>3-10</p>
			</a>
			
		</div>
		<div class="dice-desktop-info">
				<p>活动时间：2015年2月5 00:00:00 至 2015年3月15 23:59:59</p>
			</div>
		<div class="game2-box3">
			<a data-rule="rule1.html" href="javascript:void(0);" class="event-rules" id="rules2">查看活动规则</a>
			<a href="javascript:void(0);" class="event-next" id="next2">进入下一个活动（VIP专享）</a>
		</div>
	</div>
</div>

<div id="game3" class="screen">
	<div class="screen-wrap">
		<div class="rotary-table">
			<div class="rotary-pointer"></div>
			<div class="rotary-rotary">
				<img id="rotary" src="${staticFileContextPath}/static/css/activity/images/s3_turnplate.png">
			</div>
		</div>
		<div class="rotary-desktop">
			<a href="javascript:void(0);" class="rotary-button">抽奖</a>
		</div>
		
		<div class="rotary-status-history">
			<div class="rotary-status">
				<p>您还可以抽奖</p>
				<p class="status-times">
					<span data-amount="rotary">${rotaryLastNum}</span>次
				</p>
				<div class="status-links">
					<a data-modal="result" href="javascript:void(0);">我的中奖结果</a>
				</div>
				<!-- <div class="not-vip-status">
					<p>本活动仅限VIP会员参加，成为VIP会员可享受更多特权，<a href="" target="_blank">查看更多VIP特权</a></p>
				</div> -->
			</div>
			<div class="rotary-history">
				<h3>中奖名单</h3>
				<div id="rh-cycle" class="rh-cycle cycle-slideshow"
					data-cycle-slides="> div"
					data-cycle-fx=carousel
					data-cycle-timeout=2000
					data-cycle-carousel-visible=4
					data-cycle-carousel-vertical=true
					data-cycle-easing="linear"
				>	
				<c:if test="${empty allRotary}">
				      <span >快来抽取第一份幸运！</span>
				</c:if>
					<c:forEach items="${allRotary}" var="list">
			    	<div class="rh-list">
			    	<span class="rh-name">${list.userName}</span>
			    	<span class="rh-desc">${list.desc}</span>
			    	<span class="rh-date">${list.date}</span>
			    </div>
			  </c:forEach>
				
				</div>
			</div>
		</div>
		<div class="event-info">
			<p>活动时间：2015年2月19日00:00:00 至 2015年2月25日 23:59:59</p>
		</div>
		<div class="game3-box3">
			<a data-rule="rule1.html" href="javascript:void(0);" class="event-rules" id="rules3">查看活动规则</a>
			<a href="javascript:void(0);" class="event-next" id="next3">回到顶部</a>
		</div>

	</div>
</div>


<div id="rule-modal" class="pop-modal">
	<div class="modal-head">
		<h2>你的红包你做主</h2>
		<span class="modal-close" data-modal="close">&times;</span>
	</div>
	<div class="modal-body">
		<ul class="rule-detail">
	<li>用户从填写报名金额后（一旦填写不可修改），开始累计投注额，新旧平台投注额均计入统计,20天内但不超过活动结束时间，有效投注金额达到30倍后即可获得红包申领资格，红包将派发到新平台账户中（有效投注指已开奖投注）；</li>
	<li>活动参与时间是从2015年2月5日00:00:00开始，2015年3月15日23:59:59结束；</li>
	<li>全部彩种均可参与。PC端和移动端有效投注均记录在内；</li>
	<li>用户报名金额最低100元，最高为当前可提现额度，普通用户上限28888元，VIP用户上限38888元；</li>
	<li>用户最多可申领三个红包，需要获得上次红包申领资格，并在页面点击申领或前次红包失效后，才能再次参与；</li>
	<li>用户点击放弃红包，该红包领取资格作废，可申领下一个红包，重新统计投注金额；</li>
    <li>用户有效投注金额每天更新一次，当天有效投注金额需隔天查看；</li>
	<li>VIP身份判定以填写红包金额时是否为VIP为准；</li>
	<li>红包申领的最后期限是2015年3月19日23:59:59前，在此时间之前未成功申领的用户视为自动放弃红包。红包将在申领后三个工作日内派发至用户新平台账户。</li>
		</ul>
	</div>
</div>

<div id="dice-modal" class="pop-modal dice-win">
	<span class="modal-close" data-modal="close">&times;</span>
	<ul class="dm-result">
		<li></li>
		<li></li>
		<li></li>
	</ul>
	<div class="modal-body">
		<p class="dm-title">连胜8次,赌神非你莫属！</p>
		<p class="dm-desc">获得赌神奖励￥200奖金</p>
	</div>
</div>

<div id="rotary-modal" class="pop-modal">
	<span class="modal-close" data-modal="close">&times;</span>
	<div class="modal-body">
		<p class="dm-desc">50元</p>
	</div>
</div>
<div id="J-messages-giveup" class="msg-giveup">
	<p>
		请确认是否要放弃红包，
		<span>放弃后该红包领取资格作废</span>
		，可申领下一个红包。
	</p>
	<div style="text-align:center">
	<a href="javascript:void(0);" class="cancel">取消</a>
	<a href="javascript:void(0);" class="enter">确认</a>
	<a href="javascript:void(0);" class="close"></a>
	</div>
</div>
<div id="game1-event-rules" class="pop">
	<h3>你的红包你做主</h3>
	<ol>
	<li>1.用户从填写报名金额后（一旦填写不可修改），开始累计投注额，新旧平台投注额均计入统计,20天内但不超过活动结束时间，有效投注金额达到30倍后即可获得红包申领资格，红包将派发到新平台账户中（有效投注指已开奖投注）；</li>
	<li>2.活动参与时间是从2015年2月5日00:00:00开始，2015年3月15日23:59:59结束；</li>
	<li>3.全部彩种均可参与。PC端和移动端有效投注均记录在内；</li>
	<li>4.用户报名金额最低100元，最高为当前可提现额度，普通用户上限28888元，VIP用户上限38888元；</li>
	<li>5.用户最多可申领三个红包，需要获得上次红包申领资格，并在页面点击申领或前次红包失效后，才能再次参与；</li>
	<li>6.用户点击放弃红包，该红包领取资格作废，可申领下一个红包，重新统计投注金额；</li>
    <li>7.用户有效投注金额每天更新一次，当天有效投注金额需隔天查看；</li>
	<li>8.VIP身份判定以填写红包金额时是否为VIP为准；</li>
	<li>9.红包申领的最后期限是2015年3月19日23:59:59前，在此时间之前未成功申领的用户视为自动放弃红包。红包将在申领后三个工作日内派发至用户新平台账户。</li>
	</ol>
	<a href="javascript:;" class="cancel">关闭</a>
</div>

<div id="game2-event-rules" class="pop">
	<h3>喜气羊羊大小通吃</h3>
	<ol>
	<li>1.单笔充值每达到500元，即可获得一次骰子押大小机会，押中得5元，押不中得1元，每连续压中8次可再得200元；</li>
	<li>2.活动参与时间是从2015年2月5日00:00:00开始，2015年3月15日23:59:59结束；</li>
	<li>3.在活动时间范围内充值的，且充值金额除以500的整数值即为抽奖次数，单笔不满500部分不累计；</li>
	<li>4.投骰子押大小将在2015年3月19日23:59:59截止，超过此日期未押的用户将视为自动弃奖品；</li>
	<li>5.抽奖获得的奖金将在三个工作日内派发至用户新平台账户。</li>
	</ol>
	<a href="javascript:;" class="cancel">关闭</a>
</div>

<div id="game3-event-rules" class="pop">
	<h3>三羊开泰转运盘</h3>
	<ol>
	<li>1.活动仅限VIP参与，单笔充值每达到20000元，获得三羊开泰大宝箱抽奖资格，喜气羊羊大小通吃可以兼得；</li>
	<li>2.活动参与时间是从2015年2月19日00:00:00开始，2015年2月25日23:59:59结束；</li>
	<li>3.在活动时间范围内充值的，且充值金额除以20000的整数值即为抽奖次数，单笔不满20000部分不累计；</li>
	<li>4.抽奖将在2015年2月28日23:59:59截止，超过此日期未抽奖的用户将视为自动放弃奖品；</li>
	<li>5.抽奖获得的奖金将在三个工作日内派发至用户新平台账户。</li>
	</ol>
	<a href="javascript:;" class="cancel">关闭</a>
</div>


<div id="J-messages-ensure" class="msg-giveup msg-ensure">
	<p>
		红包金额一经确认不可更改，在20天内完成红包金额的30倍的有效投注即可申领，请确认是否提交？
	</p>
	<div style="text-align:center">
	<a href="javascript:;" class="cancel">取消</a>
	<a href="javascript:;" class="enter">确认</a>
	<a href="javascript:;" class="close"></a>
	</div>
</div>
<div id="J-messages-timeout" class="pop-modal ac-timeout">
	<span class="modal-close" data-modal="close">&times;</span>
	<p>
		<span class="face"></span> <span class="desc">活动已经结束</span>
	</p>
</div>
<div id="J-rules-tips" class="rules-tips pop-modal">
	<a href="javascript:;" class="close" data-modal="close"></a>
	<div class="info-content"></div>
</div>
<div id="overlay-shadow"></div>

<div class="dice-history">
		<a href="javascript:;" class="close" data-modal="close"></a>
			<table id="dice-history">
				<thead>
					<tr>
						<th class="col-white">我的记录</th>
						<th>押大小结果</th>
						<th>押大小内容</th>
						<th>奖金</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty diceDetail}">
				      <tr><td colspan="4">押了才有结果哦！<td></tr>
				</c:if>
					<c:forEach items="${diceDetail}" var="detail" varStatus="status">
				    <c:if test="${status.index<5}">
					<tr>
						<td class="col-white">${detail.time}</td>
						<td>
							<div class="dice-result-icon">
							    <c:forEach items="${detail.result}" var="re">
								<span class="dice-${re}">${re}</span>
								</c:forEach>
							</div>
						</td>
						<td>${detail.type}</td>
						<td>￥${detail.winMoney}</td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</div>

<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/jquery-1.9.1.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/jquery.cycle2.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/jquery.cycle2.carousel.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/jquery.inview.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/jQueryRotate.2.2.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/index.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/game2.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/game3.js"></script>
<script src="${staticFileContextPath}/static/js/activity/sheep_year_mobile/hongbao.js"></script>
<script type="text/javascript">
$("#next1").click(function(){
	var h1 = $("#game1").height();
	$('body,html').animate({scrollTop:h1},1000);
});
$("#next2").click(function(){
	var h2 = $("#game2").height();
	$('body,html').animate({scrollTop:h2*2},1000);
});
$("#next3").click(function(){
	$('body,html').animate({scrollTop:0},1000);
});

$("#rules1").click(function(){
	$('#game1-event-rules').show();
});
$("#rules2").click(function(){
	$('#game2-event-rules').show();
});
$("#rules3").click(function(){
	$('#game3-event-rules').show();
});

$(".pop .cancel").click(function(){
	$('.pop').hide();
});

$(".betting-records").click(function(){
	$('.dice-history').show();
	$('#overlay-shadow').show();
	
});
$(".dice-history .close").click(function(){
	$(".dice-history").hide();
	$('#overlay-shadow').hide();
});


</script>

</body>
</html>