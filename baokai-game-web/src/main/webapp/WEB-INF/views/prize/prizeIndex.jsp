<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>宝开娱乐-红包天天送</title>
<meta name="keywords" content="宝开娱乐, 宝开平台, 宝开软件, 宝开摇钱树, 宝开摇钱术, 宝开彩票, 生钱有道" />
<meta name="description" content="" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/css/activity/prize/style.css" />
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery-1.9.1.js"></script> --%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery.tmpl.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.base.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Class.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Event.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.util.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Tab.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Hover.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Select.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.cookie.js"></script>
<script type="text/javascript">
		 global_params_notice = "all,ad_top",global_userName='${userId}';
		 global_userID = '${userId}';
		 _logOut = '${userContextPath}';
		 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
</script>
	<jsp:include page="../../layouts/base-script.jsp" flush="true" />	
</head>
<body>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<jsp:include page="../../layouts/header-front-toolbar.jsp" flush="true" />
<%-- 2016.03.14 Tool Bar 整合 End--%>

<div id="top">
	<div id="header" class="wrap">
		<h1 class="seo_txt">宝开娱乐-红包天天送</h1>
		<h2 class="seo_txt">连续投注抽大奖，还有红包每天送</h2>
		<p class="tips">PC端和手机端均可参与</p>
		<p class="seo_txt">活动时间&nbsp;&nbsp;2014.12.01 - 2014.12.31</p>
	</div>
	
	<div id="sign-in" class="wrap">
		<div class="cycle" data-thisday="${ps.thisDay}">
		<input type="hidden"  id="activityStartTime" value="${activityStartTime}"/>
		<input type="hidden"  id="activityendTime" value="${activityEndTime}"/>
			<div class="cycle-box">
				<ul data-cycle-box>
				<c:forEach items="${ps.prizes}" var="prize">
				      <li data-day="${prize.day}">
				      <div class="prize normal-prize">
							<p class="date">${prize.year}年${prize.month}月${prize.day}日</p>
							<p class="desc"><strong>${prize.money}</strong><span>元红包</span></p>
							<c:choose>
							   <c:when test="${prize.status=='accepted'}"><a href="javascript:void(0);" class="button accepted">已领取</a></c:when>
							   <c:when test="${prize.status=='expired'}"><a href="javascript:void(0);"  class="button expired">未获得</a></c:when>
							   <c:when test="${prize.status=='lottery'}"><a href="${currentContextPath}/gameBet/cqssc" target="_blank" class="button lottery">投注领奖</a></c:when>
							   <c:when test="${prize.status=='enabled'}"><div  class="button enabled">点击领取</div></c:when>
							   <c:otherwise>
							   <a class="button comming">未开启</a>
							   </c:otherwise>
							</c:choose>
						</div>
					 </li>
				</c:forEach>
				
				</ul>
			</div>
			<div class="cycle-button" data-cycle-button>
				<span class="prev">prev</span>
				<span class="next">next</span>
			</div>
		</div>
		
		<ol class="rule">
			<li>此活动限元模式投注，任意彩种均可参与（包含手机投注）</li>
			<li>每日投注开奖之后，即可获得领奖资格（双色球、3D彩等跨天开奖彩种，需在开奖之后才可获得投注当日的领奖资格）</li>
			<li>每日每个用户只能领取一次投注红包，领取成功后系统会自动充值至您的账户，红包金额不可提现。</li>
			<li>用户必须在活动结束前领完所有红包，过期后不可再领取。</li>
		</ol>
	</div>

</div>

<div id="content">
	<div class="wrap">
		<div class="lottery-poll">
			<div class="lotterys">
				<div class="ltry ltry1"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_01.jpg" alt=""></div>
				<div class="ltry ltry2"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_02.jpg" alt=""></div>
				<div class="ltry ltry3"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_03.jpg" alt=""></div>
				<div class="ltry ltry4"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_04.jpg" alt=""></div>
				<div class="ltry ltry5"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_05.jpg" alt=""></div>
				<div class="ltry ltry6"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_06.jpg" alt=""></div>
				<div class="ltry ltry7"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_07.jpg" alt=""></div>
				<div class="ltry ltry8"><img src="${staticFileContextPath}/static/images/activity/prize/prize/draw_lottery_08.jpg" alt=""></div>
			</div>
			<a class="lottery-button">点击抽奖</a>
		</div>
		<div class="lottery-board">
			<h3>您还有<strong>${LuckyNumber}</strong>次抽奖机会</h3>
			<p class="desc">只要投注每满7天，即可获得一次抽奖机会，<strong>100%有奖！</strong></p>
			<a href="${currentContextPath}/gameBet/cqssc" class="go-lottery" target="_blank">立刻投注</a>
			<a class="check-result">查看中奖结果</a>
		</div>
		<div class="winner-list">
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
		<ol class="rule">
			<li>每投注满7天可以参加转盘抽奖。抽奖活动中，赠送全部为游戏币。</li>
			<li>获得的抽奖机会可以累计，但必须在活动结束前用完，活动结束后将清零。</li>
			<li>抽中充值50送50、充值100送100、充值300送300、充值1送999奖品的的用户，需要活动结束前一次性充值要求金额。</li>
			<li>充值金额当日到账，赠送金额将在活动结束后分2周赠送，赠送日期为1月15日、1月22日。</li>
			<li>抽中奖品中，抽中礼金金额将在12.22、12.29、1.5、1.12派发，取最临近日期派发。</li>
			<li>活动欢迎真实用户参与，禁止刷号等作弊行为，一旦发现不予派奖。</li>
			<li>宝开游戏平台保留活动最终解释权。</li>
		</ol>
	</div>
</div>

<div id="footer">
	<p class="copyright">© 2014 - 2017  All rights reserved. 宝开游戏平台版权所有 &nbsp;&nbsp;&nbsp;&nbsp;</p>
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
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_01.jpg" alt="prize1">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_02.jpg" alt="prize2">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_03.jpg" alt="prize3">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_04.jpg" alt="prize4">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_05.jpg" alt="prize5">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_06.jpg" alt="prize6">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_07.jpg" alt="prize7">
    		<img src="${staticFileContextPath}/static/images/activity/prize/prize/prize_08.jpg" alt="prize8">
    	</div>
    </div>
    <div class="overlay_footer">
        <button data-action="close" class="overlay_button">确&nbsp;&nbsp;定</button>
    </div>
</div>

<script src="${staticFileContextPath}/static/js/activity/prize/index.js"></script>
</body>
</html>