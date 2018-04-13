<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><a href="/admin/Fundsmanage/accountbalance" class="more">返回上一步</a><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt; <a href="#">账户余额加减</a> &gt; 加减申请</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-form">
							<li style="margin-left:20px;">
								<a href="/admin/Fundsmanage/accountbalance?swithval=6&label=1" class="btn">活动加币<b class="btn-inner"></b></a>
								<a href="/admin/Fundsmanage/accountbalance?swithval=6&label=2" class="btn">人工扣款<b class="btn-inner"></b></a>
								<a href="/admin/Fundsmanage/accountbalance?swithval=6&label=3" class="btn">理赔加币<b class="btn-inner"></b></a>
							</li>
						</ul>
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
	selectMenu('Menufunds','MenuFundConfig');

})();	
</script>
{/literal}
</body>
</html>
</body>
</html>