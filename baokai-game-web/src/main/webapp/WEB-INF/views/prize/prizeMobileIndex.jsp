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
<title>宝开娱乐-红包天天送</title>
<meta name="keywords" content="宝开娱乐, 宝开平台, 宝开软件, 宝开摇钱树, 宝开摇钱术, 宝开彩票, 生钱有道" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0">
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/css/activity/mobilePrize/style.css" />
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery.tmpl.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.base.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Class.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Event.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.util.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Tab.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Hover.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Select.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.cookie.js"></script>
</head>
<body>
<div id="header" class="wrap">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/top.jpg" class="space-img" alt="">
	<h1 class="seo_txt">宝开娱乐-红包天天送</h1>
	<h2 class="seo_txt">连续投注抽大奖，还有红包每天送</h2>
	<p class="seo_txt">PC端和手机端均可参与</p>
	<p class="seo_txt">活动时间&nbsp;&nbsp;2014.12.01 - 2014.12.31</p>
</div>	
	
<div id="sign-in" class="wrap">
		<input type="hidden"  id="activityStartTime" value="${activityStartTime}"/>
		<input type="hidden"  id="activityendTime" value="${activityEndTime}"/>
	<div class="cycle">
		<ul class="cycle-slideshow"
			data-cycle-slides="> li"
			data-cycle-swipe="true"
			data-cycle-fx="scrollHorz"
			data-cycle-prev=".cycle-button .prev"
			data-cycle-next=".cycle-button .next"
			data-cycle-timeout="4000"
			data-cycle-loader="wait"
			data-cycle-speed="800"
            data-cycle-starting-slide="${ps.thisDay}"
		>
			<c:forEach items="${ps.prizes}" var="prize">
				<li data-day="${prize.day}">
				<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize_bg_${prize.status}.png" alt="">
				<div class="prize">
					<p class="date">${prize.year}年${prize.month}月${prize.day}日</p>
					<p class="desc"><strong>${prize.money}</strong><span>元红包</span></p>
					<c:choose>
					   <c:when test="${prize.status=='lottery'}">
					   <a class="button lottery" href="${currentContextPath}/gameBet/cqssc" target="_blank">投注领奖</a>
					   </c:when>
					    <c:otherwise>
					    	<a class="button ${prize.status}">
					    	<c:choose>
							   <c:when test="${prize.status=='accepted'}">已领取</c:when>
							   <c:when test="${prize.status=='expired'}">未获得</c:when>
							   <c:when test="${prize.status=='enabled'}">点击领取</c:when>
							   <c:otherwise>
									   未开启
							   </c:otherwise>
							</c:choose>
					    	</a>
					    </c:otherwise>
					</c:choose>
				</div>
			</li>
			</c:forEach>
		</ul>
		<div class="cycle-button">
			<span class="prev"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prev.png" alt="prev"></span>
			<span class="next"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/next.png" alt="next"></span>
		</div>
	</div>
</div>

<div class="wrap rule" id="rule1">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/rule_1.png" class="space-img" alt="">
	<ol>
		<li>每日投注任意彩种（包含手机投注），凡有效投注即可领取当日签到奖金。有效投注指已开奖投注。</li>
		<li>每日每个用户只能领取一次，领取成功后系统会立即充值至您的账户，红包金额不可提现。</li>
		<li>用户必须在活动结束前领完所有红包，过期后不可再领取。</li>
	</ol>
</div>

<div class="wrap">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/draw_bg.png" class="space-img" alt="">	
</div>

<div class="wrap lottery-poll">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/ltry_space.png" class="space-img" alt="">
	<div class="lotterys">
		<div class="ltry ltry1"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_01.jpg" alt=""></div>
		<div class="ltry ltry2"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_02.jpg" alt=""></div>
		<div class="ltry ltry3"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_03.jpg" alt=""></div>
		<div class="ltry ltry4"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_04.jpg" alt=""></div>
		<div class="ltry ltry5"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_05.jpg" alt=""></div>
		<div class="ltry ltry6"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_06.jpg" alt=""></div>
		<div class="ltry ltry7"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_07.jpg" alt=""></div>
		<div class="ltry ltry8"><img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/draw_lottery_08.jpg" alt=""></div>
	</div>
	<a class="lottery-button"><span>点击抽奖</span></a>
