<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; <a href="#">资金设置</a> &gt;  <a href="#">银行管理</a> &gt;编辑银行信息</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<li class="current"><a href="/admin/Rechargemange/fundssetting?swithval=1">银行管理</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=2">可提取余额配置</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=3">充值、提现与转账配置</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=4">提现审核配置</a></li>
								</ul>
							</div>
							<div>
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">招商银行</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4" >序号</td>
											<td class="text-left"></td>
										</tr>
										<tr>
											<td class="text-left">银行名称</td>
											<td class="text-left">
												<input type="text"  class="input">
											</td>
										</tr>
										<tr>
											<td class="text-left">银行LOGO</td>
											<td class="text-left">
												<span class="ico-bank ICBC"></span><br />
												<input type="file" class="file" value="添加附件">
												<span class="ui-prompt">支持rar、jpg、png格式，大小不超过20k</span>
											</td>
										</tr>
										<tr>
											<td class="text-left">银行代码</td>
											<td class="text-left">
												<input type="text" value="" class="input">
											</td>
										</tr>
										<tr>
											<td class="text-left">MOW银行编码</td>
											<td class="text-left">
												<input type="text" value="" class="input">
											</td>
										</tr>
										<tr>
											<td class="text-left">网银地址</td>
											<td class="text-left">
												<input type="text" value="" class="input">
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<a href="javascript:void(0);" class="btn btn-important">修 改<b class="btn-inner"></b></a>
												<a href="/admin/Rechargemange/fundssetting" class="btn">返 回<b class="btn-inner"></b></a>
											</td>
										</tr>
									</tbody>
								</table>
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