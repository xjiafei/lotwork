<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
	<meta charset="UTF-8">
	<title>帮助后台 配置</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/adminHelpConfig.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
</head>
<body>
	<div class="col-content">
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/helpAdmin/goHelpManager">内容中心</a> &gt;<spring:message htmlEscape="true" javaScriptEscape="true" text="${cate2Name}"></spring:message></div></div>
			
			
			<div class="col-content">
			
				<div class="col-main">
					<div class="main">
						<div id="J-panel-help-setting" class="panel-help-setting">
						
							<!-- start -->
							<h3 class="ui-title">热门关键词</h3>
							<div class="info">
								注意：每个关键词最多填写４个文字，序号数字越大越靠前。
							</div>
							<table class="table table-info" id="J-table-keyword">
									<thead>
										<tr>
									<th>关键词</th>
									<th>序号</th>
									<th>预览</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${keywords}" var="keyword" varStatus="status">
										<tr>
											<td><input id="${status.count}" data-type="keyword" data-id="${keyword.id}" class="input w-3" type="text" value='<spring:message htmlEscape="true" javaScriptEscape="true" text="${keyword.keyword}"></spring:message>' /><span class="ui-check"><i></i></span></td>
											<td><input class="input w-1" type="text" value="${keyword.no}" disabled="disabled" /></td>
											<td><a href="javascript:void(0)" onclick="doPreview('${status.count}')">预览</a></td>
										</tr>
									</c:forEach>						
									</tbody>
							</table>
							<!-- end -->
							
							
							<!-- start -->
							<h3 class="ui-title">彩种知识目录</h3>
							<div class="info">
								<a id="J-button-knowledge-add" href="#" class="btn btn-important">添加目录<b class="btn-inner"></b></a>  &nbsp;&nbsp;注意：排序数字越大越靠前，删除目录后会相应的删除绑定在此目录下的所有文章内容。
							</div>
							<table id="J-table-knowledge" class="table table-info">
								<thead>
									<tr>
									<th>名称(限6个字)</th>
									<th>序号</th>
									<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${lotteryCateStrucs}" var="lotteryCateStruc" varStatus="status">
									<tr>
										<td><input data-type="knowledge" data-id="${lotteryCateStruc.id}" class="input w-3" type="text" value='<spring:message htmlEscape="true" javaScriptEscape="true" text="${lotteryCateStruc.name}"></spring:message>' /><span class="ui-check"><i></i></span></td>
										<td><input data-type="knowledgeSort" data-id="${lotteryCateStruc.id}" class="input w-1" type="text" value="${lotteryCateStruc.no}"/><span class="ui-check"><i></i></span></td>
										<td><a href="javascript:void(0)" class=".row-del" name="delete">删除</a></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<script id="J-knowledge-tpl" type="text/html-tmpl"> 
								<tr>
									<td><input data-type="knowledge" data-id="" class="input w-3" type="text" value="" /><span class="ui-check"><i></i></span></td>
									<td><input data-type="knowledgeSort" data-id="" class="input w-1" type="text" value="0"/><span class="ui-check"><i></i></span></td>
									<td><a href="javascript:void(0)" name="delete" class=".row-del">删除</a></td>
								</tr>
							</script>
							
							<!-- end -->
							<!-- start -->
							<h3 class="ui-title">热门彩种排序</h3>
							<div class="info">
								注意：排序数字越大越靠前。
							</div>
							<table class="table table-info" id="J-table-help">
								<thead>
									<tr>
									<th>彩种名称</th>
									<th>序号</th>
									<th><span style="visibility:hidden">操作</span></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${helps}" var="help" varStatus="status">
									<tr>
										<td><input class="input w-3" type="text" value='<spring:message htmlEscape="true" javaScriptEscape="true" text="${help.title}"></spring:message>' disabled="disabled" /></td>
										<td><input data-type="lotterySort" data-id="${help.id}" class="input w-1" type="text" value="${help.no}"/><span class="ui-check"><i></i></span></td>
										<td>&nbsp;&nbsp;</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<!-- end -->							
						</div>						
						<div style="height:500px"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

<script>
function  doDeletePre()
{
	var strArr = [];
	strArr.push('<div class="text">确定要删除该名称吗？</div>');
	strArr.push('<div class="control"><a id="sButton" data-buttontype="1" href="javascript:void(0);" class="btn" onclick="doDelete()">确定<b class="btn-inner"></b></a> <a data-buttontype="0" href="javascript:void(0);" onclick="minWindow.hide()" class="btn">取消<b class="btn-inner"></b></a></div>');
	minWindow.setContent(strArr.join(''));
	minWindow.show();
}
function doDelete()
{
	var tbody = $('#J-table-knowledge').find('tbody')
	var tr = tbody.find('tr').eq(0);
	var id=tr.find('input').eq(0).attr("data-id");
	if(id ==''){
		tr.remove();
	}else{
		var url = baseUrl + "/helpAdmin/deleteLotteryKnowledgeCate";
		var dataStr = "id="+id+"&num="+Math.random();
		$.ajax({
			url:url,
			dataType:'json',
			method:'post',
			//如果id为空，则为添加新彩种知识目录
			//该处有个逻辑问题，当先设置排序时，能不能增加，应视为无效请求
			data:dataStr,
			success:(function(){return function(data){
				if(data.status == 1){
					tr.remove();
				}else if(data.status == 0){
					alert(data.message);
					window.location.reload();
				}
			}})(),
			error:(function(){return function(xhr, type){
				alert(type);
				window.location.reload();
			}})()
		});
	}
	minWindow.hide();
}

function doPreview(id){
	var me = this;
	var keyword = $("#"+id).val();
	keyword = encodeURI(keyword);
	var url = "${frontContextPath}/help/queryListByKey?key="+keyword;
	window.open(url,'前台搜索结果页');
}
</script>
</body>