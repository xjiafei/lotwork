<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; <a href="/admin/user/freezeuserlist">客户异常</a> &gt;冻结用户管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current">冻结名单</li>
									<li>历史冻结记录</li>
								</ul>
							</div>
							<div class="ui-tab-content">
								<ul class="ui-search clearfix">
									<li class="name">
										<select class="ui-select">
											<option value="用户名">用户名</option>
											<option value="邮箱">邮箱</option>
										</select>
										<input type="text" class="input" id="name" value="请输入您的用户名">
									</li>
									<li><a href="javascript:void(0);" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
								</ul>
								<table class="table table-info">
									<thead>
										<tr>
											<th>用户名</th>
											<th>用户组</th>
											<th>余额</th>
											<th>冻结时间</th>
											<th>操作</th>
											<th>冻结原因</th>
											<th>操作人</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>vava</td>
											<td>总代</td>
											<td>100,000.00元</td>
											<td>2013-04-07 15:23</td>
											<td class="table-tool">
												<a class="ico-unlock" title="解冻" href="javascript:void(0);"></a>
											</td>
											<td>恶意竞争</td>
											<td>rain</td>
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


</body>
</html>