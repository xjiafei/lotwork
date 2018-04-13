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
		<form action="/fund/confirm" method="post" id="J-form">
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要充值</div>
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
					{include file='/default/ucenter/fund/recharge_index_title.tpl'}
				</div>
				<div class="content bank-select">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td {if $stp eq 0}class="current"{/if}><div class="con">1.选择银行并填写金额</div></td>
									<td {if $stp eq 1}class="current"{/if}><div class="tri"><div class="con">2.确认充值信息</div></div></td>
									<td {if $stp eq 2}class="current"{/if}><div class="tri"><div class="con">3.登录网上银行汇款</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<ul class="ui-form bank-select-from">
						<li class="bank-select-from-li">
							<label class="ui-label">充值银行：</label>
							<div class="bank-more">
								<div class="bank-label">
									
									<span class="bank-drop-text"  id="selectBank">- 请选择银行 -</span>
									<a class="bank-drop" href="javascript:void(0);" ><i class="ico-down" id="icoName"></i></a>
                                   
								</div>
                                 <div class="ui-check" id="selectBankEr"><i class="error"></i>请选择银行</div>
								<div class="bank-more-content" style="display:none;padding:0px;width:560px;">
									<div class="bank-list" id="banklistinfo">
                                   		<input type="hidden" name="status" id="bankselect" value="" /> 
                                   		<input type="hidden" name="type" value="0" />
                                   			
                                   			<div style="position:relative;padding-left:33px;padding-top:14px;height:58px;vertical-align:middle;">
                                   				<div style="position:absolute;top:0px;left:0px;"><img src="{$path_img}/images/funds/soon.gif"></div>
		                                   		{foreach from=$aBankCardList item=data}
		                                   			{if $data.logo == 'ICBC' || $data.logo == 'CMB' || $data.logo == 'CCB'}
			                                   			{if $vipLvl =='vip'}
			                                   				<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.vipUpLimit}" min="{$data.vipLowLimit}"  bankname="{$data['name']}"  title="{$data['name']}充值限额：最低{$data.vipLowLimit},最高{$data.vipUpLimit}"></span>
			                                   			{else}
															<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}" bankname="{$data['name']}"  title="{$data['name']}充值限额：最低{$data.lowLimit},最高{$data.upLimit}"></span>
														{/if}
													{/if}
												{/foreach}
												<div style="clear:both;"></div>
                                   			</div>
                                   			
                                   			<div style="float: left;width:100%;"><hr style="width:100%"></div>
                                   			
                                   			<div style="position:relative;padding-left:33px;">
		                                   		{foreach from=$aBankCardList item=data}
		                                   			{if $data.logo != 'ICBC' && $data.logo != 'CMB' && $data.logo != 'CCB'}
			                                   			{if $vipLvl =='vip'}
			                                   				<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.vipUpLimit}" min="{$data.vipLowLimit}"  bankname="{$data['name']}"  title="{$data['name']}充值限额：最低{$data.vipLowLimit},最高{$data.vipUpLimit}"></span>
			                                   			{else}
															<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}" bankname="{$data['name']}"  title="{$data['name']}充值限额：最低{$data.lowLimit},最高{$data.upLimit}"></span>
														{/if}
													{/if}
												{/foreach}
												<div style="clear:both;"></div>
                                   			</div>
									</div>
								</div>
							</div>
						</li>
						  <li class="ui-text">
							<div class="prompt" ><div name="initialMoney">充值金额：单笔最低充值金额为<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元<br /></div>充值时限：请在{if $iCountDown neq ''}{$iCountDown}{else}30{/if}分钟内完成充值</div>
						</li>
						<li >
							<label class="ui-label">充值金额：</label>
							<input type="text" class="input w-4" value="" disabled="disabled" id="chargeamount" name="chargeamount"  autocomplete="off" > 
                            <span class="ui-text-info">元</span>		
							<i class="check-right"></i>
                            <div name="initialMoney">			
                                <span class="ui-prompt"><div  id="div_pro" style="display:none">充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</div></span>									
                                <span class="ui-check"><i class="error"></i>充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</span>
                             </div>	
						</li>
                         <li class="ui-text" style="margin-top:-20px;">
							<span class="ui-singleline" id="chineseMoney"></span>
						</li>					
						<li class="ui-btn"><a href="javascript:void(0);" class="btn btn-important" id="J-Submit">下一步<b class="btn-inner"></b></a><span id="wait_msg" style="display:none;color:red;position:relative;top:10px;">　请稍等，正在获取信息……</span></li>
					</ul>
					<!-- {if $isSetRemark neq 1} -->
					<div style="padding:20px;color:#999;">
						注：您还在为每次充值填写不一样的附言而烦恼吗？  <a href="/remark">立即设置唯一附言</a>，从此只需记一个专属您自己的附言，充值更方便。
					</div>
					<!-- {/if} -->
					<input type="hidden" id="isCharged" value="0"/>
				</div>              
			</div>
		</div>
	</form>
	</div> 

