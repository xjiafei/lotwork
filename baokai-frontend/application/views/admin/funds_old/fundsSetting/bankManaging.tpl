<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; <a href="#">资金设置</a> &gt; 银行管理</div></div>
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
								<!-- 	<li><a href="/admin/Rechargemange/fundssetting?swithval=5">手续费返还配置</a></li>  -->
								</ul>
							</div>
							<div >
								<table class="table table-info table-group">
									<thead>
										<tr>
											<th>序号</th>
											<th>银行LOGO</th>
											<th>银行名称</th>
											<th>银行代码</th>
											<th>网银地址</th>
											<th>使用状态</th>
											<th>充值</th>
											<th>提现</th>
											<th>启用状态</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td><span class="ico-bank ICBC"></span></td>
											<td>中国工商银行</td>
											<td>ICBC</td>
											<td>www.icbc.com</td>
											<td>使用中</td>
											<td>启用</td>
											<td>启用</td>
											<td>
                                                <select name="here " onchange="location.href=this.options[this.selectedIndex].value;">
                                                    <option value=''>操作</option>
                                                    <option value=''>禁用使用</option>
                                                    <option  value=''>禁用充值</option>
                                                    <option value=''>禁用提现</option>
                                                    <option value=''>禁用手续费返还</option>
                                                    <option value=''>取消绑卡</option>
                                                    <option value="/admin/Rechargemange/fundssetting?swithval=5">编辑手续费返还</option>
                                                    <option  value="/admin/Rechargemange/fundssetting?swithval=6">编辑银行信息 </option>                                               
                                                </select>												
											</td>
										</tr>
										<tr>
											<td>02</td>
											<td><span class="ico-bank ABC"></span></td>
											<td>中国农业银行</td>
											<td>ABC</td>
											<td>www.abc.com</td>
											<td>禁用</td>
											<td>禁用</td>
											<td>禁用</td>
											<td>
												  <select name="here " onchange="location.href=this.options[this.selectedIndex].value;">
                                                    <option value=''>操作</option>
                                                    <option value=''>禁用使用</option>
                                                    <option  value=''>禁用充值</option>
                                                    <option value=''>禁用提现</option>
                                                    <option value=''>禁用手续费返还</option>
                                                    <option value=''>取消绑卡</option>
                                                    <option value="/admin/Rechargemange/fundssetting?swithval=5">编辑手续费返还</option>
                                                    <option  value="/admin/Rechargemange/fundssetting?swithval=6">编辑银行信息 </option>                                               
                                                </select>		
											</td>
										</tr>
									</tbody>
								</table>
								<div class="page-wrapper">
									<div class="page page-right">
										<a class="prev" href="#">上一步</a>
										<a href="#">1</a>
										<a href="#">2</a>
										<a class="current" href="#">3</a>
										<a href="#">4</a>
										<a href="#">5</a>
										<a href="#">6</a>
										<a href="#">7</a>
										<a href="#">8</a>
										<a href="#">9</a>
										<a href="#">...</a>
										<a class="next" href="#">下一步</a>
										<span class="page-few">到第 <input type="text" class="input" value=""> /100页</span>
										<input type="button" class="page-btn" value="确 认">
									</div>
								</div>
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