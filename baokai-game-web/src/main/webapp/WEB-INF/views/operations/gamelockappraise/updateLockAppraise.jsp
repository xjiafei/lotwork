<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>修改变价方案  封锁数据</title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">changeMenu</div>		
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>修改变价方案
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
									<li class="current"><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">变价</a></li>
									<li><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${lotteryId}">变价参数设定</a></li>
								</ul>
								
								<h3 class="ui-title">
									<a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">&lt;&lt;返回变价方案列表</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									修改方案
								</h3>
								
								<form id="J-form" action="<%=request.getContextPath() %>/gameoa/editGameLockAppraise" method="post">
								<h3 class="ui-title">
									变价方案名称:
									<input type="text" name="title" class="input w-5" style="color:#666;" value="${title }" />
									<input id="J-input-value-up" name="param-up" type="hidden" value="" />
									<input id="J-input-value-down" name="changeStruc" type="hidden" value="" />
									<input id="id" name="id" value="${id }" type="hidden">
									<input id="gameId" name="gameId" type="hidden" value="${lotteryId}"/>
								</h3>
								</form>
								
								<div class="table-cont">
									<table data-type="up" class="table table-border table-chart" border="0" cellspacing="0" cellpadding="0">
									<tbody id="J-table-chart-up">
									</tbody>
									</table>
									<div id="J-chart-up" class="chart-cont"></div>
								</div>
								
								
								
								<div class="table-cont">
									<table data-type="down" class="table table-border table-chart" border="0" cellspacing="0" cellpadding="0">
									<tbody id="J-table-chart-down">
										
									</tbody>
									</table>
									<div id="J-chart-down" class="chart-cont"></div>
								</div>
						
						
								
								<div class="page-wrapper clearfix" style="padding:10px">
									<a class="btn btn-important" href="#" id="J-button-submit">保 存<b class="btn-inner"></b></a>
									&nbsp;&nbsp;
									<a class="btn" href="#" id="J-button-reset">重 置<b class="btn-inner"></b></a>
								</div>
								
								
						
								<div style="height:200px;"></div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
	
	

<div id="J-chart-tip" class="chart-tip"></div>




<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	<style>
	.table-params {width:300px;display:inline-block;}
	.table-params th {text-align:center;background:#F1F1F1;}
	.table-params .table-align-right {text-align:right;}
								
	.table-cont {padding:10px;}
	.table-chart {display:inline-block;width:800px;}
	.table-chart th {font-weight:bold;background:#F1F1F1;}
	
	.chart-cont {width:765px;height:357px;display:inline-block;}
	.chart-tip {position:absolute;left:0;top:2px;z-index:500;background:#FFF;border:1px solid #CCC;padding:5px;line-height:150%;display:none;}
	
	.input {color:#F00;}
	
	.table-chart tr:hover td {background:#FFFFE1;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.flot.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.flot.crosshair.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelockappraise/updateLockAppraise.js"></script>
</body>