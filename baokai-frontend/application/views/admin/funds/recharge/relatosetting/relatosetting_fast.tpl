<form action="/admin/Rechargemange/index?parma=sv2&step=0&type=1"
	method="post" id="J-form1" name="J-form1">
	<table class="table table-info table-border">
		<thead>
			<tr>
				<th colspan="2" class="text-left">快捷充值配置</th>
			</tr>
		</thead>
		<tbody>
			<tr>

				<td class="text-left w-4">限额设置</td>
				<td class="text-left">
					<dl class="set-list">
						<dt>普通用户</dt>

						<!-- {foreach $banklist as $k1 => $v1} -->
						<!-- {if $v1['id'] lt '30'} -->
						<dd>
							<!-- <input type="hidden" name="logos" value ="{$v1["logo"]}"/> -->
							<span class="ui-info" style="width: 80px;">{$v1["name"]}：</span>
							<span class="ui-text-info">最低：</span> <input type="text"
								name="{$v1['id']}@otherdownlimit"
								value="{$v1['otherdownlimit']}" class="input w-1"> <span
								class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
								class="ui-text-info">最高：</span> <input type="text"
								name="{$v1['id']}@otheruplimit" value="{$v1['otheruplimit']}"
								class="input w-1"> <span class="ui-info">元</span>
						</dd>
						<!-- {/if} -->
						<!-- {/foreach} -->
						
						<dt>VIP客户</dt>
						<!-- {foreach $banklist as $k1 => $v1} -->
						<!-- {if $v1['id'] lt '30'} -->
						<dd>
							<span class="ui-info" style="width: 80px;">{$v1["name"]}：</span>
							<span class="ui-text-info">最低：</span> <input type="text"
								name="{$v1['id']}@othervipdownlimit" value="{$v1['othervipdownlimit']}"
								class="input w-1"> <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="ui-text-info">最高：</span> <input type="text"
								name="{$v1['id']}@othervipuplimit" value="{$v1['othervipuplimit']}"
								class="input w-1"> <span class="ui-info">元</span>
						</dd>
						<!-- {/if} -->
						<!-- {/foreach} -->
					</dl>
				</td>

			</tr>
			<tr>
				<td class="text-left">充值倒计时设置</td>
				<td class="text-left"><span class="ui-info">显示倒计时分钟数：</span> <input
					type="text" name="charge_ratio" value="{$cdata1}" class="input w-1">
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