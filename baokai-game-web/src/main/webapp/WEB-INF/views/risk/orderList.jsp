<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>游戏异常审核</title>
<style>
.j-ui-tip {
	background: #FFFFE1;
	border: 1px solid #CCC;
	color: #333;
	font-size: 12px;
}

.j-ui-tip .sj {
	display: none;
}

.ui-form-window li {
	margin-top: 5px;
	margin-bottom: 5px;
}

.pop-window-datepicker {
	z-index: 710;
}
</style>
<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var currentContextPath = '${currentContextPath}';
	var _lotteryId = '${lotteryId }';
	var _issueCode = '${issueCode }';
	function settab(status) {
		location.href = "/gameRisk/queryGameExceptionAuditDetail?lotteryId="
				+ _lotteryId + "&issueCode=" + _issueCode + "&status=" + status;
	}
</script>
</head>
<body>
	<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>&gt;<a href="#">游戏异常审核</a>&gt;游戏异常审核详情
			</div>
		</div>
		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<input type="hidden" id="lotteryId" value="${lotteryId }" /> <input
						type="hidden" id="issueCode" value="${issueCode }" />


					<ul class="ui-form  lottery-info">
						<li><label class="ui-label w-2 big">彩种名称：</label> <span
							class="ui-singleline">${issue.lotteryName }</span> <label
							class="ui-label w-3 big">期号：</label> <span class="ui-singleline">${issue.webIssueCode
								}</span> <label class="ui-label w-3 big">销售时间：</label> <span
							class="ui-singleline">${issue.saleDate }</span> <label
							class="ui-label w-5 big"><a
								href="${currentContextPath}/gameoa/queryOrderList?issueCode=${issueCode }&lotteryId=${issueCode }"
								class="more">去查看本期方案记录&gt;&gt;</a></label></li>
						<li><label class="ui-label w-2 big">理论开奖时间：</label> <span
							class="ui-singleline">${issue.openDrawTime }</span> <label
							class="ui-label w-3 big">开奖号码确认时间：</label> <span
							class="ui-singleline">${issue.factionDrawTime }</span></li>
						<li><label class="ui-label w-2 big">处理状态：</label> <span
							class="ui-singleline  w-5">异常记录 <font color="red">${orderCount}</font>条,已处理
								<font color="#669900">${pass}</font>条
						</span></li>

					</ul>
					<div class="ui-tab">
						<div class="play-select-title clearfix">
							<ul>
								<li <c:if test="${status==2}"> class="current" </c:if>
									onclick="settab(2)">待处理</li>
								<li <c:if test="${status==1}"> class="current" </c:if>
									onclick="settab(1)">已完成</li>
							</ul>
						</div>


						<div id="ui-tab-content">

							<c:if test="${empty detail}">
								<label class="ui-label w-2"></label>
								<c:if test="${status==1}">
									<span class="ui-singleline">本期无已处理方案</span>
								</c:if>
								<c:if test="${status==2}">
									<span class="ui-singleline">本期无待处理方案</span>
								</c:if>
							</c:if>

							<c:if test="${not empty detail}">
								<c:if test="${status==2}">
									<h3 class="ui-title">
									<permission:hasRight moduleName="RISK_AUDIT">
										<input type="button" name="audit" id="audit"
											class="btn btn-small" value="审核通过"></input>
											</permission:hasRight>
									</h3>
								</c:if>
								<table class="table table-info" id="J-table">
									<thead>
										<tr>
											<th class="w-1"><input type="checkbox" name="selectAll"
												id="selectAll"></input>全选</th>
											<th>用户名</th>
											<th>单期奖金(&yen;)</th>
											<th>单期中/投比</th>
											<th>连续中奖期数</th>
											<th>连续中奖注数</th>
											<th>单注最大奖金（&yen;）</th>
											<th>辅助查看内容</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${detail }" var="warnUserOrders">


											<tr>
												<td><input type="checkbox" name="userCheck"
													class="selectUserAll" value=""></input></td>
												<td class="table-tool"><a
													class="ico-control ico-unfold" href="#"></a> <a
													href="${adminContextPath}/admin/user/userdetail?id=${warnUserOrders.userid}" class="link">${warnUserOrders.userName}</a></td>
												<td
													<c:if test="${not empty config.orderwarnMaxwins && config.orderwarnMaxwins!=0 && warnUserOrders.totalWins > config.orderwarnMaxwins }"> class="color-red" </c:if>>
													<fmt:formatNumber value="${warnUserOrders.totalWins/10000}"
														pattern="#,###.##" minFractionDigits="2" />
												</td>
												<td
													<c:if test="${not empty config.orderwarnWinsRatio && config.orderwarnWinsRatio!=0 && warnUserOrders.winsRatio > config.orderwarnWinsRatio}">class="color-red"  </c:if>><fmt:formatNumber
														value="${warnUserOrders.winsRatio/10000}"
														pattern="#,###.##" minFractionDigits="2" /></td>
												<td>${warnUserOrders.continuousWinsIssue}</td>
												<td>${warnUserOrders.continuousWinsTimes}</td>
												<td
													<c:if test="${not empty config.orderwarnMaxslipWins && config.orderwarnMaxslipWins!=0 && warnUserOrders.maxslipWins > config.orderwarnMaxslipWins}">class="color-red" </c:if>><fmt:formatNumber
														value="${warnUserOrders.maxslipWins/10000}"
														pattern="#,###.##" minFractionDigits="2" /></td>
												<td><a
													href="${currentContextPath}/gameoa/queryOrderList?paramCode=${warnUserOrders.userName}">方案记录</a>
													<a
													href="${currentContextPath}/admin/Reporting/index?parma=sv55&UserName=${warnUserOrders.userName}">交易记录</a>
													<permission:hasRight moduleName="RISK_FREEZE">
													<a
													href="${currentContextPath}/admin/user/freezeuser?id=${warnUserOrders.userid}">冻结用户</a>
													</permission:hasRight>
												</td>
											</tr>
											<tr style="display: none;">
												<td></td>
												<td colspan="7">
													<table class="table table-border">
														<thead>
															<tr>
																<th></th>
																<th>方案编号</th>
																<th>投注时间</th>
																<th>投注金额(&yen;)</th>
																<th>奖金(&yen;)</th>
																<th>方案中/投比</th>
																<th>单注最大奖金(&yen;)</th>
																<th>单注最大中/投比</th>
																<th>是否为追号单</th>
																<th>来源</th>
																<th>审核</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${warnUserOrders.warnOrderStrucs }"
																var="riskOrderDetail">
																<tr>
																	<td><input type="checkbox" name="orderCheck"
																		userid="${riskOrders.userid }" class="selectedOrder"
																		value="${riskOrderDetail.id}"></input></td>
																	<td><a
																		href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${riskOrderDetail.orderId }&orderCode=${riskOrderDetail.orderCode }">${riskOrderDetail.orderCode
																			}</a></td>
																	<td>${riskOrderDetail.saleTime }</td>
																	<td><fmt:formatNumber
																			value="${riskOrderDetail.totamount /10000}"
																			pattern="#,###.##" minFractionDigits="2" /></td>
																	<td><fmt:formatNumber
																			value="${riskOrderDetail.countWin/10000 }"
																			pattern="#,###.##" minFractionDigits="2" /></td>
																	<td><fmt:formatNumber
																			value="${riskOrderDetail.winsRatio/10000}"
																			pattern="#,###.##" minFractionDigits="2" /></td>
																	<td><fmt:formatNumber
																			value="${riskOrderDetail.maxslipWins /10000}"
																			pattern="#,###.##" minFractionDigits="2" /></td>
																	<td><fmt:formatNumber
																			value="${riskOrderDetail.slipWinsratio/10000}"
																			pattern="#,###.##" minFractionDigits="2" /></td>
																	<td><c:if test="${riskOrderDetail.parentType==1 }">否</c:if>
																		<c:if test="${riskOrderDetail.parentType==2 }">是</c:if>
																	</td>
																	<td>${riskOrderDetail.channelName}</td>
																	<td><c:if test="${riskOrderDetail.status==0 }">
																			<a class="button-action button-noaudit"
																				name="${riskOrderDetail.id }"
																				href="javascript:void(0);">审核</a>
																			<input type="hidden" class="record-hidden-id"
																				value="1" />
																		</c:if> <c:if test="${riskOrderDetail.status==1 }">
																			<span style="color: #999;">已通过</span>
																		</c:if> <c:if test="${riskOrderDetail.status==2 }">
																			<span style="color: red;">未通过</span>
																		</c:if></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
								<input id="pageNo" name="pageNo" type="hidden"
									value="${page.pageNo}">
								<input id="pageSize" name="pageSize" type="hidden"
									value="${page.pageSize}">
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}"
									pageSize="${page.pageSize}" />
							</c:if>

						</div>
					</div>

					<div style="height: 400px;"></div>
				</div>
			</div>
		</div>
	</div>
	<script id="J-tpl-noaudit" type="text/template">
            审核备注：
   <div><textarea id="disposeMemo"  style="width:400px;height:80px;"></textarea></div>
    </script>


	<script id="J-tpl-puton" type="text/template">
	<ul class="ui-form ui-form-small">
		<li>
			<label for="" class="ui-label">${issue.lotteryName }：</label>
			<span class="ui-singleline">${issue.webIssueCode }期</span>
		</li>
		<li>
			<label for="" class="ui-label">&nbsp;</label>
			<span class="ui-singleline">继续派奖</span>
		</li>
		<li>
			<label for="" class="ui-label">异常处理备注：</label>
			<input id="J-input-memo3" type="text" value="" class="input">
		</li>
	</ul>
</script>


	<script type="text/javascript" 		src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript"
		src="${staticFileContextPath}/static/js/risk/gameExceptionAuditDetail.js"></script>

</body>