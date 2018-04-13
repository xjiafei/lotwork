<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>{$title}</title>
    <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
    <link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
    <script type="text/javascript">global_path_url="{$path_img}";</script>
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
	<script type="text/javascript" src="{$path_js}/js/phoenix.Timer.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Slider.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.GlobalAd.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
    <script type="text/javascript">
	(function() {       
		function async_load(){           
			var s = document.createElement('script');          
			s.type = 'text/javascript';          
			s.async = true;           
			s.src = "https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1";           
			var x = document.getElementsByTagName('script')[0];          
			x.parentNode.insertBefore(s, x);      
		}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false); 
	if(typeof customNum == "undefined") {
		var dt = new Date();
		customNum = "GUEST@" + dt.getHours() + dt.getMinutes() + dt.getSeconds() + "|||||||||||";
	}
	hjUserData=customNum; 
	})();
   </script>
	{literal}
	<style>
	html,body {height:100%;position:relative;overflow-x:hidden;}
	.footer {position:absolute;bottom:0;}
	.j-ui-miniwindow {width:600px;}
	</style>
	{/literal}
	
</head>
<body>
    
    {include file='/default/login/header.tpl'}
    
    <!-- step-num star -->
    <div class="step-num">
        <ul>
            <li {if $stp eq 0}class="current"{/if}><i class="step-num-1">1</i>输入用户名</li>
            <li {if $stp eq 1 or $stp eq 2 or $stp eq 3 or $stp eq 4}class="current"{/if}><i class="step-num-2">2</i>选择找回密码方式</li>
            <li {if $stp eq 5}class="current"{/if}><i class="step-num-3">3</i>重置密码</li>
            <li {if $stp eq 6}class="current"{/if}><i class="step-num-4">4</i>完成</li>
        </ul>
    </div>
    <!-- step-num end -->
    
    <div class="g_33">
			<div class="find-select-content">
            {if $stp eq 0}
            <form id="fm_stp" name="fm_stp" method="post" action="?stp=0">
            <ul class="ui-form">
                <li>
                    <label for="name" class="ui-label">用户名：</label>
                    <input type="text" value="" id="J-username" name="username"  class="input">
					<span class="ui-check-right"></span>
                    <div class="ui-check"></div>
                </li>
                <li>
                    <label for="pwd" class="ui-label">验证码：</label>
                    <input type="text" name="vcode" value="" maxlength="4" id="J-vcode" class="input w-3">
                    <img id="J-vcode-img" title="看不清，请点击更换图片"  class="verify-code" src="{$imageurl}" alt="验证码"  data-src-path="{$imageurl}" />
                    <div class="ui-check"><i class="error"></i>请输入验证码</div>
					<span class="ui-check-right"></span>
                </li>
                <li class="ui-btn"><a style="margin-left: 105px;" href="#" id="J-button-step1" class="btn">下一步<b class="btn-inner"></b></a></li>
            </ul>
         
