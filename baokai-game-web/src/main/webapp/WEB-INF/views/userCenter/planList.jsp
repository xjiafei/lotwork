<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="pg" uri="/tag-page"%>

<html>
<head>
	<title>追号记录</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/planList.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/removeIssue/removeIssueforOrder.js" ></script>
</head>
<body>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">追号记录</div>
				<div class="content">
					<form action="queryPlans" method="post" id="J-form">
					<input type="hidden" id="J-data-now" value="" />				
					<input type="hidden" id="J-date-bound" value="" />
					<input id="pageNo" type="hidden" name="pageNo" value="${page.pageNo }">
					<input id="pageSize" type="hidden" name="pageSize" value="10">
					
					<ul class="ui-search clearfix">
						<li class="search-more" id="divExpansion">展开∨</li>
						<li class="type">
							<label  class="ui-label">彩种：</label>
							<select class="ui-select" name="lotteryId" id="lotteryId">
									<option value="0">全部彩种</option>
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
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>	
									<option value="99111">宝开一分彩</option>
									<option value="99114">腾讯分分彩</option>
									<option value="99501">江苏快三</option>
									<option value="99502">安徽快三</option>
									<option value="99601">江苏骰宝</option>
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>
		                    </select>	
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
							<span class="ico-tab ico-tab-current"  pro="-1">全部</span>
							<span class="ico-tab"   pro="0">未开始</span>
							<span class="ico-tab"  pro="1">进行中</span>
							<span class="ico-tab"  pro="2">已结束</span>
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
							<label class="ui-label" for="number">追号编号：</label>
							<input type="hidden" id="planCodeValue" value="${request.planCode}">
							<input type="text" class="input" id="planCode" name="planCode" value="如：ABC77779">
						</li>
						<li class="btn-search">
							<a href="javascript:void(0);" class="btn btn-important" id="J-button-submit">确 定<b class="btn-inner"></b></a>
							<a href="javascript:void(0);" class="btn btn-link light" id="restoreDefaults">恢复默认项</a>
						</li>
					</ul>
					</form>
					
					<c:if test="${!empty plans}">
					<table class="table table-info">
						<thead>
							<tr>
								<th>追号编号</th>
								<th>彩种/玩法</th>
								<th>起始期号</th>
								<th>已追/总期数</th>
								<th>已投/总金额</th>
								<th>状态</th>
								<th>追号时间</th>
								<th>操作项</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${plans}" var="plan">
								<tr>
									<td>${plan.planCode}</td>
									<td>
									<c:forEach items="${orderId}" var="orderinfo">
										<c:if test="${plan.planid==orderinfo.orderId}">
											<a title="${orderinfo.playName}" href="#"  style = "text-decoration:underline;" >${plan.lotteryName}</a>
										</c:if>
									</c:forEach>
									</td>
									
									<td>${plan.startWebIssueCode}</td>
									<td>${plan.finishIssue} /${plan.totalIssue}</td>
									<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plan.usedAmount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span> /<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plan.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></td>
									<td>
										<c:choose>
										       <c:when test="${plan.status == 0}">
										              未开始
										       </c:when>
										       <c:when test="${plan.status == 1}">
										              进行中
										       </c:when>
										       <c:when test="${plan.status == 2}">
										              已结束
										       </c:when>
										       <c:when test="${plan.status == 3}">
										              已终止
										       </c:when>
										       <c:when test="${plan.status == 4}">
										   	处理中
										       </c:when>
										</c:choose>
									</td>
									<td>
										${plan.saleTime}
									</td>
									
									<td><a href="${currentContextPath}/gameUserCenter/queryPlanDetail?planid=${plan.planid}" target="_black">查看</a></td>
								</tr>
							</c:forEach>
						
						</tbody>
					</table>
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
					</c:if>
					
					<c:if test="${empty plans}">
						<div class="alert alert-waring">
							<i></i>
							<div class="txt">
								<h4>没有搜索到游戏记录！</h4>
							</div>
						</div>
					</c:if>
				</div>
			</div>
             <dl class="fund-info-supplement">
			<dt>说明：低频追号记录保留50天，其他彩种追号记录保留7天。</dt>
	    </dl>
		</div>

</body>


</html>