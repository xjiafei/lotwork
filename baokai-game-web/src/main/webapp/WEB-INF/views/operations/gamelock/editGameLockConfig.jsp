<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<title>封锁参数修改  封锁数据</title>
</head>
<body>
	<div id="tab_menu_id" style="display:none">lockMenu</div>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>封锁参数设定
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-title clearfix">
						<ul>
							<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
						</ul>
					</div>
					<div id="J-list-container">
						<ul class="ui-tab-title clearfix">
							<li><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁数据</a></li>
							<li class="current"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">封锁参数设定</a></li>
						</ul>
						<h3 class="ui-title"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">&lt;&lt; 返回</a></h3>
						<form id="J-form" action="<%=request.getContextPath() %>/gameoa/editConfig" method="post">
							<ul class="ui-form">
								<li>
									<input id="id" name="id" type="hidden" value="${id}">
									<input id="gameId" name="gameId" type="hidden" value="${lotteryId}">
									<input id="upValProcess" name="upValProcess" type="hidden" value="">
									<input id="upValProcess2" name="upValProcess2" type="hidden" value="">
									<input id="upValProcess3" name="upValProcess3" type="hidden" value="">
									<c:if test="${lotteryId != 99701}">
										<label class="ui-label w-4 ">
										${lotteryName }封锁参数  
										&nbsp;&nbsp;&nbsp;&nbsp;
										最大封锁值：
										</label>
										<c:if test="${lotteryId==99109 }">
											<ul class="ui-form">
												<li><label class="ui-label w-4 ">p3:</label><input id="upValProcessView" type="text" name="upValProcessView" class="ui-info textarea" style="padding:0 10px;" value="${upVal/10000}"></li>
												<li><label class="ui-label w-4 ">p5:</label><input id="upValProcessView2" type="text" name="upValProcessView2" class="ui-info textarea" style="padding:0 10px;" value="${upVal2/10000}">
												<input id="upValProcessView3" type="hidden" name="upValProcessView3" class="ui-info textarea" style="padding:0 10px;" value="">&nbsp;&nbsp;
												<a id="J-button-submit" href="#" class="btn btn-important">保存修改<b class="btn-inner"></b></a></li>
											</ul>
										</c:if>
										<c:if test="${lotteryId!=99109 }">
											<input id="upValProcessView" type="text" name="upValProcessView" class="ui-info textarea" style="padding:0 10px;" value="<fmt:formatNumber type="number" groupingUsed="false" value="${gameLock.upValProcess/10000}"/>">
											<input id="upValProcessView2" type="hidden" name="upValProcessView2" class="ui-info textarea" style="padding:0 10px;" value="">
											<input id="upValProcessView3" type="hidden" name="upValProcessView3" class="ui-info textarea" style="padding:0 10px;" value="">&nbsp;&nbsp;
											<a id="J-button-submit" href="#" class="btn btn-important">保存修改<b class="btn-inner"></b></a>
										</c:if>
									</c:if>
									
									<c:if test="${lotteryId == 99701}">
										<label class="ui-label w-4 ">
										${lotteryName}封锁参数
										</label>
										<p/>
										
										<label class="ui-label w-4 ">  
										&nbsp;&nbsp;<b>特码</b>&nbsp;&nbsp;
										最大封锁值：
										</label>
										<input id="upValProcessView" type="text" name="upValProcessView" class="ui-info textarea" style="padding:0 10px;" value="<fmt:formatNumber type="number" groupingUsed="false" value="${gameLock.upValProcess/10000}"/>">
										<p/>
										
										<label class="ui-label w-4 ">
										&nbsp;&nbsp;<b>正特码_一肖</b>&nbsp;&nbsp;
										最大封锁值：
										</label>
										<input id="upValProcessView2" type="text" name="upValProcessView2" class="ui-info textarea" style="padding:0 10px;" value="<fmt:formatNumber type="number" groupingUsed="false" value="${gameLock.upValProcess2/10000}"/>">
										<p/>
										
										<label class="ui-label w-4 ">
										&nbsp;&nbsp;<b>其他玩法</b>&nbsp;&nbsp;
										最大封锁值：
										</label>
										<input id="upValProcessView3" type="text" name="upValProcessView3" class="ui-info textarea" style="padding:0 10px;" value="<fmt:formatNumber type="number" groupingUsed="false" value="${gameLock.upValProcess3/10000}"/>">
										<a id="J-button-submit" href="#" class="btn btn-important">保存修改<b class="btn-inner"></b></a>
										<p/>
									</c:if>
								</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/editGameLockConfig.js"></script>
</body>
