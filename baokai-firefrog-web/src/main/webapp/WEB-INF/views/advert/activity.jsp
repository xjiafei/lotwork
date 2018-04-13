<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>平台升级活动</title>
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
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">活动</a><a href="#">>抽大奖</a>>配置</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
						
						<div class="ui-tab-title clearfix">
								<ul>
								<permission:hasRight moduleName="MARKET_ACTIVITY_HONGBAO">
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLog">红包明细查询</a></li>
								</permission:hasRight>
								<permission:hasRight moduleName="MARKET_ACTIVITY_CHONGJIANG">
									<li><a href="${currentContextPath}/adAdmin/queryActivityChouJianUserLog">抽奖用户查询</a></li>
								</permission:hasRight>
								<permission:hasRight moduleName="MARKET_ACTIVITY_ZHONGJIANG">
                                    <li><a href="${currentContextPath}/adAdmin/queryActivityChouJianLog">中奖结果查询</a></li>
                                </permission:hasRight>
                                <permission:hasRight moduleName="MARKET_ACTIVITY_CONFIG">
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityConfig">中奖概率配置</a></li>
								</permission:hasRight>
								</ul>
						</div>
						<input type="hidden" id="ratioTip"  name ="ratioTip" value="${ratioAll}"/>			
						<div class="clearfix">
						<form id="J-form" action="updateActivityConfig" method="post">
						<ul class="ui-form " id="J-lotterys-panel">
							<li>
							<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th>奖品名称</th>
									<th>中奖概率</th>
									<th>中奖倍数</th>
									<th>总数量</th>
									<th>剩余数量</th>
									<th>已抽出数量</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${configs}" var="configs" varStatus="configIndex">
									<tr id="${configs.id }">
										<input type="hidden" id="id"  name ="id" value="${configs.id}"/>										
										<td>${configs.awardName}</td>
										<td><input type="input" id="ratio"  name ="ratio" value="${configs.ratio/100 }"/></td>
										<td><input type="input" id="multiple"  name ="multiple" value="${configs.multiple/100 }"/></td>
										<td>${configs.allNumber }</td>
										<td><input type="input" id="lastNumber"  name ="lastNumber" value="${configs.lastNumber }"/></td>
										<td>${configs.winNumber }</td>
									</tr>
								</c:forEach>
						
							</tbody>
						</table>
						</li>
						<li>
						<div style="text-align: center;"><input id="submit" class="btn btn-primary" type="submit" value="保存"></div>	
							</li>							
						</ul>
						
						</div>			
					</div>
				</div>
				</form>
			</div>
			
		</div>
	
	<script type="text/javascript">
	var ratioTip = $("#ratioTip").val();
	if(ratioTip!="" && ratioTip != 10000){
		alert("概率不合法,目前合计为"+ratioTip/100+",必须合计为100！请修改后保存。");
	}
	
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(12)').attr("class","current");
	</script>
</body>