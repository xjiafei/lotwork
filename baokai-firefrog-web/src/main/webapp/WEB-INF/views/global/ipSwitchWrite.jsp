<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>IP白名单</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/global/ipSwitch.js"></script>
</head>
<body>
<c:choose>
<c:when test="${fn:contains(aclList,'GLOBAL_IPILLEGALITY') || fn:contains(aclList,'GLOBAL_IPLEGAL')}">

			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; IP白名单</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<permission:hasRight moduleName="GLOBAL_IPILLEGALITY"><li><a href="${currentContextPath}/globeAdmin/goIpSwitch?type=0">黑名单设置</a></li></permission:hasRight>
									<permission:hasRight moduleName="GLOBAL_IPLEGAL"><li class="current">白名单设置</li></permission:hasRight>
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
							
								<div style="padding:10px 0 0 10px;">
									<label>状态：</label>
									<div id="J-switch" class="switch has-switch">
										<div <c:if test="${wStatus==1}">class="switch-on"</c:if><c:if test="${wStatus==0}">class="switch-off"</c:if>>
											<span class="switch-left">开启</span><label>&nbsp;</label><span class="switch-right">关闭</span>
											<input class="switch-type" type="hidden" value="1" />
											<input class="switch-value" type="hidden" value="${wStatus}" />
										</div>
									</div>
								</div>
							
								<ul class="prompt" style="margin:10px 10px 0;">
									<li>通过添加白名单IP控制登录用户，启用白名单模式后，只有白名单IP内的用户能访问平台。</li>
									<li>可以使用“*”作为通配符禁止某段地址。</li>
								</ul>
								<form action="${currentContextPath}/globeAdmin/createIp" id="J-form">
								<input id="type" name="type" type="hidden" value="1">
								<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
								<ul class="ui-search">
									<li id="J-input-ip">
										<label for="" class="ui-label">+新增：</label>
										<input type="text" name="ip1" class="input w-1"  value="" style="text-align:center;"> . 
										<input type="text" name="ip2" class="input w-1"  value="" style="text-align:center;"> . 
										<input type="text" name="ip3" class="input w-1"  value="" style="text-align:center;"> . 
										<input type="text" name="ip4" class="input w-1"  value="" style="text-align:center;">
									</li>
									
									<li>
										<label for="" class="ui-label">有效期：</label>
										<input id="J-days" name="period" type="text" class="input w-1"  maxlength="5" value="" style="text-align:center;">
										<span class="ui-info">天</span>
									</li>
									<li><a href="javascript:void(0);" id="J-submit" class="btn btn-important">提 交<b class="btn-inner"></b></a></li>
								</ul>
								</form>
								
								<table class="table table-info" id="J-table-list">
									<thead>
										<tr>
											<th class="w-1"></th>
											<th>IP地址</th>
											<th>地理位置</th>
											<th>操作者</th>
											<th>起始时间</th>
											<th>终止时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									
									<c:forEach items="${list}" var="list" varStatus="status">
										<tr>
											<td><input type="checkbox" class="checked-row" value="${list.id}"></td>
											<td>${list.ip}</td>
											<td>${list.area}</td>
											<td>${list.operator}</td>
											<td>${list.effectTime}</td>
											<td>${list.expireTime}</td>
											<td><a href="javascript:void(0)" class="ip-list-delete" data-id="${list.id}">删除</a></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<div class="page-wrapper clearfix" style="float:left;">
									<span class="page-text">
										<label class="label"><input id="J-select-all" type="checkbox" class="checkbox">全选</label>
										<permission:hasRight moduleName="GLOBAL_IPLEGAL">
										<a class="btn btn-small" href="javascript:void(0);" id="J-delete-all">删除<b class="btn-inner"></b></a>
										</permission:hasRight>
									</span>
								</div>
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>		
								<br style="clear:both;">
							</div>
						</div>
						
					</div>
				</div>
			</div>
<script>
function doPre(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)-1);
	$("#J-form").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)+1);
	$("#J-form").submit();
}

function doForward(index){
    if(index == -1){
    	var reg = /^[0-9]+$/;
    	if(reg.test($("#forwardPage").val())){
		$("#pageNo").val(parseInt($("#forwardPage").val()));}
    	else{
    		return;
    	}
    }else{ 
    	$("#pageNo").val(index);
    } 
	$("#J-form").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#J-form").submit();
}
</script>	
</c:when>
<c:otherwise>
<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; IP黑白名单</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<div class="alert alert-waring">
								<i></i>
								<div class="txt">
									<h4>权限不足，请联系管理员。</h4>
								</div>
							</div>
					</div></div></div></div>
</c:otherwise>
</c:choose>			
</body>