</div>

<div class="wrap lottery-board">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/bet_1_bg.png" class="space-img" alt="">
	<div>
		<h3>您还有<strong>${LuckyNumber}</strong>次抽奖机会</h3>
		<p class="desc">只要投注每满7天，即可获得一次抽奖机会，<strong>100%有奖！</strong></p>
		<!-- <a href="" class="go-lottery" target="_blank"><span>立刻投注</span></a> -->
		<a class="check-result">查看中奖结果</a>
	</div>
</div>

<div class="wrap winner-list">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/bet_2_bg.png" class="space-img" alt="">
	<ul>
		<c:if test="${! empty luckyList}">
			    <c:forEach items="${luckyList}" var="list">
			    <li>
					<span class="name">${list.username}</span>
					<span>${list.desc}</span>
				</li>
			    </c:forEach>
			</c:if>
	</ul>
</div>

<div class="wrap rule" id="rule2">
	<img src="${staticFileContextPath}/static/images/activity/mobilePrize/rule_2.png" class="space-img" alt="">
	<ol>
		<li>每日(0:00～23:59)投注得2元，每连续投注满7天可以参加转盘抽奖。</li>
		<li>获得的抽奖机会可以累计，但必须在活动结束前用完，活动结束后将清零。</li>
		<li>抽奖活动中，赠送全部为游戏币，抽中充值50送50、充值100送100、充值300送300、充值1送999奖品的用户需要在抽中之后3个工作日内充值,赠送金额将分4周派发。</li>
		<li>抽奖活动中，抽中礼金金额在抽取后3个工作日内派发。</li>
	</ol>
</div>

<div id="footer">
	<p class="copyright">© 2014 - 2017  All rights reserved. 宝开游戏平台版权所有</p>
</div>


<div id="overlay_shadow">&nbsp;</div>

<div id="myprize_layer" class="overlay_layer">
	<div class="overlay_title">
		<h2>中奖结果统计</h2>
		<span data-action="close" class="overlay_close">&times;</span>
	</div>
	<div class="overlay_body">
		<div class="result-table">
			<table>
				<thead>
				<tr>
					<th>日期</th>
					<th>获得奖品</th>
				</tr>
				</thead>
				<tbody>
			<c:forEach items="${myLuckyList}" var="myLucky">
    			<tr>
    				<td>${myLucky.date}</td>
    				<td>${myLucky.awardName}</td>
    			</tr>
    			</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="overlay_footer">
		<button data-action="close" class="overlay_button">确&nbsp;&nbsp;定</button>
	</div>
</div>

<div id="winner_layer" class="overlay_layer" style="display:none">
	<div class="overlay_title">
		<h2>恭喜您中奖</h2>
		<span data-action="close" class="overlay_close">&times;</span>
	</div>
	<div class="overlay_body">
		<div class="prizes">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_01.jpg" alt="prize1">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_02.jpg" alt="prize2">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_03.jpg" alt="prize3">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_04.jpg" alt="prize4">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_05.jpg" alt="prize5">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_06.jpg" alt="prize6">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_07.jpg" alt="prize7">
			<img src="${staticFileContextPath}/static/images/activity/mobilePrize/prize/prize_08.jpg" alt="prize8">
		</div>
	</div>
	<div class="overlay_footer">
		<button data-action="close" class="overlay_button">确&nbsp;&nbsp;定</button>
	</div>
</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/mobilePrize/index.js"></script>
<script src="${staticFileContextPath}/static/js/activity/mobilePrize/jquery.cycle2.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/mobilePrize/jquery.cycle2.swipe.min.js"></script>
</body>
</html>