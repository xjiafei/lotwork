<form action="/admin/Rechargemange/index?parma=sv2&step=0&type=4"
	method="post" id="J-form1" name="J-form1">
	<table class="table table-info table-border">
		<thead>
			<tr>
				<th colspan="2" class="text-left">支付宝充值配置</th>
			</tr>
		</thead>
		<tbody>
			<tr>

				<td class="text-left w-4">限额设置</td>
				<td class="text-left">
					<dl class="set-list">
						<dt>普通用户</dt>

						<!-- {foreach $banklist as $k1 => $v1} -->
						<!-- {if $v1['code'] eq '30'} -->
						<!-- {if $v1['version'] eq '0'} -->
						<dd>
							<span class="ui-text-info">大于：</span> <input type="text"
								name="{$v1['id']}@lowLimit" value="{$v1['lowLimit']}"
								class="input w-1"> <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="ui-text-info">小于：</span> <input type="text"
								name="{$v1['id']}@upLimit" value="{$v1['upLimit']}"
								class="input w-1"> <span class="ui-info">元</span>
						</dd>

						<dt>VIP用户</dt>

						<dd>
							<span class="ui-text-info">大于：</span> <input type="text"
								name="{$v1['id']}@vipLowLimit" value="{$v1['vipLowLimit']}"
								class="input w-1"> <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="ui-text-info">小于：</span> <input type="text"
								name="{$v1['id']}@vipUpLimit" value="{$v1['vipUpLimit']}"
								class="input w-1"> <span class="ui-info">元</span>
						</dd>
						
						<dt><span>单日限额：</span> <input type="text"
								name="{$v1['id']}@daylimit" value="{$v1['daylimit']}"
								class="input w-1"> <span class="ui-info">元</span>
						</dt>
						
						<input type="hidden" name="{$v1['id']}@version" value="0">
						<!-- {/if} -->
						<!-- {/if} -->
						<!-- {/foreach} -->
					</dl>
				</td>

			</tr>
			<tr>
				<td class="text-left">充值倒计时设置</td>
				<td class="text-left"><span class="ui-info">显示倒计时分钟数：</span> <input
					type="text" name="charge_ratio" value="{$cdata4}" class="input w-1">
					<span class="ui-info">分钟</span></td>
			</tr>
			<!-- {if "FUND_RECHARGE_CONFIG_UPTO_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
			<tr>
				<td></td>
				<td class="text-left ui-btn"><input type="submit" value="保 存"
					class="btn btn-important"> <a href="javascript:void(0);"
					class="btn">撤销编辑<b class="btn-inner"></b></a></td>
			</tr>
			<!-- {/if} -->
		</tbody>

	</table>
</form>