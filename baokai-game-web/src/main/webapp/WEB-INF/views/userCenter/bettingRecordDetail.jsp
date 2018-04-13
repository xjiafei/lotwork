<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>投注记录</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/userCenter/bettingRecordDetail.js"></script>
<script type="text/javascript">
var baseUrl = '${currentContextPath}';
var staticFileContextPath='${staticFileContextPath}';
</script>
<style type="text/css">
.lottery-details{cursor: pointer;}
.lottery-details:hover{color:#008ad8;}
.lottery-details-area{display: none; position: absolute; width: 150px; top: 5px; left: 160px; overflow: hidden; border: 1px solid #008ad8; z-index: 100; zoom: 1;}
.lottery-details-area .num{height: 25px; line-height: 23px; background: #008ad8; color: #fff; padding-left: 10px; overflow: hidden;}
.lottery-details-area .num .multiple{float: left;width:auto;}
.lottery-details-area .num .close{float: right; margin-right: 5px; cursor: pointer;font-size:18px;}
.lottery-details-area .list{margin: 0px; padding: 10px; max-height: 70px;_height:70px;border: none; *zoom:1; word-wrap: break-word; overflow: auto; background: #fff;color:#333;}
.ico-lottery-num-blue{color:blue}
</style>
</head>
<body>
	<div class="g_28 g_last">
		<div class="common-article">
			<div class="title">方案详情</div>
			<div class="notice">您当前查看的是${orderDetail.ordersStruc.account}的${orderDetail.ordersStruc.lotteryName}第${orderDetail.ordersStruc.webIssueCode}期方案详情</div>
			<div class="content">
				<div class="bet-detail">
				 <input type="hidden" id="orderId" value="${orderDetail.ordersStruc.orderId}"/> 
					<table class="table">
						<thead>
							<tr>
								<th colspan="3" class="highbig"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th colspan="3" class="highbig"><span class="strong">${lotteryName }</span></th>
							</tr>
							<tr>
								<td>期号：${orderDetail.ordersStruc.webIssueCode}</td>
								<td>投注时间：${orderDetail.ordersStruc.formatSaleTime}</td>
								<td><c:if test="${gameIssue.status>=6}">开奖号码：
								<c:forEach items="${orderDetail.ordersStruc.numberRecordList}" varStatus="status" var="numberRecord">
								    <c:choose>
								    <c:when test="${orderDetail.ordersStruc.lotteryid==99401&&status.last}">
								     <i class="ico-lottery-num-blue">${numberRecord}</i>
								    </c:when>
								    <c:when test="${orderDetail.ordersStruc.lotteryid==99701}">
								    	<c:choose>
								    	<c:when test="${orderDetail.ordersStruc.numberRecordColorList[status.count-1] == 'GREEN'}">
								     	<i class="ico-lottery-num-green">${numberRecord}</i>
								     	</c:when>
								     	<c:when test="${orderDetail.ordersStruc.numberRecordColorList[status.count-1] == 'BLUE'}">
								     	<i class="ico-lottery-num-blue">${numberRecord}</i>
								     	</c:when>
								     	<c:otherwise>
								     	<i class="ico-lottery-num">${numberRecord}</i>
								     	</c:otherwise>
								     	
								     	</c:choose>
								    </c:when>
								    <c:otherwise>
								    <i class="ico-lottery-num">${numberRecord}</i>
								    </c:otherwise>
								    </c:choose>
								</c:forEach>
								</c:if></td>
							</tr>
							<tr>
							
								<td>投注总金额：<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${orderDetail.ordersStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>元
								</td>
								<td><c:if test="${gameIssue.status>=6 && orderDetail.ordersStruc.status!=5 }">总奖金：<span class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${orderDetail.ordersStruc.totwin/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></span>元
								</c:if></td>
								<td rowspan="2" id="cancel"><c:choose>
										<c:when test="${orderDetail.ordersStruc.canCancel == true}">
											<c:choose>
												<c:when test="${orderDetail.ordersStruc.status==4}">
													<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>
												</c:when>
												<c:otherwise>
													<a href="javascript:void(0);" class="btn"
														id="revSscheme">撤销方案<b class="btn-inner"></b></a>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${orderDetail.ordersStruc.status==4}">
													<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>
												</c:when>
											</c:choose>
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td>方案编号：<span id="pid">${orderDetail.ordersStruc.orderCode}</span></td>
								<td><c:if test="${orderDetail.planId != null && orderDetail.planId !=0 }"><a href="${currentContextPath}/gameUserCenter/queryPlanDetail?planid=${orderDetail.planId}">相关追号记录</a></c:if></td>
							</tr>
						</tbody>
					</table>
				</div>
				<table class="table table-info">
					<thead>
						<tr>
							<th>玩法</th>
							<th>投注内容</th>
							<th>注数</th>
							<c:choose>
							<c:when test="${orderDetail.ordersStruc.lotteryid==99701}">
							<th>赔率</th>
							</c:when>
							<c:when test="${orderDetail.ordersStruc.lotteryid!=99601 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
							<th>倍数</th>
							</c:when>
							</c:choose>
							<th>投注金额</th>
							<c:if test="${orderDetail.ordersStruc.lotteryid == 99112}">
							<th>加注金额</th>
							<th>转盘倍数</th>
							</c:if>
							<c:if test="${orderDetail.ordersStruc.lotteryid!=99601 && orderDetail.ordersStruc.lotteryid!=99701 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
							<th>模式</th>
							</c:if>
							<c:if test="${awardRetStatus==1}">
								<th>奖金模式</th>
							</c:if>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orderDetail.slipsStruc}" var="slipStruc">
							<tr>
								<!-- 玩法 -->
								<td>${slipStruc.gamePlayName}</td>
								<!-- 投注内容 -->
								<td><c:choose>
									<c:when test="${fn:length(slipStruc.betDetail) > 15}">
									<span class="lottery-details">
									<c:out value="${fn:substring(slipStruc.betDetail, 0, 15)}..." />
									<c:if test="${slipStruc.betDetailShow==null }">
										<a class="detailShow">详情</a>
										<div class="lottery-details-area">
										<div class="num"><span class="multiple">详情:</span>
										<em class="close" >×</em>
										</div>
										<div class="list">${slipStruc.betDetail}</div>
									</div>
									</c:if>
									</span>
									<c:if test="${slipStruc.betDetailShow!=null }">
									
									<a class="detailShow">详情</a>
									<div class="lottery-details-area">
										<div class="num"><span class="multiple">投注内容:</span>
										<em class="close" >×</em>
										</div>
										<div class="list">${slipStruc.betDetailShow}</div>
									</div>
									</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${slipStruc.betDetailShow==null }">
											<c:out value="${slipStruc.betDetail}" />
										</c:if>
										<c:if test="${slipStruc.betDetailShow!=null }">
											${slipStruc.betDetailShow}
										</c:if>
									</c:otherwise>
									</c:choose></td>
								<!-- 注数 -->
								<td>${slipStruc.totbets}</td>
								
								<!-- 倍数 -->
								<c:choose>
								<c:when test="${orderDetail.ordersStruc.lotteryid==99701}">
										<c:choose>
										<c:when test="${slipStruc.singleWin==0}">
												<td>
												<c:forEach var="lhcMultBonus" varStatus="status" items="${slipStruc.lhcMultBonus}">
												<c:choose>
												<c:when test="${!status.last}">
												<fmt:formatNumber value="${(lhcMultBonus)/10000.00}" />&nbsp;/
												</c:when>
												<c:when test="${status.last}">
												<fmt:formatNumber value="${(lhcMultBonus)/10000.00}"  />
												</c:when>
												</c:choose>
												</c:forEach> 
												</td>
				
										</c:when>
										<c:when test="${slipStruc.singleWin>0}">
												<td>
													<fmt:formatNumber value="${slipStruc.singleWin/10000.00}" /> 
												</td>
									
										</c:when>
										</c:choose>
								</c:when>
								<c:when test="${orderDetail.ordersStruc.lotteryid!=99601 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
								<td>${slipStruc.multiple}</td>
								</c:when>
								</c:choose>
								
								<!-- 投注金额 -->
								<td><span class="price"><dfn>&yen;</dfn>
								<fmt:formatNumber
											value="${slipStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2" /></span>
								</td>
								<c:if test="${orderDetail.ordersStruc.lotteryid == 99112}">
								<td><span class="price"><dfn>&yen;</dfn>
								<fmt:formatNumber
											value="${slipStruc.diamondAmount/10000.00}" pattern="#,###.##"  minFractionDigits="2" /></span>
								</td>
								<td><span class="price">
								<fmt:formatNumber
											value="${orderDetail.ordersStruc.diamondMultiple}" pattern="#,###.##"  minFractionDigits="2" /></span>
								</td>
								</c:if>
								<c:if test="${orderDetail.ordersStruc.lotteryid!=99601 && orderDetail.ordersStruc.lotteryid!=99701 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
								<!-- 模式 -->
								<td>${slipStruc.moneyModeName}</td>
								</c:if>
								<c:if test="${awardRetStatus==1}">
									<c:choose>
										<c:when test="${slipStruc.awardMode==2}">
											<td><fmt:formatNumber value="${(slipStruc.groupAward+slipStruc.retAward)/10000.00}" pattern="#,###.##"  minFractionDigits="2" /> - 0%</td>
										</c:when>
										<c:when test="${slipStruc.groupAwardDown>0}">
											<td><fmt:formatNumber value="${(slipStruc.groupAward)/10000.00}" pattern="#,###.##"  minFractionDigits="0" />/<fmt:formatNumber value="${(slipStruc.groupAwardDown)/10000.00}" pattern="#,###.##"  minFractionDigits="0" /> - <fmt:formatNumber value="${slipStruc.retPoint/100.00}" pattern="#,###.##" />%</td>
										</c:when>
										<c:otherwise>
											<td><fmt:formatNumber value="${slipStruc.groupAward/10000.00}" pattern="#,###.##"  minFractionDigits="2" /> - <fmt:formatNumber value="${slipStruc.retPoint/100.00}" pattern="#,###.##" />%</td>
										</c:otherwise>
									</c:choose>
								</c:if>
								<!-- 状态 -->
								<td id="statusName" name="statusName">
								<c:choose>
								<c:when test="${orderDetail.ordersStruc.status==7}">
									您的方案可能存在问题，请联系客服
								</c:when>
								<c:otherwise>
								<c:if test="${orderDetail.ordersStruc.status==5}">处理中</c:if>
								<c:if test="${slipStruc.status!=2&&orderDetail.ordersStruc.status!=5}">${slipStruc.statusName}</c:if>
								<c:if test="${slipStruc.status==2 && orderDetail.ordersStruc.status!=5}"><strong class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber
											value="${slipStruc.award/10000.00}" pattern="#,###.##"  minFractionDigits="2" /></strong></c:if>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
						
					</tbody>
				</table>
				<c:if test="${orderDetail.ordersStruc.lotteryid==99108|| orderDetail.ordersStruc.lotteryid==99109}">
				<div class="title">可能的中奖情况</div>	
				<table class="table table-info">
				<thead>
						<tr>
							<th>玩法&投注内容</th>
							<th>号码</th>
							<th>单倍奖金</th>
							<th>倍数</th>
							<th>总奖金</th>
						</tr>
				</thead>
				<tbody>
				<c:forEach items="${orderDetail.slipsStruc}" var="slipStruc">
					<c:forEach items="${slipStruc.gamePoints}" var="gamePoint" varStatus="pointIndex">
					<tr>
						<c:if test="${pointIndex.index==0}"><td rowspan="${slipStruc.gamePointCount}">[${slipStruc.gamePlayName}]
						
						<c:choose>
									<c:when test="${fn:length(slipStruc.betDetail) > 15}">
									<span class="lottery-details">
									<c:out value="${fn:substring(slipStruc.betDetail, 0, 15)}..." />
									<c:if test="${slipStruc.betDetailShow==null }">
										<a class="detailShow">详情</a>
										<div class="lottery-details-area">
										<div class="num"><span class="multiple">详情:</span>
										<em class="close" >×</em>
										</div>
										<div class="list">${slipStruc.betDetail}</div>
									</div>
									</c:if>
									</span>
									<c:if test="${slipStruc.betDetailShow!=null }">
									
									<a class="detailShow">详情</a>
									<div class="lottery-details-area">
										<div class="num"><span class="multiple">投注内容:</span>
										<em class="close" >×</em>
										</div>
										<div class="list">${slipStruc.betDetailShow}</div>
									</div>
									</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${slipStruc.betDetailShow==null }">
											<c:out value="${slipStruc.betDetail}" />
										</c:if>
										<c:if test="${slipStruc.betDetailShow!=null }">
											${slipStruc.betDetailShow}
										</c:if>
									</c:otherwise>
							</c:choose>
						
						</td></c:if>
						<td>
						<c:choose>
									<c:when test="${fn:length(gamePoint.point) > 15}">
									<span class="lottery-details">
									<c:out value="${fn:substring(gamePoint.point, 0, 15)}..." />
									</span>
									
									<a class="detailShow">详情</a>
									<div class="lottery-details-area">
										<div class="num"><span class="multiple">详情:</span>
										<em class="close" >×</em>
										</div>
										<div class="list"><c:if test="${gamePoint.pointView!=null }">
											${gamePoint.pointView}
											</c:if>
											<c:if test="${gamePoint.pointView==null }">
											${gamePoint.point}
											</c:if></div>
									</div>
									</c:when>
									<c:otherwise>
										<c:if test="${gamePoint.point!=null }">
											<c:if test="${gamePoint.pointView!=null }">
											${gamePoint.pointView}
											</c:if>
											<c:if test="${gamePoint.pointView==null }">
											${gamePoint.point}
											</c:if>
										</c:if>
									</c:otherwise>
									</c:choose>
						
						</td>
						<c:if test="${gamePoint.isSignRed==0 }">
						<td><fmt:formatNumber value="${gamePoint.retValue/10000 }" pattern="#,###.##"  minFractionDigits="2" /></td>
						<td>${gamePoint.mult }</td>
						<td><fmt:formatNumber value="${gamePoint.retValue*gamePoint.mult/10000 }" pattern="#,###.##"  minFractionDigits="2" /></td>
						</c:if>
						<c:if test="${gamePoint.isSignRed==1 }">
						<td><span class="color-red"><fmt:formatNumber value="${gamePoint.retValue/10000 }" pattern="#,###.##"  minFractionDigits="2" /></span></td>
						<td><span class="color-red">${gamePoint.mult }</span></td>
						<td><span class="color-red"><fmt:formatNumber value="${gamePoint.retValue*gamePoint.mult/10000 }" pattern="#,###.##"  minFractionDigits="2" /></span></td>
						</c:if>
					</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
				</table>
				</c:if>
			</div>
		</div>
	</div>

	<div class="pop w-7"
		style="position: absolute; left: 100px; display: none" id="divPrompt">
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

	<div class="pop pop-success w-4"
		style="position: absolute; left: 900px; display: none"
		id="divPromptOk">
		<i class="ico-success"></i>
		<h4 class="pop-text">方案已被撤销</h4>
	</div>
	<script type="text/javascript">
$(document).ready(function(){
	var show=false;
	$('.close').click(function() {
		$(this).parents('.lottery-details-area').eq(0).hide();
		show = false;
	});
	$('.detailShow').click(function(){
		$(this).parents('.lottery-details-area').eq(0).hide();
		show = false;
		
		if(show == false){
			var dom = $(this).next();
			dom.css({
				left: $(this).position().left,
				top: $(this).position().top
			});
			$(this).next().show();
		}
		show = true;
	});
});
</script>
	
</body>


</html>