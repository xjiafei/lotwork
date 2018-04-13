<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_fanganzhuihaoguanli" />-追号<spring:message code="gameCenter_xiangqing" /></title>
	
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="${currentContextPath}/gameoa/queryPlanList"><spring:message code="gameCenter_zhuihaojilu" /></a>>追号<spring:message code="gameCenter_xiangqing" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><a href="queryPlanList">&lt;&lt;回到追号记录列表</a></h3>
						
						<input type="hidden" id="planid" name="planid" value="${plan.planid }"/>
						<input type="hidden" id="issueCode"/>
						
						<ul class="ui-form">
							<li>
								<label class="ui-label w-2 big">${plan.lotteryName}</label>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_zhuihaobianhao" />：</label>
								<span class="ui-singleline w-3">${plan.planCode }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_qishibianhao" />：</label>
								<span class="ui-singleline w-3">${plan.startWebIssueCode }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_zhuihaoshijian" />：</label>
								<span class="ui-singleline w-3">${plan.saleTime }</span>
							</li>
							<li>
								<label class="ui-label w-2 big"></label>
								<label class="ui-label w-2 big">进度：</label>
								<span class="ui-singleline w-3">完成${plan.finishIssue }期/取消${plan.cancelIssue }期/总${plan.totalIssue }期</span>
								<label class="ui-label big" style="width:auto;padding:0 0 0 30px;">已追号金额：<span style="font-weight:normal;"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${plan.usedAmount/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/>元</span> &nbsp;&nbsp;|&nbsp;&nbsp; <spring:message code="gameCenter_quxiaojine" />：<span style="font-weight:normal;"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${plan.canceledAmount/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/>元</span> &nbsp;&nbsp;|&nbsp;&nbsp; 追号方案总金额：<span style="font-weight:normal;"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${plan.totamount/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/>元</span></label>
							</li>
							<li>
								<label class="ui-label w-2 big"></label>
								<label class="ui-label w-2 big">终止追号条件：</label>
								<span class="ui-singleline w-3">
								<c:choose>
									<c:when test="${plan.stopMode == '不停止' }">追中不停</c:when>
									<c:when test="${plan.stopMode == '中奖即停' }">追中即停</c:when>
									<c:otherwise>盈利金额＞<span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${plan.stopParams/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/>时停</c:otherwise>
								</c:choose>
								</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_yihuojiangjin" />：</label>
								<span class="ui-singleline w-2 color-red"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${plan.totalWin/10000.00 }" pattern="#,###.##"  minFractionDigits="4"/>元</span>
							</li>
							<li>
								<label class="ui-label w-2 big">追号方案</label>
								<div class="textarea w-10" style="height:116px;margin:0 30px;overflow-y:scroll">
									<table class="table">
										<thead >
											<tr>
												<th  class="big">玩法</th>
												<th class="big">投注内容</th>
												<th class="big">注数</th>
												<c:if test="${plan.lotteryid !=99601&&plan.lotteryid !=99602&&plan.lotteryid !=99603}">
												<th class="big">倍数</th>
												<th class="big">模式</th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${slips }" var="slip">
												<tr>
													<td>${slip.gamePlayName }</td>
													<td>
														
														<c:choose>
															<c:when test="${fn:length(slip.betDetail)>20}">
																<%-- <span  title="${slip.betDetail }">
																	${fn:substring(slip.betDetail,0,20)}...
																</span> --%>
																
																<c:out value="${fn:substring(slip.betDetail,0,20)}..." /><a title="${slip.betDetail}"><spring:message code="gameCenter_xiangqing" /></a>																
															</c:when>
															<c:otherwise>
																${slip.betDetail }
															</c:otherwise>
														</c:choose>
													</td>
													<td>${slip.totbets }</td>
													<c:if test="${plan.lotteryid !=99601&&plan.lotteryid !=99602&&plan.lotteryid !=99603}">
													<td>1倍</td>
													<td>${slip.moneyMode }</td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								 <c:if test="${plan.status=='进行中'&&canStop}"> 
								
									 <permission:hasRight moduleName="GAME_OPMGR_PLANLIST_DETAIL_STOP"> 
									<a id="J-button-stop" href="javascript:void(0);" class="btn btn-small">终止追号<b class="btn-inner"></b></a>
									 </permission:hasRight> 
								
								 </c:if> 
							</li>
							<li>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_yonghuming" />：</label>
								<span class="ui-singleline w-2">${plan.account}</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_banbenhao" />：</label>
								<span class="ui-singleline w-2">${plan.channelVersion}</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_laiyuan" />：</label>
								<span class="ui-singleline w-2">${plan.channelid}</span>
							</li>
						</ul>
						
						<table class="table table-info">
						<thead>
							<tr>
								<th></th>
								<th><spring:message code="gameCenter_qihao" /></th>
								<c:if test="${plan.lotteryid !=99601&&plan.lotteryid !=99602&&plan.lotteryid !=99603}">
								<th>追号倍数</th>
								</c:if>
								<th><spring:message code="gameCenter_touzhujine" /></th>
								<th>当期开奖号码</th>
								<th><spring:message code="gameCenter_zhuangtai" /></th>
								<th><spring:message code="gameCenter_jiangjin" /></th>
								<th><spring:message code="gameCenter_caozuoxiang" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${plansFutures}" var="plansFuture">
								<%-- <c:choose>
									<c:when test="${false}"> --%>
										<tr>
											<td></td>
											<td>${plansFuture.webIssueCode}</td>
											<c:if test="${plan.lotteryid !=99601&&plan.lotteryid !=99602&&plan.lotteryid !=99603}">
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
												存在异常
												</c:when>
												<c:otherwise>
												未开始
												</c:otherwise>
											</c:choose>
											</td>
											<td>
											<c:if test="${plansFuture.totwin != null && plansFuture.status == 2}">
											<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${plansFuture.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="4"/></span>
											</c:if>
											</td>
											<td>
											<c:choose>
												<c:when test="${plansFuture.status == 1}">
												<a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${plansFuture.planDetailsId }&orderCode=${plansFuture.planCode}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 2}">
												<a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${plansFuture.planDetailsId }&orderCode=${plansFuture.planCode}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 3}">
												<a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${plansFuture.planDetailsId }&orderCode=${plansFuture.planCode}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 4}">
												
												</c:when>
												<c:when test="${plansFuture.status == 5}">
												<a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${plansFuture.planDetailsId }&orderCode=${plansFuture.planCode}" target="_blank">查看</a>
												</c:when>
												<c:when test="${plansFuture.status == 6}">
												归档
												</c:when>
												<c:when test="${plansFuture.status == 7}">
												<a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${plansFuture.planDetailsId }&orderCode=${plansFuture.planCode}" target="_blank">查看</a>
												
												</c:when>
												<c:otherwise>
												<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST_DETAIL_REVOKE">
													<c:if test="${plansFuture.canCancel == true}">
													<a class="cancelPlanFuture" href="javascript:void(0);" name="${plansFuture.issueCode}">撤销</a>
													</c:if>
												</permission:hasRight>
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
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
							<%-- <c:forEach items="${orders }" var="order">
								<tr>
									<td></td>
									<td>${order.webIssueCode}</td>
									<td>${order.multiple}倍</td>
									<td><span class="price"><dfn>¥</dfn>${order.totalAmount/10000.00 }</span></td>
									<td>${order.numberRecord }</td>
									<td>
										<c:choose>
											<c:when test="${order.statusSign==1 }">
												<span class="price color-red"><dfn>¥</dfn>${order.totwin/10000.00 }</span></td>
											</c:when>
											<c:otherwise>${order.status }</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${order.statusSign==1 }">
												<a href="javascript:void(0);">查看</a>
											</c:when>
											<c:otherwise>
												<permission:hasRight moduleName="GAME_OPMGR_PLANLIST_DETAIL_REVOKE">
												<a id="J-button-cancel" href="javascript:void(0);" orderId=“${order.orderId }" issueCode="${order.issueCode }">撤销</a>
												</permission:hasRight>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach> --%>
						</tbody>
					</table>
					
						<div style="margin:10px 0px;border:1px solid #DEDEDE">
						<h3 class="ui-title" style="background:#F3F3F3;border:0;">相关帐户明细</h3>
						<table class="table table-info" style="margin-top:0">
							<thead>
								<tr>
									<th>交易流水号</th>
									<th><spring:message code="gameCenter_yonghuming" /></th>
									<th>交易时间</th>
									<th>摘要</th>
									<th>收入</th>
									<th>冻结</th>
									<th>支出</th>
									<th>可用余额</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${funds }" var="fund">
									<tr>
										
										<td><c:if test="${fund.orderCode== null }">${fund.transactionId }</c:if>
										<c:if test="${fund.orderCode != null }"><a href="${currentContextPath}/gameoa/queryOrderDetail?orderCode=${fund.orderCode}"> ${fund.transactionId }</a></c:if>
										</td>
										<td>${fund.account }</td>
										<td>${fund.transactionTime }</td>
										<td>${fund.transactionInfp }</td>
										<td class="color-green"><fmt:formatNumber value="${fund.availAccountAdd}" pattern="#,###.####"  minFractionDigits="4"/></td>
										<c:if test="${fund.freezeAccountChange>0.0}"><td class="color-red"></c:if>
										<c:if test="${fund.freezeAccountChange<=0.0}"><td></c:if> 
										 <fmt:formatNumber value="${fund.freezeAccountChange}" pattern="#,###.####"  minFractionDigits="4"/></td>
										<td class="color-red"><fmt:formatNumber value="${fund.totalAccountReduce}" pattern="#,###.####"  minFractionDigits="4"/></td>
										<td><fmt:formatNumber value="${fund.availBalance}" pattern="#,###.####"  minFractionDigits="4"/></td>
										<td>${fund.note }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div style="text-align:right;padding:10px;"><a href="${currentContextPath}/admin/Reporting/index?parma=sv55">查看全部明细&gt;&gt;</a></div>
						</div>
						<div style="height:400px;"></div>
						
					</div>
				</div>
			</div>

<!-- 撤销追号预约 -->
<!-- start -->
<div style="position:absolute;left:100px;display: none" class="pop w-7" id="divFuturePrompt">
	<div class="hd"><span class="close" id="divFutureClose"></span>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title">您确定要撤销该期追号预约吗？</h4>
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
		<h4 class="pop-title color-red">撤销预约失败，请检查网络或重试！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;left:900px;display: none" class="pop w-7" id="divFuturePromptOk">
	<div class="hd"><i class="close" id="divFutureClose"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">您的预约已被撤销！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="divFuturePromptFailuren2">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
			
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
		var lotteryid=${plan.lotteryid};
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/plan/planDetail.js"></script>
</body>