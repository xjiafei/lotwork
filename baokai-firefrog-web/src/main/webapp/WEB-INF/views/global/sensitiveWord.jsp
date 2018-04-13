<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>敏感词管理</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/global/sensitiveWord.js"></script>
	<style>
		.keyword-control {padding:0 20px;display:none;}
		.keyword-control a {display:inline-block;padding:0 10px;}
		.table-info td:hover .keyword-control {display:inline-block;}
		.pop .btn.confirm{
				background: url("${staticFileContextPath}/static/images/common/ui-btn.png") repeat scroll 0 -240px transparent;
				border: 0 none;
				border-radius: 0 0 0 0;
				color: #FFFFFF;
				height: 34px;
				line-height: 34px
		}
		.pop .btn.confirm .btn-inner{
				background: url("${staticFileContextPath}/static/images/common/ui-btn.png") repeat scroll right -240px transparent;
				height: 34px;
				width: 15px;
		}
		
	</style>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">全局管理</a> &gt; 敏感词管理 </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form id="J-search-form" action="${currentContextPath}/globeAdmin/searchSensitiveWord" method="post">
					<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
					<input type="hidden" name="type" value="${search.type}" id="type">
						<ul class="ui-search">
							<li>
								<input id="J-search-input" name="word" type="text" class="input" value="${search.word}">
								<input type="hidden" value="${search.word}" id="J-hidden-word">
							</li>
							<li><a id="J-search-submit" href="javascript:void(0);" class="btn btn-important">搜索<b class="btn-inner"></b></a></li>
							<li style="float:right;"><a id="J-button-keyword-add" href="javascript:void(0);" class="btn">+新建敏感词<b class="btn-inner"></b></a></li>
						</ul>
					</form>
						
						<div class="ui-tab">
							<div id="J-side-menu" class="ui-tab-title clearfix" style="border-bottom:1px solid #DEDEDE;">
								<ul>
									<li <c:if test="${search.type==null}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord">全部</a></li>
									<li <c:if test="${search.type==0}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=0">注册页 ${type_0}个</a>
									</li>
									<li <c:if test="${search.type==1}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=1">广告  ${type_1}个</a>
									</li>
									<li <c:if test="${search.type==2}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=2">帮助 ${type_2}个</a>
									</li>
									<li <c:if test="${search.type==3}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=3">站内信${type_3}个</a>
									</li>
									<li <c:if test="${search.type==4}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=4">评论 ${type_4}个</a>
									</li>
									<li <c:if test="${search.type==5}">class="current"</c:if>><a href="${currentContextPath}/globeAdmin/searchSensitiveWord?type=5">客服${type_5}个</a>
									</li>
									
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
								<table class="table table-info" id="J-table-list">
									<thead>
										<tr>
											<th class="w-1"></th>
											<th class="w-9">敏感词</th>
											<th>分类</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list}" var="list" varStatus="status">
										<tr>
											<td><input type="checkbox" class="checkbox" value="${list.id}"></td>
											<td>${list.word}<span class="keyword-control"><a href="#" data-id="${list.id}" data-title="${list.word}" data-pid="1" class="word-rename">重命名</a> <a href="#" class="word-delete">删除</a></span></td>
											<td>${list.type}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<c:if test="${page.totalCount<=0}">
								<div class="alert alert-waring">
									<i></i>
									<div class="txt">
										<h4>抱歉，没有找到符合的条件，请重新选择搜索！</h4>
									</div>
								</div>
								</c:if>
								<c:if test="${page.totalCount>0}">
								<div class="page-wrapper clearfix" style="float:left;">
									<span class="page-text">
										<label class="label" for="J-select-all"><input id="J-select-all" type="checkbox" class="checkbox">全选</label>
										<a class="btn btn-small" href="javascript:void(0);" id="J-delete-all">批量删除<b class="btn-inner"></b></a>
									</span>
								</div>
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>		
								<br style="clear:both;">
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
	$("#J-search-form").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)+1);
	$("#J-search-form").submit();
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
	$("#J-search-form").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#J-search-form").submit();
}
</script>			
<select id="J-sort-select-data" style="display:none;">

</select>

<script type="text/template" id="J-tpl-addkeyword">
	<ul class="ui-form ui-form-small">
		<li>
			<label class="ui-label w-2">敏感词：</label>
			<div class="textarea w-4">
				<textarea id="J-textarea-new-words"></textarea>
			</div>
		</li>
		<li>
			<label class="ui-label w-2"></label>
			<span class="ui-prompt">可批量添加，一行一个</span>
			<span id="ui-prompt" class="ui-prompt" style="color:red;display: none;"></span>
		</li>
		<li>
			<label for="" class="ui-label w-2">分类：</label>
			<select id="J-select-new-words">
				<option value ="" selected>请选择</option>
				<option value ="0">注册页</option>
				<option value ="1">广告</option>
				<option value ="2">帮助</option>
				<option value ="3">站内信</option>
				<option value ="4">评论</option>
				<option value ="5">客服</option>
			</select>
			<span id="ui-prompt2" class="ui-prompt" style="color:red;display: none;"></span>
		</li>
	</ul>
</script>
<script type="text/template" id="J-tpl-modifykeyword">
	<ul class="ui-form ui-form-small">
		<li>
		<input type="hidden" id="modify-id">
			<label class="ui-label w-2">敏感词：</label>
			<div class="textarea w-4">
				<textarea id="J-textarea-new-words"></textarea>
			</div>
		</li>
		<li>
			<span id="ui-prompt3" class="ui-prompt" style="color:red;display: none;"></span>
		</li>
	</ul>
</script>
<script type="text/template" id="J-tpl-addsort">
	<ul class="ui-form ui-form-small">
		<li>
			<label class="ui-label w-2">分类名称：</label>
			<input id="J-input-new-sort" type="text" class="input w-4" value="">
		</li>
		<li>
			<label class="ui-label w-2"></label>
			<span class="ui-prompt">4-20位字符组成，区分大小写</span>
		</li>
	</ul>
</script>
<script type="text/template" id="J-tpl-sort"><li>
		<a href="?id=<#=id#>"><#=title#> <#=num#>个</a>
		<div class="dropdown">
			<div class="dropdown-btn"><i class="ico-close"></i></div>
			<div class="dropdown-menu">
				<a href="javascript:void(0);" data-id="<#=id#>" data-title="<#=title#>" class="sort-rename">重命名</a>
				<a href="javascript:void(0);" data-id="<#=id#>" data-title="<#=title#>" class="sort-delete">删除</a>
			</div>
		</div>
	</li>
</script>
</body>
