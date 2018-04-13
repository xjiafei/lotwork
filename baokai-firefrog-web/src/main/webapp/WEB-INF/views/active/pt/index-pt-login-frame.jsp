<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>全球最佳老虎机平台PT</title>
	<link href="http://static.phl58.co/static/images/activity/pt_register/common/base.css" rel="stylesheet">
	<link href="http://static.phl58.co/static/images/activity/pt_register/ptindex/pt.css" rel="stylesheet">
	<link href="http://static.phl58.co/static/images/activity/pt_register/register_v2/pt-reg-v2.css" rel="stylesheet">
	<script>     
        addEventListener("message", function (event) {
        	if(event.data.type == "login"){
	            $('#J-input-username').val(event.data.userName);
	      		$('#J-input-password').val(event.data.passWord);
	      		$('#J-input-loginType').val(event.data.loginType);
	      		$submitDom.click();
        	}
        }, false);    
    </script>
</head>

<body >
<div class="pop pop-login">
		<ul class="ui-form">
		<li id="J-panel-vcode" style="position: relative;left: -25px;display:none;">
			<label style="color:#CDCDCD;">验证码</label>
			<input id="J-input-vcode" class="input" onpaste="return false" type="text" maxlength="4">
		</li>
		<li>
			<img id="img-code" style="width:70px;hight:150px" src="/login/changevcode" alt="验证码" onclick="this.src='/login/changevcode?r='+Math.random();" data-src-path="/login/changevcode">
			<a href="#" onclick="javascript:$('#img-code').click();">换一张</a>
			<div id="J-msg-show" data-display="hide" class="msg-show" style="color:red;"><span class="warning">请输入验证码</span></div>
		</li>
		</ul>
		
</div>
<input type="hidden" id="account">
<input id="J-input-username" type="hidden" style="display:none;">
<input type="hidden" id="J-input-password" style="display:none;">
<input type="hidden" id="J-input-loginType" style="display:none;">
<input type="hidden" id="J-input-vCodeType" value="1" style="display:none;">

<a id="btn_login" class="btn_login2" href="javascript:;" ></a>
<script src="http://static.phl58.co/static/js/game/util/jquery-1.9.1.js"></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.base.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Class.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Event.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Tab.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Hover.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.cookie.js" ></script>
<script src="http://static.phl58.co/static/js/login/login-all.min.js" ></script>
<script src="http://static.phl58.co/static/js/jquery.cookie.js" ></script>
<script src="http://static.phl58.co/static/js/phoenix.Input.js" ></script>
<script src="http://static.phl58.co/static/js/activity/pt_register/pt/pt_reg_v2.js" ></script>
<script src="http://static.phl58.co/static/js/activity/pt_register/pt/jquery.backgroundpos.min.js" ></script>
<script src="http://static.phl58.co/static/js/rsa.min.js" ></script>
</body>
</html>