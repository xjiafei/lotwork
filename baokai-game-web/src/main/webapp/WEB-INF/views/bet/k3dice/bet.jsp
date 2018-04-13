<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
<title>${lotteryName}</title>


<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" /><%--  限用base.css --%>
<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/game.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />


<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/util/phoenix.Countdown.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.Timer.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/util/phoenix.MiniWindow.js"></script>
<!-- Games命名空间 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.Games.js"></script>
<!-- 游戏父类 -->
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.Game.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.GameMethod.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.GameMessage.js"></script>


<!-- game -->
<!-- 由于加载顺序问题  这个js放在此处-->
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/phoenix.GameTypes.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/phoenix.GameStatistics.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/phoenix.GameOrder.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/phoenix.GameTrace.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/game/phoenix.GameSubmit.js"></script>





<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/k3-dice/jquery.jscrollpane.css">
<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/k3-dice/k3-dice.css">
<script src="${staticFileContextPath}/static/js/game/k3-dice/jquery-migrate-1.1.0.min.js"></script>
<%-- <script src="${staticFileContextPath}/static/js/game/k3-dice/k3-config.js"></script> --%>
<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>
<!-- <script src="../js/game/phoenix.GameMessage.js"></script> -->
<script src="${staticFileContextPath}/static/js/game/k3-dice/k3-GameMessage.js"></script>




</head>
<body>


<div class="main">

	<div class="dice-wrap">
		
		<div class="dice-panel">
			<div class="logo">江苏骰宝</div>
			<div class="shortcuts">
				<a href="/gameBet/jsk3">切换到基础版</a>
			</div>
			<div class="lottery-info-number">
				<span id="J-lottery-info-number">-</span>期
			</div>
			<div class="lottery-status">
				<div class="soldout">停售中</div>
				<div class="lottery-countdown" id="J-lottery-countdown">
					<span data-time="m1" class="time-0"></span>
					<span data-time="m2" class="time-0"></span>
					<span class="time-colon"></span>
					<span data-time="s1" class="time-0"></span>
					<span data-time="s2" class="time-0"></span>
					<strong style="display:none">预售中</strong>
				</div>
			</div>
			<div class="lottery-number" id="J-lottery-info-balls">
				<span></span>
				<span></span>
				<span></span>
			</div>
			<div class="lottery-records">
				<div class="record-top">
					<a href="/game/chart/jsdice/chart" target="_blank" class="chart">查看更多</a>
					<strong>开奖记录</strong>
				</div>
				<div class="record-content scroll-pane">
					<ul id="J-lottery-records">
					
					<c:if test="${!empty results}">
					<c:forEach items="${results}" var="order" varStatus="status">
					<li>
					<c:if test="${!empty order.numberRecordList }">
						<div class="rec1">
								<span class="dice-number dice-number-${order.numberRecordList[0]}">${order.numberRecordList[0]}</span>
								<span class="dice-number dice-number-${order.numberRecordList[1]}">${order.numberRecordList[1]}</span>
								<span class="dice-number dice-number-${order.numberRecordList[2]}">${order.numberRecordList[2]}</span>
						</div>
						<div class="rec2">${order.showList[0]}</div>
						<div class="rec3">${order.showList[1]}</div>
						<div class="rec4">${order.showList[2]}</div>
						<div class="rec5">${order.webIssueCode}期</div>
					</c:if>
					</li>
					</c:forEach>
					</c:if>
					</ul>
				</div>
			</div>
		</div>

		<div class="lottery-notice">
		</div>

		<div id="J-dice-sheet" class="dice-table"></div>

		<div class="dice-recycle"></div>

	</div>
	
</div>

<div class="dice-ctrl">
	<div class="dice-bar-wrap">
		<div id="J-dice-bar" class="dice-bar"></div>
	</div>
	<div class="game-history-wrap">
		<div class="game-history">
			<div class="history-top">
				<a href="${currentContextPath}/gameUserCenter/queryOrdersEnter?time=7" class="history-more">查看更多</a>
				<span class="history-close">&times;</span>				
			</div>
			<div class="history-content">
				<ul class="program-chase-list program-chase-list-header">
					<li>
						<div class="cell1">方案编号</div>
						<div class="cell2">期号</div>
						<div class="cell3">投单时间</div>
						<div class="cell4">投注金额（元）</div>
						<div class="cell5">开奖号码</div>
						<div class="cell6">中奖状态</div>
						<div class="cell7">投注内容</div>
					</li>
				</ul>
				<ul class="program-chase-list program-chase-list-body" data-simulation="gameHistory">
				 		<c:if test="${empty orders}">
									<td colspan="7">您最近7天暂时没有投注记录！</td>
					    </c:if>
					     <c:if test="${!empty orders}">
					     	<c:forEach items="${orders}" var="orderStruc" varStatus="status">
					     	<li>
								<div class="cell1" data-history-project="${orderStruc.orderCode}">${orderStruc.orderCode}</div>
								<div class="cell2">${orderStruc.webIssueCode}</div>
								<div class="cell3">${orderStruc.formatSaleTime}</div>
								<div class="cell4"><dfn>¥</dfn><fmt:formatNumber value="${orderStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></div>
								<div class="cell5" data-history-balls>
								<c:if test="${!empty orderStruc.numberRecordList}">
								<span class="dice-number dice-number-${orderStruc.numberRecordList[0]}">${orderStruc.numberRecordList[0]}</span>
								<span class="dice-number dice-number-${orderStruc.numberRecordList[1]}">${orderStruc.numberRecordList[1]}</span>
								<span class="dice-number dice-number-${orderStruc.numberRecordList[2]}">${orderStruc.numberRecordList[2]}</span>
								</c:if>
								<c:if test="${empty orderStruc.numberRecordList}">
								<span>-,&nbsp;</span>
								<span>-,&nbsp;</span>
								<span>-</span>
								</c:if>
								</div>
								<div class="cell6" data-history-result> <c:choose><c:when test="${orderStruc.status == 1}">已出票</c:when>
                                    <c:when test="${orderStruc.status == 2}">中<span class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></c:when>
									<c:when test="${orderStruc.status == 3}">未中奖</c:when>
									<c:when test="${orderStruc.status == 4}">已撤销</c:when>
									<c:when test="${orderStruc.status == 5}">处理中</c:when>
									<c:when test="${orderStruc.status == 7}">存在异常</c:when></c:choose></div>
								<div class="cell7">
									<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${orderStruc.orderId}"
										target="_black">投注详情</a>
								</div>
							</li>
					     	</c:forEach>
					     </c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

<div class="footer">
	<div class="footer-link">
		<p class="footer-copyright">&copy;2014-2017宝开彩票 All Rights Reserved</p>
	</div>
</div>

<script type="text/javascript">
var userContextPath = '${userContextPath}';

</script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.md5.js" ></script> 	
		<script type="text/javascript" src="${staticFileContextPath}/static/js/indexv2/signin.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/login/login-logic-top.js" ></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/game/base-all.min.js"></script>
		  <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
		
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/game-parent.min.js" ></script>
<script src="${staticFileContextPath}/static/js/jquery.jscrollpane.min.js"></script>
<script src="${staticFileContextPath}/static/js/game/k3-dice/jquery.longclick.min.js"></script>
<script src="${staticFileContextPath}/static/js/game/k3-dice/jquery-ui-custom.min.js"></script>
<script src="${staticFileContextPath}/static/js/game/k3-dice/k3-utils.js"></script>
<script src="${staticFileContextPath}/static/js/game/k3-dice/k3-dice.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/k3dice.js" ></script>
</body>
</html>