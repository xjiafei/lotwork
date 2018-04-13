<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表查询</title>
<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/currentUserReport.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/lotteryBettpyeConfig.js"></script>
<script type="text/javascript">
var baseUrl = '${currentContextPath}';
var staticFileContextPath='${staticFileContextPath}';
</script>
</head>
<body>
	<div class="g_28 g_last">
		<div class="common-article">
			<div class="title">报表查询</div>
			<div class="content">
			<c:if test="${!empty ptStatus}">
				<div class="ui-tab-title tab-title-bg clearfix">
					<ul>
							<li class="current">彩票</li>
							<li><a href="${ptContextPath}/pt/bet/queryordersmanagement">老虎机</a></li>
					</ul>
				</div>
			</c:if>
				<!--start -->
				<form id="J-form"
					action="queryUserReportByComplexCondition"
					method="post">
					<ul class="ui-search search-porxy clearfix" style="position:relative;">
						<li class="name"><label for="name" class="ui-label">用户名：</label>
							<input type="text" class="input" id="accountInput" value="${request.account}"
							name="accountInput"  /><input type="hidden" value="${request.account}" id="account"
							name="account" class="input" /></li>
						<li class="type"><label class="ui-label">彩种：</label> <input
							type="hidden" id="lotteryIdValue" value="${request.lotteryid}"></input>
							<em id="sel"></em></li>
						<li class="type"><label class="ui-label">玩法：</label>
							<input type="hidden" name="betTypeCode" id="betTypeCode" value="${request.betTypeCode}"></input>
							<input type="hidden" name="crowdId" id="crowdId" value="${request.crowdId}"></input>
							<input type="hidden" name="groupId" id="groupId" value="${request.groupId}"></input>
							<input type="hidden" name="setId" id="setId" value="${request.setId}"></input>
							<input type="hidden" name="methodId" id="methodId" value="${request.methodId}"></input>
							<input name="select" name="crowdname" id="crowdname" value="所有玩法" type="text" size="20" readonly="" />
							<div id="method_message" style="top: 21px;  border-top-width: 1px; border-top-style: solid; border-top-color: rgb(127, 157, 185); display: none;">
								<div class="allcrowd">
									<div class="crowdid" id="1">所有玩法</div>
								</div>
								<div class="allcrowd">
									<div class="crowdid" id="2">普通玩法</div>
								</div>
								<div class="allcrowd">
									<div class="crowdid" id="3">超级2000</div>
								</div>
							</div>							
						</li>
						<li class="date"><label for="dateStar" class="ui-label">日期：</label>
							<input type="text" class="input" id="J-date-start" value=""
							name="queryTimeView"> <input type="hidden" id="jdate"
							value="${request.queryTime}" name="queryTime" /></li>
						<li><a id="search"  class="btn btn-important"
							href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
					</ul>
				</form>
				<!--end -->
				<!--start -->
				<div class="alert alert-waring">
					<i></i>
					<div class="txt">
						<h4>没有符合条件的记录，请更改查询条件</h4>
					</div>
				</div>
				<!--end -->
			</div>
		</div>
		<dl class="fund-info-supplement">
				<dt>说明：您可以查询报表管理7天内的记录。</dt>
	    </dl>
	</div>
</body>

</html>