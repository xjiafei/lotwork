<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
    <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
</head>
<body>
	<!-- toolbar start -->
	<!-- toolbar end -->
	<!-- header start -->
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
				<div class="title">提现进度查询</div>
				<dl class="prompt" style="margin-bottom:10px;">
					<dt>温馨提示：</dt>
					<dd>1、提现到账时间：为了保障客户资金安全，提现将经过相关部门仔细审核，提现将在发起后30分钟内到账；</dd>
				<!--	<dd>2、如果提现订单出现待定情况，请您及时查询该笔提现是否已进入到<a href="/fundappeal/appealwithdrawlist">催到账-提现申诉</a>中，如果进入，请您及时使用申诉通道进行处理；</dd>
					<dd>3、点击进度查询右上角的"提现不到账？点这里"可以进入到催到账-提现申诉列表</dd>	-->				
					<dd>2、请您及时刷新页面以便时时了解最新进度</dd>										
				</dl>
				
				<div class="content">
			<!--		<div class="tips-right" >
						提现不到账?<a href="/fundappeal/appealwithdrawlist">点这里</a>
					</div> -->
					<ul class="ui-form form-small">
						<li>
							<label class="ui-label">开户人姓名：</label>
							<!-- {foreach from=$content item=data} -->
							<span class="ui-singleline">{$data.bankaccount}</span>
							<!-- {/foreach} -->
						</li>
						<li>
							<label class="ui-label">提现银行：</label>
							<!-- {foreach from=$content item=data} -->
							<span class="ui-singleline">{$data.bankname}</span>
							<!-- {/foreach} -->
						</li>
						<li>
							<label class="ui-label">银行卡号：</label>
							<!-- {foreach from=$content item=data} -->
							<span class="ui-singleline">{$data.banknumber}</span>
							<!-- {/foreach} -->
						</li>
						<li>
							<label class="ui-label">提现金额：</label>
							<!-- {foreach from=$content item=data} -->
							<span class="ui-singleline color-red ">{$data.withdrawAmt}元</span>
							<!-- {/foreach} -->
						</li>
					</ul>
					<div class="drawing-status">
						<div class={$usfulCSS}></div>
						<ol class="status-time">
							<li class="time1">{$withdrawTimeStr1}</li>
							<li class="time2">{$withdrawTimeStr2}</li>
							<li class="time3">{$withdrawTimeStr3}</li>
							<li class="time4">{$withdrawTimeStr4}</li>
							<li class="time5">{$withdrawTimeStr5}</li>
						</ol>
					</div>
					<div class="text-area">
						<h3>操作日志</h3>
						<div class="textarea-box" style="width:750px;height:110px">
							<!-- {foreach from=$logcontent item=data name=log} -->
							<div style="word-break:break-all">
							<!-- {if $data|strpos:'Urgency-'!=0} -->
							<p style="color: red">{$data|replace:'Urgency-':''}</p>
							<!-- {else} -->
							{if $smarty.foreach.log.index==0}
							<p style="color:green">{$data}</p>
							{else}
							<p>{$data}</p>
							{/if}
							<!-- {/if} -->
							</div>
							<!-- {/foreach} -->					
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->

<script>
(function($){
	
$('#query-all').addClass("ico-tab-current");



})(jQuery);
</script>




















</body>

</html>
