<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; <a href="#">资金设置</a> &gt; 充值、提现与转账配置</div></div>
			<div class="col-content">
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=1">银行管理</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=2">可提取余额配置</a></li>
									<li class="current"><a href="/admin/Rechargemange/fundssetting?swithval=3">充值、提现与转账配置</a></li>
									<li><a href="/admin/Rechargemange/fundssetting?swithval=4">提现审核配置</a></li>
							<!-- 		<li><a href="/admin/Rechargemange/fundssetting?swithval=5">手续费返还配置</a></li>  -->
								</ul>
							</div>
							<div>
							<form method="POST" action="/admin/Rechargemange/fundssettings?parma=sv2&sval=1">
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">充值配置</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4" >充值限额设置</td>
											<td class="text-left">
												<dl class="set-list">
													<dt>普通用户</dt>
													<dd>
														<span class="ui-text-info">工行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="100" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="45000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">农行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="100" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="45000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">建行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="100" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="45000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">招行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="100" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="45000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dt>VIP客户</dt>
													<dd>
														<span class="ui-text-info">工行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="10000" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="190000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">农行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="10000" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="190000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">建行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="10000" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="190000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dd>
														<span class="ui-text-info">招行：</span>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="10000" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="190000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
												</dl>
											</td>
										</tr>
										<tr>
											<td class="text-left">充值倒计时设置</td>
											<td class="text-left">
												<span class="ui-text-info">显示倒计时分钟数：</span>
												<input type="text" value="15" class="input w-1">
												<span class="ui-text-info">分钟</span>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<button type="submit" class="btn btn-important">保 存<b class="btn-inner"></b></button>
												<a href="javascript:void(0);" class="btn">撤销编辑<b class="btn-inner"></b></a>
											</td>
										</tr>
									</tbody>
								</table>
								</form>
								<form method="POST" action="/admin/Rechargemange/fundssettings?parma=sv2&sval=2">
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">提现配置</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4" >每日提现次数</td>
											<td class="text-left">
												<span class="ui-text-info">普通用户：</span>
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
													<option value="无限">无限</option>
												</select>
												<span class="ui-text-info">次</span>&nbsp;&nbsp;&nbsp;&nbsp;
												<span class="ui-text-info">VIP用户：</span>
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
													<option value="无限">无限</option>
												</select>
												<span class="ui-text-info">次</span>
											</td>
										</tr>
										<tr>
											<td class="text-left w-4" >提现金额</td>
											<td class="text-left">
												<dl class="set-list">
													<dt>普通用户</dt>
													<dd>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="300" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="500000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
													<dt>VIP客户</dt>
													<dd>
														<span class="ui-text-info">最低：</span>
														<input type="text" value="300" class="input w-1">
														<span class="ui-text-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
														<span class="ui-text-info">最高：</span>
														<input type="text" value="500000" class="input w-1">
														<span class="ui-text-info">元</span>
													</dd>
												</dl>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<button type="submit" class="btn btn-important">保 存<b class="btn-inner"></b></button>
												<a href="javascript:void(0);" class="btn">撤销编辑<b class="btn-inner"></b></a>
											</td>
										</tr>
									</tbody>
								</table>
								</form>
								<form method="POST" action="/admin/Rechargemange/fundssettings?parma=sv2&sval=3">
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">转账配置</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4" >每日转账限额</td>
											<td class="text-left">
												普通用户限额：<input type="text" value="10000" class="input w-1"> 元&nbsp;&nbsp;&nbsp;&nbsp;总代限额：<input type="text" value="45000" class="input w-1"> 元
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<button type="submit" class="btn btn-important">保 存<b class="btn-inner"></b></button>
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
	
	//数字校验，自动矫正不符合数学规范的数学
		var inputs = $(':text');				
		checkFn = function(){
			var me = this,v = me.value,index;
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
							
		};		
		inputs.keyup(checkFn);	inputs.blur(checkFn);
		
			
	})(jQuery);
</script>
{/literal}
</body>
</html>