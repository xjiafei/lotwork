<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>

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
				<div class="col-main" style="overflow-x: auto;">
					<div class="main">
						<h3 class="ui-title">单期盈亏表</h3>
						
						<form id="J-search-form" action="queryGameRiskIncomeReport" method="post">
						
						<input type="hidden" id="lotteryId" name="lotteryId" value="${request.lotteryId}"/>
						<input type="hidden" id="issueCode" name="issueCode" value="${request.issueCode}"/>
						<input type="hidden" id="dateType" name="dateType" value="${request.dateType}"/>						
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
							<li class="time">
								<label for="" class="ui-label">奖期：</label>
								<input id="search-issueCode" type="text" value="" class="input">
							</li>
														
							<li class="search-time time">
								<label for="" class="ui-label">时间：</label>
								<a id="date1" class="date current" href="javascript:void(0)"  value="1">今天</a>
								<a id="date3" class="date" value="3" >3天</a>
								<a id="date7" class="date"  href="javascript:void(0)" value="7">一周</a>
								<a id="date"  class="date" href="javascript:void(0)" value="">全部</a>
							</li>

							<li>
								<label class="ui-label">每页记录数：</label>
								<input id="pageCount" name="pageCount" type="text" value="${page.pageSize}" class="input input-num w-1" />
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
								<!--<a id="J-button-export" class="btn" href="javascript:void(0);">导出报表<b class="btn-inner"></b></a>  -->
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
									<th>撤销投注</th>
									<th>撒单手续费</th>
									<th>待派奖金</th>
									<th>实派奖金</th>
									<th>实派返点</th>
									<th>目前营收<div class="color-gray">投注金额+手续费-实派（奖金+返点）</div></th>
								</tr>
							</thead>
							<tbody>
							
							<c:forEach items="${reports}" var="report">
								<tr id="${report.lotteryId }_${report.issueCode}">									
									<td>${report.lotteryName}</td>
									<td><a herf="${currentContextPath}+'/gameoa/queryWinsDetailReport?lotteryid='+${report.lotteryId }+'&issueCode='+${report.issueCode }">
										${report.webIssueCode}</a>
									</td>
									<td><fmt:formatDate  value="${report.saleTimeBegin}" 
										type="both" pattern="HH:mm:ss" />-
										<fmt:formatDate  value="${report.saleTimeEnd}" 
										type="both" pattern="HH:mm:ss" />
									</td>
									<td><fmt:formatNumber value="${report.totalSales/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalCancelOrder/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalCancel/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${(report.totalWins-report.totalActualAward)/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalActualAward/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${report.totalPoints/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>						
									<td><fmt:formatNumber value="${report.totalProfit/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>								
							</c:forEach>
							
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						</c:if>
					</div>
				</div>
			</div>

    <script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/risk/report/incomeReportList.js"></script>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	
	.pop-window-datepicker {z-index:710;}
	
	.search-time a {display:inline-block;border:1px solid #CCC;padding:5px 10px;color:#999;}
	.search-time a:hover { text-decoration:none;}
	.search-time a.current {border-color:#08AE8E;font-weight:bold;color:#08AE8E}
	
	</style>
</body>

