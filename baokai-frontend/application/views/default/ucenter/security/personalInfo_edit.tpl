<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>个人资料</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{literal}
	<style>
	.ui-check-right {padding-left:24px;color:green;display:none;}
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
			<div class="title">个人资料</div>
			<div class="content">
				<form action="/safepersonal/personalinfo?act=smt" method="POST" id="J-form" name="fm_main">
				<!-- ucenter-safe-setting-firsttime start -->
				<div class="safe-personal">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-form">
						<tr>
							<td align="right" width="150">用户名：</td>
							<td><span class="text-name">{$username}</span></td>
							<td></td>
						</tr>
						<tr>
							<td align="right">可用余额：</td>
							<td><span class="num-blue">{$validMoney}</span> 元<!-- &nbsp;&nbsp;&nbsp;&nbsp;冻结余额：<span class="num-red">{$hibMoney}</span> 元 --></td>
							<td></td>
						</tr>
						<tr class="row-sex">
							<td align="right">性别：</td>
							<td>
							
								<label><input name="sex" value="0" type="radio" {if $sex eq 0} checked {/if}> 男</label>
								<label><input name= "sex" value="1" type="radio" {if $sex neq 0} checked {/if}> 女</label>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">邮箱：</td>
							<td>
								
								{if $tip eq 1}
								<input id="J-email" type="text" class="input w-4" value= "{$vmail}" data-old-value='{$vmail}' name="vmail" data-validated='{$tip}'  maxlength="50"/>
								<input id="J-demail" type="hidden" class="input w-4" value= "{$mail}" data-old-value='{$mail}' name="mail" data-validated='{$tip}' />
								<span class="ui-check-right"></span>
								<span class="form-tip">绑定后可用于找回密码</span>
								<span class="form-tip-error"><span class="ui-check" style="display: inline;"><i></i>请输入正确的邮箱地址(4-50位)，如abc123@163.com</span></span>
									{if $mail eq ''}
										<span class="form-tip-email-new">(未验证) <a href="/safepersonal/bindmail">立即绑定</a></span> 
										<span class="form-tip-email-old-value">(已验证) <a href="/safepersonal/rebindmail">修改绑定</a></span> 
									{else}
										<span class="form-tip-show">(未验证) <a href="/safepersonal/bindmail">立即绑定</a></span> 
									{/if}
								
								{else}
								<input id="J-email" type="text" class="input w-4" value= "{$vmail}" disabled="disabled" data-old-value='{$vmail}' data-validated='{$tip}' />
								<span class="ui-check-right"></span>
								<span class="form-tip-show">(已验证) <a href="/safepersonal/rebindmail">修改绑定</a></span> 
								{/if}
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">手机号：</td>
							<td>
								<input id="J-mobile" type="text" class="input w-4" value= "{$vcellphone}" data-old-value='{$vcellphone}' name="vcellphone" maxlength="11"/>
								<input id="J-dmobile" type="hidden" class="input w-4" value= "{$cellphone}" data-old-value='{$cellphone}' name="cellphone"/>
								<span class="ui-check-right"></span>
								<span class="form-tip">请输入常用手机号码</span>
								<span class="form-tip-error"><span class="ui-check" style="display: inline;"><i></i>请输入正确的11位手机号码</span></span>
								
								</td>
							<td></td>
						</tr>
						<tr>
								<td align="right">头像：</td>
								<td>
									<ul class="headList" id="headList">
										<li id="head1"><img src="{$path_img}/images/ucenter/safe/head/head1.png"></li>
										<li id="head2"><img src="{$path_img}/images/ucenter/safe/head/head2.png"></li>
										<li id="head3"><img src="{$path_img}/images/ucenter/safe/head/head3.png"></li>
										<li id="head4"><img src="{$path_img}/images/ucenter/safe/head/head4.png"></li>
										<li id="head5"> <img src="{$path_img}/images/ucenter/safe/head/head5.png"></li>
										<li id="head6"><img src="{$path_img}/images/ucenter/safe/head/head6.png"></li>
										<li id="head7"><img src="{$path_img}/images/ucenter/safe/head/head7.png"></li>
										<li id="head8"><img src="{$path_img}/images/ucenter/safe/head/head8.png"></li>
										<li id="head9"><img src="{$path_img}/images/ucenter/safe/head/head9.png"></li>
										<li id="head10"><img src="{$path_img}/images/ucenter/safe/head/head10.png"></li>
									</ul>
									<input id="headNumber" type="hidden" value="{$nickImg}" data-old-value='{$nickImg}' name="headNumber"/>

								</td>
								<td></td>
						</tr>
						<tr>
							<td align="right">昵称：</td>
							<td>
								<input id="J-nickname" type="text" class="input w-4" value= "{$nickname}" data-old-value='{$nickname}' name="nickname" maxlength="16" onblur="check_nickname()"/>
								
								<span id ="check_right" class="ui-check-right" style="display:none"></span>
								<span id="check_error" class="form-tip-error" style="display:none"></span>
								
								</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">生日：</td>
							<td>
								{if $birth}
									{assign var="birthday_disabled" value=' disabled="disabled" '}
									<input type="hidden" id="J-birthIsSet" />
								{/if}
								<input type="hidden" id="J-data-birthday-year" value="{$birthday_year}" />
								<input type="hidden" id="J-data-birthday-month" value="{$birthday_month}" />
								<input type="hidden" id="J-data-birthday-day" value="{$birthday_day}" />
								<select id="J-birthday-year" {$birthday_disabled} name="bir[year]" >
									{if {$birthday_disabled} neq ''}
									<option value="">{$birth[0]}</option>
									{else}
    									<option value="">请选择</option>
    									{section name=yearloop loop=$year_num}
    									<option value="{$year_min + $smarty.section.yearloop.index}">{$year_min + $smarty.section.yearloop.index}</option>
    									{/section}
									{/if}
								</select>
								年
								<select id="J-birthday-month" {$birthday_disabled} name="bir[month]" >
								{if {$birthday_disabled} neq ''}
									<option value="">{$birth[1]}</option>
								{else}
									<option value="">请选择</option>
								{/if}
								</select>
								月
								<select id="J-birthday-day" {$birthday_disabled} name="bir[day]">
									{if {$birthday_disabled} neq ''}
										<option value="">{$birth[2]}</option>
									{else}
									<option value="">请选择</option>
									{/if}
								</select>
								日
								<span class="form-tip-show">一旦填写不能修改</span>
								<span class="form-tip-error"></span>
							</td>
							<td></td>
						</tr>

						<!-- -----------------------QQ设置相关部分-------------- -->

						
						<tr class="field-QQ">
							<td align="right"><span id="J-ico-qq" class="ico-qq">QQ设置：</span></td>
							<td >
								<input name="qqnickname[]" id="qqnickname1" type="text" class="input w-2 field-qqnickname" value="{if $vqq[0] neq ''}{$vqq[0]['nickName']}{/if}" maxlength="16" data-auto-num="1" />
								<input name="vqqnumber[]" id="vqqnumber1" type="hidden" type="text" class="input w-2 field-qqnumber" value="{if $vqq[0] neq ''}{$vqq[0]['qq']}{/if}" data-old-value="{if $vqq[0] neq ''}{$vqq[0]['qq']}{/if}" maxlength="11" data-auto-num="1" />
								<input name="qqnumber[]" id="qqnumber1"  class="input w-2 field-qqnumber" value="{if $qq[0] neq ''}{$qq[0]['qq']}{/if}" data-old-value="{if $qq[0] neq ''}{$qq[0]['qq']}{/if}" maxlength="11" data-auto-num="1" />
								
								<a id="J-add-qq" href="#" class="control-add">添加</a>
								
								<span class="form-tip-error form-tip-error-qqnickname"></span>
								<span class="form-tip-error form-tip-error-qqnumber"></span>
								
							</td>
							<td></td>
						</tr>
						{for $i=1 to $qq|@count-1 step 1}
                            <tr class="field-QQ">
    							<td align="right">QQ{$i+1}:</td>
    							<td>
    								<input type="text" data-auto-num="2" id="qqnickname{$i+1}" maxlength="16" value="{$qq[{$i}]['nickName']}"  class="input w-2 field-qqnickname" name="qqnickname[]"> 
    								<input type="text" data-auto-num="2" id="vqqnumber{$i+1}" maxlength="11" value="{$vqq[{$i}]['qq']}" data-old-value="{$vqq[{$i}]['qq']}" class="input w-2 field-qqnumber" name="vqqnumber[]">
    								<input type="hidden" data-auto-num="2" id="qqnumber{$i+1}" maxlength="11" value="{$qq[{$i}]['qq']}" data-old-value="{$qq[{$i}]['qq']}" class="input w-2 field-qqnumber" name="qqnumber[]">
                                    <a class="ui-text-info row-delete" href="javascript:void(0)">&nbsp;删除</a>
    								<span class="form-tip-error form-tip-error-qqnickname"></span>
    								<span class="form-tip-error form-tip-error-qqnumber"></span>
    							</td>
    							<td></td>
    						</tr>
                        {/for}
						
						

						<!-- ----------------------------------------------------- -->
						<tr class="row-last" id="J-field-qq-relative">
							<td align="right">&nbsp;</td>
							<td>
								<!-- <a href="#" class="btn">保 存<b class="btn-inner"></b></a> -->
								<!-- <input id="J_submit" type="submit" class="btn" value="保 存" tabindex="4" ></input> -->
								<a id="J-button-submit" class="btn"  href="#">保 存<b class="btn-inner"></b></a>
							</td>
							<td></td>
						</tr>
				</table>
				{literal}
				<script id="J-field-qq-tpl" type="text/html-tmpl"> 
					<tr class="field-QQ">
						<td align="right">QQ${num}:</td>
						<td >
							<input name="qqnickname[]" id="qqnickname${num}" type="text" class="input w-2 field-qqnickname" value="" maxlength="16" data-auto-num="${num}" />
							<input name="qqnumber[]" id="qqnumber${num}" type="text" class="input w-2 field-qqnumber" value="" maxlength="11" data-auto-num="${num}" />
							<a class="ui-text-info row-delete" href="javascript:void(0)">&nbsp;删除</a>
							<span class="form-tip-error form-tip-error-qqnickname"></span>
							<span class="form-tip-error form-tip-error-qqnumber"></span>
						</td>
						<td></td>
					</tr>
				</script> 
				{/literal}
				</div>
				<!-- ucenter-safe-setting-firsttime end -->
				
			</div>
			</form>
		</div>
	</div>
