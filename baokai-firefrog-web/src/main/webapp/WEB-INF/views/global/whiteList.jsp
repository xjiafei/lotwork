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
		
		<title>指定IP白名單</title>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/global/whiteList.js"></script>
		<script>
			var currentPath = "${currentContextPath}";
		</script>
	</head>
	<body>
<c:choose>
<c:when test="${fn:contains(aclList,'GLOBAL_WHITELISTIP')}">
		<div class="col-crumbs">
			<div class="crumbs" style><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 指定IP列表 </div>
			<div style="top: 0px; height: 50px; right: 15px; padding-top: 3px; float: right; position: absolute;">
			<permission:hasRight moduleName="GLOBAL_WHITELISTIPADD">
				<a class="btn btn-important" id="J-button-Add_Page" 
					style="width: 63px; height: 30px; line-height: 30px; font-size: 12px;" href="${currentContextPath}/globeAdmin/addWhiteList">
					新增指定IP</a>
			</permission:hasRight>
				
			</div>
		
		</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-whiteList-form" action="${currentContextPath}/globeAdmin/searchWhiteList" method="post">
							<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
							<input type="hidden" name="ids" value="" id="ids">
							<input type="hidden" value="${search.type}" id="typeVal">
							<permission:hasRight moduleName="GLOBAL_WHITELISTIPFIND">
								<ul class="ui-search">						
									<li><label class="ui-label">請選擇：</label> <select
										class="ui-select" name="type" id="type" >
											<option value="0">用户名</option>
											<option value="1">IP地址</option>
										</select></li>
									<li>
										<input id="J-search-input" name="word" type="text" class="input" value="">
										<input type="hidden" value="${search.word}" id="J-hidden-word">
									</li>								
									<li><a href="javascript:void(0);" id="J-button-submit"
										class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
								</ul>	
							</permission:hasRight>
						<div class="ui-tab">
							<div id="J-side-menu" class="ui-tab-title clearfix" style="border-bottom:1px solid #DEDEDE;">
								<ul>
									<li<c:if test="${search.type==null}">class="current"</c:if> style="cursor:default;"  >指定IP列表</li>
								</ul>
							</div>
						</div>	
						<table class="table table-info" id="J-table-list">
									<thead>
										<tr>
											<th class="w-1"></th>
											<th>IP地址</th>
											<th>用户名</th>
											<th>地理位置</th>
											<th>操作者</th>
											<th>操作时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test="${page.totalCount ==0 }">
											<tr><td colspan=7><div style="color:red;font-size:xx-large;font-weight:bolder;text-align:center;">没有符合条件的记录</div></td></tr>										
										</c:when>
										<c:otherwise>
											<c:forEach items="${ipList}" var="ipList" varStatus="status">
												<tr>
													<td><input type="checkbox" class="checked-row" value="${ipList.id}"></td>
													<td>${ipList.ipAddr}</td>
													<td class="user">${ipList.userAcunt}</td>
													<td>${ipList.country}</td>
													<td>${ipList.operator}</td>
													<td>${ipList.operationTime}</td>
													<td>
													<permission:hasRight moduleName="GLOBAL_WHITELISTIPVIEW">
														<a href="javascript:void(0)" class="ip-list-show" data-id="${ipList.id}" >查看</a>
													</permission:hasRight>
													<permission:hasRight moduleName="GLOBAL_WHITELISTIPEDIT">
														<a href="javascript:void(0)" class="ip-list-edit" data-id="${ipList.id}">修改</a>
													</permission:hasRight>
													<permission:hasRight moduleName="GLOBAL_WHITELISTIPDEL">
														<a href="javascript:void(0)" class="ip-list-delete" data-id="${ipList.id}" >删除</a>
													</permission:hasRight>
												</td>
												</tr>
											</c:forEach>
										</c:otherwise>
								</c:choose>	
								</tbody>
								</table>
								<div class="page-wrapper clearfix" style="float:left;">
									<span class="page-text">
										<label class="label"><input id="J-select-all" type="checkbox" class="checkbox">全选</label>
										<permission:hasRight moduleName="GLOBAL_WHITELISTIPDEL">
											<a class="btn btn-small" href="javascript:void(0);" id="J-delete-all">删除<b class="btn-inner"></b></a>
										</permission:hasRight>
									</span>
								</div>
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>		
								<br style="clear:both;">
								历史记录（显示最近十条）<br style="clear:both;">	
								<table class="table table-info" id="J-table-log-list">
									<thead>
										<tr>
											<th>IP地址</th>
											<th>用户名</th>
											<th>地理位置</th>
											<th>操作者</th>
											<th>操作时间</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									
									<c:forEach items="${logList}" var="logList" varStatus="status">
										<tr>
											<td>${logList.whiteListIP}</td>
											<td class="user">${logList.accunt}</td>
											<td>${logList.country}</td>
											<td>${logList.operator}</td>
											<td>${logList.operationTime}</td>
											<td>${logList.status}</td>
											<td>
												<permission:hasRight moduleName="GLOBAL_WHITELISTIPVIEW">
													<a href="javascript:void(0)" class="log-list-show" data-id="${logList.listIP}" >查看</a>
												</permission:hasRight>
											</td>

										</tr>
									</c:forEach>
									</tbody>
								</table>
						</form>		
							
					</div>
				</div>
			</div>
<script>
function doPre(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)-1);
	$("#J-whiteList-form").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)+1);
	$("#J-whiteList-form").submit();
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
	$("#J-whiteList-form").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#J-whiteList-form").submit();
}
</script>
</c:when>
<c:otherwise>
<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 指定IP列表</div></div>
			<div class="col-content">
				<div class="col-main">
					 <div class="main">
					 <div class="alert alert-waring">
                     <i></i>
                     <div class="txt">
                        <h4>权限不足，请联系管理员。</h4>
                     </div>
					 </div>			
                     </div>
                </div>
            </div>
</c:otherwise>
</c:choose>		
	</body>
</html>