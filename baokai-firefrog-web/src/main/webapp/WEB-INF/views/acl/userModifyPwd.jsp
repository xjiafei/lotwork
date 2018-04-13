<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>修改用户密码</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/userModifyPwd.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>	
	
	<style>
		.pop .btn.confirm{
			background: url("../images/common/ui-btn.png") repeat scroll 0 -240px transparent;
			border: 0 none;
			border-radius: 0 0 0 0;
			color: #FFFFFF;
			height: 34px;
			line-height: 34px
		}
		.pb20{
			width:770px;
		}
		.pb20 .btn{
			margin-right: 30px
		}
		.w-9 {width:764px!important}
		.tree-list #J-tree-container,.tree-list #J-tree-container2 {padding-bottom:20px;}
		.ui-tree-node:hover {background:#FFFFe1;}
		.ui-tree-check,.ui-tree-del {float:right;height:24px;line-height:24px;padding-right:5px;}
		.ui-tree-del {cursor:pointer}
	</style>	
	
</head>

<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">权限中心</a> &gt; 修改密码</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="info-content" action="${currentContextPath}/aclAdmin/modifyPwd">
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">当前密码：</label>
									<input id="oldpassword" name="oldPwd" type="password" value="" class="input w-4" onpaste="return false"/><span class="ui-prompt" style="display: none;">请输入当前登录密码</span>
									<span class="ui-check"></span>
								</li>
								<li>
									<label class="ui-label" for="">新登录密码：</label>
									<input id="newpassword" name="newPwd" type="password" value="" class="input w-4" onpaste="return false"/><span class="ui-prompt" style="display: none;">6-20位字符组成,区分大小写</span>
									<span class="ui-check"></span>
								</li>
								<li>
									<label class="ui-label" for="">确认新密码：</label>
									<input id="confirmpassword" type="password" value="" class="input w-4" onpaste="return false"/><span class="ui-prompt" style="display: none;">请再次输入登录密码</span>
									<span class="ui-check"></span>
								</li>
								<li class="ui-btn">
									<a id="submit" class="btn btn-important" href="javascript:void(0);">修改<b class="btn-inner"></b></a>
								</li>
							</ul>
						</form>
						<div id="successTips" class="pop pop-success w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-success"></i>
							<h4 class="pop-text">修改密码成功</h4>
						</div>
						
						<div id="errorTips" class="pop pop-error w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-error"></i>
							<h4 class="pop-text">请输入正确的当前密码</h4>
						</div>
					</div>
				</div>
			</div>
</body>