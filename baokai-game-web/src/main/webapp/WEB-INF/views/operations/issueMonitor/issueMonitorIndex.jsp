<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_jiangqiliuchengguanli" />-<spring:message code="gameCenter_liuchengjiankong" /></title>
	
	
</head>
<body>
	
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">游戏中心</a>><a href="#"><spring:message code="gameCenter_jiangqiliuchengguanli" /></a>&gt;<spring:message code="gameCenter_liuchengjiankong" /></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form" action="queryLotteryIssueWarn" method="post">
						<div class="ui-tab">
						<ul class="ui-form" id="J-lotterys-panel">
							<li>
<%-- 								<label class="ui-label w-2 big"><spring:message code="gameCenter_caizhongming" />：</label> --%>
<!-- 								<span class="ico-tab ico-tab" id="0"><a href="#"  class="_gameType"><spring:message code="gameCenter_quanbucaizhong" /></a></span> -->
								<input type="hidden" id="result" value='${result }'/>
								<input type="hidden" id="dateType" name="dateType" value="${dateType }"/>
								<input type="hidden" id="lotteryId" name="lotteryId" value="${lotteryId }"/>
								<input type="hidden" id="issueType" name="issueType"  value="${issueType }"/>
								<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
								
							</li>
							<li>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_caizhongmingcheng"/>：</label>
								<span class="ico-tab ico-tab-item" data-number="20130911-014期" id="99101"><a href="#"  class="_gameType">重庆时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-015期" id="99102"><a href="#" class="_gameType">江西时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-016期" id="99103"><a href="#"  class="_gameType">新疆时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-017期" id="99104"><a href="#" class="_gameType">天津时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-018期" id="99105"><a href="#" class="_gameType">黑龙江时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-019期" id="99107"><a href="#" class="_gameType">上海时时乐</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-020期" id="99301"><a href="#" class="_gameType">山东11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-021期" id="99302"><a href="#" class="_gameType">江西11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-021期" id="99307"><a href="#" class="_gameType">江苏11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-022期" id="99303"><a href="#" class="_gameType">广东11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-023期" id="99304"><a href="#" class="_gameType">重庆11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-024期" id="99201"><a href="#" class="_gameType">北京快乐8</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-025期" id="99112"><a href="#" class="_gameType">秒开时时彩</a></span>
					<!-- 			<span class="ico-tab ico-tab-item" data-number="20130911-025期" id="99113"><a href="#" class="_gameType">超级2000秒秒彩（APP版）</a></span>-->
								<span class="ico-tab ico-tab-item" data-number="20130911-025期" id="99306"><a href="#" class="_gameType">秒开11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-025期" id="99108"><a href="#" class="_gameType">3D</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-026期" id="99109"><a href="#" class="_gameType">排列3/5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-027期" id="99401"><a href="#" class="_gameType">双色球</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-028期" id="99106"><a href="#" class="_gameType">宝开时时彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-029期" id="99305"><a href="#" class="_gameType">宝开11选5</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-030期" id="99111"><a href="#" class="_gameType">宝开一分彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-030期" id="99114"><a href="#" class="_gameType">腾讯分分彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99501"><a href="#" class="_gameType">江苏快三</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99502"><a href="#" class="_gameType">安徽快三</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99601"><a href="#" class="_gameType">江苏骰宝</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99701"><a href="#" class="_gameType">六合彩</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99602"><a href="#" class="_gameType">高频骰宝(娱乐厅)</a></span>
								<span class="ico-tab ico-tab-item" data-number="20130911-031期" id="99603"><a href="#" class="_gameType">高频骰宝(至尊厅)</a></span>								
								<span class="ico-tab ico-tab" id="0"><a href="#"  class="_gameType"><spring:message code="gameCenter_quanbucaizhong" /></a></span>
							<li>
								<label class="ui-label w-2 big"><spring:message code="gameCenter_shijian" />：</label>
								<span class="ico-tab" id="dateSpan" ><a href="#" name="1" id="dates" ><spring:message code="gameCenter_jintian" /></a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="3" id="dates"><spring:message code="gameCenter_santian" /></a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="7" id="dates"><spring:message code="gameCenter_yizhou" /></a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="0" id="dates"><spring:message code="gameCenter_quanbu" /></a></span>
							</li>
							<li class="checkbox-list" id="J-checkbox-switch">
								<label class="ui-label w-2"></label>
								<!--<label for="checkbox-a" class="label"><input id="checkbox-a" type="checkbox" value="1" data-value1="?a=1&b=1" data-value2="?a=2&b=2" class="checkbox"  checked="checked"/>仅查看尚未结束的奖期（页面自动更新）</label>-->
								<label for="checkbox-b" class="label"><input id="checkbox-b" type="checkbox" value="2" data-value1="?a=3&b=3" data-value2="?a=4&b=4" class="checkbox" /><spring:message code="gameCenter_jinchakancunzaiyichangdejiangqi"/></label>
							</li>
						</ul>
						
						<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_qihao" /></th>
									<th><spring:message code="gameCenter_xiaoshouriqi" /></th>
									<th><spring:message code="gameCenter_xiaoshoushijianduan" /></th>
									<th><spring:message code="gameCenter_lilunkaijiangshijian" /></th>
									<th><spring:message code="gameCenter_kaijianghaoma" /></th>
									<th><spring:message code="gameCenter_kaijianghaomaquerenshijian" /></th>
									<th>接收开奖号码时间</th>
									<th><spring:message code="gameCenter_jieduan" /></th>
									<th><spring:message code="gameCenter_yichangqingkuangmiaoshu" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${issueList}" var="issues" varStatus="issueStatus">
									<tr id="${issues.lotteryid }_${issues.issueCode}">
										<td>${issues.lotteryName }</td>
										<td>${issues.webIssueCode }</td>
										<td>${issues.saleDate }</td>
										<td>${issues.salePeriod }</td>
										<td>${issues.openDrawTime }</td>
										<c:if test="${issues.numberRecord !='' }">
											<td>${issues.numberRecord }</td>
										</c:if>
										<c:if test="${issues.numberRecord =='' }">
											<td>${issues.periodStatus }</td>
										</c:if>
										<c:choose>
										<c:when test="${issues.pauseStatus==2}">
										<td></td>
										<td></td>
										<td>已撤销</td>
										</c:when>
										<c:otherwise>
										<td>${issues.confirmDrawTime }</td>
										<td>${issues.recivceDrawTime }</td>
										<td>
										<c:choose>
											 <c:when test="${issues.periodStatus==0 }">待销售</c:when>
											<c:when test="${issues.periodStatus==1 }">销售中</c:when>
											<c:when test="${issues.periodStatus==2 }">待开奖</c:when>
											<c:when test="${issues.periodStatus==3 }">计奖中</c:when>
											<c:when test="${issues.periodStatus==4 }">验奖中</c:when>
											<c:when test="${issues.periodStatus==5 }">派奖中</c:when>
											<c:when test="${issues.periodStatus==6 }">待结束</c:when>
											<c:when test="${issues.periodStatus==7 }">待对账</c:when>
										</c:choose>
										</td>
										 </c:otherwise>
										</c:choose>
										<td><permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR_DETAIL"><span class="tip-hidden" style="display:none;">${issues.warnDescStr }</span>${issues.warnDescStr }<a class="tip-detail" id="tipDetail" name="${issues.lotteryid }_${issues.issueCode}" href="javascript:void(0);"><spring:message code="gameCenter_xiangqing" /></a></permission:hasRight></td>
									</tr>
								</c:forEach>
						
							</tbody>
						</table>
						
						<div class="page-wrapper clearfix">
							<!--<jsp:include page="../../userCenter/page.jsp"></jsp:include>-->
							<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						</div>
						
					</div>
				</div>
				</form>
			</div>
			
		</div>
		<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/issueMonitor/issueMonitor.js"></script>
</body>