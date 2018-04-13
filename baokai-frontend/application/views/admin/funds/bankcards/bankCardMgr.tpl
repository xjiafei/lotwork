	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">银行卡管理</span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
								<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								
								<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_RECHARGE"|in_array:$smarty.session.datas.info.acls} -->
									<li >充值开关</li>
                                    <!--{else}-->
                                    <li style="display:none;"></li>
									<!-- {/if} -->
								<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_WITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
									<li id="liconfig">提现开关</li>
                                    <!--{else}-->
                                    <li style="display:none;"></li>
								<!-- {/if} -->
								<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_MOVE"|in_array:$smarty.session.datas.info.acls} -->
									<li >移动端开关</li>
								<!--{else}-->
									<li style="display:none;"></li>
								<!-- {/if} -->	
									<input type="hidden" name="step" value="{$step|default:'0'}"/>
									
									
								</ul>
							
							<ul class="ui-form ui-tab-content"  id="DivRules">
                                <li>  
                                    <form action="/admin/Bankcardsmanage/index?parma=sv45&step=0" method="post" id="J-form1">
                                        <table class="table table-info table-function card-setting">
											<thead>
												<tr>
													<th colspan="2">网银充值</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<label>
															<!-- <input type="checkbox" name="load-method" value="网银充值" > --> 网银充值
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
															{if $data.code lt 30}
																<label style="width:120px;">
																	<input type="checkbox" {if $data.deposit eq '1'}checked{/if} name="net_bank[]" value="{$data.code}" > {$data.name}
																</label>
																{if $key%4 eq '3'}
																<div class="clearfix"></div>
																{/if}
															{/if}
														{/foreach}
													</td>
												</tr>
											</tbody>
											<thead>
												<tr>
													<th colspan="2">快捷充值</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<label>
															<!-- <input type="checkbox" name="load-method" value="网银充值" > --> 快捷充值
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
															{if $data.code lt 30}
																<label style="width:120px;">
																	<input type="checkbox" {if $data.other eq '1'}checked{/if} name="quick_bank[]" value="{$data.code}" > {$data.name}
																</label>
																{if $key%4 eq '3'}
																	<div class="clearfix"></div>
																{/if}
															{/if}
														{/foreach}
													</td>
												</tr>
											</tbody>
											<thead>
												<tr>
													<th colspan="2">财付通</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<label>
															    <!-- <input type="checkbox" name="load-method" value="财付通" > -->  财付通
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
															{if $data.code eq 31}
																<label style="width:120px;">
																	<input type="checkbox" {if $data.deposit eq '1'}checked{/if} name="third_bank[]" value="{$data.code}" > {$data.name}
																</label>
															{/if}
														{/foreach}
													</td>
													<td>
														
													</td>
												</tr>
											</tbody>
											<thead>
												<tr>
													<th colspan="2">银联充值</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<label>
															     银联充值
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
															{if $data.code eq 51}
																<label style="width:120px;">
																	<input type="checkbox" {if $data.deposit eq '1'}checked{/if} name="unionpay_bank[]" value="{$data.code}" > {$data.name}
																</label>
															{/if}
														{/foreach}
													</td>
													<td>
														
													</td>
												</tr>
											</tbody>
											<thead>
													<tr>
														<th colspan="2">支付宝</th>
													</tr>
												</thead>
												<tbody>
												<tr>
													<td rowspan="2" style="width:13%;">
														<label>
															 支付宝
														</label>
													</td>
													
													
														
													{foreach from=$res key=key item=data}
														{if $data.code eq 30}
															
															{if $data.version eq 0}
																<td style="padding:0px 0px;">
																	<table>
																	<td style="border-bottom:0px;">
																		<label style="padding:0px 15px;0px 15px">
																				个人版
																				<input type="checkbox" checked name="ali_bank_personal[]" class="ali_bank_personal" value="{$data.code}" style="display:none;"  >
																
																		</label>
																	</td>
																	<td style="width:100%;border-bottom:0px;">
																		<label style="padding:0px 15px;0px 15px;width:120px;text-align:center;">
																			<input type="checkbox" {if $data.normalOpen eq '1'}checked{/if} name="aliNormalOpenPersonal" class="aliPayPersonal aliPersonNor" value="1"> 普通玩家																																
																		</label>
																		<label style="padding:0px 15px;0px 15px;width:120px;text-align:center;">
																			<input type="checkbox" {if $data.vipOpen eq '1'}checked{/if} name="aliVipOpenPersonal" class="aliPayPersonal aliPersonVIP" value="1"> VIP	
																		</label>
																	</td>
																	
																	</table>
																</td>
															{/if}
															{if $data.version eq 1}
																</tr>
																<tr>
																	<td style="padding:0px 0px;background-color:white;">
																		<table>
																		<td style="border-bottom:0px;">
																			<label style="padding:0px 15px;0px 15px">
																					企业版
																					<input type="checkbox" checked name="ali_bank_business[]" class="ali_bank_business" value="{$data.code}" style="display:none;" >
																
																			</label>
																		</td>
																		<td style="width:100%;border-bottom:0px;">
																			<label style="padding:0px 15px;0px 15px;width:120px;text-align:center;">
																				<input type="checkbox" {if $data.normalOpen eq '1'}checked{/if} name="aliNormalOpenBusiness" class="aliPayBusiness aliBusiNor" value="1"> 普通玩家																																
																			</label>
																			<label style="padding:0px 15px;0px 15px;width:120px;text-align:center;">
																				<input type="checkbox" {if $data.vipOpen eq '1'}checked{/if} name="aliVipOpenBusiness" class="aliPayBusiness aliBusiVIP" value="1"> VIP	
																			</label>
																		</td>
																		
																		</table>
																	</td>
																{/if}									
														{/if}
													{/foreach}
													
													
												</tr>
												</tbody>
												<thead>
													<tr>
														<th colspan="2">微信支付</th>
													</tr>
												</thead>
													<tbody>
													<tr>
													<td style="width:13%;">
														<label>
															 微信支付
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
														{if $data.code eq 40}
															<label >
																<input type="checkbox" {if $data.deposit eq '1'}checked{/if} name="wechat[]" value="{$data.code}" > {$data.name}
															</label>
														{/if}
														{/foreach}
													
													</td>
												</tr>
												</tbody>
										</table>
										<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_RECHARGE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
										<a class="btn btn-important w-1 card-setting-savebutton" id="J-Submit-Button1">保存</a>
										<!-- {/if} -->
                                     </form>
                                </li>						
							</ul>
                            <ul class="ui-form ui-tab-content" id="DivRules2">
								<li>
                                	 <form action="/admin/Bankcardsmanage/index?parma=sv45&step=1" method="post" id="J-form2" >
                                        <table class="table table-info table-function card-setting">
											<thead>
												<tr>
													<th colspan="2">网银提现</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<label>
															<!-- <input type="checkbox" name="load-method" value="网银提现" > --> 网银提现
														</label>
													</td>
													<td>
														{foreach from=$res key=key item=data}
															{if $data.code lt 30}
																<label style="width:120px;">
																	<input type="checkbox" {if $data.withdraw eq '1'}checked{/if} name="withdraw_bank[]" value="{$data.code}" > {$data.name}
																</label>
																{if $key%4 eq '3'}
																	<div class="clearfix"></div>
																{/if}
															{/if}
														{/foreach}
													</td>
												</tr>
											</tbody>
											
										</table>
										<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_WITHDRAW_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
										<a class="btn btn-important w-1 card-setting-savebutton" id="J-Submit-Button2">保存</a>
										<!-- {/if} -->
                                        </form>                                        
                                       
                              	 	</li>						
								</ul>
								<ul class="ui-form ui-tab-content" id="DivRules3">
								
									<li>
	                                	 <form action="/admin/Bankcardsmanage/index?parma=sv45&step=2" method="post" id="J-form3" >
	                                        <table class="table table-info table-function card-setting">
												<thead>
													<tr>
														<th colspan="2">移动端快捷充值</th>
													</tr>
												</thead>
												
												<tbody>
												<tr>
													<td style="width:13%;">
														<label>
															 移动端快捷充值
														</label>
													</td>
													
													<td>
														{$count =0}
														{foreach from=$res key=key item=data}
														{if $data.moveQuickType eq 1}	
															<label>
																<input type="checkbox" id="move_quick_check" {if $data.moveQuickDeposit eq '1'}checked{/if} name="move_quick_type" value="1" > 移动端快捷充值
															</label>
															{$count = $count+1}
															{break}
														{/if}
														
										
														{/foreach}
														{if $count eq 0}
														<label>
															<input type="checkbox" id="move_quick_check" name="move_quick_type" value="1" > 移动端快捷充值
														</label>
														{/if}
															
														
													
														{foreach from=$res key=key item=data}
														{if $data.moveQuickType eq 1}
															<input type="hidden" name="move_quick_bank[]" value="{$data.code}"/>
														{/if}
														{/foreach}
													
													
													</td>
												</tr>
												</tbody>
												
												<thead>
													<tr>
														<th colspan="2">银联充值</th>
													</tr>
												</thead>
												
												<tbody>
												<tr>
													<td style="width:13%;">
														<label>
															 银联充值
														</label>
													</td>
													
													<td>
														
														{foreach from=$res key=key item=data}
														{if $data.code eq 51}
															<label >
																		<input type="checkbox" id="move_deposit_check" {if $data.moveDeposit eq '1'}checked{/if} name="move_bank[]" value="{$data.code}" > 移动端银联充值
															</label>                                               
														{/if}
														{/foreach}
													
													</td>
												</tr>
												</tbody>
                                                 <thead>
													<tr>
														<th colspan="2">支付宝充值</th>
													</tr>
												</thead>
												<tbody> <tr>
                                                            <td style="width:13%;">
                                                             <label>
																 支付宝充值
                                                              </label>
                                                            </td>
                                                            <td>
																{foreach from=$res key=key item=data}
                                                                 {if $data.code eq 30 && $data.version eq 0}
                                                                  <label>
                                                                    <input type="checkbox" id="move_deposit_check1" {if $data.moveDeposit eq '1'}checked{/if} name="move_bank[]" value="{$data.code}" > 移动端支付宝充值<br>
                                                                    <input type="checkbox" {if $data.moveNormalOpen eq '1'}checked{/if} name="aliMoveNormalOpen" value="1"> 普通玩家
													   				<input type="checkbox" {if $data.moveVipOpen eq '1'}checked{/if} name="aliMoveVipOpen" value="1"> VIP 
                                                                  </label>
                                                                 {/if}
                                                                {/foreach}
															</td>
                                                         </tr>
                                                  </tbody>
											</table>
											<!-- {if "FUND_BANKCARD_BANKCARDMANAGE_MOVE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
											<a class="btn btn-important w-1 card-setting-savebutton" id="J-Submit-Button3">保存</a>
											<!-- {/if} -->
										<form>
									</li>
											
								
								</ul>
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    <textarea id="DivContent" style="display:none;">
    	<dd>
            <input type="text" value="0" class="input w-1" name="sm[]">
            <span class="ui-info">&le;充值金额&lt;</span>
            <input type="text" value="0" class="input w-1" name="big[]">
            <span class="ui-info">时，返送手续费为：</span>
            <span class="radio-list">
                <label class="label" ><input type="radio" id="" name="radiodd1" class="radio" value="1">固值</label>
                <label class="label" ><input type="radio" id="" name="radiodd1"  class="radio" value="2">百分比</label>
            </span>
            <input type="text" value="0" class="input w-1" name="setValue[]">
            <span class="ui-info" name="moneyType"></span>
            <span class="ui-text-info"></span>
            <a href="javascript:void(0);" class="btn btn-link" name='J-Delete'>删除<b class="btn-inner"></b></a>
   		 </dd>
    </textarea>
    <textarea id="DivUnfillContent" style="display:none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您还有未填内容，请完整填写</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="CloseDf">关 闭<b class="btn-inner"></b></a>   
            </div>
        </div>
    </textarea>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {	
	var form1 = $('#J-form1'),form2=$('#J-form2'),form3=$('#J-form3'),minWindow,mask,initFn,isture=false;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	//Tab	
	var indexs=$("[name='step']").val();
	new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:indexs});
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuBankcards');
	
	$('#radios1').click(function(){		
		$('#radios1').attr("checked","checked");
		$('#Div_1').css("display","inline");
	});	
				
	//返送金额选择
	$('#radios2').click(function(){
		$('#radios2').attr("checked","checked");		
		$('#Div_1').css("display","none");
	});
	
	//支付寶個人/企業版驗證
	$('.aliPayBusiness').click(function(){
		$('.aliPayPersonal').prop("checked", false);
	});
	$('.aliPayPersonal').click(function(){
		$('.aliPayBusiness').prop("checked", false);
	});	
		
	//充值上下限配置置(表单提交校验)
	$('#J-Submit-Button1').click(function(){
		form1.submit();	
	});			
	$('#J-Submit-Button2').click(function(){
		form2.submit();	
	});	
	$('#J-Submit-Button3').click(function(){
		form3.submit();	
	});	
	
	$('input[id^="move_"]').click(function(){
            		if(!$('#move_deposit_check').prop('checked') && !$('#move_quick_check').prop('checked') && !$('#move_deposit_check1').prop('checked')){
			var msg = new phoenix.Message();
			msg.show({
						mask: true,
						hideClose: true,
						title: '温馨提示',
						content: '<h3 style="height:30px;line-height:30px;text-align:center; ">移动端快捷充值 与 移动端银联充值 均无勾选 移动端将无法充值</h3><div style="height:30px;line-height:30px;"></div>',
						confirmIsShow: 'true',
						confirmText: '确定',
						confirmFun: function(){
							msg.hide();
						}
					});
		}
	});	
	
	
			
})();	
</script>
{/literal}
</body>
</html>