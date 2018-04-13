<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">银行卡管理</a> &gt; <a href="#">银行卡绑定记录</a> &gt; 银行卡绑定配置</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><a class="btn btn-small" href="/admin/Bankcardsmanage">返回银行卡绑定列表<b class="btn-inner"></b></a>银行卡绑定配置</h3>
						<form method="POST" action="/admin/Bankcardsmanage/index?parma=sv4">
						<table class="table table-info table-border">
							<thead>
								<tr>
									<th colspan="2" class="text-left">可绑银行卡数量配置</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-left w-4" >用户可绑银行卡数量</td>
									<td class="text-left">
										<select class="ui-select w-1" name="sele_bank">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4" selected="selected">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2" class="text-left ui-btn">
										<button type="submit" class="btn btn-important">保 存<b class="btn-inner"></b></button>
										<a href="javascript:void(0);" class="btn">返 回<b class="btn-inner"></b></a>
									</td>
								</tr>
							</tbody>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuOpterators');	
	
	
})();	
</script>
{/literal}
</body>
</html>
</body>
</html>