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
				<div class="ui-tab-title tab-title-bg clearfix">
					<ul>
							<li class="current">彩票</li>
							<c:if test="${!empty ptStatus}">
								<li><a href="${ptContextPath}/pt/bet/queryordersmanagement">老虎机</a></li>
							</c:if>

					</ul>
				</div>
				<!--start -->
				<form id="J-form"
					action="queryUserReportByComplexCondition"
					method="post">
					<ul class="ui-search search-porxy clearfix" style="position:relative;" >
						<li class="name"><label for="name" class="ui-label">用户名：</label>
							<input type="text" class="input" id="accountInput" value="${request.account}"
							name="accountInput"  /><input type="hidden" id="account"
							value="${request.account}" name="account" /></li>
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
						<li><a id="search" type="submit" class="btn btn-important"
							href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
					</ul>
				</form>
				<!--end -->
				<!--start -->
				<table class="table table-info">
					<thead>
						<tr>
							<th>用户名</th>
							<th>用户组</th>
							<th>总代购费</th>
							<th>总返点</th>
							<th>实际总代购费</th>
							<th>中奖金额</th>
							<th>活动礼金</th>
							<th>总结算</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${cucrr.userCenterReportStrucs.account}</td>
							<td><c:if test="${cucrr.userCenterReportStrucs.userLvl==0}">总代</c:if>
								<c:if test="${cucrr.userCenterReportStrucs.userLvl==-1}">用户</c:if>
								<c:if
									test="${cucrr.userCenterReportStrucs.userLvl>0}">代理</c:if></td>
							<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${cucrr.userCenterReportStrucs.totalSubuserSaleroom/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${cucrr.userCenterReportStrucs.totalSubuserPoint/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${cucrr.userCenterReportStrucs.actualTotalSubuserSaleroom/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${cucrr.userCenterReportStrucs.totalSubuserWins/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${cucrr.userCenterReportStrucs.activityGifts/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><span class="price"><dfn>&yen;</dfn></span>
							<c:if test="${(cucrr.userCenterReportStrucs.totalSubuserWins+cucrr.userCenterReportStrucs.activityGifts-cucrr.userCenterReportStrucs.actualTotalSubuserSaleroom)/10000.00 >0}">+</c:if><fmt:formatNumber value="${(cucrr.userCenterReportStrucs.totalSubuserWins+cucrr.userCenterReportStrucs.activityGifts-cucrr.userCenterReportStrucs.actualTotalSubuserSaleroom)/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></td>
							<td><c:if test="${cucrr.userCenterReportStrucs.userLvl!=-1}">
							<input type="hidden" id="curUserId" value="${cucrr.userCenterReportStrucs.userId}" ></input>
									<a  id="curClick" href="#">查看下级</a>
								</c:if>
								<c:if test="${cucrr.userCenterReportStrucs.userLvl==-1}">-</c:if></td>
						</tr>
					</tbody>
				</table>
				<!--end -->
			</div>
		</div>
		<dl class="fund-info-supplement">
				<dt>说明：您可以查询报表管理7天内的记录。</dt>
	    </dl>
	</div>
</body>

</html>