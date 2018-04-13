<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>

<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>帮助后台管理首页</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/help/helpAdminManage.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>

</head>
<body>
<permission:hasRight moduleName="HELP">
	<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a
					href="${currentContextPath}/helpAdmin/goHelpManager">内容中心</a> &gt;
				帮助管理
			</div>
		</div>
		
		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<div class="panel-help-search">
						<form action="${currentContextPath}/helpAdmin/searchHelp"
							method="post" id="queryForm">
							<input id="pageNo" type="hidden" name="pageNo"
								value="${page.pageNo}"> <input id="pageSize"
								type="hidden" name="pageSize" value="${page.pageSize}">
							<input id="orderBy" type="hidden" name="orderBy"
								value="${queryReq.orderBy}"> 标题：<input type="text"
								class="input w-3" name="title" value="${queryReq.title}" />&nbsp;&nbsp;
							所属类目： <select id="J-select-type" class="ui-select" name="cateId">
								<option value=-1>全部</option>
								<c:forEach items="${cateList}" var="cateList">
									<option value="${cateList.id}"
										<c:if test="${queryReq.cateId==cateList.id}">selected</c:if>>${cateList.name}</option>
								</c:forEach>
							</select> <select id="J-select-type-2" class="ui-select" name="cateId2">
								<option value=-1>全部</option>
							</select> &nbsp;&nbsp; 是否推荐： <select class="ui-select" name="isRec">
								<option value=-1>全部</option>
								<option value=1
									<c:if test="${queryReq.isRec=='1'}">selected</c:if>>是</option>
								<option value=0
									<c:if test="${queryReq.isRec=='0'}">selected</c:if>>否</option>
							</select> &nbsp; <a href="javascript:document.forms[0].submit();"
								class="btn">搜 索<b class="btn-inner"></b></a>
						</form>
					</div>
					<table class="table table-info" id="J-data-table">
						<thead>
							<tr>
								<th>选择</th>
								<th>编号</th>
								<th>标题</th>
								<th>所属类目</th>
								<th>是否推荐</th>
								<th class="table-toggle">浏览人数<a id="browsenum" href="#"><i
										id="browsenum_icon" class="${browsenum_icon}"></i></a></th>
								<th class="table-toggle">投票已解决<a id="solvednum" href="#"><i
										id="solvednum_icon" class="${solvednum_icon}"></i></a></th>
								<th class="table-toggle">投票未解决<a id="unsolvednum" href="#"><i
										id="unsolvednum_icon" class="${unsolvednum_icon}"></i></a></th>
								<th class="table-toggle">序号<a id="no" href="#"><i
										id="no_icon" class="${no_icon}"></i></a></th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${helpList}" var="helpList" varStatus="status">
								<tr>
									<td><input type="checkbox" class="checked-row"
										value="${helpList.id}" /></td>
									<td>${status.index+1}</td>
									<td>${helpList.title}</td>
									<td>${helpList.cateName}-${helpList.cateName2}</td>
									<td><c:if test="${helpList.isRec=='1'}">是</c:if>
										<c:if test="${helpList.isRec=='0'}">否</c:if></td>
									<td>${helpList.browsenum}</td>
									<td>${helpList.solvednum}</td>
									<td><a href="javaScript:void(0)"
										onClick="showDetail('${helpList.id}')">${helpList.unsolvednum}</a></td>
									<td>${helpList.no}</td>
									<td><permission:hasRight moduleName="HELP_MANAGER">
											<a
												href="${currentContextPath}/helpAdmin/goViewHelp?helpId=${helpList.id}">查看</a>&nbsp;&nbsp;
											</permission:hasRight> <permission:hasRight moduleName="HELP_MANAGER_MODIFY">
											<a
												href="${currentContextPath}/helpAdmin/goModifyHelp?helpId=${helpList.id}">修改</a>&nbsp;&nbsp;
												</permission:hasRight> <permission:hasRight moduleName="HELP_MANAGER_DELETE">
											<a href="#" class="row-del" dir="${helpList.id}">删除</a>
										</permission:hasRight></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page-wrapper" style="float: left;">
						<span class="page-text"> <label class="label-checked-all"
							for="J-checked-row-all"><input id="J-checked-row-all"
								type="checkbox" class="checked-row-all" />&nbsp;&nbsp;全选/取消</label> <span>&nbsp;&nbsp;&nbsp;&nbsp;<a
								href="#" id="J-checked-delete">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
						</span>
						</span>
					</div>
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}"
						pageSize="${page.pageSize}" />
					<br style="clear: both;">
				</div>
			</div>
		</div>
		
	</div>
	<script>

if('${page.totalCount}'==0)
	{
	alert("抱歉，没有找到符合的条件，请重新选择搜索。");
	}

