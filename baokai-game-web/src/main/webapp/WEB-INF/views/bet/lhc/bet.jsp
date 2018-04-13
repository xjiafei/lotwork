<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>六合彩</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
  	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/lhc/game.css" />
</head>
<body>
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="六合彩" href="#">六合彩</a></h1>
			<div class="deadline">
				<div class="deadline-text">第<strong id="J-lottery-info-number">16/024</strong>期<br/><span id="J-lottery-info-day">2016/2/27</span>开奖<br/>离投注截止还有</div>
				<div class="deadline-number">
					<em class="hour-left">0</em>
					<em class="hour-right">0</em>
					<span>:</span>
					<em class="min-left">0</em>
					<em class="min-right">0</em>
					<span>:</span>
					<em class="sec-left">0</em>
					<em class="sec-right">0</em>
					<!--<strong>预售中</strong>-->
				</div>
			</div>
			<div class="winning-user">
				<ul id="J-winningList">
				</ul>
			</div>
			<div class="lottery">
				<div class="lottery-text">第<strong id="J-lottery-info-lastnumber">16/023</strong>期<br />开奖号码&gt;</div>
				<div class="lottery-number" id="J-lottery-info-balls">
					<em>0</em>
					<em>0</em>
					<em>0</em>
					<em>0</em>
					<em>0</em>
					<em>0</em>
					<span class="line"></span>
					<em>0</em>
				</div>
				<div class="lottery-link">
					<a href="${userContextPath}/help/queryLotteryDetail?helpId=1343&cate2Name=${lotteryName}" target="_blank" class="info">游戏说明</a>
				</div>
			</div>
		</div>
	</div>
	<!-- header end -->

	<div class="g_33 main">
		<div class="game-type" id="J-gameType-panel">
			<!--<div class="game-type-box">-->
				<!--<dl>-->
					<!--<dt>特码</dt>-->
					<!--<dd class="active">直选</dd>-->
					<!--<dd>趣味</dd>-->
				<!--</dl>-->
			<!--</div>-->
			<span class="game-construction">玩法持续增加中</span>
		</div>
		<div class="game-result">
			<div class="game-history">
				<p class="history-title">
					<span>期号</span>
					<span>投注金额</span>
					<span>状态</span>
				</p>
				<ul id="J-gameHistory">
				<c:if test="${empty orders}">
					<li style="cursor: default;text-align:center;">您最近7天暂时没有投注记录！</li>
			    </c:if>
			    <c:if test="${!empty orders}">
					<c:forEach items="${orders}" var="orderStruc" varStatus="status">
						<li>
							<a href="/gameUserCenter/queryOrderDetail?orderId=${orderStruc.orderId}" target="_black">
								<span>${orderStruc.webIssueCode}</span>
								<span class="yellow"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>
						  		<c:choose>
						  			<c:when test="${orderStruc.status == 1}"><span>等待开奖</span></c:when>
                                 	<c:when test="${orderStruc.status == 2}"><span class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></c:when>
									<c:when test="${orderStruc.status == 3}"><span>未中奖</span></c:when>
									<c:when test="${orderStruc.status == 4}"><span>已撤销</span></c:when>
									<c:when test="${orderStruc.status == 5}"><span>处理中</span></c:when>
									<c:when test="${orderStruc.status == 7}"><span>存在异常</span></c:when>
								</c:choose>
							</a>
						</li>
					</c:forEach>
				</c:if>
				</ul>
				<p class="text-right"><a class="link-more" href="/gameUserCenter/queryOrdersEnter?time=7" target="_blank">查看更多投注方案>></a></p>
			</div>
			<div class="game-play" id="J-gameOrder-list">
				<div class="play-list">
					<p class="play-title">
						<span class="name">玩法名称</span><span>内容</span><span>赔率</span><span>注数</span><span>金额</span></p>
					<ul id="J-balls-order-container">
						<!--<li>-->
							<!--<span class="play-type">[特码_直选]</span>-->
							<!--<span class="play-content">01</span>-->
							<!--<span class="play-odds">40.0</span>-->
							<!--<span class="play-text"><input type="text"></span>-->
							<!--<span class="close"></span>-->
						<!--</li>-->
					</ul>
					<div class="list-wave"></div>

				</div>
				<p class="list-control">
					<label>统一输入金额：<input data-param="action=setAmount&id=all" type="text" id="allAmount"></label>
					<button data-param="action=clear">清空</button>
				</p>
				<div id="J-gameOrder-amout-tool" class="amount_tool">
					<span class="arrow"></span>
					<a data-param="action=addMoney&val=1" href="javascript:;">+1</a>
					<a data-param="action=addMoney&val=10" href="javascript:;">+10</a>
					<a data-param="action=addMoney&val=100" href="javascript:;">+100</a>
					<a data-param="action=addMoney&val=500" href="javascript:;">+500</a>
					<a data-param="action=addMoney&val=0" href="javascript:;">清零</a>
				</div>
			</div>
			<div class="game-confirm">
				<p>你选择了<strong id="J-gameOrder-lotterys-num">0</strong>注，投注金额 <strong>￥<span id="J-gameOrder-amount">0</span></strong>
					元</p>
				<p><input id="J-submit-order" class="submit" type="button"></p>
			</div>
		</div>
		<div class="game-select">
			<div class="game-box" id="J-game-box">

				<!--<div class="game-odds">-->
					<!--<label class="odds-hide"><span>+</span>赔率选择：</label>-->
					<!--<ol>-->
						<!--<li class="active">40</li>-->
						<!--<li>41</li>-->
						<!--<li>42</li>-->
						<!--<li>43</li>-->
					<!--</ol>-->
				<!--</div>-->
				<!--特码直选-->
				<!--<div class="ball-list ball-direct">-->
					<!--<ul class="ball-list-ul">-->
						<!--<li>-->
							<!--<span class="ball red">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="ball blue">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li class="active">-->
							<!--<span class="ball green">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="ball">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
					<!--</ul>-->
				<!--</div>-->
				<!--<div class="select-tool">-->
					<!--<ul class="tool-animal">-->
						<!--<li class="active">鼠</li>-->
						<!--<li>牛</li>-->
						<!--<li>虎</li>-->
						<!--<li>兔</li>-->
						<!--<li>龙</li>-->
						<!--<li>蛇</li>-->
						<!--<li>马</li>-->
						<!--<li>羊</li>-->
						<!--<li>猴</li>-->
						<!--<li>狗</li>-->
						<!--<li>猪</li>-->
					<!--</ul>-->
					<!--<ul class="tool-size">-->
						<!--<li>大</li>-->
						<!--<li>小</li>-->
						<!--<li>单</li>-->
						<!--<li>双</li>-->
					<!--</ul>-->
					<!--<ul class="tool-color">-->
						<!--<li>红波</li>-->
						<!--<li>绿波</li>-->
						<!--<li>蓝波</li>-->
					<!--</ul>-->
					<!--<div class="tool-bits">-->
						<!--<span class="tool-title">尾：</span>-->
						<!--<ul>-->
							<!--<li>0</li>-->
							<!--<li>1</li>-->
							<!--<li>2</li>-->
							<!--<li>3</li>-->
							<!--<li>4</li>-->
							<!--<li>5</li>-->
							<!--<li>6</li>-->
							<!--<li>7</li>-->
							<!--<li>8</li>-->
							<!--<li>9</li>-->
						<!--</ul>-->
					<!--</div>-->
					<!--<div class="tool-ten">-->
						<!--<span class="tool-title">头：</span>-->
						<!--<ul>-->
							<!--<li>0</li>-->
							<!--<li>1</li>-->
							<!--<li>2</li>-->
							<!--<li>3</li>-->
							<!--<li>4</li>-->
						<!--</ul>-->
					<!--</div>-->
					<!--<div class="tool-keyboard">-->
						<!--<input placeholder="键盘输入号码" type="text">-->
					<!--</div>-->
				<!--</div>-->

				<!--特码生肖-->
				<!--<div class="ball-list shengxiao">-->
					<!--<h3 class="game-title">生肖</h3>-->
					<!--<ul class="ball-list-ul">-->
						<!--<li>-->
							<!--<span class="balls">猴</span>-->
							<!--<span class="ball-group">01</span>-->
							<!--<span class="ball-group">01</span>-->
							<!--<span class="ball-group">01</span>-->
							<!--<span class="ball-group">01</span>-->
							<!--<span class="ball-group">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li class="active">-->
							<!--<span class="balls">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">01</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
					<!--</ul>-->

				<!--</div>-->

				<!--特码两面-->
				<!--<div class="ball-list ball-double">-->
					<!--<h3 class="game-title">两面</h3>-->
					<!--<ul class="ball-list-ul">-->
						<!--<li class="active">-->
							<!--<span class="balls">大</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">小</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">和大</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">和小</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">单</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">双</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">和大</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="balls">小</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
					<!--</ul>-->
				<!--</div>-->

				<!--特码色波-->
				<!--<div class="ball-list ball-color">-->
					<!--<h3 class="game-title">色波</h3>-->
					<!--<ul class="ball-list-ul">-->
						<!--<li>-->
							<!--<span class="ball red">红</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li class="active">-->
							<!--<span class="ball green">绿</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
						<!--<li>-->
							<!--<span class="ball blue">蓝</span>-->
							<!--<span>x</span>-->
							<!--<span class="ball-odds">40</span>-->
						<!--</li>-->
					<!--</ul>-->
				<!--</div>-->
			</div>
			</div>
		</div>

	</div>
	<input type ="hidden" id="userContextPath" value ="${userContextPath}" />

<!-- 底部开始 -->
<div class="foot  g_33 ">
		资金安全建议：为了您的资金安全，建议定期更换您的安全密码。<br />浏览器建议：首选为IE9或IE10浏览器，其次为火狐和Google浏览器<br />分辨率建议：使用1024×768以上的分辨率。
</div>
<!-- 底部结束 -->

<!-- 切换玩法loading开始 -->
<div class="bet-common-loading" id="J-bet-loading-panel" style="display: none;"></div>
<!-- 切换玩法loading结束 -->



<!-- 工具类 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/lhc.js" ></script>

<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js" ></script>

<!-- Games命名空间 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.Games.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameStatistics.js" ></script>
<!-- 游戏父类 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.Game.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameMethod.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameMessage.js" ></script>

<!-- 实例类 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.Games.LHC.js" ></script>
<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.Games.LHC.Config.js" ></script> --%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.Games.LHC.Message.js" ></script>
<!-- 单例类 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameTypes.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameOrder.js" ></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/phoenix.GameSubmit.js" ></script>
<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/game/lhc/game-lhc-sub.js" ></script> --%>


<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>


</body>
</html>


