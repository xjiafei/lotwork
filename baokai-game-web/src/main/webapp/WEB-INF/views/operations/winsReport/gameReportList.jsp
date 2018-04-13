<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="permission"uri="/tag-permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	
	<title><spring:message code="gameCenter_baobiaoguanli" />-游戏明细报表</title>
	
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="#"><spring:message code="gameCenter_baobiaoguanli" /></a>><spring:message code="gameCenter_danqiyingkuibiao" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><spring:message code="gameCenter_baobiaoguanli" /></h3>
						
						<form id="J-search-form" action="queryGameReport" method="post">
						
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
						<li>
						<a id="J-button-export" class="btn" href="javascript:void(0);">导出报表<b class="btn-inner"></b></a>
						</li>
					</form>
						
					<c:if test="${!empty reportList}">
					
						<table class="table table-info">
							<thead>
								<tr>
									<th>交易流水号</th>
									<th><spring:message code="gameCenter_yonghuming" /></th>
									<th>交易时间</th>
									<th>游戏类型</th>
									<th><spring:message code="gameCenter_zhuihaobianhao" /></th>
									<th><spring:message code="gameCenter_fanganbianhao" /></th>
									<th>摘要</th>
									<th>金额</th>
									<th>游戏</th>
									<th><spring:message code="gameCenter_zhuangtai" /></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${reportList}" var="report">
								<tr>
									<td>${report.tid}</td>
									<td>${report.userName}</td>
									<td><fmt:formatDate  value="${report.tradeDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><c:if test="${report.gameType=='Z' }">追号单</c:if>
										<c:if test="${report.gameType=='D' }">投注单</c:if>
									 </td>
									<td>${report.planCode }</td>
									<td>${report.orderCode }</td>
									<td>${report.reson }</td>
									<td><fmt:formatNumber value="${report.amonut/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>${report.lotteryName }</td>
									<td><c:if test="${report.status==0 }">处理中</c:if>
										<c:if test="${report.status==1 }">已完成</c:if>
										<c:if test="${report.status==2 }">已撤销</c:if>
									</td>
								</tr>
								
							</c:forEach>

							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
					</c:if>
					</div>
				</div>
			</div>
<script type="text/template" id="J-tpl-export">
	<form action=exportGameReport target="_blank" id="J-download-form">
		
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
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/winsReport/gameReportList.js"></script>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
</body>