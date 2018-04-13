<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{include file='/default/script-base.tpl'}
	<script>
	function CheckingMaxAllFull(obj,maxValue,miniValue){
		var me = obj,v = me.value,index,miniValue=$(obj).attr('minvalue')*1,maxValue=$(obj).attr('maxvalue')*1;
		me.value = v = v.replace(/^\.$/g, '');
		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
			
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}
		if(me.value < miniValue){ me.value=miniValue;}
		if(me.value > maxValue){ me.value=maxValue;}
			
		}
	</script>
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
				<div class="title">返点修改</div>
				<div class="content" style="padding:20px 30px;">
					<!-- ucenter-safe-bonus start -->
					<div class="rebate-list">
						 <div id="div-context" class="lottery-switcher">
                          	<div class="lottery-tabs">
                          		<!-- {foreach from=$aAwardSeris key=key item=data} -->
                          		<a href="javascript:void(0);">{$data}</a>
                          		<!-- {/foreach} -->
								<input type="hidden" id="userId" value="{$userId}"/>
								<input type="hidden" id="type" value="1"/>
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
										<!-- {if  $sub_data.lotteryId != '99701'} -->
											<tr>
												<td rowspan="3" style="width:140px">{$sub_data.awardName}：</td>
												<!-- {if  $sub_data.lotteryId != '99113'} -->
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.directLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][0]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][0]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_directLimitRet eq $sub_data.down_maxDirectRet or $sub_data.down_directLimitRet gt $sub_data.down_maxDirectRet} -->
													<input type="input" class="input w-2" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="{$sub_data.down_directLimitRet}" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}"{/if} maxvalue="{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}','{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}','{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}')"/>
													<span>(可分配返点范围为{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}-{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="{$sub_data.down_directLimitRet}-{$sub_data.down_maxDirectRet}" disabled/>
													<span>
													<a class="label-like" data-retstatus="1" data-value="{$sub_data.directLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
												<!-- {else} -->
															<td></td>
												<!-- {/if} -->
												<td rowspan="3"><input type="radio" class="select-prize-user" class="radio" {if $sub_value.status eq '1'} disabled {/if} {if $sub_data.status eq '1'} checked{/if} name="status_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}" value="{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/></td>
											</tr>
											<!-- {/if} -->
                                            <!-- {if $sub_data.lotterySeriesCode neq '5' and $sub_data.lotterySeriesCode neq '3' and $sub_data.lotterySeriesCode neq '6' and $sub_data.lotterySeriesCode neq '8' and $sub_data.lotterySeriesCode neq '9' and $sub_data.lotteryId != '99113'} -->
											<tr>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.threeLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][1]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][1]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_threeLimitRet eq $sub_data.down_maxThreeOneRet or $sub_data.down_threeLimitRet gt $sub_data.down_maxThreeOneRet} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.threeLimitRet>0}{$sub_data.threeLimitRet}{else}0{/if}','{if $sub_data.down_threeLimitRet>0}{$sub_data.down_threeLimitRet}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.threeLimitRet>0}{$sub_data.threeLimitRet}{else}0{/if}','{if $sub_data.down_threeLimitRet>0}{$sub_data.down_threeLimitRet}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_threeLimitRet>0}{$sub_data.down_threeLimitRet}{else}0{/if}"{/if} maxvalue="{if $sub_data.threeLimitRet>=0}{$sub_data.threeLimitRet}{else}0{/if}" value="{$sub_data.down_threeLimitRet}" name="threeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_threeLimitRet>0}{$sub_data.down_threeLimitRet}{else}0{/if}-{if $sub_data.threeLimitRet>0}{$sub_data.threeLimitRet}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_threeLimitRet}-{$sub_data.down_maxThreeOneRet}" disabled name="threeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.threeLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>
											<!-- {/if} -->
											
											<!-- {if $sub_data.lotteryId == '99701'} -->


											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcFlatcodeLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][0]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][0]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcFlatcodeLimit eq $sub_data.down_maxLhcFlatcode or $sub_data.down_lhcFlatcodeLimit gt $sub_data.down_maxLhcFlatcode} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcFlatcodeLimit>0}{$sub_data.lhcFlatcodeLimit}{else}0{/if}','{if $sub_data.down_lhcFlatcodeLimit>0}{$sub_data.down_lhcFlatcodeLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcFlatcodeLimit>0}{$sub_data.lhcFlatcodeLimit}{else}0{/if}','{if $sub_data.down_lhcFlatcodeLimit>0}{$sub_data.down_lhcFlatcodeLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcFlatcodeLimit>0}{$sub_data.down_lhcFlatcodeLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcFlatcodeLimit>=0}{$sub_data.lhcFlatcodeLimit}{else}0{/if}" value="{$sub_data.down_lhcFlatcodeLimit}" name="lhcFlatcode_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcFlatcodeLimit>0}{$sub_data.down_lhcFlatcodeLimit}{else}0{/if}-{if $sub_data.lhcFlatcodeLimit>0}{$sub_data.lhcFlatcodeLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcFlatcodeLimit}-{$sub_data.down_maxLhcFlatcode}" disabled name="lhcFlatcode_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcFlatcodeLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>


											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.directLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][1]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][1]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_directLimitRet eq $sub_data.down_maxDirectRet or $sub_data.down_directLimitRet gt $sub_data.down_maxDirectRet} -->
													<input type="input" class="input w-2" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="{$sub_data.down_directLimitRet}" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}"{/if} maxvalue="{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}','{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if}','{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}')"/>
													<span>(可分配返点范围为{if $sub_data.down_directLimitRet>0}{$sub_data.down_directLimitRet}{else}0{/if}-{if $sub_data.directLimitRet>0}{$sub_data.directLimitRet}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="{$sub_data.down_directLimitRet}-{$sub_data.down_maxDirectRet}" disabled/>
													<span>
													<a class="label-like" data-retstatus="1" data-value="{$sub_data.directLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
												
											</tr>



											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcYearLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcYearLimit eq $sub_data.down_maxLhcYear or $sub_data.down_lhcYearLimit gt $sub_data.down_maxLhcYear} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcYearLimit>0}{$sub_data.lhcYearLimit}{else}0{/if}','{if $sub_data.down_lhcYearLimit>0}{$sub_data.down_lhcYearLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcYearLimit>0}{$sub_data.lhcYearLimit}{else}0{/if}','{if $sub_data.down_lhcYearLimit>0}{$sub_data.down_lhcYearLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcYearLimit>0}{$sub_data.down_lhcYearLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcYearLimit>=0}{$sub_data.lhcYearLimit}{else}0{/if}" value="{$sub_data.down_lhcYearLimit}" name="lhcYear_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcYearLimit>0}{$sub_data.down_lhcYearLimit}{else}0{/if}-{if $sub_data.lhcYearLimit>0}{$sub_data.lhcYearLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcYearLimit}-{$sub_data.down_maxLhcYear}" disabled name="lhcYear_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcYearLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcColorLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][3]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][3]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][3]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcColorLimit eq $sub_data.down_maxLhcColor or $sub_data.down_lhcColorLimit gt $sub_data.down_maxLhcColor} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcColorLimit>0}{$sub_data.lhcColorLimit}{else}0{/if}','{if $sub_data.down_lhcColorLimit>0}{$sub_data.down_lhcColorLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcColorLimit>0}{$sub_data.lhcColorLimit}{else}0{/if}','{if $sub_data.down_lhcColorLimit>0}{$sub_data.down_lhcColorLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcColorLimit>0}{$sub_data.down_lhcColorLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcColorLimit>=0}{$sub_data.lhcColorLimit}{else}0{/if}" value="{$sub_data.down_lhcColorLimit}" name="lhcColor_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcColorLimit>0}{$sub_data.down_lhcColorLimit}{else}0{/if}-{if $sub_data.lhcColorLimit>0}{$sub_data.lhcColorLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcColorLimit}-{$sub_data.down_maxLhcColor}" disabled name="lhcColor_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcColorLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][3]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>
											
											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcHalfwaveLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][4]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][4]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][4]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcHalfwaveLimit eq $sub_data.down_maxLhcHalfwave or $sub_data.down_lhcHalfwaveLimit gt $sub_data.down_maxLhcHalfwave} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcHalfwaveLimit>0}{$sub_data.lhcHalfwaveLimit}{else}0{/if}','{if $sub_data.down_lhcHalfwaveLimit>0}{$sub_data.down_lhcHalfwaveLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcHalfwaveLimit>0}{$sub_data.lhcHalfwaveLimit}{else}0{/if}','{if $sub_data.down_lhcHalfwaveLimit>0}{$sub_data.down_lhcHalfwaveLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcHalfwaveLimit>0}{$sub_data.down_lhcHalfwaveLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcHalfwaveLimit>=0}{$sub_data.lhcHalfwaveLimit}{else}0{/if}" value="{$sub_data.down_lhcHalfwaveLimit}" name="lhcHalfwave_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcHalfwaveLimit>0}{$sub_data.down_lhcHalfwaveLimit}{else}0{/if}-{if $sub_data.lhcHalfwaveLimit>0}{$sub_data.lhcHalfwaveLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcHalfwaveLimit}-{$sub_data.down_maxLhcHalfwave}" disabled name="lhcHalfwave_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcHalfwaveLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][4]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcOneyearLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][5]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][5]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][5]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcOneyearLimit eq $sub_data.down_maxLhcOneyear or $sub_data.down_lhcOneyearLimit gt $sub_data.down_maxLhcOneyear} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcOneyearLimit>0}{$sub_data.lhcOneyearLimit}{else}0{/if}','{if $sub_data.down_lhcOneyearLimit>0}{$sub_data.down_lhcOneyearLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcOneyearLimit>0}{$sub_data.lhcOneyearLimit}{else}0{/if}','{if $sub_data.down_lhcOneyearLimit>0}{$sub_data.down_lhcOneyearLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcOneyearLimit>0}{$sub_data.down_lhcOneyearLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcOneyearLimit>=0}{$sub_data.lhcOneyearLimit}{else}0{/if}" value="{$sub_data.down_lhcOneyearLimit}" name="lhcOneyear_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcOneyearLimit>0}{$sub_data.down_lhcOneyearLimit}{else}0{/if}-{if $sub_data.lhcOneyearLimit>0}{$sub_data.lhcOneyearLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcOneyearLimit}-{$sub_data.down_maxLhcOneyear}" disabled name="lhcOneyear_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcOneyearLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][5]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td rowspan="3" style="width:140px">{$sub_data.awardName}：</td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcNotinLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][6]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][6]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][6]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcNotinLimit eq $sub_data.down_maxLhcNotin or $sub_data.down_lhcNotinLimit gt $sub_data.down_maxLhcNotin} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcNotinLimit>0}{$sub_data.lhcNotinLimit}{else}0{/if}','{if $sub_data.down_lhcNotinLimit>0}{$sub_data.down_lhcNotinLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcNotinLimit>0}{$sub_data.lhcNotinLimit}{else}0{/if}','{if $sub_data.down_lhcNotinLimit>0}{$sub_data.down_lhcNotinLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcNotinLimit>0}{$sub_data.down_lhcNotinLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcNotinLimit>=0}{$sub_data.lhcNotinLimit}{else}0{/if}" value="{$sub_data.down_lhcNotinLimit}" name="lhcNotin_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcNotinLimit>0}{$sub_data.down_lhcNotinLimit}{else}0{/if}-{if $sub_data.lhcNotinLimit>0}{$sub_data.lhcNotinLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcNotinLimit}-{$sub_data.down_maxLhcNotin}" disabled name="lhcNotin_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcNotinLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][6]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
												<td rowspan="3"><input type="radio" class="select-prize-user" class="radio" {if $sub_value.status eq '1'} disabled {/if} {if $sub_data.status eq '1'} checked{/if} name="status_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}" value="{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/></td>
											</tr>

											<tr>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuein23Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][7]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][7]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][7]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuein23Limit eq $sub_data.down_maxLhcContinuein23 or $sub_data.down_lhcContinuein23Limit gt $sub_data.down_maxLhcContinuein23} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein23Limit>0}{$sub_data.lhcContinuein23Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein23Limit>0}{$sub_data.down_lhcContinuein23Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein23Limit>0}{$sub_data.lhcContinuein23Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein23Limit>0}{$sub_data.down_lhcContinuein23Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuein23Limit>0}{$sub_data.down_lhcContinuein23Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuein23Limit>=0}{$sub_data.lhcContinuein23Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuein23Limit}" name="lhcContinuein23_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuein23Limit>0}{$sub_data.down_lhcContinuein23Limit}{else}0{/if}-{if $sub_data.lhcContinuein23Limit>0}{$sub_data.lhcContinuein23Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuein23Limit}-{$sub_data.down_maxLhcContinuein23}" disabled name="lhcContinuein23_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuein23Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][7]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuein4Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][8]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][8]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][8]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuein4Limit eq $sub_data.down_maxLhcContinuein4 or $sub_data.down_lhcContinuein4Limit gt $sub_data.down_maxLhcContinuein4} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein4Limit>0}{$sub_data.lhcContinuein4Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein4Limit>0}{$sub_data.down_lhcContinuein4Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein4Limit>0}{$sub_data.lhcContinuein4Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein4Limit>0}{$sub_data.down_lhcContinuein4Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuein4Limit>0}{$sub_data.down_lhcContinuein4Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuein4Limit>=0}{$sub_data.lhcContinuein4Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuein4Limit}" name="lhcContinuein4_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuein4Limit>0}{$sub_data.down_lhcContinuein4Limit}{else}0{/if}-{if $sub_data.lhcContinuein4Limit>0}{$sub_data.lhcContinuein4Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuein4Limit}-{$sub_data.down_maxLhcContinuein4}" disabled name="lhcContinuein4_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuein4Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][8]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuein5Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][9]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][9]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][9]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuein5Limit eq $sub_data.down_maxLhcContinuein5 or $sub_data.down_lhcContinuein5Limit gt $sub_data.down_maxLhcContinuein5} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein5Limit>0}{$sub_data.lhcContinuein5Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein5Limit>0}{$sub_data.down_lhcContinuein5Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuein5Limit>0}{$sub_data.lhcContinuein5Limit}{else}0{/if}','{if $sub_data.down_lhcContinuein5Limit>0}{$sub_data.down_lhcContinuein5Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuein5Limit>0}{$sub_data.down_lhcContinuein5Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuein5Limit>=0}{$sub_data.lhcContinuein5Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuein5Limit}" name="lhcContinuein5_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuein5Limit>0}{$sub_data.down_lhcContinuein5Limit}{else}0{/if}-{if $sub_data.lhcContinuein5Limit>0}{$sub_data.lhcContinuein5Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuein5Limit}-{$sub_data.down_maxLhcContinuein5}" disabled name="lhcContinuein5_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuein5Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][9]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuenotin23Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][10]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][10]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][10]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuenotin23Limit eq $sub_data.down_maxLhcContinuenotin23 or $sub_data.down_lhcContinuenotin23Limit gt $sub_data.down_maxLhcContinuenotin23} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin23Limit>0}{$sub_data.lhcContinuenotin23Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin23Limit>0}{$sub_data.down_lhcContinuenotin23Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin23Limit>0}{$sub_data.lhcContinuenotin23Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin23Limit>0}{$sub_data.down_lhcContinuenotin23Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuenotin23Limit>0}{$sub_data.down_lhcContinuenotin23Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuenotin23Limit>=0}{$sub_data.lhcContinuenotin23Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuenotin23Limit}" name="lhcContinuenotin23_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuenotin23Limit>0}{$sub_data.down_lhcContinuenotin23Limit}{else}0{/if}-{if $sub_data.lhcContinuenotin23Limit>0}{$sub_data.lhcContinuenotin23Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuenotin23Limit}-{$sub_data.down_maxLhcContinuenotin23}" disabled name="lhcContinuenotin23_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuenotin23Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][12]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuenotin4Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][11]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][11]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][11]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuenotin4Limit eq $sub_data.down_maxLhcContinuenotin4 or $sub_data.down_lhcContinuenotin4Limit gt $sub_data.down_maxLhcContinuenotin4} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin4Limit>0}{$sub_data.lhcContinuenotin4Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin4Limit>0}{$sub_data.down_lhcContinuenotin4Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin4Limit>0}{$sub_data.lhcContinuenotin4Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin4Limit>0}{$sub_data.down_lhcContinuenotin4Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuenotin4Limit>0}{$sub_data.down_lhcContinuenotin4Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuenotin4Limit>=0}{$sub_data.lhcContinuenotin4Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuenotin4Limit}" name="lhcContinuenotin4_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuenotin4Limit>0}{$sub_data.down_lhcContinuenotin4Limit}{else}0{/if}-{if $sub_data.lhcContinuenotin4Limit>0}{$sub_data.lhcContinuenotin4Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuenotin4Limit}-{$sub_data.down_maxLhcContinuenotin4}" disabled name="lhcContinuenotin4_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuenotin4Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][11]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuenotin5Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][12]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][12]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][12]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuenotin5Limit eq $sub_data.down_maxLhcContinuenotin5 or $sub_data.down_lhcContinuenotin5Limit gt $sub_data.down_maxLhcContinuenotin5} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin5Limit>0}{$sub_data.lhcContinuenotin5Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin5Limit>0}{$sub_data.down_lhcContinuenotin5Limit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuenotin5Limit>0}{$sub_data.lhcContinuenotin5Limit}{else}0{/if}','{if $sub_data.down_lhcContinuenotin5Limit>0}{$sub_data.down_lhcContinuenotin5Limit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuenotin5Limit>0}{$sub_data.down_lhcContinuenotin5Limit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuenotin5Limit>=0}{$sub_data.lhcContinuenotin5Limit}{else}0{/if}" value="{$sub_data.down_lhcContinuenotin5Limit}" name="lhcContinuenotin5_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuenotin5Limit>0}{$sub_data.down_lhcContinuenotin5Limit}{else}0{/if}-{if $sub_data.lhcContinuenotin5Limit>0}{$sub_data.lhcContinuenotin5Limit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuenotin5Limit}-{$sub_data.down_maxLhcContinuenotin5}" disabled name="lhcContinuenotin5_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuenotin5Limit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][12]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											<tr><td></td>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.lhcContinuecodeLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][13]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][13]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][13]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_lhcContinuecodeLimit eq $sub_data.down_maxLhcContinuecode or $sub_data.down_lhcContinuecodeLimit gt $sub_data.down_maxLhcContinuecode} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuecodeLimit>0}{$sub_data.lhcContinuecodeLimit}{else}0{/if}','{if $sub_data.down_lhcContinuecodeLimit>0}{$sub_data.down_lhcContinuecodeLimit}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.lhcContinuecodeLimit>0}{$sub_data.lhcContinuecodeLimit}{else}0{/if}','{if $sub_data.down_lhcContinuecodeLimit>0}{$sub_data.down_lhcContinuecodeLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_lhcContinuecodeLimit>0}{$sub_data.down_lhcContinuecodeLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.lhcContinuecodeLimit>=0}{$sub_data.lhcContinuecodeLimit}{else}0{/if}" value="{$sub_data.down_lhcContinuecodeLimit}" name="lhcContinuecode_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_lhcContinuecodeLimit>0}{$sub_data.down_lhcContinuecodeLimit}{else}0{/if}-{if $sub_data.lhcContinuecodeLimit>0}{$sub_data.lhcContinuecodeLimit}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_lhcContinuecodeLimit}-{$sub_data.down_maxLhcContinuecode}" disabled name="lhcContinuecode_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="2" data-value="{$sub_data.lhcContinuecodeLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][13]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
												<!-- {/if} -->
												</td>
											</tr>

											
											<!-- {/if} -->
											
											<!-- {if $sub_data.lotteryId == '99601' or $sub_data.lotteryId == '99602' or $sub_data.lotteryId == '99603'} 骰寶不定位 -->
														<tr>
															<td style="text-align:left;">
															<!-- {if $sub_data.status eq '1'} --> 
																<a class="label-like" style="width:80px;"  data-value="{$sub_data.sbThreeoneRetLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
															<!-- {else} -->
																<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
															<!-- {/if} -->
															<!-- {if $sub_data.down_sbThreeoneRetLimit eq $sub_data.down_maxSbThreeoneRet or $sub_data.down_sbThreeoneRetLimit gt $sub_data.down_maxSbThreeoneRetLimit} -->
																<input type="input"  onBlur="CheckingMaxAllFull(this,'{if $sub_data.sbThreeoneRetLimit>=0}{$sub_data.sbThreeoneRetLimit}{else}0{/if}','{if $sub_data.sbThreeoneRetLimit>0}{$sub_data.sbThreeoneRetLimit}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_sbThreeoneRetLimit>0}{$sub_data.down_sbThreeoneRetLimit}{else}0{/if}"{/if} maxvalue="{if $sub_data.sbThreeoneRetLimit>0}{$sub_data.sbThreeoneRetLimit}{else}0{/if}" value="{$sub_data.down_sbThreeoneRetLimit}" name="sbThreeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为{if $sub_data.down_sbThreeoneRetLimit>0}{$sub_data.down_sbThreeoneRetLimit}{else}0{/if}-{if $sub_data.sbThreeoneRetLimit>0}{$sub_data.sbThreeoneRetLimit}{else}0{/if})</span>
															<!-- {else} -->
																<input type="input" class="input w-2"  value="{$sub_data.down_sbThreeoneRetLimit}-{$sub_data.down_maxSbThreeoneRetLimit}" disabled name="sbThreeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>
																<a class="label-like" data-retstatus="2"  data-value="{$sub_data.sbThreeoneRetLimit*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
																</span>
															<!-- {/if} -->
															</td>
														</tr>
														<!-- {/if} -->
											
											<!-- {if $sub_data.lotteryId == '99101' or $sub_data.lotteryId == '99103' or $sub_data.lotteryId == '99104' or $sub_data.lotteryId == '99105' or $sub_data.lotteryId == '99113'} -->
											<tr>
												<td style="text-align:left;">
												<!-- {if $sub_data.status eq '1'}  -->
													<a class="label-like" style="width:80px;" data-value="{$sub_data.superLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
												<!-- {else} -->
													<span class="label-like" style="width:80px;">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
												<!-- {/if} -->
												<!-- {if $sub_data.down_superLimitRet eq $sub_data.down_maxThreeOneRet or $sub_data.down_superLimitRet gt $sub_data.down_maxThreeOneRet} -->
													<input type="input" onKeyUp="CheckingMaxAllFull(this,'{if $sub_data.superLimitRet>0}{$sub_data.superLimitRet}{else}0{/if}','{if $sub_data.down_superLimitRet>0}{$sub_data.down_superLimitRet}{else}0{/if}')" onBlur="CheckingMaxAllFull(this,'{if $sub_data.superLimitRet>0}{$sub_data.superLimitRet}{else}0{/if}','{if $sub_data.down_superLimitRet>0}{$sub_data.down_superLimitRet}{else}0{/if}')" class="input w-2" {if $sub_data.status eq '1'} minvalue="{if $sub_data.down_superLimitRet>0}{$sub_data.down_superLimitRet}{else}0{/if}"{/if} maxvalue="{if $sub_data.superLimitRet>=0}{$sub_data.superLimitRet}{else}0{/if}" value="{$sub_data.down_superLimitRet}" name="superRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
													<span>(可分配返点范围为{if $sub_data.down_superLimitRet>0}{$sub_data.down_superLimitRet}{else}0{/if}-{if $sub_data.superLimitRet>0}{$sub_data.superLimitRet}{else}0{/if})</span>
												<!-- {else} -->
													<input type="input" class="input w-2"  value="{$sub_data.down_superLimitRet}-{$sub_data.down_maxThreeOneRet}" disabled name="superRet_{$sub_data.lotterySeriesCode}_{$sub_data.awardGroupId}"/>
													<span>
													<a class="label-like" data-retstatus="1" data-value="{$sub_data.superLimitRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}" data-lotterySeriesCode="{$sub_data.lotterySeriesCode}"  data-modifyprize="1" >点击修改</a>
													</span>
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
						</div>
						<script type="text/javascript" src="{$path_js}/js/userCenter/queryBonusDetails.js"></script>
						<div class="rebate-btn">
							<input type="button" value="修改返点"  id="J-btn-Subm" class="btn btn-important">
						</div>
					</div>
					<!-- ucenter-safe-bonus end -->
				</div>
			</div>
		</div>
	</div>
	<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divNoType">
		<div class="hd"><i class="close" name="divCloseUrl"></i>提示</div>
		<div class="bd">
			<h4 class="pop-title">每个彩种，至少选择一个奖金组</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn btn-important " name="J-but-close">确 定<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divIsEmpty">
		<div class="hd"><i class="close" name="divCloseUrl"></i><span>提示</span></div>
		<div class="bd">
			<h4 class="pop-title">返点设置不能为空，请重新设置</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn btn-important " name="J-but-close">确 定<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	<div class="pop w-7" style="position:absolute;left:500px;display:none;" id="changeToProxy">
		<div class="hd"><i class="close" name="divCloseUrl"></i>温馨提示</div>
		<div class="bd">
			<h4 class="pop-title">您确定要把当前会员升级为代理吗？</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn btn-important " name="J-but-changeType">确 定<b class="btn-inner"></b></a>
				<a href="javascript:void(0);" class="btn" name="J-but-close">取 消<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
	
	<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none" id="divOperatSuccess">
		<i class="ico-success"></i><h4 class="pop-text">修改成功</h4>
	</div>
	<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
		<i class="ico-error"></i><h4 class="pop-text">修改失败，请重试</h4>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script>
