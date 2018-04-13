<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<script type="text/javascript">global_path_url="{$path_img}";</script>
    <script type="text/javascript">hjUserData= "{$hjUserData}";</script>
    <script type="text/javascript">global_params_notice = "all,ad_top,agent_first_page";</script>
	<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.base.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Event.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.util.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Mask.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
    <script type="text/javascript">
	(function() {       
		function async_load(){           
			var s = document.createElement('script');          
			s.type = 'text/javascript';          
			s.async = true;           
			s.src = "";           
			var x = document.getElementsByTagName('script')[0];          
			x.parentNode.insertBefore(s, x);      
		}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  
	})();
	</script>

<body>
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
			<div class="service">
				<a title="客服" class="link-service" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" />在线客服</a>
				<a title="帮助" target="_blank" class="link-help" href="/help/goIndex">帮助</a>
			</div>
		</div>
	</div>
	<form action="/Appeal/resetsecrityinfosucc" method="post"  id="J-form">
	<input type="hidden" name="userId" value="{$userId}" >
	<input type="hidden" name="aHours" value="{$aHours}" >
	<div class="g_33">
		<div class="appeal-content">
			<dl class="prompt">
				<dt>温馨提示：</dt>
				<dd>请重新设置你的登录密码、安全密码、安全问题</dd>
				<dd>为保证账号安全，此链接仅能使用一次</dd>
				<dd>以下所有信息为必填项</dd>
			</dl>
			<h3 class="ui-title">重置登录密码</h3>					
			<ul class="ui-form">
				
				<li>
					<label for="" class="ui-label">请输入新的登录密码：</label>
					<input type="password" value="" id="J-loginPassword" class="input" maxlength="20" name="passwd">
                    <div class="ui-text-prompt" style="display: none;">6-20位字符</div>
					<div class="ui-check"><i class="error"></i>用户名格式错误</div>
                    <div class="pwd-new-strength" id="J-div-pwdstrength" style="margin-left: 20px; width: 270px; display: none;">密码强度：弱
                    <span class="pwd-new-bar"><b id="password_label" class="pwd-new-weak"></b></span>强<span id="newcheckString"></span></div>
				</li>
				<li>
					<label for="" class="ui-label">再次输入新登录密码：</label>
					<input type="password" value="" id="J-loginPassword2" class="input" maxlength="20">
                    <div class="ui-text-prompt" style="display: none;">请再次输入登录密码</div>
					<div class="ui-check"><i class="error"></i>两次输入不一致，请修改</div>
                     <div class="ui-check-right" style="display: none;"></div>
				</li>
			</ul>
			
			<h3 class="ui-title">重置安全密码</h3>
			<ul class="ui-form">
				<li>
					<label for="safeCode" class="ui-label">设置安全密码：</label>
					<input type="password" value="" id="J-safePassword" class="input" maxlength="20" name="withdrawPasswd">
                    <div class="ui-text-prompt" style="display: none;">6-20位字符</div>
					<div class="ui-check"><i class="error"></i>用户名格式错误</div>
                    <div class="pwd-new-strength" id="J-div-safestrength" style="margin-left: 20px; width: 270px; display: none;">密码强度：弱
                    <span class="pwd-new-bar"><b id="safe-password_label" class="pwd-new-weak"></b></span>强<span id="safecheckString"></span></div>
				</li>
				<li>
					<label for="checkSafeCode" class="ui-label">确认安全密码：</label>
					<input type="password" value="" id="J-safePassword2" class="input" maxlength="20">
                    <div class="ui-text-prompt" style="display: none;">请再次输入安全密码</div>
					<div class="ui-check"><i class="error"></i>两次输入不一致，请修改</div>
                    <div class="ui-check-right" style="display: none;"></div>
				</li>
			</ul>
			<h3 class="ui-title">重置安全问题</h3>
			<select style="display:none;" id="J-question-all">
				{foreach from=$questFullList item=item}
				<option value="{$item.Id}">{$item.quest}</option>
				{/foreach}
			</select>
			<ul class="ui-form set-safeissue" id="J-safe-question-select">
				<li>
					<label for="question1" class="ui-label">问题一：</label>
					<select class="ui-select" data-sort="一" name="questId">
						<option value="">请选择安全问题一</option>
						{foreach from=$questFullList item=item}
							<option value="{$item.Id}">{$item.quest}</option>
						{/foreach}
					</select>
					<div class="ui-check"><i class="error"></i>请选择安全问题一</div> 
				</li>
				<li>
					<label for="answer1" class="ui-label">答案：</label>
					<input type="text" value="" id="J-answer1" class="input" name="questAns">
                    <div class="ui-text-prompt" style="display: none;">4-16位字符,可由中文、字母、数字组成</div>
					<div class="ui-check"><i class="error"></i>请输入答案</div>
                    <div class="ui-check-right" style="display: none;"></div>
				</li>
				<li>
					<label for="question2" class="ui-label">问题二：</label>
					<select class="ui-select" data-sort="二" name="questId2">
						<option value="">请选择安全问题二</option>
						{foreach from=$questFullList item=item}
							<option value="{$item.Id}">{$item.quest}</option>
						{/foreach}
					</select>
					<div class="ui-check"><i class="error"></i>请选择安全问题二</div>
				</li>
				<li>
					<label for="answer2" class="ui-label">答案：</label>
					<input type="text" value="" id="J-answer2" class="input" name="questAns2">
                    <div class="ui-text-prompt" style="display: none;">4-16位字符,可由中文、字母、数字组成</div>
					<div class="ui-check"><i class="error"></i>请输入答案</div>
                     <div class="ui-check-right" style="display: none;"></div>
				</li>
				<li>
					<label for="question3" class="ui-label">问题三：</label>
					<select class="ui-select" data-sort="三" name="questId3">
						<option value="">请选择安全问题三</option>
						{foreach from=$questFullList item=item}
							<option value="{$item.Id}">{$item.quest}</option>
						{/foreach}
					</select>
					<div class="ui-check"><i class="error"></i>请选择安全问题三</div> 
				</li>
				<li>
					<label for="answer1" class="ui-label">答案：</label>
					<input type="text" value="" id="J-answer3" class="input" name="questAns3">
                    <div class="ui-text-prompt" style="display: none;">4-16位字符,可由中文、字母、数字组成</div>
					<div class="ui-check"><i class="error"></i>请输入答案</div>
                    <div class="ui-check-right" style="display: none;"></div>
				</li>
						
						<li class="ui-btn"><a id="J-button-submit" class="btn"  href="#">保 存<b class="btn-inner"></b></a></li>
					</ul>
				<input type="hidden" name="active" value="{$active}" />
				</div>
				</form>	
			</div>
			
		
	
	
{literal}
	<script id="J-safequestions-tpl" type="text/html-tmpl"> 
		<table class="table" id="J-safequestion-table">
			<tr class="safequestion-th">
				<th>问题</th>
				<th>答案</th>
			</tr>

		</table>
		<div class="pop-btn">
				<a id="J-safequestion-submit" class="btn btn-important" href="#">确 定<b class="btn-inner"></b></a>
				<a id="J-safequestion-cancel" class="btn" href="#">取 消<b class="btn-inner"></b></a>
		</div>
	</script>
	<script id="J-safequestions-row-tpl" type="text/html-tmpl"> 
		<tr>
			<td>${sort}.${question}</td>
			<td>${answer}</td>
		</tr>
	</script>
{/literal}
	
