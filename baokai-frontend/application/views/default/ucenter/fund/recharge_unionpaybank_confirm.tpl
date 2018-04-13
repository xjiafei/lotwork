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
									<td><div class="tri"><div class="con">3.银联在线支付汇款</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="bank-check">
						<div class="bank-title">充值确认</div>
						<ul class="ui-form">
							<li>
								<label class="ui-label">充值金额：</label>
								<span class="ui-singleline"><font color="red">{$chargeamount}</font> 元</span>
							</li>
							<li>
								<label class="ui-label">需用银行卡：</label>
								<span class="ui-singleline">可使用任意一张银行卡进行汇款</span>
							</li>
						</ul>
					</div>
					<a href="javascript:void(0);" id="J-pay-for" url="{$url}" class="btn btn-important">立即充值</a>
					<input type="hidden" id ="longTime"  value="{$longTime}" />
					<div class="help-text">更多帮助 <a target="_blank" href="/help/queryGeneralDetail?helpId=1605">进入银联充值帮助</a></div>
				</div>
			</div>
		</div>
		
	</div>
{literal}
<script>
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
		var url = $(this).attr('url');
		if($(this).attr('urlIsUsed')!='1'){
			window.open(url);
			$(this).attr('urlIsUsed','1');
		} else {
			mask.show();
			msg.show({
				hideTitle: 'false',
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">该链接已被使用,请重新充值!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					window.location.href="/fund/index?type=3&premount="+$('ul.ui-form li').eq(1).find('font').html();
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
				window.location.href="/fund/index?type=3&premount="+$('ul.ui-form li').eq(1).find('font').html();
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
				window.location.href="/fund/index?type=3&premount="+$('ul.ui-form li').eq(1).find('font').html();
			}
		});
	};
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