var emptyRewards = {$emptyRewards};
</script>
{literal}
<script type="text/javascript">

  (function($){	
	var inputs = $('.table').find('[type="input"]'),addfile,box = new LightBox("divIsEmpty"),box2=new LightBox("divOperatSuccess"),box3=new LightBox("changeToProxy"),box4=new LightBox("divNoType");	
	$(".more-link").css("display","none");
	if(emptyRewards ==1){
		$("#divOperatFailure h4").html('请联系代理或客服分配奖金组!');
		fn("divOperatFailure");
		setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
	}
	

    function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
    } 	
	
	function divConfig(obj){	
		$('#'+obj).css("display","none");
    }	
	
	function GetPostText()
	{
		var sText="";
		var sPost="";
		//var type =$("#hiddenType").val();
		var userId =$("#userId").val();
		var inputType =":radio:checked";
		/* if(type =='1'){
			inputType = ":checkbox:checked";
		} */
		$(inputType).each(function(index, element) {
			var sname=$(this).attr("name");
			var svalue=$(this).val();
			sPost+="status_"+svalue+"=1&";
			$(this).parent().parent().parent().find("input").each(function(index, element) {
                if($(this).is("input") && $(this).attr("type")=="input")
				{
				     sText=$(this).attr("name")+"="+$(this).val()+"&";
					 sPost+=sText;
				}
            });
        });
		sPost += 'id='+userId;
		return sPost;
	}

	$("#userAgent").click(function(){
		fn("changeToProxy");
		box3.Over = true;
		box3.OverLay.Color = "rgb(0, 0, 0)" ;
		box3.OverLay.Opacity = 50;  
		box3.Fixed = true;	 
		box3.Center = true; 	
		box3.Show();
	});
	$("#userPlayer").click(function(){
		$("#userAgent").removeClass("ico-tab ico-tab-current").addClass("ico-tab");
		$("#userPlayer").removeClass("ico-tab").addClass("ico-tab ico-tab-current");
		$(".select-prize-proxy").each(function(){
			$(this).hide();
		});
		$(".select-prize-user").each(function(){
			$(this).show();
		});
		$("#hiddenType").val('0');
	});
	
	function GetPostValueText(id)
	{
		var sName=id+" *";
		var sText="";
		var sPost="";
		$(sName).each(function(index, element) {
			 if(($(this).is("input") && $(this).attr("type")=="input") || $(this).attr("type")=="hidden")
			 {
				 var v=$(this).val();
				 sText=$(this).attr("name")+"="+$.trim(v)+"&";
				 sPost+=sText;
			 }
        });
		return sPost;
	}
	$("[name='J-but-changeType']").bind("click",function(){
		box3.Close();
		$("#userPlayer").removeClass("ico-tab ico-tab-current").addClass("ico-tab");
		$("#userAgent").removeClass("ico-tab").addClass("ico-tab ico-tab-current");
		$(".select-prize-user").each(function(){
			$(this).hide();
		});
		$(".select-prize-proxy").each(function(){
			$(this).show();
		});
		$("#hiddenType").val('1');
	});
    $("[name='divCloseUrl']").bind("click",function(){
		box.Close();
		box3.Close();
		box4.Close();
	});
	$("[name='J-but-close']").click(function (){
		box.Close();
		box3.Close();
		box4.Close();
	});	
	  
	$("[name='divCloseUrl']").click(function(){
		box.Close();
		box2.Close();
		box4.Close();
	});
		//提交数据
	$('#J-btn-Subm').click(function (e){	
		var istrue=true,txtName;
		//var type =$("#hiddenType").val();
		var inputType =":radio:checked";
		/* if(type =='1'){
			inputType = ":checkbox:checked";
		} */
		$(".item").each(function(index, element) {
	        if($(this).find(inputType).length==0) {
				fn("divNoType");
				box4.Over = true;
				box4.OverLay.Color = "rgb(0, 0, 0)" ;
				box4.OverLay.Opacity = 50;  
				box4.Fixed = true;	 
				box4.Center = true; 	
				box4.Show();
				istrue=false;
				return false;
			} else {
				$(this).find(inputType).each(function(index, element) {
	                      $(this).parent().parent().parent().find("input").each(function(index, element) {
	                          var oinput=$(this);
						//oinput.css("border", "0"); 			
						if($(this).is("input") && $(this).attr("type")=="input" && oinput.val()==""){						
							oinput.css("border", "solid 1px red"); 
							//oinput.focus();
							//提示窗口不能为空
							fn("divIsEmpty");
							box.Over = true;
							box.OverLay.Color = "rgb(0, 0, 0)" ;
							box.OverLay.Opacity = 50;  
							box.Fixed = true;	 
							box.Center = true; 	
							box.Show(); 	
							e.preventDefault();
							istrue=false;
							return false;		
						}
	                      });
	                  });
			}
	     });
	
		if(istrue==true){		
		    //var stype=$("#hiddenType").val();
		    var sdata=GetPostText();
			//var shidden="type="+$("#hiddenType").val()+"&userId="+$("#userId").val();
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/applycenter/modifyprize/',
				//按格式传入需要参数
				data:sdata,	
				beforeSend:function(){
					isLoading = true;
					//禁用发送								
					var button = $('#J-btn-Subm'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
						interval=setInterval(function(){
							$('#J-btn-Subm').val(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
					$("#J-btn-Subm").attr("disabled","disabled");
				},
				//返回生成链接数,各种提示						
				success:function(data){				
					if(data["status"]=="ok"){	
						fn("divOperatSuccess");
						box2.Over = true;
						box2.OverLay.Color = "rgb(0, 0, 0)" ;
						box2.OverLay.Opacity = 50;  
						box2.Fixed = true;	 
						box2.Center = true; 	
						box2.Show(); 
						setTimeout(function(){box2.Close();$('#divOperatSuccess').css("display","none");window.location.reload();},3000);
					$('#J-but-close').click(function (){
						$('#divIsEmpty').css("display","none");
						box.Close();
					});		
					}
					else{
						//失败提示后隐藏
						$("#divOperatFailure h4").html(data['data']);
						fn("divOperatFailure");
						setTimeout(
							function(){
								$('#divOperatFailure').css("display","none");
								if(data["status"]=="error1"){
									window.location.reload();
								}
							}
						,3000);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$("#divOperatFailure h4").html('网络超时,请重新设置!');
					fn("divOperatFailure");
						 setTimeout(function(){$('#divOperatFailure').css("display","none");
						 if(textStatus == 'parsererror'){
							 window.location.reload();
		                    }
						 },3000);
				},
				complete:function(){
					isLoading = false;
					clearInterval(interval);
					$('#J-btn-Subm').val("修改返点");						 	
					$("#J-btn-Subm").removeAttr("disabled","disabled");
				}
			});
		}		
	});	
    
})(jQuery);
</script>
{/literal}
</body>
</html>