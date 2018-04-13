<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>变价参数审核  封锁数据</title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">changeMenu</div>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>审核变价参数
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
							<div  id="J-list-container">
							
								<ul class="ui-tab-title clearfix">
									<li><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">变价</a></li>
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${lotteryId}">变价参数设定</a></li>
								</ul>
								
								
								
								<h3 class="ui-title">
									<a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${lotteryId}">&lt;&lt;返回参数设置查看页</a>
								</h3>
								
								
								
								<form id="J-form" action="${currentContextPath}/gameoa/auditGameLockParam" method="post">
									<input id="status" name="status" type="hidden" value="2" />
									<input id="id" name="id" type="hidden" value="${gameLockParam.id }">
									<input id="gameId" type="hidden" name="gameId" value="${lotteryId} ">
								<div class="table-cont">
									
									<table class="table table-border table-params">
										<tbody><tr>
											<th colspan="2"><b>↓ 向下变价参数</b></th>
										</tr>
										<tr>
											<td width="240" class="table-align-right">极限下调奖金最小值：</td>
											<td><span class="input-mark w-2" data-showtip="${gameLockParam.minVal/10000 }">${gameLockParam.minValProcess/10000}</span></td>
										</tr>
										<tr>
											<td class="table-align-right">变价开始-结束时间：</td>
											<td><span class="input-mark w-2" data-showtip="${gameLockParam.startTime}">${gameLockParam.startTimeProcess}</span>--<span class="input-mark w-2" data-showtip="${gameLockParam.endTime }">${gameLockParam.endTimeProcess}</span></td>
										</tr>
									</tbody></table>
									
									<br />
									
									<table class="table table-border table-params">
										<tbody><tr>
											<th colspan="2"><b>↑ 向上变价参数</b></th>
										</tr>
										<tr>
											<td width="240" class="table-align-right">靓号区三星直选购买倍数最大倍数：</td>
											<td><span class="input-mark w-2" data-showtip="4">5</span></td>
										</tr>
										<tr>
											<td class="table-align-right">靓号区显示时间：</td>
											<td><span class="input-mark w-2" data-showtip="16:00:00">18:00:00</span>--<span class="input-mark w-2" data-showtip="19:30:00">18:00:00</span></td>
										</tr>
										<tr>
											<td class="table-align-right">极限上调奖金公司最小留水：</td>
											<td><span class="input-mark w-2" data-showtip="4">-0.5</span></td>
										</tr>
									</tbody></table>
									
								</div>		
								
								
								<h3 class="ui-title">
									<a id="J-button-pass" href="#" class="btn btn-important" style="float:left;">审核通过<b class="btn-inner"></b></a>
									<a id="J-button-nopass" href="#" class="btn" style="float:left;"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
								</h3>
													
								</form>
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
			<style>
	.ui-label {width:500px;}
	.j-ui-tip-info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	
	.ui-label {width:500px;}
	
	.table-params {width:500px;}
	.table-params th {text-align:center;background:#F1F1F1;}
	.table-params .table-align-right {text-align:right;}
								
	.table-cont {padding:10px;text-align:center;}
	.table-chart {}
	.table-chart th {font-weight:bold;background:#F1F1F1;}
	
	.table-cont .input {color:#333;}
	
	.input-mark {padding:5px 20px;}
	
	</style>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelockappraise/auditGameLockParam.js"></script>
</body>