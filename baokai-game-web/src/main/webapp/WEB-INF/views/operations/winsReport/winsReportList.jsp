<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	
	<title>报表管理-单期盈亏表-高频彩种</title>
	
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="#">报表管理</a>>单期盈亏表
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">单期盈亏表</h3>
						
						<form id="J-search-form" action="queryWinsReport" method="post">
						
						<input type="hidden" id="lotteryid" name="lotteryid" value="${req.lotteryid}"/>
						<input type="hidden" id="selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
						<input type="hidden" id="startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
						<input type="hidden" id="endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
						
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
						<input id="sortType" name="sortType" type="hidden" value="${req.sortType}">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value=""><spring:message code="gameCenter_quanbucaizhong" /></option>
										<option value="99101">重庆时时彩</option>
									<option value="99106">宝开时时彩</option>
									<option value="99102">江西时时彩</option>
									<option value="99103">新疆时时彩</option>
									<option value="99104">天津时时彩</option>
									<option value="99107">上海时时乐</option>
									<option value="99105">黑龙江时时彩</option>
									<option value="99301">山东11选5</option>
									<option value="99302">江西11选5</option>
									<option value="99307">江苏11选5</option>
									<option value="99303">广东11选5</option>
									<option value="99304">重庆11选5</option>
									<option value="99305">宝开11选5</option>
									<option value="99306">秒开11选5</option>	
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>	
									<option value="99701">六合彩</option>										
									<option value="99111">宝开一分彩</option>
									<option value="99114">腾讯分分彩</option>
									<option value="99112">秒开时时彩</option>
							<!--  	<option value="99113">超级2000秒秒彩（APP版）</option>	-->	
									<option value="99501">江苏快三</option>
									<option value="99502">安徽快三</option>
									<option value="99601">江苏骰宝</option>
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>
								</select>
							</li>
							<li class="time">
							<input type="hidden" name="selectTimeMode" id="selectTimeMode" value="${req.selectTimeMode}" />
								<label for="" class="ui-label"><spring:message code="gameCenter_shijian" />：</label>
								<select class="ui-select" id="J-select-time">
									<option value="1" selected="selected"><spring:message code="gameCenter_jintian" /></option>
									<option value="2">两天</option>
									<option value="3"><spring:message code="gameCenter_santian" /></option>
									<option value="4">四天</option>
									<option value="5">五天</option>
									<option value="6">六天</option>
									<option value="7">七天</option>
									<option value="-1"><spring:message code="gameCenter_quanbu" /></option>
									<option value="0">自定义</option>
									 
								</select>
								<input id="J-time-start" type="text" value="" class="input"> - <input id="J-time-end" type="text" value="" class="input">
							</li>
							<li>
								<label class="ui-label">每页记录数：</label>
								<input id="pageCount" name="pageCount" type="text" value="${page.pageSize}" class="input input-num w-1" />
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
								<a id="J-button-export" class="btn" href="javascript:void(0);">导出报表<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
						
<%-- 					<c:if test="${!empty reports}"> --%>
						<c:if test="${!empty winsSum}">
						<div class="prompt">
							<div>从   ${winsSum.startTime} — ${winsSum.endTime}  ，  ${winsSum.lotteryName}  共销售  ${winsSum.totalIssueCount}  期</div>
							<div>总计：销量   <span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${winsSum.totalSalesSum/10000}" pattern="#,###.##"  minFractionDigits="4"/>  ，返点  <span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${winsSum.totalPointsSum/10000}" pattern="#,###.##"  minFractionDigits="4"/> 返奖 <span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${winsSum.totalWinsSum/10000}" pattern="#,###.##"  minFractionDigits="4"/>  总计盈亏值  <span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${winsSum.totalProfitSum/10000}" pattern="#,###.##"  minFractionDigits="4"/></div>
						</div>
						</c:if>
						<table class="table table-info">
							<thead>
								<tr>
									<th>日期</th>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_qihao" /></th>
									<th class="table-toggle" ><a href="#" id="1">销售总额（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current" ></i><i class="ico-down-current" ></i></a></th>
									<th>撒单手续费（<span class="price"><dfn>&yen;</dfn></span>）</th>
									<th>返点总额（<span class="price"><dfn>&yen;</dfn></span>）</th>
									<th class="table-toggle"><a href="#" id="3">返奖总额（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="5">盈亏值（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${reports}" var="report">
								<tr >
									<td>${report.reportDate}</td>
									<td><a id="${report.lotteryid }_${report.issueCode}" href="javascript:void(0);">${report.lotteryName}</a></td>
									<td>${report.webIssueCode}</td>
									<td><fmt:formatNumber value="${report.totalSales/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalCancel/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalPoints/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalWins/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalProfit/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>
								
							</c:forEach>
								<tr>
									<td>小结</td>
									<td></td>
									<td></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalSalesSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalCancelSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalPointsSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalWinsSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalProfitSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>

							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
