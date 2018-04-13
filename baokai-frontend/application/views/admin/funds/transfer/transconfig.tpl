	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 转账限额配置</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">转账限额配置</h3>
						<form action="/admin/Transfer/index?parma=sv62" method="post" id="subForm">
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<!-- {if "FUND_TRANSFER_CONFIG_HIGHER"|in_array:$smarty.session.datas.info.acls} -->
							<li class="current">向上级转账</li>
                            <!--{else}-->
                            <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_TRANSFER_CONFIG_DOWN"|in_array:$smarty.session.datas.info.acls} -->
							<li id="liconfig">向下级转账</li>
                            <!--{else}-->
                            <li style="display:none;"></li>
							<!-- {/if} -->
						</ul>
						<ul class="ui-tab-content ui-tab-content-current"  id="DivRules">
						<table class="table table-info table-function">
							
							<tbody>
								<tr>
									<td class="w-4">每日转账次数</td>
									<td>
										<select class="ui-select w-1" name="up_time">
										{foreach from=$downArray item=i}
											<option value="{$i}" {if $res.up_time == $i}selected{/if}>{$i}</option>
										{/foreach}
											<option value="-1" {if $res.up_time == '-1'}selected{/if}>不限</option>
										</select>
										<span class="ui-text-info"> 次/每位用户每天</span>
									</td>
								</tr>
								<tr>
									<td>单次转账上限</td>
									<td>
										<ul class="set-list">
											<li>
												<span class="ui-info">普通用户：</span>
												<input type="text" class="input w-1" value="{$res.up_userlimit}" name="up_userlimit">
												<span class="ui-info">元</span>
											</li>
											<li>
												<span class="ui-info">&nbsp;VIP用户：</span>
												<input type="text" class="input w-1" value="{$res.up_viplimit}" name="up_viplimit">
												<span class="ui-info">元</span>
											</li>
										</ul>
									</td>
								</tr>
								<!-- {if "FUND_TRANSFER_CONFIG_HIGHER_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
								<tr>
									<td></td>
									<td class="ui-btn">
										<a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button">确 认<b class="btn-inner"></b></a>
										<a class="btn" href="javascript:void(0);"  id="J-Close-Button">取 消<b class="btn-inner"></b></a>
									</td>
								</tr>
								<!-- {/if} -->
							</tbody>
						</table>
						</ul>
						</form>
						<form action="/admin/Transfer/index?parma=sv62" method="post" id="subForm1">
						<input type="hidden" name="step" value="{$step}"/>
						<ul class="ui-tab-content" id="DivRules2">
							<table class="table table-info table-function">
								<tbody>
									<tr>
										<td class="w-4">每日转账次数</td>
										<td>
											<select class="ui-select w-1" name="down_time">
											{foreach from=$downArray item=i}
												<option value="{$i}" {if $res.down_time == $i}selected{/if}>{$i}</option>
											{/foreach}
												<option value="-1" {if $res.down_time == '-1'}selected{/if}>不限</option>
											</select>
											<span class="ui-text-info"> 次/每位用户每天</span>
										</td>
									</tr>
									<tr>
										<td>单次转账上限</td>
										<td>
											<ul class="set-list">
												<li>
													<span class="ui-info">普通用户：</span>
													<input type="text" class="input w-1" value="{$res.down_userlimit}" name="down_userlimit">
													<span class="ui-info">元</span>
												</li>
												<li>
													<span class="ui-info">&nbsp;VIP用户：</span>
													<input type="text" class="input w-1" value="{$res.down_viplimit}" name="down_viplimit">
													<span class="ui-info">元</span>
												</li>
											</ul>
										</td>
									</tr>
									<tr>
										<td>转账客户类型</td>
										<td>
											<!-- {foreach from=$aAgentArray key=key item=data} -->
												<label style="width:70px;display:inline-block;">
													<input type="checkbox" value="{$key}" {if $res.down_lvls and $key|in_array:$res.down_lvls} checked {/if} style="margin-right:5px;vertical-align: middle;" name="down_lvls[]"> {$data}
												</label>
											<!-- {/foreach} -->
										</td>
									</tr>
									<!-- {if "FUND_TRANSFER_CONFIG_DOWN_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
									<tr>
										<td></td>
										<td class="ui-btn">
											<a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button1">确 认<b class="btn-inner"></b></a>
											<a class="btn" href="javascript:void(0);"  id="J-Close-Button1">取 消<b class="btn-inner"></b></a>
										</td>
										<!-- {/if} -->
									</tr>
								</tbody>
							</table>
						</ul>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
{include file='/admin/script-base.tpl'}
{literal}
	<script type="text/javascript">
		(function(){
			//一、二级菜单选中样式加载	
	        selectMenu('Menufunds','MenuTransferConfig');
			var form=$('#subForm'),form1=$('#subForm1'),isture=false;	
			var indexs=$("[name='step']").val();
			$('#Tabs li').removeClass('current');
			$('#Tabs li').eq(indexs).attr('class','current');
			if(indexs =='0'){
				$('#DivRules').addClass('ui-tab-content-current');
				$('#DivRules2').removeClass('ui-tab-content-current');
			} else {
				$('#DivRules').removeClass('ui-tab-content-current');
				$('#DivRules2').addClass('ui-tab-content ui-tab-content-current');
			}
			//new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:indexs});
			$('#Tabs li').click(function(){
				var indexs1 = $(this).index();
				if($(this).attr('class') !='current'){
					$('#Tabs li').removeClass('current');
					$(this).attr('class','current');
					if(indexs1 =='0'){
						$("[name='step']").val(indexs1);
						$('#DivRules').addClass('ui-tab-content-current');
						$('#DivRules2').removeClass('ui-tab-content-current');
					} else {
						$("[name='step']").val('1');
						$('#DivRules').removeClass('ui-tab-content-current');
						$('#DivRules2').addClass('ui-tab-content ui-tab-content-current');
					}
				}
			});
			function CheckFrom(obj){		
				$(obj).each(function () { 
					$(this).css("border","1px solid #CECECE");	
					if($(this).val() == ""){
						var obj=this;
						$(this).focus(); 
						$(this).css("border","1px solid red");					
						isture=false;					
						window.location.hash = "#"+this;
						return false;						
					}
					else{ isture = true;}		
				});	
			}		
			$('#J-Submit-Button').click(function(){
				form.submit();	
			});
			$('#J-Submit-Button1').click(function(){
				form1.submit();	
			});
			form.submit(function(e){			
				CheckFrom("#subForm :text");
				if(isture !=true){
					return false;
				}
				return true;	
			});		
			form1.submit(function(e){			
				CheckFrom("#subForm1 :text");
				if(isture !=true){
					return false;
				}
				return true;	
			});		
			
			$('#J-Close-Button').click(function(){
				 location=location;
			});
			$('#J-Close-Button1').click(function(){
				 location=location;
			});
		})();
	</script>
{/literal}
</html>