<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>添加用戶</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tree.js"></script>		
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/userCreate.js"></script>
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

			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">权限中心</a> &gt; 创建用户</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath}/aclAdmin/createUser" id="info-content">
							<input id="J-user-id" type="hidden" value="3" name="id"/>
							<input type="hidden" value="0" name="status"/>
							
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">用户名：</label>
									<input name="account" id="userName" type="text" value="" class="input w-4" />
									<span class="ui-text-prompt"></span>
									<!--span style="display:inline-block;width:210px;">
										<span id="J-show-username"></span>
										&nbsp;
										<a href="javascript:void(0);" class="button-getUserList" id="button-getUserList" style="display:none;">修改</a>
										<a id="button-getUserList-button" class="button-getUserList btn" href="javascript:void(0);">获取用户<b class="btn-inner"></b></a>
									</span>
									<span id="J-error-username" class="ui-text-prompt" style="display:none;">请选择一个用户</span-->
								</li>
								<li>
									<label class="ui-label" for="">密码：</label>
									<input name="passwd" id="passWord" type="password" value="" class="input w-4" onpaste="return false"/>
									<span class="ui-text-prompt">6-20位字符组成，区分大小写</span>
								</li>
								<li>
									<label class="ui-label" for="">确认密码：</label>
									<input id="confirmPassWord" type="password" value="" class="input w-4" onpaste="return false"/>
									<span class="ui-text-prompt">6-20位字符组成，区分大小写</span>
								</li>
								<li>
									<label class="ui-label" for="">密码卡绑定：</label>
									<input name="bindCard" id="passwordCard" type="text" value="" class="input w-4" />
									<span class="ui-text-prompt"></span>
								</li>
								<li>
									<label class="ui-label" for="">电话：</label>
									<input id="input-phone" name="telephone" type="text" value="" class="input w-4" />
									<span class="ui-text-prompt"></span>
									
								</li>
								<li>
									<!--label class="ui-label" for="">手机：</label-->
									<input id="input-mobile" name="cellphone" type="hidden" value="" class="input w-4" />
								</li>
								<li>
									<label class="ui-label" for="">Email：</label>
									<input id="input-email" name="email" type="text" value="" class="input w-4"  />
									<span class="ui-text-prompt"></span>
									
								</li>
								<li>
									<!-- label class="ui-label" for="">组织架构：</label> 
									<span class="ui-singleline">PMD</span> -->
									<input type="hidden" name="dep" value="PMD"/>
								</li>
								<li>
									<label class="ui-label" for="">所属权限组：</label>
									<select class="ui-select" name="groupId" id='J-group-select'>
										<option value="">请选择所属权限组</option>		
										${groups.sunBox}								
									</select>
									<span class="ui-text-prompt"></span>
								</li>
								<li class="ui-btn">
									<a id="editButton" class="btn btn-important" href="javascript:void(0);">添 加<b class="btn-inner"></b></a>
								</li>
							</ul>
						</form>
						<div id="successTips" class="pop pop-success w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-success"></i>
							<h4 class="pop-text">添加用户成功</h4>
						</div>
			
						<div id="errorTips" class="pop pop-error w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-error"></i>
							<h4 class="pop-text">添加用户失败，请重试</h4>
						</div>	
						
					<input type="hidden" id="user-input-select-userid" value="" />
					<input type="hidden" id="user-input-select-username" value="" />
					<!--textarea id="userList" style="display:none">
											<ul class="tree-list clearfix">
												<li class="tree-list-layout">
													<h3 class="title">组织构架：</h3>
													<div class="content" id="J-tree-container">
													
													</div>
													
												</li>
												<li>
													<h3 class="title">已选用户：</h3>
													<div class="content" id="J-tree-container2">
														<div id="user-selected"></div>
													</div>
												</li>
											</ul>
					</textarea-->


				</div>
			</div>
		</div>
</body>