</div>
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->	





{literal}
<script>
(function($){
		//表单变量
	var form = $('#J-form'),email,emailPar,mobile,mobilePar,year,month,day,
	    //当前月份和日期
		oDate=new Date(),
		toYear=oDate.getFullYear(),toMonth=oDate.getMonth()+1,toDay=oDate.getDate(),
	    //QQ备注和Q号
		qqnick,qqnumber,
		//日期变量
		makeOption,clearOption,monthData,getMaxDay,selectOption,birth_y = Number($.trim($('#J-data-birthday-year').val())),birth_m = Number($.trim($('#J-data-birthday-month').val())),birth_d = Number($.trim($('#J-data-birthday-day').val())),
		checkBirthday = function(){
			//未设置过生日
			if($('#J-birthIsSet').size() <= 0){
				//全部不选择或者全部选择
				if((year.val() == '' && month.val() == '' && day.val() == '') || (year.val() != '' && month.val() != '' && day.val() != '')){
					setErrorNum('birthday', -1);
					year.parent().find('.form-tip-error').hide();
				//选择不完整
				}else{
					setErrorNum('birthday', 1);
					year.parent().find('.form-tip-error').html('生日选择不完整').show();
				}
			}
		},
	
		
		//提示变量
		tipCase,
		//QQ相关变量
		qqRowNum = form.find('.field-QQ').size(),
		checkQQNickName = function(v){
			var len = phoenix.util.getByteLen(v);
			return (len >= 4 && len <= 16);
		},
		checkQQNumber = function(v){
			return (/^\d{5,11}$/).test(''+v);
		},
		//表单检测错误数量
		errorTypes = ['email','mobile','birthday','QQText1','QQText2','QQText3','QQText4','QQText5','QQ1','QQ2','QQ3','QQ4','QQ5'],
		errorHas = {},
		setErrorNum = function(name, num){
			if(typeof errorHas[name] != 'undefined'){
				errorHas[name] += num;
				errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
			}
		};
		$.each(errorTypes, function(){
			errorHas[this] = 0;
		});
		
	//Email校验和交互
	email = $('#J-email');
	emailPar = email.parent();
	email.focus(function(){
		emailPar.find('.form-tip').show();
		emailPar.find('.form-tip-show,.form-tip-error,.form-tip-email-old-value,.form-tip-email-new,.ui-check-right').hide();
	}).blur(function(){
		var v = $.trim(this.value);
		//增加长度限制（6-50位）
		if((!(/^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/).test(v) && v != '' && $.trim(email.attr('data-old-value')) != v) || v.length <6 || v.length>50){
			emailPar.find('.form-tip-error').show();
			emailPar.find('.form-tip-email-old-value').hide();
			setErrorNum('email', 1);
			emailPar.find('.form-tip').hide();
		}else{
			emailPar.find('.ui-check-right').show();
			emailPar.find('.form-tip').hide();
			
			if($.trim(email.attr('data-old-value')) == v && $.trim(email.attr('data-validated')) != '1'){
				emailPar.find('.form-tip-email-old-value').show();
			}else{
				emailPar.find('.form-tip-email-new,.form-tip-show').show();
				if($.trim(email.attr('data-old-value')) != v){
					$('#J-demail').val(v);
				} else {
					$('#J-demail').val($('#J-demail').attr('data-old-value'));
				}
			}
			if(v == ''){
				emailPar.find('.form-tip-email-new,.form-tip-show,.form-tip-email-old-value').hide();
				emailPar.find('.form-tip').show();
			}
			setErrorNum('email', -1);
		}
		
	});
	
	//手机校验
	mobile = $('#J-mobile');
	mobilePar = mobile.parent();
	mobile.focus(function(){
		mobilePar.find('.form-tip').show();
		mobilePar.find('.form-tip-error,.ui-check-right').hide();
	}).blur(function(){
		var v = $.trim(this.value);
		if(v=='')
		{
			mobilePar.find('.form-tip-error').show();
			setErrorNum('mobile', 1);
		}
		
		else if(/^0?(13[0-9]|15[012356789]|18[0123456789]|17[0123456789]|14[57])[0-9]{8}$/.test(v)|| $.trim(mobile.attr('data-old-value')) == v || v==''){     
			mobilePar.find('.form-tip-error').hide();
			mobilePar.find('.ui-check-right').show();
			setErrorNum('mobile', -1);
			if($.trim(mobile.attr('data-old-value')) != v || v==''){
				$('#J-dmobile').val(v);
			} else {
				$('#J-dmobile').val($('#J-dmobile').attr('data-old-value'));
			}
		}else if(v!=''){
			mobilePar.find('.form-tip-error').show();
			setErrorNum('mobile', 1);
		}
		mobilePar.find('.form-tip').hide();
	});

	

	
	//日期联动
	makeOption = function(numMin, numMax){
		var i,result = [],numMax = numMax == 0 ? -1 : numMax;
		result.push('<option value="">请选择</option>');
		for(i = numMin;i <= numMax;i++){
			result.push('<option value='+ i +'>' + i + '</option>');
		}
		return result.join('');
	};
	clearOption = function(){
		return '<option value="">请选择</option>';
	};
	getMaxDay = function(y, m){
		var date = new Date(Number(y), Number(m), 0);
		return date.getDate();
	};
	selectOption = function(dom, value){
		$.each(dom.find('option'), function(){
			if(this.value == ''+value){
				this.selected = 'selected';
				return false;
			}
		});
	};
	monthData = makeOption(1, 12);
	//monthData = makeOption(1, toMonth);
	year = $('#J-birthday-year');
	month = $('#J-birthday-month');
	day = $('#J-birthday-day');
	year.change(function(){
		var v = this.value;
		if(v==toYear)
		{
			monthData = makeOption(1, toMonth);
			month.html(monthData);
		}else
		{
			monthData = makeOption(1, 12);
		    month.html(monthData);
		}
		day.html(makeOption(0, 0));
		checkBirthday();
	});
	month.change(function(){
		var v = this.value,maxDay = getMaxDay(Number(year.val()), Number(v));

		if(v==toMonth && year.val() == toYear)
		{
			day.html(makeOption(1, toDay));
		}else
		{
		  day.html(makeOption(1, maxDay));
		}
		checkBirthday();
	});
	day.change(function(){
		checkBirthday();
	});
	//初始化
	if(birth_y && birth_m && birth_d){
		selectOption(year, birth_y);
		month.html(makeOption(1, 12));
		selectOption(month, birth_m);
		day.html(makeOption(1, getMaxDay(birth_y, birth_m)));
		selectOption(day, birth_d);
	}
	if($("#qqnickname1").val()==''){
		qqnick = new phoenix.Input({el:$("#qqnickname1"),defText:"备注"});
	}
	if($("#qqnumber1").val()==''){
		qqnumber=new phoenix.Input({el:$("#qqnumber1"),defText:"Q号"});
	}
	//问号提示
	tipCase = new phoenix.Tip({cls:'j-ui-tip-l j-ui-tip-yellow'});
	tipCase.setText('设置成功后会出现在首页联系上级区域，您的下级可以通过点击此处QQ图标联系您~');
	$('#J-ico-qq').hover(function(){
		tipCase.show(28, -6, this);
	},function(){
		tipCase.hide();
	});
	//增加QQ
	$('#J-add-qq').click(function(e){
		e.preventDefault();
		if(qqRowNum >= 5){
			alert('最多设置5个QQ号码');
			return false;
		}
		$('#J-field-qq-tpl').tmpl({num:qqRowNum+1}).insertBefore($('#J-field-qq-relative'));
		qqRowNum++;
		qqnick = new phoenix.Input({el:$("#qqnickname"+qqRowNum),defText:"备注"});
	    qqnumber=new phoenix.Input({el:$("#qqnumber"+qqRowNum),defText:"Q号"});
	});
	form.on('click', '.row-delete', function(){
		$(this).parent().parent().remove();
		errorHas["QQText"+qqRowNum]=0;
		errorHas["QQ"+qqRowNum]=0;
		qqRowNum -= 1;
	});
	form.on('blur', '.field-qqnickname', function(e){
		var me = this,v = $.trim(me.value),nameNum = $.trim(this.getAttribute('data-auto-num'));
		if(v=='' || v== "备注")
		{
			$('.form-tip-error-qqnickname', me.parentNode).html('<span class="ui-check" style="display: inline;"><i></i>备注不能为空</span>').show();
			setErrorNum('QQText'+nameNum, 1);
		}
		else if(checkQQNickName(v)){
			$('.form-tip-error-qqnickname', me.parentNode).hide();
			setErrorNum('QQText'+nameNum, -1);
			if(v == '' && $('.field-qqnickname', me.parentNode).val() == ''){
				$('.form-tip-error-qqnickname,.form-tip-error-qqnickname', me.parentNode).hide();
				setErrorNum('QQText'+nameNum, -1);
				setErrorNum('QQ'+nameNum, -1);
			}
		}else{
			$('.form-tip-error-qqnickname', me.parentNode).html('<span class="ui-check" style="display: inline;"><i></i>备注4-16位字符</span>').show();
			setErrorNum('QQText'+nameNum, 1);
		}
	});
	form.on('blur', '.field-qqnumber', function(e){
		var me = this,v = $.trim(me.value),nameNum = $.trim(this.getAttribute('data-auto-num')),oldMe=$('[type="hidden"]', me.parentNode);
		if(v=='' || v == "Q号")
		{
			$('.form-tip-error-qqnumber', me.parentNode).html('<span class="ui-check" style="display: inline;"><i></i>Q号不能为空</span>').show();
			setErrorNum('QQ'+nameNum, 1);
			
		}else if(checkQQNumber(v) || $.trim($(me).attr('data-old-value')) == v){
			$('.form-tip-error-qqnumber', me.parentNode).hide();
			setErrorNum('QQ'+nameNum, -1);
			if(v == '' && $('.field-qqnumber', me.parentNode).val() == ''){
				$('.form-tip-error-qqnumber,.form-tip-error-qqnumber', me.parentNode).hide();
				setErrorNum('QQText'+nameNum, -1);
				setErrorNum('QQ'+nameNum, -1);
			}
			if($.trim($(me).attr('data-old-value')) != v || v==''){
				oldMe.val(v);
			} else {
				oldMe.val(oldMe.attr('data-old-value'));
			}
		}else{
			$('.form-tip-error-qqnumber', me.parentNode).html('<span class="ui-check" style="display: inline;"><i></i>号码5-11位数字</span>').show();
			setErrorNum('QQ'+nameNum, 1);
		}
	});
	
	
	$('#J-button-submit').click(function(){
		
		form.submit();
	});
	
	var headnumber = document.getElementById("headNumber").value;
	//console.log(headnumber);
	if(headnumber=='')
	{
		headnumber = '1';
	}
	
	$('#head'+headnumber).addClass('active');
	
	$('.headList li').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		var number = $(this).index() + 1;
		
		$('#headNumber').val(number);
	});
	
	//表单提交校验
	form.submit(function(e){
		//未设置过生日
	    var err = 0;
		var nickcheck;
		
		checkBirthday();
		nickcheck = check_nickname();
		if(nickcheck==false)
		{
		
			err = err + 1;
		}
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});
         
		if(err==0 && $('.field-QQ').length >1)
		{
			$('.field-QQ').find('[type=text]').each(function(index, element) {
                $(this).focus();
			    $(this).blur();
            });
		}
		if(err > 0){
			e.preventDefault();
			return false;
		}
		return true;
	});
	
	
	
	
	


	
})(jQuery);
</script>
<script>
var c="" 
function stringBytes(c){
	var n=c.length,s;
	var len=0;
	for(var i=0; i <n;i++){
		s=c.charCodeAt(i);
		while( s > 0 ){
			len++;
			s = s >> 8;
		}
	}
    return len;
}
	function check_nickname()
	{
		var nickname;
		var msg;
		
		nickname = document.getElementById("J-nickname").value;
		len = stringBytes(nickname);
		if(len < 4)
		{
			if(nickname=='')
			{
				$('#check_error').html('请输入昵称');
				$('#check_error').show();
				$('#check_right').hide();
				return false;
			}
			else
			{
				$('#check_error').html('字元少于4个：最少输入4个字元');
				$('#check_error').show();
				$('#check_right').hide();
				return false;
			}
			//$('#check_error').show();
			
		}
		
		if( len > 12 )
		{
			$('#check_error').html('字元多于12个：最多输入12个字元');
			$('#check_error').show();
			$('#check_right').hide();
			
			return false;
		}
		
		else if(!/^[\u4e00-\u9fa5a-zA-Z0-9]+$/.test(nickname))
		{
			$('#check_error').html('只支持英文、数字、汉字');
			$('#check_error').show();
			$('#check_right').hide();
			return false;
		}
		
		else if(len >= 4 && len <= 12) {
		
			$.ajax({
				
				url:"/Safepersonal/checknickname",
				dataType:'json',
				method:'post',
				async: false,
				data:{nickname:nickname},
			
				success:function(data){
					
					//username.parent().find('.check-right').css('display', 'inline-block');
					if(data['status'] == '0'){
						
						msg = true;
						
						$('#check_error').hide();
						$('#check_right').show();
					
					} 
					if (data['status'] == '100199'){
						
						$('#check_error').html('只支持英文、数字、汉字');
						$('#check_error').show();
						$('#check_right').hide();
						msg = false;	
					
					} 
					if (data['status'] == '100198'){
						
						$('#check_error').html('该昵称已经被占用');
						$('#check_error').show();
						$('#check_right').hide();
						msg = false;		
						
					}
					if (data['status'] == '100197'){
						
						$('#check_error').html('昵称每月只能修改一次，请您次月尝试');
						$('#check_error').show();
						$('#check_right').hide();
						msg = false;	
						
					}
				},
				
			});
			
			return msg;
		}
		
		else
		{	$('#check_error').hide();
			$('#check_right').show();
			
			return true;
		}
		
	}
	
</script>
{/literal}




</body>
</html>