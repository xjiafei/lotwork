<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="permission"uri="/tag-permission"%>

<html>
<head>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/risk/singleIssueIncomeAndExpenseReport.js"></script>
	<title>游戏审核管理-统计报表-单期收支报表</title>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏审核管理</a>><a href="#">统计报表</a>>单期收支报表
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">单期收支报表</h3>
						
						<form id="J-search-form" action="querySingleIssueIncomeAndExpenseReport" method="post">
						
						<input type="hidden" id="lotteryid" name="lotteryid" value="${req.lotteryid}"/>
						<input type="hidden" id="selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
						<input type="hidden" id="startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
						<input type="hidden" id="endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
						
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label class="ui-label">彩种名称：</label>
								<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value="">全部彩种</option>
										<option value="99101">重庆时时彩</option>
										<option value="99106">宝开时时彩</option>
										<option value="99102">江西时时彩</option>
										<option value="99104">新疆时时彩</option>
										<option value="99103">天津时时彩</option>
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
										<option value="99111">宝开一分彩</option>
										<option value="99114">腾讯分分彩</option>
										<option value="99112">秒开时时彩</option>
										<option value="99501">江苏快三</option>
										<option value="99502">安徽快三</option>
										<option value="99601">江苏骰宝</option>
										<option value="99602">高频骰宝(娱乐厅)</option>
										<option value="99603">高频骰宝(至尊厅)</option>
								</select>
							</li>
						
							<li>
								<label class="ui-label">奖期：</label>
								<input type="hidden" id="webIssueCodeValue" value="${req.webIssueCode}">
								<input type="text" class="input" id="webIssueCode" name="webIssueCode" value="请输入奖期">
							</li>
							
							<li class="time">
								<label class="ui-label">时间：</label>
								<span id="time_today" class="ico-tab  ico-tab-current"  pro="1">今天</span>
								<span id="time_three" class="ico-tab"  pro="3">三天</span>
								<span id="time_seven" class="ico-tab"  pro="7">一周</span>
								<span id="time_all" class="ico-tab"  pro="-1">全部</span>
								<input type="hidden" name="time" id="time" value="${time}" /> 
							</li>							
							<li>
								<label class="ui-label">每页记录数：</label>
								<input id="pageCount" name="pageCount" type="text" value="${page.pageSize}" class="input input-num w-1" />
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
						
					<c:if test="${!empty reports}">
					
						<table class="table table-info">
							<thead>
								<tr>
									<th>彩种名称</th>
									<th>期号</th>
									<th>销售时间段</th>
									<th>实际投注</th>
									<th>游戏撤销投注</th>
									<th>撤单手续费</th>
									<th>待派奖金</th>
									<th>实派奖金</th>
									<th>实派返点</th>
									<th>目前营收</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${reports}" var="report">
								<tr>
									<td>${report.lotteryName}</td>
									<td>${report.webIssueCode}</td>
									<td>${report.saleTimePeriod}</td>
									<td><fmt:formatNumber value="${report.actualSoldAmount}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.canceledAmount}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.cancellationsFee}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.toDistributeBonuses}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.distributedBonuses}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.distributedRetPoint}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.revenue}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
					</c:if>
					</div>
				</div>
			</div>

</body>