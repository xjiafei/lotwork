<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" /> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
<title>${lotteryName}</title>

	<!--[if !IE]><-->
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/jl-dice/loading.css" />
	<script src="${staticFileContextPath}/static/js/game/jl-dice/base-all.js"></script>
	<script src="${staticFileContextPath}/static/js/game/jl-dice/pace.js"></script>
	<![endif]-->


	
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/game.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/jl-dice/jl-dice.css">
<c:if test="${lotteryName=='高频骰宝(至尊厅)'}">
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/jl-dice/jl-dice-vip.css">
</c:if>
<c:if test="${lotteryName=='高频骰宝(娱乐厅)'}">
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/jl-dice/jl-diceOverWrite.css">
</c:if>
<script>
	var staticFileContextPath = '${staticFileContextPath}';
	var userContextPath = '${userContextPath}';
</script>

</head>
<body>
<div class="public-notice"><i class="ico-close"></i><div class="inner"><i class="ico-volume"></i>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日</div></div>


<div class="main">
	<div class="dice-wrap">
		<div class="dice-panel">
			<div class="logo">高频骰宝</div>
			<div class="quickChange">
				<c:if test="${lotteryName=='高频骰宝(娱乐厅)'}">
					<a class="normalGame" href="javascript:;">娱乐厅</a>
					<a class="vipGame" href="/gameBet/jldice2">至尊厅</a>
				</c:if>
				<c:if test="${lotteryName=='高频骰宝(至尊厅)'}">
					<a class="normalGame normalActive" href="/gameBet/jldice1">娱乐厅</a>
					<a class="vipGame vipActive" href="javascript:;">至尊厅</a>
				</c:if>
			</div>
			<div class="shortcuts" style="display:none">
				<a target="_blank" href="${staticFileContextPath}/static/js/activity/jldice/index.html">骰宝专题课堂</a>
			</div>
			<div class="lottery-info-number">
				<span id="J-lottery-info-number">-</span>期
			</div>
			<div id="J-game-volume" class="game-volume" style="display:none"></div>
			<div class="lottery-status">
				<div class="soldout">停售中</div>
				<div class="lottery-countdown" id="J-lottery-countdown">
					<span data-time="m1" class="time-0"></span>
					<span data-time="m2" class="time-0"></span>
					<span class="time-colon"></span>
					<span data-time="s1" class="time-0"></span>
					<span data-time="s2" class="time-0"></span>
				</div>
			</div>
			<div class="lottery-records">
				<div class="record-top">
					<c:if test="${lotteryName=='高频骰宝(娱乐厅)'}">
						<a href="/game/chart/jldice1/chart" target="_blank" class="chart">查看更多</a>
					</c:if>
					<c:if test="${lotteryName=='高频骰宝(至尊厅)'}">
						<a href="/game/chart/jldice2/chart" target="_blank" class="chart">查看更多</a>
					</c:if>
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
						<!-- <li>
							<div class="rec1">
								<span class="dice-number dice-number-1">1</span>
								<span class="dice-number dice-number-3">3</span>
								<span class="dice-number dice-number-5">5</span>
							</div>
							<div class="rec2">15</div>
							<div class="rec3">大</div>
							<div class="rec4">单</div>
							<div class="rec5">045期</div>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
		<!--当前玩家信息-->
		<div id="dice-user" class="dice-user">
			<div class="main-chair">
				<div class="user-head">
					<div class="head-pic"></div>
					<span class="head-name"></span>
				</div>
				<div class="user-winning">+23,365.44元</div>
			</div>
		</div>

		<div id="J-dice-sheet" class="dice-table"></div>
		
		<!--<div id="dice-recycle-box">-->
			<!--<div class="dice-recycle"></div>	-->
		<!--</div>	-->

	</div>
	<div class="game-history-wrap">
		<div class="game-history">
			<div class="history-top">
				<a href="${currentContextPath}/gameUserCenter/queryOrdersEnter?time=7" class="history-more" target="_black">查看更多</a>
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
					<!-- <li>
						<div class="cell1" data-history-project="DCDSGSGDFHGDFHFDK">DCDSGSGDFHGDFHFDK</div>
						<div class="cell2">20150112-023</div>
						<div class="cell3">2014-04-20 11:44:55</div>
						<div class="cell4"><dfn>¥</dfn>30000000.00</div>
						<div class="cell5" data-history-balls>
							<span class="dice-number dice-number-1">1</span>
							<span class="dice-number dice-number-3">3</span>
							<span class="dice-number dice-number-5">5</span>
						</div>
						<div class="cell6" data-history-result>未中奖</div>
						<div class="cell7">
							<a href="xxx.php?DCDSGSGDFHGDFHFDG" target="_blank">投注详情</a>
						</div>
					</li> -->
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

<div class="dice-ctrl">
	<div class="dice-bar-wrap">
		<div id="J-dice-bar" class="dice-bar">

		</div>
	</div>

</div>

<div class="footer">
	<div class="footer-link">
		<p class="footer-copyright">&copy;2014-<fmt:formatDate value="${now}" pattern="yyyy"/> 宝开彩票 All Rights Reserved</p>
	</div>
</div>

<div class="dice-cup" id="diceCup">
	<span></span>
	<span></span>
	<span></span>
</div>
<!--结果提示框-->
<div class="result-tips" id="J-result-tips"></div>
	<%-- 	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.md5.js" ></script> 	
		<script type="text/javascript" src="${staticFileContextPath}/static/js/indexv2/signin.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/login/login-logic-top.js" ></script> --%>
		<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/game/base-all.min.js"></script> --%>
<%-- <script src="${staticFileContextPath}/static/js/game/jl-dice/base-all.js"></script> --%>
		  <%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script> --%>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/game/game-parent.min.js" ></script> --%>
	

<script src="${staticFileContextPath}/static/js/game/jl-dice/audio5.min.js"></script>

<script src="${staticFileContextPath}/static/js/jquery-migrate-1.1.0.min.js"></script>
<%-- <script src="${staticFileContextPath}/static/js/jquery.cookie.js"></script> --%>
<script src="${staticFileContextPath}/static/js/jquery.jscrollpane.min.js"></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jQueryRotate.2.2.js"></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jquery.longclick.min.js"></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jquery-ui-custom.min.js"></script>


<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jl-GameMessage.js"></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jl-utils.js"></script>
<script src="${staticFileContextPath}/static/js/game/jl-dice/jl-dice.js"></script>

<c:if test="${lotteryName=='高频骰宝(至尊厅)'}">
	<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/jldice.js" ></script>
</c:if>
<c:if test="${lotteryName=='高频骰宝(娱乐厅)'}">
	<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/jldice1.js" ></script>
</c:if>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.md5.js" ></script> 	
		<script type="text/javascript" src="${staticFileContextPath}/static/js/indexv2/signin.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/login/login-logic-top.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/game-parent.min.js" ></script> 
</body>
</html>