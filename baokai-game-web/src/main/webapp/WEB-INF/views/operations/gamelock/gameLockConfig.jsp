<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<title>封锁参数设定  封锁数据</title>
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
						<input type="hidden" id="lotteryId" value="${lotteryId}"/>
						<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toGameLockConfig?lotteryid=">
						
						<h3 class="ui-title">
							<spring:message code="gameCenter_caizhongmingcheng" />：<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
							<option value="99108">3D</option>
							<option value="99109">排列5</option>
							<option value="99401">双色球</option>
							<option value="99701">六合彩</option>									
							</select>
							&nbsp;&nbsp;
							当前状态：<c:if test="${gameLock.status==1 }">
							<span class="color-red">待审核</span>
							</c:if>
							<c:if test="${gameLock.status==2 }">
							<span class="color-red">待发布</span>
							</c:if>
							<c:if test="${gameLock.status==3 }">
							<span class="color-red"><spring:message code="gameCenter_shenhebutongguo" /></span>
							</c:if>
							<c:if test="${gameLock.status==4 }">
							<span class="color-red">进行中</span>
							</c:if>
							&nbsp;&nbsp;
								<c:if test="${gameLock.status!=2 }">
								<a href="${currentContextPath}/gameoa/toEditGameLockConfig?lotteryid=${lotteryId}" class="btn btn-important"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
								</c:if>
								
								<c:if test="${gameLock.status==1 || gameLock.status==3 }">
								<a href="${currentContextPath}/gameoa/toAuditGameLockConfig?id=${gameLock.id}&upVal=${gameLock.upVal}&upValProcess=${gameLock.upValProcess}&lotteryid=${lotteryId}" class="btn btn-important">审核<b class="btn-inner"></b></a>
								</c:if>
								<c:if test="${gameLock.status==2 }">			
								<a id="J-button-submit" href="#" class="btn btn-important">发布<b class="btn-inner"></b></a>
								<a id="J-button-submit_" href="#" class="btn btn-important">不发布<b class="btn-inner"></b></a>
								</c:if>
						</h3>
						
						<form id="J-form" action="${currentContextPath}/gameoa/publishConfig" method="post">
						<ul class="ui-form">
							<li>
								<c:if test="${lotteryId != 99701}">
									<label class="ui-label w-4 ">
									&nbsp;&nbsp;&nbsp;&nbsp;
									最大封锁值：
									</label>
									<c:if test="${lotteryId==99109 }">
										<span class="ui-info" style="padding:0 10px;">p3&nbsp;&nbsp;:&nbsp;&nbsp;
										<c:choose>
											<c:when test="${gameLock.status==1 ||gameLock.status==2}">
											<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal/10000 }">${gameLock.upValProcess/10000}</span>
											</c:when>
											<c:otherwise> ${gameLock.upValProcess/10000 }</c:otherwise>
											</c:choose></span>
											<span class="ui-info" style="padding:0 10px;">p5&nbsp;&nbsp;:&nbsp;&nbsp;<c:choose><c:when test="${gameLock.status==1 ||gameLock.status==2}">
											<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal2/10000 }">${gameLock.upValProcess2/10000}</span>
											</c:when>
											<c:otherwise> ${gameLock.upValProcess2/10000 }</c:otherwise>
										</c:choose>
										</span>
									</c:if>
									
									<c:if test="${lotteryId!=99109 }">
										<span class="ui-info" style="padding:0 10px;">
										<c:choose>
											<c:when test="${gameLock.status==1 ||gameLock.status==2}">
											<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal/10000 }"><fmt:formatNumber type="number" value="${gameLock.upValProcess/10000}"/></span>
											</c:when>
											<c:otherwise><fmt:formatNumber type="number" value="${gameLock.upValProcess/10000}"/></c:otherwise>
										</c:choose>
										</span>	
									</c:if>
								</c:if>
								
								<c:if test="${lotteryId == 99701}">
									<label class="ui-label w-2 big">
									&nbsp;特码&nbsp;
									</label>
									<label class="ui-label w-2 ">
									&nbsp;&nbsp;&nbsp;&nbsp;
									最大封锁值：
									</label>
									<span class="ui-info" style="padding:0 10px;">
									<c:choose>
										<c:when test="${gameLock.status==1 || gameLock.status==2}">
										<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal/10000 }"><fmt:formatNumber type="number" value="${gameLock.upValProcess/10000}"/></span>
										</c:when>
										<c:otherwise><fmt:formatNumber type="number" value="${gameLock.upValProcess/10000}"/></c:otherwise>
									</c:choose>
									</span>	
									<p/>
									
									<label class="ui-label w-2 big">
									&nbsp;正特码_一肖&nbsp;
									</label>
									<label class="ui-label w-2 ">
									&nbsp;&nbsp;&nbsp;&nbsp;
									最大封锁值：
									</label>
									<span class="ui-info" style="padding:0 10px;">
									<c:choose>
										<c:when test="${gameLock.status==1 || gameLock.status==2}">
										<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal2/10000 }"><fmt:formatNumber type="number" value="${gameLock.upValProcess2/10000}"/></span>
										</c:when>
										<c:otherwise><fmt:formatNumber type="number" value="${gameLock.upValProcess2/10000}"/></c:otherwise>
									</c:choose>
									</span>	
									<p/>
									
									<label class="ui-label w-2 big">
									&nbsp;其他玩法&nbsp;
									</label>
									<label class="ui-label w-2 ">
									&nbsp;&nbsp;&nbsp;&nbsp;
									最大封锁值：
									</label>
									<span class="ui-info" style="padding:0 10px;">
									<c:choose>
										<c:when test="${gameLock.status==1 || gameLock.status==2}">
										<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.upVal3/10000 }"><fmt:formatNumber type="number" value="${gameLock.upValProcess3/10000}"/></span>
										</c:when>
										<c:otherwise><fmt:formatNumber type="number" value="${gameLock.upValProcess3/10000}"/></c:otherwise>
									</c:choose>
									</span>	
									<p/>
								</c:if>
								
								<input id="id" type="hidden" name="id" value="${gameLock.id}">
								<input id="upVal" type="hidden" name="upVal" value="${gameLock.upValProcess}">
								<input id="upVal2" type="hidden" name="upVal2" value="${gameLock.upValProcess2}">
								<input id="upVal3" type="hidden" name="upVal3" value="${gameLock.upValProcess3}">
								<input id="gameId" type="hidden" name="gameId" value="${lotteryId} ">
								<input id="status" type="hidden" name="status" value="4">
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

	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/gameLockConfig.js"></script>
</body>