<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>



<script type="text/tpl" id="J-template-checkstatus">
<div>
	<p class="wd-title">对不起，您尚有一笔充值申请未完成，请完成后再发起</p>
	<div>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
		  <tr>
			<td align="right" width="130" class="wd-td-bold">充值银行：</td>
			<td><#=payBankName#><span style="margin-left:80px;"><a target="_blank" href="<#=bankUrl#>">登录网银</a></span></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">充值金额：</td>
			<td style="color:red"><#=chargeAmt#> 元</td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">收款银行：</td>
			<td style="color:red"><#=userBankName#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">收款账户名：</td>
			<td style="color:red"><#=rcvAccountName#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold"><#=rcvName#>：</td>
			<td style="color:red"><#=rcvMail#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">附言：</td>
			<td style="color:red"><#=remark#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">开户行名称：</td>
			<td style="color:red"><#=rcvBankName#></td>
		  </tr>
	  </table>
		<p class="wd-control-panel">
			您还可以 &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btn btn-important" id="J-button-order-cancel" prevalue="<#=id#>" value="撤销申请">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/fund/history/" target="_blank">充值记录</a>
		</p>
		<p class="wd-tip">
			* 如您已完成付款，请勿撤销，我们将尽快为您处理。
		</p>
	</div>
</div>
</script>
<script type="text/tpl" id="J-template-cancel-confirm">
<div>
	<p class="wd-title" style="text-align:center;">您的充值申请已撤销成功！</p>
		<p class="wd-control-panel">
			<a href="/fund/" class="btn btn-important closeBtn">继续充值<b class="btn-inner"></b></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/fund/history/" target="_blank">充值记录</a>
		</p>
</div>
</script>
<script>
var enableRecharge = {$enableRecharge};
</script>




{literal}
<script>

