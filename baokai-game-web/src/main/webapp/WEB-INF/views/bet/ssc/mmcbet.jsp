<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>秒开时时彩</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/game.min.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/mmc/mmc.css?" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/game/mmc/jquery.jscrollpane.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/activity/gameActivity/diamondMission/diamondMission.css?" />

<title>${lotteryName}</title>
</head>
<body class="bg-body">
    <input type="hidden" id="mmcBetKey" value="${mmcBetKey}"></input>
	<div class="main_content">
		<!-- header start -->
		<div class="header">
			<div class="ball_number_area">
				<div class="lamp">
					<!-- <img src="../images/game/mmc/transparent.gif" data-imgholder="../images/game/mmc/marquee.gif" alt=""> -->
					<img src="${staticFileContextPath}/static/images/game/mmc/marquee_a.gif" data-imgholder="${staticFileContextPath}/static/images/game/mmc/marquee_b.gif" alt="">
				</div>
				<div id="flipball"></div>
				<div class="flipball_mask"></div>
			</div>
			<div class="lottery_draw" data-simulation="records">
				<div class="lottery_wrap">
					<ul data-records="wrap">
					  <c:if test="${!empty nums}">
							<c:forEach items="${nums}" var="num" varStatus="status">
								<li class="hassomediamond">
									<span class="diamond-icon"></span>
									<span class="diamond-grade${num[10]}"></span>
									<span class="num_${num[8]}">${num[9]}</span><span class="num_${num[0]}">${num[1]}</span><span class="num_${num[2]}">${num[3]}</span><span class="num_${num[4]}">${num[5]}</span><span class="num_${num[6]}">${num[7]}</span>
								</li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
				<div class="prev_next">
					<span class="prev">&lsaquo;</span>
					<span class="next">&rsaquo;</span>
				</div>
			</div>
			<a href="/game/chart/${lotteryCode}/Wuxing" target="_blank" class="more_links">更多记录</a>
			<ul class="handle_hand">
				<li class="hand_up"></li>
				<!-- <li class="hand_mid"></li> -->
				<li class="hand_down"></li>
			</ul>
			<div class="handle_tips">&nbsp;</div>
		</div>
		<!-- header end -->	
		<div class="g_33 main">
			<!-- <div class="chart-switch" style="top:192px;">走势图</div> -->
			<div class="public-notice"><i class="ico-close"></i><div class="inner"><i class="ico-volume"></i>尊敬的客户您好，娱乐平台提醒您，重启时时彩2月16日</div></div>
			<div class="top_section">
				<div class="play-section">				
					<div id="J-play-select" class="play-select">
						<div class="prompt">
							<i class="icon-question"></i>
							<span class="method-tip">&nbsp;玩法提示：从三位各选1个或多个号码，选号与开奖号后三位按位一致即中1700元。</span>
							<a class="example-button" href="javascript:void(0);">选号示例</a>
							<div class="example-tip"></div>
						</div>
					</div>
				</div>
				<!-- 选球统计开始 -->
				<div class="number-section">
					<div id="J-balls-main-panel"></div>
					<div id="J-balls-statistics-panel">
						<ul class="bet-statistics">
							
							<!--
							<li class="choose-model">
								<div class="choose-list ">
									<a href="javascript:void(0);">角</a>
									<a href="javascript:void(0);">元</a>
								</div>
								<span class="info">角</span><i></i><span class="text">模式</span>
							</li>
							-->
							
							<li class="total-money">共<strong class="price" id="J-balls-statistics-amount">300.00</strong>元</li>
							<li id="J-choose-rebate" class="choose-rebate">
							&nbsp;&nbsp; 奖金
								<select id="J-select-rebate" class="J-ui-select" style="display:none;"></select>
							</li>
							<li class="moneyUnit-choose">
								<span>模式</span>
								<!-- <div class="choose-model ui-simulation-switcher">
									<span class="switcher-1" data-value="1" data-name="元">元</span>
									<span class="switcher-2" data-value="0.1" data-name="角">角</span>
								</div> -->
								<!-- <select id="J-balls-statistics-moneyUnit" data-simulation="switcher" class="J-ui-select" style="display:none;"> -->
								<select data-simulation="switcher" id="J-balls-switcher-moneyUnit" class="J-ui-select" style="display:none;">
									<option value="1" selected="selected">元</option>
									<option value="0.1">角</option>
									<option value="0.01">分</option>
								</select>
								<div class="hide-block">
									<select data-simulation="switcher" id="J-balls-statistics-moneyUnit" class="J-ui-select" style="display:none;">
										<option value="1" selected="selected">元</option>
										<option value="0.1">角</option>
										<option value="0.01">分</option>
									</select>
								</div>
							</li>
							<li class="choose-bet">你选择了<em id="J-balls-statistics-lotteryNum">0</em>注</li>
							<li class="multiple-limit" style="display:none">倍数限额：<em id="J-balls-statistics-multipleLimit">无限制</em></li>
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
							<li class="multiple-choose">
								<div id="J-counter-single-choose" class="choose-model ui-simulation-counter" data-simulation="counter"
									data-counter-max="10" data-counter-min="1" data-counter-step="1" data-counter-init="1"
								>
									<span class="counter-action" data-counter-action="decrease">－</span>
									<input type="text" value="1" class="J_counter">
									<span class="counter-action" data-counter-action="increase">＋</span>
								</div>
								<input type="hidden" id="J-balls-mmc-multiple" value="1" data-valueInput>
								<span>倍&nbsp;&nbsp;</span>
							</li>
							<!---->
							<div class="hide-block">
								<select id="J-balls-statistics-multiple" class="J-ui-select" style="display:none;">
									<option value="1" selected="selected">1</option>
									<option value="5">5</option>
									<option value="10">10</option>
									<option value="15">15</option>
								</select>
							<div>
							<!---->
						</ul>
						<div class="choose-btn"><input type="button" value="" id="J-add-order" class="" /></div>
					</div>
				</div>
				<!-- 选球统计结束 -->
				<div id="J-select-panel" class="section_handle"></div>
			</div>
			
			<div class="panel-section">
				<div id="J-result-info" class="bet-info-panel panel-select scroll-pane" style="display:none">
					<ul></ul>
				</div>
				<div class="panel-select scroll-pane">
					<ul id="J-balls-order-container"></ul>
				</div>
				<div class="panel-btn" style="display:none;">
					<a href="javascript:void(0);" id="randomone" class="take-one"><i></i>机选一注</a>
					<a href="javascript:void(0);" id="randomfive" class="take-five"><i></i>机选五注</a>
					<a href="javascript:void(0);" id="restdata" class="empty-number"><i></i>清空号码</a>
				</div>
				<ul class="bet-statistics">
					<li class="total-money">金额<strong class="price"><!--  <dfn>&yen;</dfn>  --><span id="J-gameOrder-amount">0.00</span> </strong>元</li>
					<li class="choose-bet">你选择了<em id="J-gameOrder-lotterys-num">0</em>注</li>
					<li class="multiple-choose">
						<span>整体倍数</span>
						<span id="J-balls-statistics-multiple-text" style="display:none;">1</span>
						<div id="J-counter-multiple-choose" class="choose-model ui-simulation-counter" data-simulation="counter"
							data-counter-max="15" data-counter-multiplier="1" data-counter-min="1" data-counter-step="1" data-counter-init="1"
						>
							<span class="counter-action" data-counter-action="decrease">－</span>
							<input type="text" value="1" class="J_counter">
							<span class="counter-action" data-counter-action="increase">＋</span>
						</div>
						<input type="hidden" id="" value="1" data-valueInput>
						<span>倍&nbsp;&nbsp;</span>
					</li>
					<li>连续开奖<em>
							<select id="J-balls-lotterys-num" class="J-ui-select" style="display:none;">
								<option value="1" selected="selected">1</option>
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
						</em> 次
					</li>
					<div class="hide-block">
						<select id="J-balls-statistics-multiple" class="J-ui-select" style="display:none;">
							<option value="1" selected="selected">1</option>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="30">30</option>
						</select>
					</div>
					<li style="display:none;"><label class="label" for="J-trace-switch"><input id="J-trace-switch" type="checkbox" class="checkbox">我要追号</label></li>
					<li style="display:none;"><span class="bet-tips"><i></i>追号能提高中奖率</span></li>
				</ul>

				<!-- 追号开始 暂无追号-->
				<div id="J-trace-panel" style="display:none;">
					<div class="chase-tab">
						<div class="chase-tab-title clearfix">
							<ul>
								<li class="chase-tab-t current">普通追号</li>
								<li class="chase-tab-t">高级追号</li>
							</ul>
							<h3>追号设置</h3>
							
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
							<li>共追号 <span id="J-trace-statistics-times">0</span> 期，<em><span id="J-trace-statistics-lotterys-num">0</span> </em>注，</li>
							<li>总投注金额 <strong class="price"><dfn>&yen;</dfn><span id="J-trace-statistics-amount">0.00</span></strong> 元</li>
					</ul>

				</div>
				<!-- 追号结束 -->

				<!-- 提交按钮开始 -->
				<div class="bet-btn"><input type="button" id="J-submit-order" value=""></div>
				<div class="result_button_group">
					<a href="javascript:void(0);" class="restart_game" title="再玩一次"></a>
					<a href="javascript:void(0);" class="rechoose_ball" title="重新选号"></a>
				</div>
				<!-- 提交按钮结束 -->
			</div>
			<!-- 投注记录开始 -->
			<div class="lottery-history">
				<div class="history-top">
					<h2>游戏记录</h2>
					<span class="ht-icon ht-icon-left">&nbsp;</span>
					<span class="ht-icon ht-icon-right">&nbsp;</span>
				</div>
				<div class="history-content">
					<ul class="program-chase-list program-chase-list-header">
						<li>
				            <div class="cell1">方案编号</div>
				            <div class="cell2">投注时间</div>
				            <div class="cell3">投注内容</div>
				            <div class="cell4">投注金额（元）</div>
				            <div class="cell5">开奖号码</div>
				            <div class="cell6">中奖情况</div>
				        </li>
					</ul>
					<ul class="program-chase-list program-chase-list-body" data-simulation="gameHistory">
					<c:if test="${empty orders}">
									<ul class='text-center'>您最近7天暂时没有投注记录！</ul>
					    </c:if>
					     <c:if test="${!empty orders}">
							<c:forEach items="${orders}" var="orderStruc" varStatus="status">
								  <li>
								    <div class="cell1">${orderStruc.orderCode}</div>
						            <div class="cell2">${orderStruc.formatSaleTime}</div>
						            <div class="cell3">
						                <a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${orderStruc.orderId}" target="_blank">投注详情</a>
						            </div>
						            <div class="cell4">
						                <span class="price"><dfn>¥</dfn><fmt:formatNumber value="${orderStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>
						            </div>
						            <div class="cell5"><c:if test="${empty orderStruc.numberRecord}">-,-,-,-,-</c:if>${orderStruc.numberRecord}</div>
						            <div class="cell6">
						            <c:choose><c:when test="${orderStruc.status == 1}">等待开奖</c:when>
                                    <c:when test="${orderStruc.status == 2}"><span class="price"><dfn>¥</dfn><fmt:formatNumber value="${orderStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></c:when>
									<c:when test="${orderStruc.status == 3}">未中奖</c:when>
									<c:when test="${orderStruc.status == 4}">开奖失败</c:when>
									<c:when test="${orderStruc.status == 5}">处理中</c:when>
									<c:when test="${orderStruc.status == 7}">存在异常</c:when></c:choose>
						            </div>
								 </li>
							</c:forEach>
							</c:if>
					</ul>
				</div>
				<div class="lottery-footer">
					<a href="javascript:void(0)" class="expand-btn">收起/打开列表</a>
				</div>
			</div>
			<!-- 投注记录介绍 -->
		</div>
	</div>

	<!-- 底部开始 -->
	<div class="foot  g_33 ">资金安全建议：为了您的资金安全，建议定期更换您的安全密码。<br />浏览器建议：首选为IE9或IE10浏览器，其次为火狐和Google浏览器<br />分辨率建议：使用1024×768以上的分辨率。</div>
	<!-- 底部结束 -->
	
	<img src="${staticFileContextPath}/static/images/game/mmc/diamond_pop_rotary.png" id ="diamond_pop_rotary">
	<div class="preloadimg"  style="display: none;">
		<img src="${staticFileContextPath}/static/images/game/mmc/bg-rotate_1.png" />
		<img src="${staticFileContextPath}/static/images/game/mmc/bg-rotate_2.png" />
		<img src="${staticFileContextPath}/static/images/game/mmc/bg-rotate_3.png" />
		<img src="${staticFileContextPath}/static/images/game/mmc/bg-rotate_4.png" />
	</div>

	<div class="bg-plate">
            <div class="bg-rotate"><img src="${staticFileContextPath}/static/images/game/mmc/bg-rotate.png" alt=""id="imgRotate"></div><div
        class="plate-star"id="plateStar"><strong></strong></div><div class="plate-light"></div><ul
        class="plate-nums"><li class="ball-diamonds-5"></li><li class="ball-8"></li><li class="ball-7"></li><li
        class="ball-diamonds-5"></li><li class="ball-diamonds-5"></li></ul><ul
        class="plate-order"><li><span>[后三_复式]</span><span>-,-,2,3,3</span></li><li><span>[后三_复式]</span><span>-,-,2,3,3</span></li><li><span>[后三_复式]</span><span>-,-,2,3,3</span></li><li><span>[后三_复式]</span><span>-,-,2,3,3</span></li><li><span>[后三_复式]</span><span>-,-,2,3,3</span></li></ul><div
        class="plate-pop"id="platePop"><div class="bonus-single">12345</div><div class="bonus-multiple"></div><div
        class="bonus-total"></div></div><div class="plate-title plate-title-3"></div><div
        class="plate-bonus"id="plateBonus">12345</div></div>

	<script type="text/javascript">
document.onreadystatechange = function(e)
	{	<%-- 修正 Tool Bar 整合後　CSS問題--%>
	    if (document.readyState === 'interactive')
	    {
	    	var path = window.location.pathname;
	        var newClass = path.match(/[^\/]*[^\d\/][^\/]*/);
	        $('body').addClass("bg-body");
	    }
	};
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/jquery.jscrollpane.min.js"></script>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/game-parent.min.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/mmc/phoenix.MMC.GameStatistics.js?"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/mmc/phoenix.MMC.GameOrder.js?"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/ssc/game-ssc-sub.js"></script>
	<script type="text/javascript" src="${currentContextPath}/gameBet/${lotteryCode}/config" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/mmc/mmc_utils.js?"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/gameActivity/game.mission.base.js?"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/gameActivity/diamondMission.js?"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/mmc/phoenix.Games.MMC.GameBellMethod.js?" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/bet/mmcbet.js?"></script>

	</body>
	
</html>


