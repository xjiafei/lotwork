<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>1.17  封锁数据 </title>

</head>
<body>
<div id="tab_menu_id" style="display:none">lockMenu</div>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>封锁数据
			</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
							<div id="J-list-container">
							
								<ul class="ui-tab-title clearfix">
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁数据</a></li>
									<li><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">封锁参数设定</a></li>
								</ul>
								
								
								
						<form action="${currentContextPath}/gameoa/getGameLockData" method="post" id="J-search-form">		
							<ul class="ui-search">
									<li>
										<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<select id="J-select" class="ui-select w-2" disabled="disabled">
										<c:if test="${request.lotteryId==99108 }"><option value="99108" selected="selected">3D</option></c:if>
										<c:if test="${request.lotteryId!=99108 }"><option value="99108">3D</option></c:if>	
										<c:if test="${request.lotteryId==99109 }"><option value="99109" selected="selected">p3/p5</option></c:if>
										<c:if test="${request.lotteryId!=99109 }"><option value="99109">p3/p5</option></c:if>
										<c:if test="${request.lotteryId==99401 }"><option value="99401" selected="selected">双色球</option></c:if>
										<c:if test="${request.lotteryId!=99401 }"><option value="99401">双色球</option></c:if>	
										</select>
										<input type="hidden" name="lotteryId" value="${request.lotteryId}">
									</li>
									<li>
										<label class="ui-label">奖期：</label>
										<select class="ui-select" name="issueCode">
											<c:forEach items="${issueList}" var="issueCode">
												<c:if test="${issueCode.issueCode== request.issueCode}">
													<option value="${issueCode.issueCode }" selected="selected">${issueCode.webIssueCode}</option>
												</c:if>
												<c:if test="${issueCode.issueCode!= request.issueCode}">
													<option value="${issueCode.issueCode }">${issueCode.webIssueCode}</option>
												</c:if>
											</c:forEach>
										</select>
									</li>
									<li>
										<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">查询<b class="btn-inner"></b></a>
									</li>
								</ul>
								</form>
								<div id="J-content-info">
									<table class="table table-info table-center">
										<thead>
											<tr>
												<th colspan="3">5红球封锁<spring:message code="gameCenter_xiangqing" /></th>
												<th colspan="3">1蓝球球封锁<spring:message code="gameCenter_xiangqing" /></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>号码</td>
												<td>当前注数</td>
												<td>封锁时间</td>
												<td>号码</td>
												<td>当前注数</td>
												<td>封锁时间</td>
											</tr>
											<c:forEach items="${struc.gameSharesStruc }" var="varlist">
											<tr>
												<c:forEach items="${varlist}" var="gameShares">				
												<td>${gameShares.number}</td>
												<td>${gameShares.slipVal}</td>
												<td>${gameShares.lockTime}</td>
												</c:forEach>
											</tr>
											</c:forEach>
										</tbody>
									</table>	
								</div>		
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
<script type="text/javascript">
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#lockMenu').attr("class","current");
	
	$('#J-button-submit').click(function(){
		$('#J-search-form').submit();
	});
</script>	
	
	<style>
	.ui-label {height:24px;}
	.ui-form li {margin:5px 0;}
	</style>
</body>
