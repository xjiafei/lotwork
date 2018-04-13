<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>投注查询</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/userCenter/managementBet.js"></script>
<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath = '${staticFileContextPath}';
</script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="g_28 g_last">
		<div class="common-article">
			<div class="title">投注查询</div>
			<div class="content">

				<!-- start -->
				<form action="queryOrdersManagement" method="post" id="J-form">
					<input id="pageNo" type="hidden" name="pageNo" value="${page.pageNo }"> <input
						id="pageSize" type="hidden" name="pageSize" value="10">
					<ul class="ui-search search-proxy clearfix">

						<li class="date"><label class="ui-label" for="dateStar">日期：</label>
							<input type="hidden" id="startTime" name="startTime"
							value="${request.startTime}"></input> <input type="hidden"
							id="endTime" name="endTime" value="${request.endTime}"></input> <input
							type="text" class="input" id="J-date-start" value=""
							name="dateStar"> - <input type="text" class="input"
							value="" id="J-date-end" name="dateEnd"></li>
						<li class="status"><label class="ui-label">状态：</label> <input
							type="hidden" id="statusValue" value="${request.status}" /> <select
							class="ui-select" name="status" id="status">
								<option value="0">全部状态</option>
								<option value="2">中奖</option>
								<option value="3">未中奖</option>
								<option value="1">等待开奖</option>
								<option value="4">已撤销</option>
						</select></li>
						<li class="type"><label class="ui-label">彩种：</label> <input
							type="hidden" id="lotteryIdValue" value="${request.lotteryId}"></input>
							<em id="sel"></em></li>
						<li class="chase"><label class="ui-label">追号：</label> <input
							type="hidden" id="parentTypeValue" value="${request.parentType}"></input>
							<select class="ui-select" name="parentType" id="parentType">
								<option value="0">全部</option>
								<option value="2">是</option>
								<option value="1">否</option>
						</select></li>
						<li class="name"><label class="ui-label" for="name">用户名：</label>
							<input type="text" class="input" value="${request.account}" disabled>
							<input type="hidden" class="input" name="account" id="name" value="${request.account}">
						</li>
						<li class="lower"><label class="ui-label"><input
								id="containSub" type="checkbox" class="checkbox">包含下级</label><input
							type="hidden" name="containSub" id="containSubValue"
							value="${request.containSub}" /></li>

						<li class="number"><label class="ui-label" for="number">方案编号：</label>
							<input type="hidden" id="orderCodeValue"
							value="${request.orderCode}"> <input type="text"
							class="input" id="orderCode" name="orderCode" value="如：ABC7779">
							<input type="hidden" id="userId" name="userId" value="${userId }">

						</li>
						<li class="button"><a href="javascript:void(0);"
							class="btn btn-important" id="J-button-submit">查询<b
								class="btn-inner"></b></a></li>
					</ul>
				</form>
				<!-- end -->
				<!-- start -->
				<c:if test="${!empty orders}">
					<table class="table table-info">
						<thead>
							<tr>
								<th>用户名</th>
								<th>彩种</th>
								<th>投注金额</th>
								<th>状态</th>
								<th>追号</th>
								<th>投单时间</th>
								<th>详情</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orders}" var="orderStruc">
								<tr>
									<td>${orderStruc.account}</td>
									<td>${orderStruc.lotteryName}</td>
									<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber
												value="${orderStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2" /></span></td>
									<td><c:if test="${orderStruc.status!=2}"><c:if test="${orderStruc.status==7}">存在异常</c:if><c:if test="${orderStruc.status!=7}">${orderStruc.statusName}</c:if></c:if>
										<c:if test="${orderStruc.status==2}">
											<span class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber
													value="${(orderStruc.totwin+orderStruc.totDiamondWin)/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>
										</c:if></td>
									<td><c:if test="${orderStruc.parentType==2}">是</c:if>
										<c:if test="${orderStruc.parentType!=2}">否</c:if></td>
									<td>${orderStruc.formatSaleTime}</td>
									<td><a
										href="${currentContextPath}/gameUserCenter/queryOrderDetailManagement?orderId=${orderStruc.orderId}&userId=${userId}"
										target="_blank">查看</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- end -->
					<!-- start -->
					<jsp:include page="page.jsp"></jsp:include>
				</c:if>
				<!-- end -->
				<!-- start -->
			</div>
		</div>
        <dl class="fund-info-supplement">
				<dt>说明：低频投注记录保留50天，其他彩种投注记录保留7天。</dt>
	    </dl>
	</div>
</body>

</html>