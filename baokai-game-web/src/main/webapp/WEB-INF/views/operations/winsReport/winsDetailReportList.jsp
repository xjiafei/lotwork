<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	
	<title>报表管理-玩法盈亏明细</title>
	
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
			<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="#">报表管理</a>>玩法盈亏明细
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><a href="${currentContextPath}/gameoa/queryWinsReport">返回上级</a></h3>
						
						<ul class="ui-form">
							<li>
								<label class="ui-label w-2 big">日期：</label>
								<span class="ui-singleline ">${reportDate}</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<span class="ui-singleline ">${lotteryName}</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_qihao" />：</label>
								<span class="ui-singleline ">${webIssueCode}</span>
							</li>
						</ul>
						
						<form id="J-search-form" action="queryWinsDetailReport" method="post">
						<input id="lotteryid" name="lotteryid" type="hidden" value="${req.lotteryid}">
						<input id="issueCode" name="issueCode" type="hidden" value="${req.issueCode}">
						<input id="sortType" name="sortType" type="hidden" value="${req.sortType}">
						</form>
						
						<table class="table table-info">
							<thead>
								<tr>
									<th>玩法群</th>
									<th class="table-toggle"><a href="#" id="0">玩法<i class="ico-up-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="1">销售金额（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th>返点（<span class="price"><dfn>&yen;</dfn></span>）</th>
									<th class="table-toggle"><a href="#" id="3">返奖总额（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="5">盈亏值（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${reports}" var="report">
								<tr id=>
									<td>${report.gameGroupName}</td>
									<td>${report.gameGroupName}_${report.betMethodName}</td>
									<td><fmt:formatNumber value="${report.totalSales/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalPoints/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalWins/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalProfit/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>
							</c:forEach>
								<tr>
									<td>小结</td>
									<td></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalSalesSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalPointsSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalWinsSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>总计：<fmt:formatNumber value="${winsSum.totalProfitSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/winsReport/winsDetailReportList.js"></script>
</body>