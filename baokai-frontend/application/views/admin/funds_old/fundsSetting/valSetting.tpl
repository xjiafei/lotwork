<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; <a href="#">资金设置</a> &gt; 可提取余额配置</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=1">银行管理</a></li>
									<li class="current"><a href="/admin/Rechargemange/fundssetting?swithval=1">可提取余额配置</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=3">充值、提现与转账配置</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=4">提现审核配置</a></li>
							<!-- 		<li><a href="/admin/Rechargemange/fundssetting?swithval=5">手续费返还配置</a></li>  -->
								</ul>
							</div>
							<div>
							
							<form method="POST" action="/admin/Rechargemange/fundssettings?parma=sv1">
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">不可提现余额</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4" >需累计的金额类型</td>
											<td class="text-left">
												<span class="checkbox-list ui-text-info">
													<label class="label" for="a"><input type="checkbox" class="radio" id="a" name="rad_put" value="0" checked="checked">在线充值</label>
													<label class="label" for="b"><input type="checkbox" class="radio" id="b" name="rad_put" value="1">转账</label>
													<label class="label" for="b"><input type="checkbox" class="radio" id="b" name="rad_put" value="1">人工充值</label>
												</span>
											</td>
										</tr>
										<tr>
											<td class="text-left">不可提现余额/充值金额（百分比）</td>
											<td class="text-left">
												<select class="ui-select w-1">
													<option value="5">5</option>
													<option value="10">10</option>
													<option value="15">15</option>
													<option value="20">20</option>
													<option value="25">25</option>
													<option value="30">30</option>
													<option value="35">35</option>
													<option value="40">40</option>
													<option value="45">45</option>
												</select>
												<span class="ui-text-info">%</span>
											</td>
										</tr>
										<tr>
											<td class="text-left">游戏消耗减不可提现余额比例</td>
											<td class="text-left">
												<span class="ui-text-info">1:</span>
												<select class="ui-select w-1">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
												</select>
												<span class="ui-text-info">(游戏消耗：扣除不可提下游戏币数量)</span>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<button type="submit" class="btn btn-important">保存配置<b class="btn-inner"></b></button>
												<a href="javascript:void(0);" class="btn">撤销编辑<b class="btn-inner"></b></a>
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
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawals');
		
	})(jQuery);
</script>
{/literal}
</body>
</html>