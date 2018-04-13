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
	.ui-check-right {background:url(../images/common/ui-check-right.gif) no-repeat;padding-left:24px;color:green;display:none;}
	</style>
	{/literal}
	
	{include file='/default/script-base.tpl'}
	
</head>
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
				<div class="title">修改安全问题</div>
				<div class="content">
				<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="{if $step eq 1}current{else}tri{/if}"><div class="con">验证安全问题</div></td>
									<td><div class="{if $step eq 2}current{else}tri{/if}"><div class="con">输入新安全问题</div></div></td>
									<td><div class="{if $step eq 3}current{else}tri{/if}"><div class="con">更新成功</div></div></td>
								</tr>
							</tbody>
						</table>
				</div>

				
					
					{if $step eq 1}
					<form method="post" id="J-form-step1" name="fm_spt1" action="/safepersonal/safequestedit?step=2">
					<ul class="ui-form set-safeissue">
						<li>
							<label class="ui-label">问题一：</label>
							<select id="J-question1" name="questId" class="ui-select" data-sort="一">
								<option value="">请选择安全问题一</option>
								{foreach from=$questList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题一</div>
						</li>
						<li>
							<label for="answer1" class="ui-label">答案：</label>
							<input type="text" name="questAns" value="" id="J-answer1" class="input">
							
                             <div class="ui-prompt" style="display:none">请输入答案</div> 
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						<li>
							<label class="ui-label">问题二：</label>
							<select id="J-question1" name="questId2" class="ui-select" data-sort="二">
								<option value="">请选择安全问题二</option>
								{foreach from=$questList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题二</div>
						</li>
						<li>
							<label for="answer2" class="ui-label">答案：</label>
							<input type="text" value="" name="questAns2" id="J-answer2" class="input">
							
                            <div class="ui-prompt" style="display:none">请输入答案</div>
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						<li class="ui-btn"><a class="btn" href="#" id="J-button-step1">下一步<b class="btn-inner"></b></a></li>
					</ul>
					</form>
					
					<!-- RSA Form-->
					<form action="/safepersonal/safequestedit?step=2" method="post" id="rsa-form-1">
						<input type="hidden" name="rsa_data" value="">
					</form>
					
{literal}
<script>
(function(){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	var form = $('#J-form-step1'),selects = form.find('.ui-select'),allOpts = selects.eq(0).find('option'),selValues = [],
		answer1 = $('#J-answer1'),
		answer2 = $('#J-answer2'),
		v_answer1,
		v_answer2,
		answerSuccess,
		answerError,
		vGroup,
		inputs=$('input'),
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
	selects.each(function() {
        $(this).prop("selectedIndex",0);
    });
	inputs.each(function() {
        $(this).val("");
    });
	//安全问题校验
	answerSuccess = function(msg){
		$('.ui-check', this.dom.parent()).css('display', 'none');
		
	};
	answerError = function(msg){
		$('.ui-check', this.dom.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
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
	$('#J-button-step1').click(function(e){
		var passNum = 0;
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
			//form.submit();
			//alert('');
			var rsa_data = rsa.encrypt($('#J-form-step1').serialize());
			$('#rsa-form-1 input[name=rsa_data]').val(rsa_data);
			$('#rsa-form-1').submit();
		}else{
			return false;
		}
	});
	
	answer1.focus(function(){	
		answer1.parent().find('.ui-check').css('display', 'none');
		answer1.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		answer1.parent().find('.ui-prompt').css('display', 'none');
	});
	answer2.focus(function(){	
		answer2.parent().find('.ui-check').css('display', 'none');
		answer2.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		answer2.parent().find('.ui-prompt').css('display', 'none');
	});
	
})();
</script>
{/literal}

					
					{else if $step eq 2}
					<select style="display:none;" id="J-question-all">
						{foreach from=$questFullList item=item}
						<option value="{$item.Id}">{$item.quest}</option>
						{/foreach}
					</select>
					<form method="post"  id="J-form-step2" name="fm_spt2" action="/safepersonal/safequestedit?step=3">
					<ul class="ui-form set-safeissue">
						<li>
							<label class="ui-label">问题一：</label>
							<select name="questId" class="ui-select" data-sort="一">
								<option value="">请选择安全问题一</option>
								{foreach from=$questFullList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题一</div>
						</li>
						<li>
							<label for="answer1" class="ui-label">答案：</label>
							<input type="text" value="" id="J-answer1" name="questAns" class="input">
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">请输入答案</div>
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						<li>
							<label class="ui-label">问题二：</label>
							<select name="questId2" class="ui-select" data-sort="二">
								<option value="">请选择安全问题二</option>
								{foreach from=$questFullList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题二</div>
						</li>
						<li>
							<label for="answer2" class="ui-label">答案：</label>
							<input type="text" value="" id="J-answer2" name="questAns2" class="input">
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">请输入答案</div>
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						<li>
							<label class="ui-label">问题三：</label>
							<select name="questId3" class="ui-select" data-sort="三">
								<option value="">请选择安全问题三</option>
								{foreach from=$questFullList item=item}
									<option value="{$item.Id}">{$item.quest}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择安全问题三</div>
						</li>
						<li>
							<label for="answer3" class="ui-label">答案：</label>
							<input id="J-answer3" name="questAns3" type="text" value="" class="input">
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">请输入答案</div>
							<div class="ui-check"><i class="error"></i>请输入答案</div>
						</li>
						<li class="ui-btn"><a class="btn" id="J-button-submit"  href="#" >下一步<b class="btn-inner"></b></a></li>
					</ul>
					</form>
					
					<!-- RSA Form-->
					<form action="/safepersonal/safequestedit?step=3" method="post" id="rsa-form-2">
						<input type="hidden" name="rsa_data" value="">
					</form>
					
					
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
	var answer1 = $('#J-answer1'),answer2 = $('#J-answer2'),answer3 = $('#J-answer3'),
	    answerSuccess,answerError,v_answer1,v_answer2,v_answer3,
		selects = $('.ui-select', '#J-form-step2'),
		reBuildOptions,allOpts = $('#J-question-all option'),
		selValues = [],inputs=$('input'),
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
		
		$('#J-safequestion-submit').click(function(e){
			e.preventDefault();
			//$('#J-form-step2').submit();
			//alert('');
			var rsa_data = rsa.encrypt($('#J-form-step2').serialize());
			$('#rsa-form-2 input[name=rsa_data]').val(rsa_data);
			$('#rsa-form-2').submit();
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

					
					{else if $step eq 3}
    					<div class="alert alert-success">
    						<i></i>
    						<div class="txt">
    							<h4>恭喜您，安全问题修改成功</h4>
    							<p><a href="/safepersonal/safecenter" class="btn">返回安全中心<b class="btn-inner"></b></a></p>
    						</div>
    					</div>
					{/if}
					
					
					
					
				</div>
			</div>
		</div>
	</div>
	
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
</body>
</html>