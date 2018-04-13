<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>开户中心</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
<link rel="stylesheet" href="{$path_img}/images/common/centerapply.css" />
	
	{include file='/default/script-base.tpl'}
	<script  src="{$path_js}/js/phoenix.MaxAllFull.js"></script>
	<script>
	function CheckingMaxAllFullrate(obj,maxAllfull){
		//console.log(obj.value);
		
		var me = obj,v = me.value,index;
		if($.trim(v)=='') return false;
		me.value = v = v.replace(/^\.$/g, '');
		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
		}
		 me.value = v = v.replace(/[^\d|^\.]/g, '');
		 me.value = v = v.replace(/^00/g, '0');
	 
	   me.value = v = v.replace( /([0-9]+.[0-9]{1})[0-9]*/ ,"$1"); 
		if(v.split('.').length > 2){
			
			arguments.callee.call(me);
		}
		if(me.value >= 10){ me.value=me.value.substr(0,1)}
		//if(me.value > (maxAllfull*1)){ me.value=(maxAllfull*1)}
		if(me.value.split(".")[1] > 9){ me.value=me.value.split(".")[0] + "." + me.value.split(".")[1].substr(0,1)}
		//console.log(me.value.split(".")[0] + '.' + me.value.split(".")[1].substr(0,1));
		//console.log(me.value.split(".")[0] + '.' + me.value.split(".")[1].substr(0,1));
		
		
		if(me.value != 0 && me.value < 0.5) { me.value=0.5}
		//console.log(me.value);
			
	};
	</script>
	
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
								<li class="current">链接开户</li>
								<li><a href="/applycenter/managerurl/">链接管理</a></li>
							</ul>
						</div>
						<div class="">
							<!-- start -->
							<div class="rebate-list">
								<ul class="rebate-list-setup rebate-list-ico">
									<li>
										<span class="text">开户类型：</span>
										<span class="ico-tab ico-tab-current" id="userAgent">代理</span>
										<span class="ico-tab" id="userPlayer">玩家</span>
										<input type="hidden" id="hiddenType" name="type" value="1"/>
									</li>
									<li>
										<span class="text">链接有效期：</span>
										<select name="days" class="ui-select  w-2" >
											<option value="1">1天</option>
											<option value="5">5天</option>
											<option value="10">10天</option>
											<option value="30">30天</option>
											<option value="-1" selected>永久有效</option>
										</select>
									</li>
									<li>
										<span class="text">联系QQ：</span>
										<input id="J-qq" type="text" class="input w-2" value= "{$qq}" data-old-value='{$qq}' name="qq" maxlength="10" onblur="check_qq()"/>
										<input type="hidden"  name="hqq" id="hiddenqq" value=""/>
										<span id ="qqword" class="form-tip-error" >（方便下级联系您，请慎重填写）</span>
										<span id ="check_right" class="ui-check-right" style="display:none"></span>
										<span id="check_error" class="form-tip-error" style="display:none;color:red;"></span>
									</li>
									<!-- <li>
										<span class="text">生成链接数：</span>
										<input type="input" class="input w-2" name="urlCnt" />
										<span>每次最多可生成15个开户链接</span>
									</li> -->
									<li>
										<span class="text">奖金组：</span>
										<span>
											<input type="button" value="最高" id="returnHight" class="btn">
											<input type="button" value="全选" id="returnAll" class="btn">
										</span>
									</li>
									<li>
										<span class="text">返点设置：</span>
										
										<input id="J-rate" type="text" class="input w-2" value= "返点差" name="rate" />
										<input type="button" value="确定" id="rateSetup" class="btn btn-important">
										<span class="more-link">
											<input type="button" value="返点全满" id="returnAllFull" class="btn">
											<input type="button" value="返点清零" id="returnZero" class="btn">
										</span>
                                        <input type="hidden"  name="SetUp" id="hiddenSetUp" value="1"/>
										<input type="hidden"  name="diffValue" id="hiddendiffValue" value="{$diffvalue}"/>
										<input type="hidden"  name="value200" id="hiddenvalue200" value="{$value200}"/>
										<input type="hidden"  name="wgvalue" id="hiddenwgvalue" value="{$wgvalue}"/>
										<input type="hidden"  name="ldc2" id="hiddenldc2" value="{$ldc2}"/>
										<input type="hidden"  name="ldc1" id="hiddenldc1" value="{$ldc1}"/>
										<input type="hidden" id="userlevel" value="{$smarty.session.datas.info.userLvl}" >
										
										
									</li>
                                    <li id="li-Message" style="display:none">
										<dl class="prompt">
											<dt>温馨提示：</dt>
											<dd>若使用快捷设置，下级代理将继承你的所有彩种和奖金组，并将获得统一返点。</dd>
										</dl>
									</li>
                                    <li id="li-QuickSetup" style="display:none">
										<span class="text">&nbsp;</span>
                                        <input type="input" class="input" id="Rebate" name="Rebate" onKeyUp="CheckingMaxAllFull(this,{$maxValue});" onBlur="CheckingMaxAllFull(this,{$maxValue});" />
										<span>（可分配返点范围为0-{$maxValue}）</span>
									</li>
								</ul>
								
                                <div id="div-context" class="lottery-switcher">
                                	<div class="lottery-tabs">
                                		<!-- {foreach from=$aAwardSeris key=key item=data} -->
                                		<a href="javascript:void(0);">{$data}</a>
                                		<!-- {/foreach} -->
                                	</div>

                                	<!-- 时时彩系 -->
                                	<!-- {foreach from=$result key=key item=data} -->
                                	<div class="lottery-switch">
                                		<!-- {foreach from=$aAwardGroup[$key] key=sub_key item=sub_value} -->
                                		<!-- {{assign var="sub_value" value=$result[$key][$sub_key]}} -->
                                		
                                		<dl class="item">
                                		
											<dt>{$aAwardGroup[$key][$sub_key]}</dt>
											<!--
											{foreach from=$sub_value key=sub_key1 item=sub_data}
												
												{assign var=directLimitRet value=0}
												{if $sub_data.directLimitRet>0}{assign var=directLimitRet value=$sub_data.directLimitRet}{/if}
												
												{assign var=threeLimitRet value=0}
												{if $sub_data.threeLimitRet>0}{assign var=threeLimitRet value=$sub_data.threeLimitRet}{/if}
												
												{assign var=superLimitRet value=0}
												{if $sub_data.superLimitRet>0}{assign var=superLimitRet value=$sub_data.superLimitRet}{/if}
												
												{assign var=lhcYearLimit value=0}
												{if $sub_data.lhcYearLimit>0}{assign var=lhcYearLimit value=$sub_data.lhcYearLimit}{/if}
												
												{assign var=lhcColorLimit value=0}
												{if $sub_data.lhcColorLimit>0}{assign var=lhcColorLimit value=$sub_data.lhcColorLimit}{/if}
												
												{assign var=sbThreeoneRetLimit value=0}
												{if $sub_data.sbThreeoneRetLimit>0}{assign var=sbThreeoneRetLimit value=$sub_data.sbThreeoneRetLimit}{/if}
												
												{assign var=lhcFlatcodeLimit value=0}
												{if $sub_data.lhcFlatcodeLimit>0}{assign var=lhcFlatcodeLimit value=$sub_data.lhcFlatcodeLimit}{/if}

												{assign var=lhcHalfwaveLimit value=0}
												{if $sub_data.lhcHalfwaveLimit>0}{assign var=lhcHalfwaveLimit value=$sub_data.lhcHalfwaveLimit}{/if}

												{assign var=lhcOneyearLimit value=0}
												{if $sub_data.lhcOneyearLimit>0}{assign var=lhcOneyearLimit value=$sub_data.lhcOneyearLimit}{/if}

												{assign var=lhcNotinLimit value=0}
												{if $sub_data.lhcNotinLimit>0}{assign var=lhcNotinLimit value=$sub_data.lhcNotinLimit}{/if}

												{assign var=lhcContinuein23Limit value=0}
												{if $sub_data.lhcContinuein23Limit>0}{assign var=lhcContinuein23Limit value=$sub_data.lhcContinuein23Limit}{/if}

												{assign var=lhcContinuein4Limit value=0}
												{if $sub_data.lhcContinuein4Limit>0}{assign var=lhcContinuein4Limit value=$sub_data.lhcContinuein4Limit}{/if}

												{assign var=lhcContinuein5Limit value=0}
												{if $sub_data.lhcContinuein5Limit>0}{assign var=lhcContinuein5Limit value=$sub_data.lhcContinuein5Limit}{/if}

												{assign var=lhcContinuenotin23Limit value=0}
												{if $sub_data.lhcContinuenotin23Limit>0}{assign var=lhcContinuenotin23Limit value=$sub_data.lhcContinuenotin23Limit}{/if}

												{assign var=lhcContinuenotin4Limit value=0}
												{if $sub_data.lhcContinuenotin4Limit>0}{assign var=lhcContinuenotin4Limit value=$sub_data.lhcContinuenotin4Limit}{/if}

												{assign var=lhcContinuenotin5Limit value=0}
												{if $sub_data.lhcContinuenotin5Limit>0}{assign var=lhcContinuenotin5Limit value=$sub_data.lhcContinuenotin5Limit}{/if}

												{assign var=lhcContinuecodeLimit value=0}
												{if $sub_data.lhcContinuecodeLimit>0}{assign var=lhcContinuecodeLimit value=$sub_data.lhcContinuecodeLimit}{/if}

												{assign var=award value=$sub_data.award}
												{assign var=theoryAward value=$sub_data.theoryAward}
											-->
											<dd>
												<table class="table">
													<!-- {if $sub_data.lotteryId !='99701'} 六合彩-->
													<tr>
														<td rowspan="3" style="width:80px">{$sub_data.awardName}：</td>
														<!-- {if $sub_data.lotteryId != '99113'}  超級2000秒秒彩-->
														<td style="text-align:left;">
															<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][0]}</span>
															
															<!-- {if $sub_data.lotterySeriesCode == '8'} -->
															<input type="input" class="input w-1 wg" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="" maxvalue="{$directLimitRet}" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')"/>																
															<!-- {else}  -->
															<input type="input" class="input w-1 normal" name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"  value="" maxvalue="{$directLimitRet}" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')"/>
															<!-- {/if} -->
															<span>(可分配返点范围为0-{$directLimitRet}{if $sub_data.awardName=="奖金组1800"}<span class='prize_info'></span>{/if})</span>
														</td>
														<!-- {elseif $sub_data.lotteryId !='99701'}  -->
														<td style="text-align:left;">
														</td>
														<!-- {/if} -->
														<td rowspan="2"><input type="checkbox" id="check" class="checkbox" name="status_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}" value="1"/></td>
													</tr>
													<!-- {/if} -->
													<!-- {if $sub_data.lotterySeriesCode neq '5' and $sub_data.lotterySeriesCode neq '3' and $sub_data.lotterySeriesCode neq '6' and $sub_data.lotterySeriesCode neq '8' and  $sub_data.lotterySeriesCode neq '9' and $sub_data.lotteryId != '99113'} -->
													<tr>
														<td style="text-align:left;">
															<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][1]}</span>
															<input type="input" class="input w-1 normal2" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$threeLimitRet}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$threeLimitRet}')" class="input w-1"  maxvalue="{$threeLimitRet}" value="" name="threeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
															<span>(可分配返点范围为0-{$threeLimitRet}{if $sub_data.awardName=="奖金组1800"}<span class='prize_info'></span>{/if})</span>
														</td>
													</tr>
													<!-- {/if} -->
													
													<!-- {if $sub_data.lotteryId == '99701'} 六合彩 -->
													<tr>
														
														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][0]}</span>
															
																<input type="input" class="input w-1 normal3" 
																name="lhcFlatcode_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}" value="" maxvalue="{$lhcFlatcodeLimit}"	onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcFlatcodeLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcFlatcodeLimit}')"/>

																<span>(可分配返点范围为0-{$lhcFlatcodeLimit})</span>
															</td>
														</tr>
														
														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][1]}</span>

																<input type="input" class="input w-1 ldc1" 
																name="directRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}" value="" maxvalue="{$directLimitRet}" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$directLimitRet}')"/>
																<span>(可分配返点范围为0-{$directLimitRet})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
															
																<input type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');
																CheckingMaxAllFull(this,'{$lhcYearLimit}')" 
																onBlur="calcu_prize(this,'{$award}','{$theoryAward}');
																CheckingMaxAllFull(this,'{$lhcYearLimit}')" 
																class="input w-1 normal2"  maxvalue="{$lhcYearLimit}" value="" name="lhcYear_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcYearLimit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][3]}</span>
																<input class="input w-1 normal2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcColorLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcColorLimit}')" class="input w-1 lch"  maxvalue="{$lhcColorLimit}" value="" name="lhcColor_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcColorLimit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][4]}</span>
																<input class="input w-1 normal2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcHalfwaveLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcHalfwaveLimit}')" class="input w-1 lch"  maxvalue="{$lhcHalfwaveLimit}" value="" name="lhcHalfwave_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcHalfwaveLimit})</span>
															</td>
														</tr>

														<tr>		
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][5]}</span>
																<input class="input w-1 ldc2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcOneyearLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcOneyearLimit}')" class="input w-1 lch"  maxvalue="{$lhcOneyearLimit}" value="" name="lhcOneyear_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcOneyearLimit})</span>
															</td>
														</tr>

														<tr>
															<td rowspan="3" style="width:80px">{$sub_data.awardName}：</td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][6]}</span>
																<input class="input w-1 normal2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcNotinLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcNotinLimit}')" class="input w-1 lch"  maxvalue="{$lhcNotinLimit}" value="" name="lhcNotin_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcNotinLimit})</span>
															</td>
															<td rowspan="2"><input type="checkbox" id="check" class="checkbox" name="status_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}" value="1"/></td>
														</tr>

														<tr>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][7]}</span>
																<input class="input w-1 normal2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein23Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein23Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuein23Limit}" value="" name="lhcContinuein23_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuein23Limit})</span>
															</td>
														</tr>

														

														<tr>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][8]}</span>
																<input class="input w-1 normal" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein4Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein4Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuein4Limit}" value="" name="lhcContinuein4_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuein4Limit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][9]}</span>
																<input class="input w-1 ldc11" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein5Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuein5Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuein5Limit}" value="" name="lhcContinuein5_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuein5Limit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][10]}</span>
																<input class="input w-1 normal2" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin23Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin23Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuenotin23Limit}" value="" name="lhcContinuenotin23_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuenotin23Limit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][11]}</span>
																<input class="input w-1 normal" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin4Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin4Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuenotin4Limit}" value="" name="lhcContinuenotin4_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuenotin4Limit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][12]}</span>
																<input class="input w-1 normal" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin5Limit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuenotin5Limit}')" class="input w-1 lch"  maxvalue="{$lhcContinuenotin5Limit}" value="" name="lhcContinuenotin5_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuenotin5Limit})</span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td style="text-align:left;">
																<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][13]}</span>
																<input class="input w-1 normal" type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuecodeLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$lhcContinuecodeLimit}')" class="input w-1 lch"  maxvalue="{$lhcContinuecodeLimit}" value="" name="lhcContinuecode_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
																<span>(可分配返点范围为0-{$lhcContinuecodeLimit})</span>
															</td>
														</tr>
													</tr>


													<!-- {/if} -->
													<!-- {if $sub_data.lotteryId == '99601' or $sub_data.lotteryId == '99602' or $sub_data.lotteryId == '99603'} 骰寶不定位 -->
													<tr>
														<td style="text-align:left;">
															<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
															<input type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$sbThreeoneRetLimit}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$sbThreeoneRetLimit}')" class="input w-1 sbThreeoneRet"  maxvalue="{$sbThreeoneRetLimit}" value="" name="sbThreeoneRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
															<span>(可分配返点范围为0-{$sbThreeoneRetLimit})</span>
														</td>
													</tr>
													<!-- {/if} -->
													<!-- {if $sub_data.lotteryId == '99101' or $sub_data.lotteryId == '99103' or $sub_data.lotteryId == '99104' or $sub_data.lotteryId == '99105' or $sub_data.lotteryId == '99113'} -->
													<tr>
														<td style="text-align:left;">
															<span class="label-like">{$aRateName[$sub_data.lotterySeriesCode][2]}</span>
															<input type="input" onKeyUp="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$superLimitRet}')" onBlur="calcu_prize(this,'{$award}','{$theoryAward}');CheckingMaxAllFull(this,'{$superLimitRet}')" class="input w-1 2000"  maxvalue="{$superLimitRet}" value="" name="superRet_{$sub_data.lotterySeriesCode}_{$sub_data.lotteryId}_{$sub_data.awardGroupId}"/>
															<span>(可分配返点范围为0-{$superLimitRet}，2000奖金组)</span>
														</td>
													</tr>
											
													<!-- {/if} -->
												</table>
											</dd>
											<!-- {/foreach} -->
										</dl>
										<!-- {/foreach} -->
                                	</div>
                                	<!-- {/foreach} -->
								</div>
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

								<div class="rebate-btn">
								<input type="button" value="立即开户"  id="J-btn-Subm" class="btn btn-important">
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
	<div class="hd"><i class="close" name="divCloseUrl"></i>提示</div>
	<div class="bd">
		<h4 class="pop-title">开户成功！<br />请到“链接管理”页面查看您生成的链接。</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " id="searchUrl">查看链接<b class="btn-inner"></b></a>
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
<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divUrlCntIsEmpty">
	<div class="hd"><i class="close" name="divCloseUrl"></i><span>提示</span></div>
	<div class="bd">
		<h4 class="pop-title">生成连接数不能为空，请重新填写</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " name="J-but-close">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>