if('${tipInfo}'!='' && '${tipInfo}'!=null){
	alert('${tipInfo}');
}
var cates = new Map();
var cateIds = new Map();

	<c:forEach items="${cateList}" var="cate">
	var cateList${cate.id} = new Array();
	var cateListId${cate.id} = new Array();
		<c:forEach items="${cate.subCate}" var="subCate" varStatus="status">
		cateList${cate.id}[${status.index}] = '${subCate.name}';
		cateListId${cate.id}[${status.index}] = '${subCate.id}';
		</c:forEach>
	cates.put('${cate.id}',cateList${cate.id});
	cateIds.put('${cate.id}',cateListId${cate.id});
	</c:forEach>

	if($('#J-select-type').val()>-1)
	{
		var index = $('#J-select-type').val();
		var subcates = cates.get(index);
		var idCates = cateIds.get(index);
		var subIds = cates;
		for (var i=0; i<subcates.length; i++) {
			if(idCates[i]=='${queryReq.cateId2}')
			{
				$('#J-select-type-2').append('<option value="'+idCates[i]+'"  selected>'+subcates[i]+'</option>');
			}
			else
			{
				$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
			}
		}
	}
	
$('#J-select-type').change(function() {
	$("#J-select-type-2").empty();
	var index = $(this).val();
	var subcates = cates.get(index);
	var idCates = cateIds.get(index);
	var subIds = cates;
	$('#J-select-type-2').append('<option value=-1>全部</option>');
	for (var i=0; i<subcates.length; i++) {
		$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
	}
});

$('#browsenum').click(function(){
	if($('#orderBy').val()=='BROWSENUM DESC')
	{
		$('#orderBy').attr('value','BROWSENUM');
	}
	else
	{
		$('#orderBy').attr('value','BROWSENUM DESC');
	}
	$('#queryForm').submit();
});
$('#solvednum').click(function(){
	if($('#orderBy').val()=='SOLVEDNUM DESC')
	{
		$('#orderBy').attr('value','SOLVEDNUM');
	}
	else
	{
		$('#orderBy').attr('value','SOLVEDNUM DESC');
	}
	$('#queryForm').submit();
});
$('#unsolvednum').click(function(){
	if($('#orderBy').val()=='UNSOLVEDNUM DESC')
	{
		$('#orderBy').attr('value','UNSOLVEDNUM');
	}
	else
	{
		$('#orderBy').attr('value','UNSOLVEDNUM DESC');
	}
	$('#queryForm').submit();
});
$('#no').click(function(){
	if($('#orderBy').val()=='NO DESC')
		{
		$('#orderBy').attr('value','NO');
		}
	else
		{
		$('#orderBy').attr('value','NO DESC');
		}
	$('#queryForm').submit();
});		


function doPre(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(currentPageNo-1);
	$("#queryForm").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(Number(currentPageNo)+1);
	$("#queryForm").submit();
}

function doForward(pageNo){
	$("#pageNo").val(pageNo);
	$("#queryForm").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#queryForm").submit();
}

var minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
	mask = phoenix.Mask.getInstance();
minWindow.dom.css({
	width:360
});
minWindow.addEvent('beforeShow', function(){
	mask.show();
});
minWindow.addEvent('afterShow', function(){
	setTimeout(function(){
		$('#J-input-sortname').focus();
	},500);
});
minWindow.addEvent('afterHide', function(){
	mask.hide();
});

function showDetail(helpId){
	var strArr = [];
	minWindow.setTitle("原因");
	var url = baseUrl + "/helpAdmin/queryFeedback";
	var content = '';
	$.ajax({
		url:url,
		dataType:'json',
		method:'post',
		//如果id为空，则为添加新彩种知识目录
		//该处有个逻辑问题，当先设置排序时，能不能增加，应视为无效请求
		data:"helpId=" + helpId,
		success:(function(){return function(data){
			content='<div style="height:600px;overflow-y:auto;"><ul>';
			for(var i=0;i<data.body.result.length;i++){
				var jsonObject = data.body.result[i];
				if(jsonObject.reasonId == 0){
					content = content + "<li>太简单，用不上（<font color='red'>"+jsonObject.count+"</font>）</li>";
				}
				else if(jsonObject.reasonId == 1){
					content = content + "<li>字太多，不想看（<font color='red'>"+jsonObject.count+"</font>）</li>";
				}
				else if(jsonObject.reasonId == 2){
					content = content + "<li>太复杂，看不懂（<font color='red'>"+jsonObject.count+"</font>）</li>";
				}
				else if(jsonObject.reasonId == 3){
					content = content + "<li>其他（<font color='red'>"+jsonObject.count+"</font>）:";
					content = content + "<ul>";
					for(var j=0;j<jsonObject.reason.length;j++){
						var index = j+1;
						content = content + "<li><strong>"+index+"、</strong>"+jsonObject.reason[j]+"。</li>";
					}
					content = content + "</ul>";
					content = content + "</li>";
				}
			}
			content = content + '</ul></div>';
			strArr.push(content);
			minWindow.setContent(strArr.join(''));
			minWindow.show(); 
		}})(),
		error:(function(){return function(xhr, type){
			alert(type);
		}})()
	});
}

</script>
</permission:hasRight>
</body>
</html>