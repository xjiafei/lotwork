<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>创建权限组</title>
	<style>
	.tree-list #J-tree-container,.tree-list #J-tree-container2 {padding-bottom:20px;}
	.ui-tree-node:hover {background:#FFFFe1;}
	.ui-tree-check,.ui-tree-del {float:right;height:24px;line-height:24px;padding-right:5px;}
	.ui-tree-del {cursor:pointer}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/addGroup.js"></script>
</head>
<body>
		<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a> &gt; 创建权限组</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">创建权限组</h3>
						<form id="J-form" action="#" id="">
						<input type="hidden" id="J-input-ids" value="" />
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">组名称：</label>
									<input id="groupName" type="text" value="" class="input w-4" />
									<span class="ui-text-prompt">2-20位字符组成</span>
									<input id="pid" type="hidden" value="${group.id }">
								</li>
								<li>
									<label class="ui-label" for="">所属组：</label>
									<select class="ui-select" name="pid" id="select">
									   ${groups.box}
									</select>
								</li>
								<li class="radio-list">
									<label class="ui-label" for="">是否启用：</label>
								<label class="label" for="switch-1"><input id="switch-1" name="inUser" type="radio" class="radio" value="1" checked="checked">开启（默认）</label>
								<label class="label" for="switch-2"><input id="switch-2" name="inUser" type="radio" class="radio" value="0">禁用</label>
								</li>
							</ul>
						</form>	
						<h3 class="ui-title">分配组权限</h3>


						<ul class="tree-list clearfix">
							<li class="tree-list-layout">
								<h3 class="title">可分配权限</h3>
								<div class="content" id="J-tree-container">
								

								</div>
								
							</li>
							<li>
								<h3 class="title">已授予权限</h3>
								<div class="content" id="J-tree-container2">
									
								</div>
							</li>
						</ul>





						<div style="padding-left:20px;"><a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">创 建<b class="btn-inner"></b></a></div>
						
						<div style="height:60px;"></div>
					</div>
				</div>
			</div>
	
	
<div id="tip-success" style="position:absolute;display:none;" class="pop w-4">
	<i class="ico-success"></i>
	<h4 class="pop-text">创建成功</h4>
</div>
	
	
<div id="tip-fail" style="position:absolute;display:none;" class="pop w-4">
	<i class="ico-error"></i>
	<h4 class="pop-text">创建失败，请重试</h4>
</div>
</body>
</html>