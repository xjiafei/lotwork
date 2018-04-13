<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>投注查询</title>
	
	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	</script>
	<style type="text/css">
.lottery-details{cursor: pointer;}
.lottery-details:hover{color:#009B7D;}
.lottery-details-area{display: none; position: absolute; width: 150px; top: 5px; left: 160px; overflow: hidden; border: 1px solid #076A5B; z-index: 100; zoom: 1;}
.lottery-details-area .num{height: 25px; line-height: 23px; background: #076A5B; color: #fff; padding-left: 10px; overflow: hidden;}
.lottery-details-area .num .multiple{float: left;width:auto;}
.lottery-details-area .num .close{float: right; margin-right: 5px; cursor: pointer;font-size:18px;}
.lottery-details-area .list{margin: 0px; padding: 10px; max-height: 70px;_height:70px;border: none; *zoom:1; word-wrap: break-word; overflow: auto; background: #fff;color:#333;}
.ico-lottery-num-blue{color:blue}
</style>
<script type="text/javascript"	src="${staticFileContextPath}/static/js/userCenter/bettingRecordDetail.js"></script>
</head>
<body>	
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">投注查询</div>
				<div class="notice">您当前查看的是${orderDetail.ordersStruc.account}的${orderDetail.ordersStruc.lotteryName}第${orderDetail.ordersStruc.webIssueCode}期方案详情</div>
				<div class="content">
					<div class="bet-detail">
					 <input type="hidden" id="orderId" value="${orderDetail.ordersStruc.orderId}"/> 
					<table class="table">
							<thead>
								<tr>
									<th colspan="3" class="highbig">${orderDetail.ordersStruc.lotteryName}</th>
								</tr>
							</thead>
						<tbody>
							<tr>
								<td>期号：${orderDetail.ordersStruc.webIssueCode}</td>
								<td>投注时间：${orderDetail.ordersStruc.formatSaleTime}</td>
								<td><c:if test="${!empty orderDetail.ordersStruc.numberRecord}">开奖号码：
								<c:forEach items="${orderDetail.ordersStruc.numberRecordList}" varStatus="status" var="numberRecord">
								    <c:choose>
								    <c:when test="${orderDetail.ordersStruc.lotteryid==99401&&status.last}">
								     <i class="ico-lottery-num-blue">${numberRecord}</i>
								    </c:when>
								    
								    <c:when test="${orderDetail.ordersStruc.lotteryid ==99701}">
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
								<td>投注总金额：<strong class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${orderDetail.ordersStruc.totamount/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></strong>元
								</td>
								<td><c:if test="${gameIssue.status>=6}">总奖金：<strong class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${(orderDetail.ordersStruc.totwin+orderDetail.ordersStruc.totDiamondWin)/10000.00}"  pattern="#,###.##"  minFractionDigits="2"/></strong>元
								</c:if></td>
								<td rowspan="2" id="cancel"><c:choose>
										<c:when test="${orderDetail.ordersStruc.canCancel == true}">
											<c:choose>
												<c:when test="${orderDetail.ordersStruc.status==4}">
													<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>
												</c:when>
												<c:otherwise>
													<a href="javascript:void(0);" class="btn btn-primary"
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
								<td><c:if test="${orderDetail.ordersStruc.parentType==2}"><a href="${currentContextPath}/gameUserCenter/queryPlanDetail?planid=${orderDetail.ordersStruc.parentid}">相关追号记录</a></c:if></td>
							</tr>
						</tbody>
					</table>
				</div>
				<table class="table table-info">
					<thead>
						<tr>
							<th>玩法</th>
							<th>投注内容</th>
							<c:if test="${orderDetail.ordersStruc.lotteryid!=99701}">
							<th>注数</th>
							</c:if>
							<c:choose>
							<c:when test="${orderDetail.ordersStruc.lotteryid==99701}">
							<th>赔率</th>
							</c:when>
							<c:when test="${orderDetail.ordersStruc.lotteryid!=99601&&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
							<th>倍数</th>
							</c:when>
							</c:choose>
							<th>投注金额</th>
							<c:if test="${orderDetail.ordersStruc.lotteryid==99112}">
							<th>加注金额</th>
							<th>转盘倍数</th>
							</c:if>
							<c:if test="${orderDetail.ordersStruc.lotteryid!=99601 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603 && orderDetail.ordersStruc.lotteryid!=99701}">
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
								<td>${slipStruc.gamePlayName}</td>
								<td><c:choose>
									<c:when test="${fn:length(slipStruc.betDetail) > 15}">
									<span class="lottery-details">
									<c:out value="${fn:substring(slipStruc.betDetail, 0, 15)}..." />
									<a class="detailShow">详情</a>
										<div class="lottery-details-area">
										<div class="num"><span class="multiple">详情:</span>
										<em class="close" >×</em>
										</div>
										<div class="list">${slipStruc.betDetail}</div>
										</div>
									</span>
									</c:when>
									<c:otherwise>
									<c:out value="${slipStruc.betDetail}" />
									</c:otherwise>
									</c:choose></td>
								<!-- 注数 -->
								<c:if test="${orderDetail.ordersStruc.lotteryid!=99701}">
								<td>${slipStruc.totbets}</td>
								</c:if>
								
								<!-- 倍数 -->
								<c:choose>
								<c:when test="${orderDetail.ordersStruc.lotteryid==99701}">
								<td>${slipStruc.singleWin/10000}</td>
								</c:when>
								<c:when test="${orderDetail.ordersStruc.lotteryid!=99601 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
								<td>${slipStruc.multiple}</td>
								</c:when>
								</c:choose>
								
								
								<td><strong class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${slipStruc.totamount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></strong></td>
								<c:if test="${orderDetail.ordersStruc.lotteryid==99112}">
									<td><strong class="price"><dfn>&yen;</dfn><fmt:formatNumber value="${slipStruc.diamondAmount/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></strong></td>
									<td><strong class="price"><fmt:formatNumber value="${orderDetail.ordersStruc.diamondMultiple/10}" pattern="#,###.##"  minFractionDigits="2"/></strong></td>
								</c:if>
								<!-- 模式 -->
								<c:if test="${orderDetail.ordersStruc.lotteryid!=99601 && orderDetail.ordersStruc.lotteryid!=99701 &&orderDetail.ordersStruc.lotteryid!=99602&&orderDetail.ordersStruc.lotteryid!=99603}">
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
								<td id="statusName"><c:if test="${slipStruc.status!=2}">${slipStruc.statusName}</c:if><c:if test="${slipStruc.status==2}"><strong class="price color-red"><dfn>&yen;</dfn><fmt:formatNumber value="${(slipStruc.award+slipStruc.diamondWin)/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></strong></c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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