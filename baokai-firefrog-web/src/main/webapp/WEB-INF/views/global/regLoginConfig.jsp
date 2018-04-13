<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>注册登录设置</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/global/regLoginConfig.js"></script>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"> <strong>当前位置：</strong><a href="#">全局管理</a> &gt; 注册登陆设置</div></div>
			<permission:hasRight moduleName="GLOBAL">
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form id="J-form" action="${currentContextPath}/globeAdmin/saveRegLoginConfig">
						
						<ul class="ui-form">
							<li>
								<label class="ui-label w-3" for="">同一IP重复注册（分钟）：</label>
								<input id="J-input-h" type="text" class="input w-1" value="${config.reregister}" name="reregister"/>
								<span class="ui-text-prompt">设定时间内，同一IP将无法进行多次注册。0或留空表示不限制</span>
							</li>
							<li>
								<label class="ui-label w-3" for="">登录超时时间（分钟）：</label>
								<input id="J-input-s" type="text" class="input w-1" value="${config.overTime}" name="overTime"/>
								<span class="ui-text-prompt">超出设定时间，将自动退出登录。0或留空表示不限制。</span>
							</li>
							<li class="radio-list">
								<label class="ui-label w-3" for="">是否开启多端登录：</label>
								<label for="ck1" class="label"><input id="ck1" name="multLogin" type="radio" value="1" class="radio" <c:if test="${config.multLogin=='1'}">checked="checked"</c:if>>开启（默认）</label>
								<label for="ck2" class="label"><input id="ck2" name="multLogin" type="radio" value="0" class="radio" <c:if test="${config.multLogin=='0'}">checked="checked"</c:if>>关闭</label>
								<span class="ui-text-prompt">开启后，同一账号可以在多处（Web/PC客户端/手机端）同时登录和操作，关闭该功能后，登录互踢功能生效。</span>
							</li>
							<li class="ui-btn">
								<a class="btn btn-important" id="J-form-submit" href="javascript:void(0);">保 存<b class="btn-inner"></b></a>
								<a class="btn" id="J-form-rollback" href="javascript:void(0);">取 消<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
					
					</div>
				</div>
			</div>
			</permission:hasRight>
</body>