{literal}
<script>
(function($){
	var loginPassword=$('#J-loginPassword'),loginPassword2=$('#J-loginPassword2'),safePassword = $('#J-safePassword'),safePassword2 = $('#J-safePassword2'),v_pwd,v_pwd2,
		showSuccessMessage,showErrorMessage,
		answer1 = $('#J-answer1'),answer2 = $('#J-answer2'),answer3 = $('#J-answer3'),answerSuccess,answerError,v_answer1,v_answer2,v_answer3,
		selects = $('.ui-select', '#J-safe-question-select'),reBuildOptions,allOpts = $('#J-question-all option'),selValues = [],
		vGroup,
		minWindow,mask;	
		
	//登录密码校验
	showSuccessMessage = function(msg){
		var me = this,par = me.dom.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).css('display', 'none');
		$('.ui-text-prompt', par).css('display', 'none');
		//两次密码不一样
		if(me.dom.get(0) == loginPassword2.get(0)){
			if(v_lpwd.getValue() !== v_lpwd2.getValue())
			{
			   me.validated = false;
			   par.find(".ui-check-right").css('display','none');
			   $('.ui-check', par).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			}else
			{
				par.find(".ui-check-right").css('display','inline');
			}
		}
		if((/\s/).test(me.dom.get(0).value) && me != v_lpwd2){
			me.validated = false;
			$('.ui-check', par).html('<i class="error"></i>密码不含空格');
			$('.ui-check', par).css('display', 'inline');
			return;
		}
		if((/^\d{1,8}$/).test(me.dom.get(0).value) && me != v_lpwd2){
			me.validated = false;
			$('.ui-check', par).html('<i class="error"></i>密码不能是9位以下纯数字');
			$('.ui-check', par).css('display', 'inline');
			return;
		}
		if(me.dom.get(0) == loginPassword.get(0)){
			if(v_lpwd.getValue() !== v_lpwd2.getValue() && v_lpwd2.getValue() != ''){
				showPassowrdLevel();
				me.validated = false;
				loginPassword.parent().find(".ui-check-right").css('display','inline');
				loginPassword2.parent().find(".ui-check-right").css('display','none');
				$('.ui-check', v_lpwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			}else{
				showPassowrdLevel();
				me.validated = true;
				$('.ui-check', v_lpwd2.dom.parent()).css('display', 'none');
				par.find(".ui-check-right").css('display','inline');
			}
		}
	};
	showErrorMessage = function(msg){
		var me = this,par = me.dom.parent();
		$('.ui-text-prompt', par).css('display', 'none');
		par.find(".ui-check-right").css('display','none');
		if(me.dom.get(0) == loginPassword.get(0) && me.dom.val()=="")
		{
		   $('.ui-check', par).html('<i class="error"></i>请输入新的登录密码').css('display', 'inline');
		   par.find(".ui-check-right").css('display','none');
		   me.validated = false;
		   return;
		}
		if(me.dom.get(0) == loginPassword.get(0))
		{
		  $('.ui-check', par).html('<i class="error"></i>'+msg).css('display', 'inline');
		  par.find(".ui-check-right").css('display','none');
		  me.validated = false;
		  return;
		}
		if(me.dom.get(0) == loginPassword2.get(0))
		{
			$('.ui-check', par).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			par.find(".ui-check-right").css('display','none');
			me.validated = false;
			return;
		}
		
		if(me.dom.get(0) == loginPassword.get(0) && v_lpwd.getValue() !== v_lpwd2.getValue() && v_lpwd2.getValue() != ''){
			me.validated = false;
			$('.ui-check', v_lpwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			loginPassword2.parent().find(".ui-check-right").css('display','none');
			return;
		}
	};
	v_lpwd = new phoenix.Validator({el:loginPassword,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_lpwd2 = new phoenix.Validator({el:loginPassword2,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	loginPassword.focus(function(){
		var me = $(this),par = me.parent();
		$("#J-div-pwdstrength").css("display","none");
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	loginPassword2.focus(function(){
		var me = $(this),par = me.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	safePassword.focus(function(){
		var me = $(this),par = me.parent();
		$("#J-div-safestrength").css("display","none");
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	safePassword2.focus(function(){
		var me = $(this),par = me.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	answer1.focus(function(){
		var me = $(this),par = me.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	answer2.focus(function(){
		var me = $(this),par = me.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	answer3.focus(function(){
		var me = $(this),par = me.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).hide();
		$('.ui-text-prompt',par).show();
	});
	//安全密码密码校验
	showSuccessMessage = function(msg){
		var me = this,par = me.dom.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-check', par).css('display', 'none');
		$('.ui-text-prompt', par).css('display', 'none');
		//两次密码不一样
		if(me.dom.get(0) == safePassword2.get(0)){
			if(v_pwd.getValue() !== v_pwd2.getValue())
			{
			   me.validated = false;
			   par.find(".ui-check-right").css('display','none');
			   $('.ui-check', par).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			}else
			{
				me.validated = true;
				par.find(".ui-check-right").css('display','inline');
				
			}
		}
		if((/\s/).test(me.dom.get(0).value) && me != v_pwd2){
			me.validated = false;
			$('.ui-check', par).html('<i class="error"></i>密码不含空格');
			$('.ui-check', par).css('display', 'inline');
			return;
		}
		if((/^\d{1,8}$/).test(me.dom.get(0).value) && me != v_pwd2){
			me.validated = false;
			$('.ui-check', par).html('<i class="error"></i>密码不能是9位以下纯数字');
			$('.ui-check', par).css('display', 'inline');
			return;
		}
		if(me.dom.get(0) == safePassword.get(0)){
			if(v_pwd.getValue() !== v_pwd2.getValue() && v_pwd2.getValue() != ''){
				me.validated = false;
				showSafePassowrdLevel();
				safePassword.parent().find(".ui-check-right").css('display','inline');
				safePassword2.parent().find(".ui-check-right").css('display','none');
				$('.ui-check', v_pwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			}else{
				showSafePassowrdLevel();
				me.validated = true;
				par.find(".ui-check-right").css('display','inline');
				$('.ui-check', v_pwd2.dom.parent()).css('display', 'none');
			}
		}
		if(v_lpwd.getValue()==v_pwd.getValue()){
			me.validated = false;
			$("#J-div-safestrength").css("display","none");
			par.find(".ui-check-right").css('display','none');
			$('.ui-check', par).html('<i class="error"></i>登录密码不能与安全密码相同等').css('display', 'inline');
		}
	};
	showErrorMessage = function(msg){
		var me = this,par = me.dom.parent();
		par.find(".ui-check-right").css('display','none');
		$('.ui-text-prompt', par).css('display', 'none');
		if(me.dom.get(0) == safePassword.get(0) && me.dom.val()=="")
		{
			me.validated = false;
			par.find(".ui-check-right").css('display','none');
		    $('.ui-check', par).html('<i class="error"></i>请输入安全密码').css('display', 'inline');
		    return;
		}
		if(me.dom.get(0) == safePassword.get(0))
		{
		   me.validated = false;
		   par.find(".ui-check-right").css('display','none');
		   $('.ui-check', par).html('<i class="error"></i>' + msg).css('display', 'inline');
		   return;
		}
		if(me.dom.get(0) == safePassword2.get(0))
		{
			me.validated = false;
			par.find(".ui-check-right").css('display','none');
			$('.ui-check', par).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			return;
		}
		
		if(me.dom.get(0) == safePassword.get(0) && v_pwd.getValue() !== v_pwd2.getValue() && v_pwd2.getValue() != ''){
			me.validated = false;
			safePassword.parent().find(".ui-check-right").css('display','inline');
			safePassword2.parent().find(".ui-check-right").css('display','none');
			$('.ui-check', v_pwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			return;
		}
		if(v_lpwd.getValue()==v_pwd.getValue()){
			me.validated = false;
			par.find(".ui-check-right").css('display','none');
			$('.ui-check', par).html('<i class="error"></i>登录密码不能与安全密码相同等').css('display', 'inline');
			return;
		}
	};
	v_pwd = new phoenix.Validator({el:safePassword,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_pwd2 = new phoenix.Validator({el:safePassword2,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	
	//安全问题排重复
	selects.each(function(){
		selValues.push(this.value);
	});
	reBuildOptions = function(sel, v){
		var sels = selects.not(sel),_sel,_v,oldSelValue,
		
			firstStr,arrStr;
		//console.log(selValues);
		sels.each(function(){
			_sel = $(this);
			oldSelValue = _sel.val();
			arrStr = ['<option value="">请选择安全问题'+ _sel.attr('data-sort') +'</option>'];
			allOpts.each(function(){
				_v = this.getAttribute('value');
				if($.inArray(_v, selValues) < 0 || _sel.val() == _v){
					arrStr.push('<option value="'+ _v +'">'+ $.trim(this.innerHTML) +'</option>');
				}
			});
			_sel.html(arrStr.join(''));
			_sel.val(oldSelValue);
		});
	},
	selects.change(function(){
		var me = this,v = $.trim(me.value);
		if(v == ''){
			$('.ui-check', me.parentNode).css('display', 'inline');
		}else{
			$('.ui-check', me.parentNode).hide();
		}
		selValues = [];
		selects.each(function(){
			selValues.push(this.value);
		});
		reBuildOptions(me, v);
	});
	
	//获取密码复杂等级
	var getPassowrdLevel = function(v){
		var level = 0;
		if((/^\d+$/).test(v) || (/^[A-Za-z]+$/).test(v) || (/^[^a-zA-Z0-9]+$/).test(v)){
			level = 1;
		}
		if((/^\d+$/).test(v)){
			var first = v.charAt(0);
			for (var i = 0; i < v.length; i++) {
				if(first == v.charAt(i)){
					level = 3;
				}else if(Number(first)+Number(i) == v.charAt(i)){
					level = 2;
				}else{
					level = 1;
					break;
				}
			};
		}
		if(((/\d+/).test(v) && (/[A-Za-z]+/).test(v)) || ((/\d+/).test(v) && (/[^a-zA-Z0-9]+/).test(v)) || ((/[A-Za-z]+/).test(v) && (/[^a-zA-Z0-9]+/).test(v))){
			level = 4;
		}
		if((/\d+/).test(v) && (/[A-Za-z]+/).test(v) && (/[^a-zA-Z0-9]+/).test(v)){
			level = 5;
		}
		return level;
	};

	var showPassowrdLevel = function(v){
		level = getPassowrdLevel(loginPassword.val());
			var password_label = $("#password_label");
			password_label.removeAttr("class");

			if(level == 1){
				password_label.attr("class","pwd-new-weak");
				levelStr = '试试字母、数字、标点组合吧';
			}
			if(level == 2){
				password_label.attr("class","pwd-new-weak");
				levelStr = '连续字符密码易被破解，请用多组合的密码';
			}
			if(level == 3){
				password_label.attr("class","pwd-new-weak");
				levelStr = '相同字符密码易被破解，请用多组合的密码';
			}
			if(level == 4){
				password_label.attr("class","pwd-new-middle");
				levelStr = '密码复杂度中等';
			}
			if(level == 5){
				password_label.attr("class","pwd-new-strong");
				levelStr = '密码强度好，请牢记！';
			}	

			$("#J-div-pwdstrength").css("display","inline");
			$('#newcheckString').html(levelStr);
	}

	var showSafePassowrdLevel = function(v){
		level = getPassowrdLevel(safePassword.val());
			var password_label = $("#safe-password_label");
			password_label.removeAttr("class");

			if(level == 1){
				password_label.attr("class","pwd-new-weak");
				levelStr = '试试字母、数字、标点组合吧';
			}
			if(level == 2){
				password_label.attr("class","pwd-new-weak");
				levelStr = '连续字符密码易被破解，请用多组合的密码';
			}
			if(level == 3){
				password_label.attr("class","pwd-new-weak");
				levelStr = '相同字符密码易被破解，请用多组合的密码';
			}
			if(level == 4){
				password_label.attr("class","pwd-new-middle");
				levelStr = '密码复杂度中等';
			}
			if(level == 5){
				password_label.attr("class","pwd-new-strong");
				levelStr = '密码强度好，请牢记！';
			}	

			$("#J-div-safestrength").css("display","inline");
			$('#safecheckString').html(levelStr);
	}

	//安全问题校验
	answerSuccess = function(msg){
		$('.ui-text-prompt', this.dom.parent()).css('display', 'none');
		$('.ui-check', this.dom.parent()).css('display', 'none');
		$('.ui-check-right', this.dom.parent()).css('display', 'inline');
	};
	answerError = function(msg){
		$('.ui-text-prompt', this.dom.parent()).css('display', 'none');
		$('.ui-check-right', this.dom.parent()).css('display', 'none');
		$('.ui-check', this.dom.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
	};
	v_answer1 = new phoenix.Validator({el:answer1,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
	v_answer2 = new phoenix.Validator({el:answer2,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
	v_answer3 = new phoenix.Validator({el:answer3,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
	
	
	//表单验证
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	vGroup = [];
	vGroup.push(v_lpwd);
	vGroup.push(v_lpwd2);
	vGroup.push(v_pwd);
	vGroup.push(v_pwd2);
	vGroup.push(v_answer1);
	vGroup.push(v_answer2);
	vGroup.push(v_answer3);
	
	$('#J-button-submit').click(function(e){
		
	
		var passNum = 0,tplData = [];
		e.preventDefault();
		$.each(vGroup, function(){
			this.validate();
			
			if(this.validated){
				passNum++;
			}
		});
		selects.each(function(){
			var me = $(this);
			
			if(me.val() != ''){
				passNum++;
				$('.ui-check', me.parent()).hide();
			}else{
				$('.ui-check', me.parent()).css('display', 'inline');
			}
		});
		if(passNum < (vGroup.length + selects.size())){
			return;
		}
		//禁止弹窗后修改密码，安全密码跟登录密码不能相同
		if(loginPassword.val()!=loginPassword2.val() || safePassword.val()!=safePassword2.val() ||loginPassword.val()==safePassword.val()){	e.preventDefault(); minWindow.hide();return false;}
		
		//验证成功，进行提示确认
		selects.each(function(i){
			var _osel = $(this);
			tplData.push({'sort':(i+1),'id':_osel.val(), 'question':_osel.find("option:selected").text(), 'answer':vGroup[i+4].getValue()});
		});
		minWindow.setTitle('确认安全信息');
		minWindow.setContent($('#J-safequestions-tpl').html());
		$('#J-safequestions-row-tpl').tmpl(tplData).insertAfter('#J-safequestion-table .safequestion-th');
		minWindow.show();
		$('.j-ui-miniwindow').css("top","150px").css("display","inline");
				
		$('#J-safequestion-submit').click(function(){				
			$('#J-form').submit();
		});
		$('#J-safequestion-cancel').click(function(e){
			e.preventDefault();
			minWindow.hide();
		});
	});
	
	

})(jQuery);
</script>
{/literal}
	
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
</body>
</html>
