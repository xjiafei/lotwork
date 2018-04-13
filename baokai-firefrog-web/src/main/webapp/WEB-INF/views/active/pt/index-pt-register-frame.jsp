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
	      		$submitDom.click();
        	}else if(event.data.type == "register"){
        		$('#J-input-username').focus();
        	}else if(event.data.type == "css"){
	       		$(".box-info").height('325px');
	       		$("#reg_succ").height('235px');
	       		$("#reg_succ").css("margin-top","-125px")
	       		$("#reg_login").css("display","none");
        	}
        	
       		
        }, false);    
    </script>
</head>


<body >
	<div class="container-top reg_border" style="border-top:none">
		<div >
			<div class="col-sub" style="float:none">
				<div class="box-info">
					<div class="reg_succ" id="reg_succ" style="display: none;cursor: pointer;">
						<div class="succ_icon"></div>
						<div class="succ_tips">赶快登录平台，赢取百万大奖!</div>
					</div>
					<div class="reg_title">
						<span class="ico_people"></span>账号注册
					</div>
					<div class="reg_box">
						<ul class="input_list">
							<li>
								<label>用户名</label>
								<input id="J-input-username" type="text">
								<i class="check-right"></i>
								<div class="msg-error" style="display:none" id="nameerror">
									<i class="error"></i>
								</div>
							</li>
							<li>
								<label>密码</label>
								<input type="password" id="J-input-password" >
								<i class="check-right"></i>
								<div class="pwd-strength" id="J-div-pwdstrength" style="display: none">
									弱<span class="pwd-bar"><b id="password_label" class="pwd-weak"></b></span>强
								</div>
								<div class="msg-error" style="display:none" id="pwderror">
									<i class="error"></i>
								</div>
							</li>
							<li>
								<label>确认</label>
								<input type="password"  id="J-input-password2">
								<i class="check-right"></i>
								<div class="msg-error" style="display:none" id="pwderror2">
									<i class="error"></i>
								</div>
							</li>
							<li id="J-panel-vcode" style="display:none">
								<label>验证码：</label>
								<input id="J-input-vcode" onpaste="return false" type="text" maxlength="4">
								<img id="img-code" style="width:70px;hight:150px" src="/register/changevcode" alt="验证码" onclick="this.src='/register/changevcode?r='+Math.random();" data-src-path="/register/changevcode">
								<div class="msg-error" style="display:none" id="pwderror3"><i class="error"></i></div>
								<a href="#" onclick="javascript:$('#img-code').click();">换一张</a>
							</li>

						</ul>
						<a class="btn_reg" id="J-button-submit" href="javascript:;"><span class="icon_book"></span><span id="txt_reg">提交注册</span></a>
					</div>
					<div class="reg_login" id="reg_login">
						<a id="btn_trnslogin" class="btn_login" href="javascript:;"><span class="icon_person"></span>登录平台</a>
						<div id="J-msg-show" data-display="hide" class="msg-show"></div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
			<input type="hidden" id="account">
			<input type="hidden" id="J-input-vCodeType" value="1" style="display:none;">
<a id="btn_login2" class="btn_login" href="javascript:;"></a>
<script src="http://static.phl58.co/static/js/game/util/jquery-1.9.1.js"></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.base.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Class.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Event.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Tab.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.Hover.js" ></script>
<script src="http://static.phl58.co/static/js/game/util/phoenix.cookie.js" ></script>-->
<script src="http://static.phl58.co/static/js/login/login-all.min.js" ></script>
<script src="http://static.phl58.co/static/js/jquery.cookie.js" ></script>
<script src="http://static.phl58.co/static/js/phoenix.Input.js" ></script>
<script src="http://static.phl58.co/static/js/activity/pt_register/pt/pt_reg_v2.js" ></script>
<script src="http://static.phl58.co/static/js/activity/pt_register/pt/jquery.backgroundpos.min.js" ></script>
<script src="http://static.phl58.co/static/js/rsa.min.js" ></script>


</body>
</html>