<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>投注记录</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/bettingRecords.js"></script>
	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	
	</script>
</head>
<body>	
	<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">投注记录</div>
				<div class="content">
					<div class="ui-tab-title tab-title-bg clearfix">
						<ul>
							<li class="current">彩票</li>
							<c:if test="${!empty ptStatus}">
								<li><a href="${ptContextPath}/pt/bet/list">老虎机</a></li>
							</c:if>
						</ul>
					</div>
				<form action="queryOrders" method="post" id="J-form">
					<input type="hidden" id="J-data-now" value="" />				
					<input type="hidden" id="J-date-bound" value="" />
					<input id="pageNo" type="hidden" name="pageNo" value="1">
					<input id="pageSize" type="hidden" name="pageSize" value="10">
					
					<ul class="ui-search clearfix">
						<li class="search-more" id="divExpansion">展开∨</li>
						<li class="type">
							<label  class="ui-label">彩种：</label>
							 <input
							type="hidden" id="lotteryIdValue" value="${request.lotteryId}"></input>
							<em id="sel"></em>
						</li>
						<li class="time">
							<label class="ui-label">时间：</label>
							<span id="time_today" class="ico-tab "  pro="1">今天</span>
							<span id="time_three" class="ico-tab "  pro="3">三天</span>
							<span id="time_seven" class="ico-tab  ico-tab-current"  pro="7">七天</span>
							<input type="hidden" name="time" id="time" value="${time}" /> 
						</li>
						<li class="state">
							<label class="ui-label">状态：</label>
							<span class="ico-tab ico-tab-current"  pro="0">全部</span>
							<span class="ico-tab"   pro="2">已中奖</span>
							<span class="ico-tab"  pro="3">未中奖</span>
							<span class="ico-tab"  pro="1">等待开奖</span>
							<input type="hidden" name="status" id="status" value="${request.status}" /> 
						</li>
					</ul>
					<ul class="ui-search ui-search-more clearfix" id="divDetailed">
						<li class="issues">
							<label class="ui-label">期号：</label>
							 <input type="hidden" id="webIssueCodeValue" value="${request.webIssueCode}"></input>
							<select class="ui-select" name="webIssueCode" id="webIssueCode">
								<option value="130718053">所有奖期</option>
								<option value="130718053">130718053</option>
								<option value="130718054">130718054</option>
								<option value="130718055">130718055</option>
							</select>
						</li>
						<li class="date">
							<label class="ui-label" for="dateStar">起始日期：</label>
							<input type="hidden" id="startTime" name="startTime" value="${request.startTime}"></input>
							<input type="hidden" id="endTime" name="endTime"  value="${request.endTime}"></input>
							<input type="text" class="input" id="J-date-start" value="" name="dateStar"> - <input type="text" class="input"  value="" id="J-date-end" name="dateEnd">
						</li>
						<li class="number">
							<label class="ui-label" for="number">方案编号：</label>
							<input type="hidden" id="orderCodeValue" value="${request.orderCode}">
							<input type="text" class="input" id="orderCode" name="orderCode" value="如：ABC7779">
						</li>
						<li class="btn-search">
							<a href="javascript:void(0);" class="btn btn-important" id="J-button-submit">确 定<b class="btn-inner"></b></a>
							<a href="javascript:void(0);" class="btn btn-link light" id="restoreDefaults">恢复默认项</a>
						</li>
					</ul>
					</form>
				<div class="alert alert-waring">
					<i></i>
					<div class="txt">
						<h4>没有搜索到游戏记录！</h4>
					</div>
				</div>
			</div>

		</div>
		  <dl class="fund-info-supplement">
			<dt>说明：低频投注记录保留50天，其他彩种投注记录保留7天。</dt>
	    </dl>
	</div>

</body>

</html>