<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{literal}
	<style>
	.ui-check-right {padding-left:24px;color:green;display:none;}
#J-button-submit{margin-left: 174px;}
	</style>
	{/literal}
	
	{include file='/default/script-base.tpl'}
	

<body>

{include file='/default/ucenter/header.tpl'}
	<div class="g_33 common-main">
    	<div class="g_5">
    		<!-- //////////////左侧公共页面////////////// -->
    			{include file='/default/ucenter/left.tpl'}
    		<!-- /////////////左侧公共页面/////////////// -->	
    	</div>
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">{$title}</div>
				
				
				<form action="?act=smt" method="post" id="J-form">
				<div class="content page-safecode-set">
					<dl class="prompt">
						<dt>温馨提示：</dt>
						<dd>安全信息是找回登录密码和资金流通安全凭证请妥善保存，切勿告诉他人。</dd>
					</dl>
					<h3 class="ui-title">设置安全问题</h3>
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
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">4-16位字符,可由中文、字母、数字组成</div> 
							<div class="ui-check"><i class="error"></i>请输入答案</div>
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
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">4-16位字符,可由中文、字母、数字组成</div> 
							<div class="ui-check"><i class="error"></i>请输入答案</div>
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
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">4-16位字符,可由中文、字母、数字组成</div> 
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						
						<li class="ui-btn"><a id="J-button-submit" class="btn"  href="#">设 置<b class="btn-inner"></b></a></li>
					</ul>
					<!-- ucenter-safe-setting-firsttime end -->
				</div>
				</form>
				
				<!-- RSA Form-->
				<form action="?act=smt" method="post" id="rsa-form">
					<input type="hidden" name="rsa_data" value="">
				</form>
				
				
			</div>
			
		</div>
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
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	var answer1 = $('#J-answer1'),answer2 = $('#J-answer2'),answer3 = $('#J-answer3'),answerSuccess,answerError,v_answer1,v_answer2,v_answer3,
		selects = $('.ui-select', '#J-safe-question-select'),reBuildOptions,allOpts = $('#J-question-all option'),selValues = [],
		inputs=$('input'),
		vGroup,
		minWindow,mask;
	
	//安全问题排重复
	selects.each(function(){
		selValues.push(this.value);
	});
	selects.each(function() {
        $(this).prop("selectedIndex",0);
    });
	inputs.each(function() {
        $(this).val("");
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
		
		//验证成功，进行提示确认
		selects.each(function(i){
			var _osel = $(this);
			tplData.push({'sort':(i+1),'id':_osel.val(), 'question':_osel.find("option:selected").text(), 'answer':vGroup[i].getValue()});
		});
		minWindow.setTitle('确认安全信息');
		minWindow.setContent($('#J-safequestions-tpl').html());
		$('#J-safequestions-row-tpl').tmpl(tplData).insertAfter('#J-safequestion-table .safequestion-th');
		minWindow.show();
		$('.j-ui-miniwindow').css("top","150px").css("display","inline");
		
		$('#J-safequestion-submit').click(function(e){
			e.preventDefault();
			//$('#J-form').submit();
			var rsa_data = rsa.encrypt($('#J-form').serialize());
			$('#rsa-form input[name=rsa_data]').val(rsa_data);
			$('#rsa-form').submit();
		});
		$('#J-safequestion-cancel').click(function(e){
			e.preventDefault();
			minWindow.hide();
		});
	});
	

	answer1.focus(function(){	
		answer1.parent().find('.ui-check').css('display', 'none');
		answer1.parent().find('.ui-prompt').css('display', 'inline');
		answer1.parent().find('.ui-check-right').hide();
	}).blur(function(){
		answer1.parent().find('.ui-prompt').css('display', 'none');
	});
	answer2.focus(function(){	
		answer2.parent().find('.ui-check').css('display', 'none');
		answer2.parent().find('.ui-prompt').css('display', 'inline');
		answer2.parent().find('.ui-check-right').hide();
	}).blur(function(){
		answer2.parent().find('.ui-prompt').css('display', 'none');
	});
	answer3.focus(function(){	
		answer3.parent().find('.ui-check').css('display', 'none');
		answer3.parent().find('.ui-prompt').css('display', 'inline');
		answer3.parent().find('.ui-check-right').hide();
	}).blur(function(){
		answer3.parent().find('.ui-prompt').css('display', 'none');
	});

})(jQuery);
</script>
{/literal}
	
	
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
</body>
</html>
