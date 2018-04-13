<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">一级菜单</a> &gt; <a href="#">二级级菜单</a> &gt; 三级菜单名</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">添加可疑银行卡</h3>
						<ul class="ui-form">
							<li>
								<label class="ui-label">*银行卡号：</label>
								<input type="text" value="" class="input w-4">
							</li>
							<li>
								<label class="ui-label">*选择状态：</label>
								<select class="ui-select">
									<option value="所有">所有</option>
									<option value="可疑卡">可疑卡</option>
									<option value="骗子卡">骗子卡</option>
									<option value="重复付款">重复付款</option>
									<option value="其他">其他</option>
								</select>
							</li>
							<li>
								<label class="ui-label">备注：</label>
								<div class="textarea w-6">
									<textarea id="text"></textarea>
								</div>
								<span class="ui-info vertical-bottom">0/100</span>
							</li>
							<li class="ui-btn">
								<a href="javascript:void(0);" class="btn btn-important">添加<b class="btn-inner"></b></a>
								<a href="javascript:void(0);" class="btn">返 回<b class="btn-inner"></b></a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>