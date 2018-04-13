<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_fanganzhuihaoguanli" />-方案<spring:message code="gameCenter_xiangqing" /></title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/bet/bet.css" />
<style type="text/css">
.bet-detail{padding:0px;border-bottom:0px solid #FFF;background-color:#FFF;}
</style>

</head>

<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="${currentContextPath}/gameoa/queryOrderList"><spring:message code="gameCenter_fanganjilu" /></a>>方案<spring:message code="gameCenter_xiangqing" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><a href="queryOrderList"  >&lt;&lt;回到方案记录列表</a></h3>
						
						<input type="hidden" id="lotteryid" value="${order.lotteryid }"/>
						<input type="hidden" id="issueCode" value="${order.issueCode }"/>
						<input type="hidden" id="orderId" value="${order.orderId }"/>
						
						<ul class="ui-form">
							<li>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<span class="ui-singleline w-2">${order.lotteryName }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_fanganbianhao" />：</label>
								<span class="ui-singleline">${order.orderCode }<c:if test="${order.parentType=='是'}" ><a href="${currentContextPath}/gameoa/queryPlanDetail?planid=${order.planId}&planCode=${order.planCode}" target="_blank">（相关追号记录）</a></c:if></span>
							</li>
							<li>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_qihao" />：</label>
								<span class="ui-singleline w-2">${order.webIssueCode }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_touzhushijian" />：</label>
								<span class="ui-singleline w-3">${order.saleTime }</span>
							</li>
							<li>
								<label class="ui-label w-2 big">投注总金额：</label>
								<span class="ui-singleline w-2"><span class="price"><dfn>&yen;</dfn></span>
								<fmt:formatNumber value="${order.totalAmount/10000.00 }" pattern="###,###.#" minFractionDigits="2" type="number"/>
								元</span>
								<label class="ui-label w-2 big">总奖金：</label>
								<span class="ui-singleline w-2"><span class="price"><dfn>&yen;</dfn></span>
								<fmt:formatNumber value="${(order.totwin+order.totDiamondWin)/10000.00 }" pattern="###,###.####" minFractionDigits="4" type="number"/>
								元</span>
								<!-- 开奖号码 -->
								<label class="ui-label w-2 big"><spring:message code="gameCenter_kaijianghaoma" />：</label>
								
								<c:choose>
								<c:when test="${order.lotteryid ==99701}">
								
								
									<span class="bet-detail">
									<c:forEach items="${numberRecordList}" varStatus="status" var="numberRecord">
									<c:choose>
							    	<c:when test="${numberRecordColorList[status.count-1] == 'GREEN'}">
							     	<i class="ico-lottery-num-green">${numberRecord}</i>
							     	</c:when>
							     	<c:when test="${numberRecordColorList[status.count-1] == 'BLUE'}">
							     	<i class="ico-lottery-num-blue">${numberRecord}</i>
							     	</c:when>
							     	<c:otherwise>
							     	<i class="ico-lottery-num">${numberRecord}</i>
							     	</c:otherwise>
							     	
							     	</c:choose>
								
									</c:forEach>
									</span>
								</c:when>
								<c:otherwise>
								<span class="ui-singleline w-2">${order.numberRecord }</span>
								</c:otherwise>
								
								</c:choose>
							
								<label class="ui-label w-2 big"><spring:message code="gameCenter_kaijiangShenXiao" />：</label>							
								<c:choose>
								<c:when test="${order.lotteryid ==99701}">
								
								
									<span class="bet-detail">
									<c:forEach items="${numberRecordShenXiaoList}" varStatus="status" var="numberRecord">
							     	<i class="ico-lottery-num">${numberRecord}</i>						
									</c:forEach>
									</span>
								</c:when>
								</c:choose>
								

							</li>
							<li  text-align : center >
								<label class="ui-label w-2 big"><spring:message code="gameCenter_yonghuming" />：</label>
								<span class="ui-singleline w-2">${order.account }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_banbenhao" />：</label>
								<span class="ui-singleline w-2">${order.channelVersion }</span>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_laiyuan" />：</label>
								<span class="ui-singleline w-2">${order.channelId }</span>
								<label class="ui-label w-2 big">奖金组：</label>
								<span class="ui-singleline w-2">${userAwardName }</span>					
							</li>
							<li text-align : center>
								<label class="ui-label w-2 big">总代返点：</label>
									 <!-- 六合彩反點 平碼、直選、特效、色波、兩面、 一肖、不中、23連肖(中)、45連肖(中)、連肖(不中)、連碼-->
									<c:choose>								  
										<c:when test="${order.lotteryid== 99701}">
										<span class="ui-multiline" >${lhcFlatcodeRet / 100}% , ${userDirectRet / 100 }% , ${lhcYearRet / 100}% , ${lhcColorRet / 100}%, ${lhcOneyearRet / 100}% , ${lhcNotinRet / 100}% , ${lhcContinuein23Ret / 100}% , ${lhcContinuein4Ret / 100}% ,${lhcContinuein5Ret / 100}% , ${lhcContinuenotin23Ret / 100}% ,${lhcContinuenotin4Ret / 100}% ,${lhcContinuenotin5Ret / 100}% ,${lhcContinuecodeRet / 100}% </span>																	
										</c:when>
										<c:when test="${order.lotteryid== 99601 || order.lotteryid== 99602 || order.lotteryid== 99603}">
										<span class="ui-singleline w-2">${userDirectRet / 100 }%,${userThreeoneRet / 100}%,${sbThreeoneRet / 100}%</span>
										</c:when>
										<c:otherwise>
										<span class="ui-singleline w-2">${userDirectRet / 100 }%,${userThreeoneRet / 100}%</span>	
										</c:otherwise>
									</c:choose>
							</li>
						</ul>
						<h3 class="ui-title">
						 				<c:choose>
											    <c:when test="${order.status=='撤销'}">
											    	
													<strong class="high color-red" id="revSchemeOk">方案已被撤销
													<c:if test="${order.cancelModels == 1 }">(用户)</c:if>
													<c:if test="${order.cancelModels == 2 }">(系统)</c:if>
													
													</strong>
												</c:when>
												<c:otherwise>
												<c:choose>
												<c:when test="${order.adminCanCancel == true }">
												<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST_DETAIL_REVOKE">				
													<a href="javascript:void(0);" class="btn btn-primary"
														id="revSscheme">撤销方案<b class="btn-inner"></b></a>
												</permission:hasRight>
												</c:when>
												</c:choose>
												
												</c:otherwise>
										</c:choose>
											</h3>
						
						
						<table id="J-table" class="table table-info">
							<thead>
								<tr>
									<th>玩法：</th>
									<th>注数：</th>
									<c:if test="${order.lotteryid !=99601&&order.lotteryid !=99602&&order.lotteryid !=99603}">
									<th>倍数：</th>
									</c:if>
									<th><spring:message code="gameCenter_touzhujine" />：</th> 
									<c:if test="${order.lotteryid == 99112}">
									<th>加注金额：</th>
									<th>转盘倍数：</th>
									</c:if>
									<c:if test="${order.lotteryid !=99601&&order.lotteryid !=99602&&order.lotteryid !=99603}">
									<th>模式：</th>
									</c:if>
									<c:if test="${awardRetStatus==1}">
										<th>奖金模式：</th>
										<th>奖金模式状态：</th>
									</c:if>
									<th><spring:message code="gameCenter_zhuangtai" />：</th>
									<th><spring:message code="gameCenter_jiangjin" />：</th>
									<th>中/投比：</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${slips}" var="slip">
									<tr>
										<td>${slip.gamePlayName }</td>
										<td>${slip.totbets }注</td>
										<c:if test="${order.lotteryid !=99601&&order.lotteryid !=99602&&order.lotteryid !=99603}">
										<td>${slip.multiple }倍</td>
										</c:if>
										<!-- 投注金额 -->
										<td><span class="price"><dfn>&yen;</dfn></span>
										<fmt:formatNumber value="${slip.totamount/10000.00 }" pattern="###,###.#" minFractionDigits="2" type="number"/>
										</td>
										<c:if test="${order.lotteryid == 99112}">
										<td><span class="price"><dfn>&yen;</dfn></span>
										<fmt:formatNumber value="${slip.diamondAmount/10000.00 }" pattern="###,###.#" minFractionDigits="2" type="number"/>
										</td>
										<td><span class="price"></span>
										<fmt:formatNumber value="${order.diamondMultiple }" pattern="###,###.#" minFractionDigits="2" type="number"/>
										</td>
										</c:if>
										<c:if test="${order.lotteryid !=99601 &&order.lotteryid !=99602&&order.lotteryid !=99603}">
										<td>${slip.moneyMode }</td>
										</c:if>
										<!-- 奖金模式   奖金模式状态 -->
										<c:if test="${awardRetStatus==1}">
											<c:choose>
												<c:when test="${slip.awardMode==2}">
													<td><fmt:formatNumber value="${(slip.groupAward+slip.retAward)/10000.00}" pattern="#,###.####"  minFractionDigits="4" /> - 0 %</td>
													<td>是</td>
												</c:when>
												<c:when test="${slip.groupAwardDown>0}">
													<td><fmt:formatNumber value="${(slip.groupAward)/10000.00}" pattern="#,###.##"  minFractionDigits="0" />/<fmt:formatNumber value="${(slip.groupAwardDown)/10000.00}" pattern="#,###.##"  minFractionDigits="0" /> - <fmt:formatNumber value="${slip.retPoint/100.00}" pattern="#,###.##" /> %</td>
													<td>否</td>
												</c:when>
												<c:when test="${order.lotteryid ==99701}"><!-- 六合彩 -->
													<c:choose>
													<c:when test="${slip.singleWin==0}">
														<td>
														<c:forEach var="lhcMultBonus" varStatus="status"  items="${slip.lhcMultBonus}">
															<c:choose>
															<c:when test="${!status.last}">
															<fmt:formatNumber value="${(lhcMultBonus)/10000.00}" pattern="#,###.##"  minFractionDigits="4" />&nbsp;/
															</c:when>
															<c:when test="${status.last}">
															<fmt:formatNumber value="${(lhcMultBonus)/10000.00}" pattern="#,###.##"  minFractionDigits="4" />
															</c:when>
															</c:choose>
														</c:forEach>
														 - <fmt:formatNumber value="${slip.retPoint/100.00}" pattern="#,###.##" /> %
														</td>
														<td>否</td>
													</c:when>
													<c:when test="${slip.singleWin>0}">
													<td>
														<fmt:formatNumber value="${slip.singleWin/10000.00}" pattern="#,###.##"  minFractionDigits="4" /> - <fmt:formatNumber value="${slip.retPoint/100.00}" pattern="#,###.##" /> %</td>
													<td>否</td>
													</c:when>
													</c:choose>
												</c:when>
												<c:otherwise>
													<td>
														<fmt:formatNumber value="${slip.groupAward/10000.00}" pattern="#,###.##"  minFractionDigits="4" /> - <fmt:formatNumber value="${slip.retPoint/100.00}" pattern="#,###.##" /> %</td>
													<td>否</td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<td>
										<c:choose>
										<c:when test="${slip.status==7}">存在异常</c:when>
										<c:otherwise>
											<c:if test="${slip.status==2 }">中奖</c:if>
											<c:if test="${slip.status==3 }">未中奖</c:if>
											<c:if test="${slip.status==4 }">撤销</c:if>
											<c:if test="${slip.status==5}">处理中</c:if>
										 </c:otherwise>
										 </c:choose>
										</td>
										<td><c:if test="${slip.status==2 }"><span class="price"><dfn>&yen;</dfn></span>
										<fmt:formatNumber value="${(slip.award+slip.diamondWin)/10000.00 }" pattern="###,###.####" minFractionDigits="4" type="number"/></c:if>
										</td>
										<td><fmt:formatNumber value="${slip.winsRadio/100 }" pattern="###,###.#" minFractionDigits="2" type="number"/>
										</td>
									</tr>
									<tr>
										<td>投注内容</td>
										<td colspan="7" class="table-toggle" style="white-space:normal">
										<div class="panel-lotterys">
											<div class="panel-lotterys-inner">${slip.betDetail }</div>
										</div>
										<c:if test="${(fn:length(slip.betDetail)) > 1000}">
										<div class="ico-view-more" class="clearfix">
											<i class="ico-close"></i>
										</div>
										</c:if>
										</td>
									</tr>
								</c:forEach>
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
						<h4 class="ui-title"><a target="_blank" href="/admin/Reporting/index?parma=sv55" class="more">查看全部明细&gt;&gt;</a></h4>
						</div>
						<div style="height:400px;"></div>
						

					</div>
				</div>
			</div>
			<div>
			<input type="hidden" id="error" value=${error == true? 1:0}>
			</div>
			<div class="pop w-7"
		style="position: absolute; left: 100px; display: none;" id="divPrompt">
		<div class="hd">
			<span class="close" id="divclose"></span>温馨提示
		</div>
		<div class="bd">
			<h4 class="pop-title">您确定要撤销该方案？</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divCanceled">取 消<b
					class="btn-inner"></b></a> <a href="javascript:void(0);"
					class="btn btn-important " id="J-Submit">确 定<b
					class="btn-inner"></b></a>
			</div>
		</div>
	</div>

	<div class="pop w-7"
		style="position: absolute; left: 500px; display: none"
		id="divPromptFailure">
		<div class="hd">
			<i class="close" id="divPromptFailuren1"></i>温馨提示
		</div>
		<div class="bd">
			<h4 id="message" class="pop-title color-red">方案撤销失败，请检查网络或重试！</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divPromptFailuren2">关
					闭<b class="btn-inner"></b>
				</a>
			</div>
		</div>
	</div>
	
	<div class="pop w-7"
		style="position: absolute; left: 500px; display: none"
		id="divError">
		<div class="hd">
			<i class="close" id="divError1"></i>温馨提示
		</div>
		<div class="bd">
			<h4 id="message" class="pop-title color-red">此笔订单发生异常${orderID}，<br>请洽客服人員!</h4>
			<div class="pop-btn">
				<a href="/admin/Reporting/index?parma=sv55" class="btn">关
					闭<b class="btn-inner"></b>
				</a>
			</div>
		</div>
	</div>
	
	<div class="pop pop-success w-4"
		style="position: absolute; left: 900px; display: none"
		id="divPromptOk">
		<i class="ico-success"></i>
		<h4 class="pop-text">方案已被撤销</h4>
	</div>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/orderDetail.js"></script>
</body>