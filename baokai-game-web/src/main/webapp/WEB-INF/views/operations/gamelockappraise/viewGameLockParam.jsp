<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>变价  封锁数据</title>

</head>
<body>
<div id="tab_menu_id" style="display:none">changeMenu</div>	
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>变价参数设定
			</div></div>
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
								<c:if test="${lotteryId==99109}">
									<li><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${p3}">变价</a></li>
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${p3}">变价参数设定</a></li>
								</c:if>
								<c:if test="${lotteryId!=99109}">
									<li><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">变价</a></li>
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${lotteryId}">变价参数设定</a></li>
								</c:if>
								</ul>
								
								
								

								
								
								
								<h3 class="ui-title">
									<spring:message code="gameCenter_caizhongmingcheng" />：
									<select id="J-select" class="ui-select w-2">
										<c:if test="${lotteryId==99108 }"><option value="99108" selected="selected">3D</option></c:if>
										<c:if test="${lotteryId!=99108 }"><option value="99108">3D</option></c:if>
										<c:if test="${p3==99109 }"><option value="99109" selected="selected">排列5</option></c:if>	
										<c:if test="${p3!=99109 }"><option value="99109">排列5</option></c:if>
										<c:if test="${p3==99110 }"><option value="99110" selected="selected">排列3</option></c:if>	
										<c:if test="${p3!=99110 }"><option value="99110">排列3</option></c:if>
									</select>
									
									<a href="${currentContextPath}/gameoa/toEditGameLockParam?lotteryid=${p3}" class="btn btn-important"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
									<c:if test="${gameLockParam.status==1 || gameLockParam.status==3 }">
									<a href="${currentContextPath}/gameoa/toAuditGameLockParam?lotteryid=${p3}" class="btn btn-important">审核<b class="btn-inner"></b></a>
									</c:if>
									<c:if test="${gameLockParam.status==2 }">
									<a id="J-button-publish" href="#" class="btn btn-important">发布<b class="btn-inner"></b></a>
									</c:if>
								</h3>
								
								
								
								<form id="J-form" action="${currentContextPath}/gameoa/publishGameLock" method="post">
								<input id="J-type" type="hidden" value="publish" />
								<input id="id" type="hidden" name="id" value="${gameLockParam.id }">
								<input id="minVal" type="hidden" name="minVal" value="${gameLockParam.minValProcess }">
								<input id="startTime" type="hidden" name="startTime" value="${gameLockParam.startTimeProcess}">
								<input id="endTime" type="hidden" name="endTime" value="${gameLockParam.endTimeProcess}">
								<input id="gameId" type="hidden" name="gameId" value="${p3} ">
								<div class="table-cont">
								
								<h3 class="ui-title" style="text-align:left;border-bottom:none;font-weight:normal;">
									<spring:message code="gameCenter_zhuangtai" />：<c:if test="${gameLockParam.status==1 }">
									<span class="color-red">待审核</span>
									&nbsp;&nbsp;
									修改人：${gameLockParam.modifier}
									</c:if>
									<c:if test="${gameLockParam.status==2 }">
									<span class="color-red">待发布</span>
									&nbsp;&nbsp;
									修改人：${gameLockParam.modifier}
									&nbsp;&nbsp;
									审核人：${gameLockParam.operator}
									</c:if>
									<c:if test="${gameLockParam.status==3 }">
									<span class="color-red"><spring:message code="gameCenter_shenhebutongguo" /></span>
									</c:if>
									<c:if test="${gameLockParam.status==4 }">
									<span class="color-red">已发布</span>
									</c:if>
									
								</h3>
									
									<table class="table table-border table-params">
										<tbody><tr>
											<th colspan="2"><b>↓ 向下变价参数</b></th>
										</tr>
										<tr>
											<td width="240" class="table-align-right">极限下调奖金最小值：</td>
											<td>${gameLockParam.minValProcess/10000}</td>
										</tr>
										<tr>
											<td class="table-align-right">变价开始-结束时间：</td>
											<td>${gameLockParam.startTimeProcess}---${gameLockParam.endTimeProcess}</td>
										</tr>
									</tbody></table>
									
									<br />
									
									<table class="table table-border table-params">
										<tbody><tr>
											<th colspan="2"><b>↑ 向上变价参数</b></th>
										</tr>
										<tr>
											<td width="240" class="table-align-right">靓号区三星直选购买倍数最大倍数：</td>
											<td>5</td>
										</tr>
										<tr>
											<td class="table-align-right">靓号区显示时间：</td>
											<td>18:00:00---20:20:00</td>
										</tr>
										<tr>
											<td class="table-align-right">极限上调奖金公司最小留水：</td>
											<td>-0.5</td>
										</tr>
									</tbody></table>
									
								</div>							
								</form>
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>	
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	<style>
	.ui-label {width:500px;}
	
	.table-params {width:460px;}
	.table-params th {text-align:center;background:#F1F1F1;}
	.table-params .table-align-right {text-align:right;}
								
	.table-cont {padding:10px;text-align:center;}
	.table-chart {}
	.table-chart th {font-weight:bold;background:#F1F1F1;}
	
	.table-cont .input {color:#333;}
	</style>	
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelockappraise/viewGameLockParam.js"></script>
</body>