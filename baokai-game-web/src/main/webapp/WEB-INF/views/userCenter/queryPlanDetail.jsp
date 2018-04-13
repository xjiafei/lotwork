<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>追号详情</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/queryPlanDetailDatetimePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/queryPlanDetail.js"></script>
	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	</script>
</head>
<body>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">追号详情</div>
				<div class="content">
					<div class="chas-detail">
						<table class="table">
							<tbody>
								<tr>
									<td rowspan="3" class="table-title">${plansStruc.lotteryName}</td>
									<input id="planId" name="planId" type="hidden" value="${plansStruc.planid}">
									<input id="lotteryid" name="lotteryid" type="hidden" value="${plansStruc.lotteryid}">
									
									<input id="issueCode" type="hidden">
									<td>起始期号：${plansStruc.startWebIssueCode}</td>
									<td colspan="2">追号时间：${plansStruc.saleTime}</td>
								</tr>
								<tr>
									<td>进度：已追${plansStruc.finishIssue}期/总${plansStruc.totalIssue}期</td>
									<td>已追号金额：<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansStruc.usedAmount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>元</td>
									<td>追号方案金额：<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>元</td>
								</tr>
								<tr>
									<td>终止追号条件：
										<c:choose>
										       <c:when test="${plansStruc.stopMode == 0}">
										              不停止
										       </c:when>
										       <c:when test="${plansStruc.stopMode == 1}">
										              按累计盈利停止
										       </c:when>
										       <c:when test="${plansStruc.stopMode == 2}">
										              中奖即停
										       </c:when>
										</c:choose>
									</td>
									<td>已获奖金：<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansStruc.totalWin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>元</td>
									<td>追号编号：${plansStruc.planCode}</td>
								</tr>
							</tbody>
						</table>
						<table class="table">
							<tbody>
								<tr>
									<td class="table-title">追号方案</td>
									<td>
										<div class="chas-detail-table">
											<table class="table">
												<thead>
													<tr>
														<th>玩法</th>
														<th>投注内容</th>
														<th>注数</th>
														<c:if test="${plansStruc.lotteryid!=99601&&plansStruc.lotteryid!=99602&&plansStruc.lotteryid!=99603}">
														<th>倍数</th>
														<th>模式</th>
														</c:if>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${planSlipsStrucs}" var="slip">
														<tr>
														<td>${slip.gamePlayName}</td>
														<td><div style="word-break: break-all; word-wrap:break-word;width:150px;margin:0 auto">${slip.betDetail}</div></td>
														<td>${slip.totbets}注</td>
														<c:if test="${plansStruc.lotteryid!=99601&&plansStruc.lotteryid!=99602&&plansStruc.lotteryid!=99603}">
															<td>${slip.multiple}倍</td>
															<td>
																<c:choose>
																       <c:when test="${slip.moneyMode == 1}">
																              元
																       </c:when>
																       <c:when test="${slip.moneyMode == 2}">
																              角
																       </c:when>
																	   <c:when test="${slip.moneyMode == 3}">
																              分
																       </c:when>
																</c:choose>
															</td>
														</c:if>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</td>
									<td class="vertical-bottom">
										<c:choose>
										       <c:when test="${plansStruc.canStop}">
										              <a href="javascript:void(0);" class="btn" id="revSscheme">终止追号<b class="btn-inner"></b></a>
										       </c:when>
										</c:choose>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<table class="table table-info">
						<thead>
							<tr>
								<th></th>
								<th>期号</th>
								<c:if test="${plansStruc.lotteryid!=99601&&plansStruc.lotteryid!=99602&&plansStruc.lotteryid!=99603}">
								<th>追号倍数</th>
								</c:if>
								<th>投注金额</th>
								<th>当期开奖号码</th>
								<th>状态</th>
								<th>奖金</th>
								<th>操作项</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${plansFutures}" var="plansFuture">
							<%-- 	<c:choose> --%>
									<%-- <c:when test="${plansFuture['class'].name == 'com.winterframework.firefrog.game.web.dto.OrdersStruc'}"> --%>
										<tr>
											<td></td>
											<td>${plansFuture.webIssueCode}</td>
											<c:if test="${plansStruc.lotteryid!=99601&&plansStruc.lotteryid!=99602&&plansStruc.lotteryid!=99603}">
											<td>${plansFuture.mutiple}倍</td>
											</c:if>
											<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansFuture.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></td>
											<td>${plansFuture.numberRecord}</td>
											<td>
											<c:choose>
												<c:when test="${plansFuture.status == 1}">
												等待开奖
												</c:when>
												<c:when test="${plansFuture.status == 2}">
												已派奖
												</c:when>
												<c:when test="${plansFuture.status == 3}">
												未中奖
												</c:when>
												<c:when test="${plansFuture.status == 4}">
												撤销
												</c:when>
												<c:when test="${plansFuture.status == 5}">
												处理中
												</c:when>
												<c:when test="${plansFuture.status == 6}">
												归档
												</c:when>
												<c:when test="${plansFuture.status == 7}">
												您的方案可能存在问题，请联系客服
												</c:when>
												<c:otherwise>
												未开始
												</c:otherwise>
											</c:choose>
											</td>
											<td>
											<c:if test="${plansFuture.totwin != null && plansFuture.status == 2}">
											<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansFuture.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>
											</c:if>
											</td>
											<td>
											<c:choose>
												<c:when test="${plansFuture.status == 1&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 2&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 3&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 4&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 5&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 6}">
												归档
												</c:when>
												<c:when test="${plansFuture.status == 7&&plansFuture.isOrder}">
												<a href="${currentContextPath}/gameUserCenter/queryOrderDetail?orderId=${plansFuture.planDetailsId}" target="_blank">查看</a>
												</c:when>
												<c:otherwise>
												<c:if test="${plansFuture.status<2&&plansFuture.canCancel}">
												<a class="cancelPlanFuture" href="javascript:void(0);" name="${plansFuture.issueCode}">撤销</a>
												</c:if>
												</c:otherwise>
											</c:choose>
											
											</td>
										</tr>
									<%-- </c:when> --%>
									<%-- <c:otherwise>
										<tr>
											<td></td>
											<td>${plansFuture.webIssueCode}</td>
											<td>${plansFuture.mutiple}倍</td>
											<td><span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansFuture.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span></td>
											<td></td>
											<td>
												<c:choose>
													<c:when test="${plansFuture.status == 2}">
													已撤销
													</c:when>
													<c:otherwise>
													未开始
													</c:otherwise>
												</c:choose>
											</td>
											<input id="issueCode" name="issueCode" type="hidden" />
											<td>
												<c:choose>
													<c:when test="${plansFuture.status == 2}">
													</c:when>
													<c:otherwise>
													<a class="cancelPlanFuture" href="javascript:void(0);" name="${plansFuture.issueCode}">撤销</a>
													</c:otherwise>
												</c:choose>											
											</td>
										</tr>
									</c:otherwise> --%>
								<%-- </c:choose> --%>
								
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	
<!-- start -->
<div style="position:absolute;left:100px;display: none" class="pop w-7" id="divPrompt">
	<div class="hd"><span class="close" id="divclose"></span>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title">您确定要终止整个追号计划吗？</h4>
		<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divCanceled">取 消<b class="btn-inner"></b></a>
				<a href="javascript:void(0);" class="btn btn-important "  id="J-Submit">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;left:500px;display: none" class="pop w-7" id="divPromptFailure">
	<div class="hd"><i class="close" id="divPromptFailuren1"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">追号终止失败，请检查网络或重试！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divPromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;left:500px;display: none" class="pop w-7" id="divPromptFailure3">
	<div class="hd"><i class="close" id="divPromptFailuren4"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">开奖中，追号终止失败!</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divPromptFailuren5">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;left:900px;display: none" class="pop w-7" id="divPromptOk">
	<div class="hd"><i class="close" id="divclose"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">您的追号已被终止！</h4>
		<p class="pop-text">如果您需要撤销当前期的投注方案，您可以通过"查看"进入到当前期方案详情进行操作！</p>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divPromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->

