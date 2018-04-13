<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>银行卡绑定</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	

<body>
{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<form  method="post" id="J-form" action="/bindcard/bindcardadd">		
		<div class="g_28 g_last">
			<div class="common-content bank-card-management ">
				<div class="title">银行卡绑定</div>
				<div class="content">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="current"><div class="con">1.输入银行卡信息</div></td>
									<td><div class="tri"><div class="con">2.绑定成功</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<ul class="ui-form">
						<li>
							<label for="name" class="ui-label">开户银行：</label>
							<select class="ui-select" name="bankid" id="bankid">
								<option value="0">请选择银行</option>
								{foreach from=$banklist item=data key=key}
								<option {if $data.code == $bankid} selected {/if} value="{$data.code}">{$data.name}</option>
								{/foreach}
							</select>
							<div class="ui-check"><i class="error"></i>请选择您的开户银行</div>
						</li>
						<li class="address">
							<label for="name" class="ui-label">开户银行所在城市：</label>
							<select name="province" class="ui-select"  id="province"></select>
							<select  name="city" class="ui-select" id="city"></select>                         
							<div class="ui-check"><i class="error"></i>请选择开户银行所在城市</div>
						</li>
							<div class="ui-check"><i class="error"></i>请选择您的开户银行所在城市</div>
						</li>
						<li>
							<label for="name" class="ui-label">支行名称：</label>
							<input type="text" value="" class="input w-4" name="branchAddr" id="branchAddr"  autocomplete="off"> 
                               
                            <div class="ui-check"><i class="error"></i>请填写您的开户支行名称</div></br><div  class="ui-prompt"><span style="padding-left:140px;" class="ui-prompt">填写支行名称遇到问题，请点击这里<a href="http://www.cardbaobao.com/bank/index.asp" target="_blank">参考</a></span></div>  
						</li>
						<li>
							<label for="name" class="ui-label">开户人：</label>
							<input type="text" value="" class="input w-4" maxlength="30" name="bankAccount" id="bankAccount" autocomplete="off">
							<div class="ui-check"><i class="error"></i>请填写您的开户人名称</div>
						</li>
						<li>
							<label for="name" class="ui-label">银行卡号：</label>
							<input type="text" value="" class="input w-4" name="bankNumber" id="bankNumber" autocomplete="off">
                            <div class="ui-prompt"><span class="ui-prompt">由16或18或19位数字组成，请填写银行借记卡</span></div>   
							<div class="ui-check"><i class="error"></i>由16或18或19位数字组成，请填写银行借记卡</div></br>
						</li>
						<li>
							<label for="name" class="ui-label">确认银行卡号：</label>
							<input type="text" onpaste="return false"  value="" class="input w-4" id="bankNumber2" autocomplete="off">  
                            <div class="ui-prompt"><span class="ui-prompt">请再输入一次卡号信息，并保持输入一致</span></div>                         
							<div class="ui-check"><i class="error"></i>输入的卡号信息不一致，请重新输入</div>
						</li>
						<li>
							<label for="name" class="ui-label">安全密码：</label>
							<input type="password" value="" class="input w-4" name="securityPassword" id="securityPassword" autocomplete="off">
                            <div class="ui-check"><i class="error"></i>请输入您的安全密码 <a href="/safepersonal/sefecoderetrieve?stp=2" target="_blank">忘记安全密码</a></div>
							
						</li>
						<li class="ui-btn"><a href="javascript:void(0);" class="btn btn-important" id="J-Submit">提 交<b class="btn-inner"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
	</form>
	</div>  
      <div class="pop pop-search" id="div_no" style="position:absolute;z-index:2; display:none">  
		<div class="hd"><i class="close" id="CloseDiv"></i>银行卡绑定提醒</div>
		<div class="bd">
			<div class="pop-error">
				<i class="ico-error"></i>
				<h4 class="pop-text"><span id="errortype"></span></h4>
			</div>
			<div class="pop-btn">
				<a class="btn btn-important " id="reType">重新輸入<b class="btn-inner"></b></a>
<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank" class="ui-text-info">联系客服</a>			
</div>
		</div>
	</div>
	
	
    <div class="pop w-10" id="div_ok" style="position:absolute;left:600px;display:none">  
		<div class="hd"><i class="close" id="CloseDiv2"></i>银行卡绑定提醒</div>
		<div class="bd">
			<div class="pop-success">
				<i class="ico-success"></i>
				<h4 class="pop-text">银行卡绑定成功！</h4>
			</div>
            <div class="bank-cash">
					<div class="bank-more-content">
						<table>
							<tbody>
								<tr>
									<td>开户人姓名：<span id="new_bankAccount"></span></td>
								</tr>
								<tr>
									<td>开户银行：<span id="new_bankName"></span></td>
								</tr>
								<tr>
									<td>银行卡号：<span id="new_bankCardId"></span></td>
								</tr>
							</tbody>
						</table>
					</div>
			</div>   

			<div class="pop-btn">
				<a class="btn btn-important " href="/fund">立即充值<b class="btn-inner"></b></a>
				<a class="btn" href="/withdraw">发起提现<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
   
    
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script src="{$path_js}/js/phoenix.area.js"></script> 
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script  src="{$path_js}/js/phoenix.Verification.js"></script>
<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
{literal}
<script>
(function(){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	//开户银行所在城市	
	var oProvince=$('#province')[0];
	var oCity=$('#city')[0];
	for(var i=0;i<aProvince.length;i++){
		oProvince.options[i]=new Option(aProvince[i]);	
	}
	for(var i=0;i<aCity[0].length;i++){	
		oCity.options[i]=new Option(aCity[0][i]);	
	}	
	oProvince.onchange=function(){
		oCity.length=0;
		 for(var i=0;i<aCity[this.selectedIndex].length;i++){
            oCity.options[i]=new Option(aCity[this.selectedIndex][i]);
        }
	};	
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";						
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";		
	} 	
	//数字校验，自动矫正不符合数学规范的数学(小数点保留两位)
	var inputs = $('#bankNumber'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/[^\d]/g,'');								
	};		
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	var inputs2 = $('#bankNumber2'),checkFn2;				
	checkFn2 = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');							
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');								
	};		
	inputs2.keyup(checkFn2);
	inputs2.blur(checkFn2);
	
	var form = $('#J-form'),bankid,bankidPar,province,provincePar,branchAddr,branchAddrPar,bankAccount,bankAccountPar,bankNumber,bankNumberPar,
		bankNumber2,bankNumber2Par,securityPassword,securityPasswordPar,isture="0",box = new LightBox("div_no"),box1 = new LightBox("div_ok"),
	//表单检测错误数量
	errorTypes = ['bankid','province','branchAddr','bankAccount','bankNumber','bankNumber2','securityPassword'],
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
	//公用方法提示文字控制
	var staticPrompt=function(objPar,obj){	
		objPar.find('.ui-check').css('display', 'inline');	
		objPar.find('.ui-prompt').css('display', 'none');
		setErrorNum(obj, 1);		
	}		
	var staticPromptDe=function(objPar,obj){			
		objPar.find('.ui-check').css('display', 'none');
		setErrorNum(obj, -1);	
	}	
	//集焦样式提示
	var foucePrompte=function(obj){
		obj.find('.ui-check').css('display', 'none');
		obj.find('.ui-prompt').css('display', 'inline');
	}
		
	//开户银行验证	
	bankid = $('#bankid');
	bankidPar = bankid.parent();	
	bankid.blur(function(){
		var v = $.trim(this.value);			
		if(v == '0'  ){	staticPrompt(bankidPar,'bankid');}
		else{	
			staticPromptDe(bankidPar,'bankid');
		}			
	}).focus(function(){	foucePrompte(bankidPar);});
	//开户银行所在城市验证
	province = $('#province');
	provincePar = province.parent();	
	province.blur(function(){
		var v = $.trim(this.value);		
		if(v== '请选择省'  ){	staticPrompt(provincePar,'province');}
		else{	staticPromptDe(provincePar,'province');	}			
	}).focus(function(){	foucePrompte(provincePar);});
	//支行名称验证
	branchAddr = $('#branchAddr');
	branchAddrPar = branchAddr.parent();	
	branchAddr.blur(function(){
		var v = $.trim(this.value);		
		if(v== ''){		
			/*staticPrompt(branchAddrPar,'branchAddr');*/	
			branchAddrPar.find('.ui-check').css('display', 'inline');	
			setErrorNum("branchAddr", 1);	
		}else if(v.replace(/[^\u4E00-\u9FA5]/g,'')=='' || v.length < 4)
		{
			$(this).val("");
			branchAddrPar.find('.ui-check').css('display', 'inline');	
			branchAddrPar.find('.ui-check').html("<i class='error'></i>开户支行名称输入错误，请重新输入");
		    setErrorNum("branchAddr", 1);
		}
		else if(v.length > 30)
	    {
			branchAddrPar.find('.ui-check').css('display', 'inline');	
			branchAddrPar.find('.ui-check').html("<i class='error'></i>开户支行名称长度不能超过30字，请重新输入");
		    setErrorNum("branchAddr", 1);
		}
		else{	
			staticPromptDe(branchAddrPar,'branchAddr');		
		}			
	}).focus(function(){
		foucePrompte(branchAddrPar);
	});	
	//开户人验证
	bankAccount = $('#bankAccount');
	bankAccountPar = bankAccount.parent();	
	bankAccount.blur(function(){
		var v = $.trim(this.value);	
		if(v == ''){		
		    bankAccountPar.find('.ui-check').html("<i class='error'></i>请填写您的开户人名称");
			staticPrompt(bankAccountPar,'bankAccount');	
		}else if(v.length< 2)
		{
			bankAccountPar.find('.ui-check').html("<i class='error'></i>开户人名称长度有误，请重新输入");
			staticPrompt(bankAccountPar,'bankAccount');	
		}
		else if(!/^[\u4E00-\u9FFF]{2,15}$|^[a-zA-Z]{4,30}$|^[\u4E00-\u9FFF]+(·[\u4E00-\u9FFF]+)+$|^[a-zA-Z]+( [a-zA-Z]+)+$/.test(v))
		{
			//$(this).val("");
			bankAccountPar.find('.ui-check').css('display', 'inline');	
			bankAccountPar.find('.ui-check').html("<i class='error'></i>开户人只能输入4-30字符的中文或英文名称");
			//bankAccountPar.find('.ui-check').html("<i class='error'></i>开户人只能输入中文、英文名称");
		    setErrorNum('bankAccount', 1);
		}
		else{	
			staticPromptDe(bankAccountPar,'bankAccount');
		}			
	}).focus(function(){
		foucePrompte(bankAccountPar);
	});		
	//验证卡号,Luhm校验
	bankNumber = $('#bankNumber');
	bankNumberPar = bankNumber.parent();	
	bankNumber.blur(function(){
		var v = $.trim(this.value),isture=false;
		var re = /^\d{16}$|^\d{19}$|^\d{18}$/;
		if(v==''){
			bankNumberPar.find('.ui-check').html("<i class='error'></i>请输入您所需要绑定的银行卡号");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);
		}else if(!re.test(v)){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>银行卡号由16或18或19位数字组成");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);			
		}else if( !luhmCheck(v) ){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>您输入的卡号有误，请您仔细核对");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);
			//staticPrompt(bankNumberPar,'bankNumber');
		}
		else{		
			staticPromptDe(bankNumberPar,'bankNumber');
		}	
		
				
	}).focus(function(){
		foucePrompte(bankNumberPar);
	});
	//确认验证卡号,Luhm校验
	bankNumber2 = $('#bankNumber2');
	bankNumber2Par = bankNumber2.parent();	
	bankNumber2.blur(function(){
		var v = $.trim(this.value);		
		//var isture=true;
		if( v=='' || $.trim($('#bankNumber')[0].value) != $.trim($('#bankNumber2')[0].value)){	staticPrompt(bankNumber2Par,'bankNumber2');	}
		else{	staticPromptDe(bankNumber2Par,'bankNumber2'); }			
	}).focus(function(){	foucePrompte(bankNumber2Par);});
	//安全密码验证
	securityPassword = $('#securityPassword');
	securityPasswordPar = securityPassword.parent();	
	securityPassword.blur(function(){
		var v = $.trim(this.value);			
		if( v=='' || v.length < 6 || v.length > 20 ){	staticPrompt(securityPasswordPar,'securityPassword');}
		else{	staticPromptDe(securityPasswordPar,'securityPassword');	}			
	}).focus(function(){	foucePrompte(securityPasswordPar);});	
	//表单检测
	checkform = function(){
		var bankNum = $.trim($('#bankNumber').val());
		var re = /^\d{16}$|^\d{19}$|^\d{18}$/;
		if($.trim($('#bankid').val())=='0'){	staticPrompt(bankidPar,'bankid');}
		if($.trim($('#province')[0].value)=='请选择省')
		{	
		   staticPrompt(provincePar,'province');	
		}	
		if($.trim($('#branchAddr').val())=='' || $.trim($('#branchAddr').val()).length > 20)
		{	
		   branchAddrPar.find('.ui-check').css('display', 'inline');
		   //staticPrompt(branchAddrPar,'branchAddr');
		}	
		if($.trim($('#bankAccount').val())=='' )
		{	staticPrompt(bankAccountPar,'bankAccount');
		}			
		if(bankNum==''){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>请输入您所需要绑定的银行卡号");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
		    setErrorNum('bankNumber', 1);
		    //staticPrompt(bankNumberPar,'bankNumber');	
		}else if(!re.test(bankNum)){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>银行卡号由16或18或19位数字组成");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);			
		}else if( !luhmCheck(bankNum) ){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>您输入的卡号有误，请您仔细核对");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);
			staticPrompt(bankNumberPar,'bankNumber');
		}	
		else{		
			staticPromptDe(bankNumberPar,'bankNumber');
		}
			
		if($.trim($('#bankNumber2').val())=='' ||  $.trim($('#bankNumber')[0].value) != $.trim($('#bankNumber2')[0].value))
		{	
			staticPrompt(bankNumber2Par,'bankNumber2');	
		}		
		if($.trim($('#securityPassword').val())=='' || $.trim($('#securityPassword').val()).length < 6 || $.trim($('#securityPassword').val()).length > 20)
		{	
		    staticPrompt(securityPasswordPar,'securityPassword');	
		}				
	}	
	
	$("#securityPassword").keypress(function (e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {		
			Putsubmit();
		}
	});
	
	//表单提交校验
	$('#J-Submit').click(function(){	
		Putsubmit();
		//$("#J-form").submit();
	});	
	
	function Putsubmit(){
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};

		var err = 0;
		checkform();
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});	
		if(err > 0){			
			return false;
		}
		var datas="bankid="+$.trim($('#bankid').val())+"&province="+$.trim($('#province').val())+"&city="+$.trim($('#city').val())+"&branchAddr="+$.trim($('#branchAddr').val())+"&bankAccount="+$.trim($('#bankAccount').val())+"&securityPassword="+Base64.encode($.trim($('#securityPassword').val()))+"&bankNumber="+$.trim($('#bankNumber').val())+"&bindcardType=0";
		$.ajax({			
				type:'post',
				dataType:'json',
				cache:false,
				url:'/bindcard/bindcardadd',
				data:datas,
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					ShowTipDiv();
				},
				
				success:function(data){
					if(data['status']=="ok"){	
							$("#new_bankAccount").html(data['data']['bankAccount']);
							$("#new_bankName").html(data['data']['bankaddr']);
							$("#new_bankCardId").html(data['data']['bankNumber']);
							box1.OverLay.Color = "rgb(51, 51, 51)" ; 
							box1.Over = true;   
							box1.OverLay.Opacity = 50;  
							box1.Fixed = true;	 
							box1.Center = true; 
							box1.Show();				
					}else{						
						box.OverLay.Color = "rgb(51, 51, 51)" ; 
						box.Over = true;   
						box.OverLay.Opacity = 50;  
						box.Fixed = true;	 
						box.Center = true; 
						box.Show();	
						$('#errortype').html(data['errorkey']);
					}
				},
				complete:function()	{					
					HideTipDiv();
				}
				
			});
	}	
	
	$(document).on('click', '#CloseDiv', function(){		
		box.Close();
		location='/bindcard/';
	});	
	$(document).on('click', '#CloseDiv2', function(){		
		box1.Close();
		location='/bindcard/';
	});	
	
	$(document).on('click', '#reType', function(){	
		box.Close();
		$('#securityPassword').val("");
		$('#securityPassword').focus();
	});	
	
})();
</script>	
{/literal}	
</body>
</html>
