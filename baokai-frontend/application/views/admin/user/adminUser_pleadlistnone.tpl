<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
				<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; <a href="/admin/user/accomplaints">账号申诉管理</a> &gt; vava</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-search clearfix">
							<li class="name">
								<label class="ui-label">用户名：</label>
								<input type="text" class="input" id="name" value="请输入您的用户名">
							</li>
							<li class="name">
								<label class="ui-label">审核人：</label>
								<input type="text" class="input" id="" value="">
							</li>
							<li>
								<label class="ui-label">状态：</label>
								<select class="ui-select">
									<option value="所有状态">所有状态</option>
									<option value="通过">通过</option>
									<option value="未通过">未通过</option>
									<option value="未审核">未审核</option>
								</select>
							</li>
							<li>
								<label class="ui-label">申诉类型：</label>
								<select class="ui-select">
									<option value="所有状态">所有状态</option>
									<option value="通过">安全信息</option>
									<option value="未通过">安全邮箱</option>
								</select>
							</li>
							<li><a href="javascript:void(0);" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
						</ul>
						<div class="alert alert-waring">
							<i></i>
							<div class="txt">
								<h4>没有符合条件的记录，请更改查询条件！</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
    <script  src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
	{literal}
	<script > 
	(function($){	
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUseraccomplaints');
		
		
	})(jQuery);
	</script>
	{/literal}
</body>
</html>