<!-- 撤销追号预约 -->
<!-- start -->
<div style="position:absolute;left:100px;display: none" class="pop w-7" id="divFuturePrompt">
	<div class="hd"><span class="close" id="divFutureClose"></span>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title">您确定撤销该期追号方案吗？</h4>
		<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divFutureCanceled">取 消<b class="btn-inner"></b></a>
				<a href="javascript:void(0);" class="btn btn-important "  id="J-Submit-Future">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;left:500px;display: none" class="pop w-7" id="divFuturePromptFailure">
	<div class="hd"><i class="close" id="divFuturePromptFailuren1"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">撤销方案失败，请检查网络或重试！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->

<!-- start -->
<div style="position:absolute;left:500px;display: none" class="pop w-7" id="divFuturePromptFailureIssue">
	<div class="hd"><i class="close" id="divFuturePromptFailuren1Issue"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">该期追号已经过了销售截止时间,无法撤销！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren2Issue">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->

<!-- start -->
<div style="position:absolute;left:500px;display: none" class="pop w-7" id="divFuturePromptFailure3Issue">
	<div class="hd"><i class="close" id="divFuturePromptFailuren4Issue"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">开奖中，撤销方案失败!</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren5Issue">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->

<!-- start -->
<div style="position:absolute;left:900px;display: none" class="pop w-7" id="divFuturePromptOk">
	<div class="hd"><i class="close" id="divFutureClose"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">您的方案已被撤销！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
</body>

</html>