<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">	
<head>
	<meta charset="UTF-8">
	<title>权限组管理</title>
	<style>
	.table-info th {font-weight:bold;}
	.table-info tr:hover {background:#FFFFE1;}
	.table-tool a {margin:0}
	.table-info .ico-nochild {background:url(${staticFileContextPath}/static/images/common/Sys_ModuleIcos.png) no-repeat 0 -64px;width:19px;height:19px;margin:0 2px;padding:0;margin-left:19px;}
	.table-tool .ico-fold,.table-tool .ico-unfold {width:38px;height:19px;background:url(${staticFileContextPath}/static/images/common/Sys_ModuleIcos.png) no-repeat 12px -26px;}
	.table-tool .ico-unfold {background-position:3px 3px;}
	
	
	.table-info td {padding-top:0;padding-bottom:0;}
	.table-tool a { cursor:default;}
	.table-tool .ico-fold, .table-tool .ico-unfold,.table-info .ico-nochild {height:32px;}
	.table-info tr td {border-bottom:0;}
	.table-info tr:nth-child(even) {background-color:#FFF;}
	.table-info tr:nth-child(even):hover {background-color:#FFFFE1;}
	
	.table-info tr.row-space-1 {border-bottom:1px dotted #E3E3E3;border-top:1px dotted #E3E3E3;background:#F9F9F9;}
	.table-info tr.row-space-1:hover {background-color:#FFFFE1;}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/aclGroupAuthManage.js"></script>
</head>
<body>
	<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt; 权限组管理</div></div>
	<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>权限组名</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>最后修改人</th>
									<th>最后修改时间</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${groups}" var="group" varStatus="status">
								<tr data-pid="${group.pid}" data-id="${group.id}" data-name="${group.name}" data-inUser="${group.inUser}" class="row-space-${group.lvl}">
									<td class="table-tool"><span class="row-space"></span><a href="javascript:void(0)" class="row-control ico-fold"></a><spring:message htmlEscape="true" javaScriptEscape="true" text="${group.name}"></spring:message></td>
									<td>${group.creatorer}</td>
									<td><c:if test="${group.gmtCreated != null }"><fmt:formatDate value="${group.gmtCreated}" type="both" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
									<td>${group.modifierer }</td>
									<td><c:if test="${group.gmtModified != null }"><fmt:formatDate value="${group.gmtModified}" type="both" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
									<c:if test="${group.inUser == 1 }"><td>正常</td></c:if>
									<c:if test="${group.inUser != 1 }"><td class="red-color"><span style="color:red">禁用</span></td></c:if>
									<td>
									<permission:hasRight moduleName="ACL_GM_VIEW">
										<a id="showGroupAuth" style="cursor: hand;cursor: pointer;">查看组权限</a>
										</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_VIEW_MEMBER">
										<a id="showGroupUser" style="cursor: hand;cursor: pointer;">查看组成员</a>
											</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_COPY">
										<a id="copyGroup" style="cursor: hand;cursor: pointer;">复制组</a>
											</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_MODIFY">
										<a id="updateGroup" style="cursor: hand;cursor: pointer;">修改组</a>
											</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_ADD">
										<a id="addSubGroup" style="cursor: hand;cursor: pointer;">增加子组</a>
											</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_FORBIDDEN">
										<c:if test="${group.inUser == 1 }">
										<a href="javascript:void(0)" class="row-action-close">禁用组</a>
										</c:if>
										
										<c:if test="${group.inUser != 1 }">
										<a href="javascript:void(0)" class="row-action-open">启用组</a>
										</c:if>
											</permission:hasRight>
										<permission:hasRight moduleName="ACL_GM_DEL">
										<a href="javascript:void(0)" class="row-action-delete">删除组</a>
											</permission:hasRight>
									</td>
								</tr>
							</c:forEach>		
							</tbody>
						</table>
						<form id="queryForm" action="${currentContextPath}/aclAdmin/queryAclGroup?userId=1" method="post">
						<!-- 分页标签start  -->					
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						<!-- 分页标签end  -->
						</form>
					</div>
				</div>
				</div>
<script>
function doPre(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)-1);
	$("#queryForm").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)+1);
	$("#queryForm").submit();
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
	$("#queryForm").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#queryForm").submit();
}
</script>
</body>
</html>