{literal}
<script>
(function($){
	var username = $('#J-username'),
		vcode = $('#J-vcode'),
		v_username,
		v_vcode,
		showSuccessMessage,
		showErrorMessage,
		vimg = $('#J-vcode-img'),
		isVcodePass = false,
		refreshImg;
		
	new phoenix.Input({el:username,defText:'请输入你的用户名',focusClass:'input-focus'});
	new phoenix.Input({el:vcode,defText:'请输入右侧验证码',focusClass:'input-focus'});
	
	showSuccessMessage = function(msg){
		$('.ui-check', this.dom.parent()).hide();
	};
	showErrorMessage = function(msg,obj){
		$('.ui-check', obj.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
		
	};
	
	//v_username = new phoenix.Validator({el:username,type:'username',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_username = $('#J-username');
	v_vcode = new phoenix.Validator({el:vcode,type:'vcode',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	refreshImg = function(){
		vimg.attr('src', vimg.attr('data-src-path') + '?r=' + Math.random());
	};
	vimg.click(function(){
		refreshImg();
	});
	refreshImg();
	vcode.blur(function(){
		var v = $.trim(vcode.val());
		v_vcode.validate();
		if(v == '' || v == '请输入右侧验证码'){
			showErrorMessage.call(v_vcode, '验证码格式必须是4位的字母或数字');
			return;
		}
		$.ajax({
			url:'/login/checkvcode',
			method:'post',
			dataType:'json',
			async: false,
			data:{'vcode':v},
			beforeSend:function(){
				isVcodePass = false;
			},
			success:function(data){
				if(Number(data['status']) == 1){
					showSuccessMessage.call(v_vcode, data['data']);
					$('.ui-check-right', vcode.parent()).show();
					isVcodePass = true;
				}else{
					showErrorMessage.call(v_vcode, data['data']);
					$('.ui-check-right', vcode.parent()).hide();
				}
			}
		});
		
		
	});
	//去除下一步要点两次才执行
	$('#J-button-step1').click(function(e){
		e.preventDefault();
		nextTostep2();
	});
	
	function nextTostep2(){
		
		var userNames=$.trim($('#J-username').val());	
		//v_username.validate();
		v_vcode.validate();
		//vcode.blur();
		if(!isVcodePass){
			$('.ui-check', vcode.parent()).css('display','inline');
			return;
		}
		if(v_vcode.validated && isVcodePass){
			$.ajax({
				url:'?stp=0',
				data:{vcode:v_vcode.getValue().toUpperCase(),username:username.val()},
				dataType:'json',
				method:'POST',
				success:function(data){
					var errors;
					if(data['isSuccess']){
						location.href = '?stp=1';
					}else{
						errors = data['errors'];
						$.each(errors, function(){
							var err = this;
							if(err[0] == 'vcode'){
								v_vcode.showErrorMessage(err[1]);
								vcode.val('').focus();
							}
							if(err[0] == 'username'){
								showErrorMessage(err[1],v_username);
								username.focus();
							}
						});
						$('.ui-check-right', vcode.parent()).hide();
						refreshImg();
					}
					
				},
				error:function(xhr, type){
					refreshImg();
				}
			});
		}
	}
	
	

	
})(jQuery);

</script>
{/literal}
            {else if $stp eq 1}
            <strong class="highbig">您正在找回登录密码的账号是：<span class="highlight">{$uName}</span>，请选择您准备找回登录密码的方式：</strong>
            <ul class="find-select-list">
                {if $isBindMail eq 1}
                <li>
                    <i class="ico-mail"></i>
                    <p>通过绑定的邮箱找回登录密码<br />您绑定的 邮箱为{$mail}</p>
                    <a href="?stp=2" class="btn">立即找回<b class="btn-inner"></b></a>
                </li>
                {else}
                <li class="disable">
                    <i class="ico-mail"></i>
                    <p>通过绑定的邮箱找回登录密码<br /></p>
                    <span>(未绑定，不可用)</span>
                </li>
                {/if}
                {if $isSetSafeQuest eq 1}
                <li>
                    <i class="ico-safequestion"></i>
                    <p>通过回答“安全问题”找回登录密码</p>
                    <a href="?stp=3" class="btn" id="J-button-safequestion">立即找回<b class="btn-inner"></b></a>
                </li>        
                {else}
                <li class="disable">
                    <i class="ico-safequestion"></i>
                    <p>通过回答“安全问题”找回登录密码</p>
                    <span>(未绑定，不可用)</span>
                </li>
                {/if}
                {if $isSetSafeCode eq 1}
                <li>
                    <i class="ico-safecode"></i>
                    <p>通过安全密码找回登录密码</p>
                    <a id="J-button-safePassword" href="?stp=4" class="btn">立即找回<b class="btn-inner"></b></a>
                </li>
                {else}
                    <li class="disable">
                        <i class="ico-safecode"></i>
                        <p>通过安全密码找回登录密码</p>
                        <span>(未绑定，不可用)</span>
                    </li>
                {/if}
            </ul>
            <p>上面的方式都不可用？您还可以通过<a  href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank">在线客服</a>进行人工申诉找回登录密码。</p>
            
            <div class="pop w-7" style="position: fixed; left: 50%; z-index: 1001; top: 50%; margin-top: -77.5px; margin-left: -186px; display: none;" id="divNoType">
	<div class="hd"><i class="close" name="divCloseUrl"></i>提示</div>
	<div class="bd">
		<h4 class="pop-title">安全问题连续三次错误，请30分钟后再试</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " name="J-but-close">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
			
		<script id="J-safePassword-tpl" type="text/html-tmpl"> 
                    <ul class="ui-form">
                        <li>
                            <label class="ui-label" for="verifyCode">输入安全密码：</label>
                            <input type="password" class="input" id="J-safePassword" value="" name="safepwd">
                            <div class="ui-check"><i class="error"></i>请输入安全密码</div>
                        </li>
                        <li class="ui-btn"><a id="J-submit-safePassword" href="#" class="btn">下一步<b class="btn-inner"></b></a></li>
                    </ul>
		</script>
{literal}
<script>
(function($){
	var minWindow,mask,initFn;
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance();
		minWindow.addEvent('beforeShow', function(){
			mask.show();
		});
		minWindow.addEvent('afterHide', function(){
			mask.hide();
		});
	initFn = function(){
		var safePassword = $('#J-safePassword'),
			v_safePassword;
		
		v_safePassword = new phoenix.Validator({el:safePassword,type:'password',expands:{showSuccessMessage:function(msg){
			$('.ui-check', this.dom.parent()).css('display', 'none');
		},showErrorMessage:function(msg){
			$('.ui-check', this.dom.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
		}}});
		
		$('#J-submit-safePassword').click(function(e){
			e.preventDefault();
			v_safePassword.validate();
			if(v_safePassword.validated){
				$.ajax({
					url:'?stp=4&act=1',
					dataType:'json',
					method:'POST',
					data:{safepwd:v_safePassword.getValue()},
					success:function(data){
						var errors;
						if(data['isSuccess']){
							location.href = '?stp=5';
						}else{
							errors = data['errors'];
							$.each(errors, function(){
								var err = this;
								if(err[0] == 'safepwd'){
									v_safePassword.showErrorMessage(err[1]);
								}
							});
						}
					},
					error:function(xhr, type){
						
					}
				});
			}
		});
	};
	  $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
	 });
	$('#J-button-safePassword').click(function(e){
		e.preventDefault();
		minWindow.setTitle('请填写您的安全密码');
		minWindow.setContent($('#J-safePassword-tpl').html());
		minWindow.show();
		initFn();
	});
})(jQuery);
</script>
{/literal}
			

	<script id="J-safequestions-tpl" type="text/html-tmpl"> 
                    <ul class="ui-form set-safeissue">
                        <li>
                            <label class="ui-label" for="question1">问题一：</label>
                            <select id="J-question1" name="questId" class="ui-select" data-sort="一">
								<option value="">请选择安全问题一</option>
								{foreach from=$questList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题一</div>
                        </li>
                        <li>
                            <label class="ui-label" for="answer1">答案：</label>
                            <input type="text" class="input" id="J-answer1" name="questAns" value="">
                            <div class="ui-check"><i class="error"></i>请输入答案</div>
                        </li>
                        <li>
                            <label class="ui-label" for="question2">问题二：</label>
                            <select id="J-question1" name="questId2" class="ui-select" data-sort="二">
								<option value="">请选择安全问题二</option>
								{foreach from=$questList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题二</div>
                        </li>
                        <li>
                            <label class="ui-label" for="answer2">答案：</label>
                            <input type="text" class="input" id="J-answer2" name="questAns2" value="">
                            <div class="ui-check"><i class="error"></i>请输入答案</div>
                        </li>
                        <li class="ui-btn"><a href="#" class="btn" id="J-submit-safequestion">下一步<b class="btn-inner"></b></a></li>
                    </ul>
	</script>
    <script id="J-safequestions-error" type="text/html-tmpl"> 
		<ul class="ui-form set-safeissue">
		    <li> 
		      <h4 class="pop-title">安全问题连续三次错误，请30分钟后再试</h4>
			</li>
			<li><div class="pop-btn"><a href="#" class="btn btn-important" id="J-submit-error">确定<b class="btn-inner"></b></a></div></li>
		</ul>
	</script>
{literal}
<script>
(function($){
	var minWindow,mask,initFn,
		//安全问题最多允许填写次数
		quErrMaxTimes = 10;
		
	//$(function(){
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance();
		minWindow.addEvent('beforeShow', function(){
			mask.show();
		});
		minWindow.addEvent('afterHide', function(){
			mask.hide();
		});
	//});

	$('#J-button-safequestion').click(function(e){
		e.preventDefault();
		minWindow.setTitle('请填写您的安全问题');
		minWindow.setContent($('#J-safequestions-tpl').html());
		minWindow.show();
		initFn();
	});
	

	initFn = function(){
		var cont = $('.set-safeissue'),selects = cont.find('.ui-select'),allOpts = selects.eq(0).find('option'),selValues = [],
			answer1 = $('#J-answer1'),
			answer2 = $('#J-answer2'),
			v_answer1,
			v_answer2,
			answerSuccess,
			answerError,
			vGroup,
			reBuildOptions = function(sel, v){
				var sels = selects.not(sel),_sel,_v,oldSelValue,arrStr;
	
				sels.each(function(){
					_sel = $(this);
					oldSelValue = _sel.val();
					arrStr = ['<option value="">请选择安全问题'+ _sel.attr('data-sort') +'</option>'];
					allOpts.each(function(i){
						if(i > 0){
							_v = this.getAttribute('value');
							if($.inArray(_v, selValues) < 0 || _sel.val() == _v){
								arrStr.push('<option value="'+ _v +'">'+ $.trim(this.innerHTML) +'</option>');
							}
						}
					});
					_sel.html(arrStr.join(''));
					_sel.val(oldSelValue);
				});
			};
		
		//安全问题校验
		answerSuccess = function(msg){
			$('.ui-check', this.dom.parent()).css('display', 'none');
			$('.ui-check-right', this.dom.parent()).show();
		};
		answerError = function(msg){
			$('.ui-check', this.dom.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
			$('.ui-check-right', this.dom.parent()).hide();
		};
		v_answer1 = new phoenix.Validator({el:answer1,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
		v_answer2 = new phoenix.Validator({el:answer2,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
		
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
	
		vGroup = [];
		vGroup.push(v_answer1);
		vGroup.push(v_answer2);
		$('#J-submit-safequestion').click(function(e){
			var passNum = 0;
			//quErrTimes = !!quErrTimes ? quErrTimes : 0;
			//if(quErrTimes > quErrMaxTimes){
				//alert('您的安全问题验证错误次数过多，请24小时之后再试！');
				//return false;
			//}
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
			if(passNum >= (vGroup.length + selects.size())){
				$.ajax({
					url:'?stp=3&act=1',
					dataType:'json',
					method:'POST',
					data:{questId:selects.get(0).value,questId2:selects.get(1).value,questAns:v_answer1.getValue(),questAns2:v_answer2.getValue()},
					success:function(data){
						if(data['isSuccess']){
							location.href = '?stp=5';
						}else{
							//$.cookie('quErrTimes', quErrTimes+1, {expires:1});
							$.each(data['errors'], function(){
								var err = this;
								if(err[0] == 'ansError'){
									v_answer1.showErrorMessage(err[1]);
								}else if(err[0] == 'ansError1'){
									v_answer2.showErrorMessage(err[1]);
								} else{
									minWindow.setTitle('提示');
									minWindow.setContent($('#J-safequestions-error').html());
									minWindow.show();
									$("#J-submit-error").click(function(){
										minWindow.hide();
									});
								}
							});

						}
					},
					error:function(xhr, type){
						alert('网络异常，请联系客服！');
					}
				});
			}else{
				return false;
			}
		});
	};


	
	
})(jQuery);
</script>
{/literal}
			
			
			
			
            {else if $stp eq 2}
                <ul class="ui-form">
                    <li>登录密码找回邮件已发送至您的邮箱：<strong class="light">{$mail}</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成登录密码找回。<br />您的激活链接在24小时内有效。</li>
                    <li><a href="/login/findpwd?stp=1" class="btn">上一步<b class="btn-inner"></b></a></li>
                    <li>没有收到邮件？</li>
        			<li><a class="btn" id="J-button-resubmit" href="" style="color:#CCC;">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);"><span id="J-time">60</span>s<b class="btn-inner"></b></a></li>
                </ul>
                <dl class="help-text">
                    <dt>如果半小时内没有收到邮件</dt>
                    <dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
                    <dd>联系<a id="custom-service" href="javascript:void(0);" />在线客服</a>，由客服帮你解决</dd>
                </dl>

{literal}
<script>
(function($){
	var button = $('#J-button-resubmit'),dom = $('#J-time'),timeCase,sc = 60,fn;
	fn = function(){
		if(sc <= 0){
			dom.html(sc--).parent().hide();
			button.css('color', '#333');
			timeCase.remove(fn);
		}else{
			dom.html(sc--);
		}
	};
	timeCase = phoenix.Timer({time:1000,fn:fn});
	button.click(function(e){
		e.preventDefault();
		if(sc > 0){
			return false;
		}
		location.href = '?stp=2';
	});
	 $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
	 });
})(jQuery);
</script>
{/literal}
				
				
				
            {else if $stp eq 3}

            {else if $stp eq 4}

			
			
			
			
			
			
            {else if $stp eq 5}
            <form id="J-form-password" name="fm_stp" method="post" action="?stp=0">
            <div class="find-reset-content" style="padding:0;">
                <ul class="ui-form">
                    <li class="ui-text"><strong class="highbig">设置新的登录密码</strong><br />您申请了找回密码，为保护您的账号安全，您的当前登录密码系统已随机重置，请立即修改为您常用的新的密码。</li>
                    <li>
                        <label for="name" class="ui-label">请输入新的密码：</label>
                        <input type="password" value="" id="J-password-new" class="input">
                         <div class="ui-prompt" style="display:none">6-20位字符组成，区分大小写</div>      
                        <div class="ui-check"><i class="error"></i>登录密码应为6-20位字符</div>
                        <div class="pwd-new-strength" id="J-div-pwdstrength" style="margin-left: 20px; width: 270px; display: none;">密码强度：弱
                                	<span class="pwd-new-bar"><b id="password_label" class="pwd-new-weak"></b></span>强<span id="newcheckString"></span></div>
                    </li>
                    <li>
                        <label for="pwd" class="ui-label">再次输入新密码：</label>
                        <input type="password" value="" id="J-password-new2" class="input">
						<span class="ui-check-right"></span>
						 <div class="ui-prompt" style="display:none">请再次输入登陆密码</div>
						<div class="ui-check"><i class="error"></i>两次输入的密码不一致</div>
                    </li>
                    <li class="ui-btn"><a id="J-button-submit-password" href="#" class="btn">确 定<b class="btn-inner"></b></a></li>
                </ul>
            </div>
            </form>
{literal}
<script>
(function($){
	//输入框
	var password_new = $('#J-password-new'),
	password_new2 = $('#J-password-new2'),
	v_password_new,
	v_password_new2,
	showSuccessMessage,
	showErrorMessage,
	//按钮
	button_password = $('#J-button-submit-password'),
	//表单
	form_password = $('#J-form-password'),
	vGroup_password = [],


	//登陆密码校验
	showSuccessMessage = function(msg){
		var me = this;
		$('.ui-check', me.dom.parent()).hide();
		$('.ui-check-right', me.dom.parent()).show();
		
		//登录密码两次新密码不一致
		if(me.dom.get(0) == password_new2.get(0)){
			if(v_password_new2.getValue() != v_password_new.getValue()){
				v_password_new2.validated = false;
				$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
				$('.ui-check-right', v_password_new2.dom.parent()).hide();
			}else{
				v_password_new2.validated = true;
				$('.ui-check', v_password_new2.dom.parent()).hide();
			}
		}		
		if(me.dom.get(0) == password_new.get(0)){
			if(v_password_new2.getValue() != v_password_new.getValue() && v_password_new2.getValue() != ''){
				v_password_new2.validated = false;
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
				$('.ui-check-right', v_password_new2.dom.parent()).hide();
			}else{
				v_password_new2.validated = true;
				$('.ui-check', v_password_new.dom.parent()).hide();
				$('.ui-check', v_password_new2.dom.parent()).hide();
			}
		}
	};
	showErrorMessage = function(msg){
		var me = this;
		if(me.dom.get(0) == password_new.get(0) && v_password_new.getValue() == '')
		{
		    $('.ui-check', me.dom.parent()).html('<i class="error"></i>请输入新的登录密码');
		}else if(me.dom.get(0) == password_new.get(0))
		{
			 $('.ui-check', me.dom.parent()).html('<i class="error"></i>'+msg);
		}
		$('.ui-check', me.dom.parent()).css('display', 'inline');
		$('.ui-check-right', me.dom.parent()).hide();
	};
	
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
	
	
	
	
	v_password_new = new phoenix.Validator({el:password_new,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_password_new2 = new phoenix.Validator({el:password_new2,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	password_new.blur(function(){
		if(v_password_new.validated)
		{
			$('.ui-check-right', v_password_new.dom.parent()).hide();
			$('.ui-check-right', v_password_new2.dom.parent()).hide();
			var level = 1,levelStr = '',v = $.trim(password_new.val());
			if((/\s/).test(v)){
				password_new.parent().find('.ui-prompt').css('display', 'none');
				$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>密码不含空格');
				password_new.parent().find('.ui-check').css('display', 'inline');
				return null;
			}else if((/^\d{1,8}$/).test(v)){
				password_new.parent().find('.ui-prompt').css('display', 'none');
				$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>密码不能是9位以下纯数字');
				password_new.parent().find('.ui-check').css('display', 'inline');
				return null;
			}
			$.ajax({
				url:'?stp=5&act=chck',
				data:{pwd:v_password_new.getValue()},
				dataType:'json',
				method:'POST',
				success:function(data){
					if(data['isSuccess'] =='1'){
						v_password_new.validate();
						if(v_password_new.validated){
							level = getPassowrdLevel(v);
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
					} else {
						$('.ui-check-right', v_password_new.dom.parent()).hide();
						$('.ui-check-right', v_password_new2.dom.parent()).hide();
						$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>'+data['data']);
					}
				},
				error:function(xhr, type){
				}
			});
		}
	});
	
	
	//表单提交
	vGroup_password.push(v_password_new);
	vGroup_password.push(v_password_new2);
	//登录密码
	button_password.click(function(e){
		var passNum = 0;
		e.preventDefault();
		$.each(vGroup_password, function(){
			var me = this;
			me.validate();
			if(me.validated){
				passNum++;
			}
		});
		if(passNum >= vGroup_password.length){
			if(v_password_new2.getValue() != v_password_new.getValue()){
				$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
			}else if((/\s/).test($.trim(password_new.val()))){
				password_new.parent().find('.ui-prompt').css('display', 'none');
				$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>密码不含空格');
				password_new.parent().find('.ui-check').css('display', 'inline');
				return null;
			}else if((/^\d{1,8}$/).test($.trim(password_new.val()))){
				password_new.parent().find('.ui-prompt').css('display', 'none');
				$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>密码不能是9位以下纯数字');
				password_new.parent().find('.ui-check').css('display', 'inline');
				return null;
			}else{
				$.ajax({
					url:'?stp=5&act=reset',
					data:{pwd:v_password_new.getValue()},
					dataType:'json',
					method:'POST',
					success:function(data){
						if(data['isSuccess'] =='1'){
							location.href = '?stp=6';
						} else {
							$('.ui-check-right', v_password_new.dom.parent()).hide();
							$('.ui-check-right', v_password_new2.dom.parent()).hide();
							$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>'+data['data']);
							$('.ui-check', v_password_new.dom.parent()).css('display', 'inline');
						}
					},
					error:function(xhr, type){
					}
				});
			}
		}else{
			return false;
		}
	});

	//  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });

				
	password_new.focus(function(){	
		$("#J-div-pwdstrength").css("display","none");
	    password_new.parent().find('.ui-check').css('display', 'none');
		password_new.parent().find('.ui-check-right').css('display', 'none');
		password_new.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		password_new.parent().find('.ui-prompt').css('display', 'none');
	});
	password_new2.focus(function(){	
	    password_new2.parent().find('.ui-check').css('display', 'none');
		password_new2.parent().find('.ui-check-right').css('display', 'none');
		password_new2.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		password_new2.parent().find('.ui-prompt').css('display', 'none');
	});
	
	
	
})(jQuery);
</script>
{/literal}
			
			
			
			
			
			
			
            {else if $stp eq 6}
                    <div class="content">
                        <div class="alert alert-success" style="text-align:left;padding-left:50px;">
                            <i></i>
                            <div class="txt">
                                <h4>恭喜您，密码重置成功！</h4>
                                <p><a class="btn" href="/login/">立即登录<b class="btn-inner"></b></a></p>
                            </div>
                        </div>
                    </div>
            {/if}
			
			</div>

    </div>
    
    {include file='/default/login/footer.tpl'}
    


{literal}
<script>
(function($){
    var footer = $('#footer');
    footer.css('position','fixed');
    if($(document).height()>$(window).height()){
        footer.css('position','static');
    }
	
})(jQuery);
</script>
{/literal}

</body>
</html>
