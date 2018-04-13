<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>开户中心</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
    <link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
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
		<form action="/applycenter/index/" method="post" id="J-form">
		
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">开户中心</div>
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-title tab-title-bg clearfix">
							<ul>
								<li><a href="/applycenter/index/">链接开户</a></li>
								<li class="current">链接管理</li>
							</ul>
						</div>
						<div class="">
							<!-- start -->
							<div class="rebate-list">
								<div class="notice">
									<p>您当前查看的开户链接详情如下：<a href="{$aUrl['url']}" target="_blank">{$aUrl['url']}</a></p>
									<p>链接有效期：{if $aUrl['days'] neq -1} {$aUrl['createTime']} 至 {/if}{$aUrl['exp']}</p>
								</div>
                                <div id="div-context" class="lottery-switcher">
                                <div class="lottery-tabs">
                               		<!-- {foreach from=$aAwardSeris key=key item=data} -->
                               		<a href="javascript:void(0);">{$data}</a>
                               		<!-- {/foreach} -->
                                </div>
								<!-- {foreach from=$result key=key item=data} -->
                                	<div class="lottery-switch">
                                		<!-- {foreach from=$aAwardGroup[$key] key=sub_key item=sub_value} -->
                            			<!-- {{assign var="sub_value" value=$result[$key][$sub_key]}} -->
                                		<dl class="item">
											<dt>{$aAwardGroup[$key][$sub_key]}</dt>
											<!-- {if $sub_value|@count>0} -->
											<!-- {foreach from=$sub_value key=sub_key1 item=sub_data} -->
											<dd>
												<table class="table">
													<!-- {if $sub_data.lotteryId != '99701'} -->
													<tr>
														
														<!-- {if $sub_data.lotteryId != '99113'} -->
														<td rowspan="3" style="width:160px">{$sub_data.awardName}：</td>
														<td style="width:400px">
															<span class="label-like">&nbsp;&nbsp;&nbsp;{if $aRateName[$sub_data.lotterySeriesCode][0]}{$aRateName[$sub_data.lotterySeriesCode][0]}{/if}</span>
															<input type="input" class="input w-2"   value="{$sub_data.directRet}"  readonly/>
														</td>
														<td rowspan="3"></td>
														<!-- {else} -->
														<td rowspan="2" style="width:43px">{$sub_data.awardName}：</td>
														<!-- {/if} -->
														
													</tr>
													<!-- {/if} -->
													<!-- {if $sub_data.lotterySeriesCode neq '5' and $sub_data.lotterySeriesCode neq '3' and $sub_data.lotterySeriesCode neq '6' and $sub_data.lotterySeriesCode neq '8' and $sub_data.lotterySeriesCode neq '9' and $sub_data.lotteryId != '99113'} -->
													<tr>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][1]}{$aRateName[$sub_data.lotterySeriesCode][1]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.threeoneRet}"  readonly/>
														</td>
													</tr>
													<!-- {/if} -->
													
													<!-- {if $sub_data.lotteryId == '99701'} 六合彩 -->

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][0]}{$aRateName[$sub_data.lotterySeriesCode][0]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcFlatcode}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:400px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][1]}{$aRateName[$sub_data.lotterySeriesCode][1]}{/if}</span>
															<input type="input" class="input w-2"   value="{$sub_data.directRet}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][2]}{$aRateName[$sub_data.lotterySeriesCode][2]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcYear}"  readonly/>
														</td>
													</tr>
													
													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][3]}{$aRateName[$sub_data.lotterySeriesCode][3]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcColor}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][4]}{$aRateName[$sub_data.lotterySeriesCode][4]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcHalfwave}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][5]}{$aRateName[$sub_data.lotterySeriesCode][5]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcOneyear}"  readonly/>
														</td>
													</tr>

													<tr><td rowspan="3" style="width:160px">{$sub_data.awardName}：</td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][6]}{$aRateName[$sub_data.lotterySeriesCode][6]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcNotin}"  readonly/>
														</td>
													</tr>

													<tr>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][7]}{$aRateName[$sub_data.lotterySeriesCode][7]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuein23}"  readonly/>
														</td>
													</tr>

													<tr>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][8]}{$aRateName[$sub_data.lotterySeriesCode][8]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuein4}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][9]}{$aRateName[$sub_data.lotterySeriesCode][9]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuein5}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][10]}{$aRateName[$sub_data.lotterySeriesCode][10]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuenotin23}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][11]}{$aRateName[$sub_data.lotterySeriesCode][11]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuenotin4}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][12]}{$aRateName[$sub_data.lotterySeriesCode][12]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuenotin5}"  readonly/>
														</td>
													</tr>

													<tr><td></td>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][13]}{$aRateName[$sub_data.lotterySeriesCode][13]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.lhcContinuecode}"  readonly/>
														</td>
													</tr>
													<!-- {/if}  -->
													<!-- 骰寶不定位 -->
													<!-- {if  $sub_data.lotteryId == '99601' or $sub_data.lotteryId == '99602' or $sub_data.lotteryId == '99603'} -->
													<tr>
														<td style="width:140px">
															<span class="label-like">{if $aRateName[$sub_data.lotterySeriesCode][2]}{$aRateName[$sub_data.lotterySeriesCode][2]}{/if}</span>
															<input type="input"  class="input w-2"   value="{$sub_data.sbThreeoneRet}"  readonly/>
														</td>
													</tr>
													<!-- {/if}  -->
													
													<!-- {if $sub_data.lotteryId == '99101' or $sub_data.lotteryId == '99103' or $sub_data.lotteryId == '99104' or $sub_data.lotteryId == '99105' or $sub_data.lotteryId == '99113'} -->
													<tr>
														<td style="width:140px">
															<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
															<input type="input" class="input w-2"  value="{$sub_data.superRet}" readonly/>
														</td>
													</tr>
													<!-- {/if} -->
													
												</table>
											</dd>
											<!-- {/foreach} -->
											<!-- {else} -->
												<dd>
													<table class="table">
															<tr>
																<td rowspan="3">当前无奖金组</td>
															</tr>
													</table>
												</dd>
											<!-- {/if} -->
										</dl>
										<!-- {/foreach} -->
                                	</div>
                                	<!-- {/foreach} -->
                                	<script>
									// 彩系切换，新增
									new phoenix.Tab({
										par : '.lottery-switcher',
										triggers : '.lottery-tabs > a',
										panels : '.lottery-switch',
										eventType : 'click',
										currPanelClass: 'lottery-switch-current'
									});
									</script>
								</div>
							</div>
						</div>
					</div>
					<!-- end -->
				</div>
			</div>
		</div>
		
	</form>
	</div> 
    
 <div class="pop w-7" style="position:absolute;left:100px;display:none" id="divRegSuccess">
	<div class="hd"><i class="close" id="divCloseUrl"></i>提示</div>
	<div class="bd">
		<h4 class="pop-title">开户成功！共生成<span id="retrunNum"></span>条链接。<br />请到“链接管理”页面查看您生成的链接。</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " id="searchUrl">查看链接<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divNoType">
	<div class="hd"><i class="close"></i>提示</div>
	<div class="bd">
		<h4 class="pop-title">每个彩种系，至少选择一个奖金组</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " >确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divIsEmpty">
	<div class="hd"><i class="close" id="divIsEmptyClose"></i><span>提示</span></div>
	<div class="bd">
		<h4 class="pop-title">返点设置不能为空，请重新设置</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " id="J-but-close">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none" id="divOperatSuccess">
	<i class="ico-success"></i><h4 class="pop-text">操作成功</h4>
</div>
<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
	<i class="ico-error"></i><h4 class="pop-text">开户失败，请重试</h4>
</div>
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->	
</body>
</html>