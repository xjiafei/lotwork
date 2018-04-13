<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>站内信-用户消息管理</title>
<style>
.j-ui-tip {
	background: #FFFFE1;
	border: 1px solid #CCC;
	color: #333;
	font-size: 12px;
}

.j-ui-tip .sj {
	display: none;
}

.ui-form-window li {
	margin-top: 5px;
	margin-bottom: 5px;
}

.pop-window-datepicker {
	z-index: 710;
}
</style>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/notice/msgUser.js"></script>
</head>

<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a
				href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a>
			&gt; 用户消息管理
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						<form action="${currentContextPath}/noticeAdmin/searchUserMsg"
							id="J-form" method="post">
							<input type="hidden" name="pageNo" value="${page.pageNo}"
								id="pageNo">
							<ul class="ui-search">
								<li><select class="ui-select" name="userType">
										<option value="1" <c:if test="${userType==1 }">selected</c:if>>发件人</option>
										<option value="2" <c:if test="${userType==2 }">selected</c:if>>收件人</option>
								</select> <input type="text"
									<c:if test="${userType==1 }">value="${search.sender}"</c:if>
									<c:if test="${userType==2}">value="${search.receiver}"</c:if>
									class="input w-2" name="sender"></li>
								<li class="time"><label for="" class="ui-label">发送时间：</label>
									<input id="J-time-start" type="text" value="${start}"
									class="input" name="sendTimeStartStr"> - <input
									id="J-time-end" type="text" value="${end}" class="input"
									name="sendTimeEndStr"></li>
								<li><a id="J-button-submit" class="btn btn-important"
									href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
							</ul>
						</form>

						<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th>发件人</th>
									<th>收件人</th>
									<th>内容</th>
									<th>发送时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
										<td>${list.operator}</td>
										<td>${list.acceptUser}</td>
										<td>${list.content} <c:if test="${list.repayCount!=1 }"> (${list.repayCount })</c:if></td>
										<td>${list.sentTime}</td>
										<td><permission:hasRight
												moduleName="NOTICE_USERMSGMANAGER_VIEW">
												<a
													href="${currentContextPath}/noticeAdmin/searchUserMsgDetail?id=${list.id}">查看</a>
											</permission:hasRight></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${page.totalCount<=0 }">
							<div class="alert alert-waring">
								<i></i>
								<div class="txt">
									<h4>没有符合条件的记录，请更改查询条件！</h4>
								</div>
							</div>
						</c:if>
						<c:if test="${page.totalCount>0 }">
							<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}"
								pageSize="${page.pageSize}" />
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
</body>