(function(){
	
	var minWindow = new phoenix.MiniWindow({cls:'ui-wd-funds-expired'}),
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
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
	var checkOrderStatus = function(){
		if($("#isCharged").val() == '1'){
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#J-template-checkstatus').val());
			minWindow.show();
			cancleApply($('#J-button-order-cancel').attr('prevalue'));
			return true;
		} else if($("#isCharged").val() == '2'){
			return false;
		}
		$.ajax({
			url:'/fund/checkchargesuccess/',
			dataType:'json',
			data:"type=1",
			async:false,
			success:function(data){
				//var data = {isSuccess:1, type:'', msg:'请求成功', data:{bankFrom:'充值银行名称', money:'充值金额', bankTo:'收款银行', name:'收款人', email:'收款Email地址', message:'附言', bankOpen:'开户行名称'}};
				if(Number(data['isSuccess']) == 1){
					var str = $('#J-template-checkstatus').html(),
						reg,
						p;
					for(p in data['data']){
						if(p == null){
							p = '';
						}
						reg = RegExp('<#=' + p + '#>', 'g');
						str = str.replace(reg, data['data'][p]);
					}
					$("#isCharged").val('1');
					$('#J-template-checkstatus').val(str);
					minWindow.setTitle('温馨提示');
					minWindow.setContent(str);
					minWindow.show();
					cancleApply($('#J-button-order-cancel').attr('prevalue'));
					return true;
				}else{
					$("#isCharged").val('2')
					return false;
				}
			}
		});
	};
	
	//取消充值申请
	var cancleApply = function(id){
		$('#J-button-order-cancel').click(function(e){
			e.preventDefault();
			$.ajax({
				url:'/fund/cancleorder/',
				dataType:'json',
				cache:false,
				data:"id="+id,
				beforeSend:function(){
					//禁用发送								
					var button = $('#J-button-order-cancel'),list = ['撤销中   ', '撤销中.  ', '撤销中.. ', '撤销中...'],i = 0;
						interval=setInterval(function(){
							$('#J-button-order-cancel').val(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
					$("#J-button-order-cancel").attr("disabled","disabled");	
				},
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						minWindow.hide();
						minWindow.setContent($('#J-template-cancel-confirm').html());
						minWindow.show();
						$("#isCharged").val('0');
					}else{
						alert(data['msg']);
					}
				},
				complete:function(){
					clearInterval(interval);
					$('#J-button-order-cancel').val("撤销申请");						 	
					$("#J-button-order-cancel").removeAttr("disabled","disabled");
				}
			});
		});
	}
	
	var chargeamount,chargeamountPar,minmoney=0,maxmoney=0,form = $('#J-form');	
	errorTypes = ['chargeamount'],
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
	
	var getClolor=function(obj){
			var v=obj.value;
			if(v.indexOf(".")==-1)
			{
				
				if(v.length == 5)
				{
					$(obj).attr("style","color:green");
				}else if(v.length == 6)
				{
					$(obj).attr("style","color:red");
				}else if(v.length >= 7)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}else if(v.indexOf(".")>0)
			{
				var vlist=v.split(".");
				if(vlist[0].length == 5)
				{
					$(obj).attr("style","color:green");
				}else if(vlist[0].length == 6)
				{
					$(obj).attr("style","color:red");
				}else if(vlist[0].length >= 7)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}
	    }
	
	 var cookicecodeB=$.cookie("selectbank"),cookicecode;	
	 if(cookicecodeB!=null){ 
	 	 var cookicecode= phoenix.util.decodeBase64(cookicecodeB);
		 $("#banklistinfo > span").each(function(){
			if($(this).attr("name")==cookicecode){
				 minmoney=$.trim($(this).attr("min"));				
				 maxmoney=$.trim($(this).attr("max"));
				 return;
			}			
		 }); 
		 
		 //当后台禁用此银行，而上一笔又用此银行时，不保存此银行信息,清空cookice
		 if(maxmoney !=0){
			 $('#selectBank').removeAttr("style").removeAttr("class").addClass("ico-bank "+cookicecode).attr("title",cookicecode).html('');		
			 $("#bankselect")[0].value=cookicecode;
			 $('[name="initialMoney"],#div_pro').css("display","inline");
			 $('[name="txtminmoney"]').html(minmoney);
			 $('[name="txtmaxmoney"]').html(maxmoney);	
			 $("#chargeamount").removeAttr("disabled");
		 }else{
			 $.cookie("selectbank", '', { expires: 7 }); 
			 $('[name="initialMoney"]').css("display","none");	
		}
	 }else{	  $('[name="initialMoney"]').css("display","none");	}	
	 	
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
	var checkRechargeStatus = function(){
		if(enableRecharge ==0){
			minWindow.setTitle('温馨提示');
			minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">您帐号充值功能已被冻结，请联系您的上级代理或平台客服！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
			minWindow.show();
			return false;
		}
		return true;
	};
	//充值金额验证
	chargeamount = $('#chargeamount');
	chargeamountPar = chargeamount.parent();	
	chargeamount.blur(function(){
		var v = $.trim(this.value);
		/*if($("#bankselect")[0].value==''){				
			$('#chargeamount').val('');	
		}	*/
		checkFn2(this);
		checkform();
		this.value=formatMoney(v);
		if(checkOrderStatus()){
			return false;
		}
	}).focus(function(){	
		if($("#bankselect")[0].value==''){				
			$('#selectBankEr').css('display', 'inline');	
		}	
		chargeamountPar.find('.ui-check').css('display', 'none');
		chargeamountPar.find('.ui-prompt').css('display', 'inline');
	}).keyup(function(){
		var v=this.value;
		var maxmoney=$($("[name='txtmaxmoney']")[0]).text();
        maxmoney = parseInt(maxmoney.replace(',','')); // 拿掉数字FORMAT 才能作比较
		//var minmoney=$($("[name='txtminmoney']")[0]).text();
		if(Number(v)>maxmoney)
		{
			$(this).val(maxmoney);
		}
		checkFn2(this);
	    checkform();		
        $("#chineseMoney").html(changeMoneyToChinese(this.value));
	});
	
	
	$('.bank-more-content').css("display","none");
	$('.bank-more').css("min-height","45px");		
	$('#div_pro').css("display","inline");
	
	//数字校验，自动矫正不符合数学规范的数学(小数两位)
	var inputs =  $('#chargeamount'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
			if(v.substring(index+1,v.length).length>2){				
				me.value= v  = v.substring(0, v.indexOf(".") + 3);
			}
		}	
	    getClolor(this);
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');				
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
		
	
	//当没有绑定银行卡时显示字，请选择银行
	$('#selectBank').css("display","inline-block").css("width","131px").css("text-align","center");		
	$("#banklistinfo span").mousemove(function(){
		var me=$(this);
		var title=me.attr("bankname")+"充值限额：最低"+formatMoney(me.attr("min"))+",最高"+formatMoney(me.attr("max"));
		$(this).attr("title",title);
	});
	
	//储蓄卡充值
	$("#banklistinfo span").click(function () {
		if(checkOrderStatus()){
			return false;
		}
		$("#banklistinfo span").removeClass("ico-bank-current");
		var txtstatus =$.trim($(this).attr("name"));	
		minmoney =formatMoney($.trim($(this).attr("min")));
		maxmoney =formatMoney($.trim($(this).attr("max")));		
		$(this).addClass("ico-bank ico-bank-current "+txtstatus);		
		$("#bankselect").attr("value", txtstatus);		
		//当有绑定银行,显示相应银行图片(动态绑定)
		$('#selectBank').removeAttr("style").removeAttr("class").addClass("ico-bank "+txtstatus).attr("title",txtstatus).html('');	
		$('.bank-more-content').css("display","none");
		$('.bank-more').css("min-height","45px");		
		$('#div_pro,[name="initialMoney"]').css("display","inline");
		$('#icoName').removeClass('ico-up').addClass('ico-down');
		$('[name="txtminmoney"]').html(minmoney);
		$('[name="txtmaxmoney"]').html(maxmoney);
		$("#chargeamount").removeAttr("disabled");
		if($("#bankselect")[0].value!=''){				
			$('#selectBankEr').css('display', 'none');
		}	
	});		
		
	$('#J-submit1').click(function(){
		$('#otherbank').css("display","inline");
		$('#J-submit1').css("display","none");
	});	
	
	$('#J-submit2').click(function(){
		$('#otherbank').css("display","none");
		$('#J-submit1').css("display","inline");			
	});		
		
	//银行卡展示与隐藏
	$('.bank-drop').click(function(){
		var classcss = $('#icoName').attr("class");		
		if(classcss=='ico-down'){
			$('#icoName').removeClass('ico-down').addClass('ico-up');			
			$('.bank-more-content').show();	
			$('#J-submit1').css("display","inline");
		}
		else{
			$('#icoName').removeClass('ico-up').addClass('ico-down');
			$('.bank-more').css("min-height","45px");
			$('.bank-more-content').css("display","none");
		} 		
	});	
	//表单检测
	checkform = function(){			
		if($.trim($('#chargeamount')[0].value) != "")
		{
			if((minmoney ==0 && maxmoney ==0) || $('#chargeamount')[0].value == '' || parseFloat($('#chargeamount')[0].value) > maxmoney || parseFloat($('#chargeamount')[0].value) < minmoney){
				chargeamountPar.find('.ui-check').css('display', 'inline');	
				chargeamountPar.find('.ui-prompt').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'none');
				setErrorNum('chargeamount', 1);		
			}	
			else{
				chargeamountPar.find('.ui-check').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'inline-block');
				setErrorNum('chargeamount', -1);
			}	
		}	
		else
		{
			chargeamountPar.find('.ui-check').css('display', 'inline');	
			chargeamountPar.find('.ui-prompt').css('display', 'none');
			chargeamountPar.parent().find('.check-right').css('display', 'none');
			setErrorNum('chargeamount', 1);	
		}
	}		
	 
	//表单提交校验
	$('#J-Submit').click(function(){
		form.submit();	
	});			
	
	form.submit(function(e){			
		var err = 0;	
		checkform();	
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});
		
		if(err > 0){ 
			e.preventDefault(); 
			return false;	
		}	
		else if($("#bankselect")[0].value==''){
			alert("请选择银行");
			return false;
		}
		else{
			if(!checkRechargeStatus()){
				return false;
			}
			if(checkOrderStatus()){
				return false;
			}
			if($("#isCharged").val()=='1'){
				return false;
			}
			
			//所有验证通过后保存当前选中银行及大小金额放入
			var base64Bank= new phoenix.util.Base64Method();
			var bankSelect=base64Bank.encode($.trim($("#bankselect")[0].value));    			
			$.cookie("selectbank", bankSelect, { expires: 7 }); 
			$('#wait_msg').show();
			return true;
		}
	});
	checkRechargeStatus();
})();
</script>	
{/literal}	
</body>
</html>