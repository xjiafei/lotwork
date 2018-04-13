<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>活动配置-押大小</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	
	</script>
	
</head>
<body>
	
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">活动</a>>2015春节活动>活动配置>押大小</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
						
						<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
						</div>
						<div class="ui-tab-content ui-tab-content-current">
								<ul class="ui-form">
									<li>
										<label class="ui-label w-1">&nbsp;</label>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">押大小</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=5">转盘抽奖</a></span>
									</li>
								</ul>
						<input type="hidden" id="ratioTip"  name ="ratioTip" value="${ratioAll}"/>			
						<div class="clearfix">
						<form id="J-form" action="updateActivitySheepConfig" method="post">
						<ul class="ui-form " id="J-lotterys-panel">
							<li>
							<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th>奖项</th>
									<th>奖金</th>
									<th>概率</th>
									<th>奖品数量</th>
									<th>已中奖品数量</th>
									<th>剩余奖品数量</th>
								</tr>
							</thead>
							<input type="hidden" id="activityId"  name ="activityId" value="4"/>
							<tbody>
								<c:forEach items="${configs}" var="configs" varStatus="configIndex">
									<tr id="${configs.id }">
									<input type="hidden" id="id"  name ="id" value="${configs.id}"/>
									<input type="hidden" id="winNumber"  name ="winNumber" value="${configs.winNumber}"/>
									<input type="hidden" id="awardName"  name ="awardName" value="${configs.awardName}"/>
										<td>${configs.awardName }</td>										
										<td><input type="input" class="input w-2" id="award"  name ="award" value="${configs.award/10000 }"/></td>
										<c:if test="${configs.awardName== '连中奖' }">
											<td>第<input type="input" class="input w-2" id="ratio"  name ="ratio" value="${configs.ratio/100 }"/>次连中必错</td>
										</c:if>
										<c:if test="${configs.awardName!= '连中奖' }">
											<td><input type="input"  class="input w-2" id="ratio"  name ="ratio" value="${configs.ratio/100 }"/>%</td>
										</c:if>
										<td><input type="input" class="input w-2" id="allNumber"  name ="allNumber" value="${configs.allNumber }"/></td>
										<td>${configs.winNumber }</td>
										<td>${configs.lastNumber }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</li>
						<li>
						<permission:hasRight moduleName="MARKET_ACTIVITY_UPDATEACTIVITYCONFIG">
						<div style="text-align: center;"><input id="submit1" class="btn btn-primary" type="submit" value="保存"></div>
						</permission:hasRight>	
							</li>							
						</ul>
						
						</div>			
					</div>
				</div>
				</form>
				</div>
			</div>
			
		</div>
	
	<script type="text/javascript">
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(17)').attr("class","current");
	var ratioTip = $("#ratioTip").val();
	if(ratioTip!="" && ratioTip != 10000){
		alert("概率不合法,目前合计为"+ratioTip/100+",必须合计为100！请修改后保存。");
	}
	
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(12)').attr("class","current");
	</script>
</body>