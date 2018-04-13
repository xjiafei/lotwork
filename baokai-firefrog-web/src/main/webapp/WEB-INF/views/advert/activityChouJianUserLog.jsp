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
			<!-- 从被装饰页面获取body标签内容 -->
			
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">活动</a>>抽奖用户查询
			</div>
			</div>
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
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityChouJianUserLog">抽奖用户查询</a></li>
								</permission:hasRight>
								<permission:hasRight moduleName="MARKET_ACTIVITY_ZHONGJIANG">
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityChouJianLog">中奖结果查询</a></li>
                                </permission:hasRight>
                                <permission:hasRight moduleName="MARKET_ACTIVITY_CONFIG">
									<li ><a href="${currentContextPath}/adAdmin/queryActivityConfig">中奖概率配置</a></li>
								</permission:hasRight>
								</ul>
							</div>
							<div class="clearfix">
						<form action="queryActivityChouJianUserLog" method="post" id="J-search-form">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<input type="text" id="userName" name="userName" value="${userName}"class="input w-3">
							</li>
							<li>
							<input id="tbsubmit" class="btn btn-primary" type="submit" value="搜 索">
								
							
							</li>
						</ul>
					
						<table class="table table-info" id="J-table">
							<thead>
							
								<tr>
									<th>用户名</th>
									<th>总抽奖次数</th>
									<th>未使用次数</th>
									<th>已使用次数</th>
									<th>中奖次数</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${struc!=null}">
							<c:forEach items="${struc}" var="struc" varStatus="configIndex">
								<tr>
	
									<td>${struc.account}</td>
									<td>${struc.totalLotteryCount}</td>
									<td>${struc.unUseCount}</td>
									<td>${struc.useCount}</td>
									<td>${struc.awardCount}</td>
								</tr>
								</c:forEach>
                      		</c:if>
							</tbody>
						</table>
							<c:if test="${page!=null}">
				        	<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
							</c:if>
					</form>		
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(10)').attr("class","current");
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
</body>