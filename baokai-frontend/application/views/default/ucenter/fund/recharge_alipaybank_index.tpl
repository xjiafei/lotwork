<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/tooltip.css" />
	
	{include file='/default/script-base.tpl'}
</head>
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
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab" >
					{include file='/default/ucenter/fund/recharge_index_title.tpl'}
				</div>
				<!--{foreach from=$bankInfo item=data} -->
				<!--{if $data.code ==30 && $data.deposit ==1 && $isAilOpen=='Y'} -->
				<!--{if $data.version ==0} -->
				<!-- --------------------------------------------------------------個人版--------------------------------------------------------------- -->
				<div class="content bank-select">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td {if $stp eq 0}class="current"{/if}><div class="con">1.选择银行并填写金额</div></td>
									<td {if $stp eq 1}class="current"{/if}><div class="tri"><div class="con">2.确认充值信息</div></div></td>
									<td {if $stp eq 2}class="current"{/if}><div class="tri"><div class="con">3.支付宝二维码支付</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
						<ul class="ui-form bank-select-from"  >
							<li class="bank-select-from-li" >
								<label class="ui-label" style="width:100px">充值银行：</label>
								<div class="bank-more">
									<div class="bank-label">
									<input type="hidden" name="type" value="4" />
										{foreach from=$aBankCardList item=data}
                                   		{if $vipLvl =='vip'}
                                   			<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.vipUpLimit}" min="{$data.vipLowLimit}"  title="{$data['name']}充值限额：最低{$data.vipLowLimit},最高{$data.vipUpLimit}"></span>
                                   		{else}
											<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}"  title="{$data['name']}充值限额：最低{$data.lowLimit},最高{$data.upLimit}"></span>
										{/if}
										<input type="hidden" name="status" id="bankselect" value="{$data.logo}" />
										{/foreach}
									</div>
								</div>
							</li>
							
							<li class="ui-text" style="margin-left: 110px">
								<div class="prompt" style="width: 190px" >充值时限：请在{if $iCountDown neq ''}{$iCountDown}{else}30{/if}分钟内完成充值</div>
							</li>
							
							<!-- if count($userBankStruc)>0 -->
					
							<li>
								<div class="ui-label" style="display: inline">您的支付宝账号：</div>
								<!-- <select class="ui-select w-5 bankHeight" id="selectBankNumber" name="selectBankNumber" value="">
								{foreach from=$userBankStruc item=data}
				                <option value="{$data.deBankNumber},{$data.deBankAccount},{$data.nickName}" {if {$lastChargeCard}=={$data.deBankNumber}} selected {/if}>{$data.nickName}&nbsp;{$data.bankAccount}&nbsp;{$data.bankNumber}</option>
				                {/foreach}
				           		</select> -->
				           		
				           		<input type="text" class="input w-4"  style="width: 212px !important"  value="{$lastChargeCard}" maxlength="35" name="bankNumber" id="bankNumber"  placeholder="手机/邮箱" autocomplete="off"/>
				           		<!--<a href='/bindcard/index?type=1'>支付宝管理</a>-->
								<ul id="ctmselect" class="customselect" style="display:none" >
								
								{$count =0}
								{foreach from=$userBankStruc item=data name=$key}
								
									<li id="li{$count}" style="overflow: hidden;">{$data.bankNumber}&nbsp;<p></p>{$data.bankAccount}&nbsp;
									
									<img  id="deleteBankNumber{$count}" onclick='clickDelete({$data.id},"li"+{$count})' 
										  style="float:right;height:10px;" src="{$path_img}/images/common/xx.png" onmouseover="this.style.cursor='pointer';" style="cursor:hand"/>
									</li>
	
								{$count =$count+1}
				                 {/foreach}
				              	
				                </ul>
				             		
								<img id='bankNumberQimg' src='{$path_img}/images/funds/ico-ps.png' style='vertical-align:middle;'  />
								<img id='bankNumDemo'  src='{$path_img}/images/funds/bankNum-demo.png' 
								style='vertical-align:middle;;display:none;position:absolute;z-index:3;top:0px;left:400px' />
								
								<a class='tooltip tip-text-color' >
								<div id='bankNumberQ' class='tooltiptext' style="width:348px;height:121px">
								<b>请填写正确账号：</b>
								<p>请您登录“支付宝手机应用”</p>
     							<p>如果您使用<span>【手机号】</span>登录，请输入手机号。</p>
       							<p>如果您使用<span>【邮箱地址】</span>登录，请输入完整邮箱地址。</p>
								</div>
								</a>
								<span class="ui-check"></span>
			           		</li>
							<li>
								<div for="name" style="display: inline" class="ui-label">您的支付宝昵称：</div>
								<input type="text" value='{$lastChargeAccount}' class="input w-4" style="width: 212px !important" name="bankAccount" id="bankAccount" autocomplete="off" placeholder="昵称/姓名" /> 
								<img  id='bankAccountQimg' src='{$path_img}/images/funds/ico-ps.png' style='vertical-align:middle;' />
								<img  id='bankAccountDemo' src='{$path_img}/images/funds/bankAccount-demo.png' 
								style='vertical-align:middle;display:none;z-index:3;position:absolute;top:0px;left:400px' />
								<a class='tooltip tip-text-color'>
								 <div id="bankAccountQ" class='tooltiptext'>
							       <b>如何确认您填入的【昵称】是否正确？</b>
							       <p style=>请您登录“支付宝手机应用”，</p>
							       <p>点击手机右下角进入<span>【我的】</span>，查看<span>【账户详情】</span>。</p>
							       <p>如您【昵称】栏，有显示个人昵称，请在平台填写正确昵称。</p>
							       <p>如您【昵称】栏，显示“未设置”，平台昵称一栏请输入真实姓名。</p>
							      </div>
								</a>
								<span class="ui-check"></span>
							</li>
							
							<!--
							<li class="ui-text" style="margin-left: 110px">
								 <div class="prompt">
								 <table>
								 <tr><td>注意事项：</td><td>1、 为了您的充值可以顺利到账，请选择您所需要使用的支付宝进行充值</td></tr>
								 <tr><td></td><td> 2、如您有修改过支付宝昵称，请更新平台支付宝绑定信息后进行充值</td></tr>
								 </table>
								</div>
								<div>
								<div>
							</li>
							-->
							
														
														
	
							<!--else -->
							<!--<li class="ui-text" style="margin-left: 110px">
								<div class="prompt" ><div name="initialMoney">充值条件：请在平台绑定支付宝账户后再进行充值<br /></div>
								充值时限：请在{if $iCountDown neq ''}{$iCountDown}{else}30{/if}分钟内完成充值</div>
							</li>
							 <li>
								<div class="ui-label" style="display: inline" for="name" >您的支付宝账户：</div>
								<input type="text" value='{$bankNumber}' class="input w-4" maxlength="35" name="bankNumber" id="bankNumber" autocomplete="off" placeholder="手机/邮箱">
	                           	<span class="ui-check"></span>
							</li>
							<li>
								<div for="name" style="display: inline" class="ui-label">您的支付宝姓名：</div>
								<input type="text" value='{$bankAccount}' class="input w-4" maxlength="30" name="bankAccount" id="bankAccount" autocomplete="off">
								<span class="ui-check"></span>
							</li>
							<li>
								<div for="name" style="display: inline" class="ui-label">您的支付宝昵称：</div>
								<input type="text" value='{$nickName}' class="input w-4" maxlength="30" name="nickName" id="nickName" autocomplete="off">
								<span class="ui-check"></span>
							</li>
							
							
							
							<!--/if-->
							<li>
							<label class="ui-label" style="width:100px"></label>
							<span class="ui-check-tip2">为了您充值能顺利到账，确保平台填写支付宝信息与您支付宝绑定信息一致。</span>
							
							</li>
							<li >
								<label class="ui-label" style="width:100px">充值金额：</label>
								<input type="text" class="input w-4" value="" id="chargeamount" name="chargeamount"  autocomplete="off"> 
	                            <span class="ui-text-info">元</span>		
								<i class="check-right"></i>
								<div name="initialMoney">			
                                <span class="ui-prompt"><div  id="div_pro" style="display:none">充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</div></span>									
                                <span class="ui-check"><i class="error"></i>充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</span>
                             </div>	
							</li>
							<li class="ui-text" style="margin-left: 110px">
								<span class="ui-singleline" style="display:none;" id="chineseMoney"></span>
							</li>	
						
							<li class="ui-btn" style="margin-left: 110px"><a href="javascript:void(0);" id="J-next-step" class="btn btn-disable">下一步<b class="btn-inner"></b></a><span id="wait_msg" style="display:none;color:red;position:relative;top:10px;">　请稍等，正在获取信息……</span>
							<span class="ui-check"></span>
							</li>
							
							
							
						</ul>
				</div>
				<input type="hidden" id="alipayDepositMode" value="6">
				<input type="hidden" id="chargeVersion" name="chargeVersion" value="0" />
				<!-- --------------------------------------------------------------個人版--------------------------------------------------------------- -->
				<!--{/if}-->							
				<!--{if $data.version ==1}-->	
				<!-- --------------------------------------------------------------企業版--------------------------------------------------------------- -->
				<div class="content bank-select">
					<div class="step">
						<table class="step-table">
							<tbody>
									<tr>
										<td {if $stp eq 0}class="current"{/if}><div class="con">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp1.填写金额</div></td>
										<td {if $stp eq 1}class="current"{/if}><div class="tri"><div class="con">2.确认充值信息</div></div></td>
										<td {if $stp eq 2}class="current"{/if}><div class="tri"><div class="con">3.登陆支付宝付款</div></div></td>
									</tr>
								
							</tbody>
						</table>
					</div>
						<ul class="ui-form bank-select-from"  >
							<li class="bank-select-from-li" >
								

								<label class="ui-label" style="width:100px">银行种类：</label>
								<div class="bank-more">
									<div class="bank-label">
									<input type="hidden" name="type" value="4" />
										{foreach from=$aBankCardList item=data}
                                   		{if $vipLvl =='vip'}
                                   			<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.vipUpLimit}" min="{$data.vipLowLimit}"  title="{$data['name']}充值限额：最低{$data.vipLowLimit},最高{$data.vipUpLimit}"></span>
                                   		{else}
											<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}"  title="{$data['name']}充值限额：最低{$data.lowLimit},最高{$data.upLimit}"></span>
										{/if}
										<input type="hidden" name="status" id="bankselect" value="{$data.logo}" />
										{/foreach}
									</div>
								</div>
							</li>
								<li class="ui-text" style="margin-left: 110px">
									<div class="prompt"  >充值金额：单笔最低充值金额为<span id='lowLimit'></span>元，最高<span id='upLimit'></span>元<br/>
									充值时限：请在{if $iCountDown neq ''}{$iCountDown}{else}30{/if}分钟内完成充值</div>
								</li>
							
							
							<!-- if count($userBankStruc)>0 -->
					
							<li>			           		
				           		<input type="hidden" class="input w-4"  style="width: 212px !important" maxlength="35" name="bankNumber" id="bankNumber"  placeholder="手机/邮箱" autocomplete="off"/>
				           		<!--<a href='/bindcard/index?type=1'>支付宝管理</a>-->
								<ul id="ctmselect" class="customselect" style="display:none" >
								
								{$count =0}
								{foreach from=$userBankStruc item=data1 name=$key}
								
									<li id="li{$count}" style="overflow: hidden;">{$data1.bankNumber}&nbsp;<p></p>{$data1.bankAccount}&nbsp;
									
									<img  id="deleteBankNumber{$count}" onclick='clickDelete({$data1.id},"li"+{$count})' 
										  style="float:right;height:10px;" src="{$path_img}/images/common/xx.png" onmouseover="this.style.cursor='pointer';" style="cursor:hand"/>
									</li>
	
								{$count =$count+1}
				                 {/foreach}
				              	
				                </ul>

			           		</li>

								<li >
									<label class="ui-label" style="width:100px">充值金额：</label>
									<input type="text" class="input w-4" value="" id="chargeamount" name="chargeamount"  autocomplete="off"> 
									<span class="ui-text-info">元</span>		
									<i class="check-right"></i>
									<div name="initialMoney">			
									<span class="ui-prompt"><div  id="div_pro" style="display:none">充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</div></span>									
									<span class="ui-check"><i class="error"></i>充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</span>
								 </div>	
								</li>
								<li class="ui-text" style="margin-left: 110px;height:10px;">
									<span class="ui-singleline" style="display:none;margin-top: -35px;" id="chineseMoney"></span>
								</li>	
							
								<li class="ui-btn" style="margin-left: 110px"><a href="javascript:void(0);" id="J-next-step" class="btn btn-disable">下一步<b class="btn-inner"></b></a><span id="wait_msg" style="display:none;color:red;position:relative;top:10px;">　请稍等，正在获取信息……</span>
								<span class="ui-check"></span>
								</li>
							
							
						</ul>
				</div>
				<input type="hidden" id="alipayDepositMode" value="8">
				<input type="hidden" id="chargeVersion" name="chargeVersion" value="1" />
				<!-- --------------------------------------------------------------企業版--------------------------------------------------------------- -->
				<!--{/if}-->
				<!--{/if}-->
				<!--{/foreach}-->
			</div>
		</div>
		</form>
		<input type="hidden" id="isCharged" value="0" />
		<input type="hidden" id="breakUrl" value="" />
		<input type="hidden" id="path_img" value="{$path_img}" />
		<!--<input type="hidden" id="userBankStrucCount" value="{$userBankStrucCount}" />  -->
		<input type="hidden" id="bankNumberHidden" value="" />
		<input type="hidden" id="bankAccountHidden" value="" />
	</div>
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script src="{$path_js}/js/userCenter/fundCharge.js" type="text/javascript" ></script>

<script type="text/tpl" id="J-template-checkstatus">
<div>
	<p class="wd-title">对不起，您尚有一笔充值申请未完成，请完成后再发起</p>
	<div>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
		
		  <tr>
			<td align="right" class="wd-td-bold">充值金额：</td>
			<td style="color:red"><#=chargeAmt#> 元</td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">付款支付宝：</td>
			<td style="color:red;"><#=nickName#>&nbsp;&nbsp;<#=bankAccount#>&nbsp;&nbsp;<#=bankNumber#></td>
		  </tr>
		  
		 
		  <tr>
			<td align="right" class="wd-td-bold">支付宝二维码：</td>
			
			<!--<div style="position:relative">-->
			<!--<td><div id='qr' style="z-index:1"> </div>-->
			<!--<img id="ali" style="z-index:2; position:absolute;top:200px;left:263px" src="{$path_img}/images/admin/alipay50.png" width="30" height="30"/>-->
			<!--</div>-->
			</td>
			<td>
			<div>
			<img  src="<#=breakUrl#>" width="150" height="150" alt=""/>
			</div>
			</td>
		  </tr>
		  <tr>
			<td></td><td>扫一扫二维码，可以向帐号充值哦</td>
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

<script type="text/tpl" id="J-template-checkstatus-business">
<div>
	<p class="wd-title">对不起，您尚有一笔充值申请未完成，请完成后再发起</p>
	<div>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
		
		  <tr>
			<td style="width: 50%;" align="right" class="wd-td-bold">充值金额：</td>
			<td style="width: 50%;color:red;" align="left"><#=chargeAmt#> 元</td>
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
			<a href="#" class="btn btn-important closeBtn">继续充值<b class="btn-inner"></b></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" target="_blank">充值记录</a>
		</p>
</div>
</script>

<script>
var enableRecharge = {$enableRecharge};
</script>
{literal}
<script>
function clickDelete(id,li){
	var bankNumber=$("#bankNumber");
	var bankAccount=$("#bankAccount");
	var bankNumberHidden=$("#bankNumberHidden");
	var bankAccountHidden=$("#bankAccountHidden");
	var bankNumberPar = bankNumber.parent();	
	var bankAccountPar = bankAccount.parent();	
	
	//按下刪除的與畫面上欄位一樣就不顯示
	if(bankNumber.val() == bankNumberHidden.val()){
		bankNumber.val("");
		bankAccount.val("");
	}else{
		bankNumber.val(bankNumberHidden.val());
		bankAccount.val(bankAccountHidden.val());
	}

	if(bankNumber.val()==''){
		bankNumberPar.find('.ui-check').html("").removeClass().addClass('ui-check').show();
	}
	if(bankAccount.val()==''){
		bankAccountPar.find('.ui-check').html("").removeClass().addClass('ui-check').show();
	}
	
			var datas="id="+id+"&bankId=30&mcBankId=30&bindcardType=1";
			$.ajax({			
				type:'post',
				dataType:'json',
				cache:false,
				url:'/bindcard/alipaycarddelete/',					
				data:datas,			
					
				success:function(data){			
		
					if(data['status']=="ok"){	
						 $("#"+li).remove();
					}
				}				
			});	

	
	
}
</script>
{/literal}
{literal}
<script>
(function(){
	
	var minWindow = new phoenix.MiniWindow({cls:'ui-wd-funds-expired'}),
		mask = phoenix.Mask.getInstance(),
		maxmoney = $('.bank-more span').attr('max'),
		minmoney = $('.bank-more span').attr('min');
	var isPassChargeMount=false;
	//var safePassword = $('#password'),loadingNum = 0,loadingNum2 = 0,isPassSafePassword=false;
	var chargeamount,chargeamountPar,form = $('#J-form');
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});

	
	/*	
	if($('#userBankStrucCount').val()==0){
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">对不起，您还没有在平台绑定支付宝信息，请先绑定！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="/bindcard/index?type=1">确定<b class="btn-inner"></b></a></div>');
		minWindow.show();
	}
	*/
	var splitChar = "&nbsp;";
	var replaceChar ="<p></p>"
	var isSelect=false;
	var nowselect=2;
	var chselect=2;
	var txNTP=document.getElementById("bankNumber");
	var bankAccount=document.getElementById("bankAccount");
	var liLength = $("#ctmselect>li").length;
	var ctmselect=document.getElementById("ctmselect");
	var chargeVersion = document.getElementById("chargeVersion").value;
	txNTP.onclick=function(){
		if(liLength>0){
			ctmselect.style.display="block";
			for(var j=0;j<liLength;j++){
				var li=document.getElementById("li"+j);
				var array = (li.innerHTML).split(splitChar);
				var account = array[1].replace(replaceChar,"");
				//console.log((li.innerHTML).replace(/\</p>/g,""));
				/*if(txNTP.value==array[0] && bankAccount.value==account){
					// same as the default content of txNTP
					li.style.background="#00aaee";//與text 上一樣的顯示顏色
					li.style.color="#ffffff";
				}else{
					li.style.background="#ffffff";
					li.style.color="#909090";
				}*/
				if((j+1)%2==1){
					li.style.background="#ffffff";
				}else{
					li.style.background="#f6f6f6";
				}
				li.style.color="#909090";
				
			}
		}
	}
	for(var i=0;i<liLength;i++){
		var li=document.getElementById("li"+i);
		/*li.onmousedown=function(){
		
	    //run function2
		nowselect=parseInt(this.id.substr(2));
		if(chselect!=nowselect){
			if(document.getElementById("li"+chselect)!=null){
				document.getElementById("li"+chselect).style.background="#ffffff";
				document.getElementById("li"+chselect).style.color="black";
			}
		}
		this.style.background="#00aaee"; //按下去變顏色
		this.style.color="#ffffff"; //按下去變顏色
		}*/
	

		li.onmouseup=function(){

			var array = (this.innerHTML).split(splitChar);
			var account = array[1].replace(replaceChar,"");
			$("#bankNumberHidden").val($("#bankNumber").val());
			$("#bankAccountHidden").val($("#bankAccount").val());
			
			txNTP.value=array[0];
			$("#bankAccount").val(account);
			chselect=parseInt(this.id.substr(2));
			ctmselect.style.display="none";
			//直接點下拉就顯示成功
			bankNumberPar.find('.ui-check').html("").removeClass().addClass('ui-check ui-check-ok').show();
			bankAccountPar.find('.ui-check').html("").removeClass().addClass('ui-check ui-check-ok').show();
			//checkform();
			//因直接點下拉所以先算成功一次，onblur 會因為先抓到空值判斷錯誤+1
			setErrorNum('bankNumber', -1);
			
		}
		li.onmouseover=function(){
			var array = (this.innerHTML).split(splitChar);
			var account = array[1].replace(replaceChar,"");
			isSelect=true;
			if(//parseInt(this.id.substr(2))!=nowselect ||
					(array[0]!=txNTP.value || account!=bankAccount.value)){
				//this.style.background="#ffffff";//滑鼠經過的顏色 #ffffff 白
			}
		}
		li.onmouseout=function(){
			var array = (this.innerHTML).split(splitChar);
			var account = array[1].replace(replaceChar,"");
			isSelect=false;
			if(//parseInt(this.id.substr(2))!=nowselect
					array[0]!=txNTP.value || account!=bankAccount.value){
				//this.style.background="#ffffff";//未選取 白色
			}
			else if (array[0]==txNTP.value && account==bankAccount.value){
				//this.style.background="#00aaee";//已選取
			}
		}
	}
	txNTP.onblur=function(){
		if(isSelect==false)
			ctmselect.style.display="none";
	}

	
	var position = $('#bankNumber').position();  
	var x = position.left;  
	var y = position.top+35;  
	$('#ctmselect').css('top',y).css('left',x)
	
	
	
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
	//增加企業版金額上限及下限提示
	if(chargeVersion == 1){
		$('#lowLimit').html(formatMoney($('.bank-more span').attr('min')));
		$('#upLimit').html(formatMoney($('.bank-more span').attr('max')));
	}
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
	var mask = phoenix.Mask.getInstance();
	//提示消息
	var maskMsg = {
		el:$('#J-ui-mask-msg'),
		show:function(isSuccess, msg){
			var me = this;
			if(isSuccess){
				me.el.find('i').removeClass().addClass('ico-success');
				me.el.removeClass().addClass('pop pop-success w-4');
			}else{
				me.el.find('i').removeClass().addClass('ico-error');
				me.el.removeClass().addClass('pop pop-error w-6');
			}
			me.el.find('.pop-text').html(msg);
			phoenix.util.toViewCenter(me.el);
			me.el.show();
			mask.show();
		},
		hide:function(){
			var me = this;
			me.el.hide();
			mask.hide();
		}
	};
	
	var loadingFn = function(el){
		var el = $(el);
	};
	var eventFocus = function(el, msg){
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-tip').show();
	};
	var showError = function(el, msg){
		$(el).parent().find('.ui-check').html('<i></i>'+msg).removeClass().addClass('ui-check').show();
	};
	var showLoading = function(el){
		$(el).parent().find('.ui-check').html('&nbsp;&nbsp;&nbsp;&nbsp;').removeClass().addClass('ui-check ui-check-loading').show();
	};
	var hideLoading = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	var showRight = function(el, msg){
		var msg = typeof msg == 'undefined' ? '' : msg;
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-right').show();
	};
	var hideTip = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	var ajaxCallBackList2 = [];
	var ajaxCallBack2 = function(obj){
		var i = 0,len = ajaxCallBackList2.length;
		if(loadingNum2 > 0){
			return;
		}
		for(;i < len;i++){
			if(ajaxCallBackList2[i].call(obj || null) === false){
				break;
			}
		}
		ajaxCallBackList2 = [];
	};
	
	//isAjax 是否进行远程校验
	//isSubmit 验证成功是否调用回调列队
	/* var checkSafePassword = function(isAjax, isSubmit){
		var el = safePassword,v = $.trim(el.val()),isSafePassword = false;
		if(v == ''){
			showError(el, '安全密码不能为空');
			isPass = isSafePassword = false;
		}else if(!(/^.{6,20}$/).test(v)){
			showError(el, '长度有误，请输入6-20位字符');
			isPass = isSafePassword = false;
		}else{
			hideTip(el);
			isPass = true;
		}
		if(isAjax && isPass){
			$.ajax({
				url:'/safepersonal/checksecuritypwd',
				dataType:'json',
				method:'post',
				async:false,
				data:{'securitypwd':v},
				beforeSend:function(){
					showLoading(el);
					loadingNum2 += 1;
				},
				success:function(data){
					hideLoading(el);
					loadingNum2 -= 1;
					if(data['status'] == 'ok'){
						isSafePassword = true;
						showRight(el);
						if(isSubmit){
							ajaxCallBack2();
						}
					}else{
						isSafePassword = false;
						showError(el, data['data']);
					}
				}
			});
		}
		return isSafePassword;
	}; */
	
	//安全密码
	/* safePassword.focus(function(){
		eventFocus(this, '请输入当前资金密码');
	});
	safePassword.blur(function(){
		isPassSafePassword = checkSafePassword();
	}); */


	var checkFirstAlipay = function(){
		var result ='';
		$.ajax({
			url:'/fund/checkfirstalipay/',
			dataType:'json',
			method:'post',
			async:false,
			data:{'bankNumber':$('#bankNumber').val(),'bankAccount':$('#bankAccount').val(),'nickName':$('#nickName').val()},
			success:function(data){
			
				if(data['status'] == 'ok'){
					result= true;
				}else{

					$('#J-next-step').parent().find('.ui-check')
					.html("<i class='error'></i>对不起，您尚未在平台绑定支付宝信息，请先绑定然后进行充值。").removeClass().addClass('ui-check ui-check-tip2').show();

					
					result= false;
				
				}
			}
		});

		return result;

	}
	
	
	var checkOrderStatus = function(){
		if(chargeVersion == 0){
			var type = '6';
		}else{
			var type = '8';
		}

		if($("#isCharged").val() == '1'){
			minWindow.setTitle('温馨提示');
			if(chargeVersion == 0){ //個人版的提示有QR-CODE
				minWindow.setContent($('#J-template-checkstatus').val());
			}else if(chargeVersion == 1){  //企業版的提示
				minWindow.setContent($('#J-template-checkstatus-business').val());
			}
			minWindow.show();
			cancleApply($('#J-button-order-cancel').attr('prevalue'));
			//qrfunc($('#breakUrl').val());
			return true;
		} else if($("#isCharged").val() == '2'){
			return false;
		}
		$.ajax({
			url:'/fund/checkchargesuccess/',
			dataType:'json',
			data:"type="+type,//deposit mode
			async:false,
			success:function(data){
				//var data = {isSuccess:1, type:'', msg:'请求成功', data:{bankFrom:'充值银行名称', money:'充值金额', bankTo:'收款银行', name:'收款人', email:'收款Email地址', message:'附言', bankOpen:'开户行名称'}};
				if(Number(data['isSuccess']) == 1){
					
					if(chargeVersion == 0){ //個人版的提示有QR-CODE
						var str = $('#J-template-checkstatus').html(),
							reg,
							p,
							breakUrl;
						for(p in data['data']){
							if(p == null){
								p = '';
							}
							reg = RegExp('<#=' + p + '#>', 'g');
							str = str.replace(reg, data['data'][p]);
							/*if(p == 'breakUrl'){

								breakUrl = data['data'][p];
								$('#breakUrl').val(breakUrl);
								
								
							}*/
						}
						$("#isCharged").val('1');
						$('#J-template-checkstatus').val(str);
						minWindow.setTitle('温馨提示');
						minWindow.setContent(str);
					}else if(chargeVersion == 1){  //企業版的提示
						var str = $('#J-template-checkstatus-business').html(),
							reg,
							p,
							breakUrl;
						for(p in data['data']){
							if(p == null){
								p = '';
							}
							reg = RegExp('<#=' + p + '#>', 'g');
							str = str.replace(reg, data['data'][p]);
							/*if(p == 'breakUrl'){

								breakUrl = data['data'][p];
								$('#breakUrl').val(breakUrl);
								
								
							}*/
						}
						$("#isCharged").val('1');
						$('#J-template-checkstatus-business').val(str);
						minWindow.setTitle('温馨提示');
						minWindow.setContent(str);
					
					}
					
					minWindow.show();
					//qrfunc(breakUrl);
					cancleApply($('#J-button-order-cancel').attr('prevalue'));
					
					return true;
				}else{
					$("#isCharged").val('2')
					return false;
				}
			}
		});
	};

	var qrfunc = function(url){

			$('#qr').qrcode(
	
	   			 {
	       			 width: 125,height: 125,text:  url
	       			 ,typeNumber:10,correctLevel    : 0
	   			 }
			);
		
	}


		
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
	
	var checkRechargeStatus = function(){
		if(enableRecharge ==0){
			minWindow.setTitle('温馨提示');
			minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">您帐号充值功能已被冻结，请联系您的上级代理或平台客服！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
			minWindow.show();
			return false;
		}
		return true;
	};
	errorTypes2 = ['chargeamount','bankNumber','bankAccount'],
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
	
	var getClolor=function(obj){
			var v=obj.value;
			if(v.indexOf(".")==-1)
			{
				
				if(v.length == 3)
				{
					$(obj).attr("style","color:green");
				}else if(v.length == 4)
				{
					$(obj).attr("style","color:red");
				}else if(v.length >= 5)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}else if(v.indexOf(".")>0)
			{
				var vlist=v.split(".");
				if(vlist[0].length == 3)
				{
					$(obj).attr("style","color:green");
				}else if(vlist[0].length == 4)
				{
					$(obj).attr("style","color:red");
				}else if(vlist[0].length >= 5)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}
	    }
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

	//充值金额验证
	chargeamount = $('#chargeamount');
	chargeamountPar = chargeamount.parent();	
	chargeamount.blur(function(){
		var v = $.trim(this.value);
		checkFn2(this);
		checkform();
		if(checkOrderStatus()){
			die;
		}
		isPassChargeMount = true;
		this.value=formatMoney(v);
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
	    /* $('#chineseMoney').parent().css("margin-top","-20px"); */
	    $('#chineseMoney').css("display","inline-block");
        $("#chineseMoney").html(changeMoneyToChinese(this.value));
	});
	
	
	$('.bank-more-content').css("display","none");
	$('.bank-more').css("min-height","45px");

	if(maxmoney !=0){
		 $('[name="initialMoney"],#div_pro').css("display","inline");
		 $('[name="txtminmoney"]').html(formatMoney(minmoney));
		 $('[name="txtmaxmoney"]').html(formatMoney(maxmoney));	
		 chargeamount.removeAttr("disabled");
	} else {
		 $('[name="initialMoney"]').css("display","none");
	}
	//表单检测
	checkform = function(){			
		if($.trim($('#chargeamount')[0].value) != "")
		{
			if((minmoney.replace(/,/g, '') ==0 && maxmoney.replace(/,/g, '') ==0) || $('#chargeamount')[0].value.replace(/,/g, '') == '' || parseFloat($('#chargeamount')[0].value.replace(/,/g, '')) > maxmoney || parseFloat($('#chargeamount')[0].value.replace(/,/g, '')) < minmoney.replace(/,/g, '')){
				chargeamountPar.find('.ui-check').css('display', 'inline');	
				chargeamountPar.find('.ui-prompt').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'none');
				setErrorNum2('chargeamount', 1);
				$("#J-next-step").removeClass("btn btn-important").addClass("btn btn-disable");					
			}	
			else{
				chargeamountPar.find('.ui-check').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'inline-block');
				setErrorNum2('chargeamount', -1);
				$("#J-next-step").removeClass("btn btn-disable").addClass("btn btn-important");
			}	
		}	
		else
		{
			chargeamountPar.find('.ui-check').css('display', 'inline');	
			chargeamountPar.find('.ui-prompt').css('display', 'none');
			chargeamountPar.parent().find('.check-right').css('display', 'none');
			setErrorNum2('chargeamount', 1);
			$("#J-next-step").removeClass("btn btn-important").addClass("btn btn-disable");			
		}

		
		if(chargeVersion != 1){
			var bankNum = $.trim($('#bankNumber').val());
			if(bankNum==''){
			    bankNumberPar.find('.ui-check').html("<i class='error'></i>支付宝账户名不可为空");
			    bankNumberPar.find('.ui-check').css('display', 'inline');	
			    bankNumberPar.find('.ui-prompt').css('display', 'none');
			    setErrorNum2('bankNumber',1);
			}
			
			
			if($.trim($('#bankAccount').val())=='' )
			{	
				bankAccountPar.find('.ui-check').html("<i class='error'></i>支付宝姓名不可为空");
				bankAccountPar.find('.ui-check').css('display', 'inline');	
				bankAccountPar.find('.ui-prompt').css('display', 'none');
				setErrorNum2('bankAccount',1);
			}	
		}

	}

	$('#J-next-step').click(function(){

		form.submit();
	});

	$("#bankNumber").focus(function(){
		
		setErrorNum2('bankNumber', -1);
	});

	$("#bankAccount").focus(function(){
		
		setErrorNum2('bankAccount', -1);
	});	
	
	//驗證是否超過單日金額限制
	var checkIsOverDayLimit = function(){
		var returnResult=false;	
		var depositMode = $('#alipayDepositMode').val();
		$.ajax({
			url:'/fund/checkBankDayLimit',
			type:'POST',
			dataType:'text',
			data:"depositMode="+depositMode,
			async: false,
		    success:function(data){
				var result = JSON.parse(data);
				if(result['dayLimit']==0){
					minWindow.setTitle('温馨提示');
					minWindow.setContent('<div class="pop-btn"><i class="ico-waring <#=icon-class#>"></i><h4 class="pop-text">支付宝充值已达到单日上限！</h4></div><div class="pop-btn">请您明日0点再次尝试，建议先使用其他充值方式充值</div><div class="pop-btn"><a class="btn closeBtn">关 闭<b class="btn-inner"></b></a></div>');
					$('.closeBtn').click(function(){
						window.location.href="/fund/index";
					});
					minWindow.show();
					returnResult=true;
				}else{
					returnResult=false;
				}
			},
		});	
						
		return returnResult;
		
		
		
		
		
    };
	
	form.submit(function(e){
		var err = 0;	
		checkform();	
		
		//驗證是否超過單日金額限制
		if(checkIsOverDayLimit()){  //超過上限
			return false;
		}
		
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}

		});

		$.each(errorTypes2, function(){
			if(typeof errorHas2[this] != 'undefined'){
				err += errorHas2[this];
			}

		});
		if(err > 0){ 			
			e.preventDefault(); 
			return false;	
		} else {

			if(!checkRechargeStatus()){
				return false;
			}

			if(checkOrderStatus()){
				return false;
			}


			if(!isPassChargeMount){
				return false;
			}
			/* if(!checkSafePassword(true)){
				return false;
			} */
			/* if($("#lastChargeCard").val()=='' && !checkFirstAlipay()){
				return false;
			}*/

			if($("#isCharged").val()=='1'){
				return false;
			}
			
			$('#wait_msg').show();
			return true;
		}
	});

})();
</script>
<style>
	.ui-check-tip {color:#999;}
	
	.ui-check-tip2 {color:#F46E00;}
	
	.tip-text-color p {color:#555555;font-size:13px;font-family:微软雅黑;padding-top:13px}
	.tip-text-color b {color:#f37003;font-size:13px;font-family:微软雅黑;padding-top:14px}
	.tip-text-color span {color:#009b87;}
	

	.customselect{
		display:none;
		z-index:2;
		position:absolute;
		top:274px;
		left:160px;
		border: 1px solid #e3e3e3;

		
		
	}
	.customselect li{
		
		border: 1px solid #e3e3e3;
		cursor: default;
		margin:0px;
		padding:11px;
		width:197px;
		font-size:12px;
		font-family:微软雅黑;
		
	}

	
</style>
{/literal}	
</body>

</html>