<%-- 					</c:if> --%>
					<permission:hasRight moduleName="GAME_REPORT_WINS_DETAIL">
						<script>
							var gameWinsReportWinsDetailHasRight = true;
						</script>
					</permission:hasRight>
					</div>
				</div>
			</div>
	
	
<!-- 	<div id="J-form-hidden" style="display:none;">
	
		<input type="hidden" name="lotteryid" id="J-hidden-lotteryId" value="99101" />
		<input type="hidden" name="lotteryName" id="J-hidden-lotteryName" value="重庆时时彩" />
		<input type="hidden" name="userName" id="J-hidden-userName" value="哇哈哈" />
		<input type="hidden" name="days" id="J-hidden-days" value="3" />
		<input type="hidden" name="startTime" id="J-hidden-startTime" value="2013-10-14 17:11:22" />
		<input type="hidden" name="endTime" id="J-hidden-endTime" value="2013-10-16 17:11:22" />
						
		<input type="hidden" name="sortTime" id="J-hidden-sortTotal" value="1" />
		<input type="hidden" name="sortLotteryMoney" id="J-hidden-sortReturn" value="1" />
		<input type="hidden" name="sortMoney" id="J-hidden-sortWin" value="1" />
						
	</div> -->

<script type="text/template" id="J-tpl-export">
	<form action=exportWinsReport target="_blank" id="J-download-form">
		<input type="hidden" name="lotteryid" id="J-hidden-lotteryId" value="${req.lotteryid}" />
		<input type="hidden" id="J-hidden-startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
		<input type="hidden" id="J-hidden-endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
		<input type="hidden" name="sortType" id="J-hidden-sortType" value="${req.sortType}" />
<ul class="ui-form ui-form-small">
<li><span class="ui-singleline">为您导出符合以下条件的数据</span></li>
	<li data-data="<#=lotteryName#>">
		<label class="ui-label w-2" for=""><spring:message code="gameCenter_caizhongmingcheng" />：</label>
		<span id="Span-lotteryName" class="ui-singleline"></span>
	</li>
	<li data-data="<#=startTime#><#=endTime#>">
		<label class="ui-label w-2" for="">日期：</label>
		<span id="Span-startTime" class="ui-singleline"></span> - <span id="Span-endTime" class="ui-singleline"></span>
	</li>

</ul>
<ul class="ui-form ui-form-small">
<li style="text-align:center;"><span class="ui-singleline">请选择导出报表的格式</span></li>
<li class="radio-list" style="text-align:center;">
	<label class="label" for="J-radio-doc-type-1"><input checked="checked" id="J-radio-doc-type-1" name="radio-doc-type" type="radio" class="radio radio-doc-type" value="1">excel</label>
	<label class="label" for="J-radio-doc-type-2"><input id="J-radio-doc-type-2" name="radio-doc-type" type="radio" class="radio radio-doc-type" value="2">txt</label>
</li>
</ul>
</form>
</script>

<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/winsReport/winsReportList.js"></script>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
</body>