<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none" id="divOperatSuccess">
	<i class="ico-success"></i><h4 class="pop-text">操作成功</h4>
</div>
<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
	<i class="ico-error"></i><h4 class="pop-text">开户失败，请重试</h4>
</div>
<div class="rate-info-jump">一键设置您和直属下级的<em>返点差额</em>，输入值高于您拥有的返点时下级返点清零。<br>（设置后可能需要手动调整的有：时时彩-所有超级2000玩法，趣味彩-六合彩）</div>
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->	
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script>
var emptyRewards = {$emptyRewards};
</script>
{literal}

<script type="text/javascript">

function calcu_prize(input,award,theoryAward){
	//alert(award + ' -> ' + theoryAward);
	var $input = $(input);
	var input_val = $input.val();
	var maxvalue = $input.attr('maxvalue');
	
	var $target_span = $input.next('span').find('span.prize_info');
	$target_span.html('');
	//若使用者輸入為大於0的數字
	if(!isNaN(input_val) && input_val > 0)
	{
		if(input_val > maxvalue)
		{
			input_val = maxvalue;
		}
		//var result = parseInt(award) + parseInt(theoryAward)*parseInt(input_val)/100;
		var result = parseFloat(award) + parseFloat(theoryAward)*parseFloat(input_val)/100.0;
		result = parseInt(result);
		$target_span.html('，' + result + '奖金组');
		//alert(result);
	}
}

function divConfig(obj){	
		$('#'+obj).css("display","none");
    }	
	
function check_qq()
{
	var qq;
		
	qq = document.getElementById("J-qq").value;
	//len = stringBytes(nickname);
	if(qq!='')
	{
		if(qq.length < 5)
		{
			$('#check_error').html('您输入的QQ号码有误');
			$('#check_error').show();
			$('#check_right').hide();
			$('#qqword').hide();
			
			
			$('#hiddenqq').val('');
			return false;
		}
		
		else if( qq.length > 10 )
		{
		
			$('#check_error').html('您输入的QQ号码有误');
			$('#check_error').show();
			$('#check_right').hide();
			$('#qqword').hide();
			$('#hiddenqq').val('');
			
			return false;
		}
		
		else if(!/^[0-9]+$/.test(qq))
		{
		
			$('#check_error').html('只支持数字');
			$('#check_error').show();
			$('#check_right').hide();
			$('#qqword').hide();
			$('#hiddenqq').val('');
			return false;
		}
		
		else
		{
			
			$('#check_error').hide();
			$('#check_right').show();
			$('#qqword').hide();
			$('#hiddenqq').val(qq);
			return true;
		}
	}
	else
	{
			
		$('#check_error').hide();
		$('#check_right').hide();
		$('#qqword').show();
		$('#hiddenqq').val(qq);	
		return true;
	}
		
}
	

  (function($){	
	var isPrize=false,inputs = $('.table').find('[type="input"]'),addfile,box = new LightBox("divIsEmpty"),box2=new LightBox("divRegSuccess"),box3=new LightBox("divUrlCntIsEmpty"),box4=new LightBox("divNoType");	
	if(emptyRewards ==1){
		$("#divOperatFailure h4").html('请联系代理或客服分配奖金组!');
		fn("divOperatFailure");
		setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
	}
    
	$(".input").on("blur",function(){
		var me=$(this),v=me.val();
		if(!($.trim(v)==""))
		{
			me.css("border", "solid 1px");
		}
	});
	//返点全满(待动成传参来判断最高低返点)
	var maxValue="";
	$('#returnAllFull').click(function(){
$(this).css("border","2px solid #4a95e2");
$("#returnZero").css("border","none");
		$('.table .input').each(function() {
              maxValue=$(this).attr("maxvalue");
			  $(this).val(maxValue);
        });
		//$('.checkbox').attr("checked",true);
		$('table.table').find('input').blur();
	});
	
	$('#returnAll').click(function(){
$(this).css("border","2px solid #4a95e2");
$("#returnHight").css("border","none");

		//$(".checkbox").prop("checked",true);
		var items =$('.item');
		$.each(items,function(){
			
			var dds = $(this).find('dd');
			var index = 0;
			$.each(dds,function(){
				
					$(this).show();
					$(this).find('.checkbox').prop("checked",true);
				
				index++;
			});
		})
	});
	
	$('#returnHight').click(function(){
$(this).css("border","2px solid #4a95e2");
$("#returnAll").css("border","none");

		//$('table.table').find('.checkbox').prop("checked",false);
		var items =$('.item');
		$.each(items,function(){
			
			var dds = $(this).find('dd');
			var index = 0;
			$.each(dds,function(){
				
				if(index == 0){
					//$(this).find('.checkbox').attr("checked",true);
					$(this).find('.checkbox').prop("checked",true);
				}else{
					$(this).hide();//沒選中要打開請將此code做mark 
					$(this).find('.checkbox').attr("checked",false);
				}
				index++;
			});
			
			
		})
		
	});
	
	$('#rateSetup').click(function(){
		//var diffvalue =  document.getElementById('diffvalue').value();//$('#diffvalue').val();
		var maxdiff,rate;
		maxdiff = document.getElementById('hiddendiffValue').value;
		value2000 = document.getElementById('hiddenvalue200').value;
	    wgvalue = document.getElementById('hiddenwgvalue').value;
		//console.log(wgvalue);
		ldc2value = document.getElementById('hiddenldc2').value;
		ldc1value = document.getElementById('hiddenldc1').value;
		userlevel = document.getElementById('userlevel').value;
		rate = $('#J-rate').val();
		if($('#J-rate').val()=='0' || $('#J-rate').val()=='')
		{
			$('#J-rate').val("0.5");
		}
	
		
		var items =$('.item');
		$.each(items,function(){
			
			var dds = $(this).find('dd');
			var index = 0;
			$.each(dds,function(){
				//var maxdiff = document.getElementById('hiddendiffValue').value;
				var maxvalue = $(this).find('.normal').attr("maxValue");
				var maxvalue2 = $(this).find('.normal2').attr("maxValue");
				var maxvalue3 = $(this).find('.normal3').attr("maxValue");
				var max2000 = $(this).find('.2000').attr("maxValue");
				var maxvaluewg = $(this).find('.wg').attr("maxValue");
				var maxvalueSbThreeoneRet = $(this).find('.sbThreeoneRet').attr("maxValue");
				
				rate = $('#J-rate').val();
				maxdiff = $('#hiddendiffValue').val();
				
				var ratevalue ;
				var maxrate;
				maxrate = eval(parseFloat(maxvalue) + parseFloat(maxdiff));
				
				ratevalue = eval(parseFloat(maxvalue) + parseFloat(maxdiff) -  parseFloat(rate));
				ratevalue2 = eval(parseFloat(maxvalue2) + parseFloat(maxdiff) -  parseFloat(rate));
				ratevalue3 = eval(parseFloat(maxvalue3) + parseFloat(maxdiff) -  parseFloat(rate));
				ratevalue2000 = eval(parseFloat(max2000) + parseFloat(value2000) -  parseFloat(rate));
				ratevaluewg = eval(parseFloat(maxvaluewg) + parseFloat(wgvalue) -  parseFloat(rate));
				ratevalueSbThreeoneRet = eval(parseFloat(maxvalueSbThreeoneRet) + parseFloat(maxdiff) -  parseFloat(rate));

				if(ratevalue < 0)
				{
					ratevalue = 0;
				}
				if(ratevalue2 < 0)
				{
					ratevalue2 = 0;
				}
				if(ratevalue3 < 0)
				{
					ratevalue3 = 0;
				}
				if(ratevalue2000 < 0)
				{
					ratevalue2000 = 0;
				}
				if(ratevaluewg < 0)
				{
					ratevaluewg = 0;
				}
				if(ratevalueSbThreeoneRet < 0)
				{
					ratevalueSbThreeoneRet = 0;
				}
				//console.log(maxrate);
				
				
				$(this).find('.normal').val(ratevalue.toFixed(1));
				$(this).find('.normal2').val(ratevalue2.toFixed(1));
				$(this).find('.normal3').val(ratevalue3.toFixed(1));
				$(this).find('.2000').val(ratevalue2000.toFixed(1));
				$(this).find('.wg').val(ratevaluewg.toFixed(1));
				$(this).find('.sbThreeoneRet').val(ratevalueSbThreeoneRet.toFixed(1));
			});
		});
		
	
		
		$('.ldc2').each(function() {	
			
			var ratevalue ='';
			
			maxValue=$('.ldc2').attr("maxvalue");
			var maxrate ;
			ratevalue = eval (parseFloat(maxValue) + parseFloat(ldc2value) - parseFloat(rate));
			//console.log(ratevalue);
			if(ratevalue<0)
			{
				ratevalue = 0;
			}
		
			$('.ldc2').val(ratevalue.toFixed(1));
        });
		
		$('.ldc1').each(function() {	
			
			var ratevalue ='';
			
			maxValue=$('.ldc1').attr("maxvalue");
			var maxrate
		
			
			if(rate<1)
			{
				rate=1;
			}
			
			
			if (userlevel=='0')
			{
				ratevalue	= eval (parseFloat(maxValue) -  parseFloat(rate));
				
			}
			else
			{
				ratevalue	= eval (parseFloat(maxValue) + parseFloat(ldc1value) -  parseFloat(rate));
			}
			
			$('.ldc1').val(ratevalue.toFixed(1));
			
        });

       $('.ldc11').each(function() {	
			
			var ratevalue ='';
			
			maxValue=$('.ldc11').attr("maxvalue");
			var maxrate ;
			ratevalue = eval (parseFloat(maxValue) + parseFloat(ldc1value) - parseFloat(rate));
			
			if(ratevalue<0)
			{
				ratevalue = 0;
			}
		
			$('.ldc11').val(ratevalue.toFixed(1));
        });
       
		//$('.checkbox').attr("checked",true);
		$('table.table').find('input').blur();
	});
	
	$("[name='urlCnt']").keyup(function(){
		CheckingMaxAllFull(this,15);
	});

		
	//返点清零 
	$('#returnZero').click(function(){
$(this).css("border","2px solid #4a95e2");
$("#returnAllFull").css("border","none");
		$('.table .input').val("0");
		//$('.checkbox').attr("checked",true);
		$('span.prize_info').html('');
	});
	
	$('#J-rate').keyup(function(){
		CheckingMaxAllFullrate(this,9.9);
	});
	(function rateInfo(){
		var l = $('#J-rate').offset().left;
		var t = $('#J-rate').offset().top - $('.rate-info-jump').outerHeight();
		$('.rate-info-jump').css({
			'left': l, 'top': t
		});
	}());
	$('#J-rate').click(function(){
		$('.rate-info-jump').fadeIn();
	});
	$('#J-rate').blur(function(){
		$('.rate-info-jump').fadeOut();
	});
	var defVal = $('#J-rate').val();
	$('#J-rate').bind({
        focus: function() {
            var _this = $(this);
            if (_this.val() == defVal) {
                _this.val('');
            }
        },
        blur: function() {
            var _this = $(this);
            if (_this.val() == '') {
                _this.val(defVal);
            }
        }
    });

    function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
    } 	
	
	
	function GetPostText()
	{
		var sText="";
		var sPost="";
		 $(":checkbox:checked").each(function(index, element) {
            var sname=$(this).attr("name");
			var svalue=$(this).val();
			sPost+=sname+"="+svalue+"&";
			$(this).parent().parent().parent().find("input").each(function(index, element) {
                if($(this).is("input") && $(this).attr("type")=="input")
				{
				     sText=$(this).attr("name")+"="+$(this).val()+"&";
					 sPost+=sText;
				}
            });
        });
		return sPost;
	}

    $("#span-QuickSetup").click(function(){
		isPrize=true;
		$("#div-context").css("display","none");
		$(".more-link").css("display","none");
		$("#li-QuickSetup").css("display","block");
		$("#li-Message").css("display","block");
		$("#span-DetailedSettings").removeClass("ico-tab-current");
		$(this).addClass(" ico-tab-current ");
		$("#hiddenSetUp").val("2");
	});
    
	$("#span-DetailedSettings").click(function(){
		isPrize=false;
		$("#div-context").css("display","inline");
		$(".more-link").css("display","inline");
		$("#li-QuickSetup").css("display","none");
		$("#li-Message").css("display","none");
		$("#span-QuickSetup").removeClass("ico-tab-current");
		$(this).addClass(" ico-tab-current ");
		$("#hiddenSetUp").val("1");
	});
										
	$("#userPlayer").click(function(){
		window.location="/applycenter/index?type=0";
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
	
    $("[name='divCloseUrl']").bind("click",function(){
		box.Close();
		box2.Close();
		box3.Close();
		box4.Close();
	});
	$("[name='J-but-close']").click(function (){
		box.Close();
		box2.Close();
		box3.Close();
		box4.Close();
	});	
	  
		//提交数据
	$('#J-btn-Subm').click(function (e){
		var istrue=true,txtName;
		if(isPrize)
		{
			if($.trim($("#Rebate").val())=="")
			{
				$("#Rebate").css("border", "solid 1px red"); 
				$("#Rebate").focus();
				fn("divIsEmpty");
				box.Over = true;
				box.OverLay.Color = "rgb(0, 0, 0)" ;
				box.OverLay.Opacity = 50;  
				box.Fixed = true;	 
				box.Center = true; 	
				box.Show();
				istrue=false;	
			    return false;	 	
			}
		}
		if($("#hiddenSetUp").val()=="1")
		{
			$(".item").each(function(index, element) {
                if($(this).find(":checkbox:checked").length==0)
				{
					fn("divNoType");
					box4.Over = true;
					box4.OverLay.Color = "rgb(0, 0, 0)" ;
					box4.OverLay.Opacity = 50;  
					box4.Fixed = true;	 
					box4.Center = true; 	
					box4.Show();
					istrue=false;
					return false;
				}else
				{
					$(this).find(":checkbox:checked").each(function(index, element) {
                        $(this).parent().parent().parent().find("input").each(function(index, element) {
                            var oinput=$(this);
							//oinput.css("border", "0"); 			
							if(oinput.val()==""){						
								oinput.css("border", "solid 1px red"); 
								oinput.focus();
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
		}
	
		if(istrue==true){		
		    var stype=$("#hiddenSetUp").val();
			
		    var sdata=(stype==1)?GetPostText():GetPostValueText("#J-form");
			var shidden="type="+$("#hiddenType").val()+"&SetUp="+$("#hiddenSetUp").val()+"&days="+$("[name='days']").val()+"&qq="+$("#hiddenqq").val();
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/applycenter/index/',
				//按格式传入需要参数
				data:sdata+shidden,	
				beforeSend:function(){
					istrue = true;
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
						//$('#retrunNum').text(data["data"]);
						fn("divRegSuccess");
						box2.Over = true;
						box2.OverLay.Color = "rgb(0, 0, 0)" ;
						box2.OverLay.Opacity = 50;  
						box2.Fixed = true;	 
						box2.Center = true; 	
						box2.Show(); 
					$('#J-but-close').click(function (){
						$('#divIsEmpty').css("display","none");
						box.Close();
					});		
						//查看链接数( 跳转)
						$('#searchUrl').click(function (){
							window.location = "/applycenter/managerurl/?type="+$("#hiddenType").val();
						});
					}
					else{
						//失败提示后隐藏
						$("#divOperatFailure h4").html(data['data']);
						fn("divOperatFailure");
						setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
					}
					
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$("#divOperatFailure h4").html('网络超时,请重新登录!');
					fn("divOperatFailure");
						 setTimeout(function(){$('#divOperatFailure').css("display","none");
						 if(textStatus == 'parsererror'){
		                    	window.location.href="/login/logout/"
		                    }
						 },3000);
				},
				complete:function(){
					istrue = false;
					clearInterval(interval);
					$('#J-btn-Subm').val("立即开户");						 	
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
<!--
$aAwardSeris|print_r
$aAwardGroup|print_r
$aRateName|print_r
$result|print_r
-->
