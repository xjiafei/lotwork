<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{include file='/default/script-base.tpl'}
	<script type="text/javascript">
		baseUrl = "/applycenter/queryusergameaward";
	</script>
</head>
<body>
<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->	
	<div class="g_33 common-main">
		<div class="g_5">
	<!-- //////////////左侧公共页面////////////// -->
		{include file='/default/ucenter/left.tpl'}
	<!-- /////////////左侧公共页面/////////////// -->	
		</div>

		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">{$title}</div>
				<ul class="ui-tab-title clearfix" style="margin-bottom:10px;display:none"  id='_showTag'>
					<li id='_showFhTag' class="current">彩票</li>
				</ul>
				<div class="content">
					<div class="rebate-list">
						<div id="div-context" class="lottery-switcher">
                          	<div class="lottery-tabs">
                          		<!-- {foreach from=$aAwardSeris key=key item=data} -->
									<!-- {if $data neq '休闲游戏'} -->
									<a href="javascript:void(0);">{$data}</a>
									<!-- {/if} -->
                          		<!-- {/foreach} -->
                          		<input type="hidden" id="userId" value="{$userId}"/>
                          		<input type="hidden" id="type" value="{$type}"/>
                          		<input type="hidden" id="retType" value="{$smarty.session.datas.info.isAllZero}"/>
                          	</div>
						<!-- {foreach from=$result key=key item=data} -->
							<div class="lottery-switch">
							<!-- {foreach from=$aAwardGroup[$key] key=sub_key item=sub_value} -->
	                            <!-- {{assign var="sub_value" value=$result[$key][$sub_key]}} -->
								<dl class="item">
									<dt>{$aAwardGroup[$key][$sub_key]}</dt>
								<!-- {if $sub_value|@count>0} -->
									<!-- {foreach from=$sub_value key=sub_key1 item=sub_data} -->
										<!-- {if $sub_key1 neq 'status' or $sub_key1 eq '0'} -->
											<dd {if $sub_data.betType eq '1'} class="bet-selected" {/if}>
												<table class="table">
													<!-- {if $sub_data.lotteryId neq '99701'} -->
													<tr>
														<td rowspan="3" style="width:100px">{$sub_data.awardName}：</td>

														<!-- {if  $sub_data.lotteryId != '99113'} -->
														<td >
															<a class="label-like" data-value="{$sub_data.directRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][0]}{$aRateName[$sub_data.lotterySeriesCode][0]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																	<!-- {if $sub_data.directRet eq $sub_data.maxDirectRet or $sub_data.directRet gt $sub_data.maxDirectRet} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}-{$sub_data.maxDirectRet}"/>
																	<!-- {/if} -->
																<!-- {/if} -->
														</td>
														<!-- {/if} -->
													</tr>
													<!-- {/if} -->
													<!-- {if $sub_data.lotterySeriesCode neq '5' and $sub_data.lotterySeriesCode neq '3' and $sub_data.lotterySeriesCode neq '6' and $sub_data.lotterySeriesCode neq '8' and $sub_data.lotterySeriesCode neq '9' and $sub_data.lotteryId != '99113'} -->
													<tr>
														<td>
															<a class="label-like"  data-value="{$sub_data.threeoneRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][1]}{$aRateName[$sub_data.lotterySeriesCode][1]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																	<!-- {if $sub_data.threeoneRet eq $sub_data.maxThreeOneRet or $sub_data.threeoneRet gt $sub_data.maxThreeOneRet} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.threeoneRet}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.threeoneRet}-{$sub_data.maxThreeOneRet}"/>
																	<!-- {/if} -->
																<!-- {/if} -->
														</td>
													</tr>
													<!-- {/if} -->
													<!-- 六合彩 -->
													<!-- {if  $sub_data.lotteryId == '99701'} -->
													<tr>
														<td></td>
														<td >
															<a class="label-like"  data-value="{$sub_data.lhcFlatcode*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][0]}{$aRateName[$sub_data.lotterySeriesCode][0]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcFlatcode}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>


													<tr>
														<td></td>
														<td>
															<a class="label-like" data-value="{$sub_data.directRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][1]}{$aRateName[$sub_data.lotterySeriesCode][1]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}"/>
																<!-- {/if} -->
														</td>
													</tr>



													<tr>
														<td></td>
														<td>
															<a  class="label-like"  data-value="{$sub_data.lhcYear*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][2]}{$aRateName[$sub_data.lotterySeriesCode][2]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcYear}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													
													<tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcColor*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][3]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][3]}{$aRateName[$sub_data.lotterySeriesCode][3]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcColor}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcHalfwave*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][4]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][4]}{$aRateName[$sub_data.lotterySeriesCode][4]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcHalfwave}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcOneyear*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][5]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][5]}{$aRateName[$sub_data.lotterySeriesCode][5]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcOneyear}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td rowspan="3" style="width:100px">{$sub_data.awardName}：</td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcNotin*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][6]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][6]}{$aRateName[$sub_data.lotterySeriesCode][6]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcNotin}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuein23*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][7]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][7]}{$aRateName[$sub_data.lotterySeriesCode][7]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein23}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuein4*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][8]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][8]}{$aRateName[$sub_data.lotterySeriesCode][8]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein4}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuein5*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][9]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][9]}{$aRateName[$sub_data.lotterySeriesCode][9]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein5}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuenotin23*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][10]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][10]}{$aRateName[$sub_data.lotterySeriesCode][10]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin23}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuenotin4*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][11]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][11]}{$aRateName[$sub_data.lotterySeriesCode][11]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin4}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuenotin5*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][12]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][12]}{$aRateName[$sub_data.lotterySeriesCode][12]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin5}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													<td></td>
														<td>
															<a class="label-like"  data-value="{$sub_data.lhcContinuecode*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][13]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][13]}{$aRateName[$sub_data.lotterySeriesCode][13]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuecode}"/>
																	
																<!-- {/if} -->
														</td>
													</tr>
													
													
													<!-- {/if} -->
													<!-- 骰寶不定位 -->
													<!-- {if  $sub_data.lotteryId == '99601' or $sub_data.lotteryId == '99602' or $sub_data.lotteryId == '99603'} -->
													<tr>
														<td  >
															<a  class="label-like"  data-value="{$sub_data.sbThreeoneRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][2]}{$aRateName[$sub_data.lotterySeriesCode][2]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.sbThreeoneRet}"/>
																<!-- {/if} -->
														</td>
													</tr>
													
													<!-- {/if} -->
													
													<!-- {if $sub_data.lotteryId == '99101' or $sub_data.lotteryId == '99103' or $sub_data.lotteryId == '99104' or $sub_data.lotteryId == '99105' or $sub_data.lotteryId == '99113'} -->
													<tr>
														<td>
															<a class="label-like"  data-value="{$sub_data.superRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{if $aRateName[$sub_data.lotterySeriesCode][2]}{$aRateName[$sub_data.lotterySeriesCode][2]}{/if}</a>
																<!-- {if $smarty.session.datas.info.userLvl neq '-1'} -->
																	<!-- {if $sub_data.superRet eq $sub_data.maxSuperRet or $sub_data.superRet gt $sub_data.maxSuperRet} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.superRet}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.superRet}-{$sub_data.maxSuperRet}"/>
																	<!-- {/if} -->
																<!-- {/if} -->
														</td>
													</tr>
													<!-- {/if} -->
												</table>
											</dd>
										<!-- {/if} -->
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
						<script type="text/javascript" src="{$path_js}/js/userCenter/queryBonusDetails.js"></script>
					</div>
				</div>
			</div>
		</div>
	</div>
 </div>
	
	<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
		<i class="ico-error"></i><h4 class="pop-text"></h4>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
	<script  src="{$path_js}/js/phoenix.Common.js"></script>
	<script>
	var emptyRewards = {$emptyRewards};
	</script>
{literal}
<script type="text/javascript">

  (function($){	
	if(emptyRewards ==1){
		$("#divOperatFailure h4").html('请联系客服分配奖金组!');
		fn("divOperatFailure");
		setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
	}
	
	var isOpen = true;
	
	jQuery.ajax({
		type:  "get",
		url: '/pt/index/checkuserstatus',
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: '',
		cache: false,
		async : false,
		success:function(data){		
			if(data.status==1){
				isOpen = true;
				$('#_showPtTag').show();
			}else{
				$('#_showPtTag').hide();
			}
		},
		error: function(er){
			console.log(er);
		}
	});
						
	
		
	if(isOpen == true){
		$('#_showTag').show();
	}
	
  })(jQuery);
</script>
{/literal}
</body>
</html>
