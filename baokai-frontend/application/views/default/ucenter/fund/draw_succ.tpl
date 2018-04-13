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
				<div class="title">我要提现</div>
				<div class="content bank-cash-continue">
					<div class="notice"><i class="ico-warning"></i>提现所产生的银行手续费由平台为您免除。</div>
					<div class="alert alert-mini alert-success">
						<i class="ico-success"></i>
						<div class="txt">
							<h4>提现申请成功！系统将在<span class="color-red">15分钟内</span>处理您的申请。</h4>
						</div>
					</div>
					<div class="bank-cash">
						<div class="bank-more-list">
							<div class="bank-more-content">
								<table>
									<tbody>
										<tr>
											<td>开户人姓名：{$result.bankAccount}</td>
										</tr>
										<tr>
											<td>提现银行：{$result.bankName}</td>
										</tr>
										<tr>
											<td>银行卡号：{$result.bankNumber}</td>
										</tr>
										<tr>
											<td>提现金额：<strong class="color-red">{$result.changCount}元</strong></td>
										</tr>
									</tbody>
								</table>
                            </div><br/>
                            <!-- {if $result.drawCount neq 1} -->
                            <h5>为保证提款速度，系统会将您本次提现拆分为<span class="color-red">{$result.drawCount}</span>个不同提现订单，请点击下方“查看提现记录”查询</h5>
                            <div class="bank-more-content">
                                <table>
									<tbody>
                                        <!--{foreach $drawlist as $data}-->
										<tr>
											<td>交易流水号：{$data.sn}</td> <td>提现金额：{$data.withdrawAmt}  </td>
										</tr>
										<!--{/foreach}-->
									</body>
								</table>
                            </div>
                            <!-- {/if} -->
						</div>
					</div>
					<ul class="ui-form">
						<li class="ui-btn">
							<a href="/withdraw/" class="btn btn-important">继续提现<b class="btn-inner"></b></a>
							<a class="btn btn-link" href="/withdraw/history/">查看提现记录</a>
						</li>
						<!--<li class="ui-text">
							<span class="ui-singleline">为了您的资金安全，您本日还可提现{$result.availWithdrawTime}次。</span>
						</li>-->
					</ul>
				</div>
			</div>
		</div>
		
	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->


<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal}
{/literal}
	
</body>
</html>