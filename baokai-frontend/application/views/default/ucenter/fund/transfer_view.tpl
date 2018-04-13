<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
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
		
		
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要转账</div>

				<div class="">
					<ul class="ui-tab-title clearfix" style="display:none"  id='_showTag'>
						<li class="current">账间转账</li>						
						<li ><a href="{$ptgame_server}/pt/transfer">资金转移</a></li>
					</ul>
					<div class="notice" id="notice_up" style="display:none;"><i class="ico-warning"></i>
					为了保证您的资金安全，您目前的向上级转账限额为{$aValibal.up_transfer}元/次;每天的次数上限为{$aValibal.up_time}次。
					</div>
					<div class="notice" id="notice_down" style="display:none;"><i class="ico-warning"></i>
					为了保证您的资金安全，您目前的向下级转账限额为{$aValibal.down_transfer}元/次;每天的次数上限为{$aValibal.down_time}次。
					</div>
					<span id="isTransferUp" style="display:none;">{$aValibal.isTransferUp}</span>
					<span id="isTransferDown" style="display:none;">{$aValibal.isTransferDown}</span>
					<ul class="ui-form">
						<li>
							<label class="ui-label">可用余额：</label>
							<span class="ui-singleline">
								<strong><span id="avbalance" style="display:none;">{$aValibal.availBal_format}</span><span>{$aValibal.availBal_format}</span>元</strong>
							</span>
						</li>
						<li>
                            <label class="ui-label">转账对象：</label>
                            
                            {if $userLvl eq '0'}
                                <label class="label" for=""><input type="radio" checked='checked' name="tranto" class="radio" value="0">给下级</label>
                            {else if $userLvl eq '-1' or $aValibal.down_lvls eq '0'} 
                                <label><input name="tranto" checked='checked' value="1" type="radio" > 给上级</label>
                            {else}             
                                <label><input name="tranto" value="0" type="radio" checked='checked' > 给下级</label>&nbsp; &nbsp; &nbsp;             
                                <label><input name="tranto" value="1" type="radio" > 给上级</label>                            
                            {/if}   
                        </li>          
                        <form action="/transfer/transferconform" method="post" id="J-form1">                        	
                            <div id="div_username">    
                            	<input type="hidden"  name="tranto"  value="0" />            	
                                <li>                               
                                    <label for="name" class="ui-label">收款用户名：</label>
                                    <input type="text" value="{$uname}" name="accountName" id="accountName" class="input w-4">
                                    <i class="check-right"></i>
							        <i class="check-loading"></i>                               
                                    <span class="ui-check"><i></i></span>  
                                     
                                    {if $userLvl eq '0'}
                                    <div class="ui-text-prompt">请输入您的下级用户名</div>
                                    {else if $userLvl eq '-1'} 
                                    <div class="ui-text-prompt">请输入您的下级用户名</div>
                                    {else}  
                                    <div class="ui-text-prompt">请输入您的下级用户名</div>
                                    {/if}
                                </li>
                                <li>
                                    <label for="name" class="ui-label">转账金额：</label>
                                    <input type="text" value="" name="changeCount" id="changeCount1" class="input w-4">
                                    <span class="ui-text-info">元</span>
                                    <i class="check-right"></i>
							        <i class="check-loading"></i> 
                                    <!-- <div class="ui-text-prompt"><span class="ui-text-prompt">请输入您的转账金额</span></div> -->
                                    <span id="subalance1" style="display:none;">{$aValibal.down_avaliTransfer}</span>
                                    <span class="ui-text-prompt">本次最多转出 <strong >{$aValibal.down_avaliTransfer_format}</strong> 元，今天还可以转出 <strong>{$aValibal.down_avaliTransferTime}</strong> 次</span>
                                    <div class="ui-check"><i class="error"></i><span >请输入您的转账金额</span></div>
                                </li>
                                <li class="ui-text">
									<span class="ui-singleline" id="chineseMoney"></span>
								</li>
                                <li class="ui-btn">
                                <input type="button" class="btn btn-important" id="J-Submit1" value="提 交" /><b class="btn-inner"></b></li>
                            </div>
                        </form>
                         <form action="/transfer/transferconform" method="post" id="J-form2">
                         	<input type="hidden"  name="tranto"  value="1" />  
                         	  <div id="div_username2" style="display:none">    
                                              
                                <li>
                                    <label for="name" class="ui-label">转账金额：</label>
                                    <input type="text" value="" name="changeCount" id="changeCount2" class="input w-4">
                                    <span class="ui-text-info">元</span>
                                    <i class="check-right"></i>
							        <i class="check-loading"></i> 
                                    <!-- <div class="ui-text-prompt"><span class="ui-text-prompt">请输入您的转账金额</span></div> -->
                                    <span id="subalance2" style="display:none;">{$aValibal.up_avaliTransfer}</span>
                                    <span class="ui-text-prompt">本次最多转出 <strong>{$aValibal.up_avaliTransfer_format}</strong> 元，今天还可以转出 <strong>{$aValibal.up_avaliTransferTime}</strong> 次</span>
                                    <div class="ui-check"><i class="error"></i><span >请输入您的转账金额</span></div>
                                </li>
                                 <li class="ui-text">
									<span class="ui-singleline" id="chineseMoney2"></span>
								</li>
                                <li class="ui-btn"><input type="button" class="btn btn-important" id="J-Submit2" value="提 交" /><b class="btn-inner"></b></li>
                            </div>
                        </form>				
						
					</ul>
				</div>
			</div>
		</div>
		
	</form>
	</div> 
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script>
var enableTransfer = {$enableTransfer};
</script>
{literal}
<script>
(function(){
	var minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),msg = new phoenix.Message(),
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	
	jQuery.ajax({
		type:  "get",
		url: '/pt/index/checkptusers',
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: '',
		cache: false,
		success:function(data){
			if(data.status==1){
				$('#_showTag').show();;
				
			}
		},
		error: function(er){
			console.log(er);
		}
	});
	
	var tranto=$("input[name='tranto']:checked").val();
	if(tranto=='0'){
		$('#notice_down').show();
		$('#notice_up').hide();
		$('#div_username').css("display","inline");
		$('#div_username2').css("display","none");
	}else{
		$('#notice_up').show();
		$('#notice_down').hide();
		$('#div_username').css("display","none");
		$('#div_username2').css("display","inline");
	}
	var formatMoney = function(num){
			if(num == ''){
				num = '0';
			}
			var num = num.replace(/,/g, ''),
				num = parseFloat(num),
				re = /(-?\d+)(\d{3})/;
		
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + num;
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;  
		};
	var getClolor=function(obj){
			var v=obj.value;
			if(v.indexOf(".")==-1)
			{
				
				if(v.length == 4)
				{
					$(obj).attr("style","color:green");
				}else if(v.length == 5)
				{
					$(obj).attr("style","color:red");
				}else if(v.length >= 6)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}else if(v.indexOf(".")>0)
			{
				var vlist=v.split(".");
				if(vlist[0].length == 4)
				{
					$(obj).attr("style","color:green");
				}else if(vlist[0].length == 5)
				{
					$(obj).attr("style","color:red");
				}else if(vlist[0].length >= 6)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}
	    };
    var checkTransferStatus = function(){
		if(enableTransfer ==0){
			minWindow.setTitle('温馨提示');
			minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">您帐号转账功能已被冻结，请联系您的上级代理或平台客服！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
			minWindow.show();
			return false;
		} 
		return true;
	};
	var getformatMoney=function(v){
		var index;
		v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			v = v.replace(/(.+\..*)(\.)/g, '$1');	
			if(v.substring(index,v.length).length>3){					
				v=v.substring(0,v.indexOf(".")+3);					
			}			
		}
		v = v.replace(/[^\d|^\.]/g, '');
		v = v.replace(/^00/g, '0');					
		return v;
	};
	//数字校验，自动矫正不符合数学规范的数学(小数点保留两位)
	var inputs = $('#changeCount'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');	
			if(v.substring(index,v.length).length>3){					
				me.value = v=v.substring(0,v.indexOf(".")+3);					
			}			
		}
		
		getClolor(this);
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}					
	};		
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	
	//上下级选择加载DIV
	$('[name="tranto"]').change(function(){		
		if(this.value=="0"){
			$('#notice_down').show();
			$('#notice_up').hide();
			$('#div_username').css("display","inline");
			$('#div_username2').css("display","none");
			//$('[name="tranto"]').attr("value","0").attr("checked","true");  
		}
		else {
			$('#notice_up').show();
			$('#notice_down').hide();
			$('#div_username').css("display","none");
			$('#div_username2').css("display","inline");
			//$('[name="tranto"]').attr("value","1").attr("checked","true");  
		}
	});

	//-----------------------------向下级转款开始-------------------------------
	var form1 = $('#J-form1'),accountName,accountNamePar,changeCount,changeCountPar1,	
	//表单检测错误数量
	errorTypes1 = ['accountName','changeCount1'],
	errorHas1 = {},
	setErrorNum1 = function(name, num){
		if(typeof errorHas1[name] != 'undefined'){
			errorHas1[name] += num;
			errorHas1[name] = errorHas1[name] < 0 ? 0 : (errorHas1[name] > 1 ? 1 : errorHas1[name]);
		}
	};
	$.each(errorTypes1, function(){
		errorHas1[this] = 0;
	});			
	//表单检测
	checkform = function(){		
		if($('#accountName')[0].value==''){
			accountNamePar.find('.ui-text-prompt').css('display', 'none');
			accountNamePar.find('.ui-check').html("<i></i>用户名不能为空");
			accountNamePar.find('.ui-check').css('display', 'inline');		
			accountNamePar.find('.check-right').css('display', 'none');			
			setErrorNum1('accountName', 1);	
		}
		else if(checkLenb($.trim($('#accountName')[0].value)) < 3 || 
		        checkLenb($.trim($('#accountName')[0].value)) > 16)
		{
			accountNamePar.find('.ui-text-prompt').css('display', 'none');
			accountNamePar.find('.ui-check').html("<i></i>用户名字符长度3-16位");
			accountNamePar.find('.ui-check').css('display', 'inline');	
			accountNamePar.find('.check-right').css('display', 'none');				
			setErrorNum1('accountName', 1);	
		}
		if($.trim($('#changeCount1')[0].value)=='')
		{
			changeCountPar.find('.ui-check').css('display', 'inline');	
			changeCountPar.find('.ui-text-prompt').css('display', 'none');
			setErrorNum1('changeCount1', 1);
		}			
	}		
	
    var checkAccountName=function(username){
	    var reg = /^(\w|[\u4E00-\u9FA5])*$/;
		if(username.match(reg))
		{
		   return true;
		}
		else
		{
		   return false;
		}
	}
	//返回字节数
	var checkLenb = function(username) {
        return username.replace(/[^\x00-\xff]/g,"**").length;
    }
	//用户名验,判断是否已存在
	accountName = $('#accountName');
	accountNamePar = accountName.parent();
	
	if($('#accountName').val() !=''){
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/transfer/checkuser',			
			data:'accountName='+$('#accountName').val(),	
			beforeSend:function(){
				//提交按扭禁用
				$('#J-Submit').attr('disabled',true);
				$('#J-Submit').css("color","#ccc");//white	
				accountNamePar.find('.check-loading').css('display', 'inline-block');	
				accountNamePar.find('.check-right').css('display', 'none');				
			},					
			success:function(data){								
				if(data['status']=="ok"){							
					accountNamePar.find('.ui-check').css('display', 'none');	
					accountNamePar.find('.check-right').css('display', 'inline-block');								
					setErrorNum1('accountName', -1);	
				}
				else{
					accountNamePar.find('.ui-check').css('display', 'inline');
					accountNamePar.find('.ui-check').html("<i></i>无效的用户名，请重新输入");
					accountNamePar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('accountName', 1);								
				}
			},
			complete:function(){ 	
				$('#J-Submit').attr('disabled',false);
				$('#J-Submit').css("color","white");//white	
				accountNamePar.parent().find('.check-loading').css('display', 'none');
			}
		});
	}
	
	accountName.blur(function(){
		var v = $.trim(this.value);
        if(v == "")
		{
			accountNamePar.find('.ui-check').css('display', 'inline');
			accountNamePar.find('.ui-check').html("<i></i>用户名不能为空");
			accountNamePar.find('.ui-text-prompt').css('display', 'none');
			accountNamePar.find('.check-right').css('display', 'none');	
			setErrorNum1('accountName', 1);
			return;
		}
		if(!checkAccountName(v))
		{
			accountNamePar.find('.ui-check').css('display', 'inline');
			accountNamePar.find('.ui-check').html("<i></i>用户名支持中文、字母、數字，不支持其他任何字符");
			accountNamePar.find('.ui-text-prompt').css('display', 'none');
			accountNamePar.find('.check-right').css('display', 'none');	
			setErrorNum1('accountName', 1);
			return;
			/*<i></i>用户名不存在,或不是你下级.*/
		}else if(checkLenb(v)< 3 || checkLenb(v)>16){	
			accountNamePar.find('.ui-check').css('display', 'inline');
			accountNamePar.find('.ui-check').html("<i></i>用户名字符长度3-16位");
			accountNamePar.find('.ui-text-prompt').css('display', 'none');
			accountNamePar.find('.check-right').css('display', 'none');	
			setErrorNum1('accountName', 1);
			return;
		}else
		{
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/transfer/checkuser',			
				data:'accountName='+v,	
				beforeSend:function(){	
					//提交按扭禁用
					$('#J-Submit').attr('disabled',true);
					$('#J-Submit').css("color","#ccc");//white	
					accountNamePar.find('.check-loading').css('display', 'inline-block');	
					accountNamePar.find('.check-right').css('display', 'none');				
				},					
				success:function(data){								
					if(data['status']=="ok"){							
						accountNamePar.find('.ui-check').css('display', 'none');	
						accountNamePar.find('.check-right').css('display', 'inline-block');								
						setErrorNum1('accountName', -1);	
					}
					else{
						accountNamePar.find('.ui-check').css('display', 'inline');
						accountNamePar.find('.ui-check').html("<i></i>无效的用户名，请重新输入");
						accountNamePar.find('.ui-text-prompt').css('display', 'none');
						setErrorNum1('accountName', 1);								
					}
				},
				complete:function(){ 	
					$('#J-Submit').attr('disabled',false);
					$('#J-Submit').css("color","white");//white	
					accountNamePar.parent().find('.check-loading').css('display', 'none');
				}
			});
		}	
	}).focus(function(){
		accountNamePar.find('.ui-check').css('display', 'none');
		accountNamePar.find('.ui-text-prompt').css('display', 'inline');
	});	
	
	
	var checkFn2 = function(obj){
			var me = obj,v = me.value,index;
			me.value = v = v.replace(/^\.$/g, '');		
			index = v.indexOf('.');
			if(index > 0){
				me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
				if(v.substring(index+1,v.length).length>2){				
					me.value= v  = v.substring(0, v.indexOf(".") + 3);
				}
			}else
			{
				me.value = v = v.replace(/^0/g, '');
			}		
			me.value = v = v.replace(/[^\d|^\.]/g, '');
			me.value = v = v.replace(/^00/g, '0');				
		};
	//金额验证
	changeCount = $('#changeCount1');
	changeCountPar = changeCount.parent();	
	changeCount.blur(function(){
		var v = $.trim(this.value.replace(/,/g, ''));
		if(v == ''){			
			changeCountPar.find('.ui-check').css('display', 'inline');	
			changeCountPar.find('.ui-text-prompt').css('display', 'none');
			setErrorNum1('changeCount1', 1);
		}
		else{				
			changeCountPar.find('.ui-check').css('display', 'none');
			setErrorNum1('changeCount1', -1);
			checkFn2(this);
		}	
	  this.value=formatMoney(this.value)		
	}).focus(function(){
		changeCountPar.find('.ui-check').css('display', 'none');
		changeCountPar.find('.ui-text-prompt').css('display', 'inline');
	});
	var subalance1=getformatMoney($.trim($('#subalance1').text())),
		subalance2=getformatMoney($.trim($('#subalance2').text()));//获取当前可以余额，剩余转账额度
		
	//大小写转换及金额上限限定
	$('#changeCount1').bind('keyup', function(){
		var v=this.value.replace(/,/g, '');	
		//changeCountPar.find('.check-right').css('display', 'none');				
	 	if(parseFloat(v) <= parseFloat(subalance1)){
			checkWithdraw2(this,'chineseMoney',parseFloat(v),parseFloat(subalance1));
		}
		else{
			checkWithdraw2(this,'chineseMoney',parseFloat(v)||parseFloat(subalance1),subalance1);
		}		
		if(v.length>0 && v > 0)
		{
		  changeCountPar.find('.check-right').css('display', 'inline-block');	
		}else
		{
			changeCountPar.find('.check-right').css('display', 'none');
		}
		getClolor(this);
	});	
	
	//表单提交校验
	$('#J-Submit1').click(function(){	
		if($('#isTransferDown').html()!='1'){
			msg.show({
				content: '<h3 style="height:30px;line-height:30px;text-align:center; ">您向下级的可转账次数为0,请您明天再转账!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					msg.hide();
				}
			});
		} else {
			form1.submit();
		}
	});	
	
	//表单提交校验
	form1.submit(function(e){
		if(!checkTransferStatus()){
			return false;
		}
		var err = 0;
		checkform();
		$.each(errorTypes1, function(){
			if(typeof errorHas1[this] != 'undefined'){
				err += errorHas1[this];
			}
		});	
		if(err > 0 || changeCount[0].value.replace(/,/g, '') ==0){
			e.preventDefault();
			return false;
		}
		return true;
	});
	
	//-----------------------------向上级转款开始-------------------------------
	var form2 = $('#J-form2'),changeCount2,changeCount2Par,
	//表单检测错误数量
	errorTypes2 = ['changeCount2'],
	errorHas2 = {},
	setErrorNum2 = function(name, num){
		if(typeof errorHas2[name] != 'undefined'){
			errorHas2[name] += num;
			errorHas2[name] = errorHas2[name] < 0 ? 0 : (errorHas2[name] > 1 ? 1 : errorHas2[name]);
		}
	};
	$.each(errorTypes2, function(){
		errorHas2[this] = 0;
	});			
	//表单检测
	checkform2 = function(){
		if($.trim($('#changeCount2')[0].value)=='')
		{
			changeCount2Par.find('.ui-check').css('display', 'inline');	
			changeCount2Par.find('.ui-text-prompt').css('display', 'none');
			setErrorNum2('changeCount2', 1);
		}			
	}	
	//金额验证
	changeCount2 = $('#changeCount2');
	changeCount2Par = changeCount2.parent();	
	changeCount2.blur(function(){
		var v = $.trim(this.value.replace(/,/g, ''));
		if(v == ''){			
			changeCount2Par.find('.ui-check').css('display', 'inline');	
			changeCount2Par.find('.ui-text-prompt').css('display', 'none');
			setErrorNum2('changeCount2', 1);
		}else{				
			changeCount2Par.find('.ui-check').css('display', 'none');
			setErrorNum2('changeCount2', -1);
			changeCount2Par.find('.check-right').css('display', 'inline-block');
			checkFn2(this);
		}			
		this.value=formatMoney(this.value);
	}).focus(function(){
		changeCount2Par.find('.ui-check').css('display', 'none');
		changeCount2Par.find('.ui-text-prompt').css('display', 'inline');
		changeCount2Par.find('.check-right').css('display', 'none');
	});
		
	//大小写转换及金额上限限定
	$('#changeCount2').bind('keyup', function(){
		var v=this.value.replace(/,/g, '');	
		if(parseFloat(v) <= parseFloat(subalance2)){
			checkWithdraw2(this,'chineseMoney2',parseFloat(v),parseFloat(subalance2));
		}
		else{
			checkWithdraw2(this,'chineseMoney2',parseFloat(v)||parseFloat(subalance2),subalance2);
		}		
		if(v.length>0 && parseInt(v) >0)
		{
		   changeCount2Par.find('.check-right').css('display', 'inline-block');	
		}else
		{
			changeCount2Par.find('.check-right').css('display', 'none');
		}
		getClolor(this);
	});	
	
	//表单提交校验
	$('#J-Submit2').click(function(){
		if($('#isTransferUp').html()!='1'){
			msg.show({
				content: '<h3 style="height:30px;line-height:30px;text-align:center; ">您向上级的可转账次数为0,请您明天再转账!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					msg.hide();
				}
			});
		} else {
			form2.submit();
		}
	});	
	
	//表单提交校验
	form2.submit(function(e){
		var err = 0;
		checkform2();
		$.each(errorTypes2, function(){
			if(typeof errorHas2[this] != 'undefined'){
				err += errorHas2[this];
			}
		});	
		if(err > 0|| changeCount2[0].value.replace(/,/g, '') ==0){//金额为0时不提交，
			e.preventDefault();
			return false;
		}
		return true;
	});
	checkTransferStatus();
})();
</script>	
{/literal}	
</body>
</html>