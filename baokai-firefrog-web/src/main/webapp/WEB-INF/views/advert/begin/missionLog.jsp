<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>查询日志</title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Countdown.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Common.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.EditConfirm.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.pagination.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/sortabletable.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js" ></script>
	
	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	var global_path_url='${staticFileContextPath}/static';
	</script>
<title>查询日志</title>
	<style>
		.panel-field-urgent {display:none;}
		.ui-form .J-panel-group {margin:0;}
		.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
		.checkbox-list-last {border:0;}
	</style>
</head>
<body>
			<div class="col-content">
				<div class="col-main">
					<div class="main" id="DivRules">
					<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
					<input type="hidden" name="type" value="${type}" id="type">					
						<table class="table table-info" id="questionTbd">
							<thead>
								<tr>
									<th class="text-center w-4">修改时间</th>
									<th class="text-center w-4">修改内容</th>
									<th class="text-center w-4">修改前</th>
									<th class="text-center w-4">修改后</th>
									<th class="text-center w-4">操作员</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logs}" var="log" varStatus="status">
								<tr>
									<td class="text-center w-4" >${log.updateTimeStr}</td>																	
									<td class="text-center w-4" >${log.title}</td>									
									<td class="text-center w-4" >${log.beforeUpdate}</td>
									<td class="text-center w-4" >${log.afterUpdate}</td>
									<td class="text-center w-4" >${log.logUser}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>	
						</div>
						<div align="center">
							<button class='btn'  id='close' style='position:initial' >关闭</button>
						</div>
					</div>
				</div>
				
<script>

$("#close").click(function(){
	window.close();
})
	
function doPre(){
	var currentPageNo = $("#pageNo").val();
	location.href= "${currentContextPath}/begin/toBeginLog?pageNo="+(parseInt(currentPageNo)-1)+"&type="+$("#type").val();
	location.assign();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	location.href= "${currentContextPath}/begin/toBeginLog?pageNo="+(parseInt(currentPageNo)+1)+"&type="+$("#type").val();
	location.assign();
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
	location.href= "${currentContextPath}/begin/toBeginLog?pageNo="+$("#pageNo").val()+"&type="+$("#type").val();
	location.assign();
}

function doCurrent(pageNo){
	location.href= "${currentContextPath}/begin/toBeginLog?pageNo="+pageNo+"&type="+$("#type").val();
	location.assign();
}	
	
</script>				
				
</body>