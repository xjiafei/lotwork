<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- 从被装饰页面获取body标签内容 -->
<html>
<head>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/game.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/lln115/lln115.css" />

<title>宝开11选5</title>
</head>
<body>
<div class="countdown countdown-current">
		<a href="javascript:void(0);"></a>
		<span>截止时间</span>
		<strong>02:34</strong>
	</div>
	<div class="bg-body">
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="11选5" href="#">11选5</a></h1>
			<div class="deadline">
				<div class="deadline-text">离投注截止还有</div>
				<div class="deadline-number">
					<em class="min-left">0</em>
					<em class="min-right">0</em>
					<span>:</span>
					<em class="sec-left">0</em>
					<em class="sec-right">0</em>
					<strong style="display:none">预售中</strong>
				</div>
				<div class="deadline-text">第<strong id="J-lotter-info-number">----</strong>期</div>
			</div>
			<div class="lottery">
				<div class="lottery-number" id="J-lottery-info-balls">
					<em>0</em>
					<em>1</em>
					<em>2</em>
					<em>5</em>
					<em>6</em>
				</div>
				<div class="lottery-text">第<strong id="J-lotter-info-lastnumber">----</strong>期<a href="${user.resources.path}/game/chart/${lotteryCode}/Wuxing" target="_blank">开奖号码</a></div>
			</div>
			<div class="video-link">
				<div class="lottery-video"  style="display:none">美女互动开奖视频</div>
				<div class="lottery-link">
					<a href="${user.resources.path}/game/chart/${lotteryCode}/Wuxing" target="_blank" class="chart">号码走势</a>
				</div>
			</div>
		</div>
	</div>
	<!-- header end -->
	<!-- 开奖视频开始 -->
	<div id="J-video-area" class="video-area">
		<div class="controlBar" style="display:none">
			<div class="state">
				<span id="stop_video">pause</span>
				<span id="start_video">start</span>
			</div>
		</div>
		<div id="J-loading-video" class="loading"></div>
		<div id="player"></div>
	</div>
	<!-- 开奖视频结束 -->
	<div class="g_33 main">
		<div class="chart-switch" style="top: 192px;">近期走势</div>
		<div class="play-section">
			<div class="program-chase">
				<ul class="program-chase-title">
					<li class="current">我的方案</li>
					<li>我的追号</li>
				</ul>
				<ul class="program-chase-content">
					<li>


						<table class="program-chase-table"  id="program-user-orders" >

							<thead>
								<tr>
									<th>期号</th>
									<th>投注金额</th>
									<th>状态</th>
								</tr>
							</thead>
					    <c:if test="${empty orders}">
									<td colspan="5">您最近7天暂时没有投注记录！</td>
					     </c:if>
					     <c:if test="${!empty orders}">
							<tbody >

							<c:forEach items="${orders}" var="orderStruc" varStatus="status">
								<tr>
									<td><a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${orderStruc.orderId}" target="_black">${orderStruc.webIssueCode}</a></td>
									<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></td>
									       <td>  <c:choose><c:when test="${orderStruc.status == 1}">已出票</c:when>
                                    <c:when test="${orderStruc.status == 2}">中<span class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></c:when>
									<c:when test="${orderStruc.status == 3}">未中奖</c:when>
									<c:when test="${orderStruc.status == 4}">已撤销</c:when>
									<c:when test="${orderStruc.status == 5}">处理中</c:when>
									<c:when test="${orderStruc.status == 7}">存在异常</c:when></c:choose></td>									</tr>
							</c:forEach>

							</tbody>
							</c:if>
						</table>
						<table class="program-chase-table">
                        	<tr>
                                <td colspan="3" class="text-right"><a href="${currentContextPath}/gameUserCenter/queryOrdersEnter?time=7"
                                    target="_blank" class="more">更多&raquo;</a></td>
                            </tr>
                        </table>
					</li>
					<li>
						<table class="program-chase-table"  id="program-user-plans" >

							<thead>
								<tr>
									<th>起始期号</th>
									<th>已追/总</th>
									<th>总金额</th>
									<th>状态</th>
								</tr>
							</thead>
							 <c:if test="${empty plans}">
									<td colspan="5">您最近7天暂时没有追号记录！</td>
					         </c:if>
					    	<c:if test="${!empty plans}">
							<tbody >
								<c:forEach items="${plans}" var="plan" varStatus="status">
								<tr>
									<td><a href="${currentContextPath}/gameUserCenter/queryPlanDetail?planid=${plan.planid}" target="_black">${plan.startWebIssueCode}</a></td>
									<td>${plan.finishIssue}/${plan.totalIssue}</td>
									<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plan.totamount/10000}" pattern="#,###.##"  minFractionDigits="2"/></span></td>
									<td>
                                    <c:choose><c:when test="${plan.status == 0}">未开始</c:when>
                                    <c:when test="${plan.status == 1}">进行中</c:when>
									<c:when test="${plan.status == 2}">已结束</c:when>
									<c:when test="${plan.status == 3}">已终止</c:when>
									<c:when test="${plan.status == 4}">暂停</c:when></c:choose></td>
								</tr>
							</c:forEach>
								<tr>

								</tr>
							</tbody>
						</c:if>
						</table>
                        <table class="program-chase-table">
                        	<tr>
                                <td colspan="3" class="text-right"><a class="more" href="${currentContextPath}/gameUserCenter/queryPlans?time=7"
										target="_blank" class="more">更多&raquo;</a></td>
                            </tr>
                        </table>
					</li>
				</ul>
			</div>
			<div id="J-play-select" class="play-select">
				<div class="prompt">
					<i class="icon-question"></i> <span class="method-tip"></span>
					<a class="example-button" href="javascript:void(0);">选号示例</a>
					<div class="example-tip"></div>
				</div>
			</div>
		</div>
		<!-- 走势图开始 -->
		<div class="chart-section" style="display: none" id="J-game-chart">
			<!-- 动态显示各投注方式的DOM -->

			<!-- 动态显示各投注方式的DOM -->
		</div>
		<!-- 走势图结束 -->
	<!-- 选球统计开始 -->
	<div class="number-section">
		<div id="J-balls-main-panel"></div>
		<div id="J-balls-statistics-panel">
			<ul class="bet-statistics">
				<li class="multiple-limit" style="display:none">倍数限额：<em
					id="J-balls-statistics-multipleLimit">无限制</em></li>
				<li class="choose-bet">你选择了<em
					id="J-balls-statistics-lotteryNum">0</em>注，
				</li>
				<!--
					<li class="choose-multiple">
						<div class="choose-list" style="display:none;">
							<a href="javascript:void(0);">1倍</a>
							<a href="javascript:void(0);">5倍</a>
							<a href="javascript:void(0);">10倍</a>
							<a href="javascript:void(0);">15倍</a>
						</div>
						<input type="text" value="8000" class="input" /><i></i><span class="text">倍，</span>
					</li>
					-->
				<span id="J-balls-statistics-multiple-text" style="display: none;">1</span>

				<select id="J-balls-statistics-multiple" class="J-ui-select" style="display: none;">
					<option value="1" selected="selected">1</option>
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="15">15</option>
				</select> 倍 &nbsp;&nbsp;
 
				<select id="J-balls-statistics-moneyUnit" class="J-ui-select"
					style="display: none;">
					<option value="1" selected="selected">元</option>
					<option value="0.1">角</option>
					<option value="0.01">分</option>
				</select> 
				<!--
					<li class="choose-model">
						<div class="choose-list ">
							<a href="javascript:void(0);">角</a>
							<a href="javascript:void(0);">元</a>
						</div>
						<span class="info">角</span><i></i><span class="text">模式</span>
					</li>
					-->

				<li class="total-money">共<strong class="price"
					id="J-balls-statistics-amount">300.00</strong>元
				</li>
			</ul>
			<div class="choose-btn">
				<input type="button" value="" id="J-add-order" class="" />
			</div>
		</div>
	</div>
	<!-- 选球统计结束 -->



	<div class="panel-section">
		<div class="clearfix">
			<div class="panel-select">
				<ul id="J-balls-order-container">
				</ul>
			</div>
			<div class="panel-btn">
				<a href="javascript:void(0);" id="randomone" class="take-one"><i></i>机选一注</a>
				<a href="javascript:void(0);" id="randomfive" class="take-five"><i></i>机选五注</a>
				<a href="javascript:void(0);" id="restdata" class="empty-number"><i></i>清空号码</a>
			</div>
		</div>
		<ul class="bet-statistics">
			<li>方案注数 <em id="J-gameOrder-lotterys-num">0</em> 注，
			</li>
			<li>金额<strong class="price"> <dfn>&yen;</dfn> <span
					id="J-gameOrder-amount">0.00</span>
			</strong>元
			</li>
			<li><label class="label" for="J-trace-switch"><input
					id="J-trace-switch" type="checkbox" class="checkbox">我要追号</label></li>
			<li><span class="bet-tips"><i></i>追号能提高中奖率</span></li>
		</ul>






		<!-- 追号开始 -->
		<div id="J-trace-panel" style="display: none;">


			<div class="chase-tab">
				<div class="chase-tab-title clearfix">
					<ul>
						<li class="chase-tab-t current">普通追号</li>
						<li class="chase-tab-t">高级追号</li>
					</ul>
					<h3>追号设置</h3>

					<div id="J-trace-iswinstop-panel" class="chase-stop"
						style="display: none;">
						<label class="label" for="J-trace-iswinstop"><input
							id="J-trace-iswinstop" type="checkbox" class="checkbox"  autocomplete="off"/>累计盈利</label>&gt;<input
							id="J-trace-iswinstop-money" disabled="disabled" type="text"
							class="input" value="1000"  autocomplete="off"/>元时停止追号&nbsp; <i
							class="icon-question" id="J-trace-iswinstop-hover">
							<div id="chase-stop-tip-2" class="chase-stop-tip">
								当追号计划中，累计盈利金额（已获奖金扣除已投本金）大于设定值时，即停止继续追号。<br /> 如果您不考虑追号的盈利情况，<a
									href="#" id="J-chase-stop-switch-2">点这里</a>。
							</div>
						</i>

					</div>
					<div id="J-trace-iswintimesstop-panel" class="chase-stop">
						<label class="label"><input id="J-trace-iswintimesstop"
							type="checkbox" class="checkbox" />中奖后停止追号</label><input
							style="display: none;" id="J-trace-iswintimesstop-times"
							type="text" class="input" disabled="disabled" value="1"  autocomplete="off"/>&nbsp;
						<i class="icon-question" id="J-trace-iswintimesstop-hover">
							<div id="chase-stop-tip-1" class="chase-stop-tip">
								当追号计划中，一个方案内的任意注单中奖时，即停止继续追号。<br /> 如果您希望考虑追号的实际盈利，<a href="#"
									id="J-chase-stop-switch-1">点这里</a>。
							</div>
						</i>

					</div>
				</div>


				<div class="chase-tab-content chase-tab-content-current">
					<ul class="chase-select-normal clearfix">
						<li id="J-function-select-tab">连续追：
							<ul class="function-select-title">
								<li>5期</li>
								<li>10期</li>
								<li>15期</li>
								<li>20期</li>
							</ul>
							<ul class="function-select-panel" style="display: none;">
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
						</li>
						<li><input id="J-trace-normal-times" type="text"
							class="input" value="10"  autocomplete="off"/>&nbsp;&nbsp;期</li>
						<li>倍数： <!--
							<div class="choose-model">
								<div class="choose-list " style="display:none;">
									<a href="javascript:void(0);">5</a>
									<a href="javascript:void(0);">10</a>
									<a href="javascript:void(0);">15</a>
									<a href="javascript:void(0);">20</a>
									<a href="javascript:void(0);">30</a>
								</div>
								<span class="info">5</span><i></i>
							</div>
							--> <select id="J-trace-normal-multiple" style="display: none;">
								<option>1</option>
								<option>5</option>
								<option>10</option>
								<option>15</option>
								<option>20</option>
								<option>30</option>
								<option>50</option>
						</select>
						</li>
					</ul>

					<div class="chase-table-container">
						<table id="J-trace-table" class="chase-table">
							<tbody id="J-trace-table-body">
								<tr>
									<th style="width: 125px;" class="text-center">序号</th>
									<th><label class="label"><input type="checkbox"
											class="checkbox" />追号期次</label>
									</td>
									<th>倍数</th>
									<th>金额</th>
									<th>预计开奖时间</th>
								</tr>
							</tbody>
						</table>
					</div>

				</div>





				<div class="chase-tab-content">
					<div class="chase-select-high">
						<div class="title">基本参数</div>
						<ul class="base-parameter">
							<li>起始期号： <select id="J-traceStartNumber" style="display: none;">
									<option value="201212141245">201212141245(当前期)</option>
									<option value="201212141246">201212141246</option>
									<option value="201212141247">201212141247</option>
									<option value="201212141248">201212141248</option>
									<option value="201212141249">201212141249</option>
							</select>

							</li>
							<li>追号期数： <input id="J-trace-advanced-times" type="text"
								class="input" value="10"  autocomplete="off"/>&nbsp;&nbsp;期（最多可以追<span
								id="J-trace-number-max">0</span>期）
							</li>
							<li>起始倍数： <input id="J-trace-advance-multiple" type="text"
								class="input" value="1"  autocomplete="off"/>&nbsp;&nbsp;倍
							</li>
						</ul>

						<div class="title">高级参数</div>
						<div id="J-trace-advanced-type-panel" class="high-parameter">
							<ul class="tab-title">
								<li>翻倍追号</li>
								<li>盈利金额追号</li>
								<li>盈利率追号</li>
							</ul>
							<ul class="tab-content">
								<li>
									<p data-type="a">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type1"
											checked="checked"  autocomplete="off"/> 每隔&nbsp;<input
											id="J-trace-advanced-fanbei-a-jiange" type="text"
											class="input" value="2"  autocomplete="off"/>&nbsp;期 倍数x<input
											id="J-trace-advanced-fanbei-a-multiple" type="text"
											class="input trace-input-multiple" value="2"  autocomplete="off"/>
									</p>
									<p data-type="b">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type1"  autocomplete="off"/> 前&nbsp;<input
											id="J-trace-advanced-fanbei-b-num" type="text" class="input"
											value="5" disabled="disabled"  autocomplete="off"/>&nbsp;期 倍数=起始倍数，之后倍数=<input
											id="J-trace-advanced-fanbei-b-multiple" type="text"
											class="input trace-input-multiple" value="3"
											disabled="disabled"  autocomplete="off"/>
									</p>
								</li>
								<li>
									<p data-type="a">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type2"
											checked="checked"  autocomplete="off"/> 预期盈利金额≥&nbsp;<input
											id="J-trace-advanced-yingli-a-money" type="text"
											class="input" value="100"  autocomplete="off"/>&nbsp;元
									</p>
									<p data-type="b">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type2"  autocomplete="off"/> 前&nbsp;<input
											id="J-trace-advanced-yingli-b-num" type="text" class="input"
											value="2" disabled="disabled"  autocomplete="off"/>&nbsp;期 预期盈利金额≥&nbsp;<input
											id="J-trace-advanced-yingli-b-money1" type="text"
											class="input" value="100" disabled="disabled"  autocomplete="off"/>&nbsp;元，之后预期盈利金额≥&nbsp;
                                            <input	id="J-trace-advanced-yingli-b-money2" type="text"	class="input" value="50" disabled="disabled"  autocomplete="off"/>&nbsp;元
									</p>
								</li>
								<li>
									<p data-type="a">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type3"
											checked="checked"  autocomplete="off"/> 预期盈利率≥&nbsp;<input
											id="J-trace-advanced-yinglilv-a" type="text" class="input"
											value="10"  autocomplete="off"/>&nbsp;%
									</p>
									<p data-type="b">
										<input class="trace-advanced-type-switch" type="radio"
											class="checkbox" name="trace-advanced-type3"  autocomplete="off"/> 前&nbsp;<input
											id="J-trace-advanced-yinglilv-b-num" type="text"
											class="input" value="5" disabled="disabled"  autocomplete="off"/>&nbsp;期
										预期盈利率≥&nbsp;<input id="J-trace-advanced-yingli-b-yinglilv1"
											type="text" class="input" value="30" disabled="disabled"  autocomplete="off"/>&nbsp;%，之后预期盈利率≥&nbsp;<input id="J-trace-advanced-yingli-b-yinglilv2" disabled="disabled"  autocomplete="off"	type="text" class="input" value="10" />&nbsp;%
									</p>
								</li>
							</ul>
						</div>
					</div>

					<div class="chase-btn">
						<input id="J-trace-builddetail" type="button" value="生成追号计划">
					</div>


					<div class="chase-table-container">
						<table id="J-trace-table-advanced" class="chase-table">
							<tbody id="J-trace-table-advanced-body">
								<tr>
									<th style="width: 125px;" class="text-center">序号</th>
									<th><label class="label"><input type="checkbox"
											class="checkbox" />追号期次</label>
									</td>
									<th>倍数</th>
									<th>金额</th>
									<th>预计开奖时间</th>
								</tr>
							</tbody>
						</table>
					</div>
				</div>








			</div>


			<ul class="bet-statistics">
				<li>共追号 <span id="J-trace-statistics-times">0</span> 期，<em><span
						id="J-trace-statistics-lotterys-num">0</span> </em>注，
				</li>
				<li>总投注金额 <strong class="price"><dfn>&yen;</dfn><span
						id="J-trace-statistics-amount">0.00</span></strong> 元
				</li>
			</ul>

		</div>
		<!-- 追号结束 -->






	</div>


	<!-- 提交按钮开始 -->
	<div class="bet-btn">
		<input type="button" id="J-submit-order" value="马上投注">
	</div>
	<!-- 提交按钮结束 -->
	</div>



	<!-- 底部开始 -->
	<div class="foot  g_33 ">
		资金安全建议：为了您的资金安全，建议定期更换您的安全密码。<br />浏览器建议：首选为IE9或IE10浏览器，其次为火狐和Google浏览器<br />分辨率建议：使用1024×768以上的分辨率。
	</div>
	<!-- 底部结束 -->



	<!-- loading开始 -->
	<div class="bet-common-loading" id="J-bet-loading-panel"></div>
	<!-- loading结束 -->


	<!-- 漂浮倒计时开始 -->
	<div class="countdown countdown-current">
		<a href="javascript:void(0);"></a>
		<span>截止时间</span>
		<strong>00:00</strong>
	</div>
	<!-- 漂浮倒计时结束 -->
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.md5.js" ></script> 	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/indexv2/signin.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/login/login-logic-top.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/swfobject.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lln115/game-parent.min.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/lln115/lln115/game-lln115-sub.min.js" ></script>
	<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/lln115bet.js"></script>

</body>

</html>