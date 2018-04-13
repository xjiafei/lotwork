<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">一级菜单</a> &gt; <a href="#">二级级菜单</a> &gt; 三级菜单名</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current">银行管理</li>
									<li>可提取余额配置</li>
									<li>充值、提现与转账配置</li>
									<li>提现审核配置</li>
								</ul>
							</div>
							<div>
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th colspan="2" class="text-left">提现审核设置</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4">银行名称</td>
											<td class="text-left">农业银行</td>
										</tr>
										<tr>
											<td class="text-left">返送条件</td>
											<td class="text-left">
												<strong class="big">金额：</strong>
												<select class="ui-select w-2">
													<option value="1">大于或等于</option>
													<option value="2">大于</option>
												</select>
												<input type="text" class="input w-2" value="" />
												元&nbsp;&nbsp;&nbsp;&nbsp;返手续费
											</td>
										</tr>
										<tr>
											<td class="text-left w-4">返送金额</td>
											<td class="text-left">
												<div>
													<span class="radio-list">
														<label class="label" for="a"><input type="radio" class="radio" id="a" name="1" value="1">公式计算金额</label>
														<label class="label" for="b"><input type="radio" class="radio" id="b" name="1" value="1">实际抓取金额</label>
													</span>
												</div><br />
												<div>
													<input type="text" class="input w-2" value="" />
													<select class="ui-select w-2">
														<option value="1">大于或等于</option>
														<option value="2">大于</option>
													</select>
													x
													<select class="ui-select w-2">
														<option value="1">小于或等于</option>
														<option value="2">小于</option>
													</select>
													<input type="text" class="input w-2" value="" />
													<strong class="big">时，y为：</strong>
													<span class="radio-list">
														<label class="label" for="c"><input type="radio" class="radio" id="c" name="1" value="1">固值</label>
														<label class="label" for="d"><input type="radio" class="radio" id="d" name="1" value="1">百分比</label>
													</span>
													<input type="text" class="input w-2" value="" />
													元&nbsp;&nbsp;&nbsp;&nbsp;
													<a href="javascript:void(0);">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
													<a class="btn" href="javascript:void(0);">添加区间<b class="btn-inner"></b></a>
												</div><br />
												<div>
													<input type="text" class="input w-2" value="" />
													<select class="ui-select w-2">
														<option value="1">大于或等于</option>
														<option value="2">大于</option>
													</select>
													x
													<select class="ui-select w-2">
														<option value="1">小于或等于</option>
														<option value="2">小于</option>
													</select>
													<input type="text" class="input w-2" value="" />
													<strong class="big">时，y为：</strong>
													<span class="radio-list">
														<label class="label" for="c"><input type="radio" class="radio" id="c" name="1" value="1">固值</label>
														<label class="label" for="d"><input type="radio" class="radio" id="d" name="1" value="1">百分比</label>
													</span>
													<input type="text" class="input w-2" value="" />
													元&nbsp;&nbsp;&nbsp;&nbsp;
													<a href="javascript:void(0);">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left ui-btn">
												<a href="javascript:void(0);" class="btn btn-important">提 交<b class="btn-inner"></b></a>
												<a href="javascript:void(0);" class="btn">返 回<b class="btn-inner"></b></a>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="text-left">
												<dl>
													<dt>说明：</dt>
													<dd>1、x为用户充值金额，y为所返手续费；</dd>
													<dd>2、金额必须输入至小数点后两位；</dd>
													<dd>2、数值区间必须覆盖到0到无穷大的所有数值。</dd>
												</dl>
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
</body>
</html>			
			