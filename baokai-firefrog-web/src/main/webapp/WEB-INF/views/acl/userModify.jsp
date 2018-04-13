<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>用户编辑</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/acl/userModify.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>		
</head>

<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">权限中心</a> &gt; 修改用户</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath}/aclAdmin/modifyUser" id="info-content">
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">用户名：</label>
									<span class="ui-singleline" name="account">${user.account}</span>
									<input type="hidden" name="id" id="userId" value="${user.id}">
								</li>
								<li>
									<label class="ui-label" for="">密码：</label>
									<input name="passwd" id="passWord" type="password" value="" maxlength="20" class="input w-4" />
									<span class="ui-text-prompt">不修改，请留空</span>
								</li>
								<li>
									<label class="ui-label" for="">确认密码：</label>
									<input id="confirmPassWord" type="password" value="" maxlength="20"  class="input w-4" />
									<span class="ui-text-prompt">不修改，请留空</span>
								</li>
								<li>
									<label class="ui-label" for="">密码卡绑定：</label>
									<input name="bindCard" id="passwordCard" type="text" value="${user.bindCard}" class="input w-4" />
									<span class="ui-text-prompt"></span>
								</li>
								<li>
									<label class="ui-label" for="">电话：</label>
									<input id="input-phone" name="telephone" type="text" value="${user.telephone}" class="input w-4" />
									<span class="ui-text-prompt"></span>
									
								</li>
								<li>
									<!--label class="ui-label" for="">手机：</label-->
									<input id="input-mobile" name="cellphone" type="hidden" value="" class="input w-4" />
								</li>
								<li>
									<label class="ui-label" for="">Email：</label>
									<input id="input-email" name="email" type="text" value="${user.email}" class="input w-4"  />
									<span class="ui-text-prompt"></span>
									
								</li>
								
								<li>
									<label class="ui-label" for="">所属权限组：</label>
									<select class="ui-select" name="groupId">
										<option value="-1">请选择所属权限组</option>
										${groups.sunBox}		  				
									</select>
                                    <span class="ui-text-prompt"></span>
								</li>
								
								<li>
									<label class="ui-label" for="">创建人：</label>
									<span class="ui-singleline">${user.createder}</span>
								</li>
								<li>
									<label class="ui-label" for="">创建时间：</label>
									<span class="ui-singleline"><fmt:formatDate value="${user.gmtCreated}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
								</li>
								<li>
									<label class="ui-label" for="">最后修改人：</label>
									<span class="ui-singleline">${user.modifieder}</span>
								</li>
								<li>
									<label class="ui-label" for="">最后修改时间：</label>
									<span class="ui-singleline"><fmt:formatDate value="${user.gmtModified}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
								</li>
								<li class="ui-btn">
									<a id="editButton" class="btn btn-important" href="javascript:void(0);">修改<b class="btn-inner"></b></a>
								</li>
							</ul>
						</form>
						<div id="successTips" class="pop pop-success w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-success"></i>
							<h4 class="pop-text">修改成功</h4>
						</div>
						
						<div id="errorTips" class="pop pop-error w-4" style="display:none;position:absolute;left:50%;top:50%;margin:-23px 0 0 -111px;z-index:601">
							<i class="ico-error"></i>
							<h4 class="pop-text">修改失败，请重试</h4>
						</div>
					</div>
				</div>
			</div>
</body>