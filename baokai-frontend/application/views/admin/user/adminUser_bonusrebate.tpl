<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 
			{if $typepage eq '1'}
			 <a href="/admin/user/list?search=0"><span id="menu2">客户列表</span></a> 
			{else if $typepage eq '2'}
			 <a href="/admin/proxy/index?search=0"><span id="menu2">总代管理</span></a> 
			{else}
			 <a href="/admin/user/accomplaints?search=1"><span id="menu2">账号申诉管理</span></a> 
			{/if}
			&gt; <span id="menu3">{$account}</span>
			</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<div class="ui-tab">
						<div class="ui-tab-title clearfix">
							<ul>
								<!-- {if ("USER_MANAGE_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
								<li ><a href="/admin/user/userdetail?id={$id}&typepage={$typepage}">基本资料</a></li>
							<!-- {/if} -->
							<!-- {if ("USER_MANAGE_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
								<li><a href="/admin/user/bankcard?id={$id}&account={$account}&typepage={$typepage}">查看银行卡</a></li>
							<!-- {/if} -->
								<li class="current">奖金返点</li>
							<!-- {if ("USER_MANAGE_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
								<li><a href="/admin/user/getphonesecurity?id={$id}&account={$account}&typepage={$typepage}">手机令牌</a></li>
							<!-- {/if} -->
							</ul>
						</div>
						<div >
							<input type="hidden" name="typepage" value="{$typepage|default:1}" />
							<input type="hidden" id="userId" value="{$id}"/>
                          	<input type="hidden" id="type" value="{$type}"/>
                          	<input type="hidden" id="retType" value="{$retType}"/>
							<div class="rebate-list">
								<div id="div-context" class="lottery-switcher">
                                	<div class="lottery-tabs">
                                		<!-- {foreach from=$aAwardSeris key=key item=data} -->
		                          			<a href="javascript:void(0);">{$data}</a>
		                          		<!-- {/foreach} -->
                                	</div>

                              <!-- {foreach from=$userAwardsList key=key item=data} -->
	                               <div class="lottery-switch">
									<!-- {foreach from=$aAwardGroup[$key] key=sub_key item=sub_value} -->
                                	<!-- {{assign var="sub_value" value=$userAwardsList[$key][$sub_key]}} -->
									<dl class="item">
										<dt>{$aAwardGroup[$key][$sub_key]}</dt>
									<!-- {if $sub_value|@count>0} -->
										<!-- {foreach from=$sub_value key=sub_key1 item=sub_data} -->
										<dd {if $sub_data.betType eq '1'} class="bet-selected" {/if}>
											<table class="table">
												<tbody>
												<!-- {if $sub_data.lotteryId != '99701'} -->
													<tr>
														<td rowspan="3" style="width:330px">{$sub_data.awardName}：</td>
														<!-- {if $sub_data.lotteryId != '99113'} -->
														<td style="text-align:left;">
															<a class="label-like" data-value="{$sub_data.directRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][0]}</a>
																<!-- {if $sub_data.directRet eq $sub_data.maxDirectRet or $sub_data.directRet gt $sub_data.maxDirectRet} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}-{$sub_data.maxDirectRet}"/>
																<!-- {/if} -->
														</td>
														<!-- {else} -->
														<td style="text-align:left;">
														</td>
														<!-- {/if} -->
													</tr>
												<!-- {/if} -->
													<!-- {if $sub_data.lotterySeriesCode neq '5' and $sub_data.lotterySeriesCode neq '3' and $sub_data.lotterySeriesCode neq '6' and $sub_data.lotterySeriesCode neq '9' and $sub_data.lotteryId != '99113'} -->
													<tr>
														<td style="text-align:left;">
															<a class="label-like"  data-value="{$sub_data.threeoneRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][1]}</a>
																<!-- {if $sub_data.threeoneRet eq $sub_data.maxThreeOneRet or $sub_data.threeoneRet gt $sub_data.maxThreeOneRet} -->
																	<input type="input" readonly   class="input w-2"  value="{$sub_data.threeoneRet}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.threeoneRet}-{$sub_data.maxThreeOneRet}"/>
																<!-- {/if} -->
														</td>
													</tr>
													<!-- {/if} -->
													<!-- {if $sub_data.lotteryId == '99701'} -->
													<tr>
													<tr><td></td>
														<td style="text-align:left;">
															<a class="label-like"  data-value="{$sub_data.lhcFlatcode*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][0]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][0]}</a>
																<!-- {if $sub_data.lhcFlatcode eq $sub_data.maxLhcFlatcode or $sub_data.lhcFlatcode gt $sub_data.maxLhcFlatcode} -->
																	<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcFlatcode}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcFlatcode}-{$sub_data.maxLhcFlatcode}"/>
																<!-- {/if} -->
														</td>
													</tr>


													<tr><td></td>
														<td style="text-align:left;">
															<a class="label-like" data-value="{$sub_data.directRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][1]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][1]}</a>
																<!-- {if $sub_data.directRet eq $sub_data.maxDirectRet or $sub_data.directRet gt $sub_data.maxDirectRet} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.directRet}-{$sub_data.maxDirectRet}"/>
																<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
															<a class="label-like"  data-value="{$sub_data.lhcYear*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
																<!-- {if $sub_data.lhcYear eq $sub_data.maxLhcYear or $sub_data.lhcYear gt $sub_data.maxLhcYear} -->
																	<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcYear}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcYear}-{$sub_data.maxLhcYear}"/>
																<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcColor*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][3]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][3]}</a>
																	<!-- {if $sub_data.lhcColor eq $sub_data.maxLhcColor or $sub_data.lhcColor gt $sub_data.maxLhcColor} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcColor}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcColor}-{$sub_data.maxLhcColor}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcHalfwave*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][4]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][4]}</a>
																	<!-- {if $sub_data.lhcHalfwave eq $sub_data.maxLhcHalfwave or $sub_data.lhcHalfwave gt $sub_data.maxLhcHalfwave} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcHalfwave}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcHalfwave}-{$sub_data.maxLhcHalfwave}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcOneyear*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][5]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][5]}</a>
																	<!-- {if $sub_data.lhcOneyear eq $sub_data.maxLhcOneyear or $sub_data.lhcOneyear gt $sub_data.maxLhcOneyear} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcOneyear}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcOneyear}-{$sub_data.maxLhcOneyear}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td rowspan="3" style="width:330px">{$sub_data.awardName}：</td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcNotin*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][6]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][6]}</a>
																	<!-- {if $sub_data.lhcNotin eq $sub_data.maxLhcNotin or $sub_data.lhcNotin gt $sub_data.maxLhcNotin} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcNotin}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcNotin}-{$sub_data.maxLhcNotin}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuein23*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][7]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][7]}</a>
																	<!-- {if $sub_data.lhcContinuein23 eq $sub_data.maxLhcContinuein23 or $sub_data.lhcContinuein23 gt $sub_data.maxLhcContinuein23} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein23}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuein23}-{$sub_data.maxLhcContinuein23}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuein4*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][8]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][8]}</a>
																	<!-- {if $sub_data.lhcContinuein4 eq $sub_data.maxLhcContinuein4 or $sub_data.lhcContinuein4 gt $sub_data.maxLhcContinuein4} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein4}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuein4}-{$sub_data.maxLhcContinuein4}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuein5*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][9]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][9]}</a>
																	<!-- {if $sub_data.lhcContinuein5 eq $sub_data.maxLhcContinuein5 or $sub_data.lhcContinuein5 gt $sub_data.maxLhcContinuein5} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuein5}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuein5}-{$sub_data.maxLhcContinuein5}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuenotin23*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][10]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][10]}</a>
																	<!-- {if $sub_data.lhcContinuenotin23 eq $sub_data.maxLhcContinuenotin23 or $sub_data.lhcContinuenotin23 gt $sub_data.maxLhcContinuenotin23} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin23}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuenotin23}-{$sub_data.maxLhcContinuenotin23}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuenotin4*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][11]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][11]}</a>
																	<!-- {if $sub_data.lhcContinuenotin4 eq $sub_data.maxLhcContinuenotin4 or $sub_data.lhcContinuenotin4 gt $sub_data.maxLhcContinuenotin4} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin4}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuenotin4}-{$sub_data.maxLhcContinuenotin4}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuenotin5*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][12]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][12]}</a>
																	<!-- {if $sub_data.lhcContinuenotin5 eq $sub_data.maxLhcContinuenotin5 or $sub_data.lhcContinuenotin5 gt $sub_data.maxLhcContinuenotin5} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuenotin5}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuenotin5}-{$sub_data.maxLhcContinuenotin5}"/>
																	<!-- {/if} -->
														</td>
													</tr>

													<tr><td></td>
														<td style="text-align:left;">
																<a class="label-like"  data-value="{$sub_data.lhcContinuecode*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][13]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][13]}</a>
																	<!-- {if $sub_data.lhcContinuecode eq $sub_data.maxLhcContinuecode or $sub_data.lhcContinuecode gt $sub_data.maxLhcContinuecode} -->
																		<input type="input" readonly   class="input w-2"  value="{$sub_data.lhcContinuecode}"/>
																	<!-- {else} -->
																		<input type="input" readonly  class="input w-2"  value="{$sub_data.lhcContinuecode}-{$sub_data.maxLhcContinuecode}"/>
																	<!-- {/if} -->
														</td>
													</tr>


													
													<!-- {/if} -->
													
													<!-- 骰寶不定位 -->
													<!-- {if  $sub_data.lotteryId == '99601' or $sub_data.lotteryId == '99602' or $sub_data.lotteryId == '99603'} -->
													<tr>
														<td style="text-align:left;">
															<a class="label-like"  data-value="{$sub_data.sbThreeoneRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
																<!-- {if $sub_data.sbThreeoneRet eq $sub_data.maxSbThreeoneRet or $sub_data.sbThreeoneRet gt $sub_data.maxSbThreeoneRet} -->
																	<input type="input" readonly   class="input w-2"  value="{$sub_data.sbThreeoneRet}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="{$sub_data.sbThreeoneRet}-{$sub_data.maxSbThreeoneRet}"/>
																<!-- {/if} -->
														</td>
													</tr>
													<!-- {/if} -->
													
													<!-- {if $sub_data.lotteryId == '99101' or $sub_data.lotteryId == '99103' or $sub_data.lotteryId == '99104' or $sub_data.lotteryId == '99105' or $sub_data.lotteryId == '99113'} -->
													<tr>
														<td style="text-align:left;">
															<a class="label-like"  data-value="{$sub_data.superRet*100}" data-wanfadata="1" data-wanfa="{$aRateIndex[$sub_data.lotterySeriesCode][2]}" data-awardgroup="{$sub_data.awardGroupId}" data-lottery="{$sub_data.lotteryId}">{$aRateName[$sub_data.lotterySeriesCode][2]}</a>
																<!-- {if $sub_data.superRet eq $sub_data.maxSuperRet or $sub_data.superRet gt $sub_data.maxSuperRet} -->
																	<input type="input" readonly   class="input w-2"  value="{$sub_data.superRet}"/>
																<!-- {else} -->
																	<input type="input" readonly  class="input w-2"  value="({$sub_data.superRet}-{$sub_data.maxSuperRet})"/>
																<!-- {/if} -->
														</td>
													</tr>
													<!-- {/if} -->
													
												</tbody>
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
								
								</div>
							</div>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		baseUrl = "/admin/user/queryusergameaward";
	</script>
	{include file='/admin/script-base.tpl'}
	<script type="text/javascript" src="{$path_js}/js/userCenter/queryBonusDetails.js"></script>
	{literal}
	<script > 
	(function($){	
		//一级菜单选中样式加载
		var type = $('input[name="typepage"]').val();
		if(type!=3){
			type = type -1;
		}
		if(type==0)
		{
			menuName="MenuUserlist";
		}
		else if(type==1)
		{
			menuName="MenuUsermanage";
		}else if(type==3)
		{
			menuName="MenuUseraccomplaints";
		}
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser',menuName);
		// 彩系切换，新增
		new phoenix.Tab({
			par : '.lottery-switcher',
			triggers : '.lottery-tabs > a',
			panels : '.lottery-switch',
			eventType : 'click',
			currPanelClass: 'lottery-switch-current'
		});
		
	})(jQuery);
	</script>
	{/literal}
</body>
</html>