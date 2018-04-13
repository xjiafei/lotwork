<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title><spring:message code="gameCenter_caizhongxinxiliebiao" /></title>
</head>
<body>
<permission:hasRight moduleName="GAME">
	<div class="col-main">
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>
			</div>
			</div>
			
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table class="table table-info">
							<thead>
								<tr>
									<th><spring:message code="gameCenter_paixu" /></th>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_zhuangtai" /></th>
									<th><spring:message code="gameCenter_shangxianshijian" /></th>
									<th><spring:message code="gameCenter_zuijinxiugaishijian" /></th>
									<th><spring:message code="gameCenter_caozuoxiang" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${lotteryList}" var="gameseries" varStatus="lotterys" >
									<tr>
										<td>${lotterys.index}</td>
										<td>${gameseries.lotteryName }</td>
										<c:if test="${gameseries.status==1 }"><td class="color-green">可销售</td></c:if>
										<c:if test="${gameseries.status==0 }"><td class="color-red">停售</td></c:if>
										<td>${gameseries.createTime }</td>
										<td>${gameseries.updateTime }</td>
										<td>
											<c:if test="${gameseries.lotteryid!=99112&&gameseries.lotteryid!=99306&&gameseries.lotteryid!=99113}">
											<a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=${gameseries.lotteryid}&ruleId="><c:if test="${gameseries.operationChangeStatus[0]==3|| gameseries.operationChangeStatus[0]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_jiangqiguize" /></a>
											</c:if>
											<a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${gameseries.lotteryid}&status=&awardId="><c:if test="${gameseries.operationChangeStatus[1]==3|| gameseries.operationChangeStatus[1]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_jiangjinzu" /></a>
											<c:if test="${gameseries.lotteryid==99108||gameseries.lotteryid==99109 }">
												<a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[2]==3|| gameseries.operationChangeStatus[2]==4}"><span class="color-red"></c:if>封锁</a>
												<a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[3]==3|| gameseries.operationChangeStatus[3]==4}"><span class="color-red"></c:if>变价</a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99401 || gameseries.lotteryid==99701}">
												<a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[2]==3|| gameseries.operationChangeStatus[2]==4}"><span class="color-red"></c:if>封锁</a>
											</c:if>
											
											<c:if test="${gameseries.lotteryid!=99108&&gameseries.lotteryid!=99109&&gameseries.lotteryid!=99401&&gameseries.lotteryid!=99701 }">
											<a href="${currentContextPath}/gameoa/toBetLimit?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[4]==3|| gameseries.operationChangeStatus[4]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_touzhuxianzhi" /></a>
											</c:if>
											<a href="${currentContextPath}/gameoa/toHelp?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[5]==3|| gameseries.operationChangeStatus[5]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_wanfamiaoshu" /></a>
											<a href="${currentContextPath}/gameoa/toSellingStatus?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[6]==3|| gameseries.operationChangeStatus[6]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_xiaoshouzhuangtai" /></a>
											<c:if test="${gameseries.lotteryid!=99112&&gameseries.lotteryid!=99306&&gameseries.lotteryid!=99113}">
											<a href="${currentContextPath}/gameoa/toSeriesConfig?lotteryid=${gameseries.lotteryid}"><c:if test="${gameseries.operationChangeStatus[7]==3|| gameseries.operationChangeStatus[7]==4}"><span class="color-red"></c:if><spring:message code="gameCenter_canshushezhi" /></a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99106&&killMode==1}">
											    <a id="stopAllKill">停止全部风控<a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99106&&killMode!=1}">
											     <a id="startAllKill">开启全部风控<a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99112&&mmcKillMode==1}">
											    <a id="stopKill">停止风控<a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99112&&mmcKillMode!=1}">
											     <a id="startKill">开启风控<a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99306&&mc115KillMode==1}">
											    <a id="mc115stopKill">停止风控<a>
											</c:if>
											<c:if test="${gameseries.lotteryid==99306&&mc115KillMode!=1}">
											     <a id="mc115startKill">开启风控<a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
		</div>
	<script type="text/javascript">
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	function sendUrl(url,data,toUrl){
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			cache: false,
			success:function(){
// 					window.location.reload();
					location.href = toUrl;
			},
			error: function(){
				
			}
		});
	}
	$(document).ready(function(){
	message = phoenix.Message.getInstance(),
	
	$('#startAllKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认开启全部风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/startAllKillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#stopAllKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认停止全部风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/stopAllKillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});	
	
	
	$('#startKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认开启风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/startKillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#stopKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认停止风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/stopKillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	
	$('#mc115startKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认开启风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/startMc115KillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});		
	
	$('#mc115stopKill').click(function(e){		
		message.show({
			title:'温馨提示',
			mask : true,
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认停止风控吗？</h4></div>',
			confirmText: '确认',
			confirmIsShow: true,
			confirmFun: function(){
				
				
				var url = "${currentContextPath}" + "/gameoa/stopMc115KillMode";
				var jsonStr = "";
				var data = jsonStr;
				var toUrl = "${currentContextPath}" + "/gameoa/lotteryList";
				sendUrl(url, data, toUrl);
			},
			closeText: '关闭',
			closeIsShow: true,
			closeFun: function(){
				var me =this;
				me.hide();
			}
		})
	});	
	});
	
	
	</script>
	</permission:hasRight>
</body>



