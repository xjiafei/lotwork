<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>

<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>站内信-待发信息</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/msgSent.js"></script>	
</head>

<body>
<permission:hasRight moduleName="NOTICE_CENTER">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 系统消息管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li><a href="${currentContextPath}/noticeAdmin/goSysMsgManager?msgType=0">已发信息</a></li>
									<li class="current">待发信息</li>
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
								<form action="${currentContextPath}/noticeAdmin/searchMsg" id="J-form">
								<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
								<input type="hidden" name="sendSatus" value="3" >
								<input type="hidden" name="msgType" value="1" >
									<ul class="ui-search">
										<li>
											<label for="" class="ui-label">主题名：</label>
											<input type="text" value="${search.title}" class="input w-3" name="title">
										</li>
										<li>
											<label for="" class="ui-label">操作人：</label>
											<input type="text" value="${search.operater}" class="input w-2" name="operater">
										</li>
										<li class="time">
											<label for="" class="ui-label">发送时间：</label>
											<input id="J-time-start" type="text" value="${search.timeStartStr}" name="timeStartStr" class="input"> - <input id="J-time-end" type="text" value="${search.timeEndStr}" name="timeEndStr" class="input">
										</li>
										<li><a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
									</ul>
								</form>
							
								<table class="table table-info" id="J-data-table">
									<thead>
										<tr>
											<th>主题名</th>
											<th>操作人</th>
											<th>发送时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<c:if test="${!empty list}">
									<tbody>
										<c:forEach items="${list}" var="list" varStatus="status">
										<tr>
											<td>
											<c:if test = "${list.messagePush!='无'}">
												<label class="label" style="color:red;">[消息推送]</label>
											</c:if>
											${list.title}</td>
											<td>${list.operater}</td>
											<td>${list.gmtSendedStr}</td>
											<td>
											<permission:hasRight moduleName="NOTICE_SYSMSGMNG_SEND_VIEW">
												<a href="${currentContextPath}/noticeAdmin/viewMsgManager?id=${list.id}">查看</a>
												</permission:hasRight>
												<permission:hasRight moduleName="NOTICE_SYSMSGMNG_SEND_CANCEL">
												<a href="#" class="button-del" data-id="${list.id}">删除</a>
												</permission:hasRight>
											</td>
										</tr>
										</c:forEach>
									</tbody>
									</c:if>
								</table>
								<c:if test="${!empty list}">
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>		
								</c:if>
								<c:if test="${empty list}">
								<div class="alert alert-waring">
									<i></i>
									<div class="txt">
										<h4>没有符合条件的记录，请更改查询条件！</h4>
									</div>
								</div>
								</c:if>
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
</permission:hasRight>
</body>