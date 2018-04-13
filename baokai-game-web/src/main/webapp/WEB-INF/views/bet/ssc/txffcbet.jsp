<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>${lotteryName}</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/game.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/ffc/txffc.css" />


<title>${lotteryName}</title>
</head>
<body >
<div class="bg-body" >
<!-- toolbar start -->
	<!-- <div class="public-notice">
		<i class="ico-close"></i>
		<div class="inner clearfix">
			<i class="ico-volume"></i>
			<ul>
				<li>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日</li>
				<li>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日</li>
			</ul>
		</div>
	</div> -->
	<div class="countdown countdown-current">
		<a href="javascript:void(0);"></a>
		<span>截止时间</span>
		<strong>02:34</strong>
	</div>
	<div class="clock">
		<div id="J-red-light" class="red_light"></div>
		<div class="second">
			<img src="${staticFileContextPath}/static/images/game/ffc/second_hand.png" alt="" id="second-hand" />
		</div>
	</div>
	
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="时时彩" href="#">时时彩</a></h1>
			<div class="lottery-link">
					<a href="/game/chart/${lotteryCode}/Wuxing" target="_blank" class="chart">号码走势</a>
                    <a href="${userContextPath}/help/queryLotterylHelp?cateId2=26&cate2Name=${lotteryName}" target="_blank"
						 class="info">游戏说明</a>
				</div>
			<div class="deadline">
				<div class="deadline-text"><strong id="J-lottery-info-number">....-..</strong>期<br />投注截止时间</div>
			</div>
			<div class="lottery">
				<div class="lottery-text"><strong id="J-lottery-info-lastnumber">....-..</strong>期<br />
                    <a href="/game/chart/${lotteryCode}/Wuxing" target="_blank">开奖号码</a></div>
				<div class="lottery-number" id="J-lottery-info-balls">
					<em>0</em>
					<em>1</em>
					<em>2</em>
					<em>5</em>
					<em>6</em>
				</div>
			</div>
		</div>
	</div>
	<!-- header end -->
	<div class="g_33 main">
		<div class="chart-switch" style="top:100px;">走势图</div>
		<div class="public-notice">
			<i class="ico-close"></i>
			<div class="inner clearfix">
				<i class="ico-volume"></i>
				<ul>
					<li>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日111</li>
					<li>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日222</li>
				</ul>
			</div>
		</div>
		<div class="play-section">
			<div id="J-header-clock-img" class="bg-nav"></div>
			<div id="J-play-select" class="play-select">
			</div>
		</div>
		<!-- 走势图开始 -->
		<div class="chart-section" style="display:none" id="J-game-chart">
		</div>
		<!-- 走势图结束 -->
		<!-- 选球统计开始 -->
		<div class="number-section">
			<div id="J-balls-main-panel">
				<div class="prompt" style="display:;">
					<a class="example-button" href="javascript:void(0);">玩法说明</a>
					<!--消息弹出模版-->
					<div class="example-tip" style="display:none">
						<i class="example-arrow"></i>
						<p class="example-text">这里是玩法说明这里是玩法说明这里是玩法说明这里是玩法说明这里是玩法说明这里是玩法说明这里是玩法说明</p>
						<table class="example-table">
							<tbody>
								<tr>
									<td>开奖号</td>
									<td class="text-right"><em>1</em><em>2</em><em>3</em><em>4</em><em>5</em></td>
									<td class="text-right">奖池3526534</td>
								</tr>
								<tr>
									<td>一等奖</td>
									<td class="text-right"><em>4</em><em>5</em><em>7</em></td>
									<td class="text-right"><span class="price"><dfn>&yen;</dfn><span>1000.00</span></span></td>
								</tr>
								<tr>
									<td>二等奖</td>
									<td class="text-right"><em>8</em><em>9</em><em>0</em></td>
									<td class="text-right"><span class="price"><dfn>&yen;</dfn><span>1000.00</span></span></td>
								</tr>
								<tr>
									<td>三等奖</td>
									<td class="text-right"><em>0</em><em>0</em><em>0</em></td>
									<td class="text-right"><span class="price"><dfn>&yen;</dfn><span>1000.00</span></span></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!--消息弹出模版结束-->
				</div>
			</div>
			<div id="J-balls-statistics-panel">
				
				<ul class="bet-statistics">
					<li class="multiple-limit" style="display:none">倍数限额：<em id="J-balls-statistics-multipleLimit">无限制</em></li>
					<li class="choose-bet">你选择了<em id="J-balls-statistics-lotteryNum">0</em>注，</li>
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
					<li class="choose-model">
						<div class="choose-tab">
							<a href="javascript:void(0);">角</a>
							<a href="javascript:void(0);" class="current">元</a>
						</div>
						<span class="text">模式</span>
					</li>
					<li class="choose-model">
						<div class="choose-list ">
							<a href="javascript:void(0);">角</a>
							<a href="javascript:void(0);">元</a>
						</div>
						<span class="info">角</span><i></i><span class="text">模式</span>
					</li>
					-->
					<li class="choose-model">
						<span style="display:none;" id="J-balls-statistics-multiple-text">1</span>
						<select style="display:none;" class="J-ui-select" id="J-balls-statistics-multiple">
							<option selected="selected" value="1">1</option>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
						</select>
						<span class="text">倍，</span>
					</li>
					<li class="choose-model">
						<select style="display:none;" class="J-ui-select" id="J-balls-statistics-moneyUnit">
							<option selected="selected" value="1">元</option>
							<option value="0.1">角</option>
							<option value="0.01">分</option>
						</select>
						<span class="text">模式</span>
					</li>
					<li id="J-choose-rebate" class="choose-model choose-rebate">
						<span class="text">奖金</span>
						<select id="J-select-rebate" class="J-ui-select" style="display:none;"></select>
					</li>
					<li class="total-money">共<strong class="price" id="J-balls-statistics-amount"><dfn>&yen;</dfn>0</strong>元</li>
				</ul>
				<div class="choose-btn"><input type="button" value="" id="J-add-order" class="" /></div>
			</div>
		</div>
		<!-- 选球统计结束 -->
		
		
		
		<div class="panel-section">
			<div class="clearfix">
				<div id="J-balls-order-wrap" class="panel-select">
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
				<li>方案注数 <em id="J-gameOrder-lotterys-num">0</em> 注，</li>
				<li>金额<strong class="price"> <dfn>&yen;</dfn> <span id="J-gameOrder-amount">0.00</span> </strong>元</li>
				<li><label class="label" for="J-trace-switch"><input id="J-trace-switch" type="checkbox" class="checkbox">我要追号</label></li>
				<li><span class="bet-tips"><i></i>追号能提高中奖率</span></li>
			</ul>
			





	<!-- 追号开始 -->
			<div id="J-trace-panel" style="display:none;">
				<div class="chase-tab">
					<div class="chase-tab-title clearfix">
						<ul>
							<li class="chase-tab-t current">普通追号</li>
							<li class="chase-tab-t">高级追号</li>
						</ul>
						
						<div id="J-trace-iswinstop-panel" class="chase-stop" style="display:none;">
							<label class="label" for="J-trace-iswinstop"><input id="J-trace-iswinstop" type="checkbox" class="checkbox" />累计盈利</label>&gt;<input id="J-trace-iswinstop-money" disabled="disabled" type="text" class="input" value="1000"  />元时停止追号&nbsp;
							<i class="icon-question" id="J-trace-iswinstop-hover">
								<div id="chase-stop-tip-2" class="chase-stop-tip">
									当追号计划中，累计盈利金额（已获奖金扣除已投本金）大于设定值时，即停止继续追号。<br />
									如果您不考虑追号的盈利情况，<a href="#" id="J-chase-stop-switch-2">点这里</a>。
								</div>
							</i>

						</div>
						<div id="J-trace-iswintimesstop-panel" class="chase-stop">
							<label class="label"><input id="J-trace-iswintimesstop" type="checkbox" class="checkbox" />中奖后停止追号</label><input style="display:none;" id="J-trace-iswintimesstop-times" type="text" class="input" disabled="disabled" value="1"  />&nbsp;
							<i class="icon-question" id="J-trace-iswintimesstop-hover">
								<div id="chase-stop-tip-1" class="chase-stop-tip">
									当追号计划中，一个方案内的任意注单中奖时，即停止继续追号。<br />
									如果您希望考虑追号的实际盈利，<a href="#" id="J-chase-stop-switch-1">点这里</a>。
								</div>
							</i>

						</div>
					</div>
					
					
					<div class="chase-tab-content chase-tab-content-current">
						<ul class="chase-select-normal clearfix">
							<li id="J-function-select-tab">
								连续追：
								<ul class="function-select-title">
									<li>5期</li>
									<li>10期</li>
									<li>15期</li>
									<li>20期</li>
								</ul>
								<ul class="function-select-panel" style="display:none;">
									<li></li>
									<li></li>
									<li></li>
									<li></li>
								</ul>
							</li>
							<li>
								<input id="J-trace-normal-times" type="text" class="input" value="10" />&nbsp;&nbsp;期
							</li>
							<li>
								倍数：
								<!--
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
								-->
								<select id="J-trace-normal-multiple" style="display:none;">
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
									<tr class="chase-table-thead">
										<th style="width:125px;" class="text-center">序号</th>
										<th><label class="label"><input type="checkbox" class="checkbox" />追号期次</label></td>
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
								<li>
									起始期号：
									
									<select id="J-traceStartNumber" style="display:none;">
				
										<option value="201212141245">201212141245(当前期)</option>
										<option value="201212141246">201212141246</option>
										<option value="201212141247">201212141247</option>
										<option value="201212141248">201212141248</option>
										<option value="201212141249">201212141249</option>
									</select>
									
								</li>
								<li>
									追号期数：
									<input id="J-trace-advanced-times" type="text" class="input" value="10" />&nbsp;&nbsp;期（最多可以追<span id="J-trace-number-max">0</span>期）
								</li>
								<li>
									起始倍数：
									<input id="J-trace-advance-multiple" type="text" class="input" value="1" />&nbsp;&nbsp;倍
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
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type1" checked="checked" />
											每隔&nbsp;<input id="J-trace-advanced-fanbei-a-jiange" type="text" class="input" value="2" />&nbsp;期
											倍数x<input id="J-trace-advanced-fanbei-a-multiple" type="text" class="input trace-input-multiple" value="2" />
										</p>
										<p data-type="b">
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type1" />
											前&nbsp;<input id="J-trace-advanced-fanbei-b-num" type="text" class="input" value="5" disabled="disabled" />&nbsp;期
											倍数=起始倍数，之后倍数=<input id="J-trace-advanced-fanbei-b-multiple" type="text" class="input trace-input-multiple" value="3" disabled="disabled" />
										</p>
									</li>
									<li>
										<p data-type="a">
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type2" checked="checked" />
											预期盈利金额≥&nbsp;<input id="J-trace-advanced-yingli-a-money" type="text" class="input" value="100" />&nbsp;元
										</p>
										<p data-type="b">
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type2" />
											前&nbsp;<input id="J-trace-advanced-yingli-b-num" type="text" class="input" value="2" disabled="disabled" />&nbsp;期
											预期盈利金额≥&nbsp;<input id="J-trace-advanced-yingli-b-money1" type="text" class="input" value="100" disabled="disabled" />&nbsp;元，之后预期盈利金额≥&nbsp;<input id="J-trace-advanced-yingli-b-money2" type="text" class="input" value="50" disabled="disabled" />&nbsp;元
										</p>
									</li>
									<li>
										<p data-type="a">
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type3" checked="checked" />
											预期盈利率≥&nbsp;<input id="J-trace-advanced-yinglilv-a" type="text" class="input" value="10" />&nbsp;%
										</p>
										<p data-type="b">
											<input class="trace-advanced-type-switch" type="radio" class="checkbox" name="trace-advanced-type3" />
											前&nbsp;<input id="J-trace-advanced-yinglilv-b-num" type="text" class="input" value="5" disabled="disabled" />&nbsp;期
											预期盈利率≥&nbsp;<input id="J-trace-advanced-yingli-b-yinglilv1" type="text" class="input" value="30" disabled="disabled" />&nbsp;%，之后预期盈利率≥&nbsp;<input id="J-trace-advanced-yingli-b-yinglilv2" disabled="disabled" type="text" class="input" value="10" />&nbsp;%
										</p>
									</li>
								</ul>
							</div>
						</div>
						
						<div class="chase-btn"><input id="J-trace-builddetail" type="button" value=""></div>
						
						
						<div class="chase-table-container">
						<table id="J-trace-table-advanced" class="chase-table">
								<tbody id="J-trace-table-advanced-body">
									<tr>
										<th style="width:125px;" class="text-center">序号</th>
										<th><label class="label"><input type="checkbox" class="checkbox" />追号期次</label></td>
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
						<li>共追号 <em id="J-trace-statistics-times">0</em> 期，<em id="J-trace-statistics-lotterys-num">0</em>注，</li>
						<li>总投注金额 <strong class="price"><dfn>&yen;</dfn><span id="J-trace-statistics-amount">0.00</span></strong> 元</li>
				</ul>

			</div>
	<!-- 追号结束 -->
		</div>
		
		
		<!-- 提交按钮开始 -->
		<div class="bet-btn"><input type="button" id="J-submit-order" value=""></div>
		<!-- 提交按钮结束 -->
		<div class="program-section">
			<div class="program-chase">
				<div class="program-chase-title clearfix">
					<ul>
						<li class="current">我的方案</li>
						<li>我的追号</li>
					</ul>
					<div class="chase-stop" id="J-trace-iswintimesstop-panel">
						
					</div>
				</div>
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
									<td>
									  <c:choose><c:when test="${orderStruc.status == 1}">等待开奖</c:when>
                                    <c:when test="${orderStruc.status == 2}">中<span class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber value="${orderStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></c:when>
									<c:when test="${orderStruc.status == 3}">未中奖</c:when>
									<c:when test="${orderStruc.status == 4}">已撤销</c:when>
									<c:when test="${orderStruc.status == 5}">处理中</c:when>
									<c:when test="${orderStruc.status == 7}">存在异常</c:when></c:choose></td>
								</tr>
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
                        </table>					</li>
				</ul>
			</div>
		</div>
	</div>




	<!-- 底部开始 -->
	<div class="foot  g_33 ">
		资金安全建议：为了您的资金安全，建议定期更换您的安全密码。<br />浏览器建议：首选为IE9或IE10浏览器，其次为火狐和Google浏览器<br />分辨率建议：使用1024×768以上的分辨率。
	</div>
	<!-- 底部结束 -->
</div>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery.ratate.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.simScroll.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/swfobject.js" ></script>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/game-parent.min.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/ffc/game-ffc-sub.min.js" ></script>
	<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/jlbet.js"></script>

</body>

</html>


