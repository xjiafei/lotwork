<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>操作日志</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/operateLog.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>	
</head>

<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt; 操作日志</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					
					<form id="J-form" action="${currentContextPath}/aclAdmin/searchLog">
					<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
						<ul class="ui-search">
						   <li>
								<select class="ui-select" name="searchType">
											<option value="0" <c:if test="${search.searchType==0 }">selected</c:if> >用户名</option>
											<!-- option value="1" <c:if test="${search.searchType==1 }">selected</c:if> >序号</option-->
								</select>
								<input type="text" value="${search.searchValue }" id="J-search-value" class="input w-2" name="searchValue">
							</li>
							<li>
								<label class="ui-label">IP：</label>
								<input type="text" value="${search.ip}" id="J-search-ip" class="input w-2" name="ip">
							</li>
							<li class="time">
								<label for="" class="ui-label">时间：</label>
								<input id="timeStart" type="text" value="${search.startTime}" class="input" name="startTime"> 至 <input id="timeEnd" type="text" value="${search.endTime}" class="input" name="endTime">
							</li>
							<li><a href="javascript:void(0);" id="J-button-submit" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					</form>
						<table class="table table-info">
							<thead>
								<tr>
									<th>序号</th>
									<th>用户名</th>
									<th>日志</th>
									<th>IP</th>
									<th>时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1}</td>
									<td>${list.account}</td>
									<td>${list.detail}</td>
									<td>${list.ip}</td>
									<td>${list.createTime}</td>
									<td><permission:hasRight moduleName="ACL_USER_VIEW_V">
										<a href="${currentContextPath}/aclAdmin/detailLog?id=${list.id}">查看详细</a>
										</permission:hasRight>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>							
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
</body>