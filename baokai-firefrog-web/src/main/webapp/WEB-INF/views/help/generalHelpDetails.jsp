<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<title>帮助中心 文章详情</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/generalHelpDetails.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
</head>
<body>
	<div class="clearfix">
		<input type="hidden" id='loginUserId' name='loginUserId' value="${currUser.id}">
		<input type="hidden" id='loginUserName' name='loginUserName' value="${currUser.userName}">
		<div class="help-container help-article-container">
			
			<div class="help-article-title">
				${help.title}
				<input type="hidden" id="helpId" value="${help.id}"/>
			</div>
			
			<div class="help-article-text">
				${help.content}
			</div>
			<div class="help-feedback">
				<div class="help-feedback-title">以上内容是否解决了您的问题？</div>
				<div>
					<a href="javascript:void(0)" id="J-feedback-button-yes" class="btn btn-important">是，已解决<b class="btn-inner"></b></a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" id="J-feedback-button-no" class="btn btn-primary">否，未解决<b class="btn-inner"></b></a>
				</div>
				
				
				<div id="J-help-feedback-panel" class="help-feedback-panel" style="display:none">
					<div>
						请选择原因： 
						<label for="J-reason-1"><input id="J-reason-1" name="reason" type="radio" value="0"/> 太简单，用不上</label>
						<label for="J-reason-2"><input id="J-reason-2" name="reason" type="radio" value="1"/> 字太多，不想看</label>
						<label for="J-reason-3"><input id="J-reason-3" name="reason" type="radio" value="2"/> 太复杂，看不懂</label>
						<label for="J-reason-4"><input id="J-reason-4" name="reason" type="radio" value="3"/> 其他</label>
					</div>
					<div id="J-help-feedback-text" class="textarea w-8" style="display:none;">
						<textarea id="reasonText" maxlength="100"></textarea>
					</div>
                    <span class="ui-prompt" style="padding-left:420px; margin: -25px 0px 0px 0px;display:none;">限100字</span>
                    <div id="J-help-message-panel-1" style="display: none;"><h4 class="pop-text">您的反馈信息已经成功提交，谢谢您的反馈</h4></div>
					<div class="help-feedback-subcont">
						<a href="javascript:void(0)" class="btn btn-important" id="submit">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交<b class="btn-inner"></b></a>
						<a id="J-help-feedback-cancel" href="javascript:void(0)" class="btn">取 消<b class="btn-inner"></b></a>
					</div>
				</div>
                <div class="help-feedback-panel" id="J-help-message-panel-2" style="display: none;"><h4 class="pop-text">您已提交过此次反馈</h4></div>
			</div>		
	  </div>
	</div>
</body>
