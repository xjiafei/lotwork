<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>奖金详情</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/userCenter/queryPlansDatetimePicker.js"></script>
<%--<script type="text/javascript"
	src="${staticFileContextPath}/static/js/userCenter/queryPlans.js"></script>--%>
<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
</script>
</head>
<body>
	<div class="g_28 g_last">
		<div class="common-content">
			<div class="title">奖金详情</div>
			<div class="content">
				<div class="rebate-list">
						<div class="rebate-list">
						
						<div id="div-context" class="lottery-switcher">
	                    	<div class="lottery-tabs">
	                    		<a href="javascript:void(0);">时时彩系</a>
	                    		<a href="javascript:void(0);">11选5系</a>
	                    		<a href="javascript:void(0);">3D/p3/P5系</a>
	                    		<a href="javascript:void(0);">基诺系</a>
	                    		<a href="javascript:void(0);">奖池系</a>
	                    	</div>

	                    	<!-- 时时彩系 -->
	                    	
	                    	<input type="hidden" id="userId" value="${userId}">
	                    	<c:forEach var="awardGoupMaps" items="${awardGoupMap}" varStatus="status">
	                    	<div class="lottery-switch">
	                    	<c:forEach items="${awardGoupMaps.value }" var="awardGoupMap">
	                    		<dl class="item">
	                    		<dt>${awardGoupMap.key}</dt>
	                    		<input type="hidden" id="type" value="${type}">
								<c:forEach var="bonusDetail" items="${awardGoupMap.value}">
									<dd><table class="table">
										<c:if test="${bonusDetail.lotterySeriesCode==1 }">
											<tr>
												<td rowspan="2">${bonusDetail.awardName }:</td>
												<td>
													<a class="label-like" data-value="${bonusDetail.directRet }" data-wanfadata="1" data-wanfa="1" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">直选返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.directRet/100 }-${bonusDetail.maxDirectRet/100 }" readonly/>
												</td>
											</tr>
											<tr>
												<td>
													<a class="label-like" data-value="${bonusDetail.threeoneRet }"  data-wanfadata="1" data-wanfa="2" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">不定位返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.threeoneRet/100 }-${bonusDetail.maxThreeOneRet/100 }" readonly/>
												</td>
											</tr>
										</c:if>
										<c:if test="${bonusDetail.lotterySeriesCode==3 }">
											<tr>
												<td>${bonusDetail.awardName }:</td>
												<td>
													<a class="label-like" data-value="${bonusDetail.directRet }" data-wanfadata="1" data-wanfa="3" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">所有玩法返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.directRet/100 }-${bonusDetail.maxDirectRet/100 }" readonly/>
												</td>
											</tr>
										
										</c:if>
										<c:if test="${bonusDetail.lotterySeriesCode==2 }">
											<tr>
												<td rowspan="2">${bonusDetail.awardName }:</td>
												<td>
													<a class="label-like" data-value="${bonusDetail.directRet }" data-wanfadata="1" data-wanfa="1" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">直选返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.directRet/100 }-${bonusDetail.maxDirectRet/100 }" readonly/>
												</td>
											</tr>
											<tr>
												<td>
													<a class="label-like" data-value="${bonusDetail.threeoneRet }" data-wanfadata="1" data-wanfa="2" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">不定位返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.threeoneRet/100 }-${bonusDetail.maxThreeOneRet/100 }" readonly/>
												</td>
											</tr>
										</c:if>
										<c:if test="${bonusDetail.lotterySeriesCode==4 }">
											<tr>
												<td rowspan="2">${bonusDetail.awardName }:</td>
												<td>
													<a class="label-like" data-value="${bonusDetail.directRet }" data-wanfadata="1" data-wanfa="4" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">任选型返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.directRet/100 }-${bonusDetail.maxDirectRet/100 }" readonly/>
												</td>
											</tr>
											<tr>
												<td>
													<a class="label-like" data-value="${bonusDetail.threeoneRet }" data-wanfadata="1" data-wanfa="5" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">趣味型返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.threeoneRet/100 }-${bonusDetail.maxThreeOneRet/100 }" readonly/>
												</td>
											</tr>
										</c:if>
										<c:if test="${bonusDetail.lotterySeriesCode==5 }">
											<tr>
												<td>${bonusDetail.awardName }:</td>
												<td>
													<a class="label-like" data-value="${bonusDetail.directRet }" data-wanfadata="1" data-wanfa="3" data-awardgroup="${bonusDetail.sysAwardGrouId }" data-lottery="${bonusDetail.lotteryId }">所有玩法返点</a>
													<input type="input" class="input w-2" value="${bonusDetail.directRet/100 }-${bonusDetail.maxDirectRet/100 }" readonly/>
												</td>
											</tr>
										</c:if>
									</table></dd>
								</c:forEach>
								</dl>
							</c:forEach>
							</div>
							</c:forEach>
						<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/queryBonusDetails.js"></script>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>


</html>