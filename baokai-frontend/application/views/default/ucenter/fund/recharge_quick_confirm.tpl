<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
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
									<td><div class="con">1.选择银行并填写金额</div></td>
									<td class="current"><div class="tri"><div class="con">2.确认信息充值</div></div></td>
									<td><div class="tri"><div class="con">3.登录网上银行充值</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="bank-check">
						<div class="bank-title">充值确认</div>
						<ul class="ui-form">
							<li>
								<label class="ui-label">充值银行：</label>
								<span class="ico-bank {$status}" id="{$status}"></span>
							</li>
							<li>
								<label class="ui-label">充值金额：</label>
								<span class="ui-singleline"><font color="red">{$chargeamount}</font> 元</span>
							</li>
							<li>
								<label class="ui-label">需用银行卡：</label>
								<span class="ui-singleline">可使用任意一张{$bankName}卡进行汇款</span>
							   {if $isTHpay==1}
								   <form id="gateway" method="post" action="http://{$thPay.reqReferer}/THTransfer/transfer" target="_blank">
									   <input type="hidden" name="input_charset" value="{$thPay.inputCharset}"/>
									   <input type="hidden" name="notify_url" value="{$thPay.notifyUrl}"/>
									   <input type="hidden" name="pay_type" value="{$thPay.payType}"/>
									   <input type="hidden" name="bank_code" value="{$thPay.bankCode}"/>
									   <input type="hidden" name="merchant_code" value="{$thPay.merchantCode}"/>
									   <input type="hidden" name="order_time" value="{$thPay.orderTime}"/>
									   <input type="hidden" name="order_no" value="{$thPay.orderNo}">
									   <input type="hidden" name="order_amount" value="{$thPay.orderAmount}"/>
									   <input type="hidden" id="req_referer" name="req_referer" value="{$thPay.reqReferer}"/>
									   <input type="hidden" name="customer_ip" value="{$thPay.customerIp}"/>
									   <input type="hidden" name="sign" value="{$thPay.sign}"/>
									   <input type="hidden" id="isTHpay" name="isTHpay" value="{$isTHpay}"/>
									</form>
							   {/if}
							   {if $isHBpay==1}
								   <form id="gateway" method="post" action="http://{$hbPay.transDomain}/HBTransfer/transfer" target="_blank">
									   <input type="hidden" name="signature" value="{$hbPay.sign}"/>
									   <input type="hidden" name="data" value="{$hbPay.data}"/>
									   <input type="hidden" id="isHBpay" name="isHBpay" value="{$isHBpay}"/>
									</form>
							   {/if}
							   {if $isSPpay==1}
								   <form id="gateway" method="post" action="http://{$spPay.transDomain}/SPTransfer/transfer" target="_blank">
									   <input type="hidden" name="MERCNUM" value="{$spPay.MERCNUM}"/>
									   <input type="hidden" name="TRANDATA" value="{$spPay.TRANDATA}"/>
   									   <input type="hidden" name="SIGN" value="{$spPay.SIGN}"/>
									   <input type="hidden" id="isSPpay" name="isSPpay" value="{$isSPpay}"/>
									</form>
							   {/if}
							   {if $isDDBpay==1}
								   <form id="gateway" method="post" action="http://{$ddbPay.transDomain}/DDBTransfer/transfer" target="_blank">
									<input type="hidden" name="sign" value="{$ddbPay.sign}" />
									<input type="hidden" name="merchantCode" value="{$ddbPay.merchantCode}" />
									<input type="hidden" name="serviceType" value="{$ddbPay.serviceType}" />	
									<input type="hidden" name="interfaceVersion" value="{$ddbPay.interfaceVersion}" />			
									<input type="hidden" name="inputCharset" value="{$ddbPay.inputCharset}" />	
									<input type="hidden" name="notifyUrl" value="{$ddbPay.notifyUrl}"/>
									<input type="hidden" name="signType" value="{$ddbPay.signType}" />		
									<input type="hidden" name="orderNo" value="{$ddbPay.orderNo}"/>
									<input type="hidden" name="orderTime" value="{$ddbPay.orderTime}" />	
									<input type="hidden" name="orderAmount" value="{$ddbPay.orderAmount}"/>
									<input type="hidden" name="productName" value="{$ddbPay.productName}" />	
									<input type="hidden" name="returnUrl" value="{$ddbPay.returnUrl}"/>	
									<input type="hidden" name="bankCode" value="{$ddbPay.bankCode}" />
									<input type="hidden" id="isDDBpay" name="isDDBpay" value="{$isDDBpay}"/>
									</form>
							   {/if}
							   {if $isDINpay==1}
								   <form id="gateway" method="post" action="http://{$dinPay.transDomain}/DINTransfer/transfer" target="_blank">
									<input type="hidden" name="sign" value="{$dinPay.sign}" />
									<input type="hidden" name="merchantCode" value="{$dinPay.merchantCode}" />
									<input type="hidden" name="serviceType" value="{$dinPay.serviceType}" />	
									<input type="hidden" name="interfaceVersion" value="{$dinPay.interfaceVersion}" />			
									<input type="hidden" name="inputCharset" value="{$dinPay.inputCharset}" />	
									<input type="hidden" name="notifyUrl" value="{$dinPay.notifyUrl}"/>
									<input type="hidden" name="signType" value="{$dinPay.signType}" />		
									<input type="hidden" name="orderNo" value="{$dinPay.orderNo}"/>
									<input type="hidden" name="orderTime" value="{$dinPay.orderTime}" />	
									<input type="hidden" name="orderAmount" value="{$dinPay.orderAmount}"/>
									<input type="hidden" name="productName" value="{$dinPay.productName}" />	
									<input type="hidden" name="returnUrl" value="{$dinPay.returnUrl}"/>	
									<input type="hidden" name="bankCode" value="{$dinPay.bankCode}" />
									<input type="hidden" id="isDINpay" name="isDINpay" value="{$isDINpay}"/>
									</form>
							   {/if}
							   {if $isHUAYINpay==1}
								   <form id="gateway" method="post" action="http://{$huayinPay.transDomain}/HUAYINTransfer/transfer" target="_blank">
									<input type="hidden" name="sign" value="{$huayinPay.sign}" />
									<input type="hidden" name="merchantCode" value="{$huayinPay.merchantCode}" />
									<input type="hidden" name="serviceType" value="{$huayinPay.serviceType}" />	
									<input type="hidden" name="interfaceVersion" value="{$huayinPay.interfaceVersion}" />			
									<input type="hidden" name="inputCharset" value="{$huayinPay.inputCharset}" />	
									<input type="hidden" name="notifyUrl" value="{$huayinPay.notifyUrl}"/>
									<input type="hidden" name="signType" value="{$huayinPay.signType}" />		
									<input type="hidden" name="orderNo" value="{$huayinPay.orderNo}"/>
									<input type="hidden" name="orderTime" value="{$huayinPay.orderTime}" />	
									<input type="hidden" name="orderAmount" value="{$huayinPay.orderAmount}"/>
									<input type="hidden" name="productName" value="{$huayinPay.productName}" />	
									<input type="hidden" name="returnUrl" value="{$huayinPay.returnUrl}"/>	
									<input type="hidden" name="bankCode" value="{$huayinPay.bankCode}" />
									<input type="hidden" id="isHUAYINpay" name="isHUAYINpay" value="{$isHUAYINpay}"/>
									</form>
							   {/if}
							   {if $isYINBANGpay==1}
								   <form id="gateway" method="post" action="http://{$yinbangPay.transDomain}/YINBANGTransfer/transfer" target="_blank">
									<input type="hidden" name="sign" value="{$yinbangPay.sign}" />
									<input type="hidden" name="merId" value="{$yinbangPay.merId}" />
									<input type="hidden" name="version" value="{$yinbangPay.version}" />	
									<input type="hidden" name="encParam" value="{$yinbangPay.encParam}" />	
									<input type="hidden" id="isYINBANGpay" name="isYINBANGpay" value="{$isYINBANGpay}"/>
									</form>
							   {/if}
							   {if $isJINYANGpay==1}
								   <form id="gateway" method="post" action="http://{$jinyangPay.transDomain}/JINYANGTransfer/transfer" target="_blank">
									<input type="hidden" name="sign" value="{$jinyangPay.sign}" />
									<input type="hidden" name="merId" value="{$jinyangPay.merId}" />
									<input type="hidden" name="payType" value="{$jinyangPay.payType}" />	
									<input type="hidden" name="paymoney" value="{$jinyangPay.paymoney}" />	
									<input type="hidden" name="orderno" value="{$jinyangPay.orderno}" />	
									<input type="hidden" name="asynURL" value="{$jinyangPay.asynURL}" />	
									<input type="hidden" name="version" value="{$jinyangPay.version}" />	
									<input type="hidden" name="signType" value="{$jinyangPay.signType}" />
									<input type="hidden" name="isShow" value="{$jinyangPay.isShow}" />
									<input type="hidden" id="isJINYANGpay" name="isJINYANGpay" value="{$isJINYANGpay}"/>
									</form>
							   {/if}
                            </li>
						</ul>
					</div>
					<a href="javascript:void(0);" id="J-pay-for" url="{$url}" class="btn btn-important">去网上银行付款</a>
					<input type="hidden" id ="longTime"  value="{$longTime}" />
					<div class="help-text">更多帮助 <a target="_blank" href="/help/queryGeneralHelp?cateId2=573&cate2Name=%25E5%25BF%25AB%25E6%258D%25B7%25E5%2585%2585%25E5%2580%25BC&orderBy=no desc,gmt_created desc">进入快捷充值帮助</a></div>
				</div>
			</div>
		</div>
		
	</div>
{literal}
<script>
    function send(){
        document.getElementById("gateway").submit();
    }
(function(){
	var msg = new phoenix.Message(),
		$payForButton = $('#J-pay-for');
	var mask = phoenix.Mask.getInstance();
	msg.addEvent('beforeShow', function(){
		mask.show();
	});
	msg.addEvent('afterHide', function(){
		mask.hide();
	});
	$(".closeBtn").click(function(){
		msg.hide();
		mask.hide();
	});
	//去网银充值弹层
	$payForButton.click(function(){
		 if($(this).attr('urlIsUsed')!='1'){
			var isTHpay = $("#isTHpay").val();
			var isHBpay = $("#isHBpay").val();
			var isDDBpay = $("#isDDBpay").val();
			var isDINpay = $("#isDINpay").val();
			var isHUAYINpay = $("#isHUAYINpay").val();
			var isYINBANGpay = $("#isYINBANGpay").val();
			var isJINYANGpay = $("#isJINYANGpay").val();
			if(isTHpay == 1){
				send();
			}else if(isHBpay == 1){
				send();
			}else if(isDDBpay == 1){
				send();
			}else if(isDINpay == 1){
				send();
			}else if(isHUAYINpay == 1){
				send();
			}else if(isYINBANGpay == 1){
				send();
			}else if(isJINYANGpay == 1){
				send();
			}else{
				var url = $(this).attr('url');
				window.open(url);
			}
			$(this).attr('urlIsUsed','1');
		} else {
			mask.show();
			msg.show({
				hideTitle: 'false',
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">该链接已被使用,请重新充值!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					window.location.href="/fund/index?type=1&premount="+$('ul.ui-form li').eq(1).find('font').html();
				}
			});
			return;
		}
		//点击充值 停止超时提示
		clearInterval(timer);
		mask.show();
		msg.show({
			hideTitle: 'true',
			content: '<h3 style="height:30px;line-height:30px;text-align:center;">请在新开页面上完成汇款</h3><div style="height:30px;line-height:30px;"><a target="_blank" href="/help/queryGeneralDetail?helpId=923">付款遇到问题？</a></div>',
			confirmIsShow: 'true',
			confirmText: '查看付款结果',
			confirmFun: function(){
				window.open('/fund/history');
			},
			cancelIsShow: 'true',
			cancelText: '继续付款',
			cancelFun: function(){
				window.location.href="/fund/index?type=1&premount="+$('ul.ui-form li').eq(1).find('font').html();
			}
		});
	});

	//超时失效弹窗
	var timeOverDialog = function(){
		mask.show();
		msg.show({
			mask:'true',
			hideTitle: 'true',
			content: '<h3 style="height:50px;line-height:50px;">由于你长时间没有充值，该申请已经失效</h3></div>',
			confirmIsShow: 'true',
			confirmText: '查看付款结果',
			confirmFun: function(){
				window.open('/fund/history');
			},
			cancelIsShow: 'true',
			cancelText: '重新充值',
			cancelFun: function(){
				window.location.href="/fund/index?type=1&premount="+$('ul.ui-form li').eq(1).find('font').html();
			}
		});
	};
	$('.ui-tab-title ul li').click(function(){
		var indexs = $(this).index();
		if($(this).attr('class') !='current'){
              if(indexs==0)
            {
                indexs=1;
            }
            else if(indexs==1)
            {
                indexs=0;
            }
			window.location.href="/fund/index?type="+indexs;
		}
	});
	//充值有效时间倒计时
	var seconds = $('#longTime').val()*60,
		timer,
		mm,
		ss;
	timer = setInterval(function(){
		if(seconds <= 0){
			clearInterval(timer);
			timeOverDialog();
		}
		mm = Math.floor(seconds/60%60);
		ss = Math.floor(seconds%60);
		mm = mm < 10 ? '0' + mm : mm;
		ss = ss < 10 ? '0' + ss : ss;
		seconds--;
	}, 1000);
	
})();
</script>
{/literal}
<!-- //////////////底侧公共页面////////////// -->
 {include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->