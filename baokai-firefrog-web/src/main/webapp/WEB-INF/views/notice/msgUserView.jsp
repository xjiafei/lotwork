<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>站内信-用户消息详情</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/msgUserView.js"></script>	
	<style>
		.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
		.j-ui-tip .sj {display:none;}
		.ui-form-window li {margin-top:5px;margin-bottom:5px;}
		.pop-window-datepicker {z-index:710;}
		.dia-list {width:660px;height:500px;overflow-y:scroll;padding-right:20px;border:2px solid #EEE;padding:20px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		}
	</style>
</head>

<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 用户消息管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-form ui-form-small">
							<li>
								<label class="ui-label w-2" for="">发件人：</label>
								<span class="ui-text-info">${msg.sendAccount}</span>
							</li>
							<li>
								<label class="ui-label w-2" for="">时&nbsp;&nbsp;&nbsp;间：</label>
								<span class="ui-text-info">${time}</span>
							</li>
							<li>
								<label class="ui-label w-2" for="">收件人：</label>
								<span class="ui-text-info">${msg.receiveAccount}</span>
							</li>
						</ul>
						
						<div class="dia-list" id="J-msgbox">


						</div>
						
							
						<div class="dia-input dia-input-current">
									<div class="dia-btn" style="padding-left:40px;">
										<a class="btn btn-important" href="javascript:history.go(-1);">关 闭<b class="btn-inner"></b></a>
									</div>
						</div>
						
					</div>
				</div>
			</div>
			
<input type="hidden" id="J-userid" value="1" />
<input type="hidden" id="J-msgid" value="${msg.id}" />

<script type="text/template" id="J-msg-tpl"><dl class="<#=className#>">
		<dt><strong><#=username#></strong> <#=time#></dt>
		<dd><i class="tri"></i><#=content#></dd>
	</dl>
</script>			
</body>