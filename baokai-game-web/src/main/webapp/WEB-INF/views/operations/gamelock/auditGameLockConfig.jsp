<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<title>封锁参数审核  封锁数据</title>
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
						<form id="J-form" action="${currentContextPath}/gameoa/auditConfig" method="post">
						<ul class="ui-form">
							<li>
								<c:if test="${lotteryId != 99701}">
									<label class="ui-label w-4 ">
									${lotteryName}封锁参数  
									&nbsp;&nbsp;&nbsp;&nbsp;
									最大封锁值：
									</label>
									<c:if test="${lotteryId!=99109 }">
									<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal/10000 }"><fmt:formatNumber type="number" groupingUsed="false" value="${upValProcess/10000}"/></span>
									</c:if>
									<c:if test="${lotteryId==99109 }">
									<label class="ui-label w-4 ">p3:</label><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal/10000 }">${upValProcess/10000}</span>&nbsp;&nbsp;
									<label class="ui-label w-4 ">p5:</label><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal2/10000 }">${upValProcess2/10000}</span>
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
									<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal/10000 }"><fmt:formatNumber type="number" groupingUsed="false" value="${upValProcess/10000}"/></span>
									<p/>
									
									<label class="ui-label w-4 ">
									&nbsp;&nbsp;<b>正特码_一肖</b>&nbsp;&nbsp;
									最大封锁值：
									</label>
									<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal2/10000 }"><fmt:formatNumber type="number" groupingUsed="false" value="${upValProcess2/10000}"/></span>
									<p/>
									
									<label class="ui-label w-4 ">
									&nbsp;&nbsp;<b>其他玩法</b>&nbsp;&nbsp;
									最大封锁值：
									</label>
									<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${upVal3/10000 }"><fmt:formatNumber type="number" groupingUsed="false" value="${upValProcess3/10000}"/></span>
									<p/>
								</c:if>
								
								<input type="hidden" name="id" value="${id }">
								<input type="hidden" name="gameId" value="${lotteryId }">
								<input type="hidden" name="upVal" value="${upVal }">
								<input type="hidden" name="upValProcess" value="${upValProcess }">
								<input type="hidden" name="upVal2" value="${upVal2 }">
								<input type="hidden" name="upValProcess2" value="${upValProcess2 }">
								<input type="hidden" name="upVal3" value="${upVal3 }">
								<input type="hidden" name="upValProcess3" value="${upValProcess3 }">
								<input id="status" type="hidden" name="status" value="2">
								&nbsp;&nbsp;
								<a id="J-button-submit" href="#" class="btn btn-important">审核通过<b class="btn-inner"></b></a>
								<a id="J-button-submit_" href="#" class="btn btn-important"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<style>
	.j-ui-tip-info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/auditGameLockConfig